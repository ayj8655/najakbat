<template>
  <div class="container mt-3 all-font box-border" v-if="targetActivity">
    <div class="mt-3">
      <strong>{{ userNickname }}</strong>님의 활동
    </div>
    <div class="row my-3">
      <div class="col px-0">
        <img src="@/assets/drop.png" width="30px" />
        <p class="mb-0 mt-2"><strong>{{ userNickname }}</strong>님이</p> 
        <p class="mb-0">키운 작물 개수</p>
        총 <strong>{{ targetActivity.cropCount }}</strong>개
      </div>
      <div class="col px-0">
        <img src="@/assets/sprout.png" width="30px" />
        <p class="mb-0 mt-2"><strong>{{ userNickname }}</strong>님의</p>
        <p class="mb-0">수확 횟수</p>
        총 <strong>{{ targetActivity.cropFinishCount }}</strong>회
      </div>
      <div class="col px-0">
        <img src="@/assets/money.png" width="30px" />
        <p class="mb-0 mt-2"><strong>{{ userNickname }}</strong>님의</p> 
        <p class="mb-0">추정이득</p>
        <strong>{{ targetActivity.gold }}</strong>원
      </div>
    </div>
    <div>
      <hr class="profile-line container my-3">
      <strong>{{ userNickname }}</strong>님의 완료 업적
      <div id="row">
        <div
          class="items m-2"
          v-for="(achieve, index) in achieves"
          :key="index"
        >
          <div v-if="achieve.finish" class="mission-font">
            <img :src="findImg(achieve.achieveName)" class="container">
            <p class="mt-2">{{ achieve.achieveName }}</p>
          </div>
          <div v-else class="mission-font">
            <img src="../../assets/lock.png" class="container"/>
            <p class="mt-2">{{ achieve.achieveName }}</p>
          </div>
        </div>
      </div>
    </div>
    <hr class="profile-line container my-3">
    <div class="container">
      <strong>{{ userNickname }}</strong>님의 농작물
      <div id="row" v-if="usercrops != []">
        <div
          class="items m-2"
          v-for="(usercrop, index) in usercrops"
          :key="index"
        >
          <div class="mission-font">
            <img :src="findCropImg(usercrop)" class="container">
            <p class="mt-2">가나</p>
          </div>
        </div>
      </div>
      <div v-else class="my-3">
        <h3>등록된 작물이 없습니다</h3>
        <div class="d-flex justify-content-end mt-5">
          <router-link to="/mycrop/list" class="logo "><span>작물 등록하러 가기 👉</span></router-link>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios'

export default {
  name: 'UserActivity',
  props: ['userNumber', 'userNickname'],
  data() {
    return {
      achieves : null,
      targetActivity: null,
      crops: [],
      usercrops: [],
      cropImg: []
    }
  },
  created() {
    axios.get(`achievement/user?userNumber=${this.userNumber}`)
    .then(res => {
      this.achieves = res.data
    })
    .catch(err => {
      console.error(err);
    })

    axios.get(`user/record/${this.userNumber}`)
    .then(res => {
      this.targetActivity = res.data
    })
    .catch(err => {
      console.error(err);
    })

    axios.get("guide/plant/summary").then((data) => {
        this.crops = data.data;
        this.crops.push({cropNumber: 0, image: "기타.jpg", name: "기타"})
        this.crops.forEach((c, index) => {
          this.cropImg[index] = this.crops[index].image
            ? require("@/assets/crop/" + this.crops[index].image)
            : require("@/assets/thumbnail.png");
        });
      });

    axios.get(`user/crop/list?userNumber=${this.userNumber}`)
    .then((res) => {
      this.usercrops = res.data;
      console.log(this.usercrops);
    })
    .catch((err) => {
      console.error(err);
    });
  },
  methods: {
    findImg(target) {
      return require("@/assets/ahcievement/" + target + ".png")
    },

    findCropImg(ucrop) {
      let index = 0;
      this.crops.forEach((crop, i) => {
        if(crop.cropNumber==ucrop.cropNumber) {
          index = i;
          console.log(crop);
        }
      });
      return (this.crops[index].image)? require("@/assets/crop/" + this.crops[index].image):require("@/assets/thumbnail.png");
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
  width: 100px;
}

#row .items:first-child {
  margin-left: 0;
}

#row .items p {
  margin-bottom: 8px;
  text-indent: 7px;
}

#row .items ul {
  border-radius: 3px;
  border: 1px solid #b5b5b5;
  height: 135px;
  overflow-y: scroll;
  padding: 3px 3px 8px;
  background: #fff;
}

#row .items ul li a {
  display: block;
  overflow: hidden;
  margin-top: 8px;
  padding: 3px;
  color: black;
  font-size: 12px;
  text-decoration: none;
}

#row .items ul li:first-child a {
  margin-top: 3px;
}

#row .items ul li.on a {
  border: 1px solid #c9cccf;
  border-radius: 3px;
  font-weight: bold;
  background-color: #efefef;
}

.all-font {
  font-family: Noto Sans KR
}

.mission-font {
  font-size: small;
}

.box-border {
  border: 1px solid #B6C790;
}

.profile-line {
  color: #B6C790;
}

.logo{
  text-decoration: none;
  color: black;
}
</style>