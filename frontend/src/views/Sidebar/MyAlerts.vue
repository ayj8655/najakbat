<template>
<div>

  <div>MyAlerts</div>
  <div v-for="notice in notices" :key="notice.noticeNumber">
    <div>
      {{notice.content}}
    </div>
    <div>
      {{notice.date}}
    </div>
  </div>
</div>
</template>

<script>
// import { mapActions } from 'vuex'
export default {
  name: 'Myalerts',
  data () {
    return {
      notices: this.$store.state.searchNotices
    }
  },
  created() {
    this.$store.dispatch('getSearchNotice')
  }
}
</script>

<style>

</style>