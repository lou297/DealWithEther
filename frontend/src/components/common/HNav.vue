<template>
    <v-card
        tile
    >  
        <v-app-bar color="#242526" dark>
        <v-toolbar-title><router-link class="navbar-brand" to="/" id="title">중코마켓</router-link></v-toolbar-title>

        <v-spacer></v-spacer>
        <router-link id="link-to-shop" to="/shop">상점</router-link>
        <router-link id="link-to-sell" to="/sell">판매하기</router-link>
        <router-link id="link-to-mypage" to="/mypage/wallet_create" v-if="!$store.state.user.walletAddress">마이페이지</router-link>
        <router-link id="link-to-myshop" to="/mypage/wallet_info" v-if="$store.state.user.walletAddress">내상점</router-link>
        <router-link id="link-to-logout" to="/logout"
         v-if="$store.state.isSigned">로그아웃</router-link>
        <a id="link-to-signup"
            v-if="!$store.state.isSigned"
            @click.stop="signup = true">회원가입</a>
        <a id="link-to-login"
            v-if="!$store.state.isSigned"
            @click.stop="signin = true">로그인</a>

        <v-dialog v-model="signup" max-width="500" min-width="300">
                            <Signup></Signup>
                        </v-dialog>
        <v-dialog v-model="signin" max-width="500" min-width="300">
                            <Login></Login>
                        </v-dialog>

        </v-app-bar>
    </v-card>
</template>

<script>
import Login from "@/views/Login"
import Signup from "@/views/Signup";

export default {
    data() {
        return {
            signin: false,
            signup: false,
        }
    },
    components: {
        Login,
        Signup
    },
    created () {
        console.dir("로그인?")
        console.dir(this.$store.state)
    }
};
</script>

<style>
#title{
    color: white;
    margin-left: 60px;
}

#link-to-shop, #link-to-sell, #link-to-mypage, #link-to-myshop
, #link-to-logout, #link-to-signup, #link-to-login {
    color: white;
    margin: 0 10px;
}
</style>
