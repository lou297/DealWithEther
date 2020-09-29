<template>
    <div>
        <!-- <my-page-nav></my-page-nav> -->

        <v-row>
            <v-col cols="12" lg="8" offset-lg="2">
                <v-row id="profile-container">
                    <v-col cols="4" id="profile-image-container">
                        <div>
                            <v-avatar size="130px">
                                <v-img
                                    :src="`https://picsum.photos/500/300?random`"
                                    :lazy-src="`https://picsum.photos/10/6?random`"
                                    aspect-ratio="1"
                                    class="grey lighten-2">
                                </v-img>
                            </v-avatar>
                            <p class="font-weight-bold" style="margin: 10px 0 0 0; font-size:18px">{{user.name}}</p>
                            <p class="font-weight-bold">{{user.email}}</p>
                        </div>
                        <div>
                            <v-row id="shop-info-container">
                                <v-col cols="12" sm="4">
                                    <p style="margin:0;">
                                        0
                                    </p>
                                    <p>
                                        등록 건수
                                    </p>
                                </v-col>
                                <v-col cols="12" sm="4">
                                    <p style="margin:0;">
                                        0
                                    </p>
                                    <p>
                                        판매 건수
                                    </p>
                                    <!-- <span>
                                        판매 건수 : <span>0</span>
                                    </span> -->
                                </v-col>
                                <v-col cols="12" sm="4">
                                    <p style="margin:0;">
                                        0
                                    </p>
                                    <p>
                                        구매 건수
                                    </p>
                                    <!-- <span>
                                        구매 건수 : <span>0</span>
                                    </span> -->
                                </v-col>
                            </v-row>
                        </div>
                    </v-col>
                    <v-col cols="8" id="wallet-info-container">
                        <v-row>
                            <v-col cols="6" md="3">
                                <p>보유 ETH : </p>
                            </v-col>
                            <v-col cols="6" md="3">
                                <p>{{ wallet.balance }} ETH</p>
                            </v-col>
                            <v-col cols="12" md="6">
                                <v-btn
                                    v-on:click="chargeETH()"
                                    v-bind:disabled="isCharging"
                                    color="success"
                                    width="100%"
                                >
                                    {{ isCharging ? "충전중" : "10ETH 충전하기" }}
                                </v-btn>
                            </v-col>
                        </v-row>
                        <v-row>
                            <v-col cols="6" md="3">
                                <p>보유 캐시 : </p>
                            </v-col>
                            <v-col cols="6" md="3">
                                <p>{{ wallet.cash }} CASH</p>
                            </v-col>
                            <v-col cols="6" md="3">
                                <input
                                    v-model="cashChargeAmount"
                                    type="number"
                                    class="form-control"
                                    />
                            </v-col>
                            <v-col cols="6" md="3">
                                <v-btn
                                color="primary"
                                v-on:click="chargeCash()"
                                v-bind:disabled="!canCashCharge"
                                width="100%"
                                >
                                {{ isCashCharging ? "충전중" : "캐시 충전하기" }}
                                </v-btn>
                            </v-col>
                        </v-row>
                        <v-row>
                            <v-col cols="12" md="3">
                                <p>내 지갑주소 : </p>
                            </v-col>
                            <v-col cols="12" md="9">
                                <p>{{ wallet.address }}</p>
                            </v-col>
                        </v-row>
                    </v-col>
                </v-row> 

                <v-row>
                    <v-tabs color="#356859" grow background-color="#FFFBE6">
                        <v-tab
                            class="tab-menu"
                            @click="showDealHistory">
                            등록 상품 목록
                        </v-tab>
                        <v-tab
                            class="tab-menu"
                            @click="showRegistedItemList">
                            구매 내역 목록
                        </v-tab>
                        <v-tab
                            class="tab-menu"
                            @click="showRecentlyViewHistory">
                            최근 본 목록
                        </v-tab>
                        <v-tab
                            class="tab-menu"
                            @click="showBookMarkList">
                            찜한 목록
                        </v-tab>
                    </v-tabs>
                </v-row>
                <v-row>
                        <p>{{size}}건</p>
                        <v-container v-for="item in items" :key="item.id" >
                            <item-card :item="item" @clicked="onClickItem(item.id)"></item-card>
                        </v-container>
                </v-row>
                <v-row>
                    <v-container v-for="buyPurchase in buyPurchases" :key="buyPurchase.id">
                        <buy-history :buyPurchase="buyPurchase"></buy-history>
                    </v-container>
                </v-row>
            </v-col>
        </v-row>
           
    </div>
  
</template>

<script>
import MyPageNav from "./MyPageNav.vue";
import { findById } from "@/api/user.js";
import {loadBookMark} from "@/api/bookmark.js";
import * as itemService from "../../api/item.js";
import * as purchaseService from "@/api/purchase.js";

import ItemCard from "../shop/ItemCard.vue";
import BuyHistory from "./BuyHistory.vue";

//이더리움 통신
import * as walletService from "@/api/wallet.js";
import { createWeb3 } from "@/utils/itemInventory.js";
import { buyCash, getBalance } from "@/utils/cashContract.js";
import { ethToWei } from "@/utils/ethereumUnitUtils.js";
import BN from "bn.js";

