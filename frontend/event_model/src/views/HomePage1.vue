<template>


    <div v-infinite-scroll="load" class="infinite-list" style="overflow: auto;height: 800px;margin: 40px 30px;">
        <el-carousel :interval="4000" type="card" height="400px">
            <el-carousel-item v-for="(event, index) in eventList.slice(0,6)">
                <el-card :body-style="{ padding: '0px' }" style="background-color: #CEDFEF;"  @click="goToDetail(event.id)">
                    <div style="padding: 14px">
                        <span style="font-size: 24px;font-weight: bold; opacity: 0.75;">{{event.title}}</span>
                        <img :src="'/src/assets/pictures/' + event.image.split(',')[0]">
                    </div>
                </el-card>
            </el-carousel-item>
        </el-carousel>
        <el-row gutter=20 style="margin-top: 50px;">
            <el-col :span="8" v-for="(event,index) in eventList.slice(6)" style="margin-bottom: 20px;">
                <el-card :body-style="{ padding: '0px' }" shadow="hover" style="width: 100%; height: 400px;" @click="goToDetail(event.id)">
                        <img :src="'/src/assets/pictures/' + event.image.split(',')[0]"
                        class="image"
                        style="width: 100%; height:300px;"
                        />
                        <div style="padding: 14px">
                            <div style="font-size: 16px;font-weight: bold; opacity: 0.75;">{{ event.time }}</div>
                            <span style="font-size: 16px;font-weight: bold; opacity: 0.75;">{{ event.title }}</span> 
                        </div>
                    </el-card>
            </el-col>
        </el-row>
        <!--
        <template v-for="(item, index) in eventList.slice(6)">
            <p v-if="(index)%6 == 0">{{ item.title }}</p>
            <p v-if="(index)%6 == 1">{{ item.title}}</p>
            <p v-if="(index)%6 == 2">{{ item.title}}</p>
            <p v-if="(index)%6 == 3">{{ item.title}}</p>
            <p v-if="(index)%6 == 4">{{ item.title}}</p>
            <p v-if="(index)%6 == 5">{{ item.title}}</p>
        </template>
    
        <li v-for="i in count" :key="i" class="infinite-list-item">{{ i }}</li>
        -->
    </div>

</template>


<script>
import request from '@/utils/request';

export default {
    data() {
        return {
            eventList: [],
            pageNumber: 1,
            pageSize: 12,
            count:10,
        }
    },
    created() {
        this.load()
    },
    methods: {
        load() {
            request.get('/event/imageNotEmpty?pageNumber=' + this.pageNumber + '&pageSize=' + this.pageSize).then(res => {
                if (res.code == 200) {
                    this.eventList = this.eventList.concat(res.data.content)
                    //console.log(this.eventList)

                }
                else {
                    this.$message.error(res.msg)
                }
                this.pageNumber += 1
                this.count += 2
            }).catch(err => {
                this.$message.error(err)
            })
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

    }
}


</script>


<style>


</style>