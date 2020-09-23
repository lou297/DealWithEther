package com.ecommerce;

import com.ecommerce.domain.wrapper.CashContract;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.admin.Admin;
import org.web3j.protocol.admin.methods.response.PersonalUnlockAccount;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.EthGetBalance;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.gas.ContractGasProvider;

import java.io.IOException;
import java.math.BigInteger;

@RunWith(SpringRunner.class)
@SpringBootTest
public class kmhtest {

    @Test
    public void contextLoads() throws Exception {
        Admin web3j = Admin.build(new HttpService());  // defaults to http://localhost:8545/
        Web3j web3 = Web3j.build(new HttpService());
        String account = web3.ethAccounts().send().getAccounts().get(3);
        System.out.println(account);
        PersonalUnlockAccount b = web3j.personalUnlockAccount("0x5BB93AD348a466d6F6E39f0627b1ef6C2ab80204",
                "0x88640494eb1bd328c3fe9fb7b01d99cd7f4a187eec846c210bf023d89bfe50b0").send();
//		System.out.println(web3j.ethGasPrice().send().getGasPrice());
//		System.out.println(b.getResult());
//		System.out.println(b.accountUnlocked());

        Credentials credentials = Credentials.create(
        		"0x88640494eb1bd328c3fe9fb7b01d99cd7f4a187eec846c210bf023d89bfe50b0");
        CashContract contract = CashContract.load(
                "0x5d11bF58cA0E5495a951CA106A294342d4F0876E", web3j, credentials, BigInteger.valueOf(300),
				BigInteger.valueOf(3000000));
        System.out.println(contract.isValid());
//		EthGetBalance balance = web3j.ethGetBalance(account, DefaultBlockParameterName.LATEST).send();
//		System.out.println(balance.getBalance());
//		BigInteger a = contract.balanceOf("0x5BB93AD348a466d6F6E39f0627b1ef6C2ab80204").sendAsync().get();
//		System.out.println(a);
//		System.out.println(contract.balanceOf("0x5BB93AD348a466d6F6E39f0627b1ef6C2ab80204"));
//		System.out.println(contract.buy(new BigInteger("1000000000000000000")));


    }

    @Test
    public void contextLoads() throws Exception {

    }

}
