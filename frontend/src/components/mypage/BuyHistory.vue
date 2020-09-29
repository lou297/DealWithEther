<template>
    <v-row id="container">
      <v-col cols="4">
        <v-img
          :src="`https://picsum.photos/500/300?random`"
          :lazy-src="`https://picsum.photos/10/6?random`"
          aspect-ratio="1"
          @click="onClickItem()">
        </v-img>
        <p>{{buyPurchase.itemName}}</p>
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
          <v-btn color="success" :disabled="state != 4">평가하기</v-btn>
          <v-btn color="primary" :disabled="state != 3">구매 확정</v-btn>
          <v-btn color="error" :disabled="state > 2">구매취소</v-btn>
        </div>
      </v-col>
    </v-row>
</template>
<script>
export default {
  props : [
    'buyPurchase'
  ],
  data() {
    return {
      state : this.buyPurchase.state
    };
  },
  mounted: function() {
  },
  methods: {
    onClickItem() {
      alert("클릭")
      this.$router.push("../item/detail/" + this.buyPurchase.itemId);
    },
  }
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
