package com.javaguru.shoppinglist.web;

import com.javaguru.shoppinglist.dto.EntityTransformer;
import com.javaguru.shoppinglist.dto.ProductDTO;
import com.javaguru.shoppinglist.entity.Product;
import com.javaguru.shoppinglist.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ProductController {
    private ProductService productService;

    @Autowired
    ProductController(ProductService productService) {
        this.productService = productService;
    }

    @RequestMapping("/product")
    public String showProductMenu(){
        return "product";
    }

    @RequestMapping("/add_product")
    public String showAddProductForm(Model model) {
        model.addAttribute("command", new ProductDTO());
        return "add_product";
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String saveProduct(@ModelAttribute("product") ProductDTO productDTO){
        productService.createProduct(EntityTransformer.transformFromDTO(productDTO));
        return "redirect:/product";
    }

    @RequestMapping("/products")
    public String showAllProducts(Model model) {
        List<Product> products = productService.getAllProducts();
        List<ProductDTO> productDTOS = new ArrayList<>();
        products.forEach(product -> {
            productDTOS.add(EntityTransformer.transformToDTO(product));
        });
        model.addAttribute("products", productDTOS);
        return "products";
    }

    @RequestMapping(value = "delete_product/{id}", method = RequestMethod.GET)
    public String deleteProduct(@PathVariable Long id){
        productService.deleteProductById(id);
        return "redirect:/products";
    }

    @RequestMapping("/find_product")
    public String showFindForm(){
        return "find_product";
    }

    @RequestMapping(value = "/find_by_id", method = RequestMethod.GET)
    public String findById(Model model ,@RequestParam("id") Long id){
        Product product = productService.findById(id);
        List<ProductDTO> productDTOList = new ArrayList<>();
        productDTOList.add(EntityTransformer.transformToDTO(product));
        model.addAttribute("products", productDTOList);
        return "products";
    }

    @RequestMapping(value = "/find_by_name", method = RequestMethod.GET)
    public String findByName(Model model, @RequestParam("name") String name){
        Product product = productService.findByName(name);
        List<ProductDTO> productDTOList = new ArrayList<>();
        productDTOList.add(EntityTransformer.transformToDTO(product));
        model.addAttribute("products", productDTOList);
        return "products";
    }
}
