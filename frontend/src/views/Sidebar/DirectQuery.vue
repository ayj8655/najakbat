<template>
<div>
  <header-nav></header-nav>
  <Sidebar id="side-bar" />


<div class="container">
  <h3 class="mb-3 mt-5 fw-bold">메세지</h3>
  <div class="container">
  <Qna/>
  </div>

  <!-- <h3>문의사항 분류</h3>
  <select v-model="selected">
    <option disabled value="">문의사항을 골라주세요</option>
    <option>A</option>
    <option>B</option>
    <option>C</option>
  </select>
  <h3>문의사항 내용</h3>
<textarea v-model="message" placeholder="여러줄을 입력해보세요"></textarea> -->
</div>
  <div v-show="this.$store.state.sidebar == false">
    <div id="foot"></div>
    <menubar id="menubar"></menubar>
  </div>
</div>
</template>

<script>
import HeaderNav from "@/components/Menu/HeaderNav.vue";
import Menubar from "@/components/Menu/Menubar.vue";
import Sidebar from "@/components/Sidebar/Sidebar.vue";
import Qna from "../../components/Sidebar/Qna.vue";

export default {
  components: { Menubar, HeaderNav, Sidebar, Qna},
  data () {
    return {
      selected: '',
      message: '',
    }
  }
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
  background-color: #b6c790;
  bottom: 0;
}
</style>