<template>
  <div class="container" align="center">
    <div class="col-lg-6" align="center">
      <h2>게시글 등록</h2>
      <write-form type="create"></write-form>
    </div>
  </div>
</template>

<script>
 import WriteForm from "@/components/Community/include/WriteForm.vue";

  export default {
    name: "CommunityWrite",
    components: {
      WriteForm,
    },
  };
</script>

<style>

</style>