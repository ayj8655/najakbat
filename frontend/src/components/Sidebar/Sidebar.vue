<template>
  <div id="sidebar-1">
    <transition name="slide-fade">
      <div class="container greenbg w-75" v-if="sidebar" v-click-outside="onClickOutside">
        <div class="container infos w-100 h-25 px-0 mt-5">
          <div class="container row d-flex mx-4">
            <router-link :to="'/profile/' + this.$store.state.myNumber" class="col-1 p-0 ps-2">
            <img src="@/assets/profile_sample.png" class="profile">
            </router-link>
            <div class="container col-5 d-flex flex-column mx-5 mt-2 pe-1">
              <div class="nickname">닉네임</div>
              <div class="email">email@gmail.com</div>
            </div>
          </div>
        </div>
        <div class="d-flex flex-column bd-highlight mb-3">
          <router-link to="/Settings" class="p-2 bd-highlight">설정</router-link>
          <router-link to="/DirectQuery" class="p-2 bd-highlight">1:1 문의</router-link>
          <div class="p-2 bd-highlight">
            <!-- Button trigger modal -->
            <button type="button" class="btn btn-primary" @click="logout">
              로그아웃
            </button>

            <!-- Modal -->
            <div class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
              <div class="modal-dialog">
                <div class="modal-content">
                  <div class="modal-header">
                    <!-- <h5 class="modal-title" id="exampleModalLabel">Modal title</h5> -->
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                  </div>
                  <div class="modal-body">
                    ...
                  </div>
                  <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                    <button type="button" class="btn btn-primary">Save changes</button>
                  </div>
                </div>
              </div>
            </div>
          </div>
          <div class="p-2 bd-highlight">
            <!-- Button trigger modal -->
            <button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#exampleModal">
              탈퇴하기
            </button>

            <!-- Modal -->
            <div class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
              <div class="modal-dialog">
                <div class="modal-content">
                  <div class="modal-header">
                    <h5 class="modal-title" id="exampleModalLabel">Modal title</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                  </div>
                  <div class="modal-body">
                    ...
                  </div>
                  <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                    <button type="button" class="btn btn-primary">Save changes</button>
                  </div>
                </div>
              </div>
            </div>

          </div>
        </div>
      </div>
    </transition>
  </div>
</template>

<script>
import vClickOutside from 'v-click-outside'
import { mapActions } from 'vuex'
import { mapState } from 'vuex'

export default {
  name: 'sidebar-1',
  directives: {
    clickOutside: vClickOutside.directive
  },
  methods: {
    onClickOutside () {
      this.$store.state.sidebar = false
    },
    Sidebaron () {
      this.$store.state.sidebar = ! this.$store.state.sidebar
    },
    ...mapActions ([
      'logout'
    ])

  },
  computed: {
    ...mapState ([
      'sidebar'
    ])
  }
}
</script>

<style scoped>
.greenbg {
  position: fixed;
  width: 80%;
  height: 812px;
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