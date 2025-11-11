package com.recycle.data.model;

import java.util.Collection;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter@Setter
@NoArgsConstructor
public class RecyclingCenter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Lob
    private String address;
    @JoinColumn(name = "city_id", referencedColumnName = "id")
    @ManyToOne
    private City city;


    @JoinTable(name = "recycle_product_type", joinColumns = @JoinColumn(name = "recycling_center_id"), inverseJoinColumns = @JoinColumn(name = "product_type_id"))
    @ManyToMany
    private Collection<ProductType> recycleProductType;

    public RecyclingCenter(String name, String address, City city, Collection<ProductType> recycleProductType) {
        this.name = name;
        this.address = address;
        this.city = city;
        this.recycleProductType = recycleProductType;
    }
}
