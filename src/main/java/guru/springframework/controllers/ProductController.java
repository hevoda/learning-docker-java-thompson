package guru.springframework.controllers;

import guru.springframework.commands.ProductForm;
import guru.springframework.converters.ProductToProductForm;
import guru.springframework.domain.ProductJPA;
import guru.springframework.services.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

/**
 * Created by jt on 1/10/17.
 */
@Controller
@Slf4j
public class ProductController {


    private final ProductService productService;

    private final ProductToProductForm productToProductForm;

    @Autowired
    public ProductController(ProductService productService, ProductToProductForm productToProductForm) {
        this.productService = productService;
        this.productToProductForm = productToProductForm;
    }
    @GetMapping(value = "/login")
    public String login(){
        return "login";
    }

    @GetMapping("/")
    public String redirToList(){
        return "redirect:/product/list";
    }

    @GetMapping({"/product/list", "/product"})
    public String listProducts(Model model){
        model.addAttribute("products", productService.listAll());
        return "product/list";
    }

    @GetMapping("/product/show/{id}")
    public String getProduct(@PathVariable String id, Model model){
        model.addAttribute("product", productService.getById(id));
        return "product/show";
    }

    @GetMapping ("/product/edit/{id}")
    public String edit(@PathVariable String id, Model model){
        ProductJPA productJPA = productService.getById(id);
        ProductForm productForm = productToProductForm.convert(productJPA);

        model.addAttribute("productForm", productForm);
        return "productJPA/productform";
    }

    @GetMapping("/product/new")
    public String newProduct(Model model){
        model.addAttribute("productForm", new ProductForm());
        return "product/productform";
    }

    @PostMapping(value = "/product")
    public String saveOrUpdateProduct(@Valid ProductForm productForm, BindingResult bindingResult){

        if(bindingResult.hasErrors()){
            log.warn("Validation errors while submitting product form: {}", bindingResult.getAllErrors());
            return "product/productform";
        }

        ProductJPA savedProductJPA = productService.saveOrUpdateProductForm(productForm);
        log.info("Saved product with id: {}", savedProductJPA.getId());

        return "redirect:/product/show/" + savedProductJPA.getId();
    }

    @PostMapping("/product/delete/{id}")
    public String delete(@PathVariable String id){
        log.info("Deleting product with id: {}", id);
        productService.delete(id);
        return "redirect:/product/list";
    }
}
