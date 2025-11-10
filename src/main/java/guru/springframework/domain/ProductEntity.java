package guru.springframework.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Version;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Profile;

import java.math.BigDecimal;

/**
 * Created by jt on 1/10/17.
 */
@Setter
@Getter
@Entity
@Profile({"h2", "dev"})
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Version
    private Integer version;

    private String productId;
    private String description;
    private BigDecimal price;
    private String imageUrl;

}
