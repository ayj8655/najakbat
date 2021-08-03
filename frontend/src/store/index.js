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
    water_notice: false,
    recommendedinfo_notice: false,
    newcomments_notice: false,
    newtwits_notice: false,
    nightmode_notice: false,

    // Alerts 변수
    searchNotices: '',

    // signup 정보
    userId: localStorage.getItem('userId') || '',
    myId: null
  },
  mutations: {

    // Settings mutations
    GET_NOTICE_SETTINGS(state, data) {
      state.water_notice = (data.waterNotice)? true:false;
      state.recommendedinfo_notice = (data.recommendNotice)? true: false;
      state.newcomments_notice = (data.commentNotice)? true:false;
      state.newtwits_notice = (data.messageNotice)? true:false;
      state.nightmode_notice = (data.darkMode)? true:false;
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
    getNoticeSettings(context) {
      axios.get(`user/setting/notice/1`)        
      .then(res => {
        context.commit('GET_NOTICE_SETTINGS', res.data)
        // console.log(this.state)
      })
      .catch(err => {
        console.error(err)
      })
    },
    updateNotice(context, settingsStatus) {  
      settingsStatus[0] = (settingsStatus[0])? 1:0;
      settingsStatus[1] = (settingsStatus[1])? 1:0;
      settingsStatus[2] = (settingsStatus[2])? 1:0;
      settingsStatus[3] = (settingsStatus[3])? 1:0;
      settingsStatus[4] = (settingsStatus[4])? 1:0;
      axios({
        method: 'put',
        url: `user/setting/notice`,
        data: {
          waterNotice: settingsStatus[0],
          recommendNotice: settingsStatus[1],
          commentNotice: settingsStatus[2],
          messageNotice: settingsStatus[3],
          darkMode: settingsStatus[4],
          userNumber: 1,
        }
      })
        .then(res => {
          console.log(settingsStatus)
          console.log(res.data)
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
