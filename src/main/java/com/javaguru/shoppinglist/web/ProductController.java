package com.javaguru.shoppinglist.web;

import com.javaguru.shoppinglist.dto.ProductDTO;
import com.javaguru.shoppinglist.entity.Product;
import com.javaguru.shoppinglist.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

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
        Product product = new Product();
        product.setName(productDTO.getName());
        product.setCategory(productDTO.getCategory());
        product.setPrice(productDTO.getPrice());
        product.setDiscount(productDTO.getDiscount());
        product.setDescription(productDTO.getDescription());
        productService.createProduct(product);
        return "redirect:/product";
    }

    @RequestMapping("/products")
    public String showAllProducts(Model model) {
        List<Product> products = productService.getAllProducts();
        List<ProductDTO> productDTOS = new ArrayList<>();
        products.forEach(product -> {
            ProductDTO productDTO = new ProductDTO();
            productDTO.setId(product.getId());
            productDTO.setName(product.getName());
            productDTO.setCategory(product.getCategory());
            productDTO.setPrice(product.getPrice());
            productDTO.setDiscount(product.getDiscount());
            productDTO.setDescription(product.getDescription());
            productDTOS.add(productDTO);
        });
        model.addAttribute("products", productDTOS);
        return "products";
    }

    @RequestMapping(value = "delete_product/{id}", method = RequestMethod.GET)
    public String deleteProduct(@PathVariable Long id){
        productService.deleteProductById(id);
        return "redirect:/products";
    }

/*    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView getStartingPage() {
        modelAndView = new ModelAndView();
        modelAndView.setViewName("products");
        return modelAndView;
    }

    @RequestMapping("getProductById")
    public ModelAndView getProductName(@RequestParam("id") Long id) {
        modelAndView = new ModelAndView();
        modelAndView.setViewName("products");
        Product product = productService.findById(id);
        modelAndView.addObject("name", product.getName());
        modelAndView.addObject("category", product.getCategory());
        modelAndView.addObject("price", product.getPrice());
        modelAndView.addObject("discount", product.getDiscount());
        modelAndView.addObject("description", product.getDescription());
        return modelAndView;
    }*/
}
