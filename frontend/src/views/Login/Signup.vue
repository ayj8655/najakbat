<template>
  <div class="container">
    <div class="py-4">
      <h2 class="mb-5">회원가입</h2>
      <form action="">
        <div class="mb-3 container w-75">
          <label class="form-label d-flex align-items-start">아이디</label>
          <input type="text" class="form-control mb-3" placeholder="아이디를 입력하세요" v-model="username">
          <div class="d-flex justify-content-end">
            <span class="me-auto text-danger" v-if="idOverlap">이미 사용중인 아이디입니다</span>
            <span class="me-auto" v-else-if="check.id">사용 가능한 아이디입니다</span>
            <button class="btn btn-success" :disabled=!username @click.prevent="checkId">중복확인</button>
          </div>
          <label class="form-label d-flex align-items-start">비밀번호</label>
          <input type="password" class="form-control mb-3" v-validate="'min:8'" name="min_field" placeholder="최소 8글자 이상 입력하세요" v-model="password">
          <span class="text-danger" v-if="errors.first('min_field')">비밀번호가 너무 짧습니다</span>
          <label class="form-label d-flex align-items-start">비밀번호 확인</label>
          <input type="password" class="form-control  mb-3" placeholder="비밀번호를 한번 더 입력하세요" v-model="passwordConfirmation">
          <span class="text-danger" v-if="password != passwordConfirmation">비밀번호가 서로 다릅니다</span>
          <label class="form-label d-flex align-items-start">닉네임</label>
          <input type="text" class="form-control  mb-3" name="between_field" placeholder="12자 이내로 입력하세요" v-model="nickname">
          <div class="d-flex justify-content-end">
            <span class="text-danger me-auto" v-if="nickname && nickname.length > 12">닉네임이 너무 길어요</span>
            <span class="text-danger me-auto" v-else-if="nicknameOverlap">이미 사용중인 닉네임입니다</span>
            <span class="me-auto" v-else-if="check.nickname">사용 가능한 닉네임입니다</span>
            <button class="btn btn-success" :disabled="!nickname || nickname.length > 12" @click.prevent="checkNickname(nickname)">중복확인</button>
          </div>
          <label class="form-label d-flex align-items-start">휴대폰 번호</label>
          <input type="text" class="form-control" v-validate="'digits:11'" name="digits_field" placeholder="휴대폰 번호를 입력하세요" v-model="phone">
          <div class="d-flex justify-content-end my-3" v-if="errors.first('digits_field') || !this.phone">
            <button class="btn btn-success" :disabled=true>인증번호 받기</button>
          </div>
          <PhoneCertified :phoneNum="phone" v-else @phonecertified="phonecertified" />
         
        </div>
        <button
         class="btn btn-success"
         :disabled="!(this.check.id && this.check.password && this.check.nickname && this.check.phone && (this.password === this.passwordConfirmation))"
         @click.prevent="signup([username, password, nickname, phone])"
        >회원가입 하기</button>
      </form>
    </div>
  </div>
</template>

<script>
import PhoneCertified from '@/components/Login/PhoneCertified.vue'
import { mapActions } from 'vuex'
import axios from "axios"
import router from "@/router"

export default {
  name: 'Signup',
  components: {
    PhoneCertified
  },
  data() {
    return {
      check: {
        id: false,
        password: false,
        nickname: false,
        phone:false
      },
      username: null,
      password: null,
      passwordConfirmation: null,
      nickname: null,
      phone: null,
      idOverlap: false,
      nicknameOverlap: false
    }
  },
  methods: {
    ...mapActions([
      'signup'
    ]),
    phonecertified() {
      this.check.phone = true
    },
    checkId() {
      axios.get(`user/pass/confirmId/${this.username}`)
      .then(res => {
        if (res.data === 'success') {
          this.idOverlap = false
          this.check.id = true
        }
        else {
          this.idOverlap = true
        }
      })
      .catch(err => {
        console.error(err);
      })
    },
    checkNickname(nick) {
      axios.get(`user/pass/confirmNickname/${nick}`)
      .then(res => {
        if (res.data === 'success') {
          this.nicknameOverlap = false
          this.check.nickname = true
        }
        else {
          this.nicknameOverlap = true
        } 
      })
      .catch(err => {
        console.error(err);
      })
    }
  },

  created() {
    if(this.$store.state.accessToken) {
      router.push({name: 'Main'})
    }
  },

  watch: {
    username() {
      this.check.id = false
    },
    phone() {
      this.check.phone = false
    },
    nickname() {
      this.check.nickname = false
    },
    passwordConfirmation() {
      if (this.password && !this.errors.first('min_field')) {
        if (this.password === this.passwordConfirmation) {
          this.check.password = true
          return
        }
      }
      this.check.password = false
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