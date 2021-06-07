<template>
  <v-dialog v-model="loginFlag" :fullscreen="isMobile"  max-width="460">
    <v-card class="login-container" style="border-radius:10px;height: 400px">
      <v-icon class="float-right" @click="loginFlag = false">
        mdi-close
      </v-icon>
      <div class="login-wrapper">
        <!-- 用户名 -->
        <v-text-field
          v-model="username"
          label="用户名"
          placeholder="请输入您的用户名"
          clearable
          @keyup.enter="login"
        />
        <!-- 密码 -->
        <v-text-field
          v-model="password"
          class="mt-7"
          label="密码"
          placeholder="请输入您的密码"
          @keyup.enter="login"
          :append-icon="show ? 'mdi-eye' : 'mdi-eye-off'"
          :type="show ? 'text' : 'password'"
          @click:append="show = !show"
        />
        <!-- 按钮 -->
        <v-btn
          class="mt-7"
          block
          color="blue"
          style="color:#fff"
          @click="login"
        >
          登录
        </v-btn>
        <!-- 注册 -->
        <div class="mt-10 login-tip">
          <span @click="openRegister" class="float-right">立即注册</span>
        </div>
      </div>
    </v-card>
  </v-dialog>
</template>

<script>
export default {
  data: function() {
    return {
      username: "",
      password: "",
      show: false
    };
  },
  computed: {
    loginFlag: {
      set(value) {
        this.$store.state.loginFlag = value;
      },
      get() {
        return this.$store.state.loginFlag;
      }
    },
    isMobile() {
      const clientWidth = document.documentElement.clientWidth;
      if (clientWidth > 960) {
        return false;
      }
      return true;
    }
  },
  methods: {
    openRegister() {
      this.$store.state.loginFlag = false;
      this.$store.state.registerFlag = true;
    },
    
    login() {
      
      if (this.password.trim().length == 0) {
        this.$toast({ type: "error", message: "密码不能为空" });
        return false;
      }
      const that = this;
      // eslint-disable-next-line no-undef
      
      
      //发送登录请求
      let param = new URLSearchParams();
      param.append("username", that.username);
      param.append("password", that.password);
      that.axios.post("/api/login", param).then(resp => {
        if (resp.data.code == 200) {
          const jwt = resp.headers['authorization'];
          that.username = "";
          that.password = "";
          that.$store.commit("setToken",jwt);
          that.$store.commit("login", resp.data.data);
          that.$store.commit("closeModel");
          that.$toast({ type: "success", message: resp.data.msg });
        } else {
          that.$toast({ type: "error", message: resp.data.msg });
        }
      });
      
    
      
     
    }
  }
};
</script>

<style scoped>
.social-login-title {
  margin-top: 1.5rem;
  color: #b5b5b5;
  font-size: 0.75rem;
  text-align: center;
}
.social-login-title::before {
  content: "";
  display: inline-block;
  background-color: #d8d8d8;
  width: 60px;
  height: 1px;
  margin: 0 12px;
  vertical-align: middle;
}
.social-login-title::after {
  content: "";
  display: inline-block;
  background-color: #d8d8d8;
  width: 60px;
  height: 1px;
  margin: 0 12px;
  vertical-align: middle;
}
.social-login-wrapper {
  margin-top: 1rem;
  font-size: 2rem;
  text-align: center;
}
.social-login-wrapper a {
  text-decoration: none;
}
</style>
