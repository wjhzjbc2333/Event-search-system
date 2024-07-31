### 一、前端环境

~~~
Node.js v20.10.0
npm 10.2.3
vue 3.3.13
其它更详细版本可在package.json文件中查看，主要使用element-plus作为页面组件，echarts作为图表可视化工具。
~~~



几个好用的 `vue` 文档：[Vue3官方文档](https://cn.vuejs.org/guide/introduction.html) | [菜鸟教程 ](https://www.runoob.com/vue3/vue3-tutorial.html) | [MDN 文档](https://developer.mozilla.org/zh-CN/docs/Learn/Tools_and_testing/Client-side_JavaScript_frameworks/Vue_getting_started)

[Element Plus官网 ](https://element-plus.org/zh-CN/component/overview.html)

[Apache ECharts](https://echarts.apache.org/zh/index.html)

本项目使用 `vite` 构建,

启动方法：进入项目根目录；

​                   输入`npm install`，等待依赖安装完成

​				   输入`npm run dev`， 建议先启动后端；启动后访问地址为 `localhost:5173`

### 二、功能说明

`App.vue` 是整个页面的入口。由于导航栏需要在几乎每个页面显示，所以在这里引入导航栏组件 `/components/Header.vue`

`Header.vue` 包含事件录入、事件编辑、查看事件库以及搜索功能，其中搜索功能的数据存储在此页面。可以点击高级搜索切换关键词和语义搜索。



使用 `vue-router` 作为项目的路由解决方案，所以在 `App.vue` 中能够设置导航栏显示的条件，并在具体的配置文件 `/router/index.js` 中进行配置。`index.js` 文件包含所有路由跳转对应的页面和组件信息



`/assets/` 下存放图片和地图数据(地图 `json` 文件还未加入项目中,但在 `/views/mapOfUSA或CHN` 中有使用示例)



`/utils/request.js` 为一种网络交互的封装,在进行通信时可直接使用 `request.get()` 或 `request.post()`



`/components` 和 `/views` 下存放几乎全部 `vue` 文件,主要使用文件如下:

~~~
src/components/AddEventSchema.vue       //添加事件模式
src/components/AddEventTheme.vue        //添加事件专题
src/components/AdvancedSearch.vue       //高级搜索组件
src/components/Header.vue               //导航栏
src/components/vis.vue                  //neovis.js的实例，可用于画关系图
src/views/addEvent.vue                  //添加事件,分为填写表单和上传固定格式json文件
src/views/CrisisEvents.vue              //"美国参与的冲突"页面，但内容和形式未定
src/views/EventDetail.vue               //事件详情页
src/views/HomePage1.vue                 //主页布局
src/views/SearchResults.vue             //搜索结果页面
~~~

添加事件时使用的 `json` 格式如下（提供 `all.json` 为示例）：

~~~
[
{“title": "【事件标题】",
 "time":"【事件发生时间】",
 "image":"【事件缩略图名】"
 "content": "【输入数据】",
 "summary": {"事件类型":"【事件类型】","事件论元":"【事件论元】"}},
 ...
]
~~~







此前端版本删去了许多并未使用的组件（如 `ECharts` 的示例、表单实例、时间线实例等，因其在官网能找到几乎一样的版本），且由于环境不同形成的前端界面会略有差异，可能需要额外修改。