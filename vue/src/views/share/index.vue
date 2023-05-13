<template>
  <div class="app-container">
    <el-table v-loading="loading" :data="list" stripe :border="true" class="tableList">
      <el-table-column prop="id" label="ID" width="50"/>
      <el-table-column label="文件所有者" width="150" #default="scope">
        <div style="display: flex;justify-content: center;align-items: center">
          <el-avatar :src="scope.row.avatar"></el-avatar>
          <span style="margin-left: 5px;">{{ scope.row.username }}</span>
        </div>
      </el-table-column>
      <el-table-column prop="fileName" label="文件名"/>
      <el-table-column prop="fileDescribe" label="文件描述"/>

      <el-table-column #default="scope" label="文件类型">
        <div v-if="scope.row.type===1">文档</div>
        <div v-else-if="scope.row.type===2">图片</div>
        <div v-else-if="scope.row.type===3">音乐</div>
        <div v-else-if="scope.row.type===4">视频</div>
      </el-table-column>
      <el-table-column prop="createTime" label="收藏时间" width="160"/>

      <el-table-column #default="scope" label="操作" width="200">
        <el-popconfirm title="确认下载？" confirm-button-text="是" cancel-button-text="否"
                       @onConfirm="download(scope.row.id,scope.$index)">
          <template #reference>
            <el-button type="primary" plain>下载</el-button>
          </template>
        </el-popconfirm>
        <el-popconfirm title="确认收藏？" confirm-button-text="是" cancel-button-text="否"
                       @onConfirm="sendCollect(scope.row.id,scope.$index)">
          <template #reference>
            <el-button type="primary" plain>收藏</el-button>
          </template>
        </el-popconfirm>
      </el-table-column>
    </el-table>
    <el-backtop></el-backtop>
  </div>

</template>

<script>
import request from "@/utils/request";

export default {
  name: 'Share',
  data() {
    return {
      list: [],
      loading: false
    }
  },
  mounted() {
    this.getList()
  },
  methods: {
    getList() {
      this.loading = true
      request({
        url: 'api/file/getShare',
        method: 'get'
      }).then((res) => {
        this.list = res.list
        this.loading = false
      })
    },
    download(fileId, index) {

    },
    sendCollect(fileId, index) {
      request({
        method: 'get',
        url: 'api/userCollect/collect',
        params: {
          fileId: fileId
        }
      }).then((res) => {
        console.log(res)
        if (res.code === 1) {
          this.$notify({
            title: '成功',
            message: '收藏成功',
            type: 'success'
          })
        } else if (res.code === -2) {
          this.$notify({
            title: '提示',
            message: '已收藏',
            type: 'warning'
          })
        } else {
          this.$notify({
            title: '失败',
            message: '收藏失败',
            type: 'error'
          })
        }
      })
    }
  }
}
</script>

<style scoped>

button {
  margin: 2px;
}

</style>
