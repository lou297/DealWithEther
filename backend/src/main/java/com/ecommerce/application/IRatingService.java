package com.ecommerce.application;

import com.ecommerce.domain.Item;
import com.ecommerce.domain.Rating;

import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface IRatingService {
	List<Rating> list();

	double get(long userId);

	@Transactional
	long register(Rating rating);

	@Transactional
	int delete(long id);
}