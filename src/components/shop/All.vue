<template>
    <div>
        <h-shop-categories :category="'전체보기'"></h-shop-categories>
        <v-container>
            <ul>
                <li>
                    <v-btn color="orange" text @click="setSearchBy(0)">
                        전체검색
                    </v-btn>
                </li>
                <li>
                    <v-btn color="orange" text @click="setKeyword('가전'); setSearchBy(1)">
                        가전
                    </v-btn>
                </li>
                <li>
                    <v-btn color="orange" text @click="setKeyword('유아'); setSearchBy(1)">
                        유아
                    </v-btn>
                </li>
                <li>
                    <v-btn color="orange" text @click="setKeyword('메롱'); setSearchBy(1)">
                        메롱
                    </v-btn>
                </li>
            </ul>
            <v-spacer></v-spacer>
        </v-container>
        <router-link class="nav-link" to="/item/create">상품 등록</router-link>
        <v-container>
            <v-layout row wrap justify-center>
                <v-flex xs3 sm3 md3 lg3 xl3>
                    <v-select :items="types" label="카테고리" item-text="name" item-value="value" solo style="margin-left:5px" @change="changeSearchBy"></v-select>
                </v-flex>
                <v-flex xs8 sm8 md8 lg8 xl8>
                    <v-text-field
                        id="search"
                        name="search"
                        type="text"
                        color="black"
                        placeholder="검색어를 입력하세요"
                        v-model="searchKeyword"
                        @keyup.enter="search"
                        style="margin-left:5px"
                        solo
                        clearable
                        single-line
                    ></v-text-field>
                </v-flex>
            </v-layout>
        </v-container>
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
                @input="setPage"
            ></v-pagination>
        </div>
    </div>

</template>

<script>
import {findAll, findByCategory, findByName} from "@/api/item.js";
import HShopCategories from "./HShopCategories.vue";
import ItemCard from "./ItemCard.vue";
import {getPrice} from '@/utils/itemInventory.js';

export default {
    components: {
        HShopCategories,
        ItemCard
    },
    data() {
        return {
            items: [],
            circle: false,
            disabled: false,
            length: 30,
            nextIcon: 'navigate_next',
            prevIcon: 'navigate_before',
            page: 1,
            totalVisible: 10,
            searchKeyword: "",
            searchBy: 0,
            types: [
                {name: '카테고리', value: 1},
                {name: '제목', value: 2},
                {name: '판매자', value: 3},
                {name: '임시', value: 4},
            ],
        };
    },
    methods: {
        changeSearchBy(searchBy) {
            console.log(searchBy);
            this.searchBy = searchBy;
        },
        setPage(page) {
            this.page = page;
            console.log(this.page);
            this.search();
        },
        setSearchBy(searchBy) {
            this.searchBy = searchBy;
            console.log(this.searchBy);
            this.page = 1;
            this.search();
        },
        setKeyword(searchKeyword) {
            this.searchKeyword = searchKeyword;
            console.log(this.searchKeyword);
        },
        search() {
            if (this.searchBy === 0) this.getAllList();
            else if (this.searchBy === 1) this.getByCategory(this.searchKeyword);
            else if (this.searchBy === 2) this.getByName(this.searchKeyword);
        },
        onClickItem(itemId) {
            this.$router.push("item/detail/" + itemId);
        },
        getAllList() {
            const vm = this;
            findAll(this.page, function (response) {
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
                },
                err => {
                    alert(err)
                });
        },
        getByCategory(category) {
            const vm = this;
            findByCategory(category, this.page, function (response) {
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
                },
                err => {
                    alert(err)
                });
        },
        getByName(name) {
            const vm = this;
            findByName(name, this.page, function (response) {
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
                },
                err => {
                    alert(err)
                });
        }
    },
    mounted: function () {
        this.getAllList();
    }
};
</script>

<style>
ul {
    display: flex;
    list-style: none;
}
</style>
