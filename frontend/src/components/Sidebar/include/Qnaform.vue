<template>
  <div class="pt-3 pb-3">
  <button type="button" class="btn buttoncolor2" data-bs-toggle="modal" data-bs-target="#exampleModal5">
    문의하기
  </button>

  <!-- modal -->
  <div class="modal fade" id="exampleModal5" tabindex="-1" aria-labelledby="exampleModalLabel5" aria-hidden="true">
  <div class="modal-dialog modal-dialog-centered">
    <div class="modal-content">
      <div class="">
        <div class="modal-title container" align="center" id="exampleModalLabel5"></div>
        <div class="font1 text-start mb-2 selected mx-3">문의사항 분류</div>
        <!-- <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button> -->
        <select class="selected" v-model="selected">
          <option disabled value="">문의사항 분류를 선택해주세요</option>
          <option>농작물 도감 정보 정정 요청</option>
          <option>시스템 버그 신고</option>
          <option>기타</option>
        </select>        
      </div>
      <div class="modal-body pt-2">
        <div class="font1 text-start mb-2">내용</div>
        <div class="form-group" align="left">
          <textarea
            class="form-control"
            rows="15"
            id="content"
            name="content"
            v-model="content"
            placeholder="쪽지 내용을 입력하세요."
          ></textarea>
        </div>
      </div>
      <div class="">
        <button v-if="content" type="button" data-bs-dismiss="modal" class="btn buttoncolor2 mb-3 mx-2" >보내기</button>
        <button v-else disabled type="button" data-bs-dismiss="modal" class="btn buttoncolor2 mb-3 mx-2" >보내기</button>
        <button type="button" class="btn btn-secondary mb-3 mx-2" data-bs-dismiss="modal">취소</button>
      </div>
    </div>
  </div>
</div>

  </div>
</template>

<script>
export default {
  data () {
    return {
      content: '',
      selected: '',
    }
  }
}
</script>

<style scoped>
.buttoncolor2 {
  background: #71873F;
  color: #FFFFFF;
}
.selected {
  width: 93%;
}

.font1 {
  font-family: Noto Sans KR;
  font-style: normal;
  font-weight: 500;
  font-size: 15px;
  line-height: 22px;
  /* identical to box height */


  color: #000000;
}

</style>