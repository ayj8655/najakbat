<template>
<div>
  <header-nav></header-nav>
  <Sidebar id="side-bar" />
  <router-view></router-view>
  <h3 class="mb-3 mt-5 fw-bold">알림</h3>
  <!-- <button type="button" class="btn btn-sm btn-warning mb-3" v-if="isdeleteactivated" @click="Activatedelete">삭제 취소</button> -->
  <div class="d-flex flex-row-reverse px-4">
  <!-- <button type="button" class="btn btn-sm btn-secondary mb-3" @click="Activatedelete">알림 삭제</button> -->
  <img src="../../assets/open-trash-can.png" width="40px" height="30px" type="button" class="btn btn-sm mb-2 p-0 px-1 ms-auto" v-if="isdeleteactivated" @click="Activatedelete"/>
  <img src="../../assets/trash-can-with-cover.png" width="40px" height="30px" type="button" class="btn btn-sm mb-2 p-0 px-1 ms-auto" v-else @click="Activatedelete"/>
  <div v-if="isdeleteactivated">
  <button type="button" class="btn btn-sm btn-danger mb-2 p-0 px-1" v-if="checkedList.length > 0" @click="deleteNotices">선택 삭제</button>
  <button type="button" class="btn btn-sm btn-danger mb-2 p-0 px-1" v-else disabled @click="deleteNotices">선택 삭제</button>

  </div>
  </div>
  <div v-for="(searchNotice, index) in searchNotices.slice().reverse()" :key="index">
    <div class="container px-4">
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
              <div class="content font2">
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
              <div class="content font2">
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
  <button type="button" class="btn btn-sm mt-3" data-bs-toggle="modal" data-bs-target="#deleteall" v-if="isdeleteactivated">비우기</button>
  <div v-show="this.$store.state.sidebar == false">
    <div id="foot"></div>
    <menubar id="menubar"></menubar>
  </div>
<!-- Modal -->
<div class="modal fade" id="deleteall" tabindex="-1" aria-labelledby="deleteallLabel" aria-hidden="true">
  <div class="modal-dialog modal-dialog-centered">
    <div class="modal-content modaldesign">
      <div class="">
        <h5 class="modal-title mt-3" id="deleteallLabel">전체 알림 삭제</h5>
      </div>
      <div class="modal-body">
        모든 알림을 삭제하시겠습니까?
      </div>
      <div class="">
        <button type="button" class="btn btn-sm btn-secondary mb-3 mx-2" data-bs-dismiss="modal">취소</button>
        <button type="button" class="btn btn-sm buttoncolor2 mb-3 mx-2" @click="deleteAllNotices">전체삭제</button>
      </div>
    </div>
  </div>
</div>
</div>
</template>

<script>
import Vue from 'vue'
import { mapState } from 'vuex'
import { mapActions } from 'vuex'
import HeaderNav from '@/components/Menu/HeaderNav.vue';
import Menubar from '@/components/Menu/Menubar.vue';
import Sidebar from "@/components/Sidebar/Sidebar.vue";
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
            setTimeout(() => {
        this.$router.go(0)
      }, 1000)
        this.$fire({
          title: "성공!",
          text: "알림을 삭제하였습니다.",
          type: "success",
          timer: 3000,
      })
    },
    deleteNotices () {
      this.$store.dispatch('deleteNotices', this.checkedList)
            setTimeout(() => {
        this.$router.go(0)
      }, 1000)
        this.$fire({
          title: "성공!",
          text: "알림을 삭제하였습니다.",
          type: "success",
          timer: 3000,
      })
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
    Sidebar

  }
}
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
.buttoncolor2 {
  background: #71873F;
  color: #FFFFFF;
}
.modal-content {
  background: #FFFFFF;
  border: 3px solid #B6C790;
  box-sizing: border-box;
  box-shadow: 0px 4px 4px rgba(0, 0, 0, 0.25);
  border-radius: 15px;
}
.font1 {
  font-family: Noto Sans KR;
  font-style: normal;
  font-weight: normal;
  font-size: 15px;
  line-height: 25px;

  color: #999999;
}

.font2 {
  font-family: Noto Sans KR;
  font-style: normal;
  font-weight: normal;
  font-size: 15px;
  line-height: 25px;
}

</style>