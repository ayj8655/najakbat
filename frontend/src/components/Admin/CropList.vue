<template>
  <div id="main-contents" class="container-fluid">
    <div class="container" id="body" align="center">
    </div>
  </div>
</template>

<script>
export default {
    data() {
        return {
            crops: [],
            updateCrop: {},
            delCrop: {},
        }
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