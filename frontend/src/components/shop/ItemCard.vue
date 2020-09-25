<template>
    <v-hover v-slot:default="{ hover }">
        <v-card
            class="item-card mx-auto"
            max-width="250"
            max-height="400"
            :elevation="hover ? 16 : 2"
            @click.prevent="$emit('clicked')" href=""
        >
            <v-img class="white--text align-end" height="250" :src="imgPath">
                <v-btn
                    color="red accent-4"
                    icon
                    v-if="checkLiked()"
                    @click.stop="changeLiked()"
                >
                    <v-icon middle color="red accent-4" icon>mdi-heart</v-icon>
                </v-btn>
                <v-btn color="red accent-4" icon v-else @click.stop="!changeLiked()">
                    <v-icon middle color="red accent-4">mdi-heart-outline</v-icon>
                </v-btn>
            </v-img>
            <v-card-title style="font-size:18px;">{{ item.name }}</v-card-title>
            <v-card-actions style="padding:16px 0 0 16px; color:black; float:left; font-weight:bold; font-size:15px;">{{ item.price }} CASH</v-card-actions>
            <v-card-actions style="padding:16px 16px 0 0; color:black; float:right; font-size:14px;">{{ item.registeredAt }}</v-card-actions>
            <v-card-actions style="clear:both;"></v-card-actions>
        </v-card>
    </v-hover>
</template>

<script>
import {findLikedList} from "@/api/item";
import {deleteBookMark} from "@/api/bookmark";
import {getPrice} from "@/utils/itemInventory";

export default {
    props: ['item'],
    computed: {
        imgPath() {
            console.log(process.env.VUE_APP_BACKEND)
            return process.env.VUE_APP_BACKEND + 'api/items/images/' + this.item.id + "_1";
            // return "https://picsum.photos/id/11/100/60";
        }
    },
    data() {
        return {
            userLiked: [],
        }
    },
    created() {
        console.log(this.item);
        var time = this.item.registeredAt.split("T");
        this.item.registeredAt = time[0];
        this.getLikedList();
    },
    methods: {
        getLikedList() {
            const vm = this;
            findLikedList(this.$store.state.user.id, function (response) {
                    if (response.data.length > 0) {
                        vm.userLiked = response;
                        console.log(vm.userLiked.data);
                    }
                },
                err => {
                    alert(err)
                }
            );
        },
        checkLiked() {
            if (this.$store.state.isSigned !== null)
                return this.userLiked.data.includes(this.item.id);
            else return false;
        },
        changeLiked() {
            const vm = this;
            deleteBookMark(this.$store.state.user.id, this.item.id, function (response) {
                    if (response.data.length > 0) {
                    }
                },
                err => {
                    alert(err)
                }
            );
        }
    }
};
</script>

<style>
.item-card img {
    height: 180px;
    width: 180px;
}

</style>
