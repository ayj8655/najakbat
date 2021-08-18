<template>
  <div>
    <header-nav></header-nav>
    <Sidebar id="side-bar" />
    <div v-if="getNickname" class="container">
      <div class="d-flex justify-content-start w-75 container p-0">
        <label for="file-input" class="w-25">
          <img src="@/assets/profile_sample.png" class="w-100" alt="...">
        </label>
        <input id="file-input" type="file" ref="files" style="display: none;" />
        <div class="row">
          <div class="d-flex align-items-end">
            <h2 class="ms-3 me-1 my-0">{{ getNickname }}</h2>
            <router-link to="/user/modify"><font-awesome-icon :icon="['fas', 'cog']" size="lg" class="setting-color mb-1" /></router-link>
          </div>
          <div class="d-flex align-items-start ms-3 mt-1">
            <router-link v-if="getNickname == this.userNick" to="/message">
            <font-awesome-icon :icon="['fas', 'envelope']" size="lg" class="message-color"/>
            </router-link>
            <font-awesome-icon v-else :icon="['fas', 'envelope']" size="lg" class="message-color" data-bs-toggle="modal" data-bs-target="#messagereply" @click="syncNickname(getNickname)"/>
            <!-- <router-link v-else> -->
            <!-- </router-link> -->
            <span class="message-style ms-2">쪽지함</span>
          </div>
        </div>
      </div>
      <hr class="profile-line container my-3">
      <div class="container w-75 d-flex justify-content-between my-3">
        <span @click="changeComponent(0)" v-bind:class="[componentNum === 0 ? 'select-font' : 'select-font-none']">나의 활동</span>
        <span class="select-font-none">|</span>
        <span @click="changeComponent(1)" v-bind:class="[componentNum === 1 ? 'select-font' : 'select-font-none']">내가 쓴 게시글</span>
        <span class="select-font-none">|</span>
        <span @click="changeComponent(2)" v-bind:class="[componentNum === 2 ? 'select-font' : 'select-font-none']">좋아요한 게시글</span>
      </div>
      <div class="container">
        <UserActivity v-if="componentNum === 0" :userNumber="getUserNumber" :userNickname="getNickname" />
        <MyCommunityList v-else-if="componentNum === 1" :userNumber="getUserNumber" />
        <LikeCommunity v-else :userNumber="getUserNumber" />
      </div>
    </div>
    <div v-else>
      <h1>존재하지 않는 페이지입니다</h1>
    </div>
    
  <div v-show="this.$store.state.sidebar == false">
    <div id="foot"></div>
    <menubar id="menubar"></menubar>
  </div>
  <!-- modal -->
  <div class="modal fade" id="messagereply" tabindex="-1" aria-labelledby="exampleModalLabel2" aria-hidden="true">
  <div class="modal-dialog modal-dialog-centered">
    <div class="container modaldesign">
    <div class="modal-content">
      <div class="">
        <div class="modal-title" align="center" id="exampleModalLabel">
          <!-- <div class="dropdown" align="center"> -->
          <div class="p-2 pb-2" align="center">
            <h2 class="mb-0">{{ this.messageReceiverNickname }}</h2>
          </div>
          에게 보내는 쪽지
        </div>
        <!-- <button type="button" class="btn-close mx-0" data-bs-dismiss="modal" aria-label="Close"></button> -->
      </div>
      <div class="modal-body pt-2">
        <div class="form-group" align="left">
          <textarea
            @click="findNumber"
            class="form-control"
            rows="15"
            id="content"
            name="content"
            v-model="content"
            placeholder="쪽지 내용을 입력하세요."
          ></textarea>
        </div>
      </div>
      <div class="">
        <button type="button" v-if="content" data-bs-dismiss="modal" class="btn buttoncolor mb-3 mx-2" @click="postingMessage (messageReceiverNickname)">보내기</button>
        <button type="button" v-else disabled class="btn buttoncolor mb-3 mx-2" @click="postingMessage (messageReceiverNickname)">보내기</button>
        <button type="button" class="btn btn-secondary mb-3 mx-2" data-bs-dismiss="modal">취소</button>
      </div>
    </div>
  </div>
  </div>
</div>
  </div>
</template>

<script>
// import axios from "axios";
import HeaderNav from "@/components/Menu/HeaderNav.vue";
import Menubar from "@/components/Menu/Menubar.vue";
import MyCommunityList from "@/components/Profile/MyCommunityList.vue"
import LikeCommunity from "@/components/Profile/LikeCommunity.vue"
import UserActivity from "@/components/Profile/UserActivity.vue"
import Sidebar from "@/components/Sidebar/Sidebar.vue";
import { mapState } from 'vuex'

export default {
  components: { 
    Menubar, 
    HeaderNav,
    MyCommunityList,
    UserActivity,
    LikeCommunity,
    Sidebar
  },
  data() {
    return {
      id: null,
      userProfile: null,
      componentNum: 0,
      content: '',
      receiver: '',
      userNick: localStorage.getItem('userNickname'),
    };
  },
  methods: {
    changeComponent(num) {
      this.componentNum = num
    },
    postingMessage() {
      // console.log(nickname)
      // this.$store.dispatch('getReceiverNumber', nickname)
      this.receiver = this.ReceiverNumber
      console.log(this.receiver)
      this.$store.dispatch('messagePost', [this.content, this.$store.state.ReceiverNumber])
    },
    syncNickname(nickname) {
      // console.log(nickname)
      this.$store.state.messageReceiverNickname = nickname
      this.content = ''
    },
    findNumber() {
      this.$store.dispatch('getReceiverNumber', this.$store.state.messageReceiverNickname)
    }
  },
  computed: {
    ...mapState([
      'profile',
      'ReceiverNumber',
      'messageReceiverNickname'
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
    },
  },
  created() {
    this.$store.dispatch('getProfile', this.$route.params.usernumber)
  },
};
</script>

<style scoped>
#side-bar {
  z-index: 1;
  position: fixed;
}
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

.profile-line {
  width: 75%;
  border: 1px solid #999999;
}

.setting-color {
  color: #999999;
}

.message-color {
  color: #446631;
}

.message-style {
  font-family: Noto Sans KR;
  font-style: normal;
  font-weight: normal;
}
</style>
