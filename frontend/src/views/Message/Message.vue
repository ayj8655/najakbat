<template>
<div>
  <header-nav></header-nav>
  <router-view></router-view>
  <h3 class="mb-3 mt-5 fw-bold">메세지</h3>
  <div class="container w-75 d-flex justify-content-evenly my-3 mb-5">
    <span @click="changeComponent(0)" v-bind:class="[componentNum === 0 ? 'select-font' : 'select-font-none']">받은쪽지</span>
    <span class="select-font-none"> | </span>
    <span @click="changeComponent(1)" v-bind:class="[componentNum === 1 ? 'select-font' : 'select-font-none']">보낸쪽지</span>

  </div>
  <div class="container">
    <Received v-if="componentNum === 0"/>
    <Sent v-else/>
  </div>
  <Messageform/>
  <div id="foot"></div>
  <menubar id="menubar"></menubar>
</div>
</template>

<script>
import HeaderNav from '@/components/Menu/HeaderNav.vue';
import Menubar from '@/components/Menu/Menubar.vue';
import Received from '../../components/Message/Received.vue';
import Sent from '../../components/Message/Sent.vue';
import Messageform from '../../components/Message/Messageform.vue';


export default {
  components: { 
    HeaderNav,
    Menubar,
    Received,
    Sent,
    Messageform,
    // Sent,
    // Received,
  },
  data() {
    return {
      componentNum: 0
    };
  },
  methods: {
    changeComponent(num) {
      this.componentNum = num
    }
  },
}
</script>

<style>
#foot {
  height: 150px;
}
#menubar {
  position: fixed;
  background-color: #B6C790; 
  bottom: 0;
}
.select-font-none {
  font-family: Noto Sans KR;
  font-style: normal;
  font-weight: bold;
  font-size: 13px;
  line-height: 17px;
  color: #999999;
}

.select-font {
  font-family: Noto Sans KR;
  font-style: normal;
  font-weight: bold;
  font-size: 13px;
  line-height: 17px;
  color: #446631;
}
</style>