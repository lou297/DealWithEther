<template>
    <div>
        <my-page-nav></my-page-nav>

        <v-row>
            <v-col cols="8" offset="2">
                <v-row id="profile-container">
                    <v-col cols="4" id="profile-image-container">
                        <div>
                            <v-avatar size="100px">
                                <v-img
                                    :src="`https://picsum.photos/500/300?random`"
                                    :lazy-src="`https://picsum.photos/10/6?random`"
                                    aspect-ratio="1"
                                    class="grey lighten-2">
                                </v-img>
                            </v-avatar>
                            <p class="font-weight-bold">{{userName}}</p>
                        </div>
                    </v-col>
                    <v-col cols="8" id="profile-info-container">
                        <p>등록 건수 : <span>0</span></p>
                        <p>판매 건수 : <span>0</span></p>
                        <p>구매 건수 : <span>0</span></p>
                    </v-col>
                </v-row> 

                <v-row>
                    <v-tabs color="#E65100" grow background-color="#FFF3E0">
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
                </v-row>
                <v-row>
                        <p>{{size}}건</p>
                        <v-container v-for="item in items" :key="item.id" >
                            <item-card :item="item" @clicked="onClickItem(item.id)"></item-card>
                        </v-container>
                        
                </v-row>
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
      items: [],
      size : 0
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
#profile-container {
    border: 1px solid #263238;
}

#profile-image-container {
    background-color: #FFECB3;
    padding: 50px 0;
}

#profile-info-container {
    font-size: 14px;
    align-self: center;
}

#item-container {
    min-height: 100px;
}
</style>
