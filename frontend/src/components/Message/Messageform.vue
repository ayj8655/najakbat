<template>
<div>
<button type="button" class="btn btn-success" data-bs-toggle="modal" data-bs-target="#exampleModal">
  새 쪽지 쓰기
</button>

<!-- Modal -->
<div class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
  <div class="modal-dialog modal-dialog-centered">
    <div class="modal-content">
      <div class="modal-header">
        <div class="modal-title" id="exampleModalLabel">
          <div class="form-group nicknameinput" align="center">
            <input
              type="text"
              class="form-control"
              id="nickname"
              name="nickname"
              v-model="nickname"
              placeholder="받는사람의 닉네임을 입력하세요."
            />
          </div>
        </div>
        <button type="button" class="btn-close mx-0" data-bs-dismiss="modal" aria-label="Close"></button>
      </div>
      <div class="modal-body">
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
      <div class="modal-footer">
        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
        <button type="button" class="btn btn-primary">Save changes</button>
      </div>
    </div>
  </div>
</div>
</div>
</template>

<script>
export default {
  data () {
    return {
      content: null,
      nickname: null,
    }
  }
}
</script>

<style>
.nicknameinput {
  width: 350px;
}
</style>