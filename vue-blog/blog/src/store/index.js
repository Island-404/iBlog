import Vue from "vue";
import Vuex from "vuex";
import createPersistedState from "vuex-persistedstate";

Vue.use(Vuex);

export default new Vuex.Store({
  state: {
    token: '',
    searchFlag: false,
    loginFlag: false,
    registerFlag: false,
    forgetFlag: false,
    emailFlag: false,
    drawer: false,
    loginUrl: "",
    userId: null,
    userface: null,
    nickname: null,
    intro:null,
    email: null,
    articleLikeSet: [],
    blogInfo: {}
  },
  mutations: {
    setToken: (state, token) =>{
      state.token = token
    },
    login(state, user) {
      state.userId = user.id;
      state.userface = user.userface;
      state.nickname = user.nickname;
      state.intro = user.intro;
      state.email = user.email;
      state.articleLikeSet = user.articleLikeSet ? user.articleLikeSet : [];
    },
    logout(state) {
      state.token = ""
      state.userId = null;
      state.userface = null;
      state.nickname = null;
      state.intro = null;
      state.email = null;
      state.articleLikeSet = [];
    },
    saveLoginUrl(state, url) {
      state.loginUrl = url;
    },
    saveEmail(state, email) {
      state.email = email;
    },
    updateUserInfo(state, user) {
      state.nickname = user.nickname;
      state.intro = user.intro;
      state.email = user.email;
      
    },
    updateAvatar(state, userface) {
      state.userface = userface;
    },
    checkBlogInfo(state, blogInfo) {
      state.blogInfo = blogInfo;
    },
    closeModel(state) {
      state.registerFlag = false;
      state.loginFlag = false;
      state.searchFlag = false;
      state.emailFlag = false;
    },
    articleLike(state, articleId) {
      var articleLikeSet = state.articleLikeSet;
      if (articleLikeSet.indexOf(articleId) != -1) {
        articleLikeSet.splice(articleLikeSet.indexOf(articleId), 1);
      } else {
        articleLikeSet.push(articleId);
      }
    },
    commentLike(state, commentId) {
      var commentLikeSet = state.commentLikeSet;
      if (commentLikeSet.indexOf(commentId) != -1) {
        commentLikeSet.splice(commentLikeSet.indexOf(commentId), 1);
      } else {
        commentLikeSet.push(commentId);
      }
    }
  },
  actions: {},
  modules: {},
  plugins: [
    createPersistedState({
      storage: window.sessionStorage
    })
  ]
});
