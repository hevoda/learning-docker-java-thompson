package guru.springframework.repositories;

import guru.springframework.domain.ProductDocument;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by jt on 1/10/17.
 */
@Profile("mongo")
@Repository
public interface ProductDocumentRepository extends MongoRepository<ProductDocument, String> {
}
