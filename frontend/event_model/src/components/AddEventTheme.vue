<template>
    <div class="page" style="width: 500px; margin-left: 50px; margin-top: 50px;">
    <el-form ref="eventschema" :model="eventschema" :rules="rules" label-width="200px">
      <el-form-item prop="themeName" label="事件专题名称" required>
        <el-input v-model="eventtheme.themeName"></el-input>
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
            eventtheme: {
              themeName: '',
            },
            rules: {
              themeName: [
                { required: true, message: '请输入事件专题名', trigger: 'blur' }
              ],
              
            },
            
          }
        },
        methods: {
            onSubmit() {
                request.post('/event-theme/add', this.eventtheme).then(res => {
                    if (res.code != "200") {
                        alert("网络错误！")
                    }
                    else {
                        alert("添加成功！")
                        this.$router.back()
                    }
                })
            },
            cancel() {
                this.$router.back()
            },
    
        }
    }
    
    </script>
    
    <style>
    
    </style>