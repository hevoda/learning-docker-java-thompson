package guru.springframework.converters;

import guru.springframework.commands.ProductForm;
import guru.springframework.domain.ProductJPA;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 * Created by jt on 1/10/17.
 */
@Component
public class ProductToProductForm implements Converter<ProductJPA, ProductForm> {
    @Override
    public ProductForm convert(ProductJPA productJPA) {
        ProductForm productForm = new ProductForm();
        productForm.setId(productForm.getId());
        productForm.setDescription(productJPA.getDescription());
        productForm.setPrice(productJPA.getPrice());
        productForm.setImageUrl(productJPA.getImageUrl());
        return productForm;
    }
}
