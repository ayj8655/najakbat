<template>
  <div class="container">
    <div class="py-4">
      <h2 class="mb-5">로그인</h2>
        <div class="mb-3 container w-75">
          <label for="username" class="form-label d-flex align-items-start">아이디</label>
          <input type="text" class="form-control mb-3" id="username" placeholder="아이디를 입력하세요" v-model="username">
          <label for="password" class="form-label d-flex align-items-start">비밀번호</label>
          <input type="password" class="form-control mb-3" id="password" placeholder="비밀번호를 입력하세요" v-model="password">
        </div>
        <div class="d-grid gap-2 w-75 container">
          <button class="btn btn-outline-success" :disabled="!this.username || !this.password" @click.prevent="[login([username, password]), loginError()]">로그인 하기</button>
          <button class="btn btn-success" @click="goToSignup">회원가입</button>
        </div>
        <div class="mt-3">
          <span class="mx-1" @click="goToId">아이디 찾기</span> |
          <span class="mx-1" @click="goToPassword">비밀번호 찾기</span>
        </div>
    </div>
  </div>
</template>

<script>
import { mapActions } from 'vuex'
import { mapState } from 'vuex'
import router from "@/router"

export default {
  name: 'Login',
  data() {
    return {
      username: null,
      password: null
    }
  },
  methods: {
    ...mapActions([
      'login'
    ]),
    goToSignup() {
      router.push({name: 'Signup'})
    },
    goToId() {
      router.push({name: 'FindId'})
    },
    goToPassword() {
      router.push({name: 'FindPassword'})
    },
    loginError() {
      setTimeout(() => {
        if (!this.loginCertificate) {
          this.$fire({
              title: "실패",
              text: "로그인 정보가 일치하지 않습니다",
              type: "error",
            })
        }
      }, 200);
    }
  },
  created() {
    if(this.$store.state.accessToken) {
      router.push({name: 'Main'})
    }
  },
  computed: {
    ...mapState([
      'userCertificate'
      ]),
      loginCertificate() {
        return this.userCertificate
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
  /* position: relative; */
}
</style>