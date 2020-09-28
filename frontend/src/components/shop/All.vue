<template>
    <div>
        <h-nav></h-nav>
        <v-card color="basil">
            <v-tabs v-model="tab" background-color="transparent" color="basil" grow>
                <v-flex xl1 lg1 md1 sm0 xs0><span></span></v-flex>
                <v-tab class="font-weight-bold" @click="setSearchBy(0)" style="font-family: 'Jua', sans-serif; font-size:18px;">전체 보기</v-tab>
                <v-tab v-for="item in categories" :key="item" class="font-weight-bold" @click="setKeyword(item); setSearchBy(1)" style="font-family: 'Jua', sans-serif; font-size:18px;">{{ item }}</v-tab>
                <v-flex xl1 lg1 md1 sm0 xs0><span></span></v-flex>
            </v-tabs>
            <v-container>
                <v-layout row wrap justify-center>
                    <v-flex xs3 sm3 md3 lg3 xl3>
                        <v-select :items="types" label="제목" item-text="name" item-value="value" solo style="margin-left:5px" @change="changeSearchBy"></v-select>
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
                <v-flex xl1 lg1 md1 sm0 xs0><span></span></v-flex>
                <v-flex xs12 sm6 md6 lg2 xl2
                        class="col-md-3 artwork"
                        v-for="item in items"
                        v-bind:key="item.id">
                    <item-card :item="item" @clicked="onClickItem(item.id)"></item-card>
                </v-flex>
                <v-flex xl1 lg1 md1 sm0 xs0><span></span></v-flex>
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
        </v-card>
    </div>

</template>

<script>
import {findAll, findByCategory, findByMainCategory, findByUsername, findByName} from "@/api/item.js";
import HShopCategories from "./HShopCategories.vue";
import ItemCard from "./ItemCard.vue";
import {getPrice} from '@/utils/itemInventory.js';
import HNav from "@/components/common/HNav copy";

export default {
    components: {
        HShopCategories,
        ItemCard,
        HNav,
    },
    data() {
        return {
            items: [],
            categories: ['패션/잡화',
                '뷰티/미용',
                '디지털/가전',
                '유아물품',
                '도서/티켓',
                '스포츠/레저',
                '생활/문구/가구',
                '기타',],
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
            route: 0,
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
            else if (this.searchBy === 3) this.getBySeller(this.searchKeyword);
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
                    } else {
                        vm.items = response.data;
                    }
                },
                err => {
                    alert(err)
                });
        },
        getByCategory(category) {
            const vm = this;
            findByCategory(category.split("/")[0], this.page, function (response) {
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
                    } else {
                        vm.items = response.data;
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
                    } else {
                        vm.items = response.data;
                    }
                },
                err => {
                    alert(err)
                });
        },
        getBySeller(seller) {
            const vm = this;
            findByUsername(seller, this.page, function (response) {
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
                    } else {
                        vm.items = response.data;
                    }
                },
                err => {
                    alert(err)
                });
        }
    },
    mounted: function () {
        if(this.route != 1) this.getAllList(); // 상점 눌러서 들어왔을 때
    },
    created() {
        this.searchKeyword = this.$route.params.searchKeyword;
        if(this.searchKeyword.length != 0) {
            this.searchBy = 2; // 제목으로 검색
            this.search();
            this.route = 1;
        }
        this.searchBy = 2;
    },

};
</script>

<style>
ul {
    display: flex;
    list-style: none;
}
</style>
