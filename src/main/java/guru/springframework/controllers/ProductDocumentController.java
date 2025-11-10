package guru.springframework.controllers;


import guru.springframework.domain.ProductDocument;
import guru.springframework.services.ProductServiceMongoImpl;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/api/products")
@Profile("mongo")
public class ProductDocumentController {

    private final ProductServiceMongoImpl mongoService;

    @Autowired
    public ProductDocumentController(ProductServiceMongoImpl mongoService) {
        this.mongoService = mongoService;
    }


    @GetMapping
    public ResponseEntity<List<ProductDocument>> listAll() {
        return ResponseEntity.ok(mongoService.listAll());
    }


    @GetMapping("/{id}")
    public ResponseEntity<ProductDocument> getById(@PathVariable String id) {
        ProductDocument product = mongoService.getById(id);
        if (product == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(product);
    }


    @PostMapping
    public ResponseEntity<ProductDocument> create(@Valid @RequestBody ProductDocument product) {
        ProductDocument saved = mongoService.saveOrUpdate(product);
        URI location = URI.create("/api/products/" + saved.getId());
        log.info("Created new product with id: {}", saved.getId());
        return ResponseEntity.created(location).body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductDocument> update(@PathVariable String id, @Valid @RequestBody ProductDocument product) {
        if (mongoService.getById(id) == null)
            return ResponseEntity.notFound().build();

        product.setId(new ObjectId(id));
        ProductDocument updated = mongoService.saveOrUpdate(product);
        log.info("Updated product with id: {}", updated.getId());
        return ResponseEntity.ok(updated);
    }



    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable String id) {
        ProductDocument existing  = mongoService.getById(id);
        if (existing == null) return ResponseEntity.notFound().build();
        mongoService.delete(id);
        log.info("Deleted product with id: {}", id);
        return ResponseEntity.noContent().build();
    }


}
