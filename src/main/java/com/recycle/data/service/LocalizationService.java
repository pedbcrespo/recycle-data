package com.recycle.data.service;

import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.recycle.data.model.City;
import com.recycle.data.model.Location;
import com.recycle.data.model.State;
import com.recycle.data.model.dto.IbgeCityDto;
import com.recycle.data.model.dto.IbgeDistrictDto;
import com.recycle.data.model.dto.IbgeLocationDto;
import com.recycle.data.model.dto.LocationDto;
import com.recycle.data.model.request.LocationRequest;
import com.recycle.data.repository.CityRepository;
import com.recycle.data.repository.DistrictRepository;
import com.recycle.data.repository.StateRepository;

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

    public List<State> getStates() {
        return stateRepository.findAll();
    }

    public List<LocationDto> getCities(Long stateId) {
        return cityRepository.findByStateId(stateId).stream().map(LocationDto::new).toList();
    }

    public City getCity(Long cityId) {
        return cityRepository.findById(cityId).orElse(null);
    }

    public List<LocationDto> consumeApiCitiesByStates(LocationRequest request) {
        List<State> states;
        if(!request.hasStateListToGet()) return null;
        if(request.getStateIds() != null && !request.getStateIds().isEmpty()) {
            states = stateRepository.findAllById(request.getStateIds());
        }
        else {
            states = stateRepository.findAllByUfIn(request.getUfs());
        }
        return processStates(states);
    }

    public List<LocationDto> consumeApiDistrictsByCities(LocationRequest request) {
        if(!request.hasCityListToGet()) return null;
        List<City> cities = cityRepository.findAllById(request.getCityIds());
        return processCities(cities);
    }

    private List<LocationDto> processCities(List<City> cities) {
        process(cities, this::getDistricts);
        return districtRepository.findByCityIdIn(cities.stream().map(City::getId).toList()).stream().map(LocationDto::new).toList();
    }

    private List<LocationDto> processStates(List<State> states) {
        process(states, this::getCities);
        return cityRepository.findByStateIdIn(states.stream().map(State::getId).toList()).stream().map(LocationDto::new).toList();
    }

    @Transactional
    private void getCities(State state) {
        String url = getCitiesUrl(state.getUf());
        ParameterizedTypeReference<List<IbgeCityDto>> typeRef = new ParameterizedTypeReference<>() {
        };
        fetchAndSave(url, typeRef, state, cityRepository);
    }

    @Transactional
    private void getDistricts(City city) {
        String url = getDistrictsUrl(city.getId());
        ParameterizedTypeReference<List<IbgeDistrictDto>> typeRef = new ParameterizedTypeReference<>() {
        };
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

    private <D extends IbgeLocationDto<E, P>, E, P> void fetchAndSave(String url, ParameterizedTypeReference<List<D>> typeRef, P parent, JpaRepository<E, Long> repository) {
        Collection<D> dtos = service.consume(url, typeRef);
        List<E> entities = dtos.stream()
                .map(dto -> dto.generate(parent))
                .toList();
        repository.saveAll(entities);
    }
}
