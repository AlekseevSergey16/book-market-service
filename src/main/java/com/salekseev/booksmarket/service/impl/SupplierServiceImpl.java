package com.salekseev.booksmarket.service.impl;

import com.salekseev.booksmarket.model.Supplier;
import com.salekseev.booksmarket.repository.SupplierRepository;
import com.salekseev.booksmarket.service.SupplierService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
class SupplierServiceImpl implements SupplierService {

    private final SupplierRepository supplierRepository;

    SupplierServiceImpl(SupplierRepository supplierRepository) {
        this.supplierRepository = supplierRepository;
    }

    @Override
    public long addSupplier(Supplier supplier) {
        return supplierRepository.save(supplier);
    }

    @Override
    public List<Supplier> getAllSuppliers() {
        return supplierRepository.getAllSuppliers();
    }

}
