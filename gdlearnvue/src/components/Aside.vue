<template>
  <el-aside width="200px" style="background-color: rgb(238, 241, 246);">
    <el-container class="userInfo">
      <div>
        <el-avatar :size="50" :src="user.avatar"></el-avatar>

        <div style="width: 100px">{{ user.username }}</div>
        <div style="margin: 0 auto">
          <span
            ><el-link v-show="!hasLogin" type="warning" href="/login"
              >登陆</el-link
            ></span
          >
          <span
            ><el-link v-show="hasLogin" type="warning" @click="logout"
              >登出</el-link
            ></span
          >
        </div>
      </div>
    </el-container>

      <el-menu :default-openeds="['1']" :router="true" style="margin:0 10px">
        <!-- <el-submenu index="1"> -->
          <!-- <template slot="title"><i class="el-icon-message"></i>导航一</template> -->
          <!-- <el-menu-item-group> -->
            <!-- <template slot="title">分组一</template> -->
            <el-menu-item index="1-1" route="/">首页</el-menu-item>
            <el-menu-item index="1-1" route="/gacha">抽卡模拟器</el-menu-item>
          <!-- </el-menu-item-group> -->
        <!-- </el-submenu> -->
        <el-submenu index="1">

          <template slot="title"><i class="el-icon-menu"></i>导航二</template>
          <el-menu-item-group>
            <template slot="title">分组2</template>
            <el-menu-item index="1-1">选项1</el-menu-item>
            <el-menu-item index="1-2">选项2</el-menu-item>
          </el-menu-item-group>

        </el-submenu>
      </el-menu>
      <el-container>
        <div style="text-align: center; margin: 10px 10px">
          公告栏内容-开发中
        </div>
      </el-container>
  </el-aside>
</template>

<script>
export default {
  name: "Aside",
  data() {
    return {
      user: {
        username: "请先登陆",
        avatar:
          "https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png",
      },
      hasLogin: false,
      isVisitor: false,
    };
  },
  methods: {
    logout() {
      const _this = this;
      _this.$axios
        .get("/logout", {
          headers: {
            Authorization: localStorage.getItem("token"),
          },
        })
        .then((res) => {
          _this.$store.commit("REMOVE_INFO");
          _this.$router.push("/login");
          res;
        });
    },
  },

  created() {
    var usr = this.$store.state.userInfo;
    if (usr !== null) {
      this.user.username = usr.username;
      this.user.avatar = usr.avatar;
      this.hasLogin = true;
      this.isVisitor = usr.roleid != 3;
    }
  },
};
</script>

<style scoped>
.userInfo {
  margin: 10px 70px;
}
</style>