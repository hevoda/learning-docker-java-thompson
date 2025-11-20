package guru.springframework.converters;

import guru.springframework.commands.ProductForm;
import guru.springframework.domain.ProductEntity;
import org.apache.commons.text.StringEscapeUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ProductFormToProduct implements Converter<ProductForm, ProductEntity> {

    @Override
    public ProductEntity convert(ProductForm form) {
        if (form == null) return null;

        ProductEntity entity = new ProductEntity();

        // Aggiorna ID se presente → update, altrimenti nuovo prodotto
        entity.setId(form.getId());

        entity.setDescription(sanitize(form.getDescription()));
        entity.setImageUrl(sanitize(form.getImageUrl()));
        entity.setPrice(form.getPrice());

        return entity;
    }

    private String sanitize(String value) {
        return value == null ? null : StringEscapeUtils.escapeHtml4(value.trim());
    }
}
