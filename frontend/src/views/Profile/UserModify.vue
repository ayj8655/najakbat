<template>
  <div>
    <header-nav></header-nav>
    <div class="modify-font container w-75">
      <div class="mt-3 mb-5">
        <h1>회원정보 수정</h1>
        <div>
          <p class="text-danger fs-6">저장을 눌러야 정보가 반영됩니다</p>
        </div>
      </div>
      <div class="d-flex justify-content-between my-2">
        닉네임 : {{ this.nickname }}
        <button
          button
          type="button"
          class="btn btn-outline-danger btn-sm"
          @click="nicknameModify"
          v-if="nicknameCheck"
        >
          취소
        </button>
        <button
          button
          type="button"
          class="btn btn-outline-dark btn-sm"
          @click="nicknameModify"
          v-else
        >
          수정
        </button>
      </div>
      <div class="mb-2" v-if="nicknameCheck">
        <input
          type="text"
          class="form-control mb-2"
          name="between_field"
          placeholder="12자 이내로 입력하세요"
          v-model="modify.nickname"
        />
        <div class="d-flex justify-content-start">
          <span
            class="text-danger me-auto"
            v-if="modify.nickname && modify.nickname.length > 12"
            >닉네임이 너무 길어요</span
          >
          <span class="text-danger me-auto" v-else-if="overlap.nickname"
            >이미 사용중인 닉네임입니다</span
          >
          <span class="me-auto" v-else-if="check.nickname"
            >사용 가능한 닉네임입니다</span
          >
          <button
            button
            type="button"
            class="btn btn-outline-success btn-sm ms-auto"
            :disabled="!modify.nickname || modify.nickname.length > 12"
            @click="checkNickname(modify.nickname)"
            v-if="!check.nickname"
          >
            중복확인
          </button>
          <button
            button
            type="button"
            class="btn btn-outline-success btn-sm ms-auto"
            @click.prevent="modifyNick"
            v-else
          >
            수정하기
          </button>
        </div>
      </div>
      <hr class="profile-line container my-3" />
      <div class="d-flex justify-content-between mb-2">
        비밀번호 변경하기
        <button 
          type="button"
          class="btn btn-outline-dark btn-sm"
           @click="passwordodify"
          v-if="!passwordCheck"
          >
          수정
        </button>
        <button
          type="button"
          class="btn btn-outline-danger btn-sm"
          @click="passwordodify"
          v-else
        >
          취소
        </button>
      </div>
      <div v-if="passwordCheck">
        <input type="password" class="form-control mb-3" v-validate="'min:8'" name="min_field" placeholder="최소 8글자 이상 입력하세요" v-model="password">
        <span class="text-danger d-flex align-items-start mb-3 fs-6" v-if="errors.first('min_field')">비밀번호가 너무 짧습니다</span>
        <input type="password" class="form-control  mb-3" placeholder="비밀번호를 한번 더 입력하세요" v-model="passwordConfirmation">
        <span class="text-danger d-flex align-items-start fs-6" v-if="password != passwordConfirmation">비밀번호가 서로 다릅니다</span>
        <div class="d-flex justify-content-end">
          <button class="btn btn-success btn-sm" :disabled="!passwordSameCheck" @click.prevent="chagePassword">수정</button>
        </div>
      </div>
      <hr class="profile-line container my-3" />
      <div class="d-flex justify-content-between mb-2">
        휴대폰 : {{ this.phone }}
        <button
          type="button"
          class="btn btn-outline-dark btn-sm"
          @click="phoneModify"
          v-if="!phoneCheck"
        >
          수정
        </button>
        <button
          type="button"
          class="btn btn-outline-danger btn-sm"
          @click="phoneModify"
          v-else
        >
          취소
        </button>
      </div>
      <div v-if="phoneCheck">
        <input
          type="text"
          class="form-control"
          v-validate="'digits:11'"
          name="digits_field"
          placeholder="휴대폰 번호를 입력하세요"
          v-model="modify.phone"
          v-if="!check.phone"
        />
        <PhoneCertified
          :phoneNum="modify.phone"
          @phonecertified="phonecertified"
          v-if="!check.phone"
        />
      </div>
      <hr class="profile-line container my-3" />
      <div class="d-flex justify-content-between">
        <div v-if="city">지역 : {{ this.region }} / {{ this.city }}</div>
        <div v-else>지역 : {{ this.region }}</div>
        <button
          type="button"
          class="btn btn-outline-dark btn-sm"
          @click="addressModify"
          data-bs-toggle="modal"
          data-bs-target="#exampleModal"
        >
          수정
        </button>
      </div>
      <div class="my-5 d-flex justify-content-end">
        <button
          type="button"
          class="btn btn-danger btn-sm me-3"
          @click="backToProfile"
        >
          취소
        </button>
        <button
          type="button"
          class="btn btn-success btn-sm"
          @click="[updateModity([nickname, phone, address]), putPassword()]"
        >
          저장
        </button>
      </div>
    </div>

    <div
      class="modal fade"
      id="exampleModal"
      tabindex="-1"
      aria-labelledby="exampleModalLabel"
      aria-hidden="true"
    >
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title" id="exampleModalLabel">지역 설정</h5>
            <button
              type="button"
              class="btn-close"
              data-bs-dismiss="modal"
              aria-label="Close"
            ></button>
          </div>
          <div class="modal-body">
            <PutAddress @getAddress="putAddress" />
          </div>
          <div class="modal-footer">
            <button
              type="button"
              class="btn btn-success"
              data-bs-dismiss="modal"
              :disabled="
                !this.temporaryAddress || !this.temporaryAddress.address
              "
              @click.prevent="saveAddress"
            >
              저장
            </button>
          </div>
        </div>
      </div>
    </div>
    <div id="foot"></div>
    <menubar id="menubar"></menubar>
  </div>
