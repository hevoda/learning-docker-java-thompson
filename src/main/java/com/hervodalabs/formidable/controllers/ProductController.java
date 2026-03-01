package com.hervodalabs.formidable.controllers;

import com.hervodalabs.formidable.commands.ProductForm;
import com.hervodalabs.formidable.converters.ProductToProductForm;
import com.hervodalabs.formidable.domain.ProductEntity;
import com.hervodalabs.formidable.services.impl.ProductServiceJpaImpl;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.function.Function;

@Controller
@Slf4j
@Profile("mysql")
@RequestMapping("/products") // PREFISSO AGGIORNATO
public class ProductController {

    private static final String FORM_VIEW = "product/productform";
    private static final String LIST_VIEW = "product/list";
    private static final String SHOW_VIEW = "product/show";
    private static final String REDIRECT_LIST = "redirect:/product/list";

    private final ProductServiceJpaImpl productService;
    private final ProductToProductForm converter;

    @Autowired
    public ProductController(ProductServiceJpaImpl productService, ProductToProductForm converter) {
        this.productService = productService;
        this.converter = converter;
    }

    // ================================
    // HOME PAGE (Welcome Page)
    // ================================
    @GetMapping("/")
    public String home() {
        return "product/home";
    }

    // ================================
    // LOGIN
    // ================================
    @GetMapping("/login")
    public String login() {
        log.debug("Accessing login page");
        return "product/login";
    }

    // ================================
    // REDIRECT ROOT
    // ================================
    @GetMapping("/")
    public String redirectToList() {
        log.debug("Redirecting to product list");
        return REDIRECT_LIST;
    }

    // ================================
    // LIST
    // ================================
    @GetMapping("/list")
    public String list(Model model) {
        log.debug("Listing all products");
        model.addAttribute("products", productService.listAll());
        return LIST_VIEW;
    }

    // ================================
    // SHOW
    // ================================
    @GetMapping("/show/{id}")
    public String show(@PathVariable Long id, Model model, RedirectAttributes ra) {
        log.debug("Request to show product with ID: {}", id);
        return handleProductOrRedirect(id, ra, product -> {
            model.addAttribute("product", product);
            log.info("Displaying product: {}", product.getDescription());
            return SHOW_VIEW;
        });
    }

    // ================================
    // NEW
    // ================================
    @GetMapping("/new")
    public String newProduct(Model model) {
        log.debug("Creating new product form");
        model.addAttribute("productForm", new ProductForm());
        return FORM_VIEW;
    }

    // ================================
    // EDIT
    // ================================
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Long id, Model model, RedirectAttributes ra) {
        log.debug("Request to edit product with ID: {}", id);
        return handleProductOrRedirect(id, ra, product -> {
            model.addAttribute("productForm", converter.convert(product));
            log.info("Editing product: {}", product.getDescription());
            return FORM_VIEW;
        });
    }

    // ================================
    // SAVE/UPDATE
    // ================================
    @PostMapping
    public String saveOrUpdate(
            @Valid @ModelAttribute("productForm") ProductForm form,
            BindingResult result,
            RedirectAttributes ra,
            Model model) {

        if (result.hasErrors()) {
            log.warn("Validation errors: {}", result.getAllErrors());
            model.addAttribute("productForm", form);
            return FORM_VIEW;
        }

        ProductEntity saved = productService.saveOrUpdateProductForm(form);
        log.info("Saved product with ID: {}", saved.getId());
        ra.addFlashAttribute("successMessage", "Prodotto salvato con successo!");

        return "redirect:/products/show/" + saved.getId(); // aggiornato
    }

    // ================================
    // DELETE
    // ================================
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes ra) {
        log.debug("Request to delete product with ID: {}", id);
        return handleProductOrRedirect(id, ra, product -> {
            productService.delete(id);
            ra.addFlashAttribute("successMessage", "Prodotto eliminato con successo!");
            log.info("Deleted product with ID: {}", id);
            return REDIRECT_LIST;
        });
    }

    // ================================
    // HELPER
    // ================================
    private String handleProductOrRedirect(
            Long id,
            RedirectAttributes ra,
            Function<ProductEntity, String> onSuccess) {

        ProductEntity product = productService.getById(id);

        if (product == null) {
            log.warn("Product with ID {} not found", id);
            ra.addFlashAttribute("errorMessage", "Prodotto non trovato");
            return REDIRECT_LIST;
        }

        return onSuccess.apply(product);
    }
}
