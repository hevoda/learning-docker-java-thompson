package com.hervodalabs.formidable.services;

import com.hervodalabs.formidable.commands.ProductForm;
import com.hervodalabs.formidable.domain.ProductEntity;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by jt on 1/10/17.
 */

public interface ProductService {

    List<ProductEntity> listAll();

    ProductEntity getById(Long id);

    ProductEntity saveOrUpdate(ProductEntity productEntity);

    void delete(Long id);

    ProductEntity saveOrUpdateProductForm(ProductForm productForm);
}
