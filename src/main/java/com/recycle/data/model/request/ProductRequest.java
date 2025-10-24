package com.recycle.data.model.request;

import com.recycle.data.model.Product;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Getter
@Setter
public class ProductRequest {
    private String name;
    private Collection<Long> productTypeIds;

    public Specification<Product> getSpecification() {
        if(name == null && (productTypeIds == null || productTypeIds.isEmpty())) return null;
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (name != null && !name.isBlank()) {
                predicates.add(cb.like(cb.lower(root.get("name")), "%" + name.toLowerCase() + "%"));
            }

            if (productTypeIds != null && !productTypeIds.isEmpty()) {
                Join<Object, Object> typeJoin = root.join("productType");
                predicates.add(typeJoin.get("id").in(productTypeIds));
            }
            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
