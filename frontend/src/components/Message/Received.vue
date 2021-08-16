<template>
<div>
  <keep-alive>
  <div v-if="isClick" class="mt-3"><MessageObject/></div>
  
  <div v-else class="container">
  <div class="d-flex flex-row-reverse">
  <button type="button" class="btn btn-sm btn-warning mb-2" v-if="isdeleteactivated" @click="activateDelete">삭제 취소</button>
  <button type="button" class="btn btn-sm btn-secondary mb-2" v-else @click="activateDelete">알림 삭제</button>
  </div>
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

<!-- Search list -->

    <div v-if="isSearch">    
      <div v-for="(receivedMessage, idx) in receivedMessages" :key="idx">
    <div v-if="((sword=='') || (skey=='content' && receivedMessage.content.includes(sword)) || (skey=='nickname' && receivedMessage.senderNickname.includes(sword)))" class="container px-0" @click="[detail(receivedMessage), reading([idx, receivedMessage.messageNumber])]" >
      <div class="isRead-false border border-end-0 border-start-0 bg-white">
        <div class="container notice mt-2">
          <div class="row">
            <!-- 메세지 삭제 -->


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
  
</div>
</div>

<!-- Normal list -->

<div v-else>
    <div v-for="(receivedMessage, idx) in receivedMessages" :key="idx">
    <div class="container px-0" @click="[detail(receivedMessage), reading([idx, receivedMessage.messageNumber])]" >
      <div class="isRead-false border border-end-0 border-start-0 bg-white">
        <div class="container notice mt-2" >
          <div class="row" >
          <div class="col-1 px-0" v-show="isdeleteactivated">
            <input class="mt-2" type="checkbox" id="checkbox" @click="selectMessage(receivedMessage)">
            <label for="checkbox"></label>
          </div>
            <!-- 삭제 -->
            

            <div class="font2 col-2 px-0">
              {{ receivedMessage.senderNickname }}
            </div>
            <div v-if="isdeleteactivated" class="col-4 px-0">
              <div class="font2">
                {{receivedMessage.content}}
              </div>
            </div>
            <div v-else class="col-5 px-0">
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
</div>
</div>
  <button type="button" class="btn btn-danger mt-3 mb-3" v-if="isdeleteactivated" @click="deleteMessageList">선택 삭제</button>
  <messageform v-else />
<div class="container">
    <div class="row">
      <select class="col-3 selectdesign px-0" name="key" id="skey" v-model="skey">
        <option disabled value="">검색기준</option>
          <option value="content">
             내용
            </option>
          <option value="nickname">
            닉네임
            </option>
      </select>
      <input
        type="text"
        class="mx-1 inputdesign col-7 px-0"
        
        name="sword"
        v-model="sword"
        placeholder="쪽지 검색"
        
        />
        <img src="../../assets/search_green.png" type="button" width="30px" height="30px" class="col px-0" @click="searchingmessage"/>
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
import Messageform from '@/components/Message/Messageform.vue'
// import SearchMessage from './include/SearchMessage.vue'

export default {
  components: {
    // MessageReplyForm,
    Messageform,
    MessageObject,
    // SearchMessage
  },
  data() {
    return {
      isdeleteactivated: false,
      isClick: false,
      isSearch: false,
      sword: '',
      skey: 'content',
      checkedList: [],
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
      if (! this.isdeleteactivated) {
        this.$store.state.messageContent = message.content
        this.$store.state.messageTime = message.time
        this.$store.state.messageSenderNickname = message.senderNickname
        this.$store.state.messageNumber = message.messageNumber
        this.$store.state.SenderNumber = message.senderNumber
        this.$store.state.ReceiverNumber = message.receiverNumber
        console.log(this.$store.state.messageSenderNickname)
      }
    },
    reading(messageinfo) {
      if (! this.isdeleteactivated) {
        this.isClick = ! this.isClick
        const messageIdx = messageinfo[0]
        const messageNum = messageinfo[1]
        this.$store.state.receivedMessages[messageIdx].isRead = 1
        // console.log(this.$store.state.receivedMessages[messageIdx].isRead)
        this.$store.dispatch('readingMessage', messageNum)
      }
    },
    searchingmessage() {
      this.isSearch = ! this.isSearch
      console.log(this.isSelect)
    },
    activateDelete () {
      this.isdeleteactivated = ! this.isdeleteactivated
      // console.log(this.isdeleteactivated)
    },
    selectContent () {
      this.isSelect = false
      console.log(this.isSelect)
    },
    selectNickname () {
      this.isSelect = true
      console.log(this.isSelect)
    },
    selectMessage (mymessage) {
        if (this.checkedList.includes(mymessage.messageNumber)) {
          this.checkedList.splice(this.checkedList.indexOf(mymessage.messageNumber), 1)
        }
        else {
          this.checkedList.push(mymessage.messageNumber)
        }
        console.log(this.checkedList)
    },
    deleteMessageList() {
      // this.$router.go(0)
      const messageList = JSON.parse(JSON.stringify((this.checkedList)))
      this.$store.dispatch('messageListDelete', messageList)
    }
  },
  watch: {
    sword: function () {
        this.isSearch = false
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
.selectdesign {
  background: #FFFFFF;
  border: 1px solid #B6C790;
  box-sizing: border-box;
  border-radius: 5px 5px 0px 0px;
}
.inputdesign {
  background: #FFFFFF;
  border: 3px solid #B6C790;
  box-sizing: border-box;
  border-radius: 5px;
}
</style>