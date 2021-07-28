<template>
<div class="container">
  <h2>1:1 문의</h2>
  <h3>문의사항 분류</h3>
  <select v-model="selected">
    <option disabled value="">문의사항을 골라주세요</option>
    <option>A</option>
    <option>B</option>
    <option>C</option>
  </select>
  <h3>문의사항 내용</h3>
<textarea v-model="message" placeholder="여러줄을 입력해보세요"></textarea>
</div>
</template>

<script>
export default {

}
</script>

<style>

</style>