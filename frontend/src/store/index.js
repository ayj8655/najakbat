import Vue from "vue";
import Vuex from "vuex";

Vue.use(Vuex);

export default new Vuex.Store({
  state: {
    // sidebar 변수
    
    // Settings 변수
    water_alerts: true,
    recommendedinfo_alerts: true,
    newcomments_alerts: true,
    newtwits_alerts: true,
    nightmode_alerts: true,

  },
  mutations: {},
  actions: {},
  modules: {},
});
