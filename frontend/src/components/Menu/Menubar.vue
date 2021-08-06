<template>
  <div id="menubar">
    <nav>
      <router-link to="/"><img src="@/assets/menu_home.png" id="menu_icon"></router-link>
      <router-link to="/dict"><img src="@/assets/menu_dict.png" id="menu_icon"></router-link>
      <router-link to="/mycrop"><img src="@/assets/menu_mylist.png" id="menu_icon"></router-link>
      <router-link to="/community"><img src="@/assets/menu_community.png" id="menu_icon"></router-link>
      <img src="@/assets/menu_etc.png" id="menu_icon" @click="Sidebaron">
    </nav>
  </div>
</template>

<script>
import { mapState } from 'vuex'
// import Sidebar from '../Sidebar/Sidebar.vue'

export default {
  components: {
    // Sidebar
  },
  computed: {
    ...mapState ([
      'sidebar'
    ])
  },
  methods: {
    Sidebaron () {
      this.$store.state.sidebar = ! this.$store.state.sidebar
      console.log(this.$store.state.sidebar)
    }
  }
};
</script>

<style>
#menu_icon {
    width: 18%;
    margin: 10px 2px;
}
</style>
