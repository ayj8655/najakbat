<template>
  <div class="container">
    <div class="py-4">
      <h2 class="mb-5">비밀번호 찾기</h2>
      <form action="">
        <div class="mb-3 container w-75">
          <label for="" class="form-label d-flex align-items-start">이름</label>
          <input type="text" class="form-control mb-3" placeholder="이름을 입력하세요" v-model="name">
          <label class="form-label d-flex align-items-start">휴대폰 번호</label>
          <input type="text" class="form-control" v-validate="'digits:11'" name="digits_field" placeholder="휴대폰 번호를 입력하세요" v-model="phone">
          <div class="d-flex justify-content-end my-3" v-if="errors.first('digits_field') || !this.phone">
            <button class="btn btn-success" :disabled=true>인증번호 받기</button>
          </div>
          <FindIdPhoneCertified :phoneNum="phone" :userName="name" v-else @phonecertified="phonecertified" />
        </div>
        <button class="btn btn-success" :disabled="!checkPhone" @click.prevent="putNextPage(phone)">비밀번호 찾기</button>
      </form>
    </div>
  </div>
</template>

<script>
import FindIdPhoneCertified from '@/components/Login/FindIdPhoneCertified.vue'
import { mapActions } from 'vuex'
import router from "@/router"

export default {
  name: 'FindPassword',
  components: {
    FindIdPhoneCertified,
  },
  data() {
    return {
      checkPhone: false,
      phone: null,
      name: null
    }
  },
  methods: {
    ...mapActions([
      'putNextPage'
    ]),
    phonecertified() {
      this.checkPhone = true
    }
  },

  created() {
    if(this.$store.state.accessToken) {
      router.push({name: 'Main'})
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