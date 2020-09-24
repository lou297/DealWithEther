<template>
    <v-card
        class="mx-auto"
        max-width="400"
    >
        <v-img
            class="white--text align-end"
            height="200px"
            :src="imgPath"
            @click.prevent="$emit('clicked')" href=""
        >
        </v-img>
        <v-card-title class="title">{{ item.name }}</v-card-title>
        <v-card-subtitle class="pb-0">{{ item.price }} CASH</v-card-subtitle>
        <v-card-actions>
            <v-btn color="orange" text>
                Share
            </v-btn>

            <v-btn color="orange" text>
                Explore
            </v-btn>
            <v-spacer></v-spacer>
            <v-btn icon>
                <v-icon>mdi-heart</v-icon>
            </v-btn>
            <v-btn icon>
                <v-icon>mdi-share-variant</v-icon>
            </v-btn>
        </v-card-actions>
    </v-card>
</template>

<script>
import {download as downloadImage} from "@/api/item";

export default {
    props: ['item'],
    computed: {
        imgPath() {
            return downloadImage(this.item.id, console.log('success'), console.log('fail'));
            // return "https://picsum.photos/id/11/100/60";
        }
    }
};
</script>

<style>
.item-card img {
    height: 180px;
    width: 180px;
}

.item-card:hover {
    text-decoration: underline !important;
    border: 1.2px solid #0070bc;
}

</style>
