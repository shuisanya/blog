<template>
    <div class="txtarticle">
<!--  -->
        <div class="label" v-for="label in labelList" :key="label.labelCode">
            <div class="label-title">
                <div class="label-detail-1">
                    <div class="label-detail-1-1">
                        <span><el-tag><a href="">{{label.labelName}}</a></el-tag></span> 
                    </div>
                    <div class="label-detail-1-2">{{label.blogs.length}}</div>
                </div>
            </div>

            <div class="label-detail">
                <ul class="label-ul">
                    <li v-for="blog in label.blogs" :key="blog.code">
                        <div class="detail-title">
                            <span @click="goArticle(blog.code)">{{blog.title}}</span>
                        </div>
    
                        <div class="dayTitle">
                            <span @click="goArticle(blog.code)">{{time(blog.createTime)}}</span>
                        </div>

                        <div class="label-name">
                            <div class="label-detail-1">
                                <div class="label-detail-1-1">
                                    <span><el-tag><a href="">{{label.labelName}}</a></el-tag></span> 
                                </div>
                            </div>
                        </div>

                        <div class="label-name">
                            <div class="label-detail-1">
                                <div class="label-detail-1-1">
                                    <span><el-tag>{{blog.release === 1 ? "发布":"未发布"}}</el-tag></span> 
                                </div>
                            </div>
                        </div>
                    </li>
                </ul>
            </div>

            <div class="label-desce"></div>   
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
        name:"Label_Article",
        setup(){
            const {proxy} = getCurrentInstance()
            const store = useStore()
            let label = store.state.blog.label
            let page = label.page
            let labelList = computed(() => label.labelList) 
            let route = useRoute()
            let router = useRouter()

            page.currentPage = route.params.currentPage
            page.pageSize = route.params.pageSize

            //当也页码改变时 currentPage页码
            function currentChange(currentPage){
                page.currentPage = currentPage
                store.dispatch("blog/queryLabelBlogs",page)
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
                store.dispatch("blog/queryLabelBlogs",page)
                router.push({
                    name:route.name,
                    params:{
                        currentPage:page.currentPage,
                        pageSize:pageSize
                    }
                })
            }

            onMounted(() => {
                store.dispatch("blog/queryLabelBlogs",page)
            })

            function time(time){
                return moment(time).format("YYYY-MM-DD HH:mm:ss")
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

            return{
                page,
                labelList,
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

    .label-title{
        text-align: center;
    }
    .label-detail{
        border: 1px solid #dedede;
    }
    .label-detail a{
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
    .label-ul{
        list-style: none;
    }
    .label-ul li{
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
        left: 0%;
        margin-top: 2px;
    }

    .dayTitle span:hover{
        color: coral;
        cursor: pointer;
    }

    .label-name{
        display: inline-block;
        vertical-align: top;
    }

    .label-detail-1{
        margin: 5px 10px 5px 10px;
        display: inline-block;
        vertical-align: top;
    }
    .label-detail-1-1{
        display: inline-block;
        vertical-align: top;
        height: 25px;
        padding: 5px;
        text-align: center;
    }
    .label-detail-1-1 a{
        text-decoration: none;
    }
    .label-detail-1-2{
        display: inline-block;
        width: 22px;
        height: 25px;
        vertical-align: top;
        border-radius: 50%;
        border: greenyellow 2px solid;
        padding: 5px;
        text-align: center;
    }

    .label-desce{
        border-bottom: #45bcf9 3px solid;
    }
    .page{
        position: relative;
        top: 2%;
        left: 28%;
    }
</style>>