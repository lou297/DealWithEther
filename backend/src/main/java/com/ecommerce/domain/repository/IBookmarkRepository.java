package com.ecommerce.domain.repository;

import com.ecommerce.domain.Bookmark;

import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface IBookmarkRepository {
	List<Bookmark> list();

	@Transactional
	long create(Bookmark bookmark);

	@Transactional
	int delete(long id);
}
