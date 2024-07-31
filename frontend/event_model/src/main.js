import './assets/main.css'

import { createApp } from 'vue'
import App from './App.vue'
import router from './router'


//ElementPlus
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import * as ElementPlusIconsVue from '@element-plus/icons-vue'


//Data-view
//import DataV, { setClassNamePrefix } from '@dataview/datav-vue3';

//ECharts
import * as echarts from 'echarts'

import axios from 'axios';

const app = createApp(App)

app.use(router)
app.use(ElementPlus)
//app.use(DataV, { classNamePrefix: 'dv-' });
app.config.globalProperties.$echarts = echarts

for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
    app.component(key, component)
}

app.mount('#app')
