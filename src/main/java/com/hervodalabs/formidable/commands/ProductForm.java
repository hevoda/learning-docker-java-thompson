package com.hervodalabs.formidable.commands;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.context.annotation.Profile;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@Profile({"mysql"})
public class ProductForm {

    private Long id;

    @NotBlank(message = "La descrizione è obbligatoria")
    private String description;

    @NotNull(message = "Il prezzo è obbligatorio")
    @DecimalMin(value = "0.01", message = "Il prezzo deve essere maggiore di zero")
    private BigDecimal price;

    private String imageUrl;
}
