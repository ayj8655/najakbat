<template>
  <div>{{ sentMessages }}

  </div>
</template>

<script>
import { mapState } from 'vuex'

export default {
  computed: {
  ...mapState ([
    'sentMessages'
    ])
  },
  created() {
    this.$store.dispatch('getSentMessages')
  },

}
</script>

<style>

</style>