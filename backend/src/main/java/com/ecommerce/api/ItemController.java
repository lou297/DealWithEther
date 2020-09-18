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
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
public class ItemController {
	public static final Logger logger = LoggerFactory.getLogger(ItemController.class);

	private IItemService itemService;

	@Autowired
	public ItemController(IItemService itemService) {
		Assert.notNull(itemService, "itemService 개체가 반드시 필요!");
		this.itemService = itemService;
	}

	/**
	 * TODO Sub PJT Ⅲ 과제 3 상품 등록
	 * 
	 * @param item
	 * @return Item
	 */
	@ApiOperation(value = "Register an item")
	@RequestMapping(value = "/items", method = RequestMethod.POST)
	public Item register(@RequestBody Item item) {
		System.out.println(item.toString());
		logger.info(item.toString());
		// return itemService.register(item);
		return null;
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
	public List<Item> getByName(@PathVariable String name) {
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

	@RequestMapping(value = "/items/images/{id}", method = RequestMethod.POST)
	public void uploadFile(@PathVariable long id, @RequestParam("file") MultipartFile[] file)
			throws IllegalStateException, IOException {
		System.out.println("123");
		for (int i = 0; i < file.length; i++)
			System.out.println("파일이름: " + file[i].getOriginalFilename());

		String baseDir = this.getClass().getResource("/").getPath() + "static/upload/";
		baseDir = baseDir.substring(1);

		// 리눅스를 위한 세팅
		if (baseDir.substring(0, 1).equals("C")) {
			baseDir = "C:/Users/multicampus/images/";
		} else {
			baseDir = "/home/ubuntu/dist/server/image/";
		}
		File folder = new File(baseDir);

		if (!folder.exists())
			folder.mkdirs();

		System.out.println(baseDir);

		int count = 1;
		for (MultipartFile temp : file) {
			String thisFile = Long.toString(id) + "_" + Integer.toString(count) + ".jpg";
			temp.transferTo(new File(baseDir + thisFile));
			count++;
		}
	}
}
