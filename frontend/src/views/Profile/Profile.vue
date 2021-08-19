<template>
  <div>
    <header-nav></header-nav>
    <Sidebar id="side-bar" />
    <div v-if="!isNaN(this.$route.params.usernumber)" class="container">
      <div v-if="userProfile!==null">
        <div class="d-flex justify-content-start w-75 container p-0 mt-3">
          <div class="w-25" data-bs-toggle="modal" data-bs-target="#profilePhotoModal" v-if="this.userNum == this.$route.params.usernumber">
            <img src="@/assets/profile_sample.png" class="w-100" alt="..." v-if="!getProfilePhoto">
            <div class="box" v-else>
              <img :src="getProfilePhoto" alt="" class="w-100">
            </div>
          </div>
          <div v-else class="w-25">
            <img src="@/assets/profile_sample.png" class="w-100" alt="..." v-if="!getProfilePhoto">
            <div class="box" v-else>
              <img :src="getProfilePhoto" alt="" class="w-100">
            </div>
          </div>
          <input 
            id="file-input" 
            type="file" 
            ref="files" 
            style="display: none;"
            v-on:change="fileSlc" 
          />
          <div class="row">
            <div class="d-flex align-items-end">
              <h2 class="ms-3 me-1 my-0">{{ userProfile.nickname }}</h2>
              <router-link to="/user/modify"><font-awesome-icon :icon="['fas', 'cog']" size="lg" class="setting-color mb-1" v-if="this.userNum == this.$route.params.usernumber" /></router-link>
            </div>
            <div class="d-flex align-items-start ms-3 mt-1">
              <router-link v-if="getNickname == this.userNick" to="/message">
              <font-awesome-icon :icon="['fas', 'envelope']" size="lg" class="message-color"/>
              </router-link>
              <font-awesome-icon v-else :icon="['fas', 'envelope']" size="lg" class="message-color" data-bs-toggle="modal" data-bs-target="#messagereply" @click="syncNickname(getNickname)"/>
              <!-- <router-link v-else> -->
              <!-- </router-link> -->
              <span class="message-style ms-2" v-if="this.userNum == this.$route.params.usernumber">쪽지함</span>
              <span class="message-style ms-2" v-else>쪽지보내기</span>
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
          <UserActivity v-if="componentNum === 0" :userNumber="userProfile.userNumber" :userNickname="getNickname" />
          <MyCommunityList v-else-if="componentNum === 1" :userNumber="userProfile.userNumber" />
          <LikeCommunity v-else :userNumber="userProfile.userNumber" />
        </div>
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

  <!-- Profile Photo Modal -->
  <div
    v-if="userProfile"
      class="modal fade"
      id="profilePhotoModal"
      tabindex="-1"
      aria-labelledby="profilePhotoModalLabel"
      aria-hidden="true"
    >
      <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title" id="profilePhotoModalLabel">
              내 프로필 사진 관리
            </h5>
            <button
              type="button"
              class="btn-close"
              data-bs-dismiss="modal"
              aria-label="Close"
            ></button>
          </div>
          <div class="modal-body" id="thumbnail-select">
            <label for="file-input"></label>
            <div>
              <button class="btn btn-success m-2" v-if="getProfilePhoto">
                <label for="file-input">사진 바꾸기</label>
              </button>
              <button class="btn btn-success m-2" v-else>
                <label for="file-input">사진 등록하기</label>
              </button>
              <button class="btn btn-danger m-2" data-bs-dismiss="modal" v-if="getProfilePhoto" @click="deletePhoto">
                사진 삭제하기
              </button>
            </div>
            <input
              id="file-input"
              type="file"
              ref="file"
              style="display: none"
              v-on:change="fileSlc"
              v-if="this.$store.state.userNumber==this.$route.params.usernumber"
            />
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">완료</button>
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
import axios from 'axios';

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
      userNum: localStorage.getItem('userNumber'),
    };
  },
  methods: {
    getTargetProfile() {

    },
    deletePhoto() {
      var targetPhotoNum = this.userProfile.profilePhoto.photoNumber
      axios.delete(`user/photo/delete?photoNumber=${targetPhotoNum}&userNumber=${this.userNum}`)
      .then((res) => {
        if(res.status==200) {
          this.$store.dispatch('getProfile', this.$route.params.usernumber);
          this.userProfile.profilePhoto = null;
        }
      })
      .catch(err => {
        console.error(err);
      })
    },
    fileSlc() {
      let inputFiles = this.$refs.files.files;
      if (this.$store.state.profile.profilePhoto) {
        const formData = new FormData();
        var targetPhotoNum = this.$store.state.profile.profilePhoto.photoNumber
        formData.append("image", inputFiles[0]);
        axios.put(`user/photo/update?photoNumber=${targetPhotoNum}&userNumber=${this.userNum}`, formData, {
          headers: { "Content-Type": "multipart/form-data" },
        })
        .then(res => {
          res
          this.$store.dispatch('getProfile', this.$route.params.usernumber);
          
        })
        .catch(err => {
          console.error(err);
        })
      }
      else {
        const formData = new FormData();
        formData.append("userNumber", this.userNum);
        formData.append("image", inputFiles[0]);
        axios.post('user/photo/insert', formData, {
            headers: { "Content-Type": "multipart/form-data" },
          })
        .then((res) => {
          // res
          if(res.status==200) {
            this.userProfile = null;
            axios.get(`user/${this.$route.params.usernumber}`)
            .then((res) => {
              this.userProfile = res.data;
            })
            .catch(err => {
              console.error(err);
            })
          }
          this.$store.dispatch('getProfile', this.$route.params.usernumber)
        })
        .catch(err => {
          console.error(err);
        })
      }
    },
    changeComponent(num) {
      this.componentNum = num
    },
    postingMessage() {
      // console.log(nickname)
      // this.$store.dispatch('getReceiverNumber', nickname)
      this.receiver = this.ReceiverNumber
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
      if(this.userProfile.nickname) {
        return this.userProfile.nickname
      }
      else {
        return false
      }
    },
    getUserNumber() {
      return this.userProfile.userNumber
    },
    getProfilePhoto() {
      if (this.userProfile.profilePhoto) {
         var ProfileUrl = "https://mococobucket.s3.ap-northeast-2.amazonaws.com/profile/" + this.userProfile.profilePhoto.saveFile
        return ProfileUrl
      }
      else {
        return null
      }
    }
  },
  created() {
    // console.log(this.userProfile);
    // // console.log(this.$route.params.usernumber);
    // // console.log(this.$store.state.userNumber);
    // if(!isNaN(this.$route.params.usernumber)) {
    //   let usernumber = this.$route.params.usernumber;
    //   // console.log(usernumber);
    //   if(usernumber==this.$store.state.userNumber) {
    //     if(this.$store.state.profile===null) this.$store.dispatch('getProfile', this.$store.state.userNumber);
    //     this.userProfile = this.$store.state.profile;
    //     console.log(this.userProfile);
    //   } else {
        axios.get(`user/${this.$route.params.usernumber}`)
        .then((res) => {
          // console.log(res.data);
          this.userProfile = res.data;
        })
        .catch(err => {
          console.error(err);
        })
      // }
    // }
  },
  watch: {
    $route(to, from) { 
      if (to.path.includes("profile") && from.path.includes("profile") && to.path != from.path) {
        // window.location.reload();
        this.userProfile = null;
        // if(!isNaN(this.$route.params.usernumber)) {
        //   if(this.$route.params.usernumber==this.$store.state.userNumber) {
        //     if(this.$store.state.profile===null) this.$store.dispatch('getProfile', this.$route.params.usernumber);
        //     this.userProfile = this.$store.state.profile;
        //   } else {
            axios.get(`user/${this.$route.params.usernumber}`)
            .then((res) => {
              this.userProfile = res.data;
            })
            .catch(err => {
              console.error(err);
            })
        //   }
        // }
      } 
    },
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

.box {
    width: 75px;
    height: 75px;
    border: 1px solid #a9a9a9;
    border-radius: 70%;
    overflow: hidden;
}
.box img {
  width:100%;
  min-height: 100%;
}
</style>
