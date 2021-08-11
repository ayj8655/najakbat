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

<!-- Search list -->

    <div v-if="isSearch">    
      <div v-for="(sentMessage, idx) in sentMessages" :key="idx">
    <div v-if="((sword=='') || (skey=='content' && sentMessage.content.includes(sword)) || (skey=='nickname' && sentMessage.senderNickname.includes(sword)))" class="container px-0" @click="[detail(sentMessage), reading([idx, sentMessage.messageNumber])]" >
      <div class="isRead-false border border-end-0 border-start-0 bg-white">
        <div class="container notice mt-2">
          <div class="row">
            <div class="font2 col-2 px-0">
              {{ sentMessage.senderNickname }}
            </div>
            <div class="col-5 px-0">
              <div class="font2">
                {{sentMessage.content}}
              </div>
            </div>
            <div class="font1 col-2 px-0">
              <div v-if="sentMessage.isRead">
                읽음
              </div>
              <div v-else>
                안읽음
              </div>
            </div>
            <div class="col-3 px-0">
              <div class="font1 mx-1">
                {{ sentMessage.time | moment('YYYY-MM-DD') }} 
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
    <div v-for="(sentMessage, idx) in sentMessages" :key="idx">
    <div class="container px-0" @click="[detail(sentMessage), reading([idx, sentMessage.messageNumber])]" >
      <div class="isRead-false border border-end-0 border-start-0 bg-white">
        <div class="container notice mt-2" >
          <div class="row" >
            <div class="font2 col-2 px-0">
              {{ sentMessage.senderNickname }}
            </div>
            <div class="col-5 px-0">
              <div class="font2">
                {{sentMessage.content}}
              </div>
            </div>
            <div class="font1 col-2 px-0">
              <div v-if="sentMessage.isRead">
                읽음
              </div>
              <div v-else>
                안읽음
              </div>
            </div>
            <div class="col-3 px-0">
              <div class="font1 mx-1">
                {{ sentMessage.time | moment('YYYY-MM-DD') }} 
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
</div>
</div>
<messageform/>
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
      isClick: false,
      isSearch: false,
      sword: '',
      skey: 'content',
    }
  },
  computed: {
  ...mapState ([
    'sentMessages',
    'messageContent',
    'messageTime',
    'messageSenderNickname',
    ])
  },
  created() {
    this.$store.dispatch('getSentMessages')
  },
  methods: {
    ...mapActions ([
      'readingMessage'
    ]),
    detail(message) {
      this.$store.state.messageContent = message.content
      this.$store.state.messageTime = message.time
      this.$store.state.messageSenderNickname = message.senderNickname
      this.$store.state.messageNumber = message.messageNumber
      console.log(this.$store.state.messageSenderNickname)
    },
    reading(messageinfo) {
      this.isClick = ! this.isClick
      const messageIdx = messageinfo[0]
      const messageNum = messageinfo[1]
      this.$store.state.sentMessages[messageIdx].isRead = 1
      // console.log(this.$store.state.sentMessages[messageIdx].isRead)
      this.$store.dispatch('readingMessage', messageNum)
    },
    searchingmessage() {
      this.isSearch = ! this.isSearch
      console.log(this.isSelect)
    },
    selectContent () {
      this.isSelect = false
      console.log(this.isSelect)
    },
    selectNickname () {
      this.isSelect = true
      console.log(this.isSelect)
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