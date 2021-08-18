<template>
  <section class="section">
    <div class="container">
      <h2 class="subtitle has-text-centered">
        {{ year }}년 {{ month }}월
        <!-- 현재 달력의 년, 월 표시 -->
      </h2>
      <table
        class="table has-text-centered is-fullwidth"
      >
        <thead>
          <th v-for="day in days" :key="day">{{ day }}</th>
        </thead>
        <tbody>
          <tr v-for="(date, idx) in dates" :key="idx">
            <td
              v-for="(day, secondIdx) in date"
              :key="secondIdx"
              :id="'colors' + daysForColor[day]"
            >
              {{ day }}
            </td>
          </tr>
        </tbody>
      </table>
    </div>
  </section>
</template>

<script>
import axios from "axios";
export default {
  props: {
    plantedDate: String,
  },
  data() {
    return {
      days: ["월", "화", "수", "목", "금", "토", "일"],
      dates: [],
      currentYear: 0,
      currentMonth: 0,
      year: 0,
      month: 0,
      today: 0,
      // colors: ['#446631', '#71873F', '#B6C790', '#EDDE8E', '#EBB856', '#999999'],
      // colorFlag : [true, false, false, false, false, false],
      daysForCalendar: null,
      daysForColor: [],
      startDay: 100,
    };
  },
  created() {
    // 데이터에 접근이 가능한 첫 번째 라이프 사이클
    const date = new Date();
    this.today = date.getDate();
    this.year = date.getFullYear();
    this.month = date.getMonth() + 1;
    axios
      .get(`user/crop/record/month?userCropNumber=${this.$route.params.no}`)
      .then((data) => {
        this.daysForCalendar = data.data;
        // console.log(this.plantedDate);
        // console.log(this.daysForCalendar);
        let mnum = Number(this.plantedDate.substring(5, 7));
        let dnum = Number(this.plantedDate.substring(8, 10));
        if (mnum == this.month) this.startDay = dnum;
        else this.startDay = 1;
        let lastDay = new Date(this.year, this.month, 0).getDate();
        for (let index = 0; index <= lastDay; index++) {
          if (index >= this.startDay && index <= this.today)
            this.daysForColor[index] = 0;
          else this.daysForColor[index] = -1;
        }
        this.daysForCalendar.forEach((d) => {
          let mnum = Number(d.recordDate.substring(5, 7));
          let dnum = Number(d.recordDate.substring(8, 10));
          if (mnum == this.month) {
            this.daysForColor[dnum] = d.state;
          }
        });
        // console.log(this.daysForColor);
        this.calendarData();
      });
  },
  methods: {
    calendarData() {
      const [monthFirstDay, monthLastDate, lastMonthLastDate] =
        this.getFirstDayLastDate(this.year, this.month);
      this.dates = this.getMonthOfDays(
        monthFirstDay,
        monthLastDate,
        lastMonthLastDate
      );
    },
    getFirstDayLastDate(year, month) {
      const firstDay = new Date(year, month - 1, 1).getDay(); // 이번 달 시작 요일
      const lastDate = new Date(year, month, 0).getDate(); // 이번 달 마지막 날짜
      let lastYear = year;
      let lastMonth = month - 1;
      if (month === 1) {
        lastMonth = 12;
        lastYear -= 1;
      }
      const prevLastDate = new Date(lastYear, lastMonth, 0).getDate(); // 지난 달 마지막 날짜
      return [firstDay, lastDate, prevLastDate];
    },
    getMonthOfDays(monthFirstDay, monthLastDate, prevMonthLastDate) {
      let day = 1;
      let prevDay = prevMonthLastDate - monthFirstDay + 1;
      const dates = [];
      let weekOfDays = [];
      while (day <= monthLastDate) {
        if (day === 1) {
          // 1일이 어느 요일인지에 따라 테이블에 그리기 위한 지난 셀의 날짜들을 구할 필요가 있다.
          for (let j = 0; j < monthFirstDay; j += 1) {
            weekOfDays.push(prevDay);
            prevDay += 1;
          }
        }
        weekOfDays.push(day);
        if (weekOfDays.length === 7) {
          // 일주일 채우면
          dates.push(weekOfDays);
          weekOfDays = []; // 초기화
        }
        day += 1;
      }
      const len = weekOfDays.length;
      if (len > 0 && len < 7) {
        for (let k = 1; k <= 7 - len; k += 1) {
          //   weekOfDays.push(k);
        }
      }
      if (weekOfDays.length > 0) dates.push(weekOfDays); // 남은 날짜 추가
      return dates;
    },
  },
};
</script>

<style script>
.todayFont {
  color: white;
}
#colors-1 {
}
#colors0 {
  background-color: #A8B99D;
}
#colors1 {
  background-color: #D7E4BA;
  }
#colors2 {
  background-color: #FDF2B8;
}
#colors3 {
  background-color: #FDE9EA;
}
#colors4 {
  background-color: #E7B5AC;
}
#colors5 {
  background-color: #CAC8C8;
}
</style>
