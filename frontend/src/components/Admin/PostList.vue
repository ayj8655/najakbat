<template>
  <div id="main-contents" class="container-fluid">
    <div class="container" id="body" align="center">
      <h3>게시글 목록</h3>
      <div class="row mt-5 mb-3" id="search-area">
        <span class="col-4">
          <select class="form-control mr-2" name="key" id="skey" v-model="skey">
            <option value="name">닉네임</option>
            <option value="title">제목</option>
            <option value="content">내용</option>
          </select>
        </span>
        <span class="col-8">
          <input
            type="text"
            class="form-control mr-2"
            placeholder="검색어 입력."
            name="word"
            id="sword"
            v-model="sword"
          />
        </span>
      </div>
      <table class="table mt-2">
        <colgroup>
          <col width="10%" />
          <col width="10%" />
          <col width="15%" />
          <col width="25%" />
          <col width="10%" />
          <col width="20%" />
          <col width="10%" />
        </colgroup>
        <thead>
          <tr>
            <th class="text-center">no.</th>
            <th class="text-center">분류</th>
            <th class="text-center">제목</th>
            <th class="text-center">내용</th>
            <th class="text-center">닉네임</th>
            <th class="text-center">작성일</th>
            <th class="text-center"></th>
          </tr>
        </thead>
        <tbody id="userlist">
          <tr class="view" 
            v-for="(post, index) in itemsForList" 
            :key="index"
            v-show="((sword=='') || (skey=='name' && post.userNickname.includes(sword)) || (skey=='title' && post.title.includes(sword) || (skey=='content' && post.content.includes(sword))))"
          >
            <td>{{ post.postNumber }}</td>
            <td><img :src="changeType(post.postType)" width="35px" /></td>
            <td>{{ post.title }}</td>
            <td
              v-text="splitContent(post.content)"
              @click="movePage(post.postNumber)"
            ></td>
            <td>{{ post.userNickname }}</td>
            <td v-text="changeDate(post.date)" />
            <td>
              <button
                type="button"
                class="delBtn btn btn-outline-danger btn-sm"
                data-bs-toggle="modal"
                data-bs-target="#delModal"
                @click="getModal(post)"
              >
                삭제
              </button>
            </td>
          </tr>
          <tr align="center">
            <td colspan="7" align="center">
              <b-pagination
                v-model="currentPage"
                :total-rows="rows"
                :per-page="perPage"
                aria-controls="sanggwon-list"
                class="mt-3 mb-5 justify-content-center"
                id="paging"
              ></b-pagination>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
    <!-- Modal -->
    <div
      class="modal fade"
      id="delModal"
      tabindex="-1"
      aria-labelledby="delModalLabel"
      aria-hidden="true"
    >
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title" id="delModalLabel">게시글 삭제하기</h5>
            <button
              type="button"
              class="btn-close"
              data-bs-dismiss="modal"
              aria-label="Close"
            ></button>
          </div>
          <div class="modal-body">해당 게시글의 삭제를 진행하시겠습니까?</div>
          <div class="modal-footer">
            <button
              type="button"
              class="btn btn-danger"
              @click="deletePost(delPost.postNumber)"
            >
              삭제
            </button>
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
  name: "AdminPostList",
  data() {
    return {
      posts: [],
      delPost: {},
      perPage: 10,
      currentPage: 1,
      skey: "name",
      sword: "",
    };
  },
  created() {
    axios.get("post/all").then((data) => {
      this.posts = data.data;
    });
  },
  computed: {
      rows() {
        return this.posts.length;
      },
      itemsForList() {
        return this.posts.slice(
          (this.currentPage - 1) * this.perPage,
          this.currentPage * this.perPage
        );
      },
    },
  methods: {
    changeType(type) {
      var typeimg = null;
      switch (type) {
        case 1:
          typeimg = require("@/assets/category_free.png");
          break;
        case 2:
          typeimg = require("@/assets/category_info.png");
          break;
        case 3:
          typeimg = require("@/assets/category_question.png");
          break;
        case 4:
          typeimg = require("@/assets/category_share.png");
          break;
      }
      return typeimg;
    },
    movePage(postno) {
      this.$router.push(`/community/detail/${postno}`);
    },
    changeDate(str) {
      return str.substring(0, 10) + " " + str.substring(11, 19);
    },
    splitContent(content) {
      if (content.length > 50) return content.substring(0, 51) + " ...더보기";
      else return content;
    },
    getModal(post) {
      this.delPost = post;
    },
    deletePost(postno) {
      axios.delete("post/" + postno).then((data) => {
        // console.log(data);
        if (data.data == "success") window.location.reload();
      });
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
  .view {
    text-align: center;
  }
  #search-area {
    max-width: 450px;
  }
}
#paging .page-link  {
  color: #b6c790;
}
.page-item.active .page-link {
  color: #ffffff !important;
  background-color: #b6c790 !important;
}
</style>
