package com.ecommerce.application.impl;

import com.ecommerce.application.IItemService;
import com.ecommerce.application.IRatingService;
import com.ecommerce.domain.Item;
import com.ecommerce.domain.Rating;
import com.ecommerce.domain.exception.ApplicationException;
import com.ecommerce.domain.repository.IItemRepository;
import com.ecommerce.domain.repository.IRatingRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class RatingService implements IRatingService {
	public static final Logger logger = LoggerFactory.getLogger(RatingService.class);

	private IRatingRepository ratingRepository;

	@Autowired
	public RatingService(IRatingRepository ratingRepository) {
		this.ratingRepository = ratingRepository;
	}

	@Override
	public List<Rating> list() {
		return this.ratingRepository.list();
	}

	@Override
	public long register(final Rating rating) {
		return this.ratingRepository.create(rating);
	}

	@Override
	public int delete(final long id) {
		return this.ratingRepository.delete(id);
	}
}
