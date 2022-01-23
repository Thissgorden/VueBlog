<template>
  <div class="background">
    <div class="main">
      <router-view></router-view>
    </div>
  </div>
</template>

<script>
export default {
  name: "Vue",
  created() {
    var usr = this.$store.state.userInfo;
    if (localStorage.getItem("token") != null && usr == null) {
      //解决携带JWT再次访问信息丢失
      this.$axios.get('/userInfo').then(res => {
        const userInfo = res.data.data
        this.$store.commit("SET_USERINFO",userInfo);
        location.reload();
      })
    }
  }
}
</script>

<style>
.main {
  background-color: rgba(255, 255, 255, 0.93);
  width: auto;
  max-width: 90%;
  margin: auto;
}

.background {
  margin: -8px;
  background-image: url("https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fimage.yjcf360.com%2Fu%2Fcms%2Fwww%2F202009%2F271121209bvr.gif&refer=http%3A%2F%2Fimage.yjcf360.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1640161158&t=e35f040f8370a6aa947b7a071b3ebc32");
}
</style>
