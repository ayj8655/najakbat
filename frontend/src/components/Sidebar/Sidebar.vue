<template>
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
              <div class="nickname">닉네임</div>
              <div class="email">email@gmail.com</div>
            </div>
          </div>
        </div>
        <div class="d-flex flex-column bd-highlight mb-3" @click="sidebarOff">
          <router-link  to="/Settings" class="text-design p-3 bd-highlight">설정</router-link>
          <router-link  to="/DirectQuery" class="text-design p-3 bd-highlight">1:1 문의</router-link>
          <div class="p-3 text-design bd-highlight">
            <!-- Button trigger modal -->
            <div type="button" class="" @click="logout">
              로그아웃
            </div>


            <!-- Modal -->

            

          </div>


          <div class="text-design p-3 bd-highlight">
            <!-- Button trigger modal -->
            <div type="button" class="" >
              탈퇴하기
            </div>
            <!-- <l/> -->
            <!-- Modal -->


          </div>
        </div>
      </div>
    </transition>        

  </div>
  </div>
</template>

<script>
import vClickOutside from 'v-click-outside'
import { mapActions } from 'vuex'
import { mapState } from 'vuex'
// import l from './include/l.vue'

export default {
  name: "Sidebar",
  directives: {
    clickOutside: vClickOutside.directive
  },
  // components: {
  //   l
  // },
  data () {
    return {
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
      'logout'
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
</style>