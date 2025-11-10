package guru.springframework.converters;

import guru.springframework.commands.ProductForm;
import guru.springframework.domain.ProductEntity;
import org.springframework.context.annotation.Profile;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * Created by jt on 1/10/17.
 */
@Component
@Profile({"h2", "dev"})
public class ProductFormToProduct implements Converter<ProductForm, ProductEntity> {

    @Override
    public ProductEntity convert(ProductForm productForm) {
        ProductEntity ProductEntity = new ProductEntity();
        if (productForm.getId() != null  && !StringUtils.isEmpty(productForm.getId())) {
            ProductEntity.setId(productForm.getId());
        }
        ProductEntity.setDescription(productForm.getDescription());
        ProductEntity.setPrice(productForm.getPrice());
        ProductEntity.setImageUrl(productForm.getImageUrl());
        return ProductEntity;
    }
}
