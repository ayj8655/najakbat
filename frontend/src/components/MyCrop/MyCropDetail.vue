<template>
  <div class="container" id="bg">
    <div align="left">
      <span id="left"
        ><img src="@/assets/modal_back.png" width="25px" @click="moveBack"
      /></span>
      <span id="right">
        <button>수확</button>
        <button>삭제</button>
      </span>
    </div>
    <div>
      <img v-if="ucrop.is_water" src="@/assets/water_on.png" width="30px" />
      <img v-else src="@/assets/water_off.png" width="30px" />
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
      <div class="col-6"></div>
      <div class="col-6"></div>
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
    <div>
      <h4></h4>
    </div>
    <div id="foot"></div>
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
      value : '',
      hideHeader: true,
      disabled : true,
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
          .get(`user/crop/record?cropNumber=${this.ucropno}`)
          .then((data) => {
            this.record = data.data;
          });
        axios.get(`user/crop/water?cropNumber=${this.ucropno}`).then((data) => {
          this.water = data.data;
        });
      });
  },
  methods: {
    moveBack() {
      this.$router.push("/mycrop");
    },
    dateClass(ymd, date) {
        const day = date.getDate()
        return day >= 10 && day <= 20 ? 'table-info' : ''
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
#gray-box {
  /* border: 2px solid #446631; */
  box-sizing: border-box;
  background-color: #c6c6c6;
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