</template>

<script>
// import axios from "axios";
import HeaderNav from "@/components/Menu/HeaderNav.vue";
import Menubar from "@/components/Menu/Menubar.vue";
import PhoneCertified from "@/components/Login/PhoneCertified.vue";
import PutAddress from "@/components/Login/PutAddress.vue";
import axios from "axios";
import { mapActions } from "vuex";

export default {
  components: {
    Menubar,
    HeaderNav,
    PhoneCertified,
    PutAddress,
  },
  data() {
    return {
      nickname: null,
      phone: null,
      region: null,
      city: null,
      address: null,
      temporaryAddress: null,
      userNum: null,
      password: null,
      passwordConfirmation: null,
      userId: null,

      nicknameCheck: false,
      phoneCheck: false,
      addressCheck: false,
      passwordCheck: false,
      passwordSameCheck: false,
      passwordRealChange: false,

      modify: {
        nickname: null,
        phone: null,
        address: null,
      },

      overlap: {
        nickname: false,
        phone: false,
      },

      check: {
        nickname: false,
        phone: false,
      },
    };
  },
  methods: {
    ...mapActions(["backToProfile"]),

    updateModity(credentials) {
      axios.get('user/my')
      .then(res => {
        var addressPut = { ...res.data, nickname: credentials[0], phone: credentials[1], address: credentials[2]}
        axios.put('user/', addressPut)
        .then(res => {
          res
          this.$fire({
            title: "성공!",
            text: "저장이 완료되었습니다",
            type: "success",
          })
        })
        .catch(err => {
          console.error(err);
        })
      })
      .catch(err => {
        console.error(err);
      })
    },

    nicknameModify() {
      this.nicknameCheck = !this.nicknameCheck;
    },

    phoneModify() {
      this.phoneCheck = !this.phoneCheck;
      this.check.phone = false;
    },

    addressModify() {
      this.addressCheck = !this.addressCheck;
    },

    passwordodify() {
      this.passwordCheck = !this.passwordCheck
      this.password = null
      this.passwordConfirmation = null
    },

    chagePassword() {
      this.passwordCheck = !this.passwordCheck
      this.passwordRealChange = true
    },

    // 닉네임
    modifyNick() {
      this.nickname = this.modify.nickname;
      this.modify.nickname = null;
      this.nicknameCheck = false;
      this.check.nickname = false;
    },

    checkNickname(nick) {
      axios
        .get(`user/pass/confirmNickname/${nick}`)
        .then((res) => {
          if (res.data === "fail") {
            this.overlap.nickname = true;
            return;
          }
          this.overlap.nickname = false;
          this.check.nickname = true;
        })
        .catch((err) => {
          console.error(err);
        });
    },

    // 휴대폰
    phonecertified() {
      this.check.phone = true;
      this.phone = this.modify.phone;
      this.phoneCheck = false;
    },

    // 지역 설정
    putAddress(res) {
      this.temporaryAddress = res;
    },

    saveAddress() {
      this.address = this.temporaryAddress.address;
      this.region = this.temporaryAddress.region;
      this.city = this.temporaryAddress.city;
    },

    // 비밀번호 수정
    putPassword() {
      if (this.passwordRealChange) {
        if (this.password) {
          axios.post('user/pass/pwFind', {
            id: this.userId,
            password: this.password,
            phone: this.phone
          })
          .then(res => {
            res
          })
          .err(err => {
            console.error(err);
          })
        }
      }
      console.log(this.passwordRealChange, this.password);
    }
  },
  watch: {
    passwordConfirmation() {
      if (this.password && !this.errors.first('min_field')) {
        if (this.password === this.passwordConfirmation) {
          this.passwordSameCheck = true
          return
        }
      }
      this.passwordSameCheck = false
    }
  },
  created() {
    axios
      .get("user/my")
      .then((res) => {
        this.userNum = res.data.userNumber;
        this.nickname = res.data.nickname;
        this.phone = res.data.phone;
        this.address = res.data.address;
        var getAddress = res.data.address.split(",");
        this.region = getAddress[0];
        this.city = getAddress[1];
        this.userId = res.data.id
      })
      .catch((err) => {
        console.error(err);
      });
  },
};
</script>

<style scoped>
#foot {
  height: 150px;
}
#menubar {
  position: fixed;
  background-color: #b6c790;
  bottom: 0;
}

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

.modify-font {
  font-family: Noto Sans KR;
  font-style: normal;
  font-weight: bold;
  font-size: 18px;
  line-height: 26px;
  text-align: center;
}

.profile-line {
  border: 1px solid #b6c790;
}
</style>
