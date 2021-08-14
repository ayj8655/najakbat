<template>
<div>
  <header-nav></header-nav>
  <router-view></router-view>
  <h3 class="mb-3 mt-5 fw-bold">알림</h3>
  <button type="button" class="btn btn-warning mb-2" v-if="isdeleteactivated" @click="Activatedelete">삭제 취소</button>
  <div v-else>
  <button type="button" class="btn btn-secondary mb-2" @click="Activatedelete">알림 삭제</button>
  <button type="button" class="btn btn-warning mb-2" @click="deleteAllNotices">전체 삭제</button>
  </div>
  <div v-for="(searchNotice, index) in searchNotices" :key="index">
    <div class="container px-0">
      <div v-if="searchNotice.isRead" class="isRead-false border border-end-0 border-start-0 bg-white" @click="Reading ([searchNotice, index])">
        <div class="container notice mt-2">
          <div class="row">
            <div class="col-1 px-0" v-show="isdeleteactivated">
              <!-- <img v-if="noticechecked" src="@/assets/select_on.png" alt="">
              <img v-else src="@/assets/select_off.png" alt=""> -->

              <input class="mt-2" type="checkbox" id="checkbox" @click="checkingNotice(searchNotice)">
              <label for="checkbox"></label>
            
            </div>
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
      <div v-else class="isRead-false border border-end-0 border-start-0" @click="Reading ([searchNotice, index])">
        <div class="container notice mt-2">
          <div class="row">
            <div class="col-1 px-0" v-show="isdeleteactivated">
              <!-- <img v-if="noticechecked" src="@/assets/select_on.png" alt="">
              <img v-else src="@/assets/select_off.png" alt=""> -->
              <input class="mt-2" type="checkbox" id="checkbox" @click="checkingNotice(searchNotice)">
              <label for="checkbox"></label>
            </div>
            <div class="col-2 px-0">
              <!-- <img v-if="searchNotice.isRead" src="@/assets/noti.png" width="30px" height="30px" alt=""> -->
              <img src="@/assets/noti_green.png" width="30px" height="30px" alt="">
            </div>
            <div class="col-8 px-0">
              <div class="content">
                {{searchNotice.content}}
              </div>
              <div class="date mx-1">
              {{ searchNotice.date | moment('YY-MM-DD HH:mm:ss') }} 
              </div>
            </div>
            <div class="col-1 px-0"></div>
          </div>
        </div>
      </div>
    </div>
  </div>
  <button type="button" class="btn btn-danger mt-3" v-show="isdeleteactivated" @click="deleteNotices">선택 삭제</button>
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
    // console.log(this.$store.searchNotices)
    // console.log(this.$store.noticeUnread)
  },
  data () {
    return {
      isdeleteactivated: false,
      noticechecked: false,
      checked: false,
      checkedList: [],
    }
  },

  methods: {
    ...mapActions ([
      'updateIsread',
      'deleteallNotices',
      'deleteNotice',
    ]),
    Reading (infolist) {
      if (!this.isdeleteactivated) {

        const mynotice = infolist[0]
      const myindex = infolist[1]
      mynotice.isRead = 1
      // this.$store.state.searchNotices[mynotice.noticeNumber] = mynotice.isRead
      this.$store.state.searchNotices[myindex].isRead = 1 
      const mynoticeStatus = [mynotice.isRead, mynotice.noticeNumber]
      this.$store.dispatch('updateIsread', mynoticeStatus)
      if (mynotice.title === '커뮤니티 알림') {
        this.$router.push({name: 'CommunityDetail', params: {no: mynotice.postNumber}})
      }
      else if (mynotice.title === '물주기 알림')
        this.$router.push({name: 'MyCrop'})
      else if (mynotice.title === '쪽지 알림')
        this.$router.push({name: 'Message'})
        }
    },
    Activatedelete () {
      this.isdeleteactivated = ! this.isdeleteactivated
      // console.log(this.isdeleteactivated)
    },
    checkingNotice (mynotice) {
        if (this.checkedList.includes(mynotice.noticeNumber)) {
          this.checkedList.splice(this.checkedList.indexOf(mynotice.noticeNumber), 1)
        }
        else {
          this.checkedList.push(mynotice.noticeNumber)
        }
        console.log(this.checkedList)
    },
    deleteAllNotices () {
      this.$store.dispatch('deleteAllNotices')
      this.$router.go(0)
    },
    deleteNotices () {
      this.$store.dispatch('deleteNotices', this.checkedList)
      this.$router.go(0)
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