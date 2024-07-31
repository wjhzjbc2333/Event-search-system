<template>
    <div class="radio">
      <el-radio-group v-model="radio1" class="ml-4">
        <el-radio label="1" size="large">基于关键词</el-radio>
        <el-radio label="2" size="large">基于语义</el-radio>
      </el-radio-group>
    </div>
    <div class="checkbox" style="margin-top: 20px;">
      <el-checkbox
      v-model="checkAllNation"
      :indeterminate="isIndeterminateNation"
      @change="handleCheckAllChangeNation">全部地区
      </el-checkbox>
    <el-checkbox-group v-model="checkedNations" @change="handleCheckedNationsChange">
      <el-checkbox v-for="nation in nations" :key="nation" :label="nation">{{ nation }}</el-checkbox>
    </el-checkbox-group>

    <el-checkbox
      v-model="checkAllTime"
      :indeterminate="isIndeterminateTime"
      @change="handleCheckAllChangeTime">全部年份
      </el-checkbox>
    <el-checkbox-group v-model="checkedTimes" @change="handleCheckedTimesChange">
      <el-checkbox v-for="time in times" :key="time" :label="time">{{ time }}</el-checkbox>
    </el-checkbox-group>

    <el-checkbox
      v-model="checkAllStyle"
      :indeterminate="isIndeterminateStyle"
      @change="handleCheckAllChangeStyle">全部类型
      </el-checkbox>
    <el-checkbox-group v-model="checkedStyles" @change="handleCheckedStylesChange">
      <el-checkbox v-for="style in styles" :key="style" :label="style">{{ style }}</el-checkbox>
    </el-checkbox-group>
</div>

</template>

<script>
import { ref } from 'vue'

export default {
    data() {
    return {
      radio1: ref('1'),

      checkAllNation: ref(false),
      isIndeterminateNation: ref(true),
      checkedNations: ref(['中国', '美国']),
      nations: ['中国', '美国', '英国', '法国', '俄罗斯'],

      checkAllTime: ref(false),
      isIndeterminateTime: ref(true),
      checkedTimes: ref(['2022-2023']),
      times: ['2022-2023', '2020-2022', '2015-2020', '2010-2015', '2010以前'],

      checkAllStyle: ref(false),
      isIndeterminateStyle: ref(true),
      checkedStyles: ref(['政治事件']),
      styles: ['政治事件', '经济事件', '军事事件', '科技事件'],

      }
    },
    methods: {
      handleCheckAllChangeNation(val) {
          this.checkedNations = val ? this.nations : []
          this.isIndeterminateNation = false
      },
      handleCheckedNationsChange(value) {
          const checkedCount = value.length
          this.checkAllNation = (checkedCount === this.nations.length)
          this.isIndeterminateNation = checkedCount > 0 && checkedCount < this.nations.length
      },
      handleCheckAllChangeTime(val) {
          this.checkedTimes = val ? this.times : []
          this.isIndeterminateTime = false
      },
      handleCheckedTimesChange(value) {
          const checkedCount = value.length
          this.checkAllTime = (checkedCount === this.times.length)
          this.isIndeterminateTime = checkedCount > 0 && checkedCount < this.times.length
      },
      handleCheckAllChangeStyle(val) {
          this.checkedStyles = val ? this.styles : []
          this.isIndeterminateStyle = false
      },
      handleCheckedStylesChange(value) {
          const checkedCount = value.length
          this.checkAllStyle = (checkedCount === this.styles.length)
          this.isIndeterminateStyle = checkedCount > 0 && checkedCount < this.styles.length
      }
    }
}

</script>

<style>
.el-checkbox {
    color: #000;
    font-weight: bold;
    font-size: 16px;
    font-family: '方正兰亭黑PRO';
}

.el-radio {
  color: #000;
  font-weight: bold;
  font-size: 24px;
  font-family: '方正兰亭黑PRO';
}
</style>