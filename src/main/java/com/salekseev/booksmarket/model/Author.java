package com.salekseev.booksmarket.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Author {

    private Long id;
    private String firstName;
    private String lastName;
    private String middleName;
    private String information;

}
