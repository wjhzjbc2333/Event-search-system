<template>
    <el-container>
        <el-aside width="200px">
            <p style="font-size: 24px; font-family: 方正大黑简体; color: rgba(0, 0, 0, 0.8); margin-left: 24px; margin-top: 10px;">目录</p>
            <el-tree :data="content" :props="defaultProps" @node-click="handleContentClick" style="margin-top: 10px;"/>
            <div class="eventAnalysis" style="margin-top: 20px;">
                <div style="margin-bottom: 10px; margin-left: 10px;"><el-button round style="background-color: #0f64aa; color: #fff; margin-top: 12px; margin-left: 10px;">事件时序图</el-button></div>
                <div style="margin-bottom: 10px; margin-left: 10px;"><el-button round style="background-color: #0f64aa; color: #fff; margin-top: 12px; margin-left: 10px;">事件演化图</el-button></div>
                <div style="margin-bottom: 10px; margin-left: 10px;"><el-button round style="background-color: #0f64aa; color: #fff; margin-top: 12px; margin-left: 10px;">事件脉络</el-button></div>
                <div style="margin-bottom: 10px; margin-left: 10px;"><el-button round style="background-color: #0f64aa; color: #fff; margin-top: 12px; margin-left: 10px;">相似事件</el-button></div>
                <div style="margin-bottom: 10px; margin-left: 10px;"><el-button round style="background-color: #D90000; color: #fff; margin-top: 12px; margin-left: 10px;" @click="deleteEvent">删除此事件</el-button></div>
            </div>
        </el-aside>
        <el-main style="margin-left: 50px; margin-right: 50px;">
            <div class="header">
                <h1 class="title">{{ data.title }}</h1>
                <span class="info">事件类型： {{ data.typeName }}</span>
                <el-divider direction="vertical" />
                <span class="info">事件专题： {{ data.themeName }}</span>
                <el-divider direction="vertical" />
                <span class="info">时间： {{ data.time }}</span>
            </div>
            <div class="detail" style="">
                
                <p v-for="item in para" style="text-indent: 2em; margin: 20px 0;">{{ item }}</p>
                <p>相关图片：</p>
                <div v-if="images[0] == ''">
                    <div>无</div>
                </div>
                <div v-else>
                    <div class="carousel" style="">
                        <el-carousel >
                            <el-carousel-item v-for="image in images">
                                <img :src="'/src/assets/pictures/' + image" style="object-fit: scale-down;width: 100%; height: 100%;">
                                
                            </el-carousel-item>
                        </el-carousel>
                    </div>
                </div>

            </div>
        </el-main>
    </el-container>

</template>

<script>
import request from "@/utils/request.js"


export default {
    data() {
        return {
            // id: '',
            // themeId: '', 
            // themeName: '',
            // typeId: '',
            // typeName: '',
            // title: '',
            // arguments: [],
            // description: '',
            data: '',
            images: ['无'],
            arguments: ['default',],
            para: ['详情'],

        }
    },
    methods: {
        getEvent() {
            request.get('/event/detail?id=' + this.$route.query.id).then(res => {
                console.log(res)
                if (res.code == 200) {
                    // this.id = res.data.id
                    // this.themeId = res.data.themeId
                    // this.themeName = res.data.themeName
                    // this.typeId = res.data.typeId
                    // this.typeName = res.data.typeName
                    // this.title = res.data.title
                    // this.arguments = res.data.arguments
                    // this.description = res.data.description
                    this.data = res.data
                    if (this.data.themeName == null || this.data.themeName == '') {
                        this.data.themeName = '无'
                    }
                    if (this.data.typeName == null || this.data.typeName == '') {
                        this.data.typeName = '无'
                    }
                    this.arguments = res.data.arguments
                    this.para = res.data.description.split("\n")
                    this.images = res.data.image.split(',')
                }
                else {
                    this.$message.error(res.msg)
                }
            })
        },
        show() {
            console.log(this.images)
        },
        getArgument(index) {
            if (index < this.arguments.length && index > 0) {
                var reg = new RegExp("'", "g");
                
                return this.arguments[index].split(':')[1].replace(reg, "").replace("[", "").replace("]", "")
            }
            return null
        },
        deleteEvent() {
            request.post('/event/deleteById?id=' + this.$route.query.id).then(res => {
                if (res.code == 200) {
                    this.$message.success(res.msg)
                }
                else {
                    this.$message.error(res.msg)
                }
            }).error(err => {
                this.$message.error(err)
            })
        },
        
    },
    created() {
        this.getEvent()
    }

}


</script>

<style>
h1.title {
    font-family: "方正大黑简体";
    font-size: 30px;
}

.info {
    color: #666666;
    
}

div .detail {
    font-family: "方正兰亭黑PRO";
    font-size: 18px;
    line-height: 28px;
}

</style>