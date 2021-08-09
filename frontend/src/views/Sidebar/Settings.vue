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
    <toggle-button :value="water_notice"
               color="#82C7EB"
               :sync="true"
               :labels="true"
               @change="changeWaterNotice ()"/>
    </div>
    <div class="p-2 d-flex bd-highlight">
    <h2>추천 정보 알림</h2>
    <!-- <toggle-button v-model="myDataVariable"/> -->
    <toggle-button :value="recommendedinfo_notice"
               color="#82C7EB"
               :sync="true"
               :labels="true"
               @change="changeRecommendedinfoNotice ()"/>  
    </div>
    <div class="p-2 d-flex bd-highlight">
    <h3>커뮤니티 새 댓글 알림</h3>
    <toggle-button :value="newcomments_notice"
               color="#82C7EB"
               :sync="true"
               :labels="true"
               @change="changeNewcommentsNotice ()"/>
    </div>
    <div class="p-2 d-flex bd-highlight">
    <h2>새 쪽지 알림</h2>
    <toggle-button :value="newtwits_notice"
               color="#82C7EB"
               :sync="true"
               :labels="true"
               @change="changeNewtwitsNotice ()"/>  
    </div>
    <div class="p-2 d-flex bd-highlight">
    <h2>나이트모드</h2>
    <toggle-button :value="nightmode_notice"
               color="#82C7EB"
               :sync="true"
               :labels="true"
               @change="changeNightmode ()"/>
    </div>
    <div class="p-2 d-flex bd-highlight">
    <h2>알림시간</h2>
    <h3>
      {{ noticeTime }}
    </h3>
    </div>
    <button type="button" class="btn btn-success mb-2" v-if="changes" @click="settingsUpdate">저장</button>
    <!-- <button type="button" class="btn btn-success mb-2" v-if="changes" @click="updateNotice ([this.water_notice, this.recommendedinfo_notice, this.newcomments_notice, this.newtwits_notice, this.nightmode_notice])">저장</button> -->
    <button type="button" class="btn btn-success" v-else disabled>저장</button>
  </div>  



    </div>
</div>
</template>

<script>
import { mapState } from 'vuex'
import { mapActions } from 'vuex'

export default {
  name: 'AlertsSettings',
  data() {
    return {
      // mywater_notice: this.$store.state.water_notice,
      changes: false,
    }
  },
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
.container {
  position: relative;
  width: 375px;
  height: 812px;

  background: #FFFFFF;
}
</style>
