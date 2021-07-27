<template>
  <div class="container">
    <div class="mycontainer py-4">
      <h2 class="mb-5">회원가입 완료</h2>
      <form action="">
        <div class="mb-3 container w-75 text-center">
            <div class="my-5">
                <div><strong>닉네임</strong>님,</div>
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
                            <div class="modal-body d-flex justify-content-around">
                                <select class="form-select form-select-lg m-2" aria-label=".form-select-lg example" @change="selectRegion($event)">
                                    <option value="" selected>시/도</option>
                                    <option value="서울">서울</option>
                                    <option value="인천">인천</option>
                                    <option value="경기">경기</option>
                                    <option value="강원">강원</option>
                                    <option value="충북">충북</option>
                                    <option value="충남">충남</option>
                                    <option value="대전">대전</option>
                                    <option value="세종">세종</option>
                                    <option value="경북">경북</option>
                                    <option value="경남">경남</option>
                                    <option value="대구">대구</option>
                                    <option value="울산">울산</option>
                                    <option value="부산">부산</option>
                                    <option value="전북">전북</option>
                                    <option value="전남">전남</option>
                                    <option value="광주">광주</option>
                                    <option value="제주">제주</option>
                                </select>
                                <select class="form-select form-select-lg m-2" aria-label=".form-select-lg example" v-if="this.region === '서울'" @change="selectCity($event)">
                                    <option value="" selected>시/군/구</option>
                                    <option value="강남구">강남구</option>
                                    <option value="강동구">강동구</option>
                                    <option value="강북구">강북구</option>
                                    <option value="강서구">강서구</option>
                                    <option value="관악구">관악구</option>
                                    <option value="광진구">광진구</option>
                                    <option value="구로구">구로구</option>
                                    <option value="금천구">금천구</option>
                                    <option value="노원구">노원구</option>
                                    <option value="도봉구">도봉구</option>
                                    <option value="동대문구">동대문구</option>
                                    <option value="동작구">동작구</option>
                                    <option value="마포구">마포구</option>
                                    <option value="서대문구">서대문구</option>
                                    <option value="서초구">서초구</option>
                                    <option value="성동구">성동구</option>
                                    <option value="성북구">성북구</option>
                                    <option value="송파구">송파구</option>
                                    <option value="양천구">양천구</option>
                                    <option value="영등포구">영등포구</option>
                                    <option value="용산구">용산구</option>
                                    <option value="은평구">은평구</option>
                                    <option value="종로구">종로구</option>
                                    <option value="중구">중구</option>
                                    <option value="중랑구">중랑구</option>
                                </select>
                                <select class="form-select form-select-lg m-2" aria-label=".form-select-lg example" v-else-if="this.region === '인천'" @change="selectCity($event)">
                                    <option value="" selected>시/군/구</option>
                                    <option value="강화군">강화군</option>
                                    <option value="계양구">계양구</option>
                                    <option value="남동구">남동구</option>
                                    <option value="동구">동구</option>
                                    <option value="미추홀구">미추홀구</option>
                                    <option value="부평구">부평구</option>
                                    <option value="서구">서구</option>
                                    <option value="연수구">연수구</option>
                                    <option value="옹진군">옹진군</option>
                                    <option value="중구">중구</option>
                                </select>
                                <select class="form-select form-select-lg m-2" aria-label=".form-select-lg example" v-else-if="this.region === '경기'" @change="selectCity($event)">
                                    <option value="" selected>시/군/구</option>
                                    <option value="가평">가평</option>
                                    <option value="고양">고양</option>
                                    <option value="과천">과천</option>
                                    <option value="광명">광명</option>
                                    <option value="광주">광주</option>
                                    <option value="구리">구리</option>
                                    <option value="군포">군포</option>
                                    <option value="김포">김포</option>
                                    <option value="남양주">남양주</option>
                                    <option value="동두천">동두천</option>
                                    <option value="부천">부천</option>
                                    <option value="성남">성남</option>
                                    <option value="수원">수원</option>
                                    <option value="시흥">시흥</option>
                                    <option value="안산">안산</option>
                                    <option value="안성">안성</option>
                                    <option value="안양">안양</option>
                                    <option value="양주">양주</option>
                                    <option value="양평">양평</option>
                                    <option value="여주">여주</option>
                                    <option value="연천">연천</option>
                                    <option value="오산">오산</option>
                                    <option value="용인">용인</option>
                                    <option value="의왕">의왕</option>
                                    <option value="의정부">의정부</option>
                                    <option value="이천">이천</option>
                                    <option value="파주">파주</option>
                                    <option value="평택">평택</option>
                                    <option value="포천">포천</option>
                                    <option value="하남">하남</option>
                                    <option value="화성">화성</option>
                                </select>
                                <select class="form-select form-select-lg m-2" aria-label=".form-select-lg example" v-else-if="this.region === '강원'" @change="selectCity($event)">
                                    <option value="" selected>시/군/구</option>
                                    <option value="강릉">강릉</option>
                                    <option value="고성">고성</option>
                                    <option value="동해">동해</option>
                                    <option value="삼척">삼척</option>
                                    <option value="속초">속초</option>
                                    <option value="양구">양구</option>
                                    <option value="양양">양양</option>
                                    <option value="원주">원주</option>
                                    <option value="인제">인제</option>
                                    <option value="정선">정선</option>
                                    <option value="철원">철원</option>
                                    <option value="춘천">춘천</option>
                                    <option value="태백">태백</option>
                                    <option value="평창">평창</option>
                                    <option value="홍천">홍천</option>
                                    <option value="화천">화천</option>
                                    <option value="횡성">횡성</option>
                                </select>
                                <select class="form-select form-select-lg m-2" aria-label=".form-select-lg example" v-else-if="this.region === '충북'" @change="selectCity($event)">
                                    <option value="" selected>시/군/구</option>
                                    <option value="괴산">괴산</option>
                                    <option value="단양">단양</option>
                                    <option value="보은">보은</option>
                                    <option value="영동">영동</option>
                                    <option value="옥천">옥천</option>
                                    <option value="음성">음성</option>
                                    <option value="제천">제천</option>
                                    <option value="증평">증평</option>
                                    <option value="진천">진천</option>
                                    <option value="청주">청주</option>
                                    <option value="충주">충주</option>
                                </select>
                                <select class="form-select form-select-lg m-2" aria-label=".form-select-lg example" v-else-if="this.region === '충남'" @change="selectCity($event)">
                                    <option value="" selected>시/군/구</option>
                                    <option value="계룡">계룡</option>
                                    <option value="공주">공주</option>
                                    <option value="금산">금산</option>
                                    <option value="논산">논산</option>
                                    <option value="당진">당진</option>
                                    <option value="보령">보령</option>
                                    <option value="부여">부여</option>
                                    <option value="서산">서산</option>
                                    <option value="서천">서천</option>
                                    <option value="아산">아산</option>
                                    <option value="예산">예산</option>
                                    <option value="천안">천안</option>
                                    <option value="청양">청양</option>
                                    <option value="태안">태안</option>
                                    <option value="홍성">홍성</option>
                                </select>
                                <select class="form-select form-select-lg m-2" aria-label=".form-select-lg example" v-else-if="this.region === '대전'" @change="selectCity($event)">
                                    <option value="" selected>시/군/구</option>
                                    <option value="대덕구">대덕구</option>
                                    <option value="동구">동구</option>
                                    <option value="서구">서구</option>
                                    <option value="유성구">유성구</option>
                                    <option value="중구">중구</option>
                                </select>
                                <select class="form-select form-select-lg m-2" aria-label=".form-select-lg example" v-else-if="this.region === '경북'" @change="selectCity($event)">
                                    <option value="" selected>시/군/구</option>
                                    <option value="경산">경산</option>
                                    <option value="경주">경주</option>
                                    <option value="고령">고령</option>
                                    <option value="구미">구미</option>
                                    <option value="군위">군위</option>
                                    <option value="김천">김천</option>
                                    <option value="문경">문경</option>
                                    <option value="봉화">봉화</option>
                                    <option value="상주">상주</option>
                                    <option value="성주">성주</option>
                                    <option value="안동">안동</option>
                                    <option value="영덕">영덕</option>
                                    <option value="영양">영양</option>
                                    <option value="영주">영주</option>
                                    <option value="영천">영천</option>
                                    <option value="예천">예천</option>
                                    <option value="울릉">울릉</option>
                                    <option value="울진">울진</option>
                                    <option value="의성">의성</option>
                                    <option value="청도">청도</option>
                                    <option value="청송">청송</option>
                                    <option value="칠곡">칠곡</option>
                                    <option value="포항">포항</option>
                                </select>
                                <select class="form-select form-select-lg m-2" aria-label=".form-select-lg example" v-else-if="this.region === '경남'" @change="selectCity($event)">
                                    <option value="" selected>시/군/구</option>
                                    <option value="거제">거제</option>
                                    <option value="거창">거창</option>
                                    <option value="고성">고성</option>
                                    <option value="김해">김해</option>
                                    <option value="남해">남해</option>
                                    <option value="밀양">밀양</option>
                                    <option value="사천">사천</option>
                                    <option value="산청">산청</option>
                                    <option value="양산">양산</option>
                                    <option value="의령">의령</option>
                                    <option value="진주">진주</option>
                                    <option value="창녕">창녕</option>
                                    <option value="창원">창원</option>
                                    <option value="통영">통영</option>
                                    <option value="하동">하동</option>
                                    <option value="함안">함안</option>
                                    <option value="함양">함양</option>
                                    <option value="합천">합천</option>
                                </select>
                                <select class="form-select form-select-lg m-2" aria-label=".form-select-lg example" v-else-if="this.region === '대구'" @change="selectCity($event)">
                                    <option value="" selected>시/군/구</option>
                                    <option value="남구">남구</option>
                                    <option value="달서구">달서구</option>
                                    <option value="동구">동구</option>
                                    <option value="북구">북구</option>
                                    <option value="서구">서구</option>
                                    <option value="수성구">수성구</option>
                                    <option value="중구">중구</option>
                                    <option value="달성군">달성군</option>
                                </select>
                                <select class="form-select form-select-lg m-2" aria-label=".form-select-lg example" v-else-if="this.region === '울산'" @change="selectCity($event)">
                                    <option value="" selected>시/군/구</option>
                                    <option value="남구">남구</option>
                                    <option value="동구">동구</option>
                                    <option value="북구">북구</option>
                                    <option value="중구">중구</option>
                                    <option value="울주군">울주군</option>
                                </select>
                                <select class="form-select form-select-lg m-2" aria-label=".form-select-lg example" v-else-if="this.region === '부산'" @change="selectCity($event)">
                                    <option value="" selected>시/군/구</option>
                                    <option value="강서구">강서구</option>
                                    <option value="금정구">금정구</option>
                                    <option value="남구">남구</option>
                                    <option value="동구">동구</option>
                                    <option value="동래구">동래구</option>
                                    <option value="부산진구">부산진구</option>
                                    <option value="북구">북구</option>
                                    <option value="사상구">사상구</option>
                                    <option value="사하구">사하구</option>
                                    <option value="서구">서구</option>
                                    <option value="수영구">수영구</option>
                                    <option value="연제구">연제구</option>
                                    <option value="영도구">영도구</option>
                                    <option value="중구">중구</option>
                                    <option value="해운대구">해운대구</option>
                                    <option value="기장군">기장군</option>
                                </select>
                                <select class="form-select form-select-lg m-2" aria-label=".form-select-lg example" v-else-if="this.region === '전북'" @change="selectCity($event)">
                                    <option value="" selected>시/군/구</option>
                                    <option value="고창">고창</option>
                                    <option value="군산">군산</option>
                                    <option value="김제">김제</option>
                                    <option value="남원">남원</option>
                                    <option value="무주">무주</option>
                                    <option value="부안">부안</option>
                                    <option value="순창">순창</option>
                                    <option value="완주">완주</option>
                                    <option value="익산">익산</option>
                                    <option value="임실">임실</option>
                                    <option value="장수">장수</option>
                                    <option value="전주">전주</option>
                                    <option value="정읍">정읍</option>
                                    <option value="진안">진안</option>
                                </select>
                                <select class="form-select form-select-lg m-2" aria-label=".form-select-lg example" v-else-if="this.region === '전남'" @change="selectCity($event)">
                                    <option value="" selected>시/군/구</option>
                                    <option value="강진">강진</option>
                                    <option value="고흥">고흥</option>
                                    <option value="곡성">곡성</option>
                                    <option value="광양">광양</option>
                                    <option value="구례">구례</option>
                                    <option value="나주">나주</option>
                                    <option value="담양">담양</option>
                                    <option value="목포">목포</option>
                                    <option value="무안">무안</option>
                                    <option value="보성">보성</option>
                                    <option value="순천">순천</option>
                                    <option value="신안">신안</option>
                                    <option value="여수">여수</option>
                                    <option value="영광">영광</option>
                                    <option value="영암">영암</option>
                                    <option value="완도">완도</option>
                                    <option value="장성">장성</option>
                                    <option value="장흥">장흥</option>
                                    <option value="진도">진도</option>
                                    <option value="함평">함평</option>
                                    <option value="해남">해남</option>
                                    <option value="화순">화순</option>
                                </select>
                                <select class="form-select form-select-lg m-2" aria-label=".form-select-lg example" v-else-if="this.region === '광주'" @change="selectCity($event)">
                                    <option value="" selected>시/군/구</option>
                                    <option value="광산구">광산구</option>
                                    <option value="남구">남구</option>
                                    <option value="동구">동구</option>
                                    <option value="북구">북구</option>
                                    <option value="서구">서구</option>
                                </select>
                                <select class="form-select form-select-lg m-2" aria-label=".form-select-lg example" v-else-if="this.region === '제주'" @change="selectCity($event)">
                                    <option value="" selected>시/군/구</option>
                                    <option value="서귀포">서귀포</option>
                                    <option value="제주">제주</option>
                                </select>
                            </div>
                            <div class="modal-footer">
                                <!-- <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button> -->
                                <button type="button" class="btn btn-success" data-bs-dismiss="modal" @click="saveRegion()" v-if="this.region === '세종'">저장</button>
                                <button type="button" class="btn btn-secondary" v-else-if="!this.region || !this.city">저장</button>
                                <button type="button" class="btn btn-success" data-bs-dismiss="modal" @click="saveRegion()" v-else>저장</button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="container mb-5">
                <div v-if="this.region === '세종'">
                    지역이 <b>{{ this.region }}</b>시로 설정되었습니다.
                </div>
                <div v-else-if="this.region && this.city">
                    지역이 <b>{{ this.region }}</b> / <b>{{ this.city }}</b>(으)로 설정되었습니다.
                </div>
            </div>
        <router-link to=""><button class="btn btn-success">서비스 시작</button></router-link>
      </form>
    </div>

  </div>
</template>

<script>
export default {
    name: "SignupNext",
    data() {
        return {
            region: '',
            city: ''
        }
    },
    methods: {
        selectRegion(event) {
            // console.log(event.target.value);
            this.region = event.target.value
            // console.log(this.region);
            this.city = ''
        },
        selectCity(event) {
            // console.log(event.target.value)
            this.city = event.target.value
        },
        saveRegion() {
            // console.log(event);
            console.log(this.region)
            console.log(this.city)
        }
    }
}
</script>

<style>

</style>