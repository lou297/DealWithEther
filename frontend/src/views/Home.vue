<template>
    <div>
        <h-nav></h-nav>
        <div style="height:530px; background-color: rgb(255,212,85);">
            <img src="../../public/images/avataaars.svg" alt="" style="width: 15rem; margin: 5rem 0 1rem 0;"><br>
            <h1 style="margin: 2rem 0 1rem 0; color:red; display: inline-block;">중</h1>
            <h1 style="margin: 2rem 0.7rem 1rem 0; display: inline-block;">고마켓에서 </h1>
            <h1 style="margin: 2rem 0 1rem 0; color:red; margin-left:12px; display: inline-block;"> 코</h1>
            <h1 style="margin: 2rem 0 1rem 0; display: inline-block;">인으로 거래하자</h1><br>

            <v-text-field id="search-box" type="search" class="clearfix searchform" placeholder="상품명을 검색해보세요!"
                          v-model="searchKeyword" @keyup.enter="search" style="width: 40%; margin: auto;">
            </v-text-field>
        </div>

        <v-card color="basil">
            <v-card-title class="text-center justify-center py-6">
                <h1 class="font-weight-bold text-secondary">카테고리별 인기상품</h1>
            </v-card-title>
            <v-flex xl1 lg1 md1 sm0 xs0><span></span></v-flex>
            <v-tabs v-model="tab" background-color="transparent" color="basil" grow>
                <v-flex xl1 lg1 md1 sm0 xs0><span></span></v-flex>
                <v-tab v-for="item in items" :key="item" class="font-weight-bold" @click="getCategory(item)" style="font-family: 'Jua', sans-serif; font-size:18px;">{{ item }}</v-tab>
                <v-flex xl1 lg1 md1 sm0 xs0><span></span></v-flex>
            </v-tabs>
            <v-tabs-items v-model="tab">
                <v-tab-item v-for="item in items" :key="item">
                    <v-card color="basil" flat>
                        <v-card-text>
                            <div class="row">
                                <v-flex xl1 lg1 md1 sm0 xs0><span></span></v-flex>
                                <v-flex xs12 sm6 md5 lg2 xl2 class="col-md-3 artwork" v-for="item in itemlist" v-bind:key="item.id">
                                    <item-card :item="item" @clicked="onClickItem(item.id)"></item-card>
                                </v-flex>
                                <v-flex xl1 lg1 md1 sm0 xs0><span></span></v-flex>
                            </div>
                        </v-card-text>
                    </v-card>
                </v-tab-item>
            </v-tabs-items>
        </v-card>
    </div>
</template>

<script>
import HNav from "../components/common/HNav";
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
            searchKeyword: "",
        }
    },
    components: {
        HNav,
        StepFlow,
        ItemCard
    },
    created() {

        console.log('처음시작 ');
        var default_category = "패션";
        console.log(default_category);
        const vm = this;
        findByMainCategory(
            default_category,
            function (success) { // 가져온 카테고리별 리스트 보여주기
                vm.itemlist = success.data;
            },
            function (fail) {
                console.log('카테고리 목록 가져오기 실패!');
            }
        )
        console.log(this.itemlist);
    },
    mounted: function () {


    },
    methods: {
        onClickItem(itemId) {
            this.$router.push("item/detail/" + itemId);
        },
        getCategory(cate) { // 카테고리 누르면 받아오기
            var category = cate.split("/");
            console.log(category[0]);
            const vm = this;
            findByMainCategory(
                category[0],
                function (success) { // 가져온 카테고리별 리스트 보여주기
                    vm.itemlist = success.data;
                },
                function (fail) {
                    console.log('카테고리 목록 가져오기 실패!');
                }
            )
        },
        search() { // shop으로 검색 키워드 넘기기
            var searchKeyword = this.searchKeyword;
            this.$router.push("/shop/" + searchKeyword);
        },
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
    .searchform input[type=search] {
        width: 40%;
    }
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

#search-box {
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
