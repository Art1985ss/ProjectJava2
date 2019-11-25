package com.javaguru.shoppinglist.dto;


import java.util.ArrayList;
import java.util.List;

public class ShoppingCartDTO {
    private Long id;
    private String name;
    private List<ProductDTO> productDTOList = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ProductDTO> getProductDTOList() {
        return productDTOList;
    }

    public void setProductDTOList(List<ProductDTO> productDTOList) {
        this.productDTOList = productDTOList;
    }
}
