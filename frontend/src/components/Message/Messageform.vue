<template>
<div>
<button type="button" class="btn btn-success" data-bs-toggle="modal" data-bs-target="#exampleModal">
  새 쪽지 쓰기
</button>
<!-- {{ alluserInfo }} -->

<!-- Modal -->
<div class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
  <div class="modal-dialog modal-dialog-centered">
    <div class="container modaldesign">
    <div class="modal-content">
      <div class="">
        <div class="modal-title" align="center" id="exampleModalLabel">
          <!-- <div class="dropdown" align="center"> -->
          <div class="form-group nicknameinput dropdown- p-3 pb-2" align="center">
            <input
              data-bs-toggle="dropdown"
              aria-expanded="false"
              type="text"
              class="form-control dropdown-toggle"
              id="sword"
              name="sword"
              v-model="sword"
              placeholder="받는사람의 닉네임을 입력하세요."
            />
            <ul class="dropdown-menu" aria-labelledby="sword">
              <li v-for="(userinfo, index) in alluserInfo" :key="index">
              <!-- <li v-for="(userinfo, index) in alluserInfo" :key="index" v-show="((sword='') || (userinfo.nickname.includes(sword)))"> -->
                <div v-if="((sword=='') || userinfo.nickname.includes(sword))" class="dropdown-item">
                  <div @click="syncronizeNickname (userinfo.nickname)">{{ userinfo.nickname }}</div>
                </div>
                <div v-else-if="index > 10"></div>
              </li>
            </ul>
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
        <button type="button" class="btn buttoncolor mb-3 mx-2" @click="postingMessage (sword)">보내기</button>
        <button type="button" class="btn btn-secondary mb-3 mx-2" data-bs-dismiss="modal">취소</button>
      </div>
    </div>
  </div>
  </div>
</div>
</div>
</template>

<script>
import { mapState } from 'vuex'
import { mapActions } from 'vuex'
// import Dropdown from 'vue-simple-search-dropdown'


export default {
  data () {
    return {
      content: '',
      nickname: '',
      sword: '',
      // receiver: '',
    }
  },
  created() {
    this.$store.dispatch('getUserinfoAll')
    // console.log(this.$store.state.profile.userNumber)
    // console.log(this.$store.state.alluserInfo)
  },
  computed: {
    ...mapState([
      'alluserInfo',
      // 'receiver'
    ])
  },
  methods: {
    ...mapActions([
      'messagePost'
    ]),
    postingMessage(nickname) {
      
      for (let key in this.alluserInfo) {
        if (this.alluserInfo[key] == nickname) {
          this.$store.state.receiver = this.alluserInfo[key].userNumber
        }
        console.log(this.alluserInfo[key])
      }
      console.log(this.$store.state.receiver)
      // this.$store.dispatch('messagePost', [this.content, this.receiver])
    },
    syncronizeNickname (receiverNickname) {
      // console.log(localStorage.getItem('userNumber'))
      this.sword = receiverNickname
    }
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