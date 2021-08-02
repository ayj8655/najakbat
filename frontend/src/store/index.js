import Vue from "vue";
import Vuex from "vuex";
import axios from "axios";
import router from "../router"

Vue.use(Vuex);
axios.defaults.baseURL = 'http://localhost:8080/'
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

    // signup 정보
    userId: localStorage.getItem('userId') || '',
    myId: null
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
    },

    // Find Id
    FIND_ID(state, myId) {
      state.myId = myId
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
      axios.get(`user/setting/notice/${userNumber}`)        
      .then(res => {
        context.commit('GET_NOTICE_SETTINGS', res.data)
      })
      .catch(err => {
        console.error(err)
      })
    },

    updateNotice(context, water_notice, recommendedinfo_notice, newcomments_notice, newtwits_notice, nightmode_notice, userNumber) {
      axios({
        method: 'put',
        url: `user/setting/notice`,
        data: {
          waterNotice: water_notice,
          recommendNotice: recommendedinfo_notice,
          commentNotice: newcomments_notice,
          messageNotice: newtwits_notice,
          darkMode: nightmode_notice,
          userNumber: userNumber
        }
      })
        .then(res => {
          console.log(res)
        })
        .catch(err => {
          console.error(err)
        })
      },
    
      // Signup actions
      signup({ commit }, credentials) {
        axios.post('user/', {
          id: credentials[0],
          password: credentials[1],
          nickname: credentials[2],
          phone: credentials[3]
        })
        .then(res => {
          res
          commit
          axios.get(`user/${credentials[0]}/`)
          .then(res => {
            localStorage.setItem('userId', res.data.id)
            router.push({ name: 'SignupNext' })
          })
        .catch(err => {
            console.error(err);
          })
        })
        .catch(err => {
          console.error(err);
        })
      },
      findMyId({ commit }, myPhone) {
        axios.post('http://localhost:8080/user/idFind/', {phone: myPhone})
        .then(res => {
          commit('FIND_ID', res.data)
          router.push({ name: 'FindIdNext' })
        })
        .catch(err => {
          console.error(err);
        })
      }
  },

  modules: {},
});
