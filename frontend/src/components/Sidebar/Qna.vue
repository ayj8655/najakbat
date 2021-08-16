<template>
  <div>
    <div class="container notice mt-2">
      <div class="row">
        <div class="font1 col-2 px-0">
          분류
        </div>
        <div class="col-5 px-0">
          <div class="font1">
            내용
          </div>
        </div>
        <div class="font1 col-2 px-0">
          상태
        </div>
        <div class="font1 col-3 px-0">
          날짜
        </div>
      </div>
    </div>
    <div v-for="(qna, idx) in qnas" :key="idx">
      <div class="border border-end-0 border-start-0 bg-white">
      <div class="container mt-2">
        <div class="row">
          <div class="font2 col-2 px-0">
            {{ qna.qnaType }}
          </div>
          <div class="font2 col-5 px-0">
            {{ qna.question }}
          </div>
          <div class="font1 col-2 px-0">
            {{ qna.finish }}
          </div>
          <div class="col-3 px-0">
            <div class="font1 mx-1">
            {{ qna.date | moment('YYYY-MM-DD') }}
            </div>
          </div>
          </div>
        </div>
      </div>


    </div>
  </div>
</template>

<script>
import {mapState} from 'vuex'
export default {
  created() {
    this.$store.dispatch('getQnas')
  },
  computed: {
    ...mapState ([
      'qnas'
    ])
  }
}
</script>

<style>
.font1 {
  font-family: Noto Sans KR;
  font-style: normal;
  font-weight: normal;
  font-size: 15px;
  line-height: 25px;
  /* identical to box height */

  color: #999999;
}

.font2 {
  font-family: Noto Sans KR;
  font-style: normal;
  font-weight: normal;
  font-size: 15px;
  line-height: 25px;
}
</style>