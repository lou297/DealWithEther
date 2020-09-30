package com.ecommerce.application;

import com.ecommerce.domain.Item;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;

public interface IItemService {
	String deploy() throws Exception;

	List<Item> list();

	List<Item> pageList(int page);

	List<Item> getByUser(int uid);

	List<Item> getByUser(String category, String name, int page);

	List<Item> getByName(String category, String title, int page);

	List<Item> getByCategory(String Category, int page);

	List<Item> getByMainCategory(String category);

	Item get(long id) throws IOException, Exception;

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

	@Transactional
	int changeProgressTrue(long id);

	@Transactional
	int changeProgressFalse(long id);

	List<Item> getByOnlyName(String name, int page);
}