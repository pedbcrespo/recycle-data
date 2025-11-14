package com.recycle.data.service;

import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.recycle.data.model.Product;
import com.recycle.data.model.ProductType;
import com.recycle.data.model.dto.ProductDto;
import com.recycle.data.model.request.ProductRequest;
import com.recycle.data.repository.ProductRepository;
import com.recycle.data.repository.ProductTypeRepository;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductTypeRepository productTypeRepository;

    public void register(ProductDto newProduct) throws Exception {
        Product product = createNewProduct(newProduct);
        if(product.getProductType() == null) return;
        product.setName(product.getName().toLowerCase().trim());
        productRepository.save(product);
    }

    public void register(ProductType productType) throws Exception {
        if(productType.getName() == null) return;
        productType.setName(productType.getName().toLowerCase().trim());
        productTypeRepository.save(productType);
    }

    public Collection<Product> get(ProductRequest request) {
       return productRepository.findAll(request.getSpecification());
    }

    public Collection<ProductType> getTypes() {
        return productTypeRepository.findAll();
    }

    private Product createNewProduct(ProductDto newProduct) {
        Optional<ProductType> optionalType = productTypeRepository.findById(newProduct.getTypeId());
        return optionalType.map(productType -> new Product(newProduct, productType)).orElse(null);
    }
}
