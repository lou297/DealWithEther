<template>
  <div>
    <h-nav></h-nav>
    <div id="main-overview" class="container" style="margin-top: 52px;">
      <v-row>
        <v-col>
        <div>
          <div class="box">
            <input type="text" placeholder="상품명, 지역명 등을 검색해보세요!">
            <button>검색</button>
          </div>
          <div style="float:left; padding:8px 0 0 15px;">
            판매하기 | 내상점 | 문의톡
          </div>
           <div style="clear:both;"></div>
        </div><br>
       
          <v-footer padless>
            <v-row justify="center" no-gutters>
                  <v-btn
                    v-for="link in links"
                    :key="link"
                    color="black"
                    text
                    rounded
                    class="my-2"
                    style="margin-left: 30px;"
                  >
                    {{ link }}
                  </v-btn>
            </v-row>
          </v-footer>

          <v-carousel hide-delimiters style="margin-top: 10px; height: 300px; padding:15px 15px 0 15px;">
            <v-carousel-item
              v-for="(item,i) in items"
              :key="i"
            >
            <img :src="item.src" style="height:57%; width:100%">
            </v-carousel-item>
          </v-carousel><br>

          <v-row>
           <v-col cols="3">
           <v-card class="mx-auto" max-width="230" >
              <img
                class="white--text align-end"
                height="200px"
                width="230"
                src="../../public/images/glass.jpg"
              >
              <v-card-actions style="padding-left:15px;">블루라이트 차단안경</v-card-actions>
              <v-card-actions style="padding-left:15px;">13,000원</v-card-actions>
            </v-card>
           </v-col>
           <v-col cols="3">
            <v-card class="mx-auto" max-width="230">
              <img
                class="white--text align-end"
                height="200px"
                width="230"
                src="../../public/images/image1.jpg"
              >

              <v-card-actions style="padding-left:15px;">입생로랑 정품 가방</v-card-actions>
              <v-card-actions style="padding-left:15px;">380,000원</v-card-actions>
            </v-card>
          </v-col>
           <v-col cols="3">
            <v-card class="mx-auto" max-width="230">
              <img
                class="white--text align-end"
                height="200px"
                width="230"
                src="../../public/images/image2.jpg"
              >

              <v-card-actions style="padding-left:15px;">뉴발란스 327 그레이</v-card-actions>
              <v-card-actions style="padding-left:15px;">160,000원</v-card-actions>
            </v-card>
          </v-col>
          <v-col cols="3">
            <v-card class="mx-auto" max-width="230">
              <img
                class="white--text align-end"
                height="200px"
                width="230"
                src="../../public/images/image3.jpg"
              >

              <v-card-actions style="padding-left:15px;">애플워치3 gps 스그</v-card-actions>
              <v-card-actions style="padding-left:15px;">210,000원</v-card-actions>
            </v-card>
          </v-col>
          </v-row>
    
      </v-col>
        </v-row>
        </div>
  </div>
</template>

<script>
import HNav from "../components/common/HNav";
import StepFlow from '@/components/common/StepFlow';
import Vue from 'vue';
import Vuetify from 'vuetify/lib';
import 'material-design-icons-iconfont/dist/material-design-icons.css';
import { mdiAccount } from '@mdi/js';

export default {
   data() {
    return {
      items: [
          {
            src: require('../../public/images/test.jpg')
          },
          {
            src: 'https://cdn.vuetifyjs.com/images/carousel/sky.jpg'
          },
          {
            src: 'https://cdn.vuetifyjs.com/images/carousel/bird.jpg'
          },
          {
            src: 'https://cdn.vuetifyjs.com/images/carousel/planet.jpg'
          }
        ],
        links: [
        '여성의류',
        '남성의류',
        '생활/문구',
        '디지털/가전',
        '유아물품',
        '패션잡화',
        '도서/티켓',
        '스포츠/레저',
      ],
    }
  },
  components: {
    HNav,
    StepFlow
  }
};
</script>

<style>

.box{
  margin-left: 250px;
  height: 40px;
  width: 440px;
  border: 1px solid #1b5ac2;
  background: #ffffff;
  float: left;
}

input{
  font-size: 16px;
  width: 325px;
  padding: 10px;
  border: 0px;
  outline: none;
  float: left;
}

button{
  width: 50px;
  height: 100%;
  border: 0px;
  background: #1b5ac2;
  outline: none;
  float:right;
  color: #ffffff;
}
</style>
