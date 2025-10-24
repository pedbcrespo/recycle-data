package com.recycle.data.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Collection;

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

    @JoinTable(name = "recycle_product_type", joinColumns = @JoinColumn(name = "recycling_center_id"), inverseJoinColumns = @JoinColumn(name = "product_type_id"))
    @ManyToMany
    private Collection<ProductType> recycleProductType;


    public boolean isAvailableToSave() {
        return name != null && address != null;
    }
}
