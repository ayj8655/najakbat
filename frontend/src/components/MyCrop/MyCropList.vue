<template>
  <div class="container mt-5">
    <div class="container mt-5" v-if="!isLogin">
      <img src="@/assets/자물쇠.png" alt="" class="w-50">
      <h3 class="mt-3">회원 전용 페이지입니다</h3>
      <div class="d-grid gap-2 mt-5">
        <button class="btn btn-success" type="button" @click="pushLogin">로그인 하기</button>
        <button class="btn btn-success" type="button" @click="pushSignup">회원가입 하기</button>
      </div>
    </div>
    <div v-else>
      <div style="text-align: center">
        <h3 class="m-5 crop">내 농작물</h3>
      </div>
      <div class="d-flex justify-content-end">
        <font-awesome-icon
          :icon="['fas', 'plus']"
          size="lg"
          class="pen-color"
          data-bs-toggle="modal"
          data-bs-target="#exampleModal"
        />
      </div>
      <!-- <span class="etc">
          <v-btn elevation="2" right class="btn btn-success" data-bs-toggle="modal" data-bs-target="#exampleModal">
            +
          </v-btn>
        </span> -->

      <div class="mt-2" id="crops-area">
        <hr />
        <div v-if="usercrops.length == 0">
          <h3 class="text-center">작물을 등록해주세요 :)</h3>
        </div>
        <div v-else>
          <div
            v-for="(ucrop, index) in usercrops"
            :key="index"
          >
            <div class="row">
              <div class="col-4" @click="movePage(ucrop.userCropNumber)">
                <img id="thumbnail" :src="findCropImg(ucrop)" />
              </div>
              <div class="col-8">
                <strong @click="movePage(ucrop.userCropNumber)">{{ ucrop.cropNickname }}</strong>
                <p class="mb-1" @click="movePage(ucrop.userCropNumber)">수확까지 <strong>{{ ucrop.remainDate }}일</strong> 남았습니다</p>
                <div v-if="ucrop.water">
                  <font-awesome-icon :icon="['fas', 'tint']" size="lg" class="water-color" />
                  <span v-if="ucrop.waterDate != 0" class="ms-3">D-{{ ucrop.waterDate }}</span>
                  <span v-else class="ms-3">D-{{ ucrop.waterDate }}</span>
                </div>
                <div v-else>
                  <font-awesome-icon :icon="['fas', 'tint']" size="lg" class="no-water-color" @click.prevent="commitWater(ucrop.userCropNumber)"/>
                  <span class="ms-3">👈물을 주세요</span>
                </div>
              </div>
            </div>
            <hr />
          </div>
        </div>
      </div>

      <!-- Modal -->
      <div
        class="modal fade"
        id="exampleModal"
        tabindex="-1"
        aria-labelledby="exampleModalLabel"
        aria-hidden="true"
      >
        <div class="modal-dialog modal-dialog-centered">
          <div class="modal-content">
            <div class="modal-header">
              <h5 class="modal-title" id="exampleModalLabel">작물 선택</h5>
              <button
                type="button"
                class="btn-close"
                data-bs-dismiss="modal"
                aria-label="Close"
              ></button>
            </div>
            <div class="modal-body">
              <div>
                <select
                  v-model="cropNoSelected"
                  required
                  class="form-select form-select-lg mx-0 my-2 p-2 text-center"
                  aria-label=".form-select-lg example"
                >
                  <option value="">작물 선택</option>
                  <option
                    v-for="(crop, index) in crops"
                    :key="index"
                    :value="crop.cropNumber"
                  >
                    {{ crop.name }}
                  </option>
                </select>
              </div>
              <div>
                <hr>
                <div v-if="cropNoSelected === 0">
                  <div class="d-flex justify-content-start mt-2">
                    <h5 class="mb-2">나만의 작물을 만들어보세요😊</h5>
                  </div>
                  <p class="text-danger d-flex justify-content-start">(필수 사항)</p>
                  <div class="form-floating">
                    <textarea class="form-control my-3" placeholder="Leave a comment here" id="floatingTextarea" v-model="etc.cropNick"></textarea>
                    <label for="floatingInputValue">작물 이름을 등록해주세요!</label>
                  </div>
                  <div class="d-flex justify-content-between">
                    <div class="form-floating">
                      <textarea class="form-control mb-3" placeholder="Leave a comment here" id="floatingTextarea" v-model="etc.waterDate"></textarea>
                      <label for="floatingInputValue">물 주기(day)</label>
                    </div>
                    <div class="form-floating">
                      <textarea class="form-control mb-3" placeholder="Leave a comment here" id="floatingTextarea" v-model="etc.remainDate"></textarea>
                      <label for="floatingInputValue">남은 수확일(day)</label>
                    </div>
                  </div>
                  <p class="text-success d-flex justify-content-start">(선택 사항)</p>
                  <div class="form-floating">
                    <textarea class="form-control" placeholder="Leave a comment here" id="floatingTextarea" v-model="etc.cropDesc"></textarea>
                    <label for="floatingInputValue">작물 소개를 작성해주세요!</label>
                  </div>
                </div>
                <div v-else>
                  <div class="d-flex justify-content-start mt-2">
                    <h5 class="mb-0">나만의 작물 소개를 적어보세요😊</h5>
                    <p class="text-success ms-auto">(선택 사항)</p>
                  </div>
                  <div class="form-floating">
                    <textarea class="form-control my-3" placeholder="Leave a comment here" id="floatingTextarea" v-model="cropNick"></textarea>
                    <label for="floatingInputValue">작물 이름을 지어주세요!</label>
                  </div>
                  <div class="form-floating">
                    <textarea class="form-control" placeholder="Leave a comment here" id="floatingTextarea" v-model="cropDesc"></textarea>
                    <label for="floatingInputValue">작물 소개를 작성해주세요!</label>
                  </div>
                </div>
              </div>
            </div>
            <div class="modal-footer">
              <button
                type="button"
                class="btn btn-success"
                data-bs-dismiss="modal"
                :disabled="!this.etc.cropNick || isNaN(this.etc.waterDate) || isNaN(this.etc.remainDate) || !this.etc.waterDate || !this.etc.remainDate"
                @click="saveMyCrop"
                v-if="cropNoSelected === 0"
              >
                등록
              </button>
              <button
                type="button"
                class="btn btn-success"
                data-bs-dismiss="modal"
                :disabled="!this.cropNoSelected"
                @click="saveCrop"
                v-else
              >
                등록
              </button>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";
