package guru.springframework.repositories;

import guru.springframework.domain.ProductEntity;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by jt on 1/10/17.
 */
@Profile("h2")
@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
}
