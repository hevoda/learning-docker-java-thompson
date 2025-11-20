package guru.springframework.controllers;

import guru.springframework.commands.ProductForm;
import guru.springframework.converters.ProductToProductForm;
import guru.springframework.domain.ProductEntity;
import guru.springframework.services.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class ProductControllerTest {

    @Mock
    private ProductService productService;

    @Mock
    private ProductToProductForm productToProductForm;

    @InjectMocks
    private ProductController productController;

    private MockMvc mockMvc;

    @BeforeEach
    void setup() {
        LocalValidatorFactoryBean validator = new LocalValidatorFactoryBean();
        validator.afterPropertiesSet();

        mockMvc = MockMvcBuilders
                .standaloneSetup(productController)
                .setValidator(validator)
                .build();
    }

    // ==============================
    // LIST
    // ==============================
    @Test
    @DisplayName("GET /products/list → mostra lista prodotti")
    void listProducts_ShouldReturnProductList() throws Exception {
        ProductEntity p = new ProductEntity();
        p.setId(1L);
        p.setDescription("Prodotto");
        p.setPrice(BigDecimal.TEN);

        Mockito.when(productService.listAll()).thenReturn(List.of(p));

        mockMvc.perform(get("/products/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("product/list"))
                .andExpect(model().attributeExists("products"));
    }

    // ==============================
    // SHOW
    // ==============================
    @Test
    @DisplayName("GET /products/show/{id} → prodotto trovato")
    void showProduct_ShouldReturnProduct() throws Exception {
        ProductEntity product = new ProductEntity();
        product.setId(1L);
        product.setDescription("Prodotto Test");

        Mockito.when(productService.getById(1L)).thenReturn(product);

        mockMvc.perform(get("/products/show/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("product/show"))
                .andExpect(model().attributeExists("product"));
    }

    @Test
    @DisplayName("GET /products/show/{id} → prodotto NON trovato")
    void showProduct_NotFound_ShouldRedirect() throws Exception {
        Mockito.when(productService.getById(99L)).thenReturn(null);

        mockMvc.perform(get("/products/show/99"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/products/list"))
                .andExpect(flash().attributeExists("errorMessage"));
    }

    // ==============================
    // NEW FORM
    // ==============================
    @Test
    @DisplayName("GET /products/new → mostra form vuoto")
    void newProduct_ShouldReturnEmptyForm() throws Exception {
        mockMvc.perform(get("/products/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("product/productform"))
                .andExpect(model().attributeExists("productForm"));
    }

    // ==============================
    // EDIT
    // ==============================
    @Test
    @DisplayName("GET /products/edit/{id} → prodotto trovato")
    void editProduct_ShouldReturnPopulatedForm() throws Exception {
        ProductEntity product = new ProductEntity();
        product.setId(10L);
        product.setDescription("Old Desc");

        ProductForm form = new ProductForm();
        form.setId(10L);
        form.setDescription("Old Desc");

        Mockito.when(productService.getById(10L)).thenReturn(product);
        Mockito.when(productToProductForm.convert(product)).thenReturn(form);

        mockMvc.perform(get("/products/edit/10"))
                .andExpect(status().isOk())
                .andExpect(view().name("product/productform"))
                .andExpect(model().attributeExists("productForm"));
    }

    @Test
    @DisplayName("GET /products/edit/{id} → prodotto NON trovato")
    void editProduct_NotFound_ShouldRedirect() throws Exception {
        Mockito.when(productService.getById(88L)).thenReturn(null);

        mockMvc.perform(get("/products/edit/88"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/products/list"))
                .andExpect(flash().attributeExists("errorMessage"));
    }

    // ==============================
    // SAVE
    // ==============================
    @Test
    @DisplayName("POST /products → form valido, salva e redirect a /show/{id}")
    void saveProduct_Valid_ShouldRedirect() throws Exception {
        // Mock di salvataggio: ritorna un prodotto con ID
        ProductEntity savedProduct = new ProductEntity();
        savedProduct.setId(1L);
        savedProduct.setDescription("Prodotto valido");

        Mockito.when(productService.saveOrUpdateProductForm(Mockito.any(ProductForm.class)))
                .thenReturn(savedProduct);

        mockMvc.perform(post("/products")
                        .param("description", "Prodotto valido")
                        .param("price", "12.99"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/products/show/1"));
    }

    @Test
    @DisplayName("POST /products → form NON valido, torna al form")
    void saveProduct_Invalid_ShouldReturnForm() throws Exception {

        // Quando il servizio salva il prodotto, ritorna null o non viene chiamato per form non validi
        // Qui non serve fare nulla, il controller ritornerà al form perché ci sono errori di validazione

        mockMvc.perform(post("/products")
                        .param("description", "")) // campo obbligatorio mancante
                .andExpect(status().isOk())
                .andExpect(view().name("product/productform"))
                .andExpect(model().attributeExists("productForm"));
    }

    // ==============================
    // DELETE
    // ==============================
    @Test
    @DisplayName("GET /products/delete/{id} → prodotto esistente, elimina")
    void deleteProduct_ShouldDeleteAndRedirect() throws Exception {
        ProductEntity p = new ProductEntity();
        p.setId(4L);

        Mockito.when(productService.getById(4L)).thenReturn(p);

        mockMvc.perform(get("/products/delete/4"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/products/list"))
                .andExpect(flash().attributeExists("successMessage"));

        Mockito.verify(productService).delete(4L);
    }

    @Test
    @DisplayName("GET /products/delete/{id} → prodotto NON trovato")
    void deleteProduct_NotFound_ShouldRedirect() throws Exception {
        Mockito.when(productService.getById(123L)).thenReturn(null);

        mockMvc.perform(get("/products/delete/123"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/products/list"))
                .andExpect(flash().attributeExists("errorMessage"));
    }

    // ==============================
    // ROOT & LOGIN
    // ==============================
    @Test
    @DisplayName("GET /products/ → redirect a list")
    void root_ShouldRedirectToList() throws Exception {
        mockMvc.perform(get("/products/"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/products/list"));
    }

    @Test
    @DisplayName("GET /products/login → mostra pagina login")
    void login_ShouldReturnLoginView() throws Exception {
        mockMvc.perform(get("/products/login"))
                .andExpect(status().isOk())
                .andExpect(view().name("product/login"));
    }
}
