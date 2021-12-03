<template>
  <div class="m-content">
    <el-container class="maction">
      <el-header>
        <div class="block">
          <h3>欢迎使用GDLearn博客</h3>
          <span><el-link href="/blogs">主页</el-link></span>
          <el-divider direction="vertical" v-if="isVisitor"></el-divider>
          <span><el-link type="success" href="/blog/add" v-if="isVisitor">发表博客</el-link></span>
          <el-divider direction="vertical" v-if="isVisitor"></el-divider>
          <span><el-link type="success" href="/gacha">原神抽卡模拟器</el-link></span>
        </div>
      </el-header>
    </el-container>
    <el-container class="userInfo">
      <div>
        <el-avatar :size="50" :src="user.avatar"></el-avatar>

          <div>{{ user.username }}</div>
          <div style="margin: 0 auto">
          <span><el-link v-show="!hasLogin" type="warning" href="/login">登陆</el-link></span>
          <span><el-link v-show="hasLogin" type="warning" @click="logout">登出</el-link></span>
        </div>
      </div>
    </el-container>

  </div>
</template>

<script>
export default {
  name: "Header",
  data() {
    return {
      user: {
        username: '请先登陆',
        avatar: 'https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png'
      },
      hasLogin: false,
      isVisitor: false
    }
  },
  methods: {
    logout() {
      const _this = this;
      _this.$axios.get("/logout", {
        headers: {
          "Authorization": localStorage.getItem("token")
        }
      }).then(res => {
        _this.$store.commit("REMOVE_INFO");
        _this.$router.push("/login");
        res;
      })
    },
  },
  created() {
    var usr = this.$store.state.userInfo
    if (usr !== null) {
      this.user.username = usr.username
      this.user.avatar = usr.avatar
      this.hasLogin = true;
      this.isVisitor = (usr.roleid != 3);
      console.log(usr.roleid)
    }
  }
}
</script>

<style scoped>
.m-content {
  max-width: 90%;
  height: 120px;

}

.maction {
  margin: 0 70px;
}

.block {
  width: 260px;
  margin-top: 30px;
}

.userInfo {
  width: 100px;
  margin: -40px 95%;
}
</style>