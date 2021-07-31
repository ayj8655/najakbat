<template>
  <div class="nav">
      <router-link to="/main"><img src="@/assets/mococo.png" id="left"/></router-link>
      <router-link to="/myalerts"><img src="@/assets/noti.png" /></router-link>
      <router-link to="/">프로필</router-link>
  </div>
</template>

<script>
export default {};
</script>

<style scoped>
@media (max-width: 600px) {
  .nav {
    border-bottom: 2px solid #b6c790;
    display: inline-block;
  }
  #left {
      float: left;
  }
  #right {
      float: right;
      display: inline-block;
  }
  img {
    width: 15%;
  }
}
</style>
