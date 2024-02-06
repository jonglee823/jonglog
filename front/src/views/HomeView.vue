<script setup lang="ts">

import {ref} from "vue";
import axios from 'axios';
import {useRouter} from "vue-router";

const posts= ref([]);
const router = useRouter();
const total = ref();

const currentPage = ref(1);
const per_page = ref(5)

const pageSize = ref(10)


function search(){
  const url = '/api/posts';
  axios.get(url, {
    params:{
      page: currentPage.value
      ,size: pageSize.value
    }
  })
  .then((responce) => {
    posts.value = [];
    responce.data.forEach((r: any) =>{
      posts.value.push(r);
    });
    total: responce.data.length;

  })
  .catch((error) => {
    console.log(error)
  });
}

const handleSizeChange = (val: number) => {
  pageSize.value = val
  search()
}
const handleCurrentChange = (val: number) => {
  currentPage.value = val
  search()
}
</script>


<template>
  <ul>
    <li v-for="post in posts" :key="post.id">
    <div class="title">
      <router-link :to="{name: 'read', params:{postId:post.id}}">{{post.title}}</router-link>
    </div>

    <div class="content">
      {{post.content}}
    </div>
  </li>
  </ul>

  <div class="example-pagination-block">
    <el-pagination  background layout="prev, sizes, pager, next"
                    v-model:current-page="currentPage"
                    v-model:page-size="pageSize"
                    :page-sizes="[10, 20, 30, 40, 50]"
                    :total="posts.length"
                    @size-change="handleSizeChange"
                    @current-change="handleCurrentChange"
    />
  </div>

  <div class="button">
    <router-link to="/write">
      <el-button type="primary">글작성</el-button>
    </router-link>
  </div>
</template>

<style scoped lang="scss">
ul {
  list-style: none;
  padding: 0;

  li {
    margin-bottom: 2rem;

    .title {
      a {
        font-size: 1.1rem;
        color: #383838;
        text-decoration: none;
      }

      &:hover {
        text-decoration: underline;
      }
    }

    .content {
      font-size: 0.85rem;
      margin-top: 8px;
      line-height: 1.4;
      color: #7e7e7e;
    }

    &:last-child {
      margin-bottom: 0;
    }

    .sub {
      margin-top: 8px;
      font-size: 0.78rem;

      .regDate {
        margin-left: 10px;
        color: #6b6b6b;
      }
    }
  }
}

.example-pagination-block{
  margin-top: 50px;
}

.el-pagination{
  justify-content: center;
}
.example-pagination-block .example-demonstration {
  margin-bottom: 16px;
}
</style>