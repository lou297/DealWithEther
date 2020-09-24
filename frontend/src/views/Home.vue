<template>
    <div>
        <h-nav></h-nav>
    <div style="height:530px; background-color: rgb(255,212,85);">
      
        <img src="../../public/images/avataaars.svg" alt="" style="width: 15rem; margin: 5rem 0 1rem 0;"><br>
        
        <h1 style="margin-top: 2rem; color:red; display: inline-block;">중</h1>
        <h1 style="margin-top: 2rem; display: inline-block;">고마켓에서 </h1>
        <h1 style="margin-top: 2rem; color:red; margin-left:12px; display: inline-block;"> 코</h1>
        <h1 style="margin-top: 2rem; display: inline-block;">인으로 거래하자</h1><br><br>

        <form class="clearfix searchform" style="margin-bottom:8px;">
            <input type="search" id="search-box" placeholder="상품명, 지역명 등을 검색해보세요!">
        </form>
    </div>

    <v-card color="basil" >
        <v-card-title class="text-center justify-center py-6">
        <h1 class="font-weight-bold text-secondary">카테고리별 인기상품</h1>
        </v-card-title>
        <v-tabs v-model="tab" background-color="transparent" color="basil" grow>
        <v-tab v-for="item in items" :key="item" class="font-weight-bold"  @click="getCategory(item)" style="font-family: 'Jua', sans-serif; font-size:18px;">{{ item }}</v-tab>
        </v-tabs>

        <v-tabs-items v-model="tab">
        <v-tab-item v-for="item in items" :key="item">
            <v-card color="basil" flat>
            <v-card-text>
                <div class="row">
                        <v-flex xs12 sm12 md6 lg4 xl3
                                class="col-md-3 artwork"
                                v-for="item in itemlist"
                                v-bind:key="item.id">
                            <item-card :item="item" @clicked="onClickItem(item.id)"></item-card>
                        </v-flex>
                </div>
            </v-card-text>
            </v-card>
        </v-tab-item>
        </v-tabs-items>
    </v-card>
    </div>
</template>

<script>
import HNav from "../components/common/HNav copy";
import StepFlow from '@/components/common/StepFlow';
import Vue from 'vue';
import Vuetify from 'vuetify/lib';
import 'material-design-icons-iconfont/dist/material-design-icons.css';
import {mdiAccount} from '@mdi/js';
import {findAll} from "@/api/item";
import {getPrice} from "@/utils/itemInventory";
import ItemCard from "@/components/shop/ItemCard";
import {findByMainCategory} from "@/api/item.js";

export default {
    data() {
        return {
            itemlist: [],
            circle: false,
            disabled: false,
            length: 30,
            nextIcon: 'navigate_next',
            prevIcon: 'navigate_before',
            page: 1,
            totalVisible: 10,
            tab: null,
            items: [
                '패션/잡화',
                '뷰티/미용',
                '디지털/가전',
                '유아물품',
                '도서/티켓',
                '스포츠/레저',
                '생활/문구/가구',
                '기타',
            ],
            text: 'Lorem',
        }
    },
    components: {
        HNav,
        StepFlow,
        ItemCard
    },
    mounted: function () {

        // findAll(function (response) {
        //     if (response.data.length > 0) {
        //         vm.items = response.data;
        //         vm.items.forEach(i => {
        //             // [스마트 컨트랙트] 가격 조회
        //             getPrice(
        //                 i.id,
        //                 function (price) {
        //                     vm.$set(i, "price", price);
        //                 },
        //                 function (err) {
        //                     console.error('가격 조회 실패:', err);
        //                     // alert("상품 가격 조회를 실패했습니다.");
        //                 }
        //             )
        //         })
        //     }
        // });
    },
    methods: {
        onClickItem(itemId) {
            this.$router.push("item/detail/" + itemId);
        },
        getCategory(category){ // 카테고리 누르면 받아오기
            console.log(category);
            const vm = this;
            findByMainCategory(
                category,
                function(success){ // 가져온 카테고리별 리스트 보여주기
                    vm.itemlist = success.data;
                    console.log(vm.itemlist);
                },
                function(fail){
                    console.log('카테고리 목록 가져오기 실패!');
                }
            )
        }
    },
};
</script>

<style>
@import url('https://fonts.googleapis.com/css2?family=Jua&display=swap');


.searchform label, 
.searchform input {
  color: #737373;
  vertical-align: baseline;
}

.searchform input[type=search] {
  font: 1em/1.618 Open Sans, Arial, Sans-serif;
  border: .125em solid #737373;
  border-width: 0 0 3px;
  background-color: transparent;
  padding: .1875em .375em;
  width: 80%;
}

.searchform input[type=search]:focus {
    border-color: #E18728;
    color: black;
    font-size: 22px;
    font-family: 'Jua', sans-serif;
}

@media only screen and (min-width: 48em) {
  .searchform input[type=search]{ width: 40%; }
}

.text-secondary {
  color: #2c3e50 !important;
  font-size: 25px;
}

.box {
    margin-left: 250px;
    height: 40px;
    width: 440px;
    border: 1px solid #1b5ac2;
    background: #ffffff;
    float: left;
}

input {
    font-size: 20px;
    width: 325px;
    border: 0px;
    outline: none;
    text-align: center;
    font-family: 'Jua', sans-serif;
}

input::placeholder {
    color: black;
    font-size: 22px;
    font-family: 'Jua', sans-serif;
}

#search-box{
    height: 34.6px;
    padding: 3px 6px;
    font-family: 'Jua', sans-serif;
    font-size: 22px;
}
button {
    width: 50px;
    height: 100%;
    border: 0px;
    background: #1b5ac2;
    outline: none;
    float: right;
    color: #ffffff;
}

h1, h2, h3, h4, h5, h6,
.h1, .h2, .h3, .h4, .h5, .h6 {
  margin-bottom: 0.5rem;
  font-family: 'Jua', sans-serif;
  font-weight: 800;
  line-height: 1.2;
  font-size: 2.75rem;
  line-height: 2.75rem;
  letter-spacing: 3px;
}

/* Helper classes */
.basil {
  background-color: #FFFBE6 !important;
}
.basil--text {
  color: #356859 !important;
}
</style>
