package guru.springframework.services;


import guru.springframework.domain.ProductDocument;
import guru.springframework.repositories.ProductDocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Profile("mongo")
public class ProductServiceMongoImpl {

    private final ProductDocumentRepository productRepository;

    @Autowired
    public ProductServiceMongoImpl(ProductDocumentRepository productRepository) {
        this.productRepository = productRepository;
    }

  
    public List<ProductDocument> listAll() {
        return new ArrayList<>(productRepository.findAll());
    }


    public ProductDocument getById(String id) {
        return productRepository.findById(id).orElse(null);
    }


    public ProductDocument saveOrUpdate(ProductDocument product) {
        productRepository.save(product);
        return product;
    }


    public void delete(String id) {
        productRepository.deleteById(id);
    }




}
