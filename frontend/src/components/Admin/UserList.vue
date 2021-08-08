<template>
  <div id="main-contents" class="container-fluid">
    <div class="container" id="body" align="center">
      <h3>회원 목록</h3>
      <table class="table table-hover mt-5">
        <colgroup>
          <col width="10%" />
          <col width="10%" />
          <col width="10%" />
          <col width="10%" />
          <col width="20%" />
          <col width="20%" />
          <col width="10%" />
        </colgroup>
        <thead>
          <tr>
            <th class="text-center">no.</th>
            <th class="text-center">아이디</th>
            <th class="text-center">닉네임</th>
            <th class="text-center">전화번호</th>
            <th class="text-center">주소</th>
            <th class="text-center">가입일</th>
            <th class="text-center"></th>
          </tr>
        </thead>
        <tbody id="userlist" v-for="(user, index) in users" :key="index">
          <tr class="view" data-id="">
            <td>{{ user.userNumber }}</td>
            <td>{{ user.id }}</td>
            <td>{{ user.nickname }}</td>
            <td>{{ user.phone }}</td>
            <td v-if="user.address">{{ user.address }}</td>
            <td v-else style="color: #999999;">정보 없음</td>
            <td v-text="changeDate(user.joinDate)" />
            <td>
              <button
                type="button"
                class="delBtn btn btn-outline-danger btn-sm"
                data-bs-toggle="modal"
                data-bs-target="#exampleModal"
                @click="getModal(user)"
              >
                탈퇴
              </button>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
    <!-- Modal -->
    <div
      class="modal fade"
      id="exampleModal"
      tabindex="-1"
      aria-labelledby="exampleModalLabel"
      aria-hidden="true"
    >
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title" id="exampleModalLabel">탈퇴하기</h5>
            <button
              type="button"
              class="btn-close"
              data-bs-dismiss="modal"
              aria-label="Close"
            ></button>
          </div>
          <div class="modal-body">정말 {{this.delUser.nickname}} 님의 탈퇴를 진행하시겠습니까?</div>
          <div class="modal-footer">
            <button type="button" class="btn btn-danger" @click="deleteUser(delUser.userNumber)">탈퇴</button>
            <button
              type="button"
              class="btn btn-secondary"
              data-bs-dismiss="modal"
            >
              취소
            </button>
          </div>
        </div>
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
      delUser: {},
    };
  },
  created() {
    axios.get("user/all").then(({ data }) => {
      this.users = data;
    });
  },
  methods: {
    changeDate(str) {
      return str.substring(0, 10) + " " + str.substring(11, 19);
    },
    getModal(user) {
        this.delUser = user;
    },
    deleteUser(userno) {
        axios.delete("user/"+userno).then((data) => {
            console.log(data);
            // if (data.data == "success") window.location.reload();
        });
    }
  },
};
</script>

<style>
@media(min-width: 601px) {
  .container-fluid {
    padding: 50px;
  }
  #body {
    padding: 50px 25px;
    min-height: 500px;
    background: #ffffff;
    border-radius: 15px;
  }
  .view {
    text-align: center;
  }
}
</style>
