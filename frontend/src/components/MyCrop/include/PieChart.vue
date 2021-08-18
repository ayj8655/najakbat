<script>
// import { Vue } from "vue-property-decorator";
import { Pie } from "vue-chartjs";

export default {
  extends: Pie,
  props: {
    chartData: Object,
  },
  data: () => ({
    // msg: "🎨 My Pie Stats",
    // stats: [],
    options: {
      legend: {
        display: true,
      },
      responsive: true,
      maintainAspectRatio: false,
    },
  }),
  mounted() {
    this.renderChart(this.chartData, this.options);
  },
  created() {
    //   this.renderChart(this.chartdata, this.options);
    // this.stats = this.$store.state.categories;
    // const obj = this.chartdata.datasets[0];
    // for (const stat of this.stats) {
    //   this.chartdata.labels.push(stat.title);
    //   obj.label.push(stat.title);
    //   obj.data.push(stat.value);
    //   obj.backgroundColor.push(stat.backgroundColor);
    // }
  },
};
</script>
