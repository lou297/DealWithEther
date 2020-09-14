package com.ecommerce.application;

import java.io.IOException;

public interface ICashContractService {
    int getBalance(String eoa);
    String buyCash(String eoa, String pk, double chargeAmount) throws Exception;
}
