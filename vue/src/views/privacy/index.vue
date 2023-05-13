<template>
  <div class="app-container">
    <el-form ref="form" label-width="120px" v-loading="uploadLoading">
      <el-form-item label="上传文件">
        <div style="display: flex;align-items: center">
          <el-input v-model="fileDescribe" placeholder="输入文件描述" style="width: 50%;margin-right: 2px"/>
          <el-select v-model="value" placeholder="选择文件类型">
            <el-option
              v-for="item in options"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            />
          </el-select>
          <el-upload
            ref="upload"
            action="https://localhost:444/api/file/uploadFile"
            :headers={token:token}
            :data="{describe:fileDescribe,type:value}"
            :limit="1"
            :on-success="uploadSuccess"
            :on-error="uploadError"
            :on-change="handleChange"
            :auto-upload="false"
          >
            <template #trigger>
              <el-button type="primary" plain>选择文件</el-button>
            </template>
            <el-button type="success" plain @click="submitUpload">上传</el-button>
          </el-upload>
        </div>
        <label>限制上传1个文件，需再次上传的，在上传完毕后删除列表，再重新选择</label>
      </el-form-item>
    </el-form>
    <el-divider/>
    <div v-if="list.length!==0">
      <el-table v-loading="loading" :data="list" stripe class="tableList">
        <el-table-column prop="id" label="ID" width="50"/>
        <el-table-column prop="fileName" label="文件名"/>

        <el-table-column #default="scope" label="文件类型" width="80">
          <div v-if="scope.row.fileType===1">文档</div>
          <div v-else-if="scope.row.fileType===2">图片</div>
          <div v-else-if="scope.row.fileType===3">音乐</div>
          <div v-else-if="scope.row.fileType===4">视频</div>
        </el-table-column>

        <el-table-column #default="scope" label="文件状态" width="80">
          <div v-if="scope.row.fileState===0" class="blue">未共享</div>
          <div v-else-if="scope.row.fileState===1" class="green">已共享</div>
          <div v-else-if="scope.row.fileState===-1" class="red">审核删除</div>
          <div v-else-if="scope.row.fileState===-2">用户删除</div>
        </el-table-column>
        <el-table-column prop="createTime" label="上传时间" width="160"/>

        <el-table-column #default="scope" label="操作" width="280">

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

          <div v-if="scope.row.fileState===-1">
            <el-popconfirm title="确认删除？" confirm-button-text="是" cancel-button-text="否"
                           @onConfirm="delAbsolutely(scope.row.id,scope.$index)">
              <template #reference>
                <el-button type="danger" plain>永久删除</el-button>
              </template>
            </el-popconfirm>
          </div>

          <div v-if="scope.row.fileState===-2">
            <el-popconfirm title="确认恢复？" confirm-button-text="是" cancel-button-text="否"
                           @onConfirm="restore(scope.row.id,scope.$index)">
              <template #reference>
                <el-button type="success" plain>恢复</el-button>
              </template>
            </el-popconfirm>
            <el-popconfirm title="确认删除？" confirm-button-text="是" cancel-button-text="否"
                           @onConfirm="delAbsolutely(scope.row.id,scope.$index)">
              <template #reference>
                <el-button type="danger" plain>永久删除</el-button>
              </template>
            </el-popconfirm>
          </div>
        </el-table-column>

        <el-table-column type="expand" fixed="right">
          <template #default="scope">
            <p>文件描述：{{ scope.row.fileDescribe }}</p>

            <el-collapse @change="getComments(scope.row.id,scope.$index)">
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
                    <el-table-column #default="sc" label="操作">
                      <el-popconfirm title="确认删除？" confirm-button-text="是" cancel-button-text="否"
                                     @onConfirm="commentDel(sc.row.id,scope.$index, sc.$index)">
                        <template #reference>
                          <el-button type="danger">删除评论</el-button>
                        </template>
                      </el-popconfirm>
                    </el-table-column>
                  </el-table>
                </div>
                <div v-else style="text-align: center;margin-top: 10px">暂无评论</div>
              </el-collapse-item>
            </el-collapse>

            <el-form>
              <el-form-item label="发送评论">
                <div style="display: flex;align-items: center">
                  <el-input v-model="commentContent" placeholder="输入评论"></el-input>
                  <el-button @click="comment(scope.row.id)">评论</el-button>
                </div>
              </el-form-item>
            </el-form>

          </template>
        </el-table-column>
      </el-table>
    </div>
    <div v-else>
      <p style="color: #99a9bf; display: flex; justify-content: center">还未上传文件</p>
    </div>

    <el-backtop></el-backtop>
  </div>
</template>
<script>
import request from "@/utils/request";
import {getToken} from "@/utils/auth";
import store from "@/store";

export default {
  name: 'Privacy',
  data() {
    return {
      list: [],
      uploadLoading: false,
      loading: false,
      commentLoading: false,
      commentContent: '',
      fileDescribe: '',
      value: '',
      fileList: [],
      token: getToken(),
      options: [
        {
          value: 1,
          label: '文档'
        },
        {
          value: 2,
          label: '图片'
        },
        {
          value: 3,
          label: '音乐'
        },
        {
          value: 4,
          label: '视频'
        },
        {
          value: 5,
          label: '其他'
        },
      ]
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
        this.loading = false
      })
    },
    download(fileId) {
      this.loading = true
      request({
        method: 'get',
        url: '/api/file/downloadFile',
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
    restore(fileId, index) {
      request({
        method: 'get',
        url: 'api/file/restore',
        params: {
          fileId: fileId
        }
      }).then((res) => {
        console.log(res)
        if (res.code === 1) {
          this.$notify({
            title: '成功',
            message: '恢复成功',
            type: 'success'
          })
          this.list[index].fileState = 0
        } else {
          this.$notify({
            title: '失败',
            message: '恢复失败',
            type: 'error'
          })
        }
      })
    },
    delAbsolutely(fileId, index) {
      request({
        method: 'get',
        url: 'api/file/deleteAbsolutely',
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
          this.list.splice(index, 1)
        } else {
          this.$notify({
            title: '失败',
            message: '删除失败',
            type: 'error'
          })
        }
      })
    },
    uploadSuccess(res) {
      if (res.code === 1) {
        this.$notify({
          title: '成功',
          message: '上传成功',
          type: 'success'
        })
      } else {
        this.$notify({
          title: '失败',
          message: res.msg,
          type: 'error'
        })
      }
    },
    uploadError(res) {
      this.$notify({
        title: '失败',
        message: res.msg,
        type: 'error'
      })
    },
    handleChange(file) {
      if (this.fileList.length > 1) {
        const index = this.fileList.indexOf(file)
        this.fileList.splice(0, index)
      }
    },
    async submitUpload() {
      this.uploadLoading = true
      const uploadComponent = this.$refs.upload
      await uploadComponent.submit()
      this.uploadLoading = false
      await this.getList()
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
    commentDel(commentId, fileId, index) {
      request({
        method: 'get',
        url: 'api/fileComment/deleteComment',
        params: {
          commentId: commentId
        }
      }).then((res) => {
        if (res.code === 1) {
          this.$notify({
            title: '成功',
            message: '删除成功',
            type: 'success'
          })
          this.list[fileId].comments.splice(index, 1)
        } else {
          this.$notify({
            title: '失败',
            message: '删除失败',
            type: 'error'
          })
        }
      })
    }
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
