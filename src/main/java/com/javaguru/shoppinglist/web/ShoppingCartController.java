package com.javaguru.shoppinglist.web;

import com.javaguru.shoppinglist.dto.EntityTransformer;
import com.javaguru.shoppinglist.dto.ProductDTO;
import com.javaguru.shoppinglist.dto.ShoppingCartDTO;
import com.javaguru.shoppinglist.entity.Product;
import com.javaguru.shoppinglist.entity.ShoppingCart;
import com.javaguru.shoppinglist.service.ProductService;
import com.javaguru.shoppinglist.service.ShoppingCartService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class ShoppingCartController {
    private ShoppingCartService shoppingCartService;
    private ProductService productService;

    ShoppingCartController(ShoppingCartService shoppingCartService,
                           ProductService productService){
        this.shoppingCartService = shoppingCartService;
        this.productService = productService;
    }

    @RequestMapping("/shopping_cart")
    public String showShoppingCartForm(){
        return "shopping_cart";
    }

    @RequestMapping("/shopping_carts")
    public String showAllCarts(Model model){
        List<ShoppingCart> shoppingCartList = shoppingCartService.getAll();
        List<ShoppingCartDTO> shoppingCartDTOList = shoppingCartList.stream()
                .map(EntityTransformer::transformToDTO)
                .collect(Collectors.toList());
        model.addAttribute("carts", shoppingCartDTOList);
        return "shopping_carts";
    }

    @RequestMapping(value = "/save_cart", method = RequestMethod.POST)
    public String saveCart(@ModelAttribute ShoppingCartDTO shoppingCartDTO){
        shoppingCartService.createShoppingCart(EntityTransformer.transformFromDTO(shoppingCartDTO));
        return "redirect:/shopping_carts";
    }

    @RequestMapping(value = "/delete_cart/{id}", method = RequestMethod.GET)
    public String deleteCart(@PathVariable Long id){
        shoppingCartService.delete(id);
        return "redirect:/shopping_carts";
    }

    @RequestMapping(value = "/manage_cart/{id}", method = RequestMethod.GET)
    public String manageCart(Model model, @PathVariable Long id){
        ShoppingCart shoppingCart = shoppingCartService.findById(id);
        ShoppingCartDTO shoppingCartDTO = EntityTransformer.transformToDTO(shoppingCart);
        List<ProductDTO> productDTOList = EntityTransformer.transformProductListToDTO(productService.getAllProducts());
        model.addAttribute("cart", shoppingCartDTO);
        model.addAttribute("products", productDTOList);
        model.addAttribute("total_price", String.format("%.2f", shoppingCart.getPriceTotal().orElse(new BigDecimal("0.00"))));
        return "manage_cart";
    }

    @RequestMapping(value = "shopping_cart/find_byID", method = RequestMethod.GET)
    public String findById(Model model, @RequestParam("id") Long id){
        ShoppingCartDTO shoppingCartDTO = EntityTransformer.transformToDTO(shoppingCartService.findById(id));
        return "redirect:/manage_cart/" + shoppingCartDTO.getId();
    }

    @RequestMapping(value = "shopping_cart/find_byName", method = RequestMethod.GET)
    public String findById(Model model, @RequestParam("name") String name){
        ShoppingCartDTO shoppingCartDTO = EntityTransformer.transformToDTO(shoppingCartService.findByName(name));
        return "redirect:/manage_cart/" + shoppingCartDTO.getId();
    }

    @RequestMapping(value = "/remove_product/{cartId}/{productId}", method = RequestMethod.GET)
    public String removeProductFromCart(@PathVariable Long cartId, @PathVariable Long productId){
        ShoppingCart shoppingCart = shoppingCartService.findById(cartId);
        Product product = productService.findById(productId);
        shoppingCartService.removeProductFromShoppingCart(shoppingCart, product);
        shoppingCartService.saveChanges(shoppingCart);
        return "redirect:/manage_cart/" + cartId;
    }

    @RequestMapping(value = "/add_product/{cartID}/{productID}", method = RequestMethod.GET)
    public String addProductToCart(@PathVariable Long cartID, @PathVariable Long productID){
        ShoppingCart shoppingCart = shoppingCartService.findById(cartID);
        Product product = productService.findById(productID);
        shoppingCartService.addProduct(shoppingCart, product);
        shoppingCartService.saveChanges(shoppingCart);
        return "redirect:/manage_cart/" + cartID;
    }
}
