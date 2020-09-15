<template>
    <div>
        <h-breadcrumb
            title="프로필 페이지"
            description="사용자 정보를 보여줍니다."
            ></h-breadcrumb>
        <v-container id="container">
            <my-page-nav></my-page-nav>
            <v-row style="justify-content:center;" id="profile-info-container">
                <v-col cols="2" style="align-self: center;">
                    <v-img
                        :src="`https://picsum.photos/500/300?random`"
                        :lazy-src="`https://picsum.photos/10/6?random`"
                        aspect-ratio="1"
                        class="grey lighten-2">
                    </v-img>
                </v-col>
                <v-col cols="4" id="profile-info">
                    <p class="font-weight-bold">{{user.name}}</p>
                    <p>등록 건수 : <span>0</span></p>
                    <p>판매 건수 : <span>0</span></p>
                    <p>구매 건수 : <span>0</span></p>
                </v-col>
            </v-row>
            <v-row>
                <v-tabs
                    fixed-tabs
                    background-color="#82B1FF"
                    color="indigo darken-4"
                >
                    <v-tab>
                    거래내역
                    </v-tab>
                    <v-tab>
                    최근 본 목록
                    </v-tab>
                    <v-tab>
                    찜한 목록
                    </v-tab>
                    <v-tab>
                    등록한 상품
                    </v-tab>
                </v-tabs>
            </v-row>
            <v-sheet
                class="mx-auto"
                >
                <v-slide-group
                    class="pa-4"
                    id="slide-group"
                >
                    <v-slide-item
                    v-for="n in 15"
                    :key="n"
                    v-slot:default="{ active, toggle }"
                    >
                    <v-card
                        :color="active ? 'primary' : 'grey lighten-1'"
                        class="ma-4"
                        height="100"
                        width="100"
                        @click="toggle"
                    >
                        <v-row
                        class="fill-height"
                        align="center"
                        justify="center"
                        >
                        <v-scale-transition>
                            <v-icon
                            v-if="active"
                            color="white"
                            size="48"
                            v-text="'mdi-close-circle-outline'"
                            ></v-icon>
                        </v-scale-transition>
                        </v-row>
                    </v-card>
                    </v-slide-item>
                </v-slide-group>
                </v-sheet>
        </v-container>
    </div>
  
</template>

<script>
import MyPageNav from "./MyPageNav.vue";
import { findById } from "@/api/user.js";

export default {
  components: {
    MyPageNav
  },
  data() {
    return {
      userId: this.$store.state.user.id,
      user : {}
    };
  },
  created: ()=> {
      
  },
  methods: {
      
  },
  mounted: function() {
      findById(this.userId, 
        (response) => {
            const data = response.data;
            this.user.name = data["name"];
            this.user.email = data["email"];
            console.dir(data)
            console.dir(this.user.name)
      });
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
    margin: 50px;
}

#profile-info {
    margin-left: 40px;
    margin-top:15px;
}

#slide-group {
    margin-top: 50px;
}
</style>
