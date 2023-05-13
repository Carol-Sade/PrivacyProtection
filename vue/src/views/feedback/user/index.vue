<template>
  <div class="app-container">
    <el-form ref="form" label-width="120px">
      <el-form-item label="您的反馈:">
        <div v-loading="loading">
          <el-input v-model="feedback" placeholder="输入反馈信息" autosize type="textarea"
                    style="height: 20%; width: 50% ;margin-right: 10px"/>
          <el-button type="primary" plain @click="sendFeedback">发送</el-button>
        </div>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import request from "@/utils/request";

export default {
  name: 'Index',
  data() {
    return {
      loading: false,
      feedback: ''
    }
  },
  methods: {
    sendFeedback() {
      this.loading = true
      request({
        method: 'get',
        url: 'api/feedback/feedback',
        params: {
          content: this.feedback
        }
      }).then((res) => {
        if (res.code === 1) {
          this.$notify({
            title: '成功',
            message: '发送成功',
            type: 'success'
          })
          this.serverPath = res.list
        } else {
          this.$notify({
            title: '发送失败',
            message: res.msg,
            type: 'error'
          })
        }
        this.loading = false
      })
    }
  }
}
</script>

<style scoped>

</style>
