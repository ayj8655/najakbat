<template>
  <div>
    <header-nav></header-nav>
    <Sidebar id="side-bar" />
    <router-view></router-view>
    <div v-show="this.$store.state.sidebar == false">
    <div :class="{ 'foot-bg': pathFlag }" id="foot"></div>
    <menubar id="menubar"></menubar>
    </div>
  </div>
</template>

<script>

import HeaderNav from "@/components/Menu/HeaderNav.vue";
import Menubar from "@/components/Menu/Menubar.vue";
import Sidebar from "@/components/Sidebar/Sidebar.vue";
//import { mapState } from 'vuex'

export default {
  components: {
    HeaderNav,
    Menubar,
    Sidebar
  },
  data() {
    return {
      id: null,
      componentNum: 0,
      pathFlag: false,
    };
  },
  watch: {
    $route(to, from) { 
      if (to.path != from.path) {
        if(to.path.includes("detail")) this.pathFlag = true;
        else this.pathFlag = false;
      } 
    },
  },
}
</script>

<style scoped>
#side-bar {
  z-index: 1;
  position: fixed;
}
#foot {
  height: 100px;
}
.foot-bg {
  /* background-color: #446631; */
  background-color: #4A643B;
}
</style>
