<template>
  <div class="app-container">

    <el-form ref="form" label-width="120px">
      <el-form-item label="上传智能合约">
        <div style="display: flex;">
          <el-input v-model="chaincodeName" placeholder="输入智能合约名称" style="width: 50%;margin-right: 10px"/>
          <el-upload
            ref="upload"
            :action="baseUrl"
            :data="{chaincodeName:chaincodeName}"
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

    <div v-if="list.length!==0">
      <el-table v-loading="loading" :data="list" stripe>
        <el-table-column prop="id" label="ID"></el-table-column>
        <el-table-column prop="chaincodeName" label="名称"></el-table-column>
        <el-table-column prop="version" label="版本"></el-table-column>
        <el-table-column #default="scope" label="状态">
          <div v-if="scope.row.chaincodeState===0">未安装</div>
          <div v-if="scope.row.chaincodeState===1" class="blue">已安装</div>
          <div v-if="scope.row.chaincodeState===2" class="green">已实例化</div>
        </el-table-column>
        <el-table-column prop="createTime" label="上传时间"></el-table-column>
        <el-table-column #default="scope" label="操作" width="200">
          <el-popconfirm title="确认下载？" confirm-button-text="是" cancel-button-text="否"
                         @onConfirm="download(scope.row.id)" style="margin-right: 10px">
            <template #reference>
              <el-button type="success" plain>下载</el-button>
            </template>
          </el-popconfirm>
          <el-popconfirm title="确认删除？" confirm-button-text="是" cancel-button-text="否"
                         @onConfirm="del(scope.row.id,scope.$index)">
            <template #reference>
              <el-button type="danger" plain>删除</el-button>
            </template>
          </el-popconfirm>
        </el-table-column>
      </el-table>
    </div>
    <div v-else>
      <p style="color: #99a9bf; display: flex; justify-content: center">暂无合约</p>
    </div>
    <el-divider/>

    <el-form ref="form" label-width="120px">
      <el-form-item label="安装合约">
        <div style="display: flex ;align-items: center">
          <el-input v-model="chaincodeNameInstall" placeholder="输入合约名称"
                    style="width: 50% ;margin-right: 10px"/>
          <el-input v-model="chaincodeVersionInstall" placeholder="输入合约版本号"
                    style="width: 20% ;margin-right: 10px"/>
          <el-button type="primary" plain @click="install">安装</el-button>
        </div>
      </el-form-item>

      <el-form-item label="实例化合约">
        <div style="display: flex ;align-items: center">
          <el-input v-model="chaincodeNameInstantiate" placeholder="输入合约名称"
                    style="width: 50% ;margin-right: 10px"/>
          <el-input v-model="chaincodeVersionInstantiate" placeholder="输入合约版本号"
                    style="width: 20% ;margin-right: 10px"/>
          <el-button type="primary" plain @click="instantiate">安装</el-button>
        </div>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import store from "@/store";
import request from "@/utils/request";

export default {
  name: 'Upload',
  data() {
    return {
      baseUrl: this.$baseUrl + 'api/chaincode/uploadChaincode',
      list: [],
      loading: false,
      installLoading: false,
      instantiateLoading: false,
      chaincodeName: '',
      chaincodeNameInstall: '',
      chaincodeVersionInstall: '',
      chaincodeNameInstantiate: '',
      chaincodeVersionInstantiate: ''
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
          method: 'get',
          url: '/api/chaincode/getChaincode'
        }).then((res) => {
          if (res.code === 1) {
            this.list = res.list
            this.loading = false
          }
        })
      }
    },
    download(chaincodeId) {
      this.loading = true
      if (store.getters.role === 2) {
        request({
          method: 'get',
          url: '/api/chaincode/downloadChaincode',
          params: {
            chaincodeId: chaincodeId
          }
        }).then((res) => {
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
      }
    },
    del(chaincodeId, id) {
      this.loading = true
      request({
        method: 'get',
        url: '/api/chaincode/deleteChaincode',
        params: {
          chaincodeId: chaincodeId
        }
      }).then((res) => {
        if (res.code === 1) {
          this.$notify({
            title: '成功',
            message: '删除成功',
            type: 'success'
          })
          this.list.splice(id, 1)
        } else {
          this.$notify({
            title: '失败',
            message: res.msg,
            type: 'error'
          })
        }
      })
      this.loading = false
    },
    handleChange(file) {
      if (this.list.length > 1) {
        const index = this.list.indexOf(file)
        this.list.splice(0, index)
      }
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
    async submitUpload() {
      const uploadComponent = this.$refs.upload
      await uploadComponent.submit()
      this.search()
    },
    install() {
      this.installLoading = true
      request({
        method: 'get',
        url: '/api/chaincode/installChaincode',
        params: {
          chaincodeName: this.chaincodeNameInstall,
          chaincodeVersion: this.chaincodeVersionInstall
        }
      }).then((res) => {
        if (res.code === 1) {
          this.$notify({
            title: '成功',
            message: '安装成功',
            type: 'success'
          })
        } else {
          this.$notify({
            title: '失败',
            message: res.msg,
            type: 'error'
          })
        }
      })
      this.search()
      this.installLoading = false
    },
    instantiate() {
      this.instantiateLoading = true
      request({
        method: 'get',
        url: '/api/chaincode/instantiateChaincode',
        params: {
          chaincodeName: this.chaincodeNameInstall,
          chaincodeVersion: this.chaincodeVersionInstall
        }
      }).then((res) => {
        if (res.code === 1) {
          this.$notify({
            title: '成功',
            message: '实例化成功',
            type: 'success'
          })
        } else {
          this.$notify({
            title: '失败',
            message: res.msg,
            type: 'error'
          })
        }
      })
      this.search()
      this.instantiateLoading = false
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
