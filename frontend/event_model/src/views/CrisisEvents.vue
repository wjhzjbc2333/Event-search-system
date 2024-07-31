<template>
    <div class="page">
        <el-row justify="center">
            
            <el-col :span="8">
                <el-card :body-style="{ padding: '0px' }" shadow="hover"
                style="width: 400px; height: 400px; border-radius: 5px; background-color: #cedfef;">
                    <img
                    src="@/assets/pictures/KoreaWar.jpg"
                    class="image"
                    style="width: 400px; height: 300px;"
                    />
                    <div style="padding: 14px">
                        <span class="title">朝鲜战争(1950.6—1953.7)</span>
                        <div style="margin-top: 20px; margin-left: 300px; text-decoration: underline; color: #0f64aa; cursor: pointer;" @click="KoreaWar">查看详情</div>   
                    </div>
                </el-card>
            </el-col>

            <el-col :span="8">
                <el-card :body-style="{ padding: '0px' }" shadow="hover"
                style="width: 400px; height: 400px; border-radius: 5px; background-color: #cedfef;">
                    <img
                    src="@/assets/pictures/VietnamWar.jpg"
                    class="image"
                    style="width: 400px; height: 300px;"
                    />
                    <div style="padding: 14px">
                        <span class="title">越南战争(1955.11—1975.4)</span>
                        <div style="margin-top: 20px; margin-left: 300px; text-decoration: underline; color: #0f64aa; cursor: pointer;" @click="VietnamWar">查看详情</div>
                    </div>
                </el-card>
            </el-col>

            <el-col :span="8">
                <el-card :body-style="{ padding: '0px' }" shadow="hover"
                style="width: 400px; height: 400px; border-radius: 5px; background-color: #cedfef;">
                    <img
                    src="@/assets/pictures/BosnianWar.jpg"
                    class="image"
                    style="width: 400px; height: 300px;"
                    />
                    <div style="padding: 14px">
                    <span class="title">波黑战争(1992.4-1995.12)</span>
                    <div style="margin-top: 20px; margin-left: 300px; text-decoration: underline; color: #0f64aa; cursor: pointer;" @click="BosnianWar">查看详情</div>
                    </div>
                </el-card>
            </el-col>
            
        </el-row>

        <el-row justify="center">
            
            <el-col :span="8">
                <el-card :body-style="{ padding: '0px' }" shadow="hover"
                style="width: 400px; height: 400px; border-radius: 5px; background-color: #cedfef;">
                    <img
                    src="@/assets/pictures/AfghanistanWar.jpg"
                    class="image"
                    style="width: 400px; height: 300px;"
                    />
                    <div style="padding: 14px">
                        <span class="title">阿富汗战争(2001.10-2014.12)</span>
                        <div style="margin-top: 20px; margin-left: 300px; text-decoration: underline; color: #0f64aa; cursor: pointer;" @click="AfghanistanWar">查看详情</div>   
                    </div>
                </el-card>
            </el-col>

            <el-col :span="8">
                <el-card :body-style="{ padding: '0px' }" shadow="hover"
                style="width: 400px; height: 400px; border-radius: 5px; background-color: #cedfef;">
                    <img
                    src="@/assets/pictures/IraqWar.jpg"
                    class="image"
                    style="width: 400px; height: 300px;"
                    />
                    <div style="padding: 14px">
                        <span class="title">伊拉克战争(2003.3-2011.12)</span>
                        <div style="margin-top: 20px; margin-left: 300px; text-decoration: underline; color: #0f64aa; cursor: pointer;" @click="IraqWar">查看详情</div>
                    </div>
                </el-card>
            </el-col>

            <el-col :span="8">
                <el-card :body-style="{ padding: '0px' }" shadow="hover" 
                style="width: 400px; height: 400px; border-radius: 5px; background-color: #cedfef;">
                    <img
                    src="@/assets/pictures/LibyaWar.jpg"
                    class="image"
                    style="width: 400px; height: 300px;"
                    />
                    <div style="padding: 14px">
                    <span class="title">利比亚战争(2011.3-2011.10)</span>
                    <div style="margin-top: 20px; margin-left: 300px; text-decoration: underline; color: #0f64aa; cursor: pointer;" @click="LibyaWar">查看详情</div>
                    </div>
                </el-card>
            </el-col>
            
        </el-row>

    </div>
</template>
  
<script>

export default {
    data() {

    },
    methods: {
        KoreaWar() {
            this.$router.push({path:'/crisis/KoreaWar'})
        },
        VietnamWar() {
            this.$router.push({path:'/crisis/VietnamWar'})
        },
        BosnianWar() {
            this.$router.push({path:'/crisis/BosnianWar'})
        },
        AfghanistanWar() {
            this.$router.push({path:'/crisis/AfghanistanWar'})
        },
        IraqWar() {
            this.$router.push({path:'/crisis/IraqWar'})
        },
        LibyaWar() {
            this.$router.push({path:'/crisis/LibyaWar'})
        }
    }
}


</script>
  
<style>


.el-row{
    margin-left: 40px;
    margin-top: 20px;
}

.image {
    width: 100%;
    display: block;
}

.title {
    font-size: 20px;
    font-weight: bold;
    font-family: "方正兰亭黑PRO";
}


</style>
  