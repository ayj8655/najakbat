<template>
  <div class="container">
    <div class="py-4">
      <h2 class="mb-5">새로운 비밀번호 설정</h2>
      <div class="mb-3 container w-75">
        <label class="form-label d-flex align-items-start">아이디</label>
        <input type="text" class="form-control mb-3" placeholder="아이디를 입력하세요" v-model="userId">
        <label class="form-label d-flex align-items-start">비밀번호</label>
        <input type="password" class="form-control mb-3" v-validate="'min:8'" name="min_field" placeholder="최소 8글자 이상 입력하세요" v-model="password">
        <span class="text-danger d-flex align-items-start mb-3" v-if="errors.first('min_field')">비밀번호가 너무 짧습니다</span>
        <label class="form-label d-flex align-items-start">비밀번호 확인</label>
        <input type="password" class="form-control  mb-3" placeholder="비밀번호를 한번 더 입력하세요" v-model="passwordConfirmation">
        <span class="text-danger d-flex align-items-start" v-if="password != passwordConfirmation">비밀번호가 서로 다릅니다</span>
      </div>
      <button class="btn btn-success" :disabled="!passwordCheck" @click.prevent="chagePassword">수정하기</button>
    </div>
  </div>
</template>

<script>
import axios from "axios"
import router from "@/router"

export default {
  name: 'FindPasswordNext',
  data() {
    return {
      userId: null,
      password: null,
      passwordConfirmation: null,
      passwordCheck: false,
      phoneNum: null
    }
  },
  methods: {
    chagePassword() {
      axios.post('user/pass/pwFind', {
        id: this.userId,
        password: this.password,
        phone: this.phoneNum
      })
      .then(res => {
        res
        localStorage.removeItem('FindUserPhone');
        router.push({name: 'Login'})
      })
      .catch(err => {
        console.error(err);
      })
    }
  },
  watch: {
    passwordConfirmation() {
      if (this.password && !this.errors.first('min_field')) {
        if (this.password === this.passwordConfirmation) {
          this.passwordCheck = true
          return
        }
      }
      this.passwordCheck = false
    }
  },
  created() {
    this.phoneNum = this.$store.state.findUserPhone
  }
}
</script>

<style>

</style>