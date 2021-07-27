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
                <div>
                    <b-button v-b-modal.modal-1 class="btn btn-success">지역 선택하기</b-button>

                    <b-modal id="modal-1" title="BootstrapVue">
                        <p class="my-4">Hello from modal!</p>
                    </b-modal>
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