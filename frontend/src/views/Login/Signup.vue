<template>
  <div class="container">
    <div class="mycontainer py-4">
      <h2 class="mb-5">회원가입</h2>
      <form action="">
        <div class="mb-3 container w-75">
          <label for="" class="form-label d-flex align-items-start">아이디</label>
          <input type="text" class="form-control mb-3" placeholder="아이디를 입력하세요" v-model="credentials.username" @change="IdConfirmation">
          <button @click.prevent="checkId">중복 확인</button>
          <label for="" class="form-label d-flex align-items-start">비밀번호</label>
          <input type="password" class="form-control mb-3" v-validate="'min:8'" name="min_field" placeholder="최소 8글자 이상 입력하세요" v-model="credentials.password">
          <span v-if="errors.first('min_field')">올바른 정보가 아닙니다</span>
          <label for="" class="form-label d-flex align-items-start">비밀번호 확인</label>
          <input type="password" class="form-control  mb-3" placeholder="비밀번호를 한번 더 입력하세요" v-model="passwordConfirmation">
          <span v-if="credentials.password != passwordConfirmation">비밀번호가 서로 다릅니다</span>
          <label for="" class="form-label d-flex align-items-start">닉네임</label>
          <input type="text" class="form-control  mb-3" name="between_field" placeholder="12자 이내로 입력하세요" v-model="credentials.nickname">
          <span v-if="credentials.nickname && credentials.nickname.length > 12">닉네임이 너무 길어요</span>
          <label for="" class="form-label d-flex align-items-start">휴대폰 번호</label>
          <input type="text" class="form-control" v-validate="'digits:11'" name="digits_field" placeholder="휴대폰 번호를 입력하세요" v-model="credentials.phone">
          <span v-if="errors.first('digits_field')">올바른 정보가 아닙니다</span>
          <button @click.prevent="checkPhone">인증번호 받기</button>
          <PhoneCertified />
        </div>
        <router-link to="/signupnext"><button class="btn btn-success">회원가입 하기</button></router-link>
      </form>
    </div>
  </div>
</template>

<script>
import axios from 'axios'
import PhoneCertified from '@/components/Login/PhoneCertified.vue'

export default {
  name: 'Signup',
  components: {
    PhoneCertified
  },
  data() {
    return {
      credentials: {
        username: null,
        password: null,
        nickname: null,
        phone: null
      },
      passwordConfirmation: null,
      phoneCertified: false,
      idConfirmation: false
    }
  },
  methods: {
    IdConfirmation() {
      this.idConfirmation = false
      console.log(this.idConfirmation)
    },
    checkId() {
      this.idConfirmation = true
      console.log(this.idConfirmation)
    },
    checkPhone() {
      axios.post('http://localhost:8080/user/phone', {
        phone: this.credentials.phone
      })
      .then(res => {
        console.log(res)
        // const phoneNum = res.data
      })
      .catch(err => {
        console.error(err);
      })
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