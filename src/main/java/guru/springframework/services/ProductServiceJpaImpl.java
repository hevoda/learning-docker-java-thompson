package guru.springframework.services;

import guru.springframework.commands.ProductForm;
import guru.springframework.converters.ProductFormToProduct;
import guru.springframework.domain.ProductEntity;
import guru.springframework.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jt on 1/10/17.
 */
@Service
@Profile({"h2", "dev"})
public class ProductServiceJpaImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductFormToProduct productFormToProduct;

    @Autowired
    public ProductServiceJpaImpl(ProductRepository productRepository, ProductFormToProduct productFormToProduct) {
        this.productRepository = productRepository;
        this.productFormToProduct = productFormToProduct;
    }


    @Override
    public List<ProductEntity> listAll() {
        List<ProductEntity> productEntities = new ArrayList<>();
        productRepository.findAll().forEach(productEntities::add); //fun with Java 8
        return productEntities;
    }

    @Override
    public ProductEntity getById(String id) {
        return productRepository.findById(id).orElse(null);
    }

    @Override
    public ProductEntity saveOrUpdate(ProductEntity ProductEntity) {
        productRepository.save(ProductEntity);
        return ProductEntity;
    }

    @Override
    public void delete(String id) {
        productRepository.deleteById(id);
    }

    @Override
    public ProductEntity saveOrUpdateProductForm(ProductForm productForm) {
        ProductEntity savedProductEntity = saveOrUpdate(productFormToProduct.convert(productForm));

        System.out.println("Saved ProductEntity Id: " + savedProductEntity.getId());
        return savedProductEntity;
    }
}
