import Vue from "vue";
import Vuex from "vuex";
import axios from "axios";
import router from "../router"

Vue.use(Vuex);
axios.defaults.baseURL = 'http://localhost:8080/'

axios.interceptors.request.use(config => {
  const accessToken = localStorage.getItem('access_token')
  config.headers.common['Authorization'] = accessToken ? `Bearer ${accessToken}` : ''
  return config
})

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
    searchNotices: [],

    // Message 변수
    receivedMessages: [],
    sentMessages: [],

    // signup 정보
    userId: localStorage.getItem('userId') || '',
    myNumber: localStorage.getItem('userNumber') || '',
    myId: null,
    loginCheck: false,
    accessToken: localStorage.getItem('access_token') || '',

    // profile 정보
    profile: {
      id: null,
      userNumber: null,
      nickname: null,
      gold: null
    },
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
      // for (let key in searchNotice) {
      //   const value = searchNotice[key]
      //   state.noticeIsreads.push(value.isRead)
      // }
    },

    // Message mutations

    GET_RECEIVED_MESSAGES(state, receivedMessage) {
      state.receivedMessages = receivedMessage
    },
    
    GET_SENT_MESSAGES(state, sentMessage) {
      state.sentMessages = sentMessage
    },
    // Sidebar mutations

    DELETE_TOKEN(state) {
      state.accessToken = ''
      state.userId = ''
      state.myNumber = ''
    },

    // Get Token
    UPDATE_TOKEN(state, accessToken) {
      state.accessToken = accessToken
    },

    // Find Id
    FIND_ID(state, myId) {
      state.myId = myId
    },
    CHECK_LOGIN(state, payload) {
      if (payload) {
        state.loginCheck = true
      }
      else {
        state.loginCheck = false
      }
      console.log(state.loginCheck);
    },

    // Profile
    GET_PROFILE(state, payload) {
      state.profile['id'] = payload.id
      state.profile['userNumber'] = payload.userNumber
      state.profile['nickname'] = payload.nickname
      state.profile['gold'] = payload.gold
    },
  },


  actions: {
    // Alerts actions

    getSearchNotice(context) {
      axios.get(`user/notice/1`)
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

    // Message actions
    getReceivedMessages(context) {
      axios({
        method: 'get',
        url: `message/receiver`,
        // data: {
        //   userNumber: 1,
        //   page: 1
        // }
      })
      .then(res => {
        context.commit('GET_RECEIVED_MESSAGES', res.data)
      })
      .catch(err => {
        console.error(err)
      })
    },
    getSentMessages(context) {
      axios({
        method: 'get',
        url: `message/sender`,
        // data: {
        //   userNumber: 1,
        //   page: 1
        // }
      })
      .then(res => {
        context.commit('GET_SENT_MESSAGES', res.data)
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
    // Myalerts actions
    updateIsread(context, mynoticeStatus) {
      mynoticeStatus[0] = (mynoticeStatus[0])? 1:0;
      console.log(mynoticeStatus)
      // this.state.searchNotices[mynoticeStatus[1] - 1].isRead = mynoticeStatus[0] 
      axios({
        method: 'put',
        url: `user/notice/${mynoticeStatus[1]}`,
        data: {
          isRead: mynoticeStatus[0],
          userNumber: 1,
        }
      })
        .then(res => {
          console.log(res.data)
        })
        .catch(err => {
          console.error(err)
        })
      },

    deleteallNotices() {
      axios({
        method: 'delete',
        url: `user/notice`,
        data: {
          userNumber: 1,
        }
      })
        .then(res => {
          this.state.searchNotices = [];
          console.log(res)
        })
        .catch(err => {
          console.error(err)
        })
    },
    
    deleteNotice (context, noticeNumber) {
      axios({
        method: 'delete',
        url: `user/notice/${noticeNumber}`,
        data: {
          userNumber: 1,
        }
      })
        .then(res => {
          console.log('success')
          console.log(res)
        })
        .catch(err => {
          console.error(err)
        })
    },
    // Logout actions

    logout({ commit }) {
      commit('DELETE_TOKEN')
      localStorage.removeItem('access_token')
      sessionStorage.claer()
    },
    // Signup actions
    signup({ commit }, credentials) {
      axios.post('user/signup', {
        id: credentials[0],
        password: credentials[1],
        nickname: credentials[2],
        phone: credentials[3]
      })
      .then(res => {
        res
        axios.post('user/authenticate', {
          id: credentials[0],
          password: credentials[1]
        })
        .then(res => {
          localStorage.setItem('access_token', res.data.token)
          commit('UPDATE_TOKEN', res.data.token)
          axios.get('user/user1')
          .then(res => {
            localStorage.setItem('userId', res.data.id)
            localStorage.setItem('userNumber', res.data.userNumber)
            router.push({ path: 'SignupNext' })
          })
          .catch(err => {
            console.error(err);
          })
        })
        .catch(err => {
          console.error(err);
        })
      })
      .catch(err => {
        console.error(err);
      })
    },

    updateAddress({ commit }, myAddress) {
      commit
      myAddress
      axios.get('user/user1')
      .then(res => {
        var addressPut = { ...res.data, address: myAddress}
        axios.put('user/', addressPut)
        .then(res => {
          res
          router.push({ path: 'main' })
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
      axios.post('user/idFind', {phone: myPhone})
      .then(res => {
        commit('FIND_ID', res.data)
        router.push({ name: 'FindIdNext' })
      })
      .catch(err => {
        console.error(err);
      })
    },

    login({ commit }, credentials) {
      axios.post('user/authenticate', {
        id: credentials[0],
        password: credentials[1]
      })
      .then(res => {
        localStorage.setItem('access_token', res.data.token)
        commit('UPDATE_TOKEN', res.data.token)
        axios.get('user/user1')
        .then(res => {
          localStorage.setItem('userId', res.data.id)
          localStorage.setItem('userNumber', res.data.userNumber)
          router.push({ path: 'main' })
        })
        .catch(err => {
          console.error(err);
        })
      })
      .catch(err => {
        console.error(err);
      })
    },

    // Profile actions
    getProfile({ commit }, userNumber) {
      axios.get(`user/${userNumber}`)
      .then(res => {
        commit('GET_PROFILE', res.data)
      })
      .catch(err => {
        console.error(err);
      })
    }
  },

  modules: {},
});
