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
              <span class="me-auto text-danger" v-if="!missCertifiedNumber">인증번호가 다릅니다</span>
              <button class="btn btn-success" @click.prevent="checkCertifiedInput">인증하기</button>
          </div>
      </div>
  </div>
</template>

<script>
import axios from 'axios'

export default {
    name: 'PhoneCertified',
    props: ['phoneNum'],
    data() {
        return {
            counting: false,
            numberConfirmation: false,
            certifiedNumber: '123123',
            certifiedInput: null,
            certifiedPhone: false,
            missCertifiedNumber: true
        };
    },
    methods: {
        startCountdown() {
            let phone = this.$props.phoneNum
            
            axios.get(`http://localhost:8080/user/pass/confirmPhone/${phone}`)
            .then(res => {
                if (res.data === 'fail') {
                    alert('이미 가입된 번호입니다')
                }
                else {
                    this.counting = true;
                    this.numberConfirmation = true
                    // axios.post('http://localhost:8080/user/pass/phone', {
                    //     phone: this.$props.phoneNum
                    // })
                    // .then(res => {
                    //     this.certifiedNumber = res.data
                    // })
                    // .catch(err => {
                    //     console.error(err);
                    // })
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
            if(this.certifiedNumber === this.certifiedInput) {
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