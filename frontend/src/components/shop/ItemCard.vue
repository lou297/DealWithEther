<template>
    <v-hover v-slot:default="{ hover }">
        <v-card
            class="item-card mx-auto"
            max-width="250"
            max-height="400"
            :elevation="hover ? 16 : 2"
            @click.prevent="$emit('clicked')" href=""
        >
            <v-img class="white--text align-end" height="250" :src="imgPath"></v-img>
            <v-card-title style="font-size:18px;">{{ item.name }}</v-card-title>
            <v-card-actions style="padding:16px 0 0 16px; color:black; float:left; font-weight:bold; font-size:15px;">{{ item.price }} CASH</v-card-actions>
            <v-card-actions style="padding:16px 16px 0 0; color:black; float:right; font-size:14px;">{{ item.registeredAt }}</v-card-actions>
            <v-card-actions style="clear:both;"></v-card-actions>
        </v-card>
    </v-hover>
</template>

<script>
export default {
    props: ['item'],
    computed: {
        imgPath() {
            console.log(process.env.VUE_APP_BACKEND)
            return process.env.VUE_APP_BACKEND + 'api/items/images/' + this.item.id + "_1";
            // return "https://picsum.photos/id/11/100/60";
        }
    },
    created(){
        console.log(this.item);
        var time = this.item.registeredAt.split("T");
        this.item.registeredAt = time[0];
    }
};
</script>

<style>
.item-card img {
    height: 180px;
    width: 180px;
}

</style>
