<template>
    <div class="page" style="width: 500px; margin-left: 50px; margin-top: 50px;">
    <el-form ref="eventschema" :model="eventschema" :rules="rules" label-width="200px">
      <el-form-item prop="typeName" label="事件类型名称" required>
        <el-input v-model="eventschema.typeName"></el-input>
      </el-form-item>
      <el-form-item label="触发词(以英文逗号分隔)">
        <el-input v-model="eventschema.trigger"></el-input>
      </el-form-item>
      <el-form-item label="论元(以英文逗号分隔)">
        <el-input v-model="eventschema.arguments"></el-input>
      </el-form-item>
    
      <el-form-item>
        <el-button type="primary" @click="onSubmit">立即录入</el-button>
        <el-button @click="cancel">取消</el-button>
      </el-form-item>
    </el-form>
    </div>
    </template>
    
    <script>
    import request from '@/utils/request';
    
    export default {
        data() {
          return {
            eventschema: {
              typeName: '',
              trigger: '',
              arguments: '',
              //关键词,时间,地点,部署方,部署装备
              //关键词,时间,地点,演习方
            },
            postschema: {
              typeName: '',
              trigger: [],
              arguments:[],    
            },
            rules: {
              typeName: [
                { required: true, message: '请输入事件类型名', trigger: 'blur' }
              ],
              
            },
            
          }
        },
        methods: {
            onSubmit() {
                this.postschema.typeName = this.eventschema.typeName
                this.postschema.trigger = this.eventschema.trigger.split(',')
                this.postschema.arguments = this.eventschema.arguments.split(',')
                request.post('/event-schema/add', this.postschema).then(res => {
                    if (res.code != "200") {
                        alert("网络错误！")
                    }
                    else {
                        alert("添加成功！")
                        this.$router.back()
                    }
                })
                //console.log(this.postschema)
                //this.eventschema.trigger = ''
                //this.eventschema.arguments = ''
            },
            cancel() {
                this.$router.back()
            },
    
        }
    }
    
    </script>
    
    <style>
    
    </style>