<template>
    <div>
        <h-breadcrumb title="상품 등록" style="margin:0px; padding-top:0px;"></h-breadcrumb>

        <div id="main-overview" class="container" >
            <v-row>
                <v-col cols="6">
<!--                    <img :src="defaultImage" v-if="isImageUpload == false" style="width:90%; margin-top: 15px;">-->
<!--                    <img :src="item.image" v-if="isImageUpload == true" style="height:250px; width:390px; margin-top: 15px;" ><br><br>-->
                    <v-card>
                        <v-container fluid>
                            <v-row>
                                <v-col
                                    v-for="n in 9"
                                    :key="n"
                                    class="d-flex child-flex"
                                    cols="4"
                                >
                                    <v-card flat tile class="d-flex">
                                        <v-img
                                            :src="item.image[n-1]"
                                            :lazy-src="item.image[n-1]"
                                            aspect-ratio="1"
                                            class="grey lighten-2"
                                            @click="removeImage(n-1)"
                                        >
                                        </v-img>
                                    </v-card>
                                </v-col>
                            </v-row>
                        </v-container>
                    </v-card>
                    <div class="form-group">
<!--                        <input id="upload" type="file" class="form-control" style="height: auto; margin-left:18px; width:92%;" @change="onFileChange" />-->
                        <v-file-input multiple label="File input" @change="onFileChange" ></v-file-input>
                    </div>
                </v-col>
                <v-col cols="6">
                    <v-form ref="form" v-model="valid" lazy-validation>
                        <v-text-field v-model="item.name" :counter="10" label="상품 이름" required></v-text-field>
                        <v-select v-model="item.category" :items="items" :rules="[v => !!v || 'Item is required']" label="카테고리" required></v-select>
                        <v-text-field type="number" v-model="item.price" label="가격" required></v-text-field>
                        <v-text-field v-model="item.explanation" label="설명" required></v-text-field>
                        <v-checkbox v-model="item.directDeal" label="직거래 여부" required></v-checkbox>
                        <v-text-field v-if="item.directDeal == true" 
                            v-model="item.dealRegion" label="장소" required></v-text-field>

                        <v-btn color="success" class="mr-4" @click="save">취소</v-btn>
                        <v-btn color="error" class="mr-4" @click="save">등록</v-btn>
                    </v-form>
                </v-col>
            </v-row>
        </div><br><br><br>

        <!-- <v-container>
            <div row justify-space-around class="backImg">
                <v-layout row wrap>
                    <v-flex xl6 lg6 md12 sm12 xs12>
                        <img :src="item.image"><br>
                        <div class="form-group">
                            <input id="upload" type="file" class="form-control" style="height: auto;" @change="onFileChange" />
                        </div>
                    </v-flex>
                    <v-flex xl6 lg6 md12 sm12 xs12>
                        <div class="container">
                            <div class="row">
                                <div class="col-md-12 mx-auto">
                                    <div class="card">
                                        <div class="card-body">
                                            <div class="form-group">
                                                <label id="name">상품 이름</label>
                                                <input type="text" class="form-control" id="name" v-model="item.name" />
                                            </div>
                                            <div class="form-group">
                                                <label id="name">카테고리</label>
                                                <select class="form-control" id="category" v-model="item.category">
                                                    <option value="D">디지털/가전</option>
                                                    <option value="C">유아물품</option>
                                                    <option value="H">게임/취미</option>
                                                    <option value="A">의류</option>
                                                </select>
                                            </div>
                                            <div class="form-group">
                                                <label id="name">직거래 여부</label>
                                                <input type="checkbox" class="form-control" id="check" v-model="item.directDeal"/>
                                            </div>
                                            <div class="form-group" v-if="item.directDeal == true">
                                                <label id="name">직거래 장소</label>
                                                <input type="text" class="form-control" id="name" v-model="item.dealRegion"/>
                                            </div>

                                            <div class="form-group">
                                                <label id="price">가격</label>
                                                <input type="number" class="form-control" id="price" v-model="item.price" />
                                            </div>
                                            <div class="form-group">
                                                <label id="description">상품 설명</label>
                                                <textarea class="form-control" id="description" v-model="item.explanation" placeholder=""></textarea>
                                            </div>
                                            <div class="form-group">
                                                <label id="privateKey">지갑 개인키</label>
                                                <input id="privateKey" v-model="privateKey" type="text" class="form-control" placeholder="지갑 개인키를 입력해주세요." />
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </v-flex>
                </v-layout>
                <v-layout row wrap>
                    <v-flex>
                        <button type="button" class="btn btn-primary" v-on:click="save" v-bind:disabled="isCreating">
                            채팅
                        </button>
                    </v-flex>
                    <v-flex>
                        <button type="button" class="btn btn-primary" v-on:click="save" v-bind:disabled="isCreating">
                            상품 등록
                        </button>
                    </v-flex>
                    <v-flex>
                        <button type="button" class="btn btn-primary" v-on:click="save" v-bind:disabled="isCreating">
                            등록 취소
                        </button>
                    </v-flex>
                </v-layout>
            </div>
        </v-container> -->
    </div>
