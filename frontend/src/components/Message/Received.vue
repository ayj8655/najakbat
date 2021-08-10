<template>
<div>
  <keep-alive>
  <div v-if="isClick"><MessageObject/></div>
  
  <div v-else class="container">
    <div class="container notice mt-2">
      <div class="row">
        <div class="font1 col-2 px-0">
          닉네임
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
    <div v-for="(receivedMessage, idx) in receivedMessages" :key="idx">
    <div class="container px-0" @click="[detail(receivedMessage), reading([idx, receivedMessage.messageNumber])]" >
      <div class="isRead-false border border-end-0 border-start-0 bg-white">
        <div class="container notice mt-2">
          <div class="row">
            <div class="font2 col-2 px-0">
              {{ receivedMessage.senderNickname }}
            </div>
            <div class="col-5 px-0">
              <div class="font2">
                {{receivedMessage.content}}
              </div>
            </div>
            <div class="font1 col-2 px-0">
              <div v-if="receivedMessage.isRead">
                읽음
              </div>
              <div v-else>
                안읽음
              </div>
            </div>
            <div class="col-3 px-0">
              <div class="font1 mx-1">
                {{ receivedMessage.time | moment('YYYY-MM-DD') }} 
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
<!-- Modal -->
  <div class="modal fade" id="staticBackdrop" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered">
      <div class="modal-content">
        <div class="modal-header">
          <h5 class="modal-title" id="staticBackdropLabel">Modal title</h5>
          <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
        </div>
        <div class="modal-body">
          {{ messageContent }}
        </div>
        <div class="modal-footer">
          <!-- <message-reply-form/> -->
          <button type="button" class="btn btn-primary">Understood</button>
        </div>
      </div>
    </div>
  </div>
</div>
</div>
</keep-alive>
</div>

</template>

<script>
import { mapState } from 'vuex'
import { mapActions } from 'vuex'
// import MessageReplyForm from './include/MessageReplyForm.vue'
import MessageObject from './include/MessageObject.vue'


export default {
  components: {
    // MessageReplyForm
    MessageObject
  },
  data() {
    return {
      isClick: false,
    }
  },
  computed: {
  ...mapState ([
    'receivedMessages',
    'messageContent',
    'messageTime',
    'messageSenderNickname',
    ])
  },
  created() {
    this.$store.dispatch('getReceivedMessages')
  },
  methods: {
    ...mapActions ([
      'readingMessage'
    ]),
    detail(message) {
      this.$store.state.messageContent = message.content
      this.$store.state.messageTime = message.time
      this.$store.state.messageSenderNickname = message.senderNickname
      console.log(this.$store.state.messageSenderNickname)
    },
    reading(messageinfo) {
      this.isClick = ! this.isClick
      const messageIdx = messageinfo[0]
      const messageNum = messageinfo[1]
      this.$store.state.receivedMessages[messageIdx].isRead = 1
      // console.log(this.$store.state.receivedMessages[messageIdx].isRead)
      this.$store.dispatch('readingMessage', messageNum)
    },
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