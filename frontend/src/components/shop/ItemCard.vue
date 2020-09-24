<template>
    <v-hover v-slot:default="{ hover }">
        <v-card
            class="item-card mx-auto"
            max-width="400"
            max-height="400"
            :elevation="hover ? 16 : 2"
            @click.prevent="$emit('clicked')" href=""
        >
            <v-img
                class="white--text align-end"
                min-height="250"
                :src="imgPath"
            >
            </v-img>
            <v-card-title>{{ item.name }}</v-card-title>
            <v-card-subtitle align="left">{{ item.price }} CASH</v-card-subtitle>
            <v-card-actions>
                <v-spacer></v-spacer>
                <v-btn
                    color="red accent-4"
                    icon
                    @click.stop="alert('click')"
                >
                    <v-icon middle color="red accent-4" icon>mdi-heart</v-icon>
                </v-btn>
                <v-btn
                    icon
                    @click.stop="alert('click')"
                >
                    <v-icon>mdi-share-variant</v-icon>
                </v-btn>
            </v-card-actions>
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
    }
};
</script>

<style>
.item-card img {
    height: 180px;
    width: 180px;
}

</style>
