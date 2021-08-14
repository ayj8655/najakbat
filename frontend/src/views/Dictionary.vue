<template>
<div>
  <header-nav></header-nav>
  <Sidebar id="side-bar" />

  <router-view></router-view>
  <div v-show="this.$store.state.sidebar == false">
    <div id="foot"></div>
    <menubar id="menubar"></menubar>
  </div>
</div>
</template>

<script>
import HeaderNav from '../components/Menu/HeaderNav.vue';
import Menubar from '../components/Menu/Menubar.vue';
import Sidebar from "../components/Sidebar/Sidebar.vue";

export default {
  components: { 
    HeaderNav,
    Menubar,
    Sidebar
  },
}
</script>

<style>
#side-bar {
  z-index: 1;
  position: fixed;
}

#foot {
  height: 150px;
}
#menubar {
  position: fixed;
  background-color: #B6C790; 
  bottom: 0;
}
</style>
