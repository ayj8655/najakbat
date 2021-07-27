<template>
  <div id="sidebar-1">
    <button @click="sidebar = !sidebar">
      Toggle render
    </button>
    <transition name="slide-fade">
      <div class="container greenbg w-75" v-if="sidebar" v-click-outside="onClickOutside">
        <div class="container infos w-100 h-25">
          <div class="profile"></div>
          <div class="nickname">닉네임</div>
          <div class="email">email@gmail.com</div>
        </div>
        <div class="d-flex flex-column bd-highlight mb-3">
          <div class="p-2 bd-highlight">설정</div>
          <div class="p-2 bd-highlight">1:1 문의</div>
          <div class="p-2 bd-highlight">
            <div>
              <b-button v-b-modal.modal-1>로그아웃</b-button>

              <b-modal id="modal-1" title="BootstrapVue">
                <p class="my-4">Hello from modal!</p>
              </b-modal>
            </div>
          </div>
          <div class="p-2 bd-highlight">
            <div>
              <b-button v-b-modal.modal-1>탈퇴하기</b-button>

              <b-modal id="modal-1" title="BootstrapVue">
                <p class="my-4">Hello from modal!</p>
              </b-modal>
            </div>

          </div>
        </div>
      </div>
    </transition>
  </div>
</template>

<script>
import vClickOutside from 'v-click-outside'

export default {
  name: 'sidebar-1',
  directives: {
    clickOutside: vClickOutside.directive
  },
  data: function () {
    return {
      sidebar: false,
    }
  },
  methods: {
    onClickOutside () {
      this.sidebar = false
    },
  },


}
</script>

<style scoped>
.greenbg {
  position: absolute;
  width: 315px;
  height: 812px;
  left: 36px;
  top: 0px;
  background: #B6C790;
}
.profile {
  position: absolute;
  width: 48px;
  height: 48px;
  left: 72px;
  top: 23px;

  background: #FFFFFF;
  border: 1px solid #B6C790;
  box-sizing: border-box;
}
.nickname {
  position: absolute;
  width: 34px;
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
  position: absolute;
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
  opacity: 0;
}
</style>