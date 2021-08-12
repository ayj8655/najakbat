<template>
  <div class="my-3">
    <div class="d-flex justify-content-end">
        <span v-if="!counting && numberConfirmation" class="me-auto">시간이 초과되었습니다</span>
        <button type="button" class="btn btn-success" :disabled="counting" @click="startCountdown" v-if="!certifiedPhone">
            <countdown v-if="counting" :left-time="180000" @finish="(countdown) => finish(countdown)">
                <span
                slot="process"
                slot-scope="{ timeObj }">
                    {{ timeObj.ceil.s }}초 남았습니다
                </span>
            </countdown>
            <span v-else-if="!counting && !numberConfirmation">인증번호 받기</span>
            <span v-else-if="!counting && numberConfirmation">인증번호 다시받기</span>
        </button>
        <button v-else class="btn btn-success" :disabled="certifiedPhone">인증되었습니다</button>
    </div>
      <div v-if="counting && !certifiedPhone" class="form-floating mt-3">
          <input type="text" class="form-control" id="floatingInput" placeholder="Password" v-model="certifiedInput">
          <label for="floatingInput">인증번호 6자리를 입력하세요</label>
          <div class="mt-3 d-flex justify-content-end">
              <span class="me-auto" v-if="!missCertifiedNumber">인증번호가 다릅니다</span>
              <button class="btn btn-success" @click.prevent="checkCertifiedInput">인증하기</button>
          </div>
      </div>
  </div>
</template>

<script>
import axios from 'axios'

export default {
    name: 'PhoneCertified',
    props: ['phoneNum', 'userName'],
    data() {
        return {
            counting: false,
            numberConfirmation: false,
            certifiedNumber: null,
            certifiedInput: null,
            certifiedPhone: false,
            missCertifiedNumber: true
        };
    },
    methods: {
        startCountdown() {
            let phone = this.$props.phoneNum
            let userName = this.$props.userName

            axios.post('user/pass/phone', {
                phone: phone,
                userName: userName
            })
            .then(res => {
                if (!res.data) {
                this.$fire({
                    text: "정보가 일치하지 않습니다",
                    type: "error",
                })
                }
                else {
                    this.counting = true;
                    this.numberConfirmation = true
                    this.certifiedNumber = res.data
                    console.log(this.certifiedNumber);
                }
            })
            .catch(err => {
                console.error(err);
            })
        },
        finish(countdown) {
            countdown.attrs.disabled = false
            this.counting = false
        },
        checkCertifiedInput() {
            if(this.certifiedNumber == this.certifiedInput) {
                this.certifiedPhone = true
                this.$emit('phonecertified', true)
            }
            else {
                this.missCertifiedNumber = false
            }
        }
    },

}
</script>

<style>

</style>