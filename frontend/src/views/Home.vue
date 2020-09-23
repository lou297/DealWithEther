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
                        <div style="float:left; padding:0 0 0 15px;">
                            <router-link class="nav-link" to="/sell">판매하기</router-link>
                            <!-- <a href='../components/explorer/test.vue'>판매하기</a> -->
                        </div>
                        <div style="float:left; padding:8px 0 0 15px;">
                            내상점
                        </div>
                        <div style="float:left; padding:8px 0 0 15px;">
                            문의톡해주세용
                        </div>
                        <div style="clear:both;"></div>
                    </div>
                    <br>
                    <v-footer padless>
                        <v-row justify="center" no-gutters>
                            <v-btn
                                v-for="link in links"
                                :key="link"
                                color="black"
                                text
                                rounded
                                class="my-2"
                                style="margin-left: 20px; margin-right: 20px;"
                            >
                                {{ link }}
                            </v-btn>
                        </v-row>
                    </v-footer>

                    <v-carousel hide-delimiters style="margin-top: 10px; height: 300px; padding:15px 15px 0 15px;">
                        <v-carousel-item
                            v-for="(item,i) in items2"
                            :key="i"
                        >
                            <img :src="item.src" style="height:57%; width:100%">
                        </v-carousel-item>
                    </v-carousel>
                    <br>

                    <div class="row">
                        <v-flex xs12 sm12 md6 lg4 xl3
                                class="col-md-3 artwork"
                                v-for="item in items"
                                v-bind:key="item.id">
                            <item-card :item="item" @clicked="onClickItem(item.id)"></item-card>
                        </v-flex>
                    </div>
                    <div class="text-center">
                        <v-pagination
                            v-model="page"
                            :circle="circle"
                            :disabled="disabled"
                            :length="length"
                            :next-icon="nextIcon"
                            :prev-icon="prevIcon"
                            :page="page"
                            :total-visible="totalVisible"
                        ></v-pagination>
                    </div>

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
import {mdiAccount} from '@mdi/js';
import {findAll} from "@/api/item";
import {getPrice} from "@/utils/itemInventory";
import ItemCard from "@/components/shop/ItemCard";

export default {
    data() {
        return {
            items2: [
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
            items: [],
            circle: false,
            disabled: false,
            length: 30,
            nextIcon: 'navigate_next',
            prevIcon: 'navigate_before',
            page: 1,
            totalVisible: 10,
            links: [
                '패션/잡화',
                '뷰티/미용',
                '디지털/가전',
                '유아물품',
                '도서/티켓',
                '스포츠/레저',
                '생활/문구/가구',
                '기타',
            ],
        }
    },
    components: {
        HNav,
        StepFlow,
        ItemCard
    },
    mounted: function () {
        const vm = this;

        findAll(function (response) {
            if (response.data.length > 0) {
                vm.items = response.data;
                vm.items.forEach(i => {
                    // [스마트 컨트랙트] 가격 조회
                    getPrice(
                        i.id,
                        function (price) {
                            vm.$set(i, "price", price);
                        },
                        function (err) {
                            console.error('가격 조회 실패:', err);
                            // alert("상품 가격 조회를 실패했습니다.");
                        }
                    )
                })
            }
        });
    },
    methods: {
        onClickItem(itemId) {
            this.$router.push("item/detail/" + itemId);
        }
    },
};
</script>

<style>

.box {
    margin-left: 250px;
    height: 40px;
    width: 440px;
    border: 1px solid #1b5ac2;
    background: #ffffff;
    float: left;
}

input {
    font-size: 16px;
    width: 325px;
    padding: 10px;
    border: 0px;
    outline: none;
    float: left;
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
</style>
