<template>
  <div>{{ receivedMessages }}</div>
</template>

<script>
import { mapState } from 'vuex'

export default {
    computed: {
  ...mapState ([
    'receivedMessages'
    ])
  },
  created() {
    this.$store.dispatch('getReceivedMessages')
  },
}
</script>

<style>

</style>