package guru.springframework.services;

import guru.springframework.commands.ProductForm;
import guru.springframework.domain.ProductEntity;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by jt on 1/10/17.
 */
@Service
public interface ProductService {

    List<ProductEntity> listAll();

    ProductEntity getById(String id);

    ProductEntity saveOrUpdate(ProductEntity productEntity);

    void delete(String id);

    ProductEntity saveOrUpdateProductForm(ProductForm productForm);
}
