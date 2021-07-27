<template>
  <div class="container sidebar-bg">
    <sidebar></sidebar>
  </div>
</template>

<script>
import Sidebar from '@/components/Sidebar/Sidebar.vue'

export default {
  name: 'sidebarmain',
  data () {
    return {
      title: 'SIDEBAR'
    }
  },
  components: {
    Sidebar
  }
}
</script>

<style scoped>
.sidebar-bg {
  position: relative;
  width: 375px;
  height: 812px;
  background: #FFFFFF;

}

</style>