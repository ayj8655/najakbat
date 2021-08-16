<template>
<div>
  <header-nav></header-nav>
  <Sidebar id="side-bar" />
    <div class="container2 px-4 me-1 ms-1">
    <div class="row">

    <div class="bd-highlight mb-3">
  <h3 class="mb-3 mt-5 fw-bold">설정</h3>
  <h4 class="container p-2 bd-highlight text-start">알림 설정</h4>
    <div class="container p-2 bd-highlight border border-end-0 border-start-0">
    <!-- <toggle-button @change="onChangeEventHandler"/> -->
    <div class="row">
      <div class="col-8">물 주기 알림</div>
      <toggle-button :value="water_notice"
                color="#82C7EB"
                :sync="true"
                class="togglebtn col-3"
                @change="changeWaterNotice ()"/>

    </div>
    </div>

    <div class="container p-2 bd-highlight border border-end-0 border-start-0">
      <div class="row">
    <div class="col-8">추천 정보 알림</div>
    <!-- <toggle-button v-model="myDataVariable"/> -->
    <toggle-button :value="recommendedinfo_notice"
               color="#82C7EB"
               :sync="true"
               class="togglebtn col-3"
               @change="changeRecommendedinfoNotice ()"/> 

    </div>
      </div>

    <div class="container p-2 bd-highlight border border-end-0 border-start-0">
      <div class="row">
    <div class="col-8">커뮤니티 새 댓글 알림</div>
    <toggle-button :value="newcomments_notice"
               color="#82C7EB"
               :sync="true"
               class="togglebtn col-3"
               @change="changeNewcommentsNotice ()"/>

    </div>
      </div>

    <div class="container p-2 bd-highlight border border-end-0 border-start-0">
    <div class="row">
    <div class="col-8">새 쪽지 알림</div>
    <toggle-button :value="newtwits_notice"
               color="#82C7EB"
               :sync="true"
               class="togglebtn col-3"
               @change="changeNewtwitsNotice ()"/>  

    </div>
    </div>
  <h4 class="container p-2 bd-highlight text-start mt-2">다크모드 설정</h4>
    <div class="container p-2 bd-highlight border border-end-0 border-start-0">
    <div class="row">
    <div class="col-8">다크모드</div>
    <toggle-button :value="nightmode_notice"
               color="#82C7EB"
               :sync="true"
               class="togglebtn col-3"
               @change="changeNightmode ()"/>

    </div>
    </div>
  <h4 class="container p-2 bd-highlight text-start mt-2">알림시간 설정</h4>
    <div class="p-2 bd-highlight container border border-end-0 border-start-0 mb-3">
    <div class="row">

    <div class="col-8">알림시간</div>
    <div class="col-1">
      <select v-model="selected">
        <option v-if="noticeTime > 12" disabled value="">오후 {{ noticeTime - 12 }}시</option>
        <option v-else disabled value="">오전 {{ noticeTime }}시</option>
        <option v-for="option in options" :key="option.value" :value="option.value">
          {{ option.text }}
        </option>
        <option v-for="option2 in options2" :key="option2.value" :value="option2.value">
          {{ option2.text }}
        </option>
      </select>
    </div>
    </div>
    </div>
    <button type="button" class="btn btn-success mb-2" v-if="changes" @click="settingsUpdate">저장</button>
    <!-- <button type="button" class="btn btn-success mb-2" v-if="changes" @click="updateNotice ([this.water_notice, this.recommendedinfo_notice, this.newcomments_notice, this.newtwits_notice, this.nightmode_notice])">저장</button> -->
    <button type="button" class="btn btn-success" v-else disabled>저장</button>
    </div>
  </div>  



    </div>
  <div v-show="this.$store.state.sidebar == false">
    <div id="foot"></div>
    <menubar id="menubar"></menubar>
  </div>
</div>
</template>

