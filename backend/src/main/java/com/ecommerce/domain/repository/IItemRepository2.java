package com.ecommerce.domain.repository;

import com.ecommerce.domain.Item;
import com.ecommerce.domain.ItemJpa;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IItemRepository2 extends JpaRepository<ItemJpa, Long> {
    Page<ItemJpa> getByNameContaining(Pageable pageable, String name);

    Page<ItemJpa> getBySeller(Pageable pageable, long name);

    Page<ItemJpa> getByNameContainingAndCategoryContaining(Pageable pageable, String name, String category);

    Page<ItemJpa> getBySellerAndCategoryContaining(Pageable pageable, long seller, String category);
}
