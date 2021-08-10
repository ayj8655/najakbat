<template>
  <div class="container">
    <div class="mycontainer py-4">
      <h2 class="mb-5">회원가입 완료</h2>
      <form action="">
        <div class="mb-3 container w-75 text-center">
            <div class="my-5">
                <div><strong>{{ this.nickname }}</strong>님,</div>
                <p>회원가입을 축하드립니다.</p>
                </div>
            </div>
            <div class="my-5">
                <div>지역을 선택하고,</div>
                <div class="mb-3">날씨에 따라 알림을 받아보세요!</div>
                <!-- Button trigger modal -->
                <button type="button" class="btn btn-success" data-bs-toggle="modal" data-bs-target="#exampleModal">
                지역 설정하기
                </button>

                <!-- Modal -->
                <div class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
                    <div class="modal-dialog">
                        <div class="modal-content">
                            <div class="modal-header">
                                <h5 class="modal-title" id="exampleModalLabel">지역 설정</h5>
                                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                            </div>
                            <div class="modal-body">
                                <PutAddress @getAddress="getAddress" />
                            </div>
                            <div class="modal-footer">
                                <button type="button" class="btn btn-success" data-bs-dismiss="modal" :disabled="!this.address">저장</button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="container mb-5">
                <div v-if="this.userInformation.region === '세종특별자치시'">
                    지역이 <b>{{ this.userInformation.region }}</b>로 설정되었습니다.
                </div>
                <div v-else-if="this.userInformation.region && this.userInformation.city">
                    지역이 <b>{{ this.userInformation.region }}</b> / <b>{{ this.userInformation.city }}</b>(으)로 설정되었습니다.
                </div>
            </div>
        <button class="btn btn-success" @click.prevent="saveAddress" :disabled="!this.address">서비스 시작</button>
        <p class="my-3"><router-link to="/">나중에 설정하기</router-link></p>
      </form>
    </div>

  </div>
</template>

<script>
import axios from "axios"
import PutAddress from "@/components/Login/PutAddress.vue"

export default {
    name: "SignupNext",
    components: {
        PutAddress
    },
    data() {
        return {
            userInformation: {
                region: null,
                city: null,
            },
            address: null,
            nickname: null,
        }
    },
    methods: {
        selectRegion(event) {
            this.userInformation.region = event.target.value
            this.userInformation.city = null
            this.address = null
        },
        selectCity(event) {
            this.userInformation.city = event.target.value
        },
        getAddress(event) {
            this.userInformation.region = event.region
            this.userInformation.city = event.city
            this.address = event.address
        },
        saveAddress() {
            this.$store.dispatch('updateAddress', this.address)
        }
    },
    created() {
        axios.get('user/my')
        .then(res => {
            this.nickname = res.data.nickname
        })
        .catch(err => {
            console.error(err);
        })
    }
}
</script>

<style scoped>
.mycontainer {
  background: none;
  /* width: 500px; */
  /* margin: 8rem auto 0; */
  /* text-align: center; */
  box-shadow: 0 1rem 1rem 0 rgba(0, 0, 0, .15);
  position: relative;
}
</style>