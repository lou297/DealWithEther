<template>
    <div>
        <!-- <h-nav></h-nav> -->
        <!-- <div style="height:530px; background-color: rgb(253,248,244);">
            <div style="float:left; font-size:40px; ">
             <div style="margin: 13rem 0 1rem 11rem; float:left;">한번의 터치로 쉽고 간편하게</div><br>
             <div style="margin: 13rem 0 1rem 15rem; ">로켓배송 가능한 중코마켓</div><br>
            </div>
            <img src="../../../public/images/hero-img.svg" alt="" style="width: 30rem; float:right; margin: 6rem 5rem 1rem 0rem;"><br>

        </div> -->

        <section class="hero-banner">
      <div class="container">
        <div class="row no-gutters align-items-center pt-60px">
          <div class="col-5 d-none d-sm-block">
            <div class="hero-banner__img">
              <img class="img-fluid" src="../../../public/images/hero-banner.png" alt="">
            </div>
          </div>
          <div class="col-sm-7 col-lg-6 offset-lg-1 pl-4 pl-md-5 pl-lg-0">
            <div class="hero-banner__content">
              <h4>Shop is fun</h4>
              <h1>Browse Our Premium Product</h1>
              <p>Us which over of signs divide dominion deep fill bring they're meat beho upon own earth without morning over third. Their male dry. They are great appear whose land fly grass.</p>
              <a class="button button-hero" href="#">Browse Now</a>
            </div>
          </div>
        </div>
      </div>
    </section>


        <v-card color="basil">
            <v-card-title class="text-center justify-center py-6">
                <h1 class="font-weight-bold text-secondary">카테고리별 상품목록</h1>
            </v-card-title>
            <v-tabs v-model="tab" background-color="transparent" color="basil" grow>
                <v-flex xl1 lg1 md1 sm0 xs0><span></span></v-flex>
                <v-tab class="font-weight-bold" @click="getAllList(); setSearchBy(0);" style="font-family: 'Jua', sans-serif; font-size:18px;">전체 보기</v-tab>
                <v-tab v-for="item in categories" :key="item" class="font-weight-bold" @click="categoryNow = item; getByCategory(item)" style="font-family: 'Jua', sans-serif; font-size:18px;">{{ item }}</v-tab>
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
            <v-container>
                <v-row>
                    <v-flex xs12 sm6 md4 lg3 xl3
                            class="itemCard"
                            v-for="item in items"
                            v-bind:key="item.id">
                        <item-card :item="item" @clicked="onClickItem(item.id)"></item-card>
                    </v-flex>
                </v-row>
            </v-container>
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

import {findAll, findByCategory, findByMainCategory, findByUsername, findByName,findByOnlyName} from "@/api/item.js";
import HShopCategories from "./HShopCategories.vue";
import ItemCard from "./ItemCard.vue";
import {getPrice} from '@/utils/itemInventory.js';
import HNav from "@/components/common/HNav";

export default {
    components: {
        HShopCategories,
        ItemCard,
        HNav,
    },
    data() {
        return {
            items: [],
            newItem: {
                id: 999,
                name : "dummy",
                price: "1",
                registeredAt : "2020-09-29"
            },
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
            categoryNow: "",
            types: [
                {name: '제목', value: 2},
                {name: '판매자', value: 3},
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
            alert(this.searchBy);
            if (this.searchBy === 0) this.getByOnlyName(this.searchKeyword);
            else if (this.searchBy === 1) {
                alert(this.categoryNow);
                this.getByCategory(this.categoryNow);
            }
            else if (this.searchBy === 2) this.getByName(this.categoryNow, this.searchKeyword);
            else if (this.searchBy === 3) this.getBySeller(this.categoryNow, this.searchKeyword);
        },
        onClickItem(itemId) {
            this.$router.push("item/detail/" + itemId);
        },
        getAllList() {
            const vm = this;
            findAll(this.page, function (response) {
                console.log(response.data)
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
        getByName(category, name) {
            const vm = this;
            console.log('오긴옴')
            console.log(category);
            findByName(category.split("/")[0], name, this.page, function (response) {
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
        getByOnlyName(name) {
            console.log('이름으로만 검색하기')
            const vm = this;
            findByOnlyName(name, this.page, function (response) {
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
        getBySeller(category, seller) {
            const vm = this;
             console.log(category+"카테고리")
            console.log(category.split("/")[0]);
            findByUsername(category.split("/")[0], seller, this.page, function (response) {
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
        this.searchBy = 0;
    },

};
</script>

<style>
ul {
    display: flex;
    list-style: none;
}
.itemCard {
    padding : 12px;
}

.hero-banner{background:#F1F6F7;position:relative;margin-bottom:10px}@media (min-width: 991px){.hero-banner{z-index:111}}.hero-banner::before{content:"";display:block;width:5%;height:100%;background:#384aeb;position:absolute;top:0;left:0}@media (min-width: 575px){.hero-banner::before{width:20%}}@media (min-width: 991px){.hero-banner::before{width:35%}}.hero-banner__img{position:relative;top:1px}@media (max-width: 575px){.hero-banner__content{padding-bottom:50px}}@media (min-width: 1000px){.hero-banner__content{max-width:495px}}.hero-banner__content h4{font-size:30px;font-family:"Roboto",sans-serif;color:#555555;margin-bottom:12px}.hero-banner__content h1{font-family:"Roboto",sans-serif;font-size:25px;text-transform:uppercase;font-weight:700;margin-bottom:10px}@media (min-width: 767px){.hero-banner__content h1{font-size:30px;margin-bottom:25px}}@media (min-width: 991px){.hero-banner__content h1{font-size:50px}}.hero-banner__content p{margin-bottom:10px}@media (min-width: 767px){.hero-banner__content p{margin-bottom:40px}}
</style>
