package com.ecommerce.domain.repository;

import com.ecommerce.domain.Rating;

import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface IRatingRepository {
	List<Rating> list();

	@Transactional
	long create(Rating rating);

	@Transactional
	int delete(long id);
}
