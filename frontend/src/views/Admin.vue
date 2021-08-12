<template>
  <div id="admin">
    <div id="side-menu">
      <div class="sub_menu">
        <h2>ADMIN</h2>
        <hr />
        <ul>
          <li><router-link to="/admin/users">회원 관리</router-link></li>
          <li><router-link to="/admin/crops">농작물 관리</router-link></li>
          <li><router-link to="/admin/posts">커뮤니티 관리</router-link></li>
          <li><router-link to="/admin/qna">문의사항 관리</router-link></li>
          <li><router-link to="/admin/message">전체 알림 메세지</router-link></li>
        </ul>
      </div>
    </div>
    <div class="container">
      <router-view></router-view>
    </div>
  </div>
</template>

<script>
export default {};
</script>

<style scoped>
@media (min-width: 601px) {
  #admin {
    font-family: Noto Sans KR;
    font-style: normal;
    height: 100%;
  }
  #side-menu {
    left: 0;
    position: fixed;
    width: 20%;
    height: 100%;
    color: #ffffff;
    background: #b6c790;
    display: inline-block;
  }
  ul li {
    text-decoration: none;
  }
  .container {
    width: 80%;
    float: right;
    display: inline-block;
  }
  h3 {
    color: #ffffff;
  }
}
@media (max-width: 600px) {
  #admin {
    font-family: Noto Sans KR;
    font-style: normal;
  }
  #side-menu {
    text-align: center;
    background: #b6c790;
  }
  .container {
  }
}
</style>
