<template>
<div>

  <div>MyAlerts</div>
  <div v-for="searchNotice in searchNotices" :key="searchNotice.noticeNumber">
    <div>
      {{searchNotice.content}}
    </div>
    <div>
      {{searchNotice.date}}
    </div>
  </div>
</div>
</template>

<script>
import { mapState } from 'vuex'
export default {
  name: 'Myalerts',
  created() {
    this.$store.dispatch('getSearchNotice')
  },
  computed: {
    ...mapState([
      'searchNotices',
    ])
  }
}
</script>

<style>

</style>