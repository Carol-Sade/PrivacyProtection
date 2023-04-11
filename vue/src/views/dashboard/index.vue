<template>
  <div class="dashboard-container">
<!--    <div class="dashboard-text">name: {{ name }}</div>-->
    <Title></Title>
  </div>
</template>

<script>
import { mapGetters } from 'vuex'
import Title from "@/views/dashboard/components/Title";

export default {
  name: 'Dashboard',
  components:{
    Title
  },
  computed: {
    ...mapGetters([
      'name'
    ])
  }
}
</script>

<style lang="scss" scoped>
.dashboard {
  &-container {
    margin: 30px;
  }
  &-text {
    font-size: 30px;
    line-height: 46px;
  }
}
</style>
