<template>
    <div>
        <my-page-nav></my-page-nav>

        <v-row>
            <v-col cols="3" offset="3">
                <v-img
                    :src="`https://picsum.photos/500/300?random`"
                    :lazy-src="`https://picsum.photos/10/6?random`"
                    aspect-ratio="1"
                    class="grey lighten-2">
                </v-img>
                <p class="font-weight-bold">{{userName}}</p>
            </v-col>
            <v-col cols="3" id="profile-info">
                <div>
                    <p>등록 건수 : <span>0</span></p>
                    <p>판매 건수 : <span>0</span></p>
                    <p>구매 건수 : <span>0</span></p>
                </div>
            </v-col>
        </v-row>

        <v-row id="menu-bar">
            <v-col cols="8" offset="2">
                <v-tabs
                color="white"
                background-color="#3949AB"
            >
                <v-tab
                    @click="showDealHistory">
                    거래내역
                </v-tab>
                    <v-tab
                    @click="showRecentlyViewHistory">
                    최근 본 목록
                    </v-tab>
                    <v-tab
                    @click="showBookMarkList">
                    찜한 목록
                    </v-tab>
                    <v-tab
                    @click="showRegistedItemList">
                    등록한 상품
                    </v-tab>
                </v-tabs>
                </v-col>
        </v-row>
        <v-row>
            <v-col class="d-flex child-flex" cols="8" offset="2">
                <v-container v-for="item in items" :key="item.id" >
                    <item-card :item="item" @clicked="onClickItem(item.id)"></item-card>
                </v-container>
                
            </v-col>
        </v-row>
           
    </div>
  
</template>

<script>
import MyPageNav from "./MyPageNav.vue";
import { findById } from "@/api/user.js";
import {loadBookMark} from "@/api/bookmark.js";
import * as itemService from "../../api/item.js";
import ItemCard from "../shop/ItemCard.vue";

export default {
  components: {
    MyPageNav,
    ItemCard
  },
  data() {
    return {
      userId: this.$store.state.user.id,
      userName : {},
      user : {},
      items: []
    };
  },
  created() {
      findById(this.userId, 
        (response) => {
            const data = response.data;
            this.user.name = data["name"];
            this.user.email = data["email"];
            this.userName = data["name"];
      });
  },
  methods: {
      onClickItem(itemId) {
            this.$router.push({path:"item/detail/" + itemId});
        },
      showDealHistory() {
        //   alert("deal history")
        this.items = []
      },
      showRecentlyViewHistory() {
        const bookMark = JSON.parse(sessionStorage.getItem("bookmark"))
        
        if(bookMark != undefined)
            this.items = bookMark
        else
            this.items = []
      },
      showBookMarkList() {
        //   this.items = []
          loadBookMark(this.userId,
            res => {
                this.items = res.data
            },
            error => {
                alert(err)
            }
          )
      },
      showRegistedItemList() {
          itemService.findMySaleItems(this.userId,
            res=> {
                console.dir(res)
                this.items = res.data
            },
            err => {
                alert(err)
            }
          )
      }
  },
  mounted: function() {
      
  }
};
</script>

<style>
p {
    text-align: left;
}

#container {
    margin-bottom: 100px;
}

#profile-info-container {
    /* margin: 50px; */
}

#profile-info {
    align-self: center;
}

#slide-group {
    margin-top: 50px;
}

#menu-bar {
    background-color: #3949AB;
}

p{
    text-align: center;
    color: #283593;
}
</style>
