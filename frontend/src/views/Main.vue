<template>
  <div>
      <header-nav></header-nav>
      <menubar id="menubar"></menubar>
  </div>
</template>

<script>
import HeaderNav from '../components/Menu/HeaderNav.vue'
import Menubar from '../components/Menu/Menubar.vue'
export default {
  components: { Menubar, HeaderNav },

}
</script>

<style>

#menubar {
  position: fixed;
  background-color: #B6C790; 
  bottom: 0;
}
</style>