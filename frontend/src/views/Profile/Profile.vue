<template>
  <div>
    <header-nav></header-nav>
    <div v-if="getNickname">
      <h3>{{ getNickname }}</h3>
    </div>
    <div v-else>
      <h1>존재하지 않는 페이지입니다</h1>
    </div>
    
    <div id="foot"></div>
    <menubar id="menubar"></menubar>
  </div>
</template>

<script>
// import axios from "axios";
import HeaderNav from "@/components/Menu/HeaderNav.vue";
import Menubar from "@/components/Menu/Menubar.vue";
import { mapState } from 'vuex'

export default {
  components: { Menubar, HeaderNav },
  data() {
    return {
      id: null,
    };
  },
  computed: {
    ...mapState([
      'targetUser'
    ]),
    getNickname() {
      if(this.targetUser) {
        return this.targetUser[1]
      }
      else {
        return false
      }
    }
  },
  created() {
    console.log(this.$route.params.username)
    this.$store.dispatch('getProfile', this.$route.params.username)
  },
};
</script>

<style scoped>
#foot {
  height: 150px;
}
#menubar {
  position: fixed;
  background-color: #b6c790;
  bottom: 0;
}

#row {
  white-space: nowrap; /* 가로스크롤시 중요한 속성 */
  overflow-x: auto;
  overflow-y: hidden;
  padding: 10px 10px 5px;
  /* background: #efefef; */
  width: auto;
}

#row .items {
  display: inline-block;
  /* margin-left: 10px; */
  width: 100px;
}

#row .items:first-child {
  margin-left: 0;
}

#row .items p {
  margin-bottom: 8px;
  text-indent: 7px;
}

#row .items ul {
  border-radius: 3px;
  border: 1px solid #b5b5b5;
  height: 135px;
  overflow-y: scroll;
  padding: 3px 3px 8px;
  background: #fff;
}

#row .items ul li a {
  display: block;
  overflow: hidden;
  margin-top: 8px;
  padding: 3px;
  color: black;
  font-size: 12px;
  text-decoration: none;
}

#row .items ul li:first-child a {
  margin-top: 3px;
}

#row .items ul li.on a {
  border: 1px solid #c9cccf;
  border-radius: 3px;
  font-weight: bold;
  background-color: #efefef;
}
</style>
