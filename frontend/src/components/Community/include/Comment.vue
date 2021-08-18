<template>
  <div id="comment" class="row">
    <div class="col-1" v-if="this.col == 11">
      <img src="@/assets/comment_arrow.png" width="15px" />
    </div>
    <div :class="'col-' + this.col" v-if="isShow">
      <div class="head">
        <span id="left"
          >{{ comment.userNickname
          }}<img id="messageBtn" src="@/assets/message.png" width="20px" data-bs-toggle="modal" data-bs-target="#exampleModal2" @click="syncNickname(comment.userNickname)"
        /></span>
        <span id="right" v-text="changeDate(this.comment.date)" />
      </div>
      <div
        v-if="comment.isdeleted"
        class="body"
        id="deleted"
        v-html="`삭제된 댓글입니다.`"
      ></div>
      <div v-else class="body" v-html="enterToBr(comment.content)"></div>
      <div class="foot">
        <span class="recc">
          <span>
            <img v-if="this.recoValue" src="@/assets/leaf_lightgreen.png" width="15px" />
            <img v-else src="@/assets/leaf_gray.png" width="15px" />
            {{comment.recommend}}
          </span>
          <span v-if="this.comment.parent==0"><img src="@/assets/comment.png" width="15px" />{{comment.commentCount}}</span>
        </span>
        <span
          class="btn"
          v-if="
            comment.userNumber == this.$store.state.userNumber &&
            !comment.isdeleted
          "
        >
          <label @click="writeChildChange">답글달기</label> |
          <label @click="modifyCommentView">수정</label> |
          <label @click="deleteComment">삭제</label>
        </span>
        <span
          class="btn"
          v-if="
            comment.userNumber != this.$store.state.userNumber &&
            !comment.isdeleted
          "
        >
          <span v-if="this.recoValue"><label @click="recommendComment">좋아요 취소</label> | </span>
          <span v-else><label @click="recommendComment">좋아요</label> | </span>
          <label @click="writeChildChange">답글달기</label>
        </span>
      </div>
    </div>
    <div v-else :class="'regist_form col-' + this.col">
      <div>
        <textarea
          id="content"
          name="content"
          v-model="modifyComment.content"
          cols="30"
          rows="2"
        ></textarea>
      </div>
      <div align="right">
        <button class="btn btn-success btn-sm m-2" @click="updateComment">
          수정
        </button>
        <button
          class="btn btn-secondary btn-sm m-2"
          @click="updateCommentCancel"
        >
          취소
        </button>
      </div>
    </div>
    <hr />
    <div class="row" v-if="childWrite">
      <div class="col-1">
        <img src="@/assets/comment_arrow.png" width="15px" />
      </div>
      <div class="col-11">
        <div>
          <textarea
            id="content"
            name="content"
            v-model="writeComment.content"
            cols="30"
            rows="2"
          ></textarea>
        </div>
        <div align="right">
          <button class="btn btn-success btn-sm m-2" :disabled="this.writeComment.content.length == 0" @click="writeChildComment">
            등록
          </button>
          <button
            class="btn btn-secondary btn-sm m-2"
            @click="writeChildChange"
          >
            취소
          </button>
        </div>
      </div>
      <hr />
    </div>
  <!-- Modal -->
<div class="modal fade" id="exampleModal2" tabindex="-1" aria-labelledby="exampleModalLabel2" aria-hidden="true">
  <div class="modal-dialog modal-dialog-centered">
    <div class="container modaldesign">
    <div class="modal-content">
      <div class="">
        <div class="modal-title" align="center" id="exampleModalLabel">
          <!-- <div class="dropdown" align="center"> -->
          <div class="p-2 pb-2" align="center">
            <h2 class="mb-0">{{ this.messageReceiverNickname }}</h2>
          </div>
          에게 보내는 쪽지
        </div>
        <!-- <button type="button" class="btn-close mx-0" data-bs-dismiss="modal" aria-label="Close"></button> -->
      </div>
      <div class="modal-body pt-2">
        <div class="form-group" align="left">
          <textarea
            @click="findNumber"
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
        <button type="button" v-if="content" data-bs-dismiss="modal" class="btn buttoncolor mb-3 mx-2" @click="postingMessage (messageReceiverNickname)">보내기</button>
        <button type="button" v-else disabled class="btn buttoncolor mb-3 mx-2" @click="postingMessage (messageReceiverNickname)">보내기</button>
        <button type="button" class="btn btn-secondary mb-3 mx-2" data-bs-dismiss="modal">취소</button>
      </div>
    </div>
  </div>
  </div>
