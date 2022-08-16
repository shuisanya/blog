<template>
    <div class="main-sidebar">
        <div id="sidebar-news" class="sid">
            <h3 class="catListTitle ">公告</h3>
            <div id="blog-news">
                <div id="profile_block">
                    昵称：<span> 水三丫 </span>   <br>
                </div>
            </div>
        </div>

        <div id="blog-classify" class="sid">
            <h3 class="catListTitle ">分类</h3>
            <div id="blog-news">
                <div id="profile_block">
                    <div class="label"  v-for="classify in classifies" :key="classify.classifyCode" @click="queryBlogsByClassifyCode(classify.classifyCode)"> 
                        <span >{{classify.classifyName}}</span>
                        <div class="label-item">
                            {{classify.blogs.length}}
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <div id="blog-label" class="sid">
            <h3 class="catListTitle ">标签</h3>
            <div id="blog-news">
                <div id="profile_block" v-for="label in labels" :key="label.labelCode"  @click="queryBlogsByLabelCode(label.labelCode)">
                    
                    <el-tag type="success" >
                        <span >{{label.labelName}}</span>
                    </el-tag>

                    <el-tag >
                        <span >{{label.blogs.length}}</span>
                    </el-tag>
                
                </div>
            </div>
        </div>

        <div id="blog-recor" class="sid">
            <h3 class="catListTitle ">文章数</h3>
            <div id="blog-news">
                <div id="profile_block">
                    <ul class="ul-recor">
                        <li><span >总数：</span>（ {{count}} ）</li>
                    </ul> 
                </div>
            </div>
        </div>

        <div id="blog-renk" class="sid">
            <h3 class="catListTitle ">阅读排行榜</h3>
            <div id="blog-news">
                <div id="profile_block">
                    <ul class="ul-recor"  v-for="blog in blogs" :key="blog.code">
                        <li>
                            <span @click="goArticle(blog.code)">
                            {{blog.title}} ({{blog.readNum}})
                            </span>
                        </li>
                    </ul>
                </div>
            </div>
        </div>

    </div>
</template>

<script>
    import {useRouter} from 'vue-router'
    import {ref,onMounted,getCurrentInstance} from 'vue'
    import {useStore} from 'vuex'
import axios from 'axios'
    export default {
        name:"Mian_Nav",
        setup(){
            let id = "001"
            const {proxy} = getCurrentInstance()
            const router = useRouter();

            const classifies = ref([])
            const labels = ref([])
            const blogs = ref([])
            const count = ref("")
            const store = useStore()
            let indexBlog = store.state.indexBlog
            let page = indexBlog.page


            function goArticle(code){
                const newPage = router.resolve({
                    name:"article",
                    params:{
                        id:code
                    }
                })
                window.open(newPage.href,'_blank')
            }

            function queryBlogsByClassifyCode(classifyCode){
                page.classifyCode = classifyCode
                page.release = 1
                page.labelCode = ""
                store.dispatch("indexBlog/queryBlogs",page)
                router.push({
                    name:"index_classify",
                    params:{
                        currentPage:page.currentPage,
                        pageSize:page.pageSize,
                        classifyCode:classifyCode
                    }
                })
            }
                                     
            function queryBlogsByLabelCode(labelCode){
                page.labelCode = labelCode
                page.release = 1
                page.classifyCode = ""
                store.dispatch("indexBlog/queryBlogs",page)
                router.push({
                    name:"index_label",
                    params:{
                        currentPage:page.currentPage,
                        pageSize:page.pageSize,
                        labelCode:labelCode
                    }
                })
            }

            function arrSort(arr){
                let len = arr.length
                let i;
                let j;
                let k;

                for(i=0;i<len-1;i++){
                    let temp = arr[i].blogs.length;
                    k=i;
                    for(j=len - 1;j > i;j--){
                        if(temp < arr[j].blogs.length){
                            temp = arr[j].blogs.length
                            k=j;
                        }
                    }
                    
                    temp = arr[k]
                    arr[k] = arr[i]
                    arr[i] = temp
                }

                return arr.slice(0,5)
            }

            function queryAllClassifies(){
                proxy.$axios.get(`${proxy.PATH}/index/classify/queryAllClassifies`).then(
                    respones =>{
                        classifies.value = arrSort(respones.data)
                    },
                    error => {
                        console.log('请求失败了',error.message)
                    }
                )
            }

           function queryAllLabels(){
                proxy.$axios.get(`${proxy.PATH}/index/label/queryAllLabels`).then(
                    respones =>{
                        labels.value = arrSort(respones.data)
                    },
                    error => {
                        console.log('请求失败了',error.message)
                    }
                )
            }

           function queryAllLBlogsCount(){
                proxy.$axios.get(`${proxy.PATH}/index/blog/queryAllLBlogsCount`).then(
                    respones =>{
                        count.value = respones.data 
                    },
                    error => {
                        console.log('请求失败了',error.message)
                    }
                )
            }

            function queryReadBlogRank(){
                let data = {
                    begin:0,
                    size:5,
                    release:1,
                }
                proxy.$axios.post(`${proxy.PATH}/index/blog/queryReadBlogRank`,data).then(
                    respones => {
                        blogs.value = respones.data
                    },
                    error => {
                        console.log('请求失败了',error.message)
                    }
                )
            }


            onMounted(() => {
                queryAllClassifies()
                queryAllLabels()
                queryAllLBlogsCount()
                queryReadBlogRank()
            })

            return {
                classifies,
                labels,
                count,
                blogs,
                goArticle,
                queryBlogsByClassifyCode,
                queryBlogsByLabelCode,
            }

        }
    }
</script>

<style scoped>
    .main-sidebar{
        position: relative;
        width: 20%;
        display: inline-block;
        vertical-align: top;
        left: 8%;
        margin: 1%;
    }
    .sid{
        margin: 0px 10px 10px 10px;
        border: 1px solid #dedede;
    }
    .catListTitle {
        font-size: 18px;
        padding: 10px 20px;
        background-color: #169fe6;
        color: #fff;
        font-weight: normal;
        border: 1px solid #169fe6;
    }   
    #blog-news {
        overflow: visible;
        margin-bottom: 20px;
    }
    #profile_block {
        background: #fff;
        padding: 10px;
        color: #7e8c8d;
        font-size: 13px;
        line-height: 1.8;
        margin-top: 0 !important;
        text-align: left;
    }
    #profile_block span{
        color: #9fa4a4;
        text-decoration: none;
        font-size: 13px;
    }
    #profile_block span:hover{
        color: coral;
        cursor: pointer;
    }
    .label{
        border: #dedede 1px solid;
        color: coral;
        margin: 4px;
        border-radius: 5%;
    }
    .label-item{
        position: relative;
        display: inline-block;
        width: 22px;
        height: 22px;
        border: #169fe6 3px solid;
        border-radius: 30%;
        vertical-align: top;
        left: 80px;
        margin: auto;
        text-align: center;
    }
    .label span:hover{
        color: coral;
        cursor: pointer;
    }

    .ul-recor{
        word-break: break-all;
        list-style: none;
        border: 1px solid #dedede;
        border-top: none;
    }
    .ul-recor li{
        list-style: none;
        line-height: 2;
        border-bottom: 1px solid #e9e9e9;
        padding: 15px 10px 15px 20px;
        font-size: 14px;
        color: #777;
        display: block;
    }
    .ul-recor li span{
        color: #777;
        text-decoration: none;
    }

</style>>