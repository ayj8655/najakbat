<template>
  <div class="container">
    <div class="mycontainer py-4 text-center">
      <div class="my-5">
        <p>회원님의 아이디는</p>
        <h4>{{ viewId }}</h4>
        <p>입니다</p>
      </div>
      <div class="d-flex flex-row-reverse">
          <router-link to="/findpassword">비밀번호 찾기</router-link>
      </div>
      <div class="my-3">
          <router-link to="/login"><button class="btn btn-success">로그인 하기</button></router-link>
      </div>
    </div>
  </div>
</template>

<script>
import { mapState } from 'vuex'

export default {
  name: 'FindIdNext',
  computed: {
    ...mapState([
      'myId'
    ]),
    viewId() {
      return this.myId
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