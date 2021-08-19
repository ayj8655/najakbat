<template>
  <div class="nav" v-show="this.flag">
    <div id="left">
      <router-link to="/"><img id="icon" src="@/assets/mococo.png" /></router-link>
    </div>
    <div id="right">
      <div v-if="!isLogin">
          <router-link to="/login"
            ><img id="icon" src="@/assets/Login2.png" alt="로그인" class="w-15"
          /></router-link>
      </div>
      <div v-else>
        <router-link :to="'/profile/' + this.$store.state.userNumber"
          >
          <div v-if="!profilePhoto"><img id="icon" src="@/assets/profile_sample.png" alt="프로필"/></div>
          <div class="box" v-else>
              <img id="profile-pic" :src="profilePhoto" alt="">
          </div>
        </router-link>
        <router-link v-if="notiImg" to="/myalerts">
          <img id="icon" v-if="isAlert" src="@/assets/noti_green.png" />
          <img id="icon" v-else src="@/assets/noti_new.png"/>
          </router-link>
        <router-link v-else to="/myalerts"><img id="icon" src="@/assets/noti.png" /></router-link>
      </div>
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
      isAlert: false,
      isLogin: this.$store.state.accessToken,
      profilePhoto: null,
    };
  },
  created() {
      if (this.$route.name === "MyAlerts") {
        this.isAlert = true
      }
      else {
        this.isAlert = false
      };
    this.notiImg = this.path.includes("myalerts");
      // ? require("@/assets/noti_green.png")
      // : require("@/assets/noti.png");
    if (this.$store.state.accessToken) {
      axios
        .get(`user/${this.$store.state.userNumber}`)
        .then((res) => {
          this.myProfileNumber = res.data.userNumber;
          // console.log(res);
          if(res.data.profilePhoto) {
            this.profilePhoto = "https://mococobucket.s3.ap-northeast-2.amazonaws.com/profile/" + res.data.profilePhoto.saveFile;
          }
        })
        .catch((err) => {
          console.error(err);
        });

      // console.log(this.notiImg)
      this.$store.dispatch('getSearchNotice');
      for (var item of this.$store.state.searchNotices) {
        if (!item.isRead) {
          this.notiImg = true
          break;
        }
        else {
          this.notiImg = false
        }
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
  #icon {
    width: 15%;
    margin: 5px;
    padding: 5px;
  }
  .box {
    width: 60px;
    height: 60px;
    border: 1px solid #a9a9a9;
    border-radius: 70%;
    overflow: hidden;
    display: inline-block;
    float: right;
    right: 0;
    margin: 5px;
}
  .box img {
    width:100%;
    min-height: 100%;
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

.log-btn {
  width: 25%;
}
</style>