</template>

<script>
import { create as createItem } from "@/api/item.js";
import { save as savaImage } from "@/api/item.js";
import { registerItem } from "@/utils/itemInventory.js";

export default {
    name: "ItemCreate",
    data() {
        return {
            files:[],
            item: {
                name: "",
                category: "",
                explanation: "",
                available: true,
                seller: this.$store.state.user.id,
                image: [],
                directDeal: false,
                dealRegion: "",
                price: null,
            },
            imageURL: "",
            privateKey: "",
            userId: this.$store.state.user.id,
            isCreating: false,
            defaultImage: require('../../public/images/no_image.png'),
            isImageUpload: false,
            items: [
                '여성의류',
                '남성의류',
                '생활/문구',
                '디지털/가전',
                '패션잡화',
            ],
        };
    },
    computed: {
        /**
         * public/images에 있는 로컬 이미지를 첨부해서 사용합니다.
         * DB에는 이미지 파일 이름만 저장되고
         * 화면에 보여줄 땐 'public/images/{파일이름}' 경로를 사용합니다.
         */
        imgLocalPath() {
            if (this.item.imgName) {
                return process.env.BASE_URL + "images/" + this.item.imgName;
            }

            return null;
        },
    },
    methods: {
        // 상품을 등록한다.
        save() {
            const vm = this;
            this.isCreating = true; // 아이템 등록 중임을 화면에 표시, 등록이 끝나면 false로 변경
            if (this.item.name.length <= 0 || this.item.category.length <= 0 || this.item.price === null || this.item.price <= 0 || this.item.image === null || this.item.image.length <= 0) {
                alert("입력폼을 모두 입력해주세요.");
                this.isCreating = false;
                return;
            }

            const data = new FormData();
            const file = this.files;
            data.append("file",file);

            savaImage( // 사진 등록
                data,
                function(success) {
                    alert("이미지 등록 성공!");
                },
                function(error) {
                    console.log(error);
                },
                function(final){
                    console.log('안녕');
                }
            );
            createItem( // 상품 등록 - 백앤드
                this.item,
                function(success) {
                    alert("상품 등록 성공!");
                },
                function(error) {
                    console.log(error);
                },
                function(final){
                    console.log('안녕');
                }
            );

            /**
             * TODO: PJTⅢ 과제3 Req.1-1 [상품 등록]
             * DB에 상품 등록 후 반환 받은 id를 이용해서 이더리움에 상품을 등록
             */
        },

        onFileChange(input) {
            if(input.length === 0) {
                console.log("00000000000")
                return;
            }
            for (let i = 0; i < input.length; i++) {
                const file = input.__ob__.value[i];
                this.item.image.push(URL.createObjectURL(file));
                this.files.push(file);
            }
            console.log(this.item.image)

            // var files = input.target.files || input.dataTransfer.files;
            // if (!files.length) return;
            //
            // this.isImageUpload = true;
            // const file = input.target.files[0];
            // this.item.image = URL.createObjectURL(file);
            // console.log(URL.createObjectURL(file));
            // var reader = new FileReader();
            // reader.onload = (e) => {
            //     this.imageURL = e.target.result;
            // }
        },

        removeImage(n){
            alert(n);
            // this.item.image[n].removeImage();
            this.item.image.splice(n, 1);
        }
    },
};
</script>

<style>
.image-area {
    border: 2px dashed #ced4da;
    padding: 1rem;
    position: relative;
}

.image-area::before {
    content: "업로드 된 이미지";
    font-weight: bold;
    text-transform: uppercase;
    position: absolute;
    top: 50%;
    left: 50%;
    transform: translate(-50%, -50%);
    font-size: 0.8rem;
    z-index: 1;
}

.image-area img {
    z-index: 2;
    position: relative;
}

.profile {
    display: flex;
    justify-content: center;
    /* min-width: 100px; */
    size: 200px;
}

.backImg {
    width: 80%;
    margin: auto;
    border-radius: 2rem;
    background-size: 152vh;
    padding: 20px;
    box-shadow: 3px 3px 5px 1px #595959;
}
</style>
