<template>
  <div class="container">
    <div class="mycontainer py-4">
      <h2 class="mb-5">비밀번호 찾기</h2>
      <form action="">
        <div class="mb-3 container w-75">
          <label for="" class="form-label d-flex align-items-start">아이디</label>
          <input type="text" class="form-control mb-3" id="" placeholder="아이디를 입력하세요">
          <label for="" class="form-label d-flex align-items-start">이메일</label>
          <input type="text" class="form-control mb-3" id="" placeholder="이메일을 입력하세요">
          <label for="" class="form-label d-flex align-items-start">인증번호</label>
          <input type="text" class="form-control mb-3" id="" placeholder="인증번호를 입력하세요">
        </div>
        <router-link to="/"><button class="btn btn-success">비밀번호 찾기</button></router-link>
      </form>
    </div>
  </div>
</template>

<script>
export default {

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