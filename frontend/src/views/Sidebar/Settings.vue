<template>
<div>
    <div class="container">
    <div class="d-flex flex-column bd-highlight mb-3">
    <div class="p-2 bd-highlight">
     설정
    </div>
    <div class="p-2 d-flex bd-highlight">
    <!-- <toggle-button @change="onChangeEventHandler"/> -->
    <h2>물 주기 알림</h2>
    <toggle-button :value="true"
                  :labels="{checked: 'On', unchecked: 'Off'}"/>

    </div>
    <div class="p-2 d-flex bd-highlight">
    <h2>추천 정보 알림</h2>
    <!-- <toggle-button v-model="myDataVariable"/> -->
    <toggle-button :value="true"
                  :labels="{checked: 'On', unchecked: 'Off'}"/>
      
    </div>
    <div class="p-2 d-flex bd-highlight">
    <h3>커뮤니티 새 댓글 알림</h3>
    <toggle-button :value="true"
                  :labels="{checked: 'On', unchecked: 'Off'}"/>

    </div>
    <div class="p-2 d-flex bd-highlight">
    <h2>새 쪽지 알림</h2>
    <toggle-button :value="true"
                  :labels="{checked: 'On', unchecked: 'Off'}"/>
      
    </div>
    <div class="p-2 d-flex bd-highlight">
    <h2>나이트모드</h2>
    <toggle-button :value="true"
                  :labels="{checked: 'On', unchecked: 'Off'}"/>
      
    </div>
  </div>  



    </div>
</div>
</template>

<script>

export default {

}
</script>

<style scoped>
.container {
  position: relative;
  width: 375px;
  height: 812px;

  background: #FFFFFF;
}
</style>