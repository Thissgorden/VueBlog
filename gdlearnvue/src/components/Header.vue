<template>
  <el-header height="12vh">
    <el-container class="maction">
      <el-header>
        <div class="block">
          <h3>欢迎使用GDLearn博客</h3>
          <span><el-link href="/blogs">主页</el-link></span>
          <el-divider direction="vertical"></el-divider>
          <span
            ><el-link type="success" href="/blog/add" v-if="isVisitor"
              >发表博客</el-link
            ></span
          >
          <!-- <el-divider direction="vertical" v-if="isVisitor"></el-divider>
          <span
            ><el-link type="success" href="/gacha"
              >原神抽卡模拟器</el-link
            ></span
          > -->
        </div>
      </el-header>
    </el-container>
  </el-header>
</template>

<script>
export default {
  name: "Header",
  data() {
    return {
      isVisitor: false,
      hasLogin: false
    };
  },
  created(){
    var usr = this.$store.state.userInfo;
    if (usr !== null) {
      this.hasLogin = true;
      this.isVisitor = usr.roleid != 3;
    }
  }
};
</script>

<style scoped>
.maction {
  margin: 0 70px;
}

/*.block {
  width: 260px;
  margin-top: 30px;
} */
</style>