<script>
import { mapState } from 'vuex'
import { mapActions } from 'vuex'
import HeaderNav from "@/components/Menu/HeaderNav.vue";
import Menubar from "@/components/Menu/Menubar.vue";
import Sidebar from "@/components/Sidebar/Sidebar.vue";

export default {
  name: 'AlertsSettings',
  data() {
    return {
      // mywater_notice: this.$store.state.water_notice,
      changes: false,
      selected: '',
      noon: false,
      options: [
        { text: '오전 1시', value: '1'},
        { text: '오전 2시', value: '2'},
        { text: '오전 3시', value: '3'},
        { text: '오전 4시', value: '4'},
        { text: '오전 5시', value: '5'},
        { text: '오전 6시', value: '6'},
        { text: '오전 7시', value: '7'},
        { text: '오전 8시', value: '8'},
        { text: '오전 9시', value: '9'},
        { text: '오전 10시', value: '10'},
        { text: '오전 11시', value: '11'},
        { text: '오전 12시', value: '12'},
      ],
      options2: [
        { text: '오후 1시', value: '13'},
        { text: '오후 2시', value: '14'},
        { text: '오후 3시', value: '15'},
        { text: '오후 4시', value: '16'},
        { text: '오후 5시', value: '17'},
        { text: '오후 6시', value: '18'},
        { text: '오후 7시', value: '19'},
        { text: '오후 8시', value: '20'},
        { text: '오후 9시', value: '21'},
        { text: '오후 10시', value: '22'},
        { text: '오후 11시', value: '23'},
        { text: '오후 12시', value: '24'}
      ]
    }
  },
  components: { Menubar, HeaderNav, Sidebar },
  methods: {
    ...mapActions ([
      'updateNotice',
    ]),
    changeWaterNotice () {
      this.$store.state.water_notice = !this.$store.state.water_notice
    },
    changeRecommendedinfoNotice () {
      this.$store.state.recommendedinfo_notice = !this.$store.state.recommendedinfo_notice
    },
    changeNewcommentsNotice () {
      this.$store.state.newcomments_notice = !this.$store.state.newcomments_notice
    },
    changeNewtwitsNotice () {
      this.$store.state.newtwits_notice = !this.$store.state.newtwits_notice
    },
    changeNightmode () {
      this.$store.state.nightmode_notice = !this.$store.state.nightmode_notice
    },

    // test () {
    //   console.log(this.$store.state)
    // }
    settingsUpdate() {
      var timenum = Number(this.selected)
      const settingsStatus = [this.water_notice, this.recommendedinfo_notice, this.newcomments_notice, this.newtwits_notice, this.nightmode_notice, timenum]
      // console.log(settingsStatus)
      this.$store.dispatch('updateNotice', settingsStatus)
      this.changes = false
      this.$router.go(0)
    },


  },
  created() {
    this.$store.dispatch('getNoticeSettings');
    console.log(this.changes)
  },

  watch: {
    water_notice: function () {
      if (this.changes) {
        this.changes = true
      } else {
        this.changes = ! this.changes
      }
    },
    recommendedinfo_notice: function () {
      if (this.changes) {
        this.changes = true
      } else {
        this.changes = ! this.changes
      }
    },
    newcomments_notice: function () {
      if (this.changes) {
        this.changes = true
      } else {
        this.changes = ! this.changes
      }
    },
    newtwits_notice: function () {
      if (this.changes) {
        this.changes = true
      } else {
        this.changes = ! this.changes
      }
      
    },
    nightmode_notice: function () {
      if (this.changes) {
        this.changes = true
      } else {
        this.changes = ! this.changes
      }
      
    },
    selected: function () {
      if (this.changes) {
        this.changes = true
      } else {
        this.changes = ! this.changes
      }
      
    },

  },
  computed: {
    ...mapState([
      'water_notice',
      'recommendedinfo_notice',
      'newcomments_notice',
      'newtwits_notice',
      'nightmode_notice',
      'noticeTime',
    ]),
  },
}

</script>

<style scoped>
.togglebtn {
  z-index: 0;
  right: 0px;
}

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
</style>
