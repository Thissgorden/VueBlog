<template>
  <div class="m-content">
    <h3>欢迎来到GDLearn博客</h3>
    <div class="block">
      <el-avatar :size="50" :src="user.avatar"></el-avatar>
      <div>{{user.username}}</div>
    </div>

    <div class="maction">
      <span><el-link href="/blogs">主页</el-link></span>
      <el-divider direction="vertical"></el-divider>
      <span><el-link type="success" href="/blog/add">发表博客</el-link></span>
      <el-divider direction="vertical"></el-divider>
      <span><el-link v-show="!hasLogin" type="warning" href="/login">登陆</el-link></span>
      <span><el-link v-show="hasLogin" type="warning" @click="logout">登出</el-link></span>
    </div>

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
      hasLogin:false
    }
  },
  methods:{
    logout() {
      const _this = this;
      _this.$axios.get("/logout",{
        headers:{
          "Authorization": localStorage.getItem("token")
        }
      }).then(res =>{
        _this.$store.commit("REMOVE_INFO");
        _this.$router.push("/login");
        res;
      })
    },
  },
  created() {
    var usr = this.$store.getters.getUser
    if(Object.prototype.hasOwnProperty.call('id',usr)){
      this.user.username = this.$store.getters.getUser.username;
      this.user.avatar = this.$store.getters.getUser.avatar;
      this.hasLogin = true;
    }
  }
}
</script>

<style scoped>
.m-content {
  /*background-color: azure;*/
  max-width: 960px;
  margin:0 auto;
  text-align: center;
}
.maction {
  margin: 10px;
}
</style>