<template>
  <v-row id="container">
    <v-col cols="4">
      <v-img
        :src="`https://picsum.photos/500/300?random`"
        :lazy-src="`https://picsum.photos/10/6?random`"
        aspect-ratio="1"
        @click="onClickItem()"
      >
      </v-img>
      <p>{{ buyPurchase.itemName }}</p>
    </v-col>
    <v-col cols="8" id="progress-container">
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
      <div>
        <v-btn color="success" :disabled="state != 4" @click="evaluate"
          >평가하기</v-btn
        >
        <v-btn color="primary" :disabled="state != 3" @click="confirm"
          >구매 확정</v-btn
        >
        <v-btn color="error" :disabled="state > 2" @click="cancel"
          >구매취소</v-btn
        >
      </div>
    </v-col>
  </v-row>
</template>
<script>
import * as purchaseService from "@/api/purchase.js";
import * as walletService from "@/api/wallet.js";
import * as ratingService from "@/api/rating.js";
export default {
  props: ["buyPurchase"],
  data() {
    return {
      userId: this.$store.state.user.id,
      state: -1,
      ratings: {
        id: 0,
        purchasesId: 0,
        evaluator: 0,
        getter: 0,
        score: 0,
      },
    };
  },
  created() {
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
  mounted: function() {},
  methods: {
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
      this.ratings.purchasesId = this.buyPurchase.id;
      this.ratings.evaluator = this.buyPurchase.buyerId;
      this.ratings.getter = this.buyPurchase.sellerId;
      this.ratings.score = parseInt(score);

      ratingService.create(
        this.ratings,
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

#progress-container {
  align-self: center;
}
</style>
