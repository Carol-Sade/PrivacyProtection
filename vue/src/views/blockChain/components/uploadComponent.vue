<template>
  <div>
    <el-form ref="form" label-width="120px">
      <el-form-item label="搜索路径">
        <div style="display: flex">
          <el-input v-model="searchPath" placeholder="输入搜索路径" style="width: 50% ;margin-right: 10px"/>
          <el-button type="primary" plain @click="search">搜索</el-button>
          <el-button type="primary" plain @click="clean">清空</el-button>
        </div>
      </el-form-item>
      <el-form-item style="margin: 0">
        <label @click="changePath">区块链默认路径：{{ defaultPath }}</label>
      </el-form-item>

    </el-form>
    <div v-if="serverPath!==null">
      <el-table v-loading="loading" :data="serverPath" stripe :border="true">
        <el-table-column prop="type" label="类型" #default="scope">
          {{ scope.row.type === 'directory' ? '文件夹' : '文件' }}
        </el-table-column>
        <el-table-column prop="fileName" label="名称"></el-table-column>
        <el-table-column prop="timeStamp" label="更新时间"></el-table-column>
        <el-table-column #default="scope" label="操作" width="200">
          <el-popconfirm title="确认下载？" confirm-button-text="是" cancel-button-text="否"
                         @onConfirm="download(scope.row.fileName)" style="margin-right: 10px">
            <template #reference>
              <el-button type="success" plain>下载</el-button>
            </template>
          </el-popconfirm>
          <el-popconfirm title="确认删除？" confirm-button-text="是" cancel-button-text="否"
                         @onConfirm="del(scope.row.fileName,scope.$index)">
            <template #reference>
              <el-button type="danger" plain>删除</el-button>
            </template>
          </el-popconfirm>
        </el-table-column>
      </el-table>
      <el-divider/>
    </div>
  </div>
</template>

<script>
import store from "@/store";
import request from "@/utils/request";

export default {
  name: 'UploadComponent',
  data() {
    return {
      searchPath: '/opt',
      serverPath: [],
      uploadPath: '',
      defaultPath: '/opt/gopath/src/github.com/hyperledger/fabric-samples/chaincode',
      fileList: [],
      loading: false
    }
  },
  mounted() {
    this.search()
  },
  methods: {
    search() {
      this.loading = true
      if (store.getters.role === 2) {
        request({
          method: 'post',
          url: '/api/upload/getPath',
          params: {
            path: this.searchPath
          }
        }).then((res) => {
          if (res.code === 1) {
            this.$notify({
              title: '成功',
              message: '搜索成功',
              type: 'success'
            })
            this.serverPath = res.list
          } else {
            this.$notify({
              title: '搜索失败',
              message: res.msg,
              type: 'error'
            })
          }
          this.loading = false
        })
      }
    },
    changePath() {
      this.searchPath = this.defaultPath
      this.search()
    },
    clean() {
      this.searchPath = ''
      this.serverPath = null
    },
    download(fileName) {
      this.loading = true
      if (store.getters.role === 2) {
        request({
          method: 'post',
          url: '/api/upload/download',
          params: {
            path: this.searchPath + '/' + fileName
          }
        }).then((res) => {
          console.log(res)
          if (res.code === 1) {
            try {
              const link = document.createElement('a')
              link.href = res.file
              // 添加 download 属性并设置文件名
              link.download = 'file'
              // 将这个链接添加到页面
              document.body.appendChild(link)
              // 模拟鼠标点击事件
              link.click()
              // 移除临时元素以保持页面干净
              document.body.removeChild(link)
              this.$notify({
                title: '成功',
                message: '下载成功',
                type: 'success'
              })
            } catch (e) {
              this.$notify({
                title: '下载失败',
                message: e,
                type: 'error'
              })
            }
          } else {
            this.$notify({
              title: '下载失败',
              message: res.msg,
              type: 'error'
            })
          }
        })
        this.loading = false
      }
    },
    del(fileName, id) {
      this.loading = true
      request({
        method: 'post',
        url: '/api/upload/delete',
        params: {
          path: this.searchPath + '/' + fileName
        }
      }).then((res) => {
        if (res.code === 1) {
          this.$notify({
            title: '成功',
            message: '删除成功',
            type: 'success'
          })
          this.serverPath.splice(id, 1)
        } else {
          this.$notify({
            title: '失败',
            message: res.msg,
            type: 'error'
          })
        }
      })
      this.loading = false
    }
  }
}
</script>

<style scoped>

</style>