</div>
  </div>
</template>

<script>
import axios from "axios";
import { mapState} from 'vuex'

export default {
  name: "comment",
  components: {},
  props: {
    comment: Object,
    recoFlag: Boolean,
    col: Number,
  },
  data() {
    return {
      userNumber: this.$store.state.userNumber,
      userNickname: this.$store.state.userNickname,
      isShow: true,
      childWrite: false,
      modifyComment: {},
      writeComment: {userNumber: null, userNickname: "", parent: "", postno: null, content: ""},
      recoValue: this.recoFlag,
      pickComment: this.comment,
      content: '',
      receiver: '',
      // receiverNickname: '',
    };
  },
  // computed: {
  //   recoValue() {
  //     if(this.$props.recoFlag) {

  //     }
  //   }
  // },
  methods: {
    movePage() {
      this.$router.push(`/profile/${this.comment.userNumber}`);
    },
    changeDate(str) {
      return str.substring(0, 10) + " " + str.substring(11, 19);
    },
    enterToBr(str) {
      if (str) return str.replace(/(?:\r\n|\r|\n)/g, "<br />");
    },
    modifyCommentView() {
      this.modifyComment = this.comment;
      // console.log(this.modifyComment);
      this.isShow = false;
    },
    deleteComment() {
      // console.log(this.comment.commentNumber);
      if (confirm("정말로 삭제하시겠습니까?")) {
        axios.delete(`comment/${this.comment.commentNumber}`).then((data) => {
          if (data.data == "success") window.location.reload();
        });
      }
    },
    recommendComment() {
      axios
        .put(
          `comment/recommend/${this.comment.commentNumber}?user_number=${this.userNumber}`
        )
        .then((data) => {
          if (data.data == "success") {
            this.recoValue = !this.recoValue
            if(this.recoValue) {
              this.pickComment.recommend++
            }
            else {
              this.pickComment.recommend--
            }
          }
        });
    },
    updateComment() {
      axios
        .put(`comment/${this.modifyComment.commentNumber}`, this.modifyComment)
        .then((data) => {
          if(data.data=="success") {
            this.modifyComment = null;
            this.$emit("reload-comment");
          }
          this.updateCommentCancel();
        })
        .catch((err) => {
          console.log(err);
        });
    },
    updateCommentCancel() {
      this.isShow = true;
    },
    writeChildChange() {
      this.childWrite = !this.childWrite;
      this.writeComment.userNumber = this.userNumber;
      this.writeComment.userNickname = this.userNickname;
      this.writeComment.postno = this.comment.postNumber;
      this.writeComment.parent = this.comment.parent;
    },
    writeChildComment() {
      if(this.writeComment.content.length>0) {
        // console.log(this.writeComment);
        axios.post(`comment/?user_number=${this.userNumber}&postno=${this.comment.postNumber}&parent=${(this.comment.parent)? this.comment.parent:this.comment.commentNumber}`, this.writeComment).then((data) => {
          if(data.data=="success") {
            this.writeChildChange();
            this.writeComment = null;
            this.$emit("reload-comment");
          }
        }).catch((err) => {
          console.log(err);
        });
      }
    },
    postingMessage() {
      // console.log(nickname)
      // this.$store.dispatch('getReceiverNumber', nickname)
      this.receiver = this.ReceiverNumber
      console.log(this.receiver)
      this.$store.dispatch('messagePost', [this.content, this.$store.state.ReceiverNumber])
    },
    syncNickname(nickname) {
      // console.log(nickname)
      this.$store.state.messageReceiverNickname = nickname
      this.content = ''
    },
    findNumber() {
      this.$store.dispatch('getReceiverNumber', this.$store.state.messageReceiverNickname)
    }
  },
  computed: {
    ...mapState([
      'ReceiverNumber',
      'messageReceiverNickname'
    ])
  },
};
</script>

<style scoped>
#comment {
  margin: 5px;
  text-align: left;
}
.head {
  display: block;
}
#left img {
  margin-left: 5px;
}
#right {
  float: right;
  display: inline-block;
  color: #999999;
}
.body {
  margin: 5px;
}
.recc > span {
  margin-right: 10px;
}
.foot img {
  margin-right: 5px;
}
.foot .btn {
  float: right;
  display: inline-block;
  color: #999999;
}
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
</style>
