package com.salekseev.booksmarket.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
@Builder
public class Book {

    private Long id;
    private String title;
    private String description;
    private List<Author> authors;
    private Publisher publisher;
    private Genre genre;
    private Integer publicationYear;
    private Double cost;
    private Integer pages;
    private Integer weight;

}
