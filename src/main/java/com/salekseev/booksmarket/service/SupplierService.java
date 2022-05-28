package com.salekseev.booksmarket.service;

import com.salekseev.booksmarket.model.Supplier;

import java.util.List;

public interface SupplierService {

    long addSupplier(Supplier supplier);

    List<Supplier> getAllSuppliers();

}
