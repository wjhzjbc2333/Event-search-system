<template>
    <div class="search-box" style="position: relative; background-color: #ebf0fa; min-width: 1200px; margin-top: 20px;">
        <div class="search-data" style="margin: auto; width:60%;">
            <el-card v-for="event in eventList"  shadow="hover" style="margin-bottom: 20px;" @click="goToDetail(event.id)">
                <template #header>
                    <h3>{{ event.title == null ? "标题" : event.title}}</h3>    
                    <span>类型：{{ event.typeName == null ? "无" : event.typeName}}</span>
                    <el-divider direction="vertical" />
                    <span>专题：{{ event.themeName == null ? "无" : event.themeName}}</span>
                    <el-divider direction="vertical" />
                    <span>时间：{{ event.time == null ? "无" : event.time}}</span>
                </template>
                <el-row>
                    <el-col :span="5">
                        <span>
                            <!--<img :src="event.image == '' ? '/src/assets/noimg.png' : '/src/assets/pictures/' + event.image.split(',')[0]" style="width:120px;height: 120px;">-->
                            <img :src="imgPath(event.image)" style="width:120px;height: 120px;">
                        </span>
                    </el-col>
                    <el-col :span="19">
                        <span>{{ event.description.split('\n')[0] }}</span>
                    </el-col>

                </el-row>
            </el-card> 
            <div class="pagination">
                <el-pagination
                v-model:current-page="pageNumber"
                v-model:page-size="pageSize"
                v-model:total="total"
                layout="total, prev, pager, next, jumper"
                @size-change="handleSizeChange"
                @current-change="handleCurrentChange"
                />
            </div>
        </div>
        
    </div>   
</template>
  
<script>
import request from "@/utils/request.js"

export default {
data() {
    return {
        eventList: [],
        pageSize: 10,
        pageNumber: 1,
        total: 0,
        k: 10,
        type: "",
        des: "",
        eventType: "",
        eventTheme: "",
    }

},
methods: {
    handleCurrentChange() {
        //console.log(this.pageNumber)
        this.getEvents()
    },
    handleSizeChange() {

    },
    show() {
        //console.log(this.$route.query.des)
        console.log(this.eventList)
    },
    imgPath(path) {
        //console.log(path)
        if (path != null && path != '') {
            return "/src/assets/pictures/" + path.split(',')[0]
        }
        else return "/src/assets/noimg.png"
    },
    goToDetail(id) {
        console.log(id)
        let route = this.$router.resolve({
                path: "/eventDetail",
                query: {
                    id: id,
                },
        });
        window.open(route.href, "_blank");
    },
    getEvents() {
        if (this.type == "semantic") {
            request.get('/event/findBySemanticKNN?pageNumber=' + this.pageNumber + "&pageSize=" + this.pageSize + "&des=" + this.des + "&k=" + this.k).then(res => {
                //console.log(res)
                if (res.code == 200) {
                    this.pageNumber = res.data.pageable.pageNumber + 1
                    this.pageSize = res.data.pageable.pageSize
                    this.eventList = res.data.content
                    this.total = res.data.totalElements
                } else {
                    this.$message.error(res.msg)
                }


            }).catch(err => {
                this.$message.error(err)
            })
        }
        else {
            request.get('/event/findByDescriptionPageable?pageNumber=' + this.pageNumber + '&pageSize=' + this.pageSize + '&des=' + this.$route.query.des
                + '&eventType=' + this.$route.query.eventType + '&eventTheme=' + this.$route.query.eventTheme
            ).then(res => {
                //获得服务器端回传的map对象
                //console.log(res)
                if (res.code == 200) {
                    this.pageNumber = res.data.pageable.pageNumber + 1
                    this.pageSize = res.data.pageable.pageSize
                    this.eventList = res.data.content
                    this.total = res.data.totalElements
                } else {
                    this.$message.error(res.msg)
                }

            }).catch(err => {
                this.$message.error(err)
            })
        }
    },
},
created() {
    this.des = this.$route.query.des
    this.pageNumber = this.$route.query.pageNumber
    this.pageSize = this.$route.query.pageSize
    if (this.$route.query.type == "semantic") {
        this.type = "semantic"
        this.k = this.$route.query.k
    }
    else {
        this.type = "keyword"
        this.eventType = this.$route.query.eventType
        this.eventTheme = this.$route.query.eventTheme
    }
    this.getEvents();
}
}
</script>

<style>
.pagination-tools {
margin-top: 10px;
text-align: center;
}

body{background-color: #ebf0fa}

</style>
