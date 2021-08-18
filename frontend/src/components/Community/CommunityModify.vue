<template>
  <div class="container" align="center">
    <div class="mt-5 col-lg-6" align="center">
      <h2>게시글 수정</h2>
      <write-form type="modify"></write-form>
    </div>
  </div>
</template>

<script>
 import WriteForm from "@/components/Community/include/WriteForm.vue";

  export default {
    name: "CommunityModify",
    components: {
      WriteForm,
    },
  };
</script>

<style>
.container > * {
  font-family: Noto Sans KR;
  font-style: normal;
}
</style>