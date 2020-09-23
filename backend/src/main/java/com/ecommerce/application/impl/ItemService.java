package com.ecommerce.application.impl;

import com.ecommerce.application.IItemService;
import com.ecommerce.domain.Item;
import com.ecommerce.domain.exception.ApplicationException;
import com.ecommerce.domain.repository.IItemRepository;
import com.ecommerce.domain.wrapper.EscrowFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.tx.gas.DefaultGasProvider;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ItemService implements IItemService {
	public static final Logger logger = LoggerFactory.getLogger(ItemService.class);
	@Value("${eth.erc20.contract}")
	private String ERC20_TOKEN_CONTRACT;

	@Value("${eth.admin.address}")
	private String ADMIN_ADDRESS;

	@Value("${eth.admin.wallet.filename}")
	private String WALLET_RESOURCE;

	@Value("${eth.encrypted.password}")
	private String PASSWORD;

	private IItemRepository itemRepository;
	private EscrowFactory escrowFactory;
	private ContractGasProvider contractGasProvider = new DefaultGasProvider();
	private Credentials credentials;

	@Autowired
	private Web3j web3j;

	@Autowired
	public ItemService(IItemRepository itemRepository) {
		this.itemRepository = itemRepository;
	}

	@Override
	public List<Item> list() {
		return this.itemRepository.list();
	}

	@Override
	public List<Item> pageList(int page) {
		return this.itemRepository.pageList(page);
	}

	@Override
	public Item get(final long id) {
		return this.itemRepository.get(id);
	}

	@Override
	public List<Item> getByUser(int uid) {
		return this.itemRepository.getByUser(uid);
	}

	@Override
	public List<Item> getByName(String title, int page) {
		return this.itemRepository.getByName(title, page);
	}

	@Override
	public List<Item> getByCategory(String category, int page) {
		return this.itemRepository.getByCategory(category, page);
	}

	/**
	 * TODO Sub PJT Ⅲ 과제 3 상품 등록 시 상품 정보를 저장한다.
	 * 
	 * @param item
	 * @return Item
	 * @throws Exception
	 */
	@Override
	public Item register(final Item item) throws Exception {
		long id = this.itemRepository.create(item);

		Web3j web3 = Web3j.build(new HttpService());

		credentials = Credentials.create(item.getPk());

		String contract = "0xcffB8d211935ec7d1281d391b946259d85cde3AD";

		escrowFactory = EscrowFactory.load(contract, web3, credentials, contractGasProvider);

		TransactionReceipt tr = escrowFactory.registerItem(BigInteger.valueOf(id), BigInteger.valueOf(item.getPrice()))
				.send();

		return this.itemRepository.get(id);
	}

	/**
	 * TODO Sub PJT Ⅲ 과제 3 상품 판매 취소
	 * 
	 * @param id 상품 id
	 * @return Item
	 */
	@Override
	public int delete(final long id) {
		return this.itemRepository.delete(id);
	}

	@Override
	public int viewCountUpdate(final long id) {
		return this.itemRepository.viewCountUpdate(id);
	}

	/**
	 * 상품 정보 업데이트
	 * 
	 * @param item
	 * @return
	 */
	public Item update(final Item item) {
		Item itemStored = this.itemRepository.get(item.getId());
		if (itemStored == null)
			throw new ApplicationException("해당 상품을 찾을 수 없습니다.");

		if (item.getSeller() == 0 || item.getSeller() != itemStored.getSeller())
			throw new ApplicationException("잘못된 접근입니다.");

		if (item.getName() == null || "".equals(item.getName()))
			item.setName(itemStored.getName());
		if (item.getExplanation() == null || "".equals(item.getExplanation()))
			item.setExplanation(itemStored.getExplanation());
		if (item.getAvailable() == null || "".equals(item.getAvailable()))
			item.setAvailable(itemStored.getAvailable());
		if (item.getImage() == 0 || "".equals(item.getImage()))
			item.setImage(itemStored.getImage());
		if (item.getPrice() == 0)
			item.setPrice(itemStored.getPrice());

		int affected = this.itemRepository.update(item);
		if (affected == 0)
			throw new ApplicationException("상품정보수정 처리가 반영되지 않았습니다.");

		return this.itemRepository.get(item.getId());
	}

	@Override
	public int imageUpdate(long id, int image) {
		return this.itemRepository.imageUpdate(id, image);
	}

	@Override
	public int complete(long id, String eoa, String pk) throws Exception {
		Web3j web3 = Web3j.build(new HttpService());
		System.out.println(pk);
		credentials = Credentials.create(pk);

		String contract = "0xcffB8d211935ec7d1281d391b946259d85cde3AD";

		escrowFactory = EscrowFactory.load(contract, web3, credentials, contractGasProvider);

		TransactionReceipt tr = escrowFactory.deregisterItem(BigInteger.valueOf(id)).send();
		// return this.itemRepository.complete(id);
		return 0;
	}

}
