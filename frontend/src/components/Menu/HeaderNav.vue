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
      <router-link v-if="notiImg" to="/myalerts"><img src="@/assets/noti_green.png" /></router-link>
      <router-link v-else to="/myalerts"><img src="@/assets/noti.png" /></router-link>
    </div>
  </div>
</template>

<script>
import axios from "axios";
// import { mapActions } from 'vuex'
import { mapState } from 'vuex'

export default {
  data() {
    return {
      path: this.$route.path,
      notiImg: null,
      myProfileNumber: null,
    };
  },
  created() {
    this.notiImg = this.path.includes("myalerts")
      // ? require("@/assets/noti_green.png")
      // : require("@/assets/noti.png");
    axios
      .get("user/my")
      .then((res) => {
        this.myProfileNumber = res.data["userNumber"];
      })
      .catch((err) => {
        console.error(err);
      })
    ,
    // console.log(this.notiImg)
    this.$store.dispatch('getSearchNotice')
    for (var item of this.$store.state.searchNotices) {
      if (!item.isRead) {
        this.notiImg = true
        break;
      }
      else {
        this.notiImg = false
      }
    }
  },
  computed: {
    flag() {
      if(this.$route.path.includes("mycrop/detail")) return false
      else return true
    },
    ...mapState([
      'searchNotices',
      // 'noticeIsreads',
    ])
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
