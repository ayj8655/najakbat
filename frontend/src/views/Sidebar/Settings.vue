<template>
<div>
  <header-nav></header-nav>
  <Sidebar id="side-bar" />
    <div class="container2 px-4 me-1 ms-1">
    <div class="row">

    <div class="bd-highlight mb-3">
    <div class="p-2 bd-highlight">
     설정
    </div>
    <div class="container p-2 bd-highlight">
    <!-- <toggle-button @change="onChangeEventHandler"/> -->
    <div class="row">
    <div class="col-8">물 주기 알림</div>
<toggle-button :value="water_notice"
              @change="changeWaterNotice ()"/>

    </div>
    </div>

    <div class="container p-2 bd-highlight">
      <div class="row">
    <div class="col">추천 정보 알림</div>
    <!-- <toggle-button v-model="myDataVariable"/> -->
    <toggle-button :value="recommendedinfo_notice"
               class="togglebtn col"    
               color="#82C7EB"
               :sync="true"
               :labels="true"
               @change="changeRecommendedinfoNotice ()"/>  
    </div>
      </div>

    <div class="container p-2 bd-highlight">
      <div class="row">
    <div class="col">커뮤니티 새 댓글 알림</div>
    <toggle-button :value="newcomments_notice"
               class="togglebtn col"
               color="#82C7EB"
               :sync="true"
               :labels="true"
               @change="changeNewcommentsNotice ()"/>
    </div>
      </div>

    <div class="container p-2 bd-highlight">
    <div class="row">
    <div class="col">새 쪽지 알림</div>
    <toggle-button :value="newtwits_notice"
               color="#82C7EB"
               :sync="true"
               class="togglebtn col"               
               :labels="true"
               @change="changeNewtwitsNotice ()"/>  
    </div>
    </div>

    <div class="container p-2 bd-highlight">
    <div class="row">
    <div class="col">나이트모드</div>
    <toggle-button :value="nightmode_notice"
               color="#82C7EB"
               class="togglebtn col"               
               :sync="true"
               :labels="true"
               @change="changeNightmode ()"/>
    </div>
    </div>

    <div class="p-2 bd-highlight container">
    <div class="row">

    <div class="col">알림시간</div>
    <h3 class="col">
      {{ noticeTime }}
    </h3>
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
      const settingsStatus = [this.water_notice, this.recommendedinfo_notice, this.newcomments_notice, this.newtwits_notice, this.nightmode_notice]
      this.$store.dispatch('updateNotice', settingsStatus)
    },


  },
  created() {
    this.$store.dispatch('getNoticeSettings');
    console.log(this.noticeTime)
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
.togglebtn col {
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
