<template>
    <div class="container mt-5">
      <div style="text-align: center">
        <h3 class="m-5 crop">내 농작물</h3>
      </div>
      <span class="etc">
        <v-btn elevation="2" right class="btn btn-success" data-bs-toggle="modal" data-bs-target="#exampleModal">
          +
        </v-btn>
      </span>

    <div class="mt-2" id="crops-area">
      <hr />
      <div
        v-for="(crop, index) in crops"
        :key="index"
        @click="movePage(crop.cropNumber)"
      >
        <div>
          <img id="thumbnail" :src="cropImg[index]" width="45px" />
          <strong>{{ crop.name }}</strong>
        </div>
        <hr />
      </div>
    </div>

    <!-- Modal -->
    <div class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="exampleModalLabel">작물 선택</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body d-flex justify-content-around">          
                  <select v-model ="crop" required class="form-select form-select-lg m-2" aria-label=".form-select-lg example"  @change='selectCrop(crop.cropNumber)'>
                    <option value ='' hidden> 작물 </option>
                    <option v-for= "crop in crops" v-bind:value="crop" v-bind:key="crop.cropNumber">{{crop.name}}</option>
                  </select>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-success" data-bs-dismiss="modal" @click="saveCrop()" :disabled="!this.userSelectCrop===''" >저장</button>
                </div>
            </div>
        </div>
    </div>

  </div>
</template>

<script>
import axios from "axios";

export default {
  data() {
    return {
      crops: [],
      usercrops:[],
      cropImg: [],
      usercropImg: [],
      userSelectCrop:'',
    };
  },
  created() {
    axios.get("user/crop").then((data) => {
      this.usercrops = data.data;
      this.usercrops.forEach((c, index) => {
        this.usercropImg[index] = (this.usercrops[index].image)? require("@/assets/crop/"+this.usercrops[index].image):require("@/assets/thumbnail.png");
      });
    });
 
    axios.get("guide/plant/summary").then((data) => {
      this.crops = data.data;
      this.crops.forEach((c, index) => {
        this.cropImg[index] = (this.crops[index].image)? require("@/assets/crop/"+this.crops[index].image):require("@/assets/thumbnail.png");
      });
    });
  
  },
  methods: {
    movePage(cropNumber) {
      this.$router.push(`/mylist/detail/${cropNumber}`);
    },
    selectCrop(event) {
      this.userSelectCrop = event;
      console.log(this.userSelectCrop);

    },
    saveCrop(){


    },
  },
}
</script>

<style>
.container > * {
  font-family: Noto Sans KR;
  font-style: normal;
}
.dict {
  font-weight: bold;
}
#search-area > * {
  display: inline-block;
}
#search-area > img {
  margin-right: 5px;
}
#crops-area > div {
  text-align: left;
}
#thumbnail {
  margin: 5px 20px;
}
.crop{
  display: inline;
}
.etc {
  float: right;
  display: inline-block;
}

</style>