package com.ecommerce.application.impl;

import com.ecommerce.application.IEscrowContractService;
import com.ecommerce.application.IPurchaseService;
import com.ecommerce.application.IWalletService;
import com.ecommerce.domain.Cash;
import com.ecommerce.domain.Item;
import com.ecommerce.domain.Purchase;
import com.ecommerce.domain.PurchaseInfo;
import com.ecommerce.domain.Wallet;
import com.ecommerce.domain.exception.ApplicationException;
import com.ecommerce.domain.repository.IItemRepository;
import com.ecommerce.domain.repository.IPurchaseRepository;
import com.ecommerce.domain.wrapper.CashContract;
import com.ecommerce.domain.wrapper.Escrow;
import com.ecommerce.domain.wrapper.EscrowFactory;
import com.ecommerce.domain.wrapper.EscrowFactory.NewEscrowEventResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.tx.gas.DefaultGasProvider;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class PurchaseService implements IPurchaseService {

    public static final Logger logger = LoggerFactory.getLogger(ItemService.class);

    @Value("${eth.erc20.contract}")
    private String ERC20_TOKEN_CONTRACT;

    @Value("${eth.item.contract}")
    private String ITEM_CONTRACT;

    @Value("${eth.purchase.record.contract}")
    private String PURCHASE_CONTRACT_ADDRESS;

    @Value("${eth.admin.address}")
    private String ADMIN_ADDRESS;

    @Value("${eth.admin.wallet.filename}")
    private String WALLET_RESOURCE;

    @Value("${eth.encrypted.password}")
    private String PASSWORD;

    @Value("${spring.web3j.client-address}")
    private String NETWORK_URL;

    private EscrowFactory escrowFactory;
    private CashContract cashContract;
    private Escrow escrow;
    private ContractGasProvider contractGasProvider = new DefaultGasProvider();
    private Credentials credentials;

    @Autowired
    private IEscrowContractService escrowContractService;

    @Autowired
    private IWalletService walletService;

    @Autowired
    private Web3j web3j;

    private IPurchaseRepository purchaseRepository;
    private IItemRepository itemRepository;

    @Autowired
    public PurchaseService(IPurchaseRepository purchaseRepository, IItemRepository itemRepository) {
        this.purchaseRepository = purchaseRepository;
        this.itemRepository = itemRepository;
    }

    @Override
    public List<Purchase> list() {
        return this.purchaseRepository.list();
    }

    @Override
    public Purchase get(long id) {
        return this.purchaseRepository.get(id);
    }

    @Override
    public Purchase getByPurchaseId(int pid) {
        return this.purchaseRepository.getByPurchaseId(pid);
    }

    /**
     * TODO Sub PJT Ⅲ 과제 3 판매자 관련 Purchase 조회
     */
    @Override
    public List<PurchaseInfo> getBySeller(int id) {
        return null;
    }

    /**
     * TODO Sub PJT Ⅲ 과제 3 구매자 관련 Purchase 조회
     */
    @Override
    public List<PurchaseInfo> getByBuyer(int id) {
        return null;
    }

    /**
     * TODO Sub PJT Ⅲ 과제 3 Purchase 정보 등록
     * 
     * @param purchase
     * @return
     */
    @Override
    public Purchase create(final Purchase purchase) {
        return null;
    }

    /**
     * TODO Sub PJT Ⅲ 과제 3 Purchase 상태 업데이트
     * 
     * @return
     */
    @Override
    public Purchase updateState(int pid, String state) {
        return null;
    }

    @Override
    public long startPurchase(long id, Cash cash) throws Exception {
        web3j = Web3j.build(new HttpService(NETWORK_URL));

        credentials = Credentials.create(cash.getPrivateKey());

        escrowFactory = EscrowFactory.load(ITEM_CONTRACT, web3j, credentials, contractGasProvider);

        TransactionReceipt tr = escrowFactory.purchaseItem(BigInteger.valueOf(id)).send();

        List<NewEscrowEventResponse> list = escrowFactory.getNewEscrowEvents(tr);

        for (NewEscrowEventResponse neer : list) {
            if (neer.itemId.longValue() == id) {
                Purchase purchase = new Purchase();
                Wallet wallet = walletService.get(neer.buyer);
                purchase.setBuyerId(wallet.getOwnerId());
                wallet = walletService.get(neer.seller);
                purchase.setSellerId(wallet.getOwnerId());
                purchase.setContractAddress(neer._address);
                purchase.setCreatedAt(LocalDateTime.now());
                purchase.setItemId(id);
                purchase.setPurchaseId(neer.purchaseId.longValue());

                cashContract = CashContract.load(ERC20_TOKEN_CONTRACT, web3j, credentials, contractGasProvider);

                Item item = itemRepository.get(id);

                TransactionReceipt tr3 = cashContract
                        .transfer(purchase.getContractAddress(), BigInteger.valueOf(item.getPrice() + 20)).send();

                escrow = Escrow.load(purchase.getContractAddress(), web3j, credentials, contractGasProvider);

                TransactionReceipt tr2 = escrow.checkDeposit().send();
                purchase.setState("P");
                return purchaseRepository.create(purchase);
            }
        }
        return -1;

    }

    @Override
    public long send(long purchaseId, Cash cash) throws Exception {
        web3j = Web3j.build(new HttpService(NETWORK_URL));

        credentials = Credentials.create(cash.getPrivateKey());

        Purchase purchase = purchaseRepository.getByPurchaseId(purchaseId);

        escrow = Escrow.load(purchase.getContractAddress(), web3j, credentials, contractGasProvider);

        TransactionReceipt tr = escrow.send().send();
        purchase.setState("S");
        purchaseRepository.update(purchase);
        return purchaseRepository.update(purchase);
    }

    @Override
    public long confirm(long purchaseId, Cash cash) throws Exception {
        web3j = Web3j.build(new HttpService(NETWORK_URL));

        credentials = Credentials.create(cash.getPrivateKey());

        Purchase purchase = purchaseRepository.getByPurchaseId(purchaseId);

        escrow = Escrow.load(purchase.getContractAddress(), web3j, credentials, contractGasProvider);

        TransactionReceipt tr = escrow.confirm().send();
        purchase.setState("C");
        return purchaseRepository.update(purchase);
    }

    @Override
    public long cancel(long purchaseId, Cash cash) throws Exception {
        web3j = Web3j.build(new HttpService(NETWORK_URL));

        credentials = Credentials.create(cash.getPrivateKey());

        Purchase purchase = purchaseRepository.getByPurchaseId(purchaseId);

        escrow = Escrow.load(purchase.getContractAddress(), web3j, credentials, contractGasProvider);

        TransactionReceipt tr = escrow.cancel().send();
        purchase.setState("X");
        return purchaseRepository.update(purchase);
    }

}
