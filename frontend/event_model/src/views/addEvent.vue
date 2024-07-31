<template>
  <div class="page" style="margin-top: 100px;">
    <el-row :gutter="100">
      <el-col :span="12" style="border-right: solid 1px var(--el-border-color);">
        <el-form ref="event" :model="event" :rules="rules" label-width="200px">
          <el-form-item prop="title" label="事件名称" required>
            <el-input v-model="event.title"></el-input>
          </el-form-item>
          <el-form-item label="事件发生时间">
            <el-input v-model="event.time"></el-input>
          </el-form-item>
          <el-form-item label="事件论元(以英文逗号分隔)">
            <el-input v-model="argumentsInString"></el-input>
          </el-form-item>

          <el-form-item label="事件专题">
            <el-select v-model="selectedTheme" value-key="themeName" placeholder="选择" style="width: 240px" @change="changeTheme">
              <el-option v-for="item in optionsTheme"
              :key="item.id"
              :label="item.themeName"
              :value="item"
              />
            </el-select>
            <!--<el-button @click="getAllThemes">获取专题</el-button>-->
          </el-form-item>

          <el-form-item label="事件类型">
            <el-select v-model="selectedType" value-key="typeName" placeholder="选择" style="width: 240px" @change="changeType">
              <el-option v-for="item in optionsType" 
              :key="item.id" 
              :label="item.typeName" 
              :value="item"
              />
            </el-select>
            <!--<el-button @click="getAllTypes">获取类型</el-button>-->
          
          </el-form-item>

          <el-form-item label="事件详情" prop="description" required>
            <el-input type="textarea" v-model="event.description"></el-input>
          </el-form-item>
          
          <el-form-item label="上传图片">
            <el-upload
              v-model:file-list="imageList"
              action="http://localhost:9090/event/uploadPicture"
              list-type="picture-card"
              :on-preview="handlePictureCardPreview"
              :on-remove="handleRemove"
              :on-change="handlePictureChange"
            >
              <el-icon><Plus /></el-icon>
            </el-upload>

            <el-dialog v-model="dialogVisible">
              <img w-full :src="dialogImageUrl" alt="Preview Image" />
            </el-dialog>

          </el-form-item>

          <el-form-item>
            <el-button type="primary" @click="onSubmit">立即录入</el-button>
            <el-button @click="cancel">取消</el-button>
          </el-form-item>
        </el-form>
      </el-col>

      <el-col :span="11">
        <el-upload
          class="upload"
          drag
          action="http://localhost:9090/event/importWithJson"
          :multiple="true"
          :on-success="handleImport"
        >
          <el-icon class="el-icon--upload"><upload-filled /></el-icon>
          <div class="el-upload__text">
            将文件拖拽到此或<em>点击此处上传</em>
          </div>
          <template #tip>
            <div class="el-upload__tip">
              上传json文件以批量导入(json文件小于1MB)
            </div>
          </template>
        </el-upload>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import request from '@/utils/request.js';
import axios from 'axios';
//import { UploadProps } from 'element-plus';

export default {
    data() {
      return {
        event: {
          themeId: '',
          themeName: '',
          title: '',
          arguments: [],
          typeId: '',
          typeName: '',
          description: '',
          time: '',
          image: '',
          
        },
        argumentsInString: '',
        optionsType: [],
        selectedType: '',
        optionsTheme: [],
        selectedTheme: '',
        imageList: [],
        dialogVisible: false,
        dialogImageUrl: '',

        rules: {
          title: [
            { required: true, message: '请输入事件名称', trigger: 'blur' }
          ],
          description: [
            { required: true, message: '请填写事件详情', trigger: 'blur' }
          ]
        },
        
      }
    },
    created() {
      this.getAllThemes()
      this.getAllTypes()
    },
    methods: {
      
      handleImport(res, file, fileList) {
        if (res.code == 200) {
          this.$message.success("导入成功!")
          console.log(res)
        }
        else {
          this.$message.error(res.msg)
        }

      },
      onSubmit() {
        this.event.arguments = this.argumentsInString.split(",")
        let allimage = ''
        for (let i = 0; i < this.imageList.length;i++) {
          allimage = allimage + this.imageList[i].name + ','
        }
        if (allimage != '') {
          allimage = allimage.substring(0, allimage.length - 1)
        }
        this.event.image = allimage
        //console.log("images:  " + this.imageList)

        // let formData = new FormData()
        // formData.append('raw', this.imageList[0].raw)
        // console.log(formData)

        // axios({
        //   method: 'post',
        //   url: 'http://localhost:9090/event/uploadPicture',
        //   data: formData,
        //   headers: {
        //     'Content-Type':'multipart/form-data'
        //   }
        // }).then(res => {
        //   console.log(res)
        // })

        // request.post('/event/uploadPictures', this.imageList).then(res => {
        //   if (res.code == "200") {
        //     this.$message.success("导入成功！")
            
        //   }
        //   else {
        //     this.$message.error(res.msg)
        //   }
        // }).catch(err => {
        //   this.$message.error(err)
        // })


        request.post('/event/add', this.event).then(res => {
          if (res.code == "200") {
            this.$message.success("导入成功！")
            //console.log(res.data)
            this.$router.back()
          }
          else {
            this.$message.error(res.msg)
          }
        }).catch(err => {
          this.$message.error(err)
        })
        //this.imageList = []
        //console.log(this.event)
      },
      cancel() {
        this.$router.back()
      },
      changeType(data) {
        //console.log(data)
        var { id, typeName } = data
        this.event.typeId = id
        this.event.typeName = typeName
      },
      changeTheme(data) {
        console.log(data)
        var { id, themeName } = data
        this.event.themeId = id
        this.event.themeName = themeName
      },
      getAllTypes() {
        this.optionsType = []
        request.get('/event-schema/all').then(res => {
          if (res.code != '200') {
            alert("网络错误！")
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
            alert("网络错误！")
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

      handlePictureCardPreview(file) {
        //console.log(file)
        this.dialogVisible = true
        this.dialogImageUrl = file.url
      },

      handleRemove(file, fileList) {
        //console.log(file.name)
        //console.log(this.imageList)
      },

      handlePictureChange(file, fileList) {

        console.log(file)
        // const date = new Date();
        // const dateString = date.toISOString();
        // file.name = dateString
        // this.imageList.push(file)
      },

    }
}

</script>

<style>

</style>