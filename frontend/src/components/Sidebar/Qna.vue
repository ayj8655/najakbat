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
      <div type="button" @click="detail(qna)" data-bs-toggle="modal" data-bs-target="#exampleModal2" class="isRead-false border border-end-0 border-start-0 bg-white">
      <div class="container mt-2">
        <div class="row">
          <div class="font2 col-3 px-0">
            {{ qna.qnaType }}
          </div>
          <div class="font2 col-4 px-0">
            {{ qna.question.substring(0,5) }}...
          </div>
          <div class="font1 col-2 px-0">
            <div v-if="qna.finish" class="font1">답변완료</div>
            <div v-else class="font1">미답변</div>
          </div>
          <div class="col-3 px-0">
            <div class="font1 mx-1 p-1">
            {{ qna.date | moment('YYYY-MM-DD') }}
            </div>
          </div>
          </div>
        </div>
      </div>

    </div>
<div class="modal fade" id="exampleModal2" tabindex="-1" aria-labelledby="exampleModalLabel2" aria-hidden="true">
  <div class="modal-dialog modal-dialog-centered">
    <div class="container modaldesign">
    <div class="modal-content1">
      <div class="">
        <div class="modal-title" align="center" id="exampleModalLabel">
      
          <div class="p-2 pb-2" align="center">
            
            <div class="mb-0 font2">분류: {{this.qnaType}}</div>
          </div>
        </div>
      
      </div>
      <div class="modal-body pt-2">
        <div class="mycontainer text-start p-1">{{ this.qnaQuestion }}</div>
      </div>
      <div class="">
        <div v-if="this.qnaAnswer">
          <div class="mb-2 font2">운영자 답변</div> 
          <div class="mx-3 mycontainer text-start p-1 mb-2">
            {{ this.qnaAnswer }}
          </div>
          </div>
        <button type="button" class="btn btn-secondary mb-3 mx-2" data-bs-dismiss="modal">닫기</button>
      </div>
    </div>
</div>
</div>
</div>
      <!-- modal -->
    <Qnaform/>
  </div>
</template>

<script>
import {mapState} from 'vuex'
import Qnaform from '../../components/Sidebar/include/Qnaform.vue'
export default {
  components: {
    Qnaform
  },
  created() {
    this.$store.dispatch('getQnas')
  },
  data() {
    return {
      isdeleteactivated: false,
      isClick: false,
      qnaQuestion: '',
      qnaType: '',
      qnaAnswer: null,
      content: '',
    }
  },
  computed: {
    ...mapState ([
      'qnas'
    ])
  },
  methods: {
    detail(object) {
      this.qnaQuestion = object.question
      this.qnaType = object.qnaType
      if (object.answer) {
        this.qnaAnswer = object.answer
      }
      else {
        this.qnaAnswer = null
      }
      // console.log(this.qnaQuestion)
      // console.log(this.qnaType)
      // console.log(this.qnaAnswer)
    },
    splitContent(content) {
      return content.substring(0,51)
    }
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
.modal-content1 {
  background: #FFFFFF;
  border: 3px solid #B6C790;
  box-sizing: border-box;
  box-shadow: 0px 4px 4px rgba(0, 0, 0, 0.25);
  border-radius: 15px;
}
.buttoncolor {
  background: #71873F;
  color: #FFFFFF;
}
.mycontainer {
  background: #FFFFFF;
  border: 1px solid #999999;
  box-sizing: border-box;
  border-radius: 5px;
  height: 170px;
}

</style>