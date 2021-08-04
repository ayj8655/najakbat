<template>
  <div>
    <header-nav></header-nav>
    <div v-if="getNickname" class="container">
      <div class="d-flex justify-content-start w-75 container">
        <img src="@/assets/profile_sample.png" class="img-thumbnail profile-size" alt="...">
        <h3 class="mx-3">{{ getNickname }}</h3>
      </div>
      <div class="container w-75 d-flex justify-content-between my-3">
        <span @click="changeComponent(0)" v-bind:class="[componentNum === 0 ? 'select-font' : 'select-font-none']">나의 활동</span>
        <span class="select-font-none">|</span>
        <span @click="changeComponent(1)" v-bind:class="[componentNum === 1 ? 'select-font' : 'select-font-none']">내가 쓴 게시글</span>
        <span class="select-font-none">|</span>
        <span @click="changeComponent(2)" v-bind:class="[componentNum === 2 ? 'select-font' : 'select-font-none']">좋아요한 게시글</span>
      </div>
      <div class="container">
        <UserActivity v-if="componentNum === 0" :userNumber="getUserNumber" />
        <MyCommunityList v-else-if="componentNum === 1" :userNumber="getUserNumber" />
        <LikeCommunity v-else :userNumber="getUserNumber" />
      </div>
    </div>
    <div v-else>
      <h1>존재하지 않는 페이지입니다</h1>
    </div>
    
    <div id="foot"></div>
    <menubar id="menubar"></menubar>
  </div>
</template>

<script>
// import axios from "axios";
import HeaderNav from "@/components/Menu/HeaderNav.vue";
import Menubar from "@/components/Menu/Menubar.vue";
import MyCommunityList from "@/components/Profile/MyCommunityList.vue"
import LikeCommunity from "@/components/Profile/LikeCommunity.vue"
import UserActivity from "@/components/Profile/UserActivity.vue"
import { mapState } from 'vuex'

export default {
  components: { 
    Menubar, 
    HeaderNav,
    MyCommunityList,
    UserActivity,
    LikeCommunity
  },
  data() {
    return {
      id: null,
      componentNum: 0
    };
  },
  methods: {
    changeComponent(num) {
      this.componentNum = num
    }
  },
  computed: {
    ...mapState([
      'profile'
    ]),
    getNickname() {
      if(this.profile.nickname) {
        return this.profile.nickname
      }
      else {
        return false
      }
    },
    getUserNumber() {
      return this.profile.userNumber
    }
  },
  created() {
    this.$store.dispatch('getProfile', this.$route.params.usernumber)
  },
};
</script>

<style scoped>
#foot {
  height: 150px;
}
#menubar {
  position: fixed;
  background-color: #b6c790;
  bottom: 0;
}

#row {
  white-space: nowrap; /* 가로스크롤시 중요한 속성 */
  overflow-x: auto;
  overflow-y: hidden;
  padding: 10px 10px 5px;
  /* background: #efefef; */
  width: auto;
}

#row .items {
  display: inline-block;
  /* margin-left: 10px; */
  width: 100px;
}

#row .items:first-child {
  margin-left: 0;
}

#row .items p {
  margin-bottom: 8px;
  text-indent: 7px;
}

#row .items ul {
  border-radius: 3px;
  border: 1px solid #b5b5b5;
  height: 135px;
  overflow-y: scroll;
  padding: 3px 3px 8px;
  background: #fff;
}

#row .items ul li a {
  display: block;
  overflow: hidden;
  margin-top: 8px;
  padding: 3px;
  color: black;
  font-size: 12px;
  text-decoration: none;
}

#row .items ul li:first-child a {
  margin-top: 3px;
}

#row .items ul li.on a {
  border: 1px solid #c9cccf;
  border-radius: 3px;
  font-weight: bold;
  background-color: #efefef;
}

.select-font-none {
  font-family: Noto Sans KR;
  font-style: normal;
  font-weight: bold;
  font-size: 13px;
  line-height: 17px;
  color: #999999;
}

.select-font {
  font-family: Noto Sans KR;
  font-style: normal;
  font-weight: bold;
  font-size: 13px;
  line-height: 17px;
  color: #446631;
}

.profile-size {
  width: 25%;
}
</style>
