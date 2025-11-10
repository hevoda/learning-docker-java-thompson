package guru.springframework;

import guru.springframework.domain.ProductEntity;
import guru.springframework.repositories.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@ActiveProfiles("h2")
public class SpringBootMongodbApplicationTests {

	/*@Autowired
	private MongoTemplate mongoTemplate;

	@Test
	public void contextLoads() {
		// Verifica se il MongoTemplate è correttamente autoiniettato
		assert mongoTemplate != null;
	}*/


	@Autowired
    private ProductRepository productRepository;

    @Test
    void contextLoads() {
        // Test JPA con H2
        ProductEntity product = new ProductEntity();
        product.setDescription("Test Product");
        product.setPrice(new BigDecimal("9.99"));
        product.setProductId("12345");
        productRepository.save(product);

        ProductEntity savedProduct = productRepository.findById(String.valueOf(product.getId())).orElse(null);
        assertNotNull(savedProduct);  // Verifica che il prodotto sia stato salvato correttamente
    }


}
