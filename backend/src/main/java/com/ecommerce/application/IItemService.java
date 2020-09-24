package com.ecommerce.application;

import com.ecommerce.domain.Item;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;

public interface IItemService {
	List<Item> list();

	List<Item> pageList(int page);

	List<Item> getByUser(int uid);

	List<Item> getByName(String title, int page);

	List<Item> getByCategory(String Category, int page);

	List<Item> getByMainCategory(String category);

	Item get(long id);

	@Transactional
	Item register(Item item) throws IOException, Exception;

	@Transactional
	int viewCountUpdate(long id);

	@Transactional
	Item update(Item item);

	@Transactional
	int imageUpdate(long id, int image);

	@Transactional
	int delete(long id);

	@Transactional
	int complete(long id, String eoa, String pk) throws Exception;
}