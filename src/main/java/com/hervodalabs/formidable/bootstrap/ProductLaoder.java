package com.hervodalabs.formidable.bootstrap;


import com.hervodalabs.formidable.domain.ProductEntity;
import com.hervodalabs.formidable.repositories.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@Slf4j
@Profile({"mysql"})
public class ProductLaoder implements ApplicationListener<ContextRefreshedEvent> {

    private final ProductRepository productRepository;


    @Autowired
    public ProductLaoder(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }




    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {

        ProductEntity shirt = new ProductEntity();
        shirt.setDescription("Spring Framework Guru Shirt");
        shirt.setPrice(new BigDecimal("18.95"));
        shirt.setImageUrl("https://springframework.guru/wp-content/uploads/2015/04/spring_framework_guru_shirt-rf412049699c14ba5b68bb1c09182bfa2_8nax2_512.jpg");

        productRepository.save(shirt);

        log.info("Saved Shirt - id: " + shirt.getId());

        ProductEntity mug = new ProductEntity();
        mug.setDescription("Spring Framework Guru Mug");
        mug.setImageUrl("https://springframework.guru/wp-content/uploads/2015/04/spring_framework_guru_coffee_mug-r11e7694903c348e1a667dfd2f1474d95_x7j54_8byvr_512.jpg");

        mug.setPrice(new BigDecimal("11.95"));
        productRepository.save(mug);

        log.info("Saved Mug - id:" + mug.getId());
    }
}
