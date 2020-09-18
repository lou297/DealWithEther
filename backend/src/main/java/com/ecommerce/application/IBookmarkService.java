package com.ecommerce.application;

import com.ecommerce.domain.Bookmark;
import com.ecommerce.domain.Item;
import com.ecommerce.domain.Rating;

import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface IBookmarkService {
	List<Bookmark> list();

	@Transactional
	long register(Bookmark bookmark);

	@Transactional
	int delete(long id);
}