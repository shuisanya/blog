<template>
    <div class="txtarticle">
<!--  -->
        <div class="classify" v-for="classify in classifyList" :key="classify.classifyCode">
            <div class="classify-title">
                <div class="classify-detail-1">
                    <div class="classify-detail-1-1">
                        <span>{{classify.classifyName}}</span> 
                    </div>
                    <div class="classify-detail-1-2">{{classify.blogs.length}}</div>
                </div>
            </div>

            <div class="classify-detail">
                <tr class="classify-ul" v-for="blog in classify.blogs" :key="blog.code">
                    <td>
                        <div class="detail-title">
                            <span @click="goArticle(blog.code)">{{blog.title}}</span>
                        </div>
                    </td>
                    <td>
                        <div class="dayTitle">
                            <span @click="goArticle(blog.code)">{{time(blog.createTime)}}</span>
                        </div>
                    </td>
                        
                    <td>
                        <div class="classify-name">
                            <div class="classify-detail-1">
                                <div class="classify-detail-1-1">
                                    <span>{{classify.classifyName}}</span> 
                                </div>
                            </div>
                        </div>
                    </td>
                        
                    <td>
                        <div class="classify-name">
                            <div class="classify-detail-1">
                                <div class="classify-detail-1-1">
                                    <span>{{blog.release === 1 ? "发布":"未发布"}}</span> 
                                </div>
                            </div>
                        </div>
                    </td>
                </tr>
            </div>

            <div class="classify-desce"></div>   
        </div>

        <div class="page">
            <el-pagination
            @current-change="currentChange"
            @size-change="sizeChange"
            background
            :page-size="page.pageSize"
            :page-sizes="[2,4,6]"
            :current-page="page.currentPage"
            layout="total, sizes, prev, pager, next, jumper"
            :total="page.pageTotalCount">
            </el-pagination>
        </div>
    </div>

</template>

<script>
    import { computed, getCurrentInstance, onMounted } from 'vue'
    import { useStore } from 'vuex'
    import { useRoute, useRouter } from 'vue-router'
    // 将时间戳转换为日期的库
    import moment from 'moment'
    export default {
        name:"Classify_Article",
        setup(){
            const {proxy} = getCurrentInstance()
            const store = useStore()
            let classify = store.state.blog.classify
            let page = classify.page
            let classifyList = computed(() => classify.classifyList) 
            let route = useRoute()
            let router = useRouter()

            page.currentPage = route.params.currentPage
            page.pageSize = route.params.pageSize

            //当也页码改变时 currentPage页码
            function currentChange(currentPage){
                page.currentPage = currentPage
                store.dispatch("blog/queryClassifyBlogs",page)
                router.push({
                    name:route.name,
                    params:{
                        currentPage:currentPage,
                        pageSize:page.pageSize
                    }
                })
            }

            // 每页数量改变时
            function sizeChange(pageSize){
                page.pageSize = pageSize
                page.currentPage = 1
                store.dispatch("blog/queryClassifyBlogs",page)
                router.push({
                    name:route.name,
                    params:{
                        currentPage:page.currentPage,
                        pageSize:pageSize
                    }
                })
            }

            function goArticle(code){
                const newPage = router.resolve({
                    name:"article",
                    params:{
                        id:code
                    }
                })
                window.open(newPage.href,'_blank')
            }
   
            onMounted(() => {
                store.dispatch("blog/queryClassifyBlogs",page)
            })

            function time(time){
                return moment(time).format("YYYY-MM-DD HH:mm:ss")
            }

            return{
                page,
                classifyList,
                currentChange,
                sizeChange,
                time,
                goArticle
            }
        }
    }
</script>

<style scoped>
    .txtarticle{
        position: relative;
        width: 80%;
        display: inline-block;
        vertical-align: top;
        left: 8%;
        margin: 1%;
        overflow-y: auto;
        overflow-x: hidden;
        height: 620px;
    }
    .classify{
        position: relative;
        margin-top: 3%;
        margin-bottom: 2%;
    }

    .classify-title{
        text-align: center;
    }
    .classify-detail{
        border: 1px solid #dedede;
    }
    .classify-detail a{
        text-decoration: none;
    }

    .detail-title{
        display: inline-block;
        padding: 8px 6px;
        width: 400px;
    }
    .detail-title span:hover{
        color: coral;
        cursor: pointer;
    }
    .classify-ul{
        list-style: none;
    }
    .classify-ul td{
        border-bottom: #dedede 1px solid;
    }
    .dayTitle{
        color: #fff;
        background-color: #45bcf9;
        padding: 8px 6px;
        font-size: 12px;
        display: inline-block;
        vertical-align: top;
        position: relative;
        left: -20%;
        margin-top: 2px;
    }

    .dayTitle span:hover{
        color: coral;
        cursor: pointer;
    }

    .classify-name{
        display: inline-block;
        vertical-align: top;
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
    .classify-detail-1-1{
        display: inline-block;
        vertical-align: top;
        height: 25px;
        border: coral 3px solid;
        border-radius: 20%;
        padding: 5px;
        text-align: center;
    }
    .classify-detail-1-2{
        display: inline-block;
        vertical-align: top;
        height: 23px;
        margin: 2px;
        border: coral 3px solid;
        border-radius: 20%;
        padding: 5px;
        text-align: center;
    }

    .classify-desce{
        border-bottom: #45bcf9 3px solid;
    }
    .page{
        position: relative;
        left: 28%;
    }
</style>>