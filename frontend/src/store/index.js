import Vue from "vue";
import Vuex from "vuex";
import axios from "axios";
import router from "../router"



Vue.use(Vuex);
axios.defaults.baseURL = 'http://192.168.45.109:8080/'

axios.interceptors.request.use(config => {
  const accessToken = localStorage.getItem('access_token')
  config.headers.common['Authorization'] = accessToken ? `Bearer ${accessToken}` : ''
  return config
})

export default new Vuex.Store({
  state: {
    // user 정보
    userNumber: '',
    userNickname: '',
    // sidebar 변수
    sidebar: false,
    // Settings 변수
    water_notice: false,
    recommendedinfo_notice: false,
    newcomments_notice: false,
    newtwits_notice: false,
    nightmode_notice: false,
    noticeTime: '',
    receiver: '',


    // Alerts 변수
    searchNotices: [],

    // Message 변수
    receivedMessages: [],
    sentMessages: [],
    messageContent: '',
    messageTime: '',
    messageReceiverNickname: '',
    alluserInfo: '',
    messageNumber: '',

    // signup 정보
    userId: localStorage.getItem('userId') || '',
    userNickname: localStorage.getItem('userNickname') || '',
    userNumber: localStorage.getItem('userNumber') || '',
    myId: null,
    loginCheck: false,
    accessToken: localStorage.getItem('access_token') || '',

    // profile 정보
    profile: {
      id: null,
      userNumber: null,
      userNickname: null,
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
      state.noticeTime = data.noticeTime
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

    GET_MESSAGE(state, message_data) {
      state.Message = message_data
  
    },
    // Sidebar mutations

    DELETE_TOKEN(state) {
      state.accessToken = '';
      state.userId = '';
      state.userNumber = '';
      state.userNickname = '';
    },

    // Get User
    UPDATE_LOGIN_USER(state, payload) {
      state.accessToken = payload.token
      state.userNumber = payload.userNumber
      state.userNickname = payload.nickname
      state.userId = payload.id
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
    GET_USERINFO_ALL(state, data){
      state.alluserInfo = data
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
        params: {
          userNumber: 1,
          page: 0
        }
      })
      .then(res => {
        // console.log(res.data)
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
        params: {
          userNumber: 1,
          page: 0
        }
      })
      .then(res => {
        context.commit('GET_SENT_MESSAGES', res.data)
      })
      .catch(err => {
        console.error(err)
      })
    },
    getUserinfoAll (context){
      axios.get(`user/all`)
      .then(res => {
        context.commit('GET_USERINFO_ALL', res.data)
        // console.log(this.state.alluserInfo)
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


    readingMessage(context, messageNum) {
      axios({
        method: 'put',
        url: `message/`,
        params: {
          messageNumber: Number(messageNum),
        },
        // data: {
        //   isRead: 1
        //   // messageNumber: Number(messageNum)
        // }
      })
        .then(res => {
          console.log(res.data)
        })
        .catch(err => {
          console.log(messageNum)
          console.error(err)
        })
      },

      // 메세지 작성
    messagePost(context, [content, receiver]) {
        axios({
          method: 'post',
          url: `message/`,
          params: {
            content: content,
            sender: localStorage.getItem('userNumber'),
            receiver: receiver
          }
          })
          .then(res => {
            this.dispatch('getUserinfoAll')
            console.log(res.data)
            // console.log(receiver)
            // router.push({name: 'Profile', params: {
            //   userNumber: 1,
            //   page: 0
            // }} )
          })
          .catch(err => {
            // console.log(this.receiver)
            // console.log(receiver)
            console.error(err)
          })
        context
      },
    messageDelete(context, [messageNum]) {
      axios({
        method: 'delete',
        url: `message/`,
        params: {
          messageNumber: messageNum
        }
        })
        .then(res => {
          // console.log(this.messageNumber)
          console.log(res.data)
        })
        .catch(err => {
          // console.log(messageNum)
          console.log(err)
          // console.log(this.messageNumber)
        })
      // context
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
      commit('DELETE_TOKEN');
      localStorage.removeItem('access_token');
      localStorage.clear();
    },

    //Modify actions
    backToProfile() {
      router.go(-1)
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
          commit
          axios.get('user/my')
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
      axios.get('user/my')
      .then(res => {
        var addressPut = { ...res.data, address: myAddress}
        axios.put('user/', addressPut)
        .then(res => {
          res
          router.push({ name: 'Main' })
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
      axios.post('user/pass/idFind', {phone: myPhone})
      .then(res => {
        commit('FIND_ID', res.data)
        router.push({ name: 'FindIdNext' })
      })
      .catch(err => {
        console.error(err);
      })
    },

    login({ commit }, credentials) {
      var getToken = null
        try {
          getToken = WebViewBridge.webViewToApp('겟토큰');
          console.log(getToken);
        } catch (error) {}
      
      if(getToken) {
        axios({
          method: 'post',
          url: `phone/authenticate`,
          params: {
            id: credentials[0],
            password: credentials[1],
            token: getToken
          }
        })
        .then(res => {
          localStorage.setItem('access_token', res.data.token)
          commit('UPDATE_LOGIN_USER', res.data)
          axios.get('user/my')
          .then(res => {
            localStorage.setItem('userId', res.data.id)
            localStorage.setItem('userNumber', res.data.userNumber)
            localStorage.setItem('userNickname', res.data.nickname)
            router.push({ name: 'Main' })
          })
          .catch(err => {
            console.error(err);
          })
        })
        .catch(err => {
          console.error(err);
        })
      }
      else {
        axios.post('user/authenticate', {
          id: credentials[0],
          password: credentials[1]
        })
        .then(res => {
          localStorage.setItem('access_token', res.data.token)
          commit('UPDATE_LOGIN_USER', res.data)
          axios.get('user/my')
          .then(res => {
            localStorage.setItem('userId', res.data.id)
            localStorage.setItem('userNumber', res.data.userNumber)
            localStorage.setItem('userNickname', res.data.nickname)
            router.push({ name: 'Main' })
          })
          .catch(err => {
            console.error(err);
          })
        })
        .catch(err => {
          console.error(err);
        })
      }
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