import router from "@/router"

export default {
  data() {
    return {
      crops: [],
      cropNoSelected: "",
      usercrops: [],
      cropImg: [],
      usercropImg: [],
      userSelectCrop: "",
      cropDesc: null,
      cropNick: null,
      isLogin: this.$store.state.accessToken,
      pickCrop: {
        userNumber: null,
      },
      etc: {
        cropNick: null,
        cropDesc: null,
        waterDate: null,
        remainDate: null
      }
    };
  },
  created() {
    if (this.$store.state.accessToken) {
      axios
        .get("user/my")
        .then((res) => {
          this.pickCrop.userNumber = res.data.userNumber;
          axios
            .get(`user/crop/list?userNumber=${this.pickCrop.userNumber}`)
            .then((res) => {
              console.log(res.data);
              this.usercrops = res.data;
            })
            .catch((err) => {
              console.error(err);
            });
        })
        .catch((err) => {
          console.error(err);
        });
  
      axios.get("guide/plant/summary").then((data) => {
        this.crops = data.data;
        this.crops.push({cropNumber: 0, image: "기타.jpg", name: "기타"})
        // console.log(this.crops);
        this.crops.forEach((c, index) => {
          this.cropImg[index] = this.crops[index].image
            ? require("@/assets/crop/" + this.crops[index].image)
            : require("@/assets/thumbnail.png");
        });
      });
    }
  },
  methods: {
    pushLogin() {
      router.push({name: 'Login'})
    },
    pushSignup() {
      router.push({name: 'Signup'})
    },
    movePage(cropNumber) {
      this.$router.push("/mycrop/detail/" + cropNumber);
    },
    findCropImg(ucrop) {
      let index = 0;
      this.crops.forEach((crop, i) => {
        if(crop.cropNumber==ucrop.cropNumber) {
          index = i;
        }
      });
      return (this.crops[index].image)? require("@/assets/crop/" + this.crops[index].image):require("@/assets/thumbnail.png");
    },
    // selectCrop(event) {
    //   this.userSelectCrop = event;
    // },

    saveMyCrop() {
      if(!this.etc.cropDesc) {
        this.etc.cropDesc = '작물소개를 작성해보세요!'
      }
      axios({
        method: 'post',
        url: 'user/crop/',
        params: {
          cropdesc: this.etc.cropDesc,
          cropnickname: this.etc.cropNick,
          cropno: this.cropNoSelected,
          userno: this.pickCrop.userNumber,
          growingPeriodData: Number(this.etc.remainDate),
          waterPeriodData : Number(this.etc.waterDate)
        }
      })
      .then((res) => {
        if (res.data == "success") window.location.reload();
        this.etc.cropNick = null
        this.etc.cropDesc = null
        this.etc.remainDate = null
        this.etc.waterDate = null
      })
      .catch((err) => {
        console.error(err);
      });
    },

    saveCrop() {
      if (!this.cropNick) {
        for (var i = 0; i < this.crops.length; i++) {
          if (this.cropNoSelected == this.crops[i].cropNumber) {
            this.cropNick = this.crops[i].name
          }
        }
      }

      if (!this.cropDesc) {
        this.cropDesc = '작물소개를 작성해보세요!'
      }

      axios({
        method: 'post',
        url: 'user/crop/',
        params: {
          cropdesc: this.cropDesc,
          cropnickname: this.cropNick,
          cropno: this.cropNoSelected,
          userno: this.pickCrop.userNumber
        }
      })
      .then((res) => {
        if (res.data == "success") window.location.reload();
      })
      .catch((err) => {
        console.error(err);
      });

      this.cropNick = null
      this.cropDesc = null
    },

    commitWater(event) {
      axios.post(`user/crop/water?userCropNumber=${ event }`)
      .then(res =>{
        if (res.data === 'success')  {
          for(var i = 0; i < this.usercrops.length; i++) {
            if (this.usercrops[i].userCropNumber == event) {
              this.usercrops[i].water = true
            }
          }
        }
      })
      .catch(err => {
        console.error(err);
      })
    }
  },
};
</script>

<style scoped>
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
  width: 60px;
  height: 60px;
  border-radius: 5px;
}
.crop {
  display: inline;
}
.etc {
  float: right;
  display: inline-block;
}

.pen-color {
  color: #446631;
}

.water-color {
  color: #0BC3FD;
}

.no-water-color {
  color: #999999;
}
</style>
