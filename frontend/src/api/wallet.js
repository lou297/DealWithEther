import { createInstance } from "./index.js";
import { BLOCKCHAIN_URL } from "../config/index.js";
import Web3 from "web3";

const instance = createInstance();

function findAddressById(id, success, fail) {
  instance
    .get("/api/wallets/of/" + id)
    .then(function(response) {
      success(response.data["address"]);
    })
    .catch(fail);
}

function findByUserId(id, success, fail) {
  instance
    .get("/api/wallets/of/" + id)
    .then(success)
    .catch(fail);
}

function isValidPrivateKey(userId, privateKey, success) {
  var web3 = new Web3(new Web3.providers.HttpProvider(BLOCKCHAIN_URL));
  findByUserId(userId, function(response) {
    var address = response.data["address"];
    var account = web3.eth.personal.unlockAccount(address, privateKey, 600).then(success);
    console.dir(address);
    console.dir(account);
    // success(account && account.address == address);
  },
  (error)=> {
    console.dir(error);
    alert("키 인증에 실패하였습니다.");
  });
}

function registerWallet(userId, walletAddress, success, fail) {
  const body = {
    ownerId: userId,
    address: walletAddress
  };

  instance
    .post("/api/wallets", JSON.stringify(body))
    .then(success)
    .catch(fail);
}

function chargeEther(walletAddress, success, fail) {
  instance
    .put("/api/wallets/" + walletAddress)
    .then(success)
    .catch(fail);
}

function chargeCash(walletAddress, privateKey, cashChargeAmount, success, fail) {
  const body = {
    walletAddress,
    privateKey,
    chargeAmount : cashChargeAmount
  };
  instance
    .put("/api/wallets/cash", JSON.stringify(body) )
    .then(success)
    .catch(fail)
}

export {
  findAddressById,
  findByUserId,
  registerWallet,
  isValidPrivateKey,
  chargeEther,
  chargeCash
};
