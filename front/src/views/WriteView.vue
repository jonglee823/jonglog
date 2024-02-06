<script setup lang="ts">
import {ref} from "vue";
import {useRouter} from "vue-router";
import axios from 'axios';

const title=ref("")
const content=ref("")
const router = useRouter()

const write= function (){
  axios.post('/api/posts', {
    title: title.value
    , content: content.value
  })
  .then(function (response) {
      router.replace({name:"home"})
  })
  .catch(function (error) {
    console.log(error)
    alert(error.response.data.message);
  });
}

const clear= function(){
  title.value='';
  content.value='';
}
</script>

<template>
  <div >
    <el-input v-model="title" placeholder="제목을 입력해 주세요."/>
  </div>

  <div class="mt-2">
    <el-input v-model="content"  type="textarea"  placeholder="내용을 입력해 주세요." rows="15"></el-input>
  </div>

  <div class="mt-3 button">
    <el-popconfirm title="저장 하겠습니까?"
      @confirm="write"
    >
      <template #reference>
        <el-button type="primary">저장</el-button>
      </template>
    </el-popconfirm>

    <el-popconfirm title="초기화 하겠습니까?"
                   @confirm="clear">
      <template #reference>
        <el-button type="danger" plain>Delete</el-button>
      </template>
    </el-popconfirm>
  </div>
</template>

<style scoped>
.button{
  margin-top: 30px;
  text-align : right;
}
</style>