<template>
  <div id="menubar">
    <nav>
      <router-link to="/main"><img src="@/assets/menu_home.png"></router-link>
      <router-link to="/dict"><img src="@/assets/menu_dict.png"></router-link>
      <router-link to="/mylist"><img src="@/assets/menu_mylist.png"></router-link>
      <router-link to="/community"><img src="@/assets/menu_community.png"></router-link>
      <router-link to="/sidebarmain"><img src="@/assets/menu_etc.png"></router-link>
    </nav>
  </div>
</template>

<script>
export default {};
</script>

<style>
img {
    width: 18%;
    margin: 10px 2px;
}
</style>
