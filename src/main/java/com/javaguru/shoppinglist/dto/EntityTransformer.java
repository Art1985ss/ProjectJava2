package com.javaguru.shoppinglist.dto;

import com.javaguru.shoppinglist.entity.Product;
import com.javaguru.shoppinglist.entity.ShoppingCart;

import java.util.List;
import java.util.stream.Collectors;

public class EntityTransformer {
    public static ProductDTO transformToDTO(Product product) {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(product.getId());
        productDTO.setName(product.getName());
        productDTO.setCategory(product.getCategory());
        productDTO.setPrice(product.getPrice());
        productDTO.setDiscount(product.getDiscount());
        productDTO.setDescription(product.getDescription());
        return productDTO;
    }

    public static Product transformFromDTO(ProductDTO productDTO) {
        Product product = new Product();
        product.setName(productDTO.getName());
        product.setCategory(productDTO.getCategory());
        product.setPrice(productDTO.getPrice());
        product.setDiscount(productDTO.getDiscount());
        product.setDescription(productDTO.getDescription());
        return product;
    }

    public static ShoppingCartDTO transformToDTO(ShoppingCart shoppingCart) {
        ShoppingCartDTO shoppingCartDTO = new ShoppingCartDTO();
        shoppingCartDTO.setId(shoppingCart.getId());
        shoppingCartDTO.setName(shoppingCart.getName());
        shoppingCartDTO.setProductDTOList(transformProductListToDTO(shoppingCart.getProductList()));
        return shoppingCartDTO;
    }

    public static List<ProductDTO> transformProductListToDTO(List<Product> productList) {
        return productList.stream()
                .map(EntityTransformer::transformToDTO)
                .collect(Collectors.toList());
    }

    public static ShoppingCart transformFromDTO(ShoppingCartDTO shoppingCartDTO) {
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setName(shoppingCartDTO.getName());
        shoppingCartDTO.getProductDTOList()
                .forEach(productDTO -> shoppingCart.addProduct(transformFromDTO(productDTO)));
        return shoppingCart;
    }
}
