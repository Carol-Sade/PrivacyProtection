<template>
  <div>
    <el-table v-loading="loading" :data="list" stripe :border="true" class="tableList">
      <el-table-column prop="id" label="ID" width="50"/>
      <el-table-column label="用户" width="200" #default="scope">
        <div style="display: flex;justify-content: center;align-items: center">
          <el-avatar :src="scope.row.avatar"></el-avatar>
          <span style="margin-left: 5px;">{{ scope.row.username }}</span>
        </div>
      </el-table-column>
      <el-table-column prop="content" label="内容"/>
      <el-table-column prop="time" label="发布时间" width="200"/>
      <el-table-column #default="scope" label="操作" width="200">
        <el-popconfirm title="确认删除？" confirm-button-text="是" cancel-button-text="否"
                       @onConfirm="del(scope.row.id,scope.$index)">
          <template #reference>
            <el-button type="danger" plain>删除</el-button>
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
  name: 'Index',
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
        url: 'api/feedback/getFeedbacks',
        method: 'get'
      }).then((res) => {
        this.list = res.list
        this.loading = false
      })
    },
    del(feedbackId, index) {
      request({
        method: 'get',
        url: 'api/feedback/deleteFeedback',
        params: {
          feedbackId: feedbackId
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
