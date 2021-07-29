<template>
<div>
    <div class="container">
    <div class="d-flex flex-column bd-highlight mb-3">
    <div class="p-2 bd-highlight">
     설정
    </div>
    <div class="p-2 d-flex bd-highlight">
    <!-- <toggle-button @change="onChangeEventHandler"/> -->
    <h2>물 주기 알림</h2>
    <toggle-button :value="mywater_notice"
               color="#82C7EB"
               :sync="true"
               :labels="true"
               @change="changeWaterNotice ()"/>
    </div>
    <div class="p-2 d-flex bd-highlight">
    <h2>추천 정보 알림</h2>
    <!-- <toggle-button v-model="myDataVariable"/> -->
    <toggle-button :value="myrecommendedinfo_notice"
               color="#82C7EB"
               :sync="true"
               :labels="true"
               @change="changeRecommendedinfoNotice ()"/>  
    </div>
    <div class="p-2 d-flex bd-highlight">
    <h3>커뮤니티 새 댓글 알림</h3>
    <toggle-button :value="mynewcomments_notice"
               color="#82C7EB"
               :sync="true"
               :labels="true"
               @change="changeNewcommentsNotice ()"/>
    </div>
    <div class="p-2 d-flex bd-highlight">
    <h2>새 쪽지 알림</h2>
    <toggle-button :value="mynewtwits_notice"
               color="#82C7EB"
               :sync="true"
               :labels="true"
               @change="changeNewtwitsNotice ()"/>  
    </div>
    <div class="p-2 d-flex bd-highlight">
    <h2>나이트모드</h2>
    <toggle-button :value="mynightmode_notice"
               color="#82C7EB"
               :sync="true"
               :labels="true"
               @change="changeNightmode ()"/>
    
    </div>
    <button type="button" class="btn btn-success mb-2" v-if="changes" @click="settingsUpdate">저장</button>
    <button type="button" class="btn btn-success" v-else disabled>저장</button>
  </div>  



    </div>
</div>
</template>

<script>
// import { mapState } from 'vuex'

export default {
  name: 'AlertsSettings',
  data() {
    return {
      mywater_notice: this.$store.state.water_notice,
      myrecommendedinfo_notice: this.$store.state.recommendedinfo_notice,
      mynewcomments_notice: this.$store.state.newcomments_notice,
      mynewtwits_notice: this.$store.state.newtwits_notice,
      mynightmode_notice: this.$store.state.nightmode_notice,
      changes: false,
    }
  },
  methods: {
    changeWaterNotice () {
      this.mywater_notice = !this.mywater_notice
    },
    changeRecommendedinfoNotice () {
      this.myrecommendedinfo_notice = !this.myrecommendedinfo_notice
    },
    changeNewcommentsNotice () {
      this.mynewcomments_notice = !this.mynewcomments_notice
    },
    changeNewtwitsNotice () {
      this.mynewtwits_notice = !this.mynewtwits_notice
    },
    changeNightmode () {
      this.mynightmode_notice = !this.mynightmode_notice
    },
    settingsUpdate() {
      this.$store.dispatch('updateNotcie', this.mywater_notice, this.myrecommendedinfo_notice, this.mynewcomments_notice, this.mynewtwits_notice, this.mynightmode_notice)
    }
  
  },
    created() {
    this.$store.dispatch('getNoticeSettings')
  },
  watch: {
    mywater_notice: function () {
      if (this.changes) {
        this.changes = true
      } else {
        this.changes = ! this.changes
      }
    },
    myrecommendedinfo_notice: function () {
      if (this.changes) {
        this.changes = true
      } else {
        this.changes = ! this.changes
      }
    },
    mynewcomments_notice: function () {
      
      if (this.changes) {
        this.changes = true
      } else {
        this.changes = ! this.changes
      }
    },
    mynewtwits_notice: function () {
      if (this.changes) {
        this.changes = true
      } else {
        this.changes = ! this.changes
      }
      
    },
    mynightmode_notice: function () {
      if (this.changes) {
        this.changes = true
      } else {
        this.changes = ! this.changes
      }
      
    },

  }
  // computed: {
  //   ...mapState([
  //     'water_alerts',
  //     'recommendedinfo_alerts',
  //     'newcomments_alerts',
  //     'newtwits_alerts',
  //     'nightmode_alerts',
  //   ]),
  }


</script>

<style scoped>
.container {
  position: relative;
  width: 375px;
  height: 812px;

  background: #FFFFFF;
}
</style>