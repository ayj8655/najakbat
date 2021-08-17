<template>
  <div id="main-contents" class="container-fluid">
    <div class="container" id="body" align="center">
      <h3>1:1 문의 목록</h3>
      <div class="row mt-5" id="search-area">
        <span class="col-4">
          <select class="form-control mr-2" name="key" id="skey" v-model="skey">
            <option value="name">닉네임</option>
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
      <table class="table table-hover mt-3">
        <colgroup>
          <col width="5%" />
          <col width="20%" />
          <col width="20%" />
          <col width="10%" />
          <col width="20%" />
          <col width="25%" />
        </colgroup>
        <thead>
          <tr>
            <th class="text-center">no.</th>
            <th class="text-center">분류</th>
            <th class="text-center">내용</th>
            <th class="text-center">닉네임</th>
            <th class="text-center">작성일</th>
            <th class="text-center"></th>
          </tr>
        </thead>
        <tbody 
          id="qnalist" 
          v-for="(qna, index) in itemsForList" 
          :key="index"
          v-show="((sword=='') || (skey=='name' && qna.userNickname.includes(sword)) || (skey=='content' && qna.question.includes(sword)))"
        >
          <tr class="view" data-id="">
            <td>{{ qna.qnaNumber }}</td>
            <td>{{ qna.qnaType }}</td>
            <td
              v-text="splitContent(qna.question)"
            ></td>
            <td>{{ qna.userNickname }}</td>
            <td v-text="changeDate(qna.date)" />
            <td>
              <button
                v-if="qna.finish"
                type="button"
                class="ansBtn btn btn-outline-secondary btn-sm disabled"
              >
                답변완료
              </button>
              <button
                v-else
                type="button"
                class="ansBtn btn btn-outline-primary btn-sm"
                data-bs-toggle="modal"
                data-bs-target="#ansModal"
                @click="getAnsModal(qna)"
              >
                답변하기
              </button>
              <button
                type="button"
                class="delBtn btn btn-outline-danger btn-sm m-2"
                data-bs-toggle="modal"
                data-bs-target="#delModal"
                @click="getDelModal(qna)"
              >
                삭제
              </button>
            </td>
          </tr>
        </tbody>
      </table>
      <b-pagination
        v-model="currentPage"
        :total-rows="rows"
        :per-page="perPage"
        aria-controls="qnalist"
        class="mt-3 mb-5 justify-content-center"
        id="paging"
      ></b-pagination>
    </div>
    <!-- Modal -->
    <div
      class="modal fade"
      id="ansModal"
      tabindex="-1"
      aria-labelledby="ansModalLabel"
      aria-hidden="true"
    >
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title" id="ansModalLabel">문의사항 답변하기</h5>
            <button
              type="button"
              class="btn-close"
              data-bs-dismiss="modal"
              aria-label="Close"
            ></button>
          </div>
          <div class="modal-body" align="left">
            <p>분류: {{this.ansQnA.qnaType}}</p>
            <div class="">
              <label for="question mt-2">질문:</label>
              <div id="question" name="question" v-html="enterToBr(this.ansQnA.question)"></div>
            </div>
            <div class="form-group mt-2" align="left">
              <label for="answer">답변:</label>
              <textarea
                class="form-control"
                rows="15"
                id="answer"
                name="answer"
                v-model="ansQnA.answer"
                placeholder="답변을 입력하세요."
              ></textarea>
            </div>
          </div>
          <div class="modal-footer">
            <button
              type="button"
              class="btn btn-success"
              @click="answerQnA"
            >
              답변 등록
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
            <h5 class="modal-title" id="delModalLabel">문의사항 삭제하기</h5>
            <button
              type="button"
              class="btn-close"
              data-bs-dismiss="modal"
              aria-label="Close"
            ></button>
          </div>
          <div class="modal-body">해당 문의사항의 삭제를 진행하시겠습니까?</div>
          <div class="modal-footer">
            <button
              type="button"
              class="btn btn-danger"
              @click="deleteQnA(delQnA.qnaNumber)"
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
  data() {
    return {
      qnas: [],
      ansQnA: {},
      delQnA: {},
      ansContent: "",
      perPage: 10,
      currentPage: 1,
      skey: "name",
      sword: "",
    }
  },
  created() {
    axios.get("qna/all").then((data)=>{
      this.qnas = data.data;
    });
  },
  computed: {
    rows() {
      return this.qnas.length;
    },
    itemsForList() {
      return this.qnas.slice(
        (this.currentPage - 1) * this.perPage,
        this.currentPage * this.perPage
      );
    },
  },
  methods: {
    changeDate(str) {
      return str.substring(0, 10) + " " + str.substring(11, 19);
    },
    splitContent(content) {
      return content.substring(0, 21) + "...";
    },
    getAnsModal(qna) {
        this.ansQnA = qna;
    },
    getDelModal(qna) {
        this.delQnA = qna;
    },
    enterToBr(str) {
      if (str) return str.replace(/(?:\r\n|\r|\n)/g, "<br />");
    },
    answerQnA() {
      axios({
        method: 'put',
        url: `qna/${this.ansQnA.qnaNumber}`,
        params: {
          qnano: this.ansQnA.qnaNumber,
          answer: this.ansQnA.answer,
        }
      }).then((data) => {
        // console.log(data.data);
        if(data.data=="success") window.location.reload();
      }).catch((err) => {
        console.log(err);
      });
    },
    deleteQnA(qnaNumber) {
      axios.delete(`qna/${qnaNumber}`).then((data)=>{
        // console.log(data.data);
        if(data.data=="success") window.location.reload();
      });
    },
  },
}
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
#paging .page-link {
  color: #b6c790;
}
.page-item.active .page-link {
  color: #ffffff !important;
  background-color: #b6c790 !important;
}
</style>