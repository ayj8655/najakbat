<template>
  <div class="container">
    <div v-for="sentMessage in sentMessages" :key="sentMessage.noticeNumber">
    <div class="container px-0">
      <div class="isRead-false border border-end-0 border-start-0 bg-white">
        <div class="container notice mt-2">
          <div class="row">
            <div class="col-1 px-0">
              
            </div>
            <div class="col-2 px-0">
             
            </div>
            <div class="col-8 px-0">
              <div class="content">
                {{sentMessage.content}}
              </div>
              <div class="date mx-1">
                {{ sentMessage.time | moment('YYYY-MM-DD HH:mm:ss') }} 
              </div>
            </div>
            <div class="col-1 px-0"></div>
          </div>
        </div>
      </div>
    </div>
  </div>
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