package com.ecommerce.application.impl;

import com.ecommerce.application.ICashContractService;
import com.ecommerce.domain.CommonUtil;
import com.ecommerce.domain.CryptoUtil;
import com.ecommerce.domain.exception.ApplicationException;
import com.ecommerce.domain.wrapper.CashContract;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.datatypes.Function;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.ECKeyPair;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.request.Transaction;
import org.web3j.protocol.core.methods.response.EthGetTransactionReceipt;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.tx.gas.DefaultGasProvider;

import java.io.IOException;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;

@Service
public class CashContractService implements ICashContractService {
    private static final Logger log = LoggerFactory.getLogger(CashContractService.class);

    @Value("${eth.erc20.contract}")
    private String ERC20_TOKEN_CONTRACT;

    @Value("${eth.admin.address}")
    private String ADMIN_ADDRESS;

    @Value("${eth.admin.wallet.filename}")
    private String WALLET_RESOURCE;

    @Value("${eth.encrypted.password}")
    private String PASSWORD;

    private CashContract cashContract;
    private ContractGasProvider contractGasProvider = new DefaultGasProvider();
    private Credentials credentials;

    @Autowired
    private Web3j web3j;

    /**
     * TODO Sub PJT Ⅱ 과제 3 토큰 잔액 조회
     * 
     * @param eoa
     * @return
     */
    @Override
    public int getBalance(String eoa) {

        return -1;
    }

    @Override
    public String buyCash(String eoa, String pk, double chargeAmount) throws Exception {
        
        System.out.println(eoa);
        System.out.println(pk);
        System.out.println(chargeAmount);
        Web3j web3 = Web3j.build(new HttpService());  // defaults to http://localhost:8545/
        
        Credentials dummyCredentials = Credentials.create(pk);
        ECKeyPair keyPair = dummyCredentials.getEcKeyPair();
        BigInteger privateKey = keyPair.getPrivateKey();
        BigInteger publicKey = keyPair.getPublicKey();
        System.out.println(privateKey);
        System.out.println(publicKey);
        
        credentials = Credentials.create(pk);
        cashContract = CashContract.load(ERC20_TOKEN_CONTRACT, web3, credentials, BigInteger.valueOf(2), BigInteger.valueOf(100000000));
        
        // long cashAmount = (long) (chargeAmount * 100000);
        // TransactionReceipt transactionReceipt = contract.buy(BigInteger.valueOf(cashAmount)).send();

        System.out.println("ok");
        return null;
    }

    
}
