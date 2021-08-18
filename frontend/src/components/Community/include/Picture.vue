<template>
  <div class="my-5">
    <div id="row">
      <div v-if="photos && photos.length == 1">
        <img :src="photos[0]" class="w-100 container" />
      </div>
      <div
        class="items m-2"
        v-for="(photo, index) in photos"
        :key="index"
        @click="clickPhoto(photo)"
        data-bs-toggle="modal" data-bs-target="#staticBackdrop"
        v-else
      >
        <img :src="photo" />
      </div>
    </div>
    <div class="modal fade" id="staticBackdrop" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
      <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content">
          <div class="modal-header">
            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
          </div>
          <div class="modal-body">
            <img :src="pickImg" class="w-100 container"/>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'Picture',
  props: ["images"],
  data() {
    return {
      pickImg: null
    }
  },
  computed: {
    photos() {
      if(this.$props.images) {
        var imgs = this.$props.images
        var imgsUrl = []
        for (var i = 0; i < imgs.length; i++) {
          var imgurl = "https://mococobucket.s3.ap-northeast-2.amazonaws.com/post/" + imgs[i].saveFile
          imgsUrl.push(imgurl)
        }
      }
      return imgsUrl
    }
  },
  methods: {
    clickPhoto(getPhoto) {
      this.pickImg = getPhoto
    }
  }
}
</script>

<style scoped>
  #row {
  white-space: nowrap; /* 가로스크롤시 중요한 속성 */
  overflow-x: auto;
  overflow-y: hidden;
  padding: 10px 10px 5px;
  /* background: #efefef; */
  width: auto;
}

#row .items {
  display: inline-block;
  /* margin-left: 10px; */
  width: 150px;
}
#row .items img {
  width: 150px;
  height: 150px;
  border-radius: 15px;
  border: solid 1px #999999;
}
</style>