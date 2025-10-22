package guru.springframework.services;

import guru.springframework.commands.ProductForm;
import guru.springframework.domain.ProductJPA;

import java.util.List;

/**
 * Created by jt on 1/10/17.
 */
public interface ProductService {

    List<ProductJPA> listAll();

    ProductJPA getById(String id);

    ProductJPA saveOrUpdate(ProductJPA productJPA);

    void delete(String id);

    ProductJPA saveOrUpdateProductForm(ProductForm productForm);
}
