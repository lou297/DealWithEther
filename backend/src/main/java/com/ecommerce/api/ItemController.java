package com.ecommerce.api;

import com.ecommerce.application.IItemService;
import com.ecommerce.domain.Item;
import com.ecommerce.domain.exception.EmptyListException;
import com.ecommerce.domain.exception.NotFoundException;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
public class ItemController
{
	public static final Logger logger = LoggerFactory.getLogger(ItemController.class);

	private IItemService itemService;

	@Autowired
	public ItemController(IItemService itemService) {
		Assert.notNull(itemService, "itemService 개체가 반드시 필요!");
		this.itemService = itemService;
	}

	/**
	 * TODO Sub PJT Ⅲ 과제 3
	 * 상품 등록
	 * 
	 * @param item
	 * @return Item
	 */
	@ApiOperation(value = "Register an item")
	@RequestMapping(value = "/items", method = RequestMethod.POST)
	public Item register(@RequestBody Item item) {
		return itemService.register(item);
	}

	@ApiOperation(value = "Fetch all items")
	@RequestMapping(value = "/items", method = RequestMethod.GET)
	public List<Item> list() {
		List<Item> list = itemService.list();

		if (list == null || list.isEmpty())
			throw new EmptyListException("NO DATA");

		return list;
	}

	@ApiOperation(value = "Fetch an item with id")
	@RequestMapping(value = "/items/{id}", method = RequestMethod.GET)
	public Item get(@PathVariable int id) {
		Item item = itemService.get(id);
		if (item == null) {
			logger.error("NOT FOUND ID: ", id);
			throw new NotFoundException(id + " 상품 정보를 찾을 수 없습니다.");
		}
		return item;
	}

	@ApiOperation(value = "Fetch an item with id")
	@RequestMapping(value = "/items/seller/{id}", method = RequestMethod.GET)
	public List<Item> getSellerItem(@PathVariable int id) {
		List<Item> item = itemService.getByUser(id);
		if (item == null) {
			logger.error("NOT FOUND ID: ", id);
			throw new NotFoundException(id + " 상품 정보를 찾을 수 없습니다.");
		}
		return item;
	}

	@ApiOperation(value = "Fetch an item with name")
	@RequestMapping(value = "/items/name/{name}", method = RequestMethod.GET)
	public List<Item> getByUser(@PathVariable String name) {
		List<Item> items = itemService.getByName(name);
		if (items == null || items.size() == 0) {
			logger.error("NOT FOUND LIST OF NAME: ", name);
			return null;
		}
		return items;
	}

	@ApiOperation(value = "Fetch an item with category")
	@RequestMapping(value = "/items/category/{category}", method = RequestMethod.GET)
	public List<Item> getByCategory(@PathVariable String category) {
		List<Item> items = itemService.getByCategory(category);
		if (items == null || items.size() == 0) {
			logger.error("NOT FOUND LIST OF CATEGORY: ", category);
			return null;
		}
		return items;
	}

	@ApiOperation(value = "Fetch an item with id")
	@RequestMapping(value = "/items/of/{uid}", method = RequestMethod.GET)
	public List<Item> getByUser(@PathVariable int uid) {
		List<Item> items = itemService.getByUser(uid);
		if (items == null || items.size() == 0) {
			logger.error("NOT FOUND LIST OF UID: ", uid);
			return null;
		}
		return items;
	}

	/**
	 * TODO Sub PJT Ⅲ 과제 3 상품 판매 취소
	 * 
	 * @param id 아이템 id
	 * @return Item
	 */
	@ApiOperation(value = "Delete an item with id")
	@RequestMapping(value = "/items/{id}", method = RequestMethod.DELETE)
	public int delete(@PathVariable int id) {
		return itemService.delete(id);
	}

	@ApiOperation(value = "Update an item")
	@RequestMapping(value = "/items", method = RequestMethod.PUT)
	public Item update(@RequestBody Item item) {
		return itemService.update(item);
	}
}
