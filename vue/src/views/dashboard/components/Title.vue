<template>
  <div id="chart" :style="{width: '100%', height: '200px'}" style="float: left"></div>
</template>

<script>
import * as echarts from "echarts";

export default {
  name: "Title",
  data() {
    return {
      chart: '',
    }
  },
  mounted() {
    this.initChart()
  },
  methods: {
    initChart() {
      this.chart = echarts.init(document.getElementById('chart'))
      var option = {
        graphic: {
          elements: [
            {
              type: 'text',
              top: 'center',
              style: {
                text: '"基于区块链的"\n\n隐私保护数据共享系统',
                fontSize: 40,
                fontFamily: 'monospace',
                fontWeight: 'lighter',
                lineDash: [0, 200],
                lineDashOffset: 0,
                fill: 'transparent',
                stroke: '#000',
                lineWidth: 1
              },
              keyframeAnimation: {
                duration: 3000,
                loop: false,
                keyframes: [
                  {
                    percent: 0.7,
                    style: {
                      fill: 'transparent',
                      lineDashOffset: 200,
                      lineDash: [200, 0]
                    }
                  },
                  {
                    // Stop for a while.
                    percent: 0.8,
                    style: {
                      fill: 'transparent'
                    }
                  },
                  {
                    percent: 1,
                    style: {
                      fill: 'black'
                    }
                  }
                ]
              }
            }
          ]
        }
      }
      this.chart.setOption(option)
      window.addEventListener("resize", () => {
        this.chart.resize();
      });
    }
  }
}
</script>

<style scoped>

</style>
