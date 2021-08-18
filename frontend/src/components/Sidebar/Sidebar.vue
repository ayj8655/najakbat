<template>
<div>
  <div>

  <div id="Sidebar">
    <transition name="slide-fade">
      <div class="container greenbg w-75" v-if="sidebar" v-click-outside="onClickOutside">
        <div class="container infos w-100 px-0 mt-5">
          <div class="container row d-flex mx-4">
            <router-link :to="'/profile/' + profileNumber" class="col-1 p-0 ps-2">
            <img src="@/assets/profile_sample.png" class="profile" @click="sidebarOff">
            </router-link>
            <div class="container col-5 d-flex flex-column mx-5 mt-2 pe-1">
              <div class="nickname">{{ nickname }}</div>
              <div class="email">email@gmail.com</div>
            </div>
          </div>
        </div>
        <div class="d-flex flex-column bd-highlight mb-3" @click="sidebarOff">
          <router-link  to="/Settings" class="text-design p-3 bd-highlight">설정</router-link>
          <router-link  to="/DirectQuery" class="text-design p-3 bd-highlight">1:1 문의</router-link>
          <router-link v-if="profileNumber == 1" to="/Admin" class="text-design p-3 bd-highlight">관리자 페이지</router-link>
          <div class="p-3 text-design bd-highlight" data-bs-toggle="modal" data-bs-target="#logout">
            <!-- Button trigger modal -->
            <div type="button" class="">
              로그아웃
            </div>
          </div>
          <div class="text-design p-3 bd-highlight" data-bs-toggle="modal" data-bs-target="#withdrawl">
            <!-- Button trigger modal -->
            <div type="button" class="" >
              탈퇴하기
            </div>
          </div>
        </div>
      </div>
    </transition>        
  </div>
  </div>

  <!-- logout Modal -->
<div class="modal fade" id="logout" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
  <div class="modal-dialog modal-dialog-centered">
    <div class="modal-content modaldesign">
      <div class="">
        <h5 class="modal-title mt-3" id="deleteallLabel">로그아웃</h5>
      </div>
      <div class="modal-body">
        로그아웃 하시겠습니까?
      </div>
      <div class="">
        <button type="button" class="btn btn-sm btn-secondary mb-3 mx-2" data-bs-dismiss="modal">취소</button>
        <button type="button" class="btn btn-sm buttoncolor2 mb-3 mx-2" @click="logout">로그아웃</button>
      </div>
    </div>
  </div>
</div>

<!-- 탈퇴하기 모달 -->
<div class="modal fade" id="withdrawl" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
  <div class="modal-dialog modal-dialog-centered">
    <div class="modal-content modaldesign">
      <div class="">
        <h5 class="modal-title mt-3" id="deleteallLabel">회원탈퇴</h5>
      </div>
      <div class="modal-body">
        정말로 탈퇴 하시겠습니까?
      </div>
      <div class="">
        <button type="button" class="btn btn-sm btn-secondary mb-3 mx-2" data-bs-dismiss="modal">취소</button>
        <button type="button" class="btn btn-sm btn-danger mb-3 mx-2" @click="withDrawl">탈퇴하기</button>
      </div>
    </div>
  </div>
</div>
  </div>
</template>

<script>
import vClickOutside from 'v-click-outside'
import { mapActions } from 'vuex'
import { mapState } from 'vuex'
// import Logout from '@/components/Sidebar/include/Logout.vue'

export default {
  name: "Sidebar",
  directives: {
    clickOutside: vClickOutside.directive
  },
  components: {
      // Logout
  },
  
  data () {
    return {
      nickname: localStorage.getItem('userNickname'),
      profileNumber: localStorage.getItem('userNumber')
    }
  },
  methods: {
    onClickOutside () {
      this.$store.state.sidebar = false
    },

    Sidebaron () {
      this.$store.state.sidebar = ! this.$store.state.sidebar
      console.log(this.profileNumber)
    },
    sidebarOff() {
      this.$store.state.sidebar = false
    },
    ...mapActions ([
      'logout',
      'withDrawl'
    ])

  },
  computed: {
    ...mapState ([
      'sidebar',
      'myNumber'
    ])
  }
}
</script>

<style scoped>
.modal-backdrop { 
  z-index: -999999; 
}
.infos {
  height: 150px;
}
.greenbg {
  position: fixed;
  width: 80%;
  height: 1000px;
  right: 0px;
  top: 0px;
  align-content: right;
  background: #B6C790;
}
.profile {
  /* position: absolute; */
  width: 48px;
  height: 48px;
  left: 72px;
  top: 23px;

  /* background: #FFFFFF; */
  border: 1px solid #B6C790;
  box-sizing: border-box;
}
.nickname {
  /* position: absolute; */
  width: 70px;
  height: 17px;
  left: 127px;
  top: 28px;

  font-family: Noto Sans KR;
  font-style: normal;
  font-weight: bold;
  font-size: 12px;
  line-height: 17px;

  color: #FFFFFF;
}
.email {
  /* position: absolute; */
  width: 70px;
  height: 13px;
  left: 127px;
  top: 45px;

  font-family: Noto Sans KR;
  font-style: normal;
  font-weight: normal;
  font-size: 9px;
  line-height: 13px;
  /* identical to box height */


  color: #000000;
}
.text-design {
  text-decoration: none;
  color: #FFFFFF;
  font-weight: bold;
  border-radius: 15px 15px;
}

.text-design:hover {
  background: white;
  opacity: 0.5;
  color: #01431B;
  box-shadow: 5px 5px 5px;
}
.slide-fade-enter-active {
  transition: all .3s ease;
}
.slide-fade-leave-active {
  transition: all .3s cubic-bezier(1.0, 0.5, 0.8, 1.0);
}
.slide-fade-enter, .slide-fade-leave-to
/* .slide-fade-leave-active below version 2.1.8 */ {
  transform: translateX(10px);
  opacity: 0.1;
}
.modal-content {
  background: #FFFFFF;
  border: 3px solid #B6C790;
  box-sizing: border-box;
  box-shadow: 0px 4px 4px rgba(0, 0, 0, 0.25);
  border-radius: 15px;
}
.buttoncolor2 {
  background: #71873F;
  color: #FFFFFF;
}
</style>