<template>
    <div class="div">
        <div class="div1">
            <div>
                <h1>BLOG</h1>
            </div> 
        </div>

        <div class="div2">
            <el-menu
                :default-active="activeIndex2"
                class="el-menu-demo"
                mode="horizontal"
                @select="handleSelect"
                background-color="#87CEFF"
                text-color="#FFFFFF"
                active-text-color="#CD3333">

                <el-menu-item index="1" @click="goHome"> <el-icon><Discount/></el-icon>首页</el-menu-item>
                <el-menu-item index="2" @click="goClassify"><el-icon><Folder/></el-icon>分类</el-menu-item>
                <el-menu-item index="3" @click="goLabel"><el-icon><Document/></el-icon>标签</el-menu-item>
                <el-menu-item index="4" @click="goDate"><el-icon><Odometer/></el-icon>归档</el-menu-item>
                <el-menu-item index="5"><el-icon><User/></el-icon>关于我</el-menu-item>
            </el-menu>
        </div>

        <div class="div3">
            <el-input v-model="searchInput" placeholder="请输入内容" class="search-input"></el-input>
            <el-button class="search-input" @click="searchBlog" circle><el-icon><Search /></el-icon></el-button>
        </div>
    </div>
</template>

<script>
    import { useRouter } from 'vue-router'
    import { useStore } from 'vuex'
    import { ref } from 'vue'

    export default {
        name:'Head',

        setup(){

            const router = useRouter()
            const store = useStore()
            let indexBlog = store.state.indexBlog
            let page = indexBlog.page

            let searchInput = ref("")
            // 路由到首页
            function goHome(){
                router.push({
                    name:"home",
                })
            }
            // 路由到首页归档页
            function goDate(){
                router.push({
                    name:"date",
                    params:{
                        currentPage:1,
                        pageSize:2,
                    }
                })
            }
            // 路由到首页分类页
            function goClassify(){
                router.push({
                    name:"index_classify",
                    params:{
                        classifyCode:"allBlogs",
                        currentPage:1,
                        pageSize:10,
                    }
                })
            }
            // 路由到首页标签页
            function goLabel(){
                router.push({
                    name:"index_label",
                    params:{
                        labelCode:"allBlogs",
                        currentPage:1,
                        pageSize:10,
                    }
                })
            }

            function searchBlog(){
                page.searchInput = searchInput
                page.release = 1
                store.dispatch("indexBlog/queryBlogs",page)
            }


            return {
                goHome,
                goDate,
                goClassify,
                goLabel,
                searchBlog,
                searchInput
            }
        }
    }
</script>

<style scoped>
    .div{
        height: 100px;
        background-color: #87CEFF;
    }
    .div1{
        width: 20%;
        background-color: #87CEFF;
        position: relative;
        display: inline-block;
        top: 10%;        
    }
    .div1 div {
       color: green; 
       position: relative;
       left: 25%;
    }
    .div2{
        width: 40%;
        background-color: #87CEFF;
        position: relative;
        display: inline-block;
        top: 10%;        
    }
    .div3{
        background-color: #87CEFF;
        position: relative;
        display: inline-block;
        left: 15%;
        top: 0%;        
    }
    .search-input{
        display: inline;
    }
</style>