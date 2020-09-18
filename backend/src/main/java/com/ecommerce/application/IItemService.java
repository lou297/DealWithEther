package com.ecommerce.application;

import com.ecommerce.domain.Item;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface IItemService {
	List<Item> list();

	List<Item> pageList(int page);

	List<Item> getByUser(int uid);

	List<Item> getByName(String title, int page);

	List<Item> getByCategory(String Category, int page);

	Item get(long id);

	@Transactional
	Item register(Item item);

	@Transactional
	Item update(Item item);

	@Transactional
	int delete(long id);
}