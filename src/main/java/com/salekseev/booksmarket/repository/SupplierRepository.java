package com.salekseev.booksmarket.repository;

import com.salekseev.booksmarket.model.Supplier;

import java.util.List;

public interface SupplierRepository {

    long save(Supplier supplier);

    List<Supplier> getAllSuppliers();

}
