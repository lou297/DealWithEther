<template>
    <div>
        <h-nav></h-nav>
        <div style="height:100px; background-color: rgb(255,212,85);">
            <h1 style="margin: 2rem 0.7rem 1rem 2rem; display: inline-block;">거래 결과</h1>
        </div>
        <v-card color="basil">
            <div class="row">
                <v-flex xl1 lg1 md1 sm0 xs0><span></span></v-flex>
                <v-card>
                    <v-list three-line xl10 lg10 md10 sm12 xs12>
                        <v-list-item-group v-for="(item, index) in items" :key="item.itemId">
                            <v-divider
                                v-if="index !== 0"
                            ></v-divider>
                            <v-list-item>
                                <v-list-item-avatar >
                                    <v-img :src="imgPath(item.itemId)"></v-img>
                                </v-list-item-avatar>
                            </v-list-item>
                            <sell-list-card :item="item"></sell-list-card>
                        </v-list-item-group>
                    </v-list>
                </v-card>
                <v-flex xl1 lg1 md1 sm0 xs0><span></span></v-flex>
            </div>
        </v-card>
    </div>

</template>

<script>
import HNav from "@/components/common/HNav copy";
import {findMySalePurchases} from "@/api/purchase";
import {findById} from "@/api/item"
import SellListCard from "@/components/mypage/SellListCard";

export default {
    components: {
        SellListCard,
        HNav,
    },
    data() {
        return {
            items: [],
            userId: this.$store.state.user.id,
            name: [],
        };
    },
    computed: {
        function(){
            console.log(this.items.length);
            for (let i = 0; i <this.items.length; i++) {
                const temp = this.getItemName(this.items[i].itemId);
                this.name.push(temp);
                console.log(temp)
            }
        }
    },
    methods: {
        imgPath(id) {
            console.log(process.env.VUE_APP_BACKEND)
            return process.env.VUE_APP_BACKEND + 'api/items/images/' + id + "_1";
        },
        getPurchaseHistory() {
            findMySalePurchases(this.userId, (res) => {
                if (res) {
                    console.log(res);
                    this.items = res.data;
                } else {
                    alert("개인키 인증에 실패하였습니다.");
                }
            });
        },
    },
    mounted: function () {

    },
    created() {
        this.getPurchaseHistory();
        for (let i = 0; i <this.items.length; i++) {
            const temp = this.getItemName(this.items[i].itemId);
            this.name.push(temp);
            console.log(temp)
        }
    },

};
</script>

<style>
ul {
    display: flex;
    list-style: none;
}
</style>
