<template>
    <div class="classify">
        <div class="classify-title">
            <h3>分类</h3>
            <div>
                <span>共 <h3> {{classifies.length}} </h3> 个</span>
            </div>
            
        </div>

        <div class="classify-detail">
            <div class="classify-detail-1" v-for="classify in classifies" :key="classify.classifyCode" @click="queryBlogsByClassifyCode(classify.classifyCode)">
                <div class="classify-detail-1-1">
                    <span>{{classify.classifyName}}</span> 
                </div>
                <div class="classify-detail-1-2">{{classify.blogs.length}}</div>
            </div>

        </div>
    </div>
</template>

<script>
    import {useRoute, useRouter} from 'vue-router'
    import {ref,onMounted,getCurrentInstance} from 'vue'
    import {useStore} from 'vuex'
    export default {
        name:"Main_Home_Clasify",

        setup(){
            const {proxy} = getCurrentInstance()
            const router = useRouter();
            const route = useRoute();
            const store = useStore()
            const classifies = ref([])

            let indexBlog = store.state.indexBlog
            let page = indexBlog.page

            page.currentPage = route.params.currentPage
            page.pageSize = route.params.pageSize
            
            const classifyCode = route.params.classifyCode
            
            

            function queryBlogsByClassifyCode(classifyCode){
                page.classifyCode = classifyCode
                page.release = 1
                page.labelCode = ""
                page.searchInput = ""
                store.dispatch("indexBlog/queryBlogs",page)
                router.push({
                    name:route.name,
                    params:{
                        currentPage:page.currentPage,
                        pageSize:page.pageSize,
                        classifyCode:classifyCode
                    }
                })
            }
            


            function queryAllClassifies(){
                proxy.$axios.get(`${proxy.PATH}/index/classify/queryAllClassifies`).then(
                    respones =>{
                        classifies.value = respones.data
                    },
                    error => {
                        console.log('请求失败了',error.message)
                    }
                )
            }

            onMounted(() => {
                queryAllClassifies()
                page.release = 1
                page.searchInput = ""
                if(route.params.classifyCode !== "allBlogs"){
                    page.classifyCode = classifyCode
                }
                store.dispatch("indexBlog/queryBlogs",page)
            })

            return{
                classifies,
                queryBlogsByClassifyCode
            }
        }
    }
</script>

<style scoped>
    .classify{
        background: #fff;
        border: 1px solid #dedede;
        margin-bottom: -1px;
        margin-bottom: 5%;
    }
    .classify-title{
        background: #fff;
        border: 1px solid #dedede;
    }
    .classify-title div{
        position: relative;
        left: 88%;
        display: inline-block;
    }
    .classify-title h3{
        display: inline-block;
        color: green;
    }
    .classify-title div h3{
        color: red;
    }
    .classify-detail{
        background: #fff;
        border: 1px solid #dedede;
    }
    .classify-detail-1{
        margin: 5px 10px 5px 10px;
        display: inline-block;
        vertical-align: top;
    }
    .classify-detail-1:hover{
        color: #87CEFF;
        cursor: pointer;
    }
    .classify-detail-1-1{
        display: inline-block;
        vertical-align: top;
        height: 25px;
        border: coral 2px solid;
        padding: 5px;
        text-align: center;
    }
    .classify-detail-1-1 a{
        text-decoration: none;
    }
    .classify-detail-1-2{
        display: inline-block;
        width: 22px;
        height: 25px;
        vertical-align: top;
        border: coral 2px solid;
        padding: 5px;
        text-align: center;
    }
</style>>
