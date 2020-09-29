<template>
  <div>
    <v-container>
      <v-layout row>
        <v-flex col xl5 lg5 md5 sm12 xs12>
          <v-carousel cycle hide-delimiter-background show-arrows-on-hover>
            <v-carousel-item v-for="n in item.image" :key="n">
              <v-sheet height="100%">
                <v-row class="fill-height" align="center" justify="center">
                  <v-img
                    :src="imgPath(n)"
                    aspect-ratio="1"
                    class="grey lighten-2"
                  ></v-img>
                </v-row>
              </v-sheet>
            </v-carousel-item>
          </v-carousel>
        </v-flex>
        <v-flex xl1 lg1 md1 sm0 xs0> </v-flex>
        <v-flex col xl6 lg6 md6 sm12 xs12>
          <v-col>
            <v-row>
              <v-col style="float: left; text-align: left"
                ><h5>{{ item.explanation }}</h5></v-col
              >
            </v-row>
            <v-row>
              <v-col style="float: right; text-align: left"
                ><h4 style="display: inline; font-weight: bold">
                  {{ item.price }}
                </h4>
                원</v-col
              >
            </v-row>
            <v-divider style="margin-top: 0px" />
            <v-row>
              <v-col cols="3" style="text-align: left">판매자</v-col>
              <v-col
                cols="8"
                style="text-align: left; padding-bottom: 0px; height: 48px"
              >
                <p style="float: left">{{ item.seller.name }} (</p>
                <v-img
                  style="
                    text-align: left;
                    width: 15px;
                    float: left;
                    margin: 3px 4px 0 4px;
                  "
                  :src="star"
                ></v-img>
                3.7 )
              </v-col>
              <v-col cols="3" style="text-align: left">상품 등록일</v-col>
              <v-col cols="8" style="text-align: left">{{ time }}</v-col>
              <v-col cols="3" style="text-align: left">거래방법</v-col>

              <v-col
                v-if="item.directDeal == true"
                cols="8"
                style="text-align: left"
                >직거래</v-col
              >
              <v-col
                v-if="item.directDeal == true"
                cols="3"
                style="text-align: left"
                >직거래 장소</v-col
              >
              <v-col
                v-if="item.directDeal == true"
                cols="8"
                style="text-align: left"
                >{{ item.dealRegion }}</v-col
              >
              <v-col v-else cols="8" style="text-align: left">택배</v-col>
              <v-col cols="3" style="text-align: left">상태</v-col>
              <v-col cols="8" style="text-align: left">{{
                item.available ? "판매중" : "판매 종료"
              }}</v-col>

              <v-row>
                <v-col cols="2.2">
                  <v-btn large style="width: 100%" @click="BookMark">찜</v-btn>
                </v-col>
                <v-col cols="2.2">
                  <v-btn large color="primary" style="width: 100%"
                    >문의톡</v-btn
                  >
                </v-col>
                <v-col cols="2.2">
                  <v-btn large color="warning" style="width: 100%"
                    >네고요청</v-btn
                  >
                </v-col>
                <v-col cols="2.2">
                  <v-btn
                    large
                    color="error"
                    style="width: 100%"
                    @click="purchase"
                    >바로구매</v-btn
                  >
                </v-col>
              </v-row>
            </v-row>
          </v-col>
        </v-flex>
        <v-divider />
      </v-layout>
    </v-container>
  </div>
</template>

<script>
// import * as itemService from "../../api/item.js";
// import * as auctionService from "../../api/auction.js";
// import * as userService from "../../api/user.js";
import { findById as findUserById } from "@/api/user.js";
import * as purchaseService from "@/api/purchase.js";
import { weiToEth } from "@/utils/ethereumUnitUtils.js";
// import { ITEM_STATUS } from "../../config/constants.js";
import { getLocalImg } from "@/utils/imgLoader.js";
import { getPrice } from "@/utils/itemInventory.js";
import { findById } from "@/api/item.js";
import { CATEGORY } from "@/utils/category.js";
import { bookMarkSave } from "@/api/bookmark.js";
import * as walletService from "@/api/wallet.js";
import HNav from "../../components/common/HNav";

export default {
  name: "ItemDetail",
  components: {
    HNav,
  },
  data() {
    return {
      item: {
        id: null,
        name: "",
        category: null,
        explanation: "",
        available: null,
        state: "",
        seller: {
          id: null,
          name: "",
          email: "",
        },
        directDeal: false,
        dealRegion: "",
        image: 0,
        price: null,
        registeredAt: null,
      },
      bookMarkList: {
        userId: "",
        itemId: "",
      },
      userId: this.$store.state.user.id,
      star: require("../../../public/images/star.png"),
      time: "",
      cash: 0,
    };
  },
  methods: {
    purchase() {
      var address = "";
      var vm = this;
      if (this.cash < this.item.price) {
        alert("캐시가 부족합니다!");
        return;
      }
      const privateKey = prompt("상품을 구매하시려면 개인키를 입력하세요.");
      let check = false;
      var id = this.userId;
      walletService.isValidPrivateKey(this.userId, privateKey, (res) => {
        if (res) {
          address = prompt("배송받을 주소를 입력하세요!");
          purchaseService.create(
            id,
            privateKey,
            (response) => {
              alert("구매 의사를 전달하였습니다");
            },
            (error) => {
              alert("에러가 발생하였습니다.");
            }
          );
        } else {
          alert("개인키 인증에 실패하였습니다.");
          this.isCashCharging = false;
        }
      });
    },
    goBack: function () {
      // 이전 페이지로 이동한다.
      this.$router.go(-1);
    },
    convertWeiToEth(value) {
      if (value) {
        return weiToEth(value.toString()) + " ETH";
      } else {
        return "-";
      }
    },
    getImg(name) {
      if (name) {
        console.log(name);
        return getLocalImg(name);
      }
      return null;
    },
    imgPath(n) {
      console.log(process.env.VUE_APP_BACKEND);
      return (
        process.env.VUE_APP_BACKEND +
        "api/items/images/" +
        this.item.id +
        "_" +
        n
      );
    },
    saveBookMark() {
      var bookMark = JSON.parse(sessionStorage.getItem("bookmark"));
      if (bookMark == undefined) {
        bookMark = new Set();
        bookMark.add(this.item);
      } else {
        var duplicate = false;
        for (var item of bookMark) {
          if (this.item.id == item.id) {
            duplicate = true;
            break;
          }
        }
        if (!duplicate) {
          bookMark = new Set([...bookMark, this.item]);
        }
      }
      sessionStorage.setItem("bookmark", JSON.stringify(bookMark));
    },
    BookMark() {
      this.bookMarkList.userId = this.userId;
      this.bookMarkList.itemId = this.item.id;

      bookMarkSave(
        this.bookMarkList,
        function (success) {
          console.log("찜 성공");
        },
        function (fail) {
          console.dir(fail);
        }
      );
    },
  },
  filters: {
    symbolToFullName(symbol) {
      return CATEGORY[symbol];
    },
  },
  created() {
    this.item.id = this.$route.params.id;
  },
  mounted: function () {
    const vm = this;

    // [DB] 상품 상세 정보 조회
    findById(
      this.item.id,
      function (res) {
        const result = res.data;
        vm.item.name = result.name;
        vm.item.category = result.category;
        vm.item.explanation = result.explanation ? result.explanation : "";
        vm.item.available = result.available;
        vm.item.seller.id = result.seller;
        vm.item.image = result.image;
        vm.item.price = result.price;
        vm.item.directDeal = result.directDeal;
        vm.item.dealRegion = result.dealRegion;
        vm.item.registeredAt = result.registeredAt;
        console.log(vm.item);

        var date = "";
        date = vm.item.registeredAt.split("T");
        vm.time += date[0];
        vm.time += " ";
        vm.time += date[1];

        // 판매자 정보
        findUserById(result.seller, function (res) {
          const result = res.data;
          vm.item.seller.name = result.name;
          vm.item.seller.email = result.email;
          vm.saveBookMark();
        });
      },

      function (error) {
        console.error(error);
        alert("DB에서 상품 상세 정보 조회를 가져올 수 없습니다.");
      }
    );
    // [Smart Contract] 가격 조회
    getPrice(
      this.item.id,
      function (price) {
        vm.item.price = price;
        console.log(price);
      },
      function (err) {
        console.error("가격 조회 실패:", err);
      }
    );

    walletService.findByUserId(this.userId, (res) => {
      if (res) {
        vm.cash = res.data.cash;
        console.log(vm.cash);
      }
    });
  },
};
</script>

<style>
img.center {
  display: block;
  margin: 2rem auto;
}
</style>
