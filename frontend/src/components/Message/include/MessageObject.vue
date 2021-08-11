<template>
  <div>
    <div class="container messageDesign w-80">
      <div class="row p-3 pb-2 pt-2" align="left">
        <div class="row">
          <div class="col-1 p-0">
            <img src="@/assets/modal_back_green.png" type="button" @click="goingback" class="back w-75 h-75">
          </div>
          <div class="col-11">
            <div class="sender">
              {{ this.$store.state.messageSenderNickname }}
            </div>
            <div class="date pb-3">
              {{ this.$store.state.messageTime | moment('YY-MM-DD HH:mm:ss')}}
            </div>
          </div>
        </div>
        <div class="mycontainer">
          <div class="content p-1">
            {{ this.$store.state.messageContent }}
          </div>
        </div>
      </div>
      <div class="d-flex justify-content-center">
        <message-reply-form/>
        <button type="button" class="btn btn-secondary mb-3 mx-2" @click="deletingmessage">삭제</button>
      </div>
    </div>
  </div>
</template>

<script>
import Vue from 'vue'
// import { mapState } from 'vuex'
import { mapActions } from 'vuex'
import vueMoment from 'vue-moment'
import MessageReplyForm from '@/components/Message/include/MessageReplyForm.vue'
// import history from 'window'
Vue.use(vueMoment)

export default {
  methods: {
    ...mapActions([
      'messageDelete'
    ]),
    goingback() {
      this.$router.go(0)
    },
    deletingmessage() {
      // console.log(this.$store.state.messageNumber)
      this.$router.go(0)
      this.$store.dispatch('messageDelete', [this.$store.state.messageNumber])
    }

  },
  components: {
    MessageReplyForm,
  }

}
</script>

<style>
.messageDesign {
  height: 300px;
  background: #FFFFFF;
  border: 3px solid #B6C790;
  box-sizing: border-box;
  box-shadow: 0px 4px 4px rgba(0, 0, 0, 0.25);
  border-radius: 15px;
  /* position: relative; */
}
.sender {
  font-family: Noto Sans KR;
  font-style: normal;
  font-weight: bold;
  font-size: 15px;
  line-height: 22px;
  /* identical to box height */

  /* text-align: left; */
}
.date {
  font-family: Noto Sans KR;
  font-style: normal;
  font-weight: normal;
  font-size: 9px;
  line-height: 13px;
  /* identical to box height */
  text-align: left;

  color: #999999;
}
.content {
  font-family: Noto Sans KR;
  font-style: normal;
  font-weight: normal;
  font-size: 13px;
  line-height: 13px;
  color: #000000;
}
.mycontainer {
  background: #FFFFFF;
  border: 1px solid #999999;
  box-sizing: border-box;
  border-radius: 5px;
  height: 170px;
}
.back {
  width: 20px;
  height: 20px;
}

</style>