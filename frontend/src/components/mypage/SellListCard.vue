<template>
    <v-list-item-content xl10 lg10 md10 sm12 xs12>
        <v-list-item-title>{{name}}</v-list-item-title>
        <v-list-item-subtitle>현재 결제 진행중입니다</v-list-item-subtitle>
        <v-spacer></v-spacer>
    </v-list-item-content>
</template>

<script>
import {findById, findLikedList} from "@/api/item";
import {deleteBookMark, bookMarkSave} from "@/api/bookmark";
import {getPrice} from "@/utils/itemInventory";

export default {
    props: ['item'],
    computed: {},
    data() {
        return {
            name: "",
        }
    },
    created() {
        console.log(this.item);
        this.getItemName(this.item.itemId)
    },
    methods: {
        getItemName(itemId){
            const vm = this;
            findById(itemId, (res) => {
                if (res) {
                    vm.name = res.data.name;
                    console.log(vm.name)
                } else {
                    alert("개인키 인증에 실패하였습니다.");
                }
            });
        },
    }
};
</script>

<style>
.item-card img {
    height: 180px;
    width: 180px;
}

</style>
