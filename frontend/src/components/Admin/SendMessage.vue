<template>
  <div id="main-contents" class="container-fluid">
    <div class="container" id="body" align="center">
      <h3>알림 메세지 발송</h3>
      <div width="80%">
        <div></div>
        <div class="m-2" align="left">
          <div id="selecting-all-area">
            <input
              type="checkbox"
              id="selectingAll"
              v-model="allSelected"
              @change="selectAll"
            />
            <label for="selectingAll">전체 선택</label>
          </div>
          <div id="selecting-user-area" v-show="isShow">
            <span v-for="(user, index) in users" :key="index" id="select-item">
              <input
                type="checkbox"
                :id="'selectingUser' + user.userNumber"
                :value="user.userNumber"
                v-model="selected"
                @change="selectUser"
              />
              <label
                :for="'selectingUser' + user.userNumber"
                id="select-item-label"
                >{{ user.nickname }} ({{ user.id }})</label
              >
            </span>
          </div>
          <!-- <div>{{this.selected}}</div> -->
          <div v-if="isShow" @click="changeIsShow">회원목록 접기</div>
          <div v-else @click="changeIsShow">회원목록 펼치기</div>
        </div>
        <div class="form-group m-2" align="left">
          <label for="title">제목:</label>
          <input
            type="text"
            class="form-control"
            id="title"
            name="title"
            v-model="title"
            placeholder="알림 메세지의 제목을 입력하세요."
          />
        </div>
        <div class="form-group m-2" align="left">
          <label for="content">내용:</label>
          <textarea
            class="form-control"
            rows="15"
            id="content"
            name="content"
            v-model="content"
            placeholder="알림 내용을 입력하세요."
          ></textarea>
        </div>
      </div>
      <div
        :class="[{ btnDefault: !isActive }, { btnChange: isActive }]"
        id="sendBtn"
      >
        메세지 전송
      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";

export default {
  data() {
    return {
      users: [],
      usernos: [],
      selected: [],
      allSelected: false,
      title: "",
      content: "",
      isShow: false,
    };
  },
  created() {
    axios.get("user/all").then((data) => {
      this.users = data.data;
      this.users.forEach((user, index) => {
          this.usernos[index] = user.userNumber;
        });
    });
  },
  computed: {
    isActive() {
      if (this.selected.length && this.title.length > 0 && this.content.length > 0) return true;
      else return false;
    },
  },
  methods: {
    selectAll() {
      if (this.allSelected) {
        this.selected = this.usernos;
      } else this.selected = [];
    },
    selectUser() {
      if (this.selected.length == this.users.length) this.allSelected = true;
      else this.allSelected = false;
    },
    changeIsShow() {
      this.isShow = !this.isShow;
    },
  },
};
</script>

<style>
@media (min-width: 601px) {
  .container-fluid {
    padding: 50px;
  }
  #body {
    padding: 50px 25px;
    min-height: 500px;
    background: #ffffff;
    border-radius: 15px;
  }
}
#selecting-user-area {
  padding: 20px;
  background-color: #efefef;
}
#select-item {
  padding: 5px;
  margin: 10px;
  background-color: #ffffff;
  border-radius: 15px;
  border: 2px solid gray;
}
#select-item-label {
  margin: 0 10px;
}
.btnDefault {
  padding: 5px;
  margin: 5px;
  border-radius: 5px;
  color: #ffffff;
  background-color: #b6c790;
}
.btnChange {
  padding: 5px;
  margin: 5px;
  border-radius: 5px;
  color: #ffffff;
  background-color: #446631;
}
</style>
