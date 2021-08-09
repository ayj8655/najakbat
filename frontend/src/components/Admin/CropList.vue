<template>
  <div id="main-contents" class="container-fluid">
    <div class="container" id="body" align="center">
      <h3>농작물 목록</h3>
      <table class="table table-hover mt-5">
        <colgroup>
          <col width="20%" />
          <col width="50%" />
          <col width="30%" />
        </colgroup>
        <thead>
          <tr>
            <th class="text-center">no.</th>
            <th class="text-center">농작물 이름</th>
            <th class="text-center"></th>
          </tr>
        </thead>
        <tbody id="userlist" v-for="(crop, index) in crops" :key="index">
          <tr class="view" data-id="">
            <td>{{ crop.cropNumber }}</td>
            <td id="name"><img id="thumbnail" :src="getCropImg(crop)" />{{ crop.name }}</td>
            <td>
              <button
                type="button"
                class="updateBtn btn btn-outline-success btn-sm m-2"
                data-bs-toggle="modal"
                data-bs-target="#updateModal"
                @click="getUpdateModal(crop)"
              >
                수정
              </button>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
    <!-- Modal -->
    <div
      class="modal fade"
      id="updateModal"
      tabindex="-1"
      aria-labelledby="updateModalLabel"
      aria-hidden="true"
    >
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title" id="updateModalLabel">농작물 수정하기</h5>
            <button
              type="button"
              class="btn-close"
              data-bs-dismiss="modal"
              aria-label="Close"
            ></button>
          </div>
          <div class="modal-body">
              <p>{{this.thisCrop.name}}</p>
              <div class="form-group" align="left">
                <label for="description">설명:</label>
                <textarea
                    class="form-control"
                    rows="15"
                    id="description"
                    name="description"
                    v-model="thisCrop.description"
                    placeholder="설명을 입력하세요."
                ></textarea>
              </div>
              <div class="form-group" align="left">
                <label for="cultivation">재배:</label>
                <textarea
                    class="form-control"
                    rows="15"
                    id="cultivation"
                    name="cultivation"
                    v-model="thisCrop.cultivation"
                    placeholder="재배방법을 입력하세요."
                ></textarea>
              </div>
              <div class="form-group" align="left">
                <label for="sun">햇빛 선호도:</label>
                <input
                    type="text"
                    class="form-control"
                    id="sun"
                    name="sun"
                    v-model="thisCrop.sun"
                    placeholder="햇빛 선호도를 입력하세요. (1~5)"
                />
              </div>
              <div class="form-group" align="left">
                <label for="water">물 선호도:</label>
                <input
                    type="text"
                    class="form-control"
                    id="water"
                    name="water"
                    v-model="thisCrop.water"
                    placeholder="물 선호도를 입력하세요. (1~5)"
                />
              </div>
              <div class="form-group" align="left">
                <label for="temperature">생육 온도:</label>
                <input
                    type="text"
                    class="form-control"
                    id="temperature"
                    name="temperature"
                    v-model="thisCrop.temperature"
                    placeholder="생육온도를 입력하세요. (ex.15~25)"
                />
              </div>
          </div>
          <div class="modal-footer">
            <button
              type="button"
              class="btn btn-success"
              @click="updateCrop(this.thisCrop)"
            >
              수정
            </button>
            <button
              type="button"
              class="btn btn-secondary"
              data-bs-dismiss="modal"
            >
              취소
            </button>
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
      thisCrop: {},
    };
  },
  created() {
    axios.get("guide/plant/").then((data) => {
      this.crops = data.data;
    });
  },
  methods: {
    getCropImg(crop) {
      return (crop.image)? require("@/assets/crop/"+crop.image):require("@/assets/thumbnail.png");
    },
    getUpdateModal(crop) {
        this.thisCrop = crop;
    },
    // updateCrop(crop) {
    //     axios.put("guide/plant/", {}).then((data) => {
    //         console.log(data);
    //     });
    // },
  },
};
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
#name {
    text-align: left;
}
#thumbnail {
  margin: 5px 20px;
  width: 45px; height: 45px;
  border-radius: 5px;
}
</style>
