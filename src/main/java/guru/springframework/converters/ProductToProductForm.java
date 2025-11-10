package guru.springframework.converters;

import guru.springframework.commands.ProductForm;
import guru.springframework.domain.ProductEntity;
import org.springframework.context.annotation.Profile;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 * Created by jt on 1/10/17.
 */
@Component
@Profile({"h2", "dev"})
public class ProductToProductForm implements Converter<ProductEntity, ProductForm> {
    @Override
    public ProductForm convert(ProductEntity ProductEntity) {
        ProductForm productForm = new ProductForm();
        productForm.setId(productForm.getId());
        productForm.setDescription(ProductEntity.getDescription());
        productForm.setPrice(ProductEntity.getPrice());
        productForm.setImageUrl(ProductEntity.getImageUrl());
        return productForm;
    }
}
