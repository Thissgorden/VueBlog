<template>
  <el-container :style="{'min-height': clientHeight + 'px'}">
    <Aside></Aside>
    <el-container direction="vertical">
      <Header></Header>
      <el-main class="block">
        <el-timeline>
          <el-timeline-item
            :timestamp="blog.created"
            placement="top"
            v-for="blog in blogs"
            :key="blog.id"
          >
            <el-card>
              <h4>
                <router-link
                  :to="{ name: 'BlogDetail', params: { blogId: blog.id } }"
                >
                  {{ blog.title }}
                </router-link>
              </h4>
              <p>{{ blog.description }}</p>
            </el-card>
          </el-timeline-item>
        </el-timeline>

        <el-pagination
          class="mpage"
          background
          layout="prev, pager, next"
          :current-page="currentPage"
          :page-size="pageSize"
          @current-change="page"
          :total="total"
        >
        </el-pagination>
      </el-main>
      <Footer></Footer>
    </el-container>
    
  </el-container>
</template>

<script>
import Header from "../components/Header";
import Footer from "@/components/Footer";
import Aside from "../components/Aside.vue";

export default {
  name: "Blogs",
  components: { Footer, Header, Aside },
  data() {
    return {
      blogs: {},
      currentPage: 1,
      total: 0,
      pageSize: 5,
      clientHeight: document.documentElement.clientHeight
    };
  },
  methods: {
    page(currentPage) {
      //定义了一个获取数据的方法
      const _this = this;
      _this.$axios.get("/blog/list?currentPage=" + currentPage).then((res) => {
        _this.blogs = res.data.data.records;
        _this.currentPage = res.data.data.current;
        _this.total = res.data.data.total;
        _this.pageSize = res.data.data.size;
      });
    },
  },
  created() {
    this.page(1);
  },
};
</script>

<style scoped>
.mpage {
  margin: auto;
  text-align: center;
}
.block {
  width: 95%;
}
</style>