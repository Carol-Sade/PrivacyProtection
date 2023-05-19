<template>
  <div class="app-container">
    <div v-if="list.length!==0">
      <el-table v-loading="loading" :data="list" stripe class="tableList">
        <el-table-column prop="fileId" label="ID" width="50"/>
        <el-table-column label="文件所有者" width="150" #default="scope">
          <div style="display: flex;justify-content: center;align-items: center">
            <el-avatar :src="scope.row.avatar"></el-avatar>
            <span style="margin-left: 5px;">{{ scope.row.username }}</span>
          </div>
        </el-table-column>
        <el-table-column prop="fileName" label="文件名"/>

        <el-table-column #default="scope" label="文件类型" width="80">
          <div v-if="scope.row.fileType===1">文档</div>
          <div v-else-if="scope.row.fileType===2">图片</div>
          <div v-else-if="scope.row.fileType===3">音乐</div>
          <div v-else-if="scope.row.fileType===4">视频</div>
          <div v-else-if="scope.row.fileType===5">其他</div>
        </el-table-column>

        <el-table-column prop="createTime" label="收藏时间" width="160"/>
        <el-table-column #default="scope" label="操作" width="200">
          <el-popconfirm title="确认下载？" confirm-button-text="是" cancel-button-text="否"
                         @onConfirm="download(scope.row.fileId)">
            <template #reference>
              <el-button type="primary" plain>下载</el-button>
            </template>
          </el-popconfirm>
          <el-popconfirm title="确认取消？" confirm-button-text="是" cancel-button-text="否"
                         @onConfirm="cancelCollect(scope.row.id,scope.$index)">
            <template #reference>
              <el-button type="warning" plain>取消收藏</el-button>
            </template>
          </el-popconfirm>
        </el-table-column>
        <el-table-column type="expand" fixed="right">
          <template #default="scope">
            <p>文件描述：{{ scope.row.fileDescribe }}</p>

            <el-collapse @change="getComments(scope.row.fileId,scope.$index)">
              <el-collapse-item title="评论列表" name="1" v-loading="commentLoading">
                <div v-if="scope.row.comments.length!==0">
                  <el-table :data="scope.row.comments">
                    <el-table-column label="文件所有者" width="150" #default="scope">
                      <div style="display: flex;justify-content: center;align-items: center">
                        <el-avatar :src="scope.row.avatar"></el-avatar>
                        <span style="margin-left: 5px;">{{ scope.row.username }}</span>
                      </div>
                    </el-table-column>
                    <el-table-column prop="content" label="评论内容"/>
                    <el-table-column prop="createTime" label="评论时间"/>
                  </el-table>
                </div>
                <div v-else style="text-align: center;margin-top: 10px">暂无评论</div>
              </el-collapse-item>
            </el-collapse>

            <el-form>
              <el-form-item label="发送评论">
                <div style="display: flex;align-items: center">
                  <el-input v-model="commentContent" placeholder="输入评论"></el-input>
                  <el-button @click="comment(scope.row.fileId)">评论</el-button>
                </div>
              </el-form-item>
            </el-form>

          </template>
        </el-table-column>

      </el-table>

      <el-backtop></el-backtop>
    </div>
    <div v-else>
      <p style="color: #99a9bf; display: flex; justify-content: center">还未收藏文件</p>
    </div>
  </div>
</template>

<script>
import request from "@/utils/request";

export default {
  name: 'Index',
  data() {
    return {
      list: [],
      loading: false,
      commentLoading: false,
      commentContent: ''
    }
  },
  mounted() {
    this.getList()
  },
  methods: {
    getList() {
      this.loading = true
      request({
        url: 'api/userCollect/getUserCollections',
        method: 'get'
      }).then((res) => {
        this.list = res.list
        this.loading = false
      })
    },
    download(fileId) {
      this.loading = true
      request({
        method: 'get',
        url: '/api/file/downloadUserFile',
        params: {
          fileId: fileId
        }
      }).then((res) => {
        console.log(res)
        if (res.code === 1) {
          try {
            const link = document.createElement('a')
            link.href = res.url
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
        } else if (res.code === -2) {
          this.$notify({
            title: '下载失败',
            message: '文件校验出错',
            type: 'error'
          })
        } else {
          this.$notify({
            title: '下载失败',
            message: res.msg,
            type: 'error'
          })
        }
      })
      this.loading = false
    },
    cancelCollect(collectId, index) {
      request({
        method: 'get',
        url: 'api/userCollect/cancelCollect',
        params: {
          collectId: collectId
        }
      }).then((res) => {
        console.log(res)
        if (res.code === 1) {
          this.$notify({
            title: '成功',
            message: '取消成功',
            type: 'success'
          })
          this.list.splice(index, 1)
        } else {
          this.$notify({
            title: '失败',
            message: '取消失败',
            type: 'error'
          })
        }
      })
    },
    getComments(fileId, index) {
      if (this.list[index].comments !== []) {
        this.commentLoading = true
        request({
          method: 'get',
          url: 'api/fileComment/getFileComments',
          params: {
            fileId: fileId
          }
        }).then((res) => {
          this.commentLoading = false
          this.list[index].comments = res.list
        })
      }
    },
    comment(fileId) {
      request({
        method: 'get',
        url: 'api/fileComment/comment',
        params: {
          fileId: fileId,
          content: this.commentContent
        }
      }).then((res) => {
        console.log(res)
        if (res.code === 1) {
          this.$notify({
            title: '成功',
            message: '评论成功',
            type: 'success'
          })
        } else {
          this.$notify({
            title: '失败',
            message: '评论失败',
            type: 'error'
          })
        }
        this.commentContent = null
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
