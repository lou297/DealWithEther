<template>
    <v-card
        class="mx-auto"
        max-width="400"
        hover="true"
    >
        <v-img
            class="white--text align-end"
            height="200px"
            :src="imgPath"
        >
        </v-img>
        <v-card-title class="title">{{ item.name }}</v-card-title>
        <v-card-subtitle class="pb-0">{{ item.price }} CASH</v-card-subtitle>

        <v-card-text class="text--primary">
            <div>Whitehaven Beach</div>

            <div>Whitsunday Island, Whitsunday Islands</div>
        </v-card-text>

        <v-card-actions>
            <v-btn
                color="orange"
                text
            >
                Share
            </v-btn>

            <v-btn
                color="orange"
                text
            >
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
export default {
    props: ['item'],
    computed: {
        imgPath() {
            // return process.env.BASE_URL + 'images/' + this.item.image;
            return "https://picsum.photos/id/11/100/60";
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
