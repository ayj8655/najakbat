<template>
  <div class="container">
    <div class="mycontainer py-4">
      <h2 class="mb-5">로그인</h2>
      <form action="">
        <div class="mb-3 container w-75">
          <label for="username" class="form-label d-flex align-items-start">아이디</label>
          <input type="text" class="form-control mb-3" id="username" placeholder="아이디를 입력하세요" v-model="credentials.username">
          <label for="password" class="form-label d-flex align-items-start">비밀번호</label>
          <input type="password" class="form-control mb-3" id="password" placeholder="비밀번호를 입력하세요" v-model="credentials.password">
          <div class="d-flex flex-row-reverse">
            <router-link to="/signup">회원가입</router-link>
            <router-link to="/findid">아이디 찾기</router-link>
            <router-link to="/findpassword">비밀번호 찾기</router-link>
          </div>
        </div>
        <button class="btn btn-secondary" v-if="!credentials.username || !credentials.password">로그인 하기</button>
        <button class="btn btn-success" v-else>로그인 하기</button>
      </form>
    </div>
  </div>
</template>

<script>
export default {
  name: 'Login',
  data() {
    return {
      credentials: {
        username: null,
        password: null
      }
    }
  }

}
</script>

<style scoped>
.mycontainer {
  background: none;
  /* width: 500px; */
  /* margin: 8rem auto 0; */
  /* text-align: center; */
  box-shadow: 0 1rem 1rem 0 rgba(0, 0, 0, .15);
  position: relative;
}
</style>