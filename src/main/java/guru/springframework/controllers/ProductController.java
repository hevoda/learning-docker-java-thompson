package guru.springframework.controllers;

import guru.springframework.commands.ProductForm;
import guru.springframework.converters.ProductToProductForm;
import guru.springframework.domain.ProductEntity;
import guru.springframework.services.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@Slf4j
@Profile({"h2", "dev"})
public class ProductController {

    private final ProductService productService;
    private final ProductToProductForm productToProductForm;

    @Autowired
    public ProductController(ProductService productService, ProductToProductForm productToProductForm) {
        this.productService = productService;
        this.productToProductForm = productToProductForm;
    }

    // ------------------- Pages -------------------

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/")
    public String redirectToList() {
        return "redirect:/product/list";
    }

    @GetMapping({"/product/list", "/products"})
    public String listProducts(Model model) {
        model.addAttribute("products", productService.listAll());
        return "product/list";
    }

    // ------------------- Show/Edit/Create -------------------

    @GetMapping("/product/show/{id}")
    public String showProduct(@PathVariable String id, Model model, RedirectAttributes redirectAttributes) {
        ProductEntity product = getProductOrRedirect(id, redirectAttributes);
        if (product == null) return "redirect:/product/list";
        model.addAttribute("product", product);
        return "product/show";
    }

    @GetMapping("/product/edit/{id}")
    public String editProduct(@PathVariable String id, Model model, RedirectAttributes redirectAttributes) {
        ProductEntity productEntity = getProductOrRedirect(id, redirectAttributes);
        if (productEntity == null) return "redirect:/product/list";
        model.addAttribute("productForm", productToProductForm.convert(productEntity));
        return "product/productform";
    }

    @GetMapping("/product/new")
    public String newProduct(Model model) {
        model.addAttribute("productForm", new ProductForm());
        return "product/productform";
    }

    // ------------------- Save/Update -------------------

    @PostMapping("/product")
    public String saveOrUpdateProduct(
            @Valid ProductForm productForm,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes,
            Model model) {

        if (bindingResult.hasErrors()) {
            log.warn("Validation errors while submitting product form: {}", bindingResult.getAllErrors());
            model.addAttribute("productForm", productForm);
            return "product/productform";
        }

        ProductEntity savedProduct = productService.saveOrUpdateProductForm(productForm);
        log.info("Saved product with id: {}", savedProduct.getId());
        redirectAttributes.addFlashAttribute("successMessage", "Product saved successfully!");
        return "redirect:/product/show/" + savedProduct.getId();
    }

    // ------------------- Delete -------------------

    @DeleteMapping("/product/delete/{id}")
    public String deleteProduct(@PathVariable String id, RedirectAttributes redirectAttributes) {
        ProductEntity product = getProductOrRedirect(id, redirectAttributes);
        if (product == null) return "redirect:/product/list";

        productService.delete(id);
        log.info("Deleted product with id: {}", id);
        redirectAttributes.addFlashAttribute("successMessage", "Product deleted successfully!");
        return "redirect:/product/list";
    }

    // ------------------- Helper Methods -------------------

    /**
     * Helper to fetch product by ID or add an error message and return null if not found.
     */
    private ProductEntity getProductOrRedirect(String id, RedirectAttributes redirectAttributes) {
        ProductEntity product = productService.getById(id);
        if (product == null) {
            log.warn("Product not found for id: {}", id);
            redirectAttributes.addFlashAttribute("errorMessage", "Product not found");
        }
        return product;
    }
}
