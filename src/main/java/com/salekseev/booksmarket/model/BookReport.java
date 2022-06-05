package com.salekseev.booksmarket.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BookReport {

    private Long id;
    private String title;
    private Genre genre;
    private Double cost;
    private Integer countSold;
    private Double totalCostSold;

}
