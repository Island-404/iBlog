<template>
  <div>
    <div class="user-banner banner">
      <h1 class="banner-title">个人中心</h1>
    </div>
    <v-card class="blog-container">
      <div>
        <span class="info-title">基本信息</span>
      </div>
      <v-row class="info-wrapper">
        <v-col md="3" cols="12">
          <button id="pick-avatar">
            <v-avatar size="140">
              <img :src="this.$store.state.userface" />
            </v-avatar>
          </button>
          <avatar-cropper
            @uploaded="uploadAvatar"
            trigger="#pick-avatar"
            upload-url="/api/users/avatar"
            :upload-headers=headers
          />
        </v-col>
        <v-col md="7" cols="12">
          <v-text-field
            v-model="userInfo.nickname"
            label="昵称"
            placeholder="请输入您的昵称"
          />
          
          <v-text-field
            v-model="userInfo.intro"
            class="mt-7"
            label="简介"
            placeholder="介绍下自己吧"
          />

          <v-text-field
            v-model="userInfo.email"
            class="mt-7"
            label="邮箱号"
            placeholder="请绑定邮箱"
          />

          <v-btn @click="updataUserInfo" outlined class="mt-5">修改</v-btn>
        </v-col>
      </v-row>
    </v-card>
  </div>
</template>

<script>
import AvatarCropper from "vue-avatar-cropper";
export default {
  components: { AvatarCropper },
  data: function() {
    return {
      userInfo: {
        nickname: this.$store.state.nickname,
        intro: this.$store.state.intro,
        email: this.$store.state.email
      },
      headers:{
        'Authorization': this.$store.state.token
      }
    };
  },
  methods: {
    updataUserInfo() {
      this.axios.put("/api/user/info",this.userInfo, {
        headers:{
          'Authorization': this.$store.state.token
        }}).then(({ data }) => {
        if (data.code==200) {
          this.$store.commit("updateUserInfo", this.userInfo);
          this.$toast({ type: "success", message: data.msg });
          this.$router.go(0)
        } else {
          this.$toast({ type: "error", message: data.msg });
        }
      });
    },
    uploadAvatar(data) {
      if (data.code == 200) {
        this.$store.commit("updateAvatar", data.data);
        this.$toast({ type: "success", message: data.msg });
      } else {
        this.$toast({ type: "error", message: data.msg });
      }
    }
  },
  computed: {
    email() {
      return this.$store.state.email;
    },
    loginType() {
      return this.$store.state.loginType;
    }
  }
};
</script>

<style scoped>
.user-banner {
  background: url(https://www.static.talkxj.com/article/setting.jpeg) center
    center / cover no-repeat;
  background-color: #49b1f5;
}
.info-title {
  font-size: 1.25rem;
  font-weight: bold;
}
.info-wrapper {
  margin-top: 1rem;
  display: flex;
  align-items: center;
  justify-content: center;
}
#pick-avatar {
  outline: none;
}
.binding {
  display: flex;
  align-items: center;
}
</style>
