<template>
  <el-container direction="vertical">
    <Header></Header>
    <el-container style="margin: 20px">
      <div>仅供娱乐，该界面数据不保存！图片数据来自：https://wiki.biligame.com/ys</div>
      <div>概率与保底系统逻辑上和原神游戏内一致，不代表游戏内实际概率。注意！数据库内容并不包含所有角色以及武器</div>
    </el-container>
    <h1 style="margin: 0 20px">原神抽卡模拟界面</h1>
    <el-divider></el-divider>
    <el-row :gutter="20">
      <el-col :span="12">
        <div class="grid-content bg-purple">
          <el-container class="mainImg">
            <div>
              <el-image src="https://patchwiki.biligame.com/images/ys/6/64/bux6bw2qyob0d18abq4a1c8gppx2zp5.png"/>
              <div class="gachaButton">
                <el-button round @click="rollOnce()">抽取一次</el-button>
                <el-button round @click="rollTen">抽取十次</el-button>
              </div>
            </div>
          </el-container>
        </div>
      </el-col>

      <el-col :span="10">
        <div class="grid-content bg-purple">
          <template>
            <el-table
                :data="tableData"
                stripe
                height="950"
                border
                style="width: 100%">
              <el-table-column
                  prop="icon"
                  label="图标"
                  width="90">
                <template slot-scope="scope">
                  <img width="60px" height="60px" :src="scope.row.icon"/>
                </template>
              </el-table-column>
<!--              <el-table-column-->
<!--                  prop="count"-->
<!--                  label="抽数"-->
<!--                  width="50">-->
<!--              </el-table-column>-->
              <el-table-column
                  prop="typ"
                  label="类型"
                  width="50">
              </el-table-column>
              <el-table-column
                  prop="itemname"
                  label="名称"
                  width="180">
              </el-table-column>
              <el-table-column
                  prop="rare"
                  label="稀有度">
              </el-table-column>
            </el-table>
          </template>
          <div>累计抽取{{tableData.length}}次，累计花费{{tableData.length*160}}原石，五星{{count.star5}}个，四星{{count.star4}}个，三星{{count.star3}}个</div>
        </div>
      </el-col>
    </el-row>
    <Footer></Footer>
  </el-container>
</template>

<script>
import Header from "@/components/Header";
import Footer from "@/components/Footer";

import qs from 'qs'

export default {
  data() {
    return{
      tableData: [],
      count: {
        star5:0,
        star4:0,
        star3:0,
      },
      //保底数据
      protect: {
        protectcount: 0,
        protect4: false,
        protect: false,
        protect4count: 0
      }
    }
  },
  name: "Gacha",
  components: {Header,Footer},
  methods:{
    rollOnce(){
      this.$axios.post('/Gacha?'+qs.stringify(this.protect)).then(res=>{
        this.protect = res.data.data.protect;
        var gacha = res.data.data.gacha;

        console.log('大保底:'+this.protect.protectcount+'----小保底:'+this.protect.protect4count)

        if(gacha.typ == '1'){gacha.typ='角色'}
        else if(gacha.typ == '2'){gacha.typ='武器'}

        if(gacha.rare =='3'){gacha.rare='★★★';this.count.star3++}
        else if(gacha.rare =='4'){gacha.rare='★★★★';this.count.star4++}
        else if(gacha.rare =='5'){gacha.rare='★★★★★';this.count.star5++}
        this.tableData.unshift(gacha)
      })
    },
    rollTen(){
      this.$axios.post('/Gacha10?'+qs.stringify(this.protect)).then(res=>{
        this.protect = res.data.data.protect;
        var gachas = res.data.data.gachas;

        for (let i = 0; i < gachas.length; i++) {
          var gacha =gachas[i];
          if(gacha.typ == '1'){gacha.typ='角色'}
          else if(gacha.typ == '2'){gacha.typ='武器'}

          if(gacha.rare =='3'){gacha.rare='★★★';this.count.star3++}
          else if(gacha.rare =='4'){gacha.rare='★★★★';this.count.star4++}
          else if(gacha.rare =='5'){gacha.rare='★★★★★';this.count.star5++}
          this.tableData.unshift(gacha)
        }
      })
    }
  }
}
</script>

<style scoped>
.mainImg {
  margin-left: 10px;
}

.gachaButton {
  width: 300px;
  margin: 10px 65%;
}

.el-footer {
  text-align: center;
}
</style>