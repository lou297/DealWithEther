<template>
    <div>
        <h-shop-categories :category="'전체보기'"></h-shop-categories>
        <v-container>
            <v-layout row wrap justify-center>
                <v-flex xs3 sm3 md3 lg3 xl3>
                    <v-select :items="types" label="카테고리" solo v-model="typeBox" style="margin-left:5px"></v-select>
                </v-flex>
                <v-flex xs8 sm8 md8 lg8 xl8>
                    <v-text-field
                        id="search"
                        name="search"
                        type="text"
                        color="black"
                        placeholder="검색어를 입력하세요"
                        v-model="search"
                        @keyup.enter="searchRoute"
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
            ></v-pagination>
        </div>
    </div>

</template>

<script>
import {findAll} from "@/api/item.js";
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
        };
    },
    methods: {
        onClickItem(itemId) {
            this.$router.push("item/detail/" + itemId);
        }
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
    }
};
</script>

<style></style>
