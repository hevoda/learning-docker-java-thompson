package guru.springframework.services;

import guru.springframework.commands.ProductForm;
import guru.springframework.converters.ProductFormToProduct;
import guru.springframework.domain.ProductJPA;
import guru.springframework.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jt on 1/10/17.
 */
@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductFormToProduct productFormToProduct;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, ProductFormToProduct productFormToProduct) {
        this.productRepository = productRepository;
        this.productFormToProduct = productFormToProduct;
    }


    @Override
    public List<ProductJPA> listAll() {
        List<ProductJPA> productJPAS = new ArrayList<>();
        productRepository.findAll().forEach(productJPAS::add); //fun with Java 8
        return productJPAS;
    }

    @Override
    public ProductJPA getById(String id) {
        return productRepository.findById(id).orElse(null);
    }

    @Override
    public ProductJPA saveOrUpdate(ProductJPA productJPA) {
        productRepository.save(productJPA);
        return productJPA;
    }

    @Override
    public void delete(String id) {
        productRepository.deleteById(id);
    }

    @Override
    public ProductJPA saveOrUpdateProductForm(ProductForm productForm) {
        ProductJPA savedProductJPA = saveOrUpdate(productFormToProduct.convert(productForm));

        System.out.println("Saved ProductJPA Id: " + savedProductJPA.getId());
        return savedProductJPA;
    }
}
