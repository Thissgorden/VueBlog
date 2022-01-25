<template>
  <el-container>
    <div v-if="status == 1">
      <h2>
        激活成功！将在{{ num }}秒后跳转，或者
        <router-link to="/login">点此进行跳转</router-link>
      </h2>
    </div>

    <div v-if="status == 2">
      <h2>
        激活失败！请联系管理员。将在{{ num }}秒后跳转，或者
        <router-link to="/login">点此进行跳转</router-link>
      </h2>
    </div>

    <div v-if="status == 3">
      <h2>
        参数有误！将在{{ num }}秒后跳转，或者
        <router-link to="/login">点此进行跳转</router-link>
      </h2>
    </div>
  </el-container>
</template>

<script>
export default {
  name: "RegistryP",
  data() {
    return {
      num: 10,
      status: 0,
    };
  },
  methods: {
    doUpdate() {
      var fnum = this.num;
      fnum--;

      if (fnum <= 0) {
        this.$router.push("/login");
      }
      this.num = fnum;
    },
  },
  created() {
    const status = this.$route.query.status;
    if (status == 1) {
      //激活成功
      this.status = 1;
      window.setInterval(this.doUpdate, 1000);

      
    } else if (status == 2) {
      //激活失败
      this.status = 2;
      window.setInterval(this.doUpdate, 1000);
    } else {
      //参数有误
      this.status = 3;
      window.setInterval(this.doUpdate, 1000);
    }
  },
};
</script>

<style scoped>
</style>