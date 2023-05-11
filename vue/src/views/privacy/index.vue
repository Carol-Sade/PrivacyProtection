<template>
  <div class="app-container">
    <el-form ref="form" label-width="120px">
      <el-form-item label="上传路径">
        <div style="display: flex;">
          <el-input v-model="uploadPath" placeholder="输入上传路径" style="width: 50%;margin-right: 10px"/>
          <el-upload
            ref="upload"
            action="https://localhost:444/api/upload/upload"
            :data="{path:uploadPath}"
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
  </div>
</template>
<script>
export default {
  name: 'Privacy'
}
</script>

<style scoped>

</style>
