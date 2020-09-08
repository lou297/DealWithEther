package com.ecommerce.application.impl;

import com.ecommerce.application.ICashContractService;
import com.ecommerce.application.IEthereumService;
import com.ecommerce.application.IWalletService;
import com.ecommerce.domain.Address;
import com.ecommerce.domain.Wallet;
import com.ecommerce.domain.exception.ApplicationException;
import com.ecommerce.domain.exception.NotFoundException;
import com.ecommerce.domain.repository.IWalletRepository;
import com.ecommerce.infrastructure.repository.WalletRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.admin.Admin;
import org.web3j.protocol.admin.methods.response.PersonalUnlockAccount;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.request.Transaction;
import org.web3j.protocol.core.methods.response.EthGetBalance;
import org.web3j.protocol.http.HttpService;
import org.web3j.utils.Convert;
import org.web3j.utils.Convert.Unit;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

/**
 * TODO Sub PJT Ⅱ 과제 1, 과제 3 과제 1: 지갑 관련 기능 구현 1) 지갑 등록, 2) 지갑 조회, 3) 충전 과제 3:
 * 지갑 관련 기능 확장 구현 1) 지갑 토큰 잔액 조회 추가
 *
 * IWalletService를 implements 하여 구현합니다.
 */
@Service
public class WalletService implements IWalletService {
	private static final Logger log = LoggerFactory.getLogger(WalletService.class);

	private IWalletRepository walletRepository;
	private IEthereumService ethereumService;
	private ICashContractService cashContractService;

	@Autowired
	public WalletService(IWalletRepository walletRepository, IEthereumService ethereumService,
			ICashContractService cashContractService) {
		this.walletRepository = walletRepository;
		this.ethereumService = ethereumService;
		this.cashContractService = cashContractService;
	}

	/**
	 * 사용자 id로 지갑을 조회한다.
	 * 
	 * @param userId
	 * @return
	 */
	@Override
	public Wallet get(final long userId) {
		Wallet wallet = walletRepository.get(userId);
		
		// Web3j web3 = Web3j.build(new HttpService("http://localhost:8545/"));
		// EthGetBalance balanceWei = null;
		// try {
		// 	balanceWei = web3.ethGetBalance(wallet.getAddress(), DefaultBlockParameterName.LATEST).send();
		// } catch (IOException e) {
		// 	e.printStackTrace();
		// }
		// BigDecimal balanceInEther = Convert.fromWei(balanceWei.getBalance().toString(), Unit.ETHER);
		// wallet.setBalance(balanceInEther);
		// this.syncBalance(wallet.getAddress(), balanceInEther, 0);


		return wallet;
	}
	/**
	 * 지갑을 DB에 등록한다.
	 * @param wallet
	 * @return
	 */
	@Override
	public Wallet register(final Wallet wallet)
	{
		walletRepository.create(wallet);
		return wallet;
	}

	/**
	 * DB에 저장된 지갑주소의 정보와 이더리움의 잔액 정보를 동기화한다.
	 * @param walletAddress
	 * @return Wallet
	 */
	@Override
	public Wallet syncBalance(final String walletAddress, final BigDecimal balance, final int cash)
	{
		walletRepository.updateBalance(walletAddress, balance, cash);
		return walletRepository.get(walletAddress);
	}

	/**
	 * [지갑주소]로 이더를 송금하는 충전 기능을 구현한다.
	 * 무한정 충전을 요청할 수 없도록 조건을 두어도 좋다.
	 * @param walletAddress
	 * @return Wallet
	 */
	@Override
	public Wallet requestEth(String walletAddress) {

		Admin web3 = Admin.build(new HttpService("http://localhost:8545/"));
		PersonalUnlockAccount personalUnlockAccount = null;
		try {
			personalUnlockAccount = web3.personalUnlockAccount("0xa229097aaac92c5f178d61cfa8fe5ae00f6505d1", "test0").send();
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (personalUnlockAccount.accountUnlocked()) {
			// send a transaction
			BigInteger nonce = BigInteger.valueOf(100);
			BigInteger gasprice = BigInteger.valueOf(100);
			BigInteger gaslimit = BigInteger.valueOf(100);

			Transaction transaction = Transaction.createContractTransaction(
				"0xa229097aaac92c5f178d61cfa8fe5ae00f6505d1",
				nonce,
             	gasprice,  // we use default gas limit
              	null
			);

			
		}
		return null;
	}
}
