<template>
    <div>
        <header-nav></header-nav>
        <router-view></router-view>
        <div id="foot"></div>
        <menubar id="menubar"></menubar>
    </div>
</template>

<script>

import HeaderNav from "@/components/Menu/HeaderNav.vue";
import Menubar from "@/components/Menu/Menubar.vue";
//import { mapState } from 'vuex'

export default {
  components: { 
    HeaderNav,
    Menubar,
  }, 
  data() {
    return {
      id: null,
      componentNum: 0
    };
  },
}
</script>


<style scoped>
  #foot {
  height: 18%;
  }
</style>