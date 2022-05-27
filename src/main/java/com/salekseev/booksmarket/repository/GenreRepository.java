package com.salekseev.booksmarket.repository;

import com.salekseev.booksmarket.model.Genre;

import java.util.List;

public interface GenreRepository {

    List<Genre> findAllGenres();

}
