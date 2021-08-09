<template>
  <div id="main-contents" class="container-fluid">
    <div class="container" id="body" align="center">
      <h3>1:1 문의 목록</h3>
      <table class="table table-hover mt-5">
        <colgroup>
          <col width="10%" />
          <col width="20%" />
          <col width="20%" />
          <col width="20%" />
          <col width="10%" />
          <col width="10%" />
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
        <tbody id="userlist" v-for="(qna, index) in qnas" :key="index">
          <tr class="view" data-id="">
            <td>{{ qna.qnaNumber }}</td>
            <td><img :src="changeType(qna.qnaType)" width="35px" /></td>
            <td>{{ qna.title }}</td>
            <td
              v-text="splitContent(qna.content)"
              @click="movePage(qna.qnaNumber)"
            ></td>
            <td>{{ qna.userNickname }}</td>
            <td v-text="changeDate(qna.date)" />
            <td>
              <button
                v-if="!qna.answer"
                type="button"
                class="ansBtn btn btn-outline-primary btn-sm"
                data-bs-toggle="modal"
                data-bs-target="#ansModal"
                @click="getAnsModal(qna)"
              >
                답변하기
              </button>
              <button
                v-else
                type="button"
                class="ansBtn btn btn-outline-secondary btn-sm"
              >
                답변완료
              </button>
              <button
                type="button"
                class="delBtn btn btn-outline-danger btn-sm"
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
          <div class="modal-body">
            <p>분류: {{this.ansQnA.qnaType}}</p>
            <div>
              <label for="question">답변:</label>
              <div id="question" name="question" v-text="enterToBr(this.ansQnA.question)"></div>
            </div>
            <div class="form-group" align="left">
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
              답변완료
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
export default {
  data() {
    return {
      qnas: [],
      ansQnA: {},
      delQnA: {},
      ansContent: "",
    }
  },
  methods: {
    getAnsModal(qna) {
        this.ansQnA = qna;
    },
    getDelModal(qna) {
        this.delQnA = qna;
    },
    enterToBr(str) {
      if (str) return str.replace(/(?:\r\n|\r|\n)/g, "<br />");
    },
    answerQnA() {},
    deleteQnA(qnaNumber) {
      console.log(qnaNumber);
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
}
</style>