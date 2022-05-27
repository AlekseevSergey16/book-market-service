package com.salekseev.booksmarket.service.impl;

import com.salekseev.booksmarket.model.Genre;
import com.salekseev.booksmarket.repository.GenreRepository;
import com.salekseev.booksmarket.service.GenreService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
class GenreServiceImpl implements GenreService {

    private final GenreRepository genreRepository;

    GenreServiceImpl(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    @Override
    public List<Genre> getAllGenres() {
        return genreRepository.findAllGenres();
    }

}
