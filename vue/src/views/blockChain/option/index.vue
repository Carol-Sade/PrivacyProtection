<template>
  <div class="app-container">
    <UploadComponent></UploadComponent>

    <el-form ref="form" label-width="120px">
      <el-form-item label="安装实例化合约">
        <div style="display: flex">
          <el-input v-model="installPath" placeholder="输入需要操作的合约地址" style="width: 50% ;margin-right: 10px"/>
          <el-button type="primary" plain @click="install">安装</el-button>
        </div>
      </el-form-item>

    </el-form>
  </div>
</template>

<script>
import UploadComponent from "@/views/blockChain/components/uploadComponent.vue";

export default {
  name: 'Option',
  components: {
    UploadComponent
  },
  data() {
    return {
      installPath: ''
    }
  },
  methods: {
    install() {
      console.log(this.installPath)
    }
  }
}
</script>

<style scoped>

</style>
