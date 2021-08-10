<template>
  <div class="nav" v-show="this.flag">
    <div id="left">
      <router-link to="/"><img src="@/assets/mococo.png" /></router-link>
    </div>
    <div id="right">
      <div v-if="!myProfileNumber">
        <router-link to="/login"
          ><img src="@/assets/login.png" alt="로그인"
        /></router-link>
      </div>
      <div v-else>
        <router-link :to="'/profile/' + this.myProfileNumber"
          ><img src="@/assets/profile_sample.png" alt="프로필"
        /></router-link>
      </div>
      <router-link to="/myalerts"><img :src="notiImg" /></router-link>
    </div>
  </div>
</template>

<script>
import axios from "axios";

export default {
  data() {
    return {
      path: this.$route.path,
      notiImg: require("@/assets/noti.png"),
      myProfileNumber: null,
    };
  },
  created() {
    this.notiImg = this.path.includes("myalerts")
      ? require("@/assets/noti_green.png")
      : require("@/assets/noti.png");
    axios
      .get("user/my")
      .then((res) => {
        this.myProfileNumber = res.data["userNumber"];
      })
      .catch((err) => {
        console.error(err);
      });
  },
  computed: {
    flag() {
      if(this.$route.path.includes("mycrop/detail")) return false
      else return true
    }
  }
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
@media (min-width: 601px) {
  .nav {
    height: 80px;
    border-bottom: 2px solid #b6c790;
    display: inline-block;
  }
  img {
    width: 55px;
    margin: 5px;
    padding: 5px;
  }
}
</style>