export default {
  components: {
    MyPageNav,
    ItemCard,
    BuyHistory
  },
  data() {
    return {
    //   userId: this.$store.state.user.id,
      user : {
          id : this.$store.state.user.id,
          name : "",
          email : ""
      },
      size : 0,
      wallet: {
        id: 0,
        ownerId: null,
        address: "",
        balance: 0,
        cash: 0,
        receivingCount: 0
      },
      items: [],
      buyPurchases : [],
      isCharging: false, // 현재 코인을 충전하고 있는 중인지 확인
      isCashCharging: false, // 현재 캐시을 충전하고 있는 중인지 확인
      cashChargeAmount: 0.1,
      walletAddress: this.$store.state.user.walletAddress
    };
  },
  created() {
      this.fetchUserInfo()
      this.fetchWalletInfo()
      this.fetchBuyHistory()
  },
  computed: {
    canCashCharge() {
        return this.cashChargeAmount >= 0.1 && !this.isCashCharging;
    }
  },
  methods: {
      onClickItem(itemId) {
            this.$router.push("../item/detail/" + itemId);
        },
      showDealHistory() {
        //   alert("deal history")
        this.items = []
      },
      showRecentlyViewHistory() {
        const bookMark = JSON.parse(sessionStorage.getItem("bookmark"))
        
        if(bookMark != undefined)
            this.items = bookMark
        else
            this.items = []
      },
      showBookMarkList() {
        //   this.items = []
          loadBookMark(this.user.id,
            res => {
                this.items = res.data
            },
            error => {
                alert(err)
            }
          )
      },
      showRegistedItemList() {
          itemService.findMySaleItems(this.user.id,
            res=> {
                console.dir(res)
                this.items = res.data
            },
            err => {
                alert(err)
            }
          )
      },
      fetchWalletInfo() {
      /**
       * TODO: PJTⅡ 과제1 Req.1-2 [지갑 조회]
       * 사용자 지갑을 조회하여 잔액을 화면에 보여준다.
       */
      walletService.findByUserId(this.user.id,
        (response)=> {
          this.wallet.id = response.data.id;
          this.wallet.ownerId = response.data.ownerId;
          this.wallet.address = response.data.address;
          this.wallet.balance = response.data.balance;
          this.wallet.cash    = response.data.cash;
          this.wallet.receivingCount = response.data.receivingCount;
        },
        (error)=> {
          alert("지갑 조회 중 에러 발생");
          console.dir(error);
        }
      )

      /**
       * TODO: PJTⅡ 과제3 Req.1-3 [지갑 조회 확장]
       * 캐시 잔액 정보를 포함하여 화면에 보여준다.
       */

    },
    /**
     * TODO: PJTⅡ 과제1 Req.1-3 [코인 충전]
     * 이더 충전을 요청
     */
    chargeETH() {
      this.isCharging = true;
      walletService.chargeEther(this.walletAddress,
        (response)=> {
          console.dir(response);
          this.wallet.balance = response.data.balance;
          this.wallet.receivingCount = response.data.receivingCount;
          alert("충전 되었습니다.");
          this.isCharging = false;
        },
        (error)=> {
          console.dir(error);
          alert("충전에 실패하였습니다.");
          this.isCharging = false;
        }
      )
    },
    chargeCash() {
      const vm = this;
      this.isCashCharging = true;
      const privateKey = prompt("캐시를 충전하시려면 개인키를 입력하세요.");
      let check = false;
      walletService.isValidPrivateKey(this.user.id, privateKey, 
        (res) => {
          if (res) {
            /**
             * TODO: PJTⅡ 과제3 Req.1-1 [토큰 구매]
             * 이더를 지불하고 캐시를 충전
             */
            walletService.chargeCash(this.wallet.address, privateKey, this.cashChargeAmount,
              (response)=> {
                console.dir(response);
                this.wallet.balance = response.data.balance;
                this.wallet.cash    = response.data.cash;
                alert((this.cashChargeAmount*100000)+"Cash가 충전 되었습니다.");
                this.isCashCharging = false;
              },
              (error)=>{
                console.dir(error);
                alert("캐쉬 충전에 실패하였습니다.");
                this.isCashCharging = false;
              }
            )
    
          }else {
            alert("개인키 인증에 실패하였습니다.");
            this.isCashCharging = false;
          }
        }
      )
    },
    /**
     * TODO: PJTⅡ 과제3 Req.1-2 [토큰 잔액 조회]
     * 스마트 컨트랙트의 잔액 확인 함수 호출
     */
    fetchCashBalance() {
      
    },
    fetchEtherBalance() {
      const vm = this;
      walletService.findByUserId(this.user.id, function(res) {
        const web3 = createWeb3();
        vm.wallet.balance = web3.utils.fromWei(
          res.data.balance.toString(),
          "ether"
        );
      });
    },
    // 회원 정보 가져온다.
    fetchUserInfo() {
      findById(this.user.id, 
        (response) => {
            const data = response.data;
            this.user.name = data["name"];
            this.user.email = data["email"];
      });
    },
    fetchBuyHistory() {
        purchaseService.findMyPurchases(this.user.id,
            res => {
                this.buyPurchases = res.data
                console.dir(res)
            },
            err => {
                alert(err)
            }
        )
    }
  },
  mounted: function() {
      
  }
};
</script>

<style>
#profile-container {
    /* border: 1px solid #263238; */
}

#profile-image-container {
    background-color: #ffd455;
    padding: 30px 0 18px 0;
}
#shop-info-container {
    margin-left: 20px;
    margin-right: 20px;
}

#wallet-info-container {
    align-self: center;
}

#wallet-info-container p {
    font-size: 17px;
}

#item-container {
    min-height: 100px;
}

.tab-menu {
    font-size: 16px;
    font-weight: bold;
}
</style>
