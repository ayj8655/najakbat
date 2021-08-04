<template>
<div>
  <header-nav></header-nav>
  <router-view></router-view>
  <h3 class="mb-3 mt-5 fw-bold">알림</h3>
  <button type="button" class="btn btn-danger mb-2" @click="deleteallNotices">Danger</button>
  <div v-for="searchNotice in searchNotices" :key="searchNotice.noticeNumber">
    <div class="container px-0">
      <div v-if="searchNotice.isRead" class="isRead-false border border-end-0 border-start-0 bg-white" draggable @click="Reading (searchNotice)">
        <div class="container notice mt-2">
          <div class="row">
            <div class="col-1 px-0" @drop="deleteNotice" @dragover.prevent @dragenter.prevent></div>
            <div class="col-2 px-0">
              <img v-if="searchNotice.isRead" src="@/assets/noti.png" width="30px" height="30px" alt="">
              <!-- <img v-else src="@/assets/noti_green.png" width="30px" height="30px" alt=""> -->
            </div>
            <div class="col-8 px-0">
              <div class="content">
                {{searchNotice.content}}
              </div>
              <div class="date mx-1">
                {{ searchNotice.date | moment('YYYY-MM-DD HH:mm:ss') }} 
              </div>
            </div>
            <div class="col-1 px-0"></div>
          </div>
        </div>
      </div>
      <div v-else class="isRead-false border border-end-0 border-start-0" draggable @click="Reading (searchNotice)">
        <div class="container notice mt-2">
          <div class="row">
            <div class="col-1 px-0" @drop="deleteNotice" @dragover.prevent @dragenter.prevent ></div>
            <div class="col-2 px-0">
              <!-- <img v-if="searchNotice.isRead" src="@/assets/noti.png" width="30px" height="30px" alt=""> -->
              <img src="@/assets/noti_green.png" width="30px" height="30px" alt="">
            </div>
            <div class="col-8 px-0">
              <div class="content">
                {{searchNotice.content}}
              </div>
              <div class="date mx-1">
              {{ searchNotice.date | moment('YYYY-MM-DD HH:mm:ss') }} 
              </div>
            </div>
            <div class="col-1 px-0"></div>
          </div>
        </div>
      </div>
    </div>
  </div>
  <div id="foot"></div>
  <menubar id="menubar"></menubar>
</div>
</template>

<script>
import Vue from 'vue'
import { mapState } from 'vuex'
import { mapActions } from 'vuex'
import HeaderNav from '@/components/Menu/HeaderNav.vue';
import Menubar from '@/components/Menu/Menubar.vue';
import vueMoment from 'vue-moment'
Vue.use(vueMoment)

export default {
  name: 'Myalerts',
  created() {
    this.$store.dispatch('getSearchNotice')
  },
  methods: {
    ...mapActions ([
      'updateIsread',
      'deleteallNotices',
      'deleteNotice',
    ]),
    Reading (mynotice) {
      mynotice.isRead = 1
      // this.$store.state.searchNotices[mynotice.noticeNumber] = mynotice.isRead
      const mynoticeStatus = [mynotice.isRead, mynotice.noticeNumber]
      // console.log(this.$store.state.searchNotices)
      this.$store.state.searchNotices[mynoticeStatus[1] - 1].isRead = mynoticeStatus[0] 
      this.$store.dispatch('updateIsread', mynoticeStatus)
    },
    test () {
      console.log('success')
    }
  },
  computed: {
    ...mapState([
      'searchNotices',
      // 'noticeIsreads',
    ])
  },
  components: { 
    HeaderNav,
    Menubar,
  }
}
</script>

<style>
#foot {
  height: 150px;
}
#menubar {
  position: fixed;
  background-color: #B6C790; 
  bottom: 0;
}
.isRead-false {
  width: 100%;
  height: 52px;
  top: 204px;
  
  background: #E7F0D4;
}
.notice {
  width: 100%;
  height: 33px;
}
.content {
  font-family: Noto Sans KR;
  font-style: normal;
  font-weight: normal;
  font-size: 17px;
  line-height: 25px;
  text-align: left;
  color: #000000;
}
.date {
  font-family: Noto Sans KR;
  font-style: normal;
  font-weight: normal;
  font-size: 9px;
  line-height: 13px;
  /* identical to box height */
  color: #999999;
  text-align: right;
}
</style>