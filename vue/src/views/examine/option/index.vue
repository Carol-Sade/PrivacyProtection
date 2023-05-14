<template>
  <div class="app-container">
    <div v-if="list.length!==0">
      <el-table v-loading="loading" :data="list" stripe class="tableList">
        <el-table-column prop="id" label="ID" width="50"/>
        <el-table-column label="文件操作者" width="150" #default="scope">
          <div style="display: flex;justify-content: center;align-items: center">
            <el-avatar :src="scope.row.avatar"></el-avatar>
            <span style="margin-left: 5px;">{{ scope.row.username }}</span>
          </div>
        </el-table-column>
        <el-table-column prop="fileName" label="文件名"/>

        <el-table-column prop="optionName" label="操作方式" width="160"/>
        <el-table-column prop="createTime" label="操作时间" width="160"/>
        <el-table-column #default="scope" label="操作" width="200">
          <el-popconfirm title="确认删除？" confirm-button-text="是" cancel-button-text="否"
                         @onConfirm="deleteOption(scope.row.id,scope.$index)">
            <template #reference>
              <el-button type="danger" plain>删除记录</el-button>
            </template>
          </el-popconfirm>
        </el-table-column>
      </el-table>
      <el-backtop></el-backtop>
    </div>
    <div v-else>
      <p style="color: #99a9bf; display: flex; justify-content: center">暂无操作</p>
    </div>
  </div>
</template>

<script>
import request from "@/utils/request";

export default {
  name: "index",
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
        url: 'api/fileOption/getExamineOptions',
        method: 'get'
      }).then((res) => {
        this.list = res.list
        this.loading = false
      })
    },
    deleteOption(optionId, index) {
      request({
        method: 'get',
        url: 'api/fileOption/deleteOption',
        params: {
          optionId: optionId
        }
      }).then((res) => {
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
    }
  }
}
</script>

<style scoped>

</style>
