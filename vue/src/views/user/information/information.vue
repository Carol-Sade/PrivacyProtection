<template>
  <div class="app-container">
    <el-form label-width="100px" class="demo-ruleForm"
             style="width: 70%;margin-top: 30px">
      <el-form-item label="选择头像">
        <el-upload
          class="avatar-uploader"
          :action="baseUrl"
          :headers="{token:getToken()}"
          :show-file-list="false"
          accept=".jpg,.png"
          :on-success="handleAvatarSuccess"
          :before-upload="beforeAvatarUpload"
        >
          <el-avatar :src="imageUrl" shape="square" :size="100"/>
        </el-upload>
      </el-form-item>
    </el-form>
    <el-divider/>
    <el-form :model="usernameForm" status-icon :rules="usernameRule" ref="usernameForm"
             label-width="100px" class="demo-ruleForm"
             style="width: 70%;margin-top: 30px">
      <el-form-item label="新用户名" prop="username">
        <el-input v-model="usernameForm.username" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="submitUsername('usernameForm')">提交</el-button>
        <el-button @click="resetForm('usernameForm')">重置</el-button>
      </el-form-item>
    </el-form>
    <el-divider/>
    <el-form :model="ruleForm" status-icon :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm"
             style="width: 70%;margin-top: 30px">
      <el-form-item label="原密码" prop="oldPass">
        <el-input type="password" v-model="ruleForm.oldPass" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="新密码" prop="pass">
        <el-input type="password" v-model="ruleForm.pass" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="确认密码" prop="checkPass">
        <el-input type="password" v-model="ruleForm.checkPass" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="submitForm('ruleForm')">提交</el-button>
        <el-button @click="resetForm('ruleForm')">重置</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import {MessageBox} from "element-ui";
import request from "@/utils/request";
import store from "@/store";
import {getToken} from "@/utils/auth";
import {mapState} from "vuex";


export default {
  name: 'Infomation',
  data() {
    var validateUsername = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请输入新用户名'))
      } else {
        setTimeout(() => {
          if (this.usernameForm.username !== '') {
            this.$refs.usernameForm.validateField('username')
          }
          callback()
        }, 1000)
      }
    }
    var validatePass = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请输入密码'))
      } else if (value.length < 6) {
        callback(new Error('密码不可少于6位'))
      } else {
        if (this.ruleForm.checkPass !== '') {
          this.$refs.ruleForm.validateField('checkPass')
        }
        callback()
      }
    }
    var validatePass2 = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请再次输入密码'))
      } else if (value !== this.ruleForm.pass) {
        callback(new Error('两次输入密码不一致!'))
      } else {
        callback()
      }
    }
    return {
      baseUrl: this.$baseUrl + 'api/user/resetAvatar',
      imageUrl: '',
      usernameForm: {
        username: ''
      },
      ruleForm: {
        oldPass: '',
        pass: '',
        checkPass: ''
      },
      usernameRule: {
        username: [
          {validator: validateUsername, trigger: 'blur'}
        ],
      },
      rules: {
        oldPass: [
          {validator: validatePass, trigger: 'blur'}
        ],
        pass: [
          {validator: validatePass, trigger: 'blur'}
        ],
        checkPass: [
          {validator: validatePass2, trigger: 'blur'}
        ],
      }
    };
  },
  mounted() {
    this.getUserInfo()
  },
  methods: {
    getUserInfo() {
      request({
        method: 'get',
        url: 'api/user/getUserInfo',
      }).then((res) => {
        if (res.code === 1) {
          this.imageUrl = res.avatar
          this.usernameForm.username = name
        }
      })
    },
    getToken,
    handleAvatarSuccess(res, file) {
      this.imageUrl = URL.createObjectURL(file.raw)
      if (res.code === 1) {
        this.$notify({
          title: '成功',
          message: '修改成功',
          type: 'success'
        })
      } else {
        this.$notify({
          title: '失败',
          message: '修改失败',
          type: 'error'
        })
      }
    },
    beforeAvatarUpload(file) {
      const isJPG = file.type === 'image/jpeg';
      const isLt2M = file.size / 1024 / 1024 < 2;
    },
    submitUsername(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          request({
            method: 'get',
            url: 'api/user/resetUsername',
            params: {
              newUsername: this.usernameForm.username
            }
          }).then(res => {
            if (res.code === 1) {
              this.$notify({
                title: '成功',
                message: '修改成功',
                type: 'success'
              })
            } else if (res.code === -2) {
              this.$notify({
                title: '失败',
                message: '用户名已存在',
                type: 'error'
              })
            } else {
              this.$notify({
                title: '失败',
                message: '修改失败',
                type: 'error'
              })
            }
          })
        }
      })
    },
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          request({
            method: 'get',
            url: 'api/user/resetPassword',
            params: {
              oldPassword: this.ruleForm.oldPass,
              newPassword: this.ruleForm.pass
            }
          }).then(res => {
            if (res.code === 1) {
              MessageBox.confirm('密码已重置，请重新登录', '提示', {
                confirmButtonText: '重新登陆',
                cancelButtonText: '取消',
                type: 'warning'
              }).then(() => {
                location.reload()
                store.dispatch('user/resetToken').then(() => {
                  location.reload()
                })
              })
            } else if (res.code === -2) {
              this.$notify({
                title: '失败',
                message: '原密码错误',
                type: 'error'
              })
            } else if (res.code === 0) {
              this.$notify({
                title: '失败',
                message: '修改失败',
                type: 'error'
              })
            }
          })
        } else {
          return false
        }
      })
    },
    resetForm(formName) {
      this.$refs[formName].resetFields()
    }
  }
}
</script>

<style scoped>
.avatar-uploader .el-upload {
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
}

.avatar-uploader .el-upload:hover {
  border-color: #409EFF;
}

.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 178px;
  height: 178px;
  line-height: 178px;
  text-align: center;
}

.avatar {
  width: 100px;
  height: 100px;
  display: block;
}

</style>

