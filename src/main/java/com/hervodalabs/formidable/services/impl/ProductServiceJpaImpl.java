package com.hervodalabs.formidable.services.impl;

import com.hervodalabs.formidable.commands.ProductForm;
import com.hervodalabs.formidable.converters.ProductFormToProduct;
import com.hervodalabs.formidable.domain.ProductEntity;
import com.hervodalabs.formidable.repositories.ProductRepository;
import com.hervodalabs.formidable.services.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;


@Slf4j
@Profile("mysql")
@Service
public class ProductServiceJpaImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductFormToProduct toProduct;

    public ProductServiceJpaImpl(ProductRepository productRepository, ProductFormToProduct toProduct) {
        this.productRepository = productRepository;
        this.toProduct = toProduct;
    }

    @Override
    public List<ProductEntity> listAll() {
        log.debug("Fetching all products from repository");
        List<ProductEntity> products = productRepository.findAll();
        log.info("Fetched {} products", products.size());
        return products;
    }

    @Override
    public ProductEntity getById(Long id) {
        log.debug("Fetching product by ID: {}", id);
        return productRepository.findById(id)
                .orElseGet(() -> {
                    log.warn("Product with ID {} not found", id);
                    return null;
                });
    }

    @Override
    public ProductEntity saveOrUpdate(ProductEntity entity) {
        log.debug("Saving/updating product: {}", entity.getDescription());
        ProductEntity saved = productRepository.save(entity);
        log.info("Saved product with ID: {}", saved.getId());
        return saved;
    }

    @Override
    public void delete(Long id) {
        log.debug("Deleting product with ID: {}", id);
        productRepository.deleteById(id);
        log.info("Deleted product with ID: {}", id);
    }

    @Override
    public ProductEntity saveOrUpdateProductForm(ProductForm form) {
        log.debug("Converting ProductForm to ProductEntity for saving: {}", form.getDescription());
        ProductEntity entity = toProduct.convert(form);
        ProductEntity saved = saveOrUpdate(Objects.requireNonNull(entity));
        log.info("Saved ProductEntity from form with ID: {}", saved.getId());
        return saved;
    }
}
