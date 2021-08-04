<template>
  <div class="nav">
      <div id="left">
      <router-link to="/main"><img src="@/assets/mococo.png"/></router-link>
      </div>
      <div id="right">
      <router-link :to="'/profile/' + this.myProfileNumber"><img alt="프로필" /></router-link>
      <router-link to="/myalerts"><img src="@/assets/noti.png" /></router-link>
      </div>
  </div>
</template>

<script>

export default {
  data() {
    return {
      // path: this.$route.path,
      // notiImg: "",
      myProfileNumber: this.$store.state.myNumber
    }
  },
  created() {
    // this.notiImg = (this.path.include("myalert"))? require("@/assets/noti_green.png"): require("@/assets/noti.png");
  },
};
</script>

<style scoped>
@media (max-width: 600px) {
  .nav {
    border-bottom: 2px solid #b6c790;
    display: inline-block;
  }
  #left img {
    float: left;
  }
  #right img {
      float: right;
      right: 0;
      display: inline-block;
  }
  img {
    width: 15%;
    margin: 5px;
    padding: 5px;
  }
}
</style>
