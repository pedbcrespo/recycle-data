package com.recycle.data.model.specification;

import com.recycle.data.model.Product;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.Specification;
import jakarta.persistence.criteria.Join;



import java.util.Collection;

@Getter@Setter
public class ProductSpecification {
    public static Specification<Product> hasProductTypeId(Long productTypeId) {
        return (root, query, cb) -> {
            if (productTypeId == null) {
                return cb.conjunction();
            }
            Join<Object, Object> typeJoin = root.join("productType");
            return cb.equal(typeJoin.get("id"), productTypeId);
        };
    }

    public static Specification<Product> hasName(String name) {
        return (root, query, cb) -> {
            if (name == null || name.isBlank()) {
                return cb.conjunction();
            }
            return cb.like(cb.lower(root.get("name")), "%" + name.toLowerCase() + "%");
        };
    }
}
