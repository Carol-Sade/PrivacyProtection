<template>
  <div class="app-container">
    <el-form ref="form" label-width="120px">
      <el-form-item label="上传文件">
        <div style="display: flex;">
          <el-upload
            ref="upload"
            action="https://localhost:444/api/upload/upload"
            :data="{}"
            :limit="1"
            :on-success="uploadSuccess"
            :on-error="uploadError"
            :on-change="handleChange"
            :auto-upload="false"
          >
            <template #trigger>
              <el-button type="primary" plain style="margin-right: 10px;">选择文件</el-button>
            </template>
            <el-button type="success" plain @click="submitUpload">上传</el-button>
          </el-upload>
        </div>
        <label>限制上传1个文件，需再次上传的，在上传完毕后删除列表，再重新选择</label>
      </el-form-item>
    </el-form>
    <el-divider/>
    <el-table v-loading="loading" :data="list" stripe :border="true" class="tableList">
      <el-table-column prop="id" label="ID" width="50"/>
      <el-table-column prop="fileName" label="文件名"/>
      <el-table-column prop="fileDescribe" label="文件描述"/>

      <el-table-column #default="scope" label="文件类型">
        <div v-if="scope.row.fileType===1">文档</div>
        <div v-else-if="scope.row.fileType===2">图片</div>
        <div v-else-if="scope.row.fileType===3">音乐</div>
        <div v-else-if="scope.row.fileType===4">视频</div>
      </el-table-column>

      <el-table-column #default="scope" label="文件状态">
        <div v-if="scope.row.fileState===0" class="blue">未共享</div>
        <div v-else-if="scope.row.fileState===1" class="green">已共享</div>
        <div v-else-if="scope.row.fileState===-1" class="red">审核删除</div>
        <div v-else-if="scope.row.fileState===-2">用户删除</div>
      </el-table-column>
      <el-table-column prop="createTime" label="收藏时间" width="160"/>

      <el-table-column #default="scope" label="操作" width="200">

        <div v-if="scope.row.fileState===0">
          <el-popconfirm title="确认下载？" confirm-button-text="是" cancel-button-text="否"
                         @onConfirm="download(scope.row.id)">
            <template #reference>
              <el-button type="primary" plain>下载</el-button>
            </template>
          </el-popconfirm>

          <el-popconfirm title="确认共享？" confirm-button-text="是" cancel-button-text="否"
                         @onConfirm="share(scope.row.id,scope.$index)">
            <template #reference>
              <el-button type="success" plain>共享</el-button>
            </template>
          </el-popconfirm>
          <el-popconfirm title="确认删除？" confirm-button-text="是" cancel-button-text="否"
                         @onConfirm="del(scope.row.id,scope.$index)">
            <template #reference>
              <el-button type="danger" plain>删除</el-button>
            </template>
          </el-popconfirm>
        </div>

        <div v-if="scope.row.fileState===1">
          <el-popconfirm title="确认下载？" confirm-button-text="是" cancel-button-text="否"
                         @onConfirm="download(scope.row.id,scope.$index)">
            <template #reference>
              <el-button type="primary" plain>下载</el-button>
            </template>
          </el-popconfirm>

          <el-popconfirm title="确认取消？" confirm-button-text="是" cancel-button-text="否"
                         @onConfirm="cancelShare(scope.row.id,scope.$index)">
            <template #reference>
              <el-button type="warning" plain>取消共享</el-button>
            </template>
          </el-popconfirm>
          <el-popconfirm title="确认删除？" confirm-button-text="是" cancel-button-text="否"
                         @onConfirm="del(scope.row.id,scope.$index)">
            <template #reference>
              <el-button type="danger" plain>删除</el-button>
            </template>
          </el-popconfirm>
        </div>

      </el-table-column>
    </el-table>
    <el-backtop></el-backtop>
  </div>
</template>
<script>
import request from "@/utils/request";

export default {
  name: 'Privacy',
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
        url: 'api/file/getMyFiles',
        method: 'get'
      }).then((res) => {
        this.list = res.list
        console.log(res)
        this.loading = false
      })
    },
    download(fileId) {

    },
    share(fileId, index) {
      request({
        method: 'get',
        url: 'api/file/share',
        params: {
          fileId: fileId
        }
      }).then((res) => {
        console.log(res)
        if (res.code === 1) {
          this.$notify({
            title: '成功',
            message: '共享成功',
            type: 'success'
          })
          this.list[index].fileState = 1
        } else {
          this.$notify({
            title: '失败',
            message: '共享失败',
            type: 'error'
          })
        }
      })
    },
    cancelShare(fileId, index) {
      request({
        method: 'get',
        url: 'api/file/cancelShare',
        params: {
          fileId: fileId
        }
      }).then((res) => {
        console.log(res)
        if (res.code === 1) {
          this.$notify({
            title: '成功',
            message: '取消成功',
            type: 'success'
          })
          this.list[index].fileState = 0
        } else {
          this.$notify({
            title: '失败',
            message: '取消失败',
            type: 'error'
          })
        }
      })
    },
    del(fileId, index) {
      request({
        method: 'get',
        url: 'api/file/deleteFile',
        params: {
          fileId: fileId
        }
      }).then((res) => {
        console.log(res)
        if (res.code === 1) {
          this.$notify({
            title: '成功',
            message: '删除成功',
            type: 'success'
          })
          this.list[index].fileState = -2
        } else {
          this.$notify({
            title: '失败',
            message: '删除失败',
            type: 'error'
          })
        }
      })
    },
    uploadSuccess() {

    },
    uploadError() {

    },
    handleChange() {

    },
    submitUpload() {

    },
  }
}
</script>

<style scoped>

.green {
  color: #67C23A;
}

.yellow {
  color: #E6A23C;
}

.red {
  color: #F56C6C;
}

.blue {
  color: #409EFF;
}

button {
  margin: 2px;
}

</style>
