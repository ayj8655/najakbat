<template>
  <div class="container" id="bg">
    <div id="head" align="left">
      <span id="left"
        ><img src="@/assets/modal_back.png" width="25px" @click="moveBack"
      /></span>
      <span id="right">
        <button
          class="btn btn-success"
          data-bs-toggle="modal"
          data-bs-target="#harvModal"
        >
          수확
        </button>
        <button
          class="btn btn-danger"
          data-bs-toggle="modal"
          data-bs-target="#delModal"
        >
          삭제
        </button>
      </span>
    </div>
    <div id="thumbnail-area" align="center">
      <div><img src="@/assets/leaf_darkgreen.png" width="60px" /></div>
      <!-- <div><img :src="crop.image" width="100px" /></div> -->
    </div>
    <div class="m-2">
      <font-awesome-icon
        v-if="ucrop.water"
        :icon="['fas', 'tint']"
        size="lg"
        class="water-color"
      />
      <font-awesome-icon
        v-else
        :icon="['fas', 'tint']"
        size="lg"
        class="no-water-color"
        @click.prevent="commitWater(ucrop.userCropNumber)"
      />
    </div>
    <div class="row m-2" id="gray-box">
      <div id="data-black"><strong>{{ crop.name }}</strong></div>
      <div id="inline" v-if="this.nameEdit">
        <input
          type="text"
          class="form-control"
          id="nameForEdit"
          name="nameForEdit"
          v-model="nameForEdit"
          placeholder="작물 이름을 작성해주세요"
        />
        <button class="btn btn-outline-success" :disabled="this.nameForEdit.length==0 || this.nameForEdit == this.ucrop.cropNickname" @click="editCropNickname">저장</button>
        <button class="btn btn-outline-secondary" @click="editName(false)">취소</button>
      </div>
      <div id="inline" v-else>
          <font-awesome-icon :icon="['fas', 'pencil-alt']" size="lg" class="pen-color-none" />
            <h3>{{ ucrop.cropNickname }}</h3>
          <font-awesome-icon :icon="['fas', 'pencil-alt']" size="lg" @click="editName(true)" class="pen-color" />
      </div>
      <hr />
      <div id="inline" v-if="this.descEdit">
        <input
          type="text"
          class="form-control"
          id="descForEdit"
          name="descForEdit"
          v-model="descForEdit"
          placeholder="작물 소개를 작성해주세요"
        />
        <button class="btn btn-outline-success" :disabled="this.descForEdit.length==0 || this.descForEdit==this.ucrop.description" @click="editCropDescription">저장</button>
        <button class="btn btn-outline-secondary" @click="editDesc(false)">취소</button>
      </div>
      <div id="inline" v-else>
          <font-awesome-icon :icon="['fas', 'pencil-alt']" size="sm" class="pen-color-none" />
          <p>{{ ucrop.description }}</p>
          <font-awesome-icon :icon="['fas', 'pencil-alt']" size="sm" @click="editDesc(true)" class="pen-color" />
      </div>
    </div>
    <div class="row m-2" id="gray-box">
      <div>
        <h3>D - {{ ucrop.remainDate }}</h3>
      </div>
      <hr class="m-2" />
      <div class="col-6">
        <div><strong>등록일</strong></div>
        <div>{{ this.plantedDate }}</div>
      </div>
      <div class="col-6">
        <div><strong>수확목표일</strong></div>
        <div>{{ this.targetDate }}</div>
      </div>
    </div>
    <div class="m-2" id="contents-area">
      <h4>날씨</h4>
      <div id="content" v-if="this.weather">
        <img :src="weatherImg" width="45px" class="m-2" />
        <p>{{ this.weather.city }} {{ this.weather.gugun }}</p>
        <p>{{ this.weather.weather }}</p>
        <p>온도: {{ this.weather.temperature }}℃</p>
        <p>습도: {{ this.weather.humidity }}%</p>
      </div>
      <div id="content" v-else>
        <h3 class="mt-3 mb-3">:(</h3>
        <p>날씨 정보를 불러오려면</p>
        <p>회원정보 수정페이지에서 주소를 등록해주세요.</p>
        <button class="btn btn-secondary m-2" @click="movePage">설정하러 가기</button>
      </div>
    </div>
    <div class="m-2" id="contents-area">
      <h4>상태달력</h4>
      <div>
        <calendar></calendar>
        <!-- <b-calendar
          v-model="value"
          :date-info-fn="dateClass"
          label-help=""
          locale="ko"
        ></b-calendar> -->
      </div>
    </div>
    <div class="row m-2">
      <h4>기록</h4>
      <div class="col-6" v-if="this.water.length">
        <div id="more" @click="waterModalOn()"><span>more ></span></div>
        <div id="water-content">
          <div id="water-content-item" v-if="this.water.length > 0">
            <span>
              <img src="@/assets/water_on.png" width="30px" />
              물 주기
            </span>
            <div id="date">{{ this.water[0].recordDate }}</div>
          </div>
          <div id="water-content-item" v-if="this.water.length > 1">
            <span>
              <img src="@/assets/water_on.png" width="30px" />
              물 주기
            </span>
            <div id="date">{{ this.water[1].recordDate }}</div>
          </div>
          <div id="water-content-item" v-if="this.water.length > 2">
            <span>
              <img src="@/assets/water_on.png" width="30px" />
              물 주기
            </span>
            <div id="date">{{ this.water[0].recordDate }}</div>
          </div>
        </div>
      </div>
      <div class="col-6" v-else>
        <div id="more"><span>more ></span></div>
        <div  id="gray-box-2">물을 준 기록이 없어요 :(</div>
      </div>
      <div class="col-6" v-if="this.record.length">
        <div id="more"><span>more ></span></div>
        <div id="record-content">
          <div id="record-content-item" v-if="this.record.length > 0">
            <span>
              <img width="30px" />
              상태기록
              <div>{{ this.record[0].state }}</div>
            </span>
            <div id="date">{{ this.record[0].recordDate }}</div>
          </div>
          <div id="record-content-item" v-if="this.record.length > 1">
            <span>
              <img width="30px" />
              상태기록
              <div>{{ this.record[1].state }}</div>
            </span>
            <div id="date">{{ this.record[1].recordDate }}</div>
          </div>
          <div id="record-content-item" v-if="this.record.length > 2">
            <span>
              <img width="30px" />
              상태기록
              <div>{{ this.record[2].state }}</div>
            </span>
            <div id="date">{{ this.record[2].recordDate }}</div>
          </div>
        </div>
      </div>
      <div class="col-6" id="gray-box-2" v-else>
        <div id="more"><span>more ></span></div>
        <div id="gray-box-2">등록한 상태기록이 없어요 :(</div>
      </div>
    </div>
    <div>
      <h4>통계</h4>
    </div>
    <div>
      <h4>가격정보</h4>
    </div>
    <div>
      <h4>작물 도감</h4>
      <div class="m-2" id="contents-area">
        <div id="content">
          <div class="mt-5 mb-5">
            <p><strong>햇빛 선호도</strong></p>
            <div id="sun-area">
              <span v-for="(s, index) in suns" :key="index"
                ><img :src="s"
              /></span>
            </div>
          </div>
          <div class="mb-5">
            <p><strong>물 선호도</strong></p>
            <div id="water-area">
              <span v-for="(w, index) in waters" :key="index"
                ><img :src="w"
              /></span>
            </div>
          </div>
          <div class="mb-5">
            <p><strong>생육 온도</strong></p>
            <div id="temp-area">
              <span>{{ this.lowTemp }}</span>
              <span><div id="temp" /></span>
              <span>{{ this.highTemp }}</span>
            </div>
          </div>
        </div>
      </div>
    </div>
    <!-- <div>
      <h4>커뮤니티</h4>
    </div> -->
    <div id="foot"></div>
    <water-modal class="m-5" id="water-modal" :water="water" :waterFlag="waterFlag" @water-modal-off="waterModalOff"></water-modal>
    <!-- Modal -->
    <div
      class="modal fade"
      id="harvModal"
      tabindex="-1"
      aria-labelledby="harvModalLabel"
      aria-hidden="true"
    >
      <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title" id="harvModalLabel">수확하기</h5>
            <button
              type="button"
              class="btn-close"
              data-bs-dismiss="modal"
              aria-label="Close"
            ></button>
          </div>
          <div class="modal-body">
            <div class="d-flex justify-content-start mt-2">
              <h5 class="mb-0">
                이번 수확의 추정이득을 작성해주세요! 😊
                <span class="text-danger"> *</span>
              </h5>
            </div>
            <div class="form-floating">
              <textarea
                class="form-control my-3"
                placeholder="Leave a comment here"
                id="floatingTextarea"
                v-model="price"
              ></textarea>
              <label for="floatingInputValue"
                >숫자만 작성해주세요 (ex. 3,650원 → 3650)</label
              >
            </div>
          </div>
          <hr />
          <div>해당 작물 재배를 계속하시겠습니까? :)</div>
          <div class="modal-footer">
            <button
              type="button"
              class="btn btn-success"
              :disabled="this.price.length == 0 || isNaN(this.price)"
              @click="harvestCrop(false)"
            >
              재배 계속하기
            </button>
            <button
              type="button"
              class="btn btn-danger"
              data-bs-dismiss="modal"
              :disabled="this.price.length == 0 || isNaN(this.price)"
              @click="harvestCrop(true)"
            >
              재배 그만두기
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
    <!-- Modal -->
    <div
      class="modal fade"
      id="delModal"
      tabindex="-1"
      aria-labelledby="delModalLabel"
      aria-hidden="true"
    >
      <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title" id="delModalLabel">내 농작물 삭제하기</h5>
            <button
              type="button"
              class="btn-close"
              data-bs-dismiss="modal"
              aria-label="Close"
            ></button>
          </div>
          <div class="modal-body">해당 농작물의 삭제를 진행하시겠습니까?</div>
          <div class="modal-footer">
            <button type="button" class="btn btn-danger" data-bs-dismiss="modal" @click="deleteCrop()">
              삭제
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
import Calendar from './include/Calendar.vue';
import WaterModal from './include/WaterModal.vue';

export default {
  components: {Calendar, WaterModal},
  data() {
    return {
      ucropno: this.$route.params.no,
      ucrop: {},
      record: [],
      water: [],
      crop: {},
      weather: {},
      weatherImg: require("@/assets/sun.png"),
      value: "",
      hideHeader: true,
      disabled: true,
      suns: [null, null, null, null, null],
      waters: [null, null, null, null, null],
      plantedDate: "",
      targetDate: "",
      lowTemp: "",
      highTemp: "",
      price: "",
      waterFlag: false,
      nameEdit: false,
      descEdit: false,
      nameForEdit: "",
      descForEdit: "",
    };
  },
  created() {
    axios.get(`user/crop/detail?userCropNumber=${this.ucropno}`).then((res) => {
      this.ucrop = res.data;
      this.nameForEdit = this.ucrop.cropNickname;
      this.descForEdit = this.ucrop.description;
      this.plantedDate = this.ucrop.plantedDate.substring(0, 10);
      this.targetDate = this.ucrop.targetDate.substring(0, 10);
      axios.get(`guide/plant/${this.ucrop.cropNumber}`).then((data) => {
        // console.log(this.crop);
        this.crop = data.data;
        this.crop.image = require("@/assets/crop/" + this.crop.image);
        this.lowTemp = this.crop.temperature.split("~")[0] + "℃";
        this.highTemp = this.crop.temperature.split("~")[1] + "℃";
        this.suns.forEach((s, index) => {
          this.suns[index] =
            index < this.crop.sun
              ? require("@/assets/sun.png")
              : require("@/assets/sun_off.png");
        });
        this.waters.forEach((w, index) => {
          this.waters[index] =
            index < this.crop.water
              ? require("@/assets/water_on.png")
              : require("@/assets/water_off.png");
        });
      });
      axios
        .get(`user/crop/record?userCropNumber=${this.ucropno}`)
        .then((data) => {
          this.record = data.data;
          this.record.forEach((r) => {
            r.recordDate = this.changeDate(r.recordDate);
          });
          // console.log(this.record);
        })
        .catch((err) => {
          console.log(err);
        });
      axios
        .get(`user/crop/water?userCropNumber=${this.ucropno}`)
        .then((data) => {
          this.water = data.data;
          // console.log(this.water);
          this.water.forEach((w) => {
            w.recordDate = this.changeDate(w.recordDate);
          });
        })
        .catch((err) => {
          console.log(err);
        });
    });
    axios.get("user/my").then((res) => {
      if(res.data.address) {
        axios.get(`weather/${res.data.address}`).then((data) => {
          // console.log(data);
          this.weather = data.data;
          switch (this.weather.weather) {
            case "흐림":
              this.weatherImg = require("@/assets/cloud.png");
              break;
            case "구름많음":
              this.weatherImg = require("@/assets/cloud-2.png");
              break;
            case "비":
              this.weatherImg = require("@/assets/rain.png");
              break;
            case "비/눈":
              this.weatherImg = require("@/assets/rain-snow.png");
              break;
            case "눈":
              this.weatherImg = require("@/assets/snow.png");
              break;
            case "소나기":
              this.weatherImg = require("@/assets/shower.png");
              break;
            default:
              break;
          }
        });
      } else {
        this.weather = null;
      }
    });
  },
  methods: {
    moveBack() {
      this.$router.push("/mycrop");
    },
    movePage() {
      this.$router.push("/user/modify");
    },
    harvestCrop(flag) {
      if (flag) {
        //완전 수확
        axios
          .put(
            `user/crop/fullharvest?userCropNumber=${this.ucropno}&price=${this.price}`
          )
          .then((data) => {
            if (data.data == "success") this.$router.push("/mycrop");
          });
      } else {
        //임시 수확
        axios
          .put(
            `user/crop/tempharvest?userCropNumber=${this.ucropno}&price=${this.price}`
          )
          .then((data) => {
            if (data.data == "success") window.location.reload();
          });
      }
    },
    deleteCrop() {
      axios.delete(`user/crop/?userCropNumber=${this.ucropno}`).then((data)=>{
        if(data.data=="success") this.$router.push("/mycrop");
      });
    },
    changeDate(str) {
      if (str) return str.substring(0, 10) + " " + str.substring(11, 19);
    },
    commitWater(event) {
      axios
        .post(`user/crop/water?userCropNumber=${event}`)
        .then((res) => {
          if (res.data === "success") {
            this.ucrop.water = true;
          }
        })
        .catch((err) => {
          console.error(err);
        });
    },
    waterModalOn() {
      this.waterFlag = true;
    },
    waterModalOff() {
      this.waterFlag = false;
    },
    editName(flag) {
      this.nameEdit = flag;
    },
    editDesc(flag) {
      this.descEdit = flag;
    },
    editCropNickname() {
      axios.put(`user/crop/?userCropNumber=${this.ucropno}&userCropNickname=${this.nameForEdit}&description=${this.ucrop.description}`).then((data) => {
        if(data.data=="success") {
          this.ucrop.cropNickname = this.nameForEdit;
          this.editName(false);
        }
      });
    },
    editCropDescription() {
      axios.put(`user/crop/?userCropNumber=${this.ucropno}&userCropNickname=${this.ucrop.cropNickname}&description=${this.descForEdit}`).then((data) => {
        if(data.data=="success") {
          this.ucrop.description = this.descForEdit;
          this.editDesc(false);
        }
      });
    },
    dateClass(ymd, date) {
      const day = date.getDate();
      return day >= 10 && day <= 20 ? "table-info" : "";
    },
  },
};
</script>

<style scoped>
#bg {
  min-height: 812px;
  background-color: #446631;
}
#bg > * {
  font-family: Noto Sans KR;
  font-style: normal;
}
#right {
  float: right;
  display: inline-block;
}
#thumbnail-area > div {
  align: center;
  background-color: #ffffff;
  width: 100px;
  height: 100px;
  border-radius: 50%;
}
#thumbnail-area img {
  margin-top: 20px;
}
.water-color {
  color: #0bc3fd;
}
.no-water-color {
  color: #999999;
}
#gray-box {
  /* border: 2px solid #446631; */
  color: #ffffff;
  background-color: #aaaaaa;
  border-radius: 5px;
  min-height: 100px;
  padding: 20px;
}
#gray-box-2 {
  /* border: 2px solid #446631; */
  color: #ffffff;
  background-color: #aaaaaa;
  border-radius: 5px;
  min-height: 50px;
  padding: 10px;
  font-size: 12px;
}
#data-black {
  color: #2d2d2d;
}
h4 {
  text-align: left;
  font-weight: bold;
  color: #ffffff;
}
#more {
  color: #ffffff;
  text-align: right;
}
#date {
  font-size: 12px;
}
#water-content-item,
#record-content-item {
  min-height: 60px;
  background-color: #ffffff;
  border-radius: 5px;
  margin: 5px 0;
}
#contents-area > div {
  /* border: 2px solid #446631; */
  box-sizing: border-box;
  background-color: #ffffff;
  border-radius: 5px;
  min-height: 200px;
  padding: 20px;
}
#temp {
  width: 50%;
  height: 0px;
  background: #999999;
  border: 5px solid #999999;
  border-radius: 5px;
  display: inline-block;
  margin: 0px 5px;
}
#sun-area img,
#water-area img {
  width: 30px;
  margin: 5px;
}
#foot {
  height: 60px;
}

#water-modal {
  background-color: #446631;
  border-radius: 15px;
  border: solid 3px white;
  position: fixed;
  top: 50%;
  left: 50%;
  transform:translate(-50%, -50%);
}
.pen-color-none {
  color: #aaaaaa;;
}
.pen-color {
  color: #ffffff;
}
#inline > * {
  display: inline-block;
  margin: 5px;
}
</style>
