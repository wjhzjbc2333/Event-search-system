<template>
    <div>
      <div id="mynetwork"></div>
    </div>
</template>
   
<script>
  import vis from 'vis'
   
  export default {
    name: 'vis',
    data () {
      return {
        nodes: new vis.DataSet([
          { id: 1, label: 'Node 1' },
          { id: 2, label: 'Node 2' },
          { id: 3, label: 'Node 3' },
          { id: 4, label: 'Node 4' },
          { id: 5, label: 'Node 5' }
        ]),
        edges: new vis.DataSet([
          { from: 1, to: 3 },
          { from: 1, to: 2 },
          { from: 2, to: 4 },
          { from: 2, to: 5 }
        ])
      }
    },
    mounted () {
      this.globalTrace() 
    },
    methods: {
      globalTrace () {
        // 建立拓扑图
        var container = document.querySelector('#mynetwork')
        // 以vis格式提供数据
        var data = {
          nodes: this.nodes,
          edges: this.edges
        }
        var options = { // 对vis的一些设置
   
        }
        // 初始化网络
        this.network = new vis.Network(container, data, options)
      }
    }
  }
  </script>
   
  <style scoped>
    #mynetwork {
      width: 600px;
      height: 400px;
      border: 1px solid lightgray;
    }
  </style>