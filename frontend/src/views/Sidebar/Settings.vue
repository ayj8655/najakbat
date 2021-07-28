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
    <toggle-button :value="mywater_alerts"
               color="#82C7EB"
               :sync="true"
               :labels="true"
               @change="changeWaterAlerts ()"/>
    </div>
    <div class="p-2 d-flex bd-highlight">
    <h2>추천 정보 알림</h2>
    <!-- <toggle-button v-model="myDataVariable"/> -->
    <toggle-button :value="myrecommendedinfo_alerts"
               color="#82C7EB"
               :sync="true"
               :labels="true"
               @change="changeRecommendedinfoAlerts ()"/>  
    </div>
    <div class="p-2 d-flex bd-highlight">
    <h3>커뮤니티 새 댓글 알림</h3>
    <toggle-button :value="mynewcomments_alerts"
               color="#82C7EB"
               :sync="true"
               :labels="true"
               @change="changeNewcommentsAlerts ()"/>
    </div>
    <div class="p-2 d-flex bd-highlight">
    <h2>새 쪽지 알림</h2>
    <toggle-button :value="mynewtwits_alerts"
               color="#82C7EB"
               :sync="true"
               :labels="true"
               @change="changeNewtwitsAlerts ()"/>  
    </div>
    <div class="p-2 d-flex bd-highlight">
    <h2>나이트모드</h2>
    <toggle-button :value="mynightmode_alerts"
               color="#82C7EB"
               :sync="true"
               :labels="true"
               @change="changeNightmode ()"/>
    
    </div>
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
      mywater_alerts: this.$store.state.water_alerts,
      myrecommendedinfo_alerts: this.$store.state.recommendedinfo_alerts,
      mynewcomments_alerts: this.$store.state.newcomments_alerts,
      mynewtwits_alerts: this.$store.state.newtwits_alerts,
      mynightmode_alerts: this.$store.state.nightmode_alerts,
    }
  },
  methods: {
    changeWaterAlerts () {
      this.mywater_alerts = !this.mywater_alerts
    },
    changeRecommendedinfoAlerts () {
      this.myrecommendedinfo_alerts = !this.myrecommendedinfo_alerts
    },
    changeNewcommentsAlerts () {
      this.mynewcomments_alerts = !this.mynewcomments_alerts
    },
    changeNewtwitsAlerts () {
      this.mynewtwits_alerts = !this.mynewtwits_alerts
    },
    changeNightmode () {
      this.mynightmode_alerts = !this.mynightmode_alerts
    },

  },
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