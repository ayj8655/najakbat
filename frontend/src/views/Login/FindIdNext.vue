<template>
  <div class="container">
    <div class="py-4 text-center">
      <div class="my-5">
        <p>회원님의 아이디는</p>
        <h3 class="id-margin">{{ viewId }}</h3>
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
import router from "@/router"

export default {
  name: 'FindIdNext',
  computed: {
    ...mapState([
      'myId'
    ]),
    viewId() {
      return this.myId
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
.id-margin {
  margin: 0 auto 16px;
}
</style>