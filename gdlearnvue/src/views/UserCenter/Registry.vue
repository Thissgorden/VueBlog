<template>
  <el-container>
    <el-form
      :model="loginForm"
      :rules="rules"
      status-icon
      ref="loginForm"
      label-width="100px"
      class="demo-ruleForm"
    >
      <el-form-item label="用户名" prop="username" style="width: 380px">
        <el-input v-model="loginForm.username"></el-input>
      </el-form-item>

      <el-form-item label="邮箱" prop="email" style="width: 380px">
        <el-input v-model="loginForm.email"></el-input>
      </el-form-item>

      <el-form-item label="密码" prop="password" style="width: 380px">
        <el-input type="password" v-model="loginForm.password"></el-input>
      </el-form-item>
      <el-form-item
        label="确认密码"
        prop="confirmPassword"
        style="width: 380px"
      >
        <el-input
          type="password"
          v-model="loginForm.confirmPassword"
        ></el-input>
      </el-form-item>

      <el-form-item label="验证码" prop="code">
        <el-input v-model="loginForm.code" class="codeinput"></el-input>
        <el-image
          :src="captchaImg"
          @click="getCaptcha"
          class="codeimg"
        ></el-image>
      </el-form-item>

      <el-form-item>
        <el-button type="primary" @click="submitForm('loginForm')"
          >登录</el-button
        >
        <!-- <el-button @click="resetForm('loginForm')">重置</el-button> -->
      </el-form-item>
    </el-form>
  </el-container>
</template>

<script>
import qs from "qs";

export default {
  name: "Registry",

  data() {
    var validatePass = (rule, value, callback) => {
      if (value === "") {
        callback(new Error("请输入密码"));
      } else if (value.length < 5 || value.length > 15) {
        callback(new Error("长度字符在5到15个字符"));
      } else {
        callback();
      }
    };
    var validatePass2 = (rule, value, callback) => {
      if (value === "") {
        callback(new Error("请再次输入密码"));
      } else if (value !== this.loginForm.password) {
        callback(new Error("两次输入密码不一致!"));
      } else {
        callback();
      }
    };
    var validateEmail = (rule, value, callback) => {
      var mail_filter = new RegExp(/^[a-z0-9]+([._\\-]*[a-z0-9])*@([a-z0-9]+[-a-z0-9]*[a-z0-9]+.){1,63}[a-z0-9]+$/);
      var tmpresult = mail_filter.test(value);
      

      if (value === "") {
        callback(new Error("请输入邮箱"));
        return
      } 
      else if (tmpresult == false) {
        callback(new Error("请输入正确的邮箱"));
        return
      }

      var dbcheck
      this.$axios.get("/checkMail",{params: {email: value}}).then(res=> {
        dbcheck = res.data.code
      }) 

      if( dbcheck == 203 ){
        callback(new Error("该邮箱已被使用"));
      }
      else {
        callback();
      }
    };
    return {
      loginForm: {
        username: "",
        password: "",
        email: "",
        confirmPassword: "",
        code: "",
        key: "",
      },
      rules: {
        username: [
          { required: true, message: "请输入用户名", trigger: "blur" },
          {
            min: 5,
            max: 15,
            message: "长度在 5 到 15 个字符",
            trigger: "blur",
          },
        ],
        email: [
          { required: true, message: "请输入邮箱", trigger: "blur" },
          {
            validator: validateEmail,
            trigger: "blur",
          },
        ],
        password: [
          { required: true, message: "请输入密码", trigger: "blur" },
          {
            validator: validatePass,
            trigger: "blur",
          },
        ],
        confirmPassword: [
          { required: true, message: "请再次输入密码", trigger: "blur" },
          {
            validator: validatePass2,
            trigger: "blur",
          },
        ],
        code: [
          { required: true, message: "请输入验证码", trigger: "blur" },
          { min: 5, max: 5, message: "请输入正确的验证码", trigger: "blur" },
        ],
      },
      captchaImg: "",
    };
  },
  methods: {
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          const _this = this;
          this.$axios
            .post("/login?" + qs.stringify(this.loginForm))
            .then((res) => {
              const jwt = res.headers["authorization"];
              const userInfo = res.data.data;

              _this.$store.commit("SET_TOKEN", jwt);
              _this.$store.commit("SET_USERINFO", userInfo);

              _this.$router.push("/");
            });
        } else {
          console.log("提交出错");
          return false;
        }
      });
    },
        getCaptcha() {
      this.$axios.get("/captcha").then((res) => {
        this.loginForm.key = res.data.data.key; //存储返回的token
        this.captchaImg = res.data.data.base64Img;
      });
    },
    created() {},
  },
};
</script>

<style scoped>
</style>