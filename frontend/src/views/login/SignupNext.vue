<template>
  <div class="container">
    <div class="mycontainer py-4">
      <h2 class="mb-5">회원가입 완료</h2>
      <form action="">
        <div class="mb-3 container w-75 text-center">
            <div class="my-5">
                <div><strong>닉네임</strong>님,</div>
                <p>회원가입을 축하드립니다.</p>
                </div>
            </div>
            <div class="my-5">
                <div>지역을 선택하고,</div>
                <div class="mb-3">날씨에 따라 알림을 받아보세요!</div>
                <!-- Button trigger modal -->
<button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#exampleModal">
  Launch demo modal
</button>

<!-- Modal -->
<div class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="exampleModalLabel">Modal title</h5>
        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
      </div>
      <div class="modal-body">
        ...
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
        <button type="button" class="btn btn-primary">Save changes</button>
      </div>
    </div>
  </div>
</div>
            </div>
        <router-link to=""><button class="btn btn-success">서비스 시작</button></router-link>
      </form>
    </div>

  </div>
</template>

<script>
export default {

}
</script>

<style>

</style>