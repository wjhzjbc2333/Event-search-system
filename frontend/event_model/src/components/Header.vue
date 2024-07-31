<template>
    
    <div class="nav" style="display: flex; flex-wrap: wrap; flex-shrink: 0;">
        <!--
        <img alt="Vue logo" class="logo" src="@/assets/logo.svg" width="40" height="40" />
        <h1 class="pageHeader">事件模型</h1>
        -->
        <el-menu
            :ellipsis="false"
            class="navmenu"
            style="height: 56px;"
            mode="horizontal"
            @select="handleSelect"
            >
            <el-menu-item index="1">
                <img alt="Vue logo" class="logo" src="@/assets/logo.svg" width="40" height="40" />
            </el-menu-item>
            <el-menu-item index="2"><h1 style="font-family: 方正大黑简体; font-size: 24px;font-weight: 400;color: #000; margin-left: -30px;" @click="goToHomePage">事件模型</h1></el-menu-item>
            <el-menu-item index="3" @click="addEvent">事件录入</el-menu-item>
            <el-sub-menu index="4">
                <template #title>事件编辑</template>
                <!--<el-menu-item index="4-1">关系修改</el-menu-item>-->
                <el-menu-item index="4-2" @click="addEventSchema">添加事件类型</el-menu-item>
                <el-menu-item index="4-3" @click="addEventTheme">添加事件专题</el-menu-item>
                    <el-sub-menu index="4-4">
                        <template #title>item four</template>
                        <el-menu-item index="4-4-1">item one</el-menu-item>
                        <el-menu-item index="4-4-2">item two</el-menu-item>
                        <el-menu-item index="4-4-3">item three</el-menu-item>
                    </el-sub-menu>
            </el-sub-menu>
            <el-sub-menu index="5">
                <template #title>查看事件库</template>
                <el-menu-item index="5-1"><router-link to="/crisis">美国卷入的危机事件</router-link></el-menu-item>
                <el-menu-item index="5-2">其他事件</el-menu-item>
            </el-sub-menu>
            <el-menu-item index="6">
                <el-input
                class="input-search"
                v-model="searchInput"
                @keyup.enter="search"
                placeholder="搜索"
                style="height: 32px; width: 300px; margin-left: 50px;border-radius: 4px;">
                    <template #prepend>
                        <el-button @click = "search"><el-icon><Search /></el-icon></el-button>
                    </template>
                </el-input>
            </el-menu-item>
            <el-menu-item index="7">
                <el-button 
                @click="advancedSearch = true"
                style="background-color: #0f64aa; color: #fff;">
                    高级搜索
                </el-button>
                <el-dialog v-model="advancedSearch" title="高级搜索">
                    <!--<AdvancedSearch/>-->
                    <div class="radio">
                        <el-radio-group v-model="radio1">
                            <el-radio label="1" size="large">基于关键词</el-radio>
                            <el-radio label="2" size="large">基于语义</el-radio>
                        </el-radio-group>
                    </div>
                    <div class="selectTheme" v-show="radio1 == '1'">
                        <p>选择事件专题</p>
                        <el-select v-model="selectedTheme" value-key="themeName" placeholder="选择" style="width: 240px" @change="changeTheme">
                            <el-option v-for="item in optionsTheme"
                            :key="item.id"
                            :label="item.themeName"
                            :value="item"
                            />
                        </el-select>
                    </div>
                    <div class="selectType" v-show="radio1 == '1'">
                        <p>选择事件类型</p>
                        <el-select v-model="selectedType" value-key="typeName" placeholder="选择" style="width: 240px" @change="changeType">
                            <el-option v-for="item in optionsType" 
                            :key="item.id" 
                            :label="item.typeName" 
                            :value="item"
                            />
                        </el-select>
                    </div>
                    <div class="knn"  v-show="radio1 == '2'">
                        <p>输入要获取的结果数量</p>
                        <el-input v-model="k" style="width: 200px;"></el-input>
                    </div>
                    
                </el-dialog>
            </el-menu-item>
            <!--
            <el-menu-item index="8">
                <el-button type="primary"><el-icon><Setting /></el-icon></el-button>
            </el-menu-item>
            <el-menu-item index="9">
                <el-button type="info"><el-icon><Message /></el-icon></el-button>
            </el-menu-item>
            <el-menu-item index="10">
                <el-button type="success"><el-icon><Grid /></el-icon></el-button>
            </el-menu-item>
            <el-menu-item index="11"> 
                <el-button type="primary" circle class="user" @click="goToUser"><el-icon><User /></el-icon></el-button>
            </el-menu-item>
            -->
        </el-menu>
    </div>
    

