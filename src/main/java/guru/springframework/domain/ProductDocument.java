package guru.springframework.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@Setter
@Getter
@Document(collection = "products")
public class ProductDocument {

    @Id
    private ObjectId id;

    private String productId;
    private String description;
    private BigDecimal price;
    private String imageUrl;

}
