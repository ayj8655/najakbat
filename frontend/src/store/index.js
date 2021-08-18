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
    


    // Alerts 변수
    searchNotices: [],
    noticeUnread: [],
    // Message 변수
    receivedMessages: [],
    sentMessages: [],
    messageContent: '',
    messageTime: '',
    messageReceiverNickname: '',
    
    messageNumber: '',
    SenderNumber: '',
    ReceiverNumber: '',

    // qna 변수
    qnas: [],
    qnaQuestion: '',
    qnaType: '',
    qnaTime: '',
    qnaAnswer: '',
    

    // signup 정보
    userId: localStorage.getItem('userId') || '',
    userNickname: localStorage.getItem('userNickname') || '',
    userNumber: localStorage.getItem('userNumber') || '',
    myId: null,
    loginCheck: false,
    accessToken: localStorage.getItem('access_token') || '',
    userCertificate: true,

    // profile 정보
    profile: [],

    // 비밀번호 찾기 정보
    findUserPhone: localStorage.getItem('FindUserPhone') || '',
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
      // for (let notice in searchNotice) {
      //   if (!notice.isRead)
      //   state.noticeUnread.push(notice.isRead)
      // }
    },

    // Message mutations

    GET_RECEIVED_MESSAGES(state, receivedMessage) {
      state.receivedMessages = receivedMessage
    },
    
    GET_SENT_MESSAGES(state, sentMessage) {
      state.sentMessages = sentMessage
    },

    GET_RECEIVER_NUMBER(state, message_data) {
      state.ReceiverNumber = message_data
    },
    // Sidebar mutations

    DELETE_TOKEN(state) {
      state.accessToken = '';
      state.userId = '';
      state.userNumber = '';
      state.userNickname = '';
    },

    // Qna mutations

    GET_QNA(state, qna) { 
      state.qnas = qna
    },

    // Get User
    UPDATE_LOGIN_USER1(state, payload) {
      state.accessToken = payload.token
    },

    UPDATE_LOGIN_USER2(state, payload) {
      state.userNumber = payload.userNumber
      state.userNickname = payload.nickname
      state.userId = payload.id
    },

    // user certificate
    LOGIN_CERTIFICATE(state, payload) {
      state.userCertificate = payload
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
      state.profile = payload
    },
    GET_USERINFO_ALL(state, data){
      state.alluserInfo = data
    },

    //Find Password
    PUT_PHONENUM(state, payload) {
      state.findUserPhone = payload
    }
  },


  actions: {
    // Alerts actions

    getSearchNotice(context) {
      axios.get(`user/notice/${localStorage.getItem('userNumber')}`)
      .then(res => {
        context.commit('GET_SEARCH_NOTICE', res.data)
      })
      .catch(err => {
        console.error(err)
      })
    },


    // Settings actions
    getNoticeSettings(context) {
      axios.get(`user/setting/notice/${localStorage.getItem('userNumber')}`)        
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

    getReceiverNumber(context, nickName) {
      axios({
        method: 'get',
        url: `user/search/${nickName}`
      })
      .then(res => {
        context.commit('GET_RECEIVER_NUMBER', res.data)
        // console.log(this.state.ReceiverNumber)
      })
      .catch(err => {
        console.error(err)
      })
    },

    // qna actions
    getQnas (context) {
      axios({
        method: 'get',
        url: `qna/all`
      })
      .then(res => {
        context.commit('GET_QNA', res.data)
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
          noticeTime: settingsStatus[5],
          userNumber: localStorage.getItem('userNumber'),
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
          userNumber: localStorage.getItem('userNumber'),
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
            console.log(res.data)
            this.ReceiverNumber = ''
          })
          .catch(err => {
            // console.log(this.receiver)
            console.log(receiver)
            console.error(err)
          })
        context
      },

    //  qna 작성
    qnaPost(context, [qnatype, question]) {
      console.log(localStorage.getItem('userNickname'))
      console.log(localStorage.getItem('userNumber'))
      axios({
        method: 'post',
        url: `qna/`,
        params: {
          qnatype: qnatype,
          question: question,
          usernickname: localStorage.getItem('userNickname'),
          userno: localStorage.getItem('userNumber')
        }
      })
      .then(res => {
        console.log(res.data)
        // this.$router.go(0)
      })
      .catch(err => {
        // console.log(usernickname)
        // console.log(userno)
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
    messageListDelete(context, messageList) {
      var myurl = "";
      for (var item of messageList) {
        myurl += "messageNumberList=" + item + "&";
      }
      myurl = myurl.slice(0, -1);

      axios({
        method: 'delete',
        url: `message/list?` + myurl,
        // params: {
        //   messageNumberList: 20
        // },
      })
      .then(res => {
        console.log(res.data)
        // console.log(url)
      })
      .catch(err => {
        console.log(err)
        // console.log(url)
      })
    },
    
    deleteNotices (context, noticeNumberList) {
      var noticeurl = "";
      for (var item of noticeNumberList) {
        noticeurl += "noticeno=" + item + "&";
      }
      noticeurl = noticeurl.slice(0, -1);
      console.log(noticeurl)
      axios({
        method: 'delete',
        url: `user/notice/list?` + noticeurl,
      })
      .then(res => {
        // console.log('success')
        console.log(res.data)
        // console.log(url)
      })
      .catch(err => {
        console.error(err)
        // console.log(url)
      })
    },

    deleteAllNotices(context) {
      axios({
        method: 'delete',
        url: `user/notice`,
        params: {
          userNumber: localStorage.getItem('userNumber'),
        }
      })
        .then(res => {
          // this.state.searchNotices = [];
          console.log(res)
        })
        .catch(err => {
          console.error(err)
        })
    },
    
    // QNA actions
    

    // Logout actions

    logout({ commit }) {
      commit('DELETE_TOKEN');
      localStorage.removeItem('access_token');
      localStorage.clear();
      router.go(0)
    },
    withDrawl(context) {
      axios({ 
        method: 'delete',
        url: `user/${localStorage.getItem('userNumber')}`
      })
      .then(res => {
        console.log(res.data)
      })
      .catch(err => {
        console.error(err)
      })
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
        phone: credentials[3],
        userName: credentials[4]
      })
      .then(res => {
        res
        axios.post('user/authenticate', {
          id: credentials[0],
          password: credentials[1]
        })
        .then(res => {
          localStorage.setItem('access_token', res.data.token)
          commit('UPDATE_LOGIN_USER1', res.data)
          axios.get('user/my')
          .then(res => {
            localStorage.setItem('userId', res.data.id)
            localStorage.setItem('userNumber', res.data.userNumber)
            localStorage.setItem('userNickname', res.data.nickname)
            commit('UPDATE_LOGIN_USER2', res.data)
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
          commit('UPDATE_LOGIN_USER1', res.data)
          axios.get('user/my')
          .then(res => {
            localStorage.setItem('userId', res.data.id)
            localStorage.setItem('userNumber', res.data.userNumber)
            localStorage.setItem('userNickname', res.data.nickname)
            commit('UPDATE_LOGIN_USER2', res.data)
            commit('LOGIN_CERTIFICATE', true)

            router.push({ name: 'Main' })
          })
          .catch(err => {
            console.error(err);
          })
        })
        .catch(err => {
          console.error(err);
          commit('LOGIN_CERTIFICATE', false)
        })
      }
      else {
        axios.post('user/authenticate', {
          id: credentials[0],
          password: credentials[1]
        })
        .then(res => {
          localStorage.setItem('access_token', res.data.token)
          commit('UPDATE_LOGIN_USER1', res.data)
          axios.get('user/my')
          .then(res => {
            localStorage.setItem('userId', res.data.id)
            localStorage.setItem('userNumber', res.data.userNumber)
            localStorage.setItem('userNickname', res.data.nickname)
            commit('UPDATE_LOGIN_USER2', res.data)
            commit('LOGIN_CERTIFICATE', true)
            router.push({ name: 'Main' })
          })
          .catch(err => {
            console.error(err);
          })
        })
        .catch(err => {
          console.error(err);
          commit('LOGIN_CERTIFICATE', false)
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
    },

    // FindPassword actions
    putNextPage({ commit }, phoneNum) {
      commit('PUT_PHONENUM', phoneNum)
      localStorage.setItem('FindUserPhone', phoneNum)
      router.push({ name: 'FindPasswordNext' })
    }
  },


  modules: {},
});
