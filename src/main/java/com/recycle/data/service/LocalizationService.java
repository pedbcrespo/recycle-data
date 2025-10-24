package com.recycle.data.service;

import com.recycle.data.enums.Region;
import com.recycle.data.model.City;
import com.recycle.data.model.Location;
import com.recycle.data.model.State;
import com.recycle.data.model.dto.CityDto;
import com.recycle.data.model.dto.DistrictDto;
import com.recycle.data.model.dto.LocationDto;
import com.recycle.data.repository.CityRepository;
import com.recycle.data.repository.DistrictRepository;
import com.recycle.data.repository.StateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

@Service
public class LocalizationService {
    @Autowired
    private ApiService service;

    @Autowired
    private StateRepository stateRepository;

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private DistrictRepository districtRepository;

    private final String baseUrl = "https://servicodados.ibge.gov.br/api/v1/localidades";

    public void consumeApiCitiesByStates() {
        List<State> north = stateRepository.findByRegion(Region.NORTH);
        List<State> northeast = stateRepository.findByRegion(Region.NORTHEAST);
        List<State> southeast = stateRepository.findByRegion(Region.SOUTHEAST);
        List<State> south = stateRepository.findByRegion(Region.SOUTH);
        List<State> midwest = stateRepository.findByRegion(Region.MIDWEST);

        List<CompletableFuture<Void>> futures = List.of(
                CompletableFuture.runAsync(() -> processStates(north)),
                CompletableFuture.runAsync(() -> processStates(northeast)),
                CompletableFuture.runAsync(() -> processStates(southeast)),
                CompletableFuture.runAsync(() -> processStates(south)),
                CompletableFuture.runAsync(() -> processStates(midwest))
        );
        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
    }

    public void consumeApiDistrictsByCities() {
        List<City> north = cityRepository.findByStateRegion(Region.NORTH);
        List<City> northeast = cityRepository.findByStateRegion(Region.NORTHEAST);
        List<City> southeast = cityRepository.findByStateRegion(Region.SOUTHEAST);
        List<City> south = cityRepository.findByStateRegion(Region.SOUTH);
        List<City> midwest = cityRepository.findByStateRegion(Region.MIDWEST);

        List<CompletableFuture<Void>> futures = List.of(
                CompletableFuture.runAsync(() -> processCities(north)),
                CompletableFuture.runAsync(() -> processCities(northeast)),
                CompletableFuture.runAsync(() -> processCities(southeast)),
                CompletableFuture.runAsync(() -> processCities(south)),
                CompletableFuture.runAsync(() -> processCities(midwest))
        );
        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
    }

    private void processCities(List<City> cities) {
        process(cities, this::getDistricts);
    }

    private void processStates(List<State> states) {
        process(states, this::getCities);
    }

    @Transactional
    private void getCities(State state) {
        String url = getCitiesUrl(state.getUf());
        ParameterizedTypeReference<List<CityDto>> typeRef = new ParameterizedTypeReference<List<CityDto>>() {};
        fetchAndSave(url, typeRef, state, cityRepository);
    }

    @Transactional
    private void getDistricts(City city) {
        String url = getDistrictsUrl(city.getId());
        ParameterizedTypeReference<List<DistrictDto>> typeRef = new ParameterizedTypeReference<List<DistrictDto>>() {};
        fetchAndSave(url, typeRef, city, districtRepository);
    }

    private String getCitiesUrl(String uf) {
        return this.baseUrl + "/estados/" + uf.toUpperCase() + "/municipios";
    }

    private String getDistrictsUrl(Long cityId ) {
        return this.baseUrl + "/municipios/" + cityId.toString() + "/distritos";
    }

    private <L extends Location> void process(List<L> processList, Consumer<L> consumer) {
        for(L element : processList) {
            try {
                consumer.accept(element);
            } catch (Exception e) {
                System.err.println("Erro ao processar " + element.getName() + ": " + e.getMessage());
                throw new RuntimeException();
            }
        }
    }

    private <D extends LocationDto<E, P>, E, P> void fetchAndSave(String url, ParameterizedTypeReference<List<D>> typeRef, P parent, JpaRepository<E, Long> repository) {
        Collection<D> dtos = service.consume(url, typeRef);
        List<E> entities = dtos.stream()
                .map(dto -> dto.generate(parent))
                .toList();
        repository.saveAll(entities);
    }
}
