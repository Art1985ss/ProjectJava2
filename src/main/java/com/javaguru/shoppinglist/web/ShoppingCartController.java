package com.javaguru.shoppinglist.web;

import com.javaguru.shoppinglist.dto.EntityTransformer;
import com.javaguru.shoppinglist.dto.ProductDTO;
import com.javaguru.shoppinglist.dto.ShoppingCartDTO;
import com.javaguru.shoppinglist.entity.Product;
import com.javaguru.shoppinglist.entity.ShoppingCart;
import com.javaguru.shoppinglist.service.ProductService;
import com.javaguru.shoppinglist.service.ShoppingCartService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/shopping_carts")
public class ShoppingCartController {
    private ShoppingCartService shoppingCartService;
    private ProductService productService;

    ShoppingCartController(ShoppingCartService shoppingCartService,
                           ProductService productService) {
        this.shoppingCartService = shoppingCartService;
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<List<ShoppingCartDTO>> getAll() {
        List<ShoppingCartDTO> shoppingCartDTOList = shoppingCartService.getAll()
                .stream().map(EntityTransformer::transformToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(shoppingCartDTOList);
    }

    @PostMapping
    public ResponseEntity<ShoppingCartDTO> createShoppingCart(@RequestBody ShoppingCartDTO shoppingCartDTO) {
        ShoppingCart shoppingCart = EntityTransformer.transformFromDTO(shoppingCartDTO);
        shoppingCartService.createShoppingCart(shoppingCart);
        return new ResponseEntity<>(shoppingCartDTO, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ShoppingCartDTO> findById(@PathVariable Long id) {
        ShoppingCartDTO shoppingCartDTO = EntityTransformer.transformToDTO(shoppingCartService.findById(id));
        return new ResponseEntity<>(shoppingCartDTO, HttpStatus.FOUND);
    }

    @GetMapping("/")
    public ResponseEntity<ShoppingCartDTO> findByName(@RequestParam String name) {
        ShoppingCartDTO shoppingCartDTO = EntityTransformer.transformToDTO(shoppingCartService.findByName(name));
        return new ResponseEntity<>(shoppingCartDTO, HttpStatus.FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteById(@PathVariable Long id) {
        shoppingCartService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("{cartId}/products/{productId}")
    public ResponseEntity<ShoppingCartDTO> addProduct(@PathVariable Long cartId, @PathVariable Long productId) {
        ShoppingCart shoppingCart = shoppingCartService.findById(cartId);
        Product product = productService.findById(productId);
        shoppingCartService.addProduct(shoppingCart, product);
        shoppingCartService.saveChanges(shoppingCart);
        return new ResponseEntity<>(EntityTransformer.transformToDTO(shoppingCart), HttpStatus.ACCEPTED);
    }

    @DeleteMapping("{cartId}/products/{productId}")
    public ResponseEntity<ShoppingCartDTO> removeProduct(@PathVariable Long cartId, @PathVariable Long productId) {
        ShoppingCart shoppingCart = shoppingCartService.findById(cartId);
        Product product = productService.findById(productId);
        shoppingCartService.removeProductFromShoppingCart(shoppingCart, product);
        shoppingCartService.saveChanges(shoppingCart);
        return new ResponseEntity<>(EntityTransformer.transformToDTO(shoppingCart), HttpStatus.ACCEPTED);
    }

    @GetMapping("/{cartId}/products")
    public ResponseEntity<List<ProductDTO>> getAllProducts(@PathVariable Long cartId) {
        List<ProductDTO> productDTOList = shoppingCartService.findById(cartId).getProductList()
                .stream().map(EntityTransformer::transformToDTO).collect(Collectors.toList());
        return new ResponseEntity<>(productDTOList, HttpStatus.FOUND);
    }
}
