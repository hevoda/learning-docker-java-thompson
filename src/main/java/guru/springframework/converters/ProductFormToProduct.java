package guru.springframework.converters;

import guru.springframework.commands.ProductForm;
import guru.springframework.domain.ProductJPA;
import org.bson.types.ObjectId;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * Created by jt on 1/10/17.
 */
@Component
public class ProductFormToProduct implements Converter<ProductForm, ProductJPA> {

    @Override
    public ProductJPA convert(ProductForm productForm) {
        ProductJPA productJPA = new ProductJPA();
        if (productForm.getId() != null  && !StringUtils.isEmpty(productForm.getId())) {
            productJPA.setId(productForm.getId());
        }
        productJPA.setDescription(productForm.getDescription());
        productJPA.setPrice(productForm.getPrice());
        productJPA.setImageUrl(productForm.getImageUrl());
        return productJPA;
    }
}
