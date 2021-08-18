<template>
<div>
<button type="button" class="btn buttoncolor" data-bs-toggle="modal" data-bs-target="#exampleModal2">
  답장
</button>
<!-- {{ alluserInfo }} -->

<!-- Modal -->
<div class="modal fade" id="exampleModal2" tabindex="-1" aria-labelledby="exampleModalLabel2" aria-hidden="true">
  <div class="modal-dialog modal-dialog-centered">
    <div class="container modaldesign">
    <div class="modal-content">
      <div class="">
        <div class="modal-title" align="center" id="exampleModalLabel">
          <!-- <div class="dropdown" align="center"> -->
          <div class="p-2 pb-2" align="center">
            <h2 class="mb-0">{{ this.$store.state.messageSenderNickname }}</h2>
          </div>
          에게 보내는 쪽지
        </div>
        <!-- <button type="button" class="btn-close mx-0" data-bs-dismiss="modal" aria-label="Close"></button> -->
      </div>
      <div class="modal-body pt-2">
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
        <button type="button" v-if="content" data-bs-dismiss="modal" class="btn buttoncolor mb-3 mx-2" @click="postingMessage ()">보내기</button>
        <button type="button" v-else disabled class="btn buttoncolor mb-3 mx-2" @click="postingMessage ()">보내기</button>
        <button type="button" class="btn btn-secondary mb-3 mx-2" data-bs-dismiss="modal">취소</button>
      </div>
    </div>
  </div>
  </div>
</div>
</div>
</template>

<script>
// import { mapState } from 'vuex'
import { mapActions } from 'vuex'
// import Dropdown from 'vue-simple-search-dropdown'


export default {
  data () {
    return {
      content: '',
      nickname: this.$store.state.messageSenderNickname,
      // sword2: '',
      // receiver: '',
    }
  },
  
  methods: {
    ...mapActions([
      'messagePost',
      'messageDelete'
    ]),
    postingMessage() {

      // console.log(this.$store.state.receiver)
      // console.log(this.$store.state.messageSenderNickname)
      this.$store.dispatch('messagePost', [this.content, this.$store.state.SenderNumber])
      this.$route.go(0)
    },
  },

}
</script>

<style scoped>
.nicknameinput {
  width: 70%;
}
.modal-content {
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
</style>