</template>

<script>
import { ref } from 'vue'
import { useRouter } from 'vue-router';
import {Setting, Message, Star, Edit, Search} from '@element-plus/icons-vue'
import AdvancedSearch from '@/components/AdvancedSearch.vue'
import request from '@/utils/request';

export default {
    data() {
        return {
            //activeIndex: ref('1')
            advancedSearch: ref(false),
            searchInput: ref(''),
            radio1: "1",
            optionsTheme: [],
            optionsType: [],
            selectedTheme: '',
            selectedType: '',
            k: 10,

        };
    },
    components: {
        AdvancedSearch,
        Setting, Message, Star, Edit, Search
    },
    created() {
        this.getAllThemes()
        this.getAllTypes()
    },
    methods: {
        handleClick() {
            //console.log(111)
        },
        handleSelect() {
            
        },
        goToUser() {
            this.$router.push({path:'/user'})
        },
        goToHomePage() {
            this.$router.push({path:'/'})
        }, 
        addEvent(){
            this.$router.push({path:'/addEvent'})
        },
        addEventSchema() {
            this.$router.push({path:'/addEventSchema'})
        },
        addEventTheme() {
            this.$router.push({path:'/addEventTheme'})
        },
        changeType(data) {
            console.log(data)
            var { id, typeName } = data
            //this.event.typeId = id
            this.selectedType = typeName
        },
        changeTheme(data) {
            console.log(data)
            var { id, themeName } = data
            //this.event.themeId = id
            this.selectedTheme = themeName
        },
        getAllTypes() {
            this.optionsType = []
            request.get('/event-schema/all').then(res => {
            if (res.code != '200') {
                this.$message.error(res.msg)
            }
            else if (res.data != null) {
                for (var i = 0; i < res.data.length; i++) {         
                    var item = new Object();
                    item.id = res.data[i].id
                    item.typeName = res.data[i].typeName
                    this.optionsType.push(item)
                }
                //console.log(this.optionsType)
            }
            })
        },
        getAllThemes() {
            this.optionsTheme = []
            request.get('/event-theme/all').then(res => {
            if (res.code != '200') {
                this.$message.error(res.msg)
            }
            else if (res.data != null) {
                for (var i = 0; i < res.data.length; i++) {         
                var item = new Object();
                item.id = res.data[i].id
                item.themeName = res.data[i].themeName
                this.optionsTheme.push(item)

                }
                //console.log(this.optionsTheme)
            }
            })
        },
        search() {
            if (this.radio1 == '1') {
                let route = this.$router.resolve({
                    path: "/searchResults",
                    query: {
                        type: "keyword",
                        pageNumber: 1,
                        pageSize: 10,
                        des: this.searchInput,
                        eventType: this.selectedType,
                        eventTheme: this.selectedTheme
                    },
                });
                window.open(route.href, "_blank");
            }
            else if (this.radio1 == '2') {
                let route = this.$router.resolve({
                    path: "/searchResults",
                    query: {
                        type: "semantic",
                        pageNumber: 1,
                        pageSize: 10,
                        des: this.searchInput,
                        k: this.k
                    },
                });
                window.open(route.href, "_blank");
            }
            //this.$router.push({ name: 'searchResults', state: { id: '2333' } })
            else {
                let route = this.$router.resolve({
                    path: "/searchResults",
                    query: {
                        type: "keyword",
                        pageNumber: 1,
                        pageSize: 10,
                        des: this.searchInput,
                        eventType: this.selectedType,
                        eventTheme: this.selectedTheme
                    },
                });
                window.open(route.href, "_blank");
            }
            
            // request.get("/event/findByDescriptionPageable?pageNum=0&pageSize=10&des=俄罗斯").then(res => {
            //     if (res.code != 200) {
            //         alert("网络错误！")

            //     }
            //     else if (res.data.content == null) {
                    
            //     }
            //     else {
            //         console.log(res.data.content)
            //     }

            // })
        }
    }
}


</script>

<style>
.el-header {
    position: relative;
    width: 100%;
    height: 56px;
    background-color: #fff;      
    box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
}


.logo {
    float:left; 
    margin-left: 15px; 
    margin-top: 8px;
}
  
.pageHeader {
    font-family: "方正大黑简体";
    font-size: 24px;
    font-weight: 400;
    color: #000;
    margin-top: 10px;
    margin-left: 10px;
}


</style>