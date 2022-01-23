<template>
  <el-container>
    <Aside></Aside>
    <el-container direction="vertical">
    <Header></Header>
    <div class="mblog">
      <div>
      <h2 class="title">{{ blog.title }}</h2>
      <h3 class="author">作者:{{blog.owner}}</h3>
    </div>
      <div class="function-area">
        <div class="function-part" v-if="ownBlog">
          <el-button slot="reference" @click="gotoEdit(blog.id)" type="primary">编辑</el-button>
        </div>

        <div class="function-part" v-if="ownBlog">
          <el-popconfirm title="确定删除吗？" @confirm="delBlog(blog.id)">
            <el-button slot="reference" type="danger">删除</el-button>
          </el-popconfirm>
        </div>
      </div>

      <el-divider></el-divider>
      <div class="markdown-body" v-html="blog.content"></div>
    </div>
    <Footer></Footer>
    </el-container>

  </el-container>

</template>

<script>
import Header from "@/components/Header";
import Footer from "@/components/Footer";
import Aside from "../components/Aside.vue";
//import "github-markdown-css/github-markdown.css"

export default {
  name: "BlogDetail",
  components: {Footer, Header,Aside},
  data() {
    return {
      blog: {
        id: '',
        title: '加载中',
        content: '请稍后，内容加载中',
        owner: ''
      },
      ownBlog: false
    }
  }, methods: {
    delBlog(blogId) {
      this.$axios.get('/blog/delete?blogId='+blogId).then(() => {
        this.$alert('删除成功','提示',{
          confirmButtonText:'确定',
          callback:()=>{
            this.$router.push('/blogs')
          }
        })
      })
    },
    gotoEdit(blogId){
      this.$router.push('/blog/'+blogId+'/edit')
    }
  },
  created() {
    const _this = this;
    const blogId = this.$route.params.blogId;
    if (blogId) {
      _this.$axios.get('/blog/detail/' + blogId).then(res => {
        const blog = res.data.data//整个data就是blog对象
        if (blog.id !== null) {
          _this.blog.id = blog.id;
        }
        _this.blog.title = blog.title;

        _this.blog.owner = blog.ownerName;
        //由于是markdown内容，需要解析后显示
        //_this.blog.content = blog.content;
        var MarkdownIt = require("markdown-it");
        var md = new MarkdownIt();

        _this.blog.content = md.render(blog.content);

        var usr = this.$store.getters.getUser;

        if (Object.prototype.hasOwnProperty.call(usr, 'id')) {
          _this.ownBlog = (blog.userId === this.$store.getters.getUser.id || usr.roleid == 2)
        }
      });
    }
  }
}
</script>

<style scoped>
.mblog {
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
  width: 95%;
  min-height: 700px;
  padding: 10px 35px;
  margin: auto
}

.title {
  padding: 20px 0;
  height: 10px;
}

.function-part {
  margin: 10px;
  display:inline-block;
}

.function-area {
  width: 50%;
}

.author {
  padding: 20px 0;
  height: 10px;
}

.markdown-body{
  overflow: hidden;
  word-wrap:break-word;
  word-break: break-all;
}
</style>