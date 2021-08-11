<template>
  <div id="comment" class="row">
    <div class="col-1" v-if="this.col == 11">
      <img src="@/assets/comment_arrow.png" width="15px" />
    </div>
    <div :class="'col-' + this.col" v-if="isShow">
      <div class="head">
        <span id="left"
          >{{ comment.userNickname
          }}<img id="messageBtn" src="@/assets/message.png" width="20px"
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
            <img v-if="this.recoFlag" src="@/assets/leaf_lightgreen.png" width="15px" />
            <img v-else src="@/assets/leaf_gray.png" width="15px" />
            {{comment.recommend}}
          </span>
          <span v-if="this.comment.parent==0"><img src="@/assets/comment.png" width="15px" />{{comment.commentCount}}</span>
        </span>
        <span
          class="btn"
          v-if="
            comment.userNumber == this.$store.state.myNumber &&
            !comment.isdeleted
          "
        >
          <label @click="modifyCommentView">수정</label> |
          <label @click="deleteComment">삭제</label>
        </span>
        <span
          class="btn"
          v-if="
            comment.userNumber != this.$store.state.myNumber &&
            !comment.isdeleted
          "
        >
          <span v-if="this.recoFlag"><label @click="recommendComment">좋아요 취소</label> | </span>
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
          <button class="btn btn-success btn-sm m-2" @click="writeChildComment">
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
  </div>
</template>

<script>
import axios from "axios";

export default {
  name: "comment",
  components: {},
  props: {
    comment: Object,
    recoFlag: Boolean,
  },
  data() {
    return {
      col: this.comment.commentNumber == 0 || !this.comment.parent ? 12 : 11,
      userNumber: this.$store.state.myNumber,
      userNickname: this.$store.state.nickname,
      isShow: true,
      childWrite: false,
      modifyComment: {},
      writeComment: {userNumber: null, userNickname: "", parent: "", postno: null, content: ""},
    };
  },
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
          console.log(data);
          if (data.data == "success") window.location.reload();
        });
    },
    updateComment() {
      axios
        .put(`comment/${this.modifyComment.commentNumber}`, this.modifyComment)
        .then((data) => {
          if (data.data == "success") console.log("댓글 수정 성공");
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
      this.writeComment.parent = this.comment.commentNumber;
    },
    writeChildComment() {
      console.log(this.writeComment);
      axios.post(`comment/?user_number=${this.userNumber}&postno=${this.comment.postNumber}&parent=${this.comment.commentNumber}`, this.writeComment).then((data) => {
        if (data.data == "success") window.location.reload();
      }).catch((err) => {
        console.log(err);
      });
    },
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
</style>
