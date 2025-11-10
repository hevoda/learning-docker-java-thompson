package guru.springframework.converters;

import guru.springframework.commands.ProductForm;
import guru.springframework.domain.ProductDocument;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;


@Component
@Profile("mongo")
public class ProductDocumentMapper  {

    public ProductDocument toDocument(ProductForm source) {
        ProductDocument productDocument = new ProductDocument();
        productDocument.setProductId(source.getId().toString());
        productDocument.setPrice(source.getPrice());
        productDocument.setImageUrl(source.getImageUrl());
        productDocument.setDescription(source.getDescription());
        return null;
    }


    public ProductForm toProductForm(ProductDocument source) {
        ProductForm productForm = new ProductForm();
        productForm.setId(Long.valueOf(source.getId().toString()));
        productForm.setDescription(source.getDescription());
        productForm.setPrice(source.getPrice());
        productForm.setImageUrl(source.getImageUrl());
        return productForm;
    }


}
