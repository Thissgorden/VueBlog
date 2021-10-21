<template>
  <div>
    <Header></Header>
    <div class="mblog">

      <h2 class="title">{{ blog.title }}</h2>
      <el-link icon="el-icon-edit" v-if="ownBlog">
        <router-link :to="{name:'BlogEdit',params: {blogId: blog.id}}">编辑</router-link>
      </el-link>
      <el-divider></el-divider>
      <div class="markdown-body" v-html="blog.content"></div>

    </div>
  </div>
</template>

<script>
import Header from "@/components/Header";
import "github-markdown-css/github-markdown.css"

export default {
  name: "BlogDetail",
  components: {Header},
  data() {
    return {
      blog: {
        id:'',
        title: '加载中',
        content: '请稍后，内容加载中'
      },
      ownBlog: false
    }
  },
  created() {
    const _this = this;
    const blogId = this.$route.params.blogId;
    if (blogId) {
      _this.$axios.get('/blog/detail/' + blogId).then(res => {
        const blog = res.data.data//整个data就是blog对象
        if(blog.id !== null){
          _this.blog.id = blog.id;
        }
        _this.blog.title = blog.title;
        //由于是markdown内容，需要解析后显示
        //_this.blog.content = blog.content;
        var MarkdownIt = require("markdown-it");
        var md = new MarkdownIt();

        _this.blog.content = md.render(blog.content);

        var usr = this.$store.getters.getUser;

        if(Object.prototype.hasOwnProperty.call('id',usr)){
        _this.ownBlog = (blog.userId === _this.$store.getters.getUser.id)
        }
      });
    }
  }
}
</script>

<style scoped>
.mblog {
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
  width: 100%;
  min-height: 700px;
  padding: 10px 35px;
}
.title{
  padding: 20px 0px;
  height: 10px;
}
</style>