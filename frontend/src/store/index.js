import Vue from "vue";
import Vuex from "vuex";
import axios from "axios";
Vue.use(Vuex);

export default new Vuex.Store({
  state: {
    // user 정보
    userNumber: '',
    // sidebar 변수
    
    // Settings 변수
    water_notice: true,
    recommendedinfo_notice: true,
    newcomments_notice: true,
    newtwits_notice: true,
    nightmode_notice: true,

    // Alerts 변수
    searchNotices: '',
  },
  mutations: {

    // Settings mutations
    GET_NOTICE_SETTINGS(state, waterNotice, recommendNotice, commentNotice, messageNotice, darkMode) {
      state.water_notice = waterNotice
      state.recommendedinfo_notice = recommendNotice
      state.newcomments_notice = commentNotice
      state.newtwits_notice = messageNotice
      state.nightmode_notice = darkMode
    },

    GET_SEARCH_NOTICE(state, searchNotice) {
      state.searchNotices = searchNotice
    }
  },

  actions: {
    // Alerts actions

    getSearchNotice(context, userNumber) {
      axios.get(`user/notice/${userNumber}`)
      .then(res => {
        context.commit('GET_SEARCH_NOTICE', res.data)
      })
      .catch(err => {
        console.error(err)
      })
    },

    // Settings actions
    getNoticeSettings(context, userNumber) {
      axios({
        method: 'get',
        url: `user/setting/notice`,
        data: {
          userNumber: userNumber
        }
      })
      .then(res => {
        context.commit('GET_NOTICE_SETTINGS', res.data)
      })
      .catch(err => {
        console.error(err)
      })
    },

    updateNotice(context, water_notice, recommendedinfo_notice, newcomments_notice, newtwits_notice, nightmode_notice) {
      axios({
        method: 'put',
        url: `user/setting/notice`,
        data: {
          water_notice,
          recommendedinfo_notice,
          newcomments_notice,
          newtwits_notice,
          nightmode_notice
        }
      })
        .then(res => {
          console.log(res)
        })
        .catch(err => {
          console.error(err)
        })
      }
    
    
  },

  modules: {},
});
