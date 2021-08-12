<template>
  <div class="container" id="bg">
    <div id="head" align="left">
      <span id="left"
        ><img src="@/assets/modal_back.png" width="25px" @click="moveBack"
      /></span>
      <span id="right">
        <button class="btn btn-success"
          data-bs-toggle="modal"
          data-bs-target="#harvModal"
          @click="getDelModal(ucrop.userCropNumeber)">
          수확
        </button>
        <button
          class="btn btn-danger"
          data-bs-toggle="modal"
          data-bs-target="#delModal"
          @click="getDelModal(ucrop.userCropNumeber)"
        >
          삭제
        </button>
      </span>
    </div>
    <div id="thumbnail-area" align="center">
      <div><img src="@/assets/leaf_darkgreen.png" width="60px" /></div>
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
        @click.prevent="commitWater(this.ucrop.userCropNumber)"
      />
    </div>
    <div id="gray-box">
      <div>{{ crop.name }}</div>
      <div>
        <h3>{{ ucrop.cropNickname }}</h3>
      </div>
      <div>{{ ucrop.description }}</div>
    </div>
    <div class="row" id="gray-box">
      <div>
        <h3>D - {{ ucrop.remainDate }}</h3>
      </div>
      <hr class="m-2" />
      <div class="col-6">
        <div>등록일</div>
        <div>{{ this.plantedDate }}</div>
      </div>
      <div class="col-6">
        <div>수확목표일</div>
        <div>{{ this.targetDate }}</div>
      </div>
    </div>
    <div>
      <h4>날씨</h4>
    </div>
    <div id="contents-area">
      <h4>상태달력</h4>
      <div>
        <b-calendar
          v-model="value"
          locale="ko"
          :hide-header="hideHeader"
          label-help=""
          :date-info-fn="dateClass"
        ></b-calendar>
      </div>
    </div>
    <div class="row">
      <h4>기록</h4>
      <div class="col-6" v-if="this.water.length">
        <div id="more"><span>more ></span></div>
        <div id="water-content">
          <span>
            <img src="@/assets/water_on.png" width="30px" />
            물 주기
            <div>{{ this.water[0].recordDate }}</div>
          </span>
          <span>
            <img src="@/assets/water_on.png" width="30px" />
            물 주기
            <div>{{ this.water[1].recordDate }}</div>
          </span>
          <span>
            <img src="@/assets/water_on.png" width="30px" />
            물 주기
            <div>{{ this.water[2].recordDate }}</div>
          </span>
        </div>
      </div>
      <div class="col-6" v-else>
        <div>물을 준 기록이 없습니다 :(</div>
      </div>
      <div class="col-6" v-if="this.record.length">
        <div id="more"><span>more ></span></div>
        <div id="record-content">
          <span>
            <img width="30px" />
            상태기록
            <div>{{ this.record[0].state }}</div>
            <div>{{ this.record[0].recordDate }}</div>
          </span>
          <span>
            <img width="30px" />
            상태기록
            <div>{{ this.record[1].state }}</div>
            <div>{{ this.record[1].recordDate }}</div>
          </span>
          <span>
            <img width="30px" />
            상태기록
            <div>{{ this.record[2].state }}</div>
            <div>{{ this.record[2].recordDate }}</div>
          </span>
        </div>
      </div>
      <div class="col-6" v-else>
        <div>등록한 상태기록이 없습니다 :(</div>
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
      <div class="m-4" id="contents-area">
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
    <div>
      <h4>커뮤니티</h4>
    </div>
    <div id="foot"></div>
    
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
              해당 작물 재배를 계속하시겠습니까? :)
            </div>
            <div class="modal-footer">
              <button
                type="button"
                class="btn btn-success"
                @click="harvestCrop()"
              >
                재배 계속하기
              </button>
              <button
                type="button"
                class="btn btn-danger"
                @click="deleteCrop()"
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
              <button
                type="button"
                class="btn btn-danger"
                @click="deleteCrop()"
              >
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

export default {
  data() {
    return {
      ucropno: this.$route.params.no,
      ucrop: {},
      record: [],
      water: [],
      crop: {},
      harvestCropNo: "",
      value: "",
      hideHeader: true,
      disabled: true,
      suns: [null, null, null, null, null],
      waters: [null, null, null, null, null],
      plantedDate: "",
      targetDate: "",
      lowTemp: "",
      highTemp: "",
    };
  },
  created() {
    axios
      .get(`user/crop/all?userNumber=${this.$store.state.userNumber}`)
      .then((data) => {
        data.data.forEach((c) => {
          if (this.ucropno == c.cropNumber) {
            this.ucrop = c;
            // break;
          }
        });
        this.plantedDate = this.ucrop.plantedDate.substring(0, 10);
        this.targetDate = this.ucrop.targetDate.substring(0, 10);
        console.log(this.ucrop);
        axios.get(`guide/plant/${this.ucrop.cropNumber}`).then((data) => {
          this.crop = data.data;
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
          })
          .catch((err) => {
            console.log(err);
          });
        axios
          .get(`user/crop/water?userCropNumber=${this.ucropno}`)
          .then((data) => {
            this.water = data.data;
          })
          .catch((err) => {
            console.log(err);
          });
      });
  },
  methods: {
    moveBack() {
      this.$router.push("/mycrop");
    },
    getDelModal(no) {
      this.harvestCropNo = no;
    },
    harvestCrop() {},
    deleteCrop() {
      // axios.
    },
    commitWater(event) {
      axios
        .post(`user/crop/water?userCropNumber=${event}`)
        .then((res) => {
          if (res.data === "success") {
            for (var i = 0; i < this.usercrops.length; i++) {
              if (this.usercrops[i].userCropNumber == event) {
                this.usercrops[i].water = true;
              }
            }
          }
        })
        .catch((err) => {
          console.error(err);
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
  box-sizing: border-box;
  background-color: #aaaaaa;
  border-radius: 5px;
  min-height: 100px;
  padding: 20px;
  margin: 15px;
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
#water-content,
#record-content {
  min-height: 60px;
  background-color: #ffffff;
  border-radius: 5px;
  margin: 5px;
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
  height: 150px;
}
</style>
