package com.salekseev.booksmarket.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class Publisher {

    private Long id;

    private String name;
    private String phone;
    private String email;
    private String information;

}
