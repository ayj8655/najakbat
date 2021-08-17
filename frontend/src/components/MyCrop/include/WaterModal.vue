<template>
  <div id="water-content" v-show="waterFlag">
    <div align="right"><img src="@/assets/modal_end.png" @click="modalOff()" width="30px"></div>
    <div id="water-content-item" v-for="(w, index) in water" :key="index">
      <span>
        <img src="@/assets/water_on.png" width="15px" />
        물 주기
      </span>
      <div id="date">{{ w.recordDate }}</div>
    </div>
  </div>
</template>

<script>
export default {
  props: {
    water : Array,
    waterFlag : Boolean,
  },
  methods: {
    modalOff() {
      this.$emit("water-modal-off");
    },
  },
};
</script>

<style scoped>
#water-content-item {
  min-height: 60px;
  background-color: #ffffff;
  border-radius: 5px;
  margin: 5px 0;
}
</style>
