<template>
  <div>
      <button type="button" class="btn btn-primary" :disabled="counting" @click="startCountdown">
        <countdown v-if="counting" :left-time="5000">
            <span
            slot="process"
            slot-scope="{ timeObj }">
                {{ timeObj.ceil.s }}
            </span>
            <span slot="finish">Done!</span>
        </countdown>
        <span v-else>Fetch Verification Code</span>
      </button>
  </div>
</template>

<script>
export default {
    name: 'PhoneCertified',
    data() {
        return {
            counting: false,
        };
    },
    methods: {
        startCountdown: function () {
            this.counting = true;
        },
        onCountdownEnd: function () {
            this.counting = false;
        },
    },

}
</script>

<style>

</style>