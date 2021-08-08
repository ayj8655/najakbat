<template>
  <div id="main-contents" class="container-fluid">
    <div class="container" id="body" align="center">
      <h3>1:1문의 목록</h3>
      <table class="table table-hover mt-5">
        <colgroup>
          <col width="10%" />
          <col width="10%" />
          <col width="20%" />
          <col width="30%" />
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
            <td>{{ qna.postNumber }}</td>
            <td><img :src="changeType(qna.qnaType)" width="35px" /></td>
            <td>{{ qna.title }}</td>
            <td
              v-text="splitContent(qna.content)"
              @click="movePage(qna.postNumber)"
            ></td>
            <td>{{ qna.userNickname }}</td>
            <td v-text="changeDate(qna.date)" />
            <td>
              <button
                type="button"
                class="delBtn btn btn-outline-danger btn-sm"
                data-bs-toggle="modal"
                data-bs-target="#delModal"
                @click="getModal(qna)"
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
export default {

}
</script>

<style>

</style>