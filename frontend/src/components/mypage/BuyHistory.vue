<template>
  <v-row id="container">
    <v-col cols="2" id="item-info-container">
      <v-img
        :src="imgPath(item.itemId)"
        :lazy-src="`https://picsum.photos/10/6?random`"
        aspect-ratio="1"
        @click="onClickItem()"
      >
      </v-img>
      <p style="margin: 0">{{ item.name }}</p>
    </v-col>
    <v-col cols="10" id="progress-container">
      <v-stepper alt-labels v-model="state">
        <v-stepper-header>
          <v-stepper-step step="1" :complete="state >= 1">
            입금 대기
          </v-stepper-step>
          <v-divider></v-divider>
          <v-stepper-step step="2" :complete="state >= 2">
            입금 완료
          </v-stepper-step>
          <v-divider></v-divider>
          <v-stepper-step step="3" :complete="state >= 3">
            배송 시작
          </v-stepper-step>
          <v-divider></v-divider>
          <v-stepper-step step="4" :complete="state >= 4">
            거래 완료
          </v-stepper-step>
        </v-stepper-header>
      </v-stepper>
      <div id="btn-container">
        <v-btn color="error" :disabled="state > 2" @click="cancel"
          >구매취소</v-btn
        >
        <v-btn color="primary" :disabled="state != 3" @click="confirm"
          >구매 확정</v-btn
        >
        <v-btn color="success" :disabled="state != 4" @click="evaluate"
          >평가하기</v-btn
        >
      </div>
    </v-col>
  </v-row>
</template>
<script>
import * as purchaseService from "@/api/purchase.js";
import * as walletService from "@/api/wallet.js";
import * as ratingService from "@/api/rating.js";
import * as itemService from "@/api/item.js";
export default {
  props: ["buyPurchase"],
  data() {
    return {
      userId: this.$store.state.user.id,
      state: -1,
      item: {},
      rating: {
        id: 0,
        purchasesId: 0,
        evaluator: 0,
        getter: 0,
        score: 0,
      },
    };
  },
  created() {
    this.updateState();
    this.fetchPurchaseInfo();
  },
  mounted: function () {
    const vm = this;
    ratingService.getList(
      (res) => {
        if (res) {
          vm.ratings = res.data;
          for (var key in vm.ratings) {
            // console.log(vm.ratings[key]);
            if (
              vm.ratings[key].purchasesId == vm.buyPurchase.id &&
              vm.ratings[key].evaluator == vm.buyPurchase.buyerId
            ) {
              vm.state = 6;
            }
          }
        }
      },
      (err) => {
        console.log("에러");
      }
    );
  },
  methods: {
    updateState() {
      switch (this.buyPurchase.state) {
        case "I":
          this.state = 1;
          break;
        case "P":
          this.state = 2;
          break;
        case "S":
          this.state = 3;
          break;
        case "C":
          this.state = 4;
          break;
        case "X":
          this.state = 5;
          break;
      }
    },
    imgPath(id) {
      return process.env.VUE_APP_BACKEND + "api/items/images/" + id + "_1";
    },
    fetchPurchaseInfo() {
      itemService.findById(
        this.buyPurchase.itemId,
        (res) => {
          this.item = res.data;
        },
        (err) => {
          alert(err);
        }
      );
    },
    onClickItem() {
      alert("클릭");
      this.$router.push("../item/detail/" + this.buyPurchase.itemId);
    },
    evaluate() {
      var score = prompt("점수를 입력해주세요 (1~5)");
      if (score != 5 && score != 1 && score != 2 && score != 3 && score != 4) {
        alert("잘못된 점수입니다!");
        return;
      }
      this.rating.purchasesId = this.buyPurchase.id;
      this.rating.evaluator = this.buyPurchase.buyerId;
      this.rating.getter = this.buyPurchase.sellerId;
      this.rating.score = parseInt(score);

      ratingService.create(
        this.rating,
        (res) => {
          alert("평가 성공!");
        },
        (err) => {
          alert("평가 실패!");
        }
      );
    },
    confirm() {
      const privateKey = prompt("구매를 확정하려면 개인키를 입력하세요.");
      const vm = this;
      walletService.isValidPrivateKey(this.userId, privateKey, (res) => {
        if (res) {
          purchaseService.confirm(
            this.buyPurchase.purchaseId,
            privateKey,
            (res) => {
              alert("구매가 확정되었습니다.");
            },
            (error) => {
              alert("구매가 확정되지 않았습니다. 다시 시도해주세요");
            }
          );
        } else {
          alert("개인키 인증에 실패하였습니다.");
          this.isCashCharging = false;
        }
      });
    },
    cancel() {
      const privateKey = prompt("구매를 취소하려면 개인키를 입력하세요.");
      const vm = this;
      walletService.isValidPrivateKey(this.userId, privateKey, (res) => {
        if (res) {
          purchaseService.cancel(
            this.buyPurchase.purchaseId,
            privateKey,
            (res) => {
              alert("구매가 취소되었습니다.");
            },
            (error) => {
              alert("구매가 취소되지 않았습니다. 다시 시도해주세요");
            }
          );
        } else {
          alert("개인키 인증에 실패하였습니다.");
          this.isCashCharging = false;
        }
      });
    },
  },
};
</script>

<style>
#container {
  border: 1px solid black;
}

#item-info-container {
  align-self: center;
}

#progress-container {
  align-self: center;
}

#btn-container {
  float: right;
}
</style>
