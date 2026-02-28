package com.hervodalabs.formidable.converters;

import com.hervodalabs.formidable.commands.ProductForm;
import com.hervodalabs.formidable.domain.ProductEntity;
import org.apache.commons.text.StringEscapeUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ProductToProductForm implements Converter<ProductEntity, ProductForm> {

    @Override
    public ProductForm convert(ProductEntity entity) {
        if (entity == null) return null;

        ProductForm form = new ProductForm();

        form.setId(entity.getId());
        form.setDescription(desanitize(entity.getDescription()));
        form.setPrice(entity.getPrice());
        form.setImageUrl(desanitize(entity.getImageUrl()));

        return form;
    }

    /**
     * Desanitizzazione per visualizzazione nei form HTML.
     * Mantiene sicurezza XSS, ma rende il testo leggibile.
     */
    private String desanitize(String value) {
        if (value == null) return null;
        return StringEscapeUtils.unescapeHtml4(value.trim());
    }
}
