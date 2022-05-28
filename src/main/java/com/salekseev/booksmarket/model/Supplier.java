package com.salekseev.booksmarket.model;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Supplier {

    private Long id;
    private String name;

}
