<template>
    
</template>
<script>
export default {
  data() {
    return {
      hasWallet: this.$store.state.user.walletAddress != null,
      viewName: ""
    };
  },
  mounted: function() {
    this.viewName = this.$route.name;
  }
};
</script>

<style></style>
