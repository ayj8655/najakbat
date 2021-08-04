<template>
<div>
  <header-nav></header-nav>
  <router-view></router-view>
  <h3 class="mb-3 mt-5 fw-bold">알림</h3>
  <received></received>
  <sent></sent>
  
  <div id="foot"></div>
  <menubar id="menubar"></menubar>
</div>
</template>

<script>
import HeaderNav from '@/components/Menu/HeaderNav.vue';
import Menubar from '@/components/Menu/Menubar.vue';
import Received from '../../components/Message/Received.vue';
import Sent from '../../components/Message/Sent.vue';
export default {
  components: { 
    HeaderNav,
    Menubar,
    Received,
    Sent,
    // Sent,
    // Received,
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
</style>