<template>
    <div class="content">
      <div ref="charts" style="width: 1400px; height: 800px"></div>
    </div>
  </template>
    
    
  <script>
  import * as echarts from "echarts";
  import USA from "@/assets/map/USA.json"
  export default {
  created () {
      this.$nextTick(() => {
      this.initCharts();
      })
  },
  methods: {
      initCharts() {
      const charts = echarts.init(this.$refs["charts"]);
      echarts.registerMap('USA', USA, {
        '阿拉斯加': {
        left: -131,
        top: 25,
        width: 15
        },
        '夏威夷': {
        left: -110,
        top: 28,
        width: 5
        },
        '波多黎各': {
        left: -76,
        top: 26,
        width: 2
        }
      });
      
      const option = {
        title: {
            text: '2012年美国人口',
            subtext: '数据来源: www.census.gov',
            sublink: 'http://www.census.gov/popest/data/datasets.html',
            left: 'left'
        },
        tooltip: {
            trigger: 'item',
            showDelay: 0,
            transitionDuration: 0.2,
        },
        visualMap: {
            left: 'right',
            top: 'middle',
            min: 500000,
            max: 38000000,
            inRange: {
                color: [
                '#313695',
                '#4575b4',
                '#74add1',
                '#abd9e9',
                '#e0f3f8',
                '#ffffbf',
                '#fee090',
                '#fdae61',
                '#f46d43',
                '#d73027',
                '#a50026'
                ]
            },
            text: ['High', 'Low'],
            calculable: true
        },
        toolbox: {
        show: true,
        //orient: 'vertical',
        left: 'right',
        top: 'top',
        feature: {
            dataView: { readOnly: false },
            restore: {},
            saveAsImage: {}
        }
        },
        series: [
        {
            name: '各州人口',
            type: 'map',
            roam: true,
            map: 'USA',
            emphasis: {
            label: {
                show: true
            }
            },
            data: [
            { name: '亚拉巴马', value: 4822023 },
            { name: '阿拉斯加', value: 731449 },
            { name: '亚利桑那', value: 6553255 },
            { name: '阿肯色', value: 2949131 },
            { name: '加利福尼亚', value: 38041430 },
            { name: '科罗拉多', value: 5187582 },
            { name: '康涅狄格', value: 3590347 },
            { name: '特拉华', value: 917092 },
            { name: '哥伦比亚特区', value: 632323 },
            { name: '佛罗里达', value: 19317568 },
            { name: '佐治亚', value: 9919945 },
            { name: '夏威夷', value: 1392313 },
            { name: '爱达华', value: 1595728 },
            { name: '伊利诺伊', value: 12875255 },
            { name: '印地安那', value: 6537334 },
            { name: '爱荷华', value: 3074186 },
            { name: '堪萨斯', value: 2885905 },
            { name: '肯塔基', value: 4380415 },
            { name: '路易斯安那', value: 4601893 },
            { name: '缅因', value: 1329192 },
            { name: '马里兰', value: 5884563 },
            { name: '马赛诸塞', value: 6646144 },
            { name: '密歇根', value: 9883360 },
            { name: '明尼苏达', value: 5379139 },
            { name: '密西西比', value: 2984926 },
            { name: '密苏里', value: 6021988 },
            { name: '蒙大拿', value: 1005141 },
            { name: '内布拉斯加', value: 1855525 },
            { name: '内华达', value: 2758931 },
            { name: '新罕布什尔', value: 1320718 },
            { name: '新泽西', value: 8864590 },
            { name: '新墨西哥', value: 2085538 },
            { name: '纽约', value: 19570261 },
            { name: '北卡罗来', value: 9752073 },
            { name: '北达科他', value: 699628 },
            { name: '俄亥俄', value: 11544225 },
            { name: '俄克拉荷马', value: 3814820 },
            { name: '俄勒冈', value: 3899353 },
            { name: '宾夕法尼亚', value: 12763536 },
            { name: '罗德岛', value: 1050292 },
            { name: '南卡罗来', value: 4723723 },
            { name: '南达科他', value: 833354 },
            { name: '田纳西', value: 6456243 },
            { name: '德克萨斯', value: 26059203 },
            { name: '犹他', value: 2855287 },
            { name: '佛蒙特', value: 626011 },
            { name: '弗吉尼亚', value: 8185867 },
            { name: '华盛顿', value: 6897012 },
            { name: '西弗吉尼亚', value: 1855413 },
            { name: '威斯康星', value: 5726398 },
            { name: '怀俄明', value: 576412 },
            { name: '波多黎各', value: 3667084 }
            ]
        }
        ]
    };
      // 地图注册，第一个参数的名字必须和option.geo.map一致
      echarts.registerMap("usa",USA)
      charts.setOption(option);
      },
  },
  };
  </script>