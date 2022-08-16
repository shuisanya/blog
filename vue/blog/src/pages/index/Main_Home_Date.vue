<template>
    <div class="txtarticle">
<!--  -->
        <div class="date" v-for="dateBlog in dateBlogs" :key="dateBlog.date">
            <div class="date-title">
                <h2>{{dateBlog.date}}</h2>
            </div>

            <div class="date-detail">
                <ul class="date-ul">
                    <li v-for="blog in dateBlog.blogs" :key="blog.code">
                        <div class="detail-title" >
                            <span @click="goArticle(blog.code)">{{blog.title}}</span>
                        </div>
    
                        <div class="dayTitle">
                            <span @click="goArticle(blog.code)">{{time(blog.createTime)}}</span>
                        </div>
                    </li>
                </ul>
            </div>

            <div class="date-desce"></div>   
        </div>

        <div class="page">
            <el-pagination
            @current-change="currentChange"
            @size-change="sizeChange"
            background
            :page-size="page.pageSize"
            :page-sizes="[2,3,4]"
            :current-page="page.currentPage"
            layout="total, sizes, prev, pager, next, jumper"
            :total="page.pageTotalCount">
            </el-pagination>
        </div>

    </div>

</template>

<script>
    import { reactive ,ref, getCurrentInstance,onMounted} from 'vue';
    import {useRoute, useRouter} from "vue-router"
    // 将时间戳转换为日期的库
    import moment from 'moment'
    export default {
        name:"Mian_Home_Date",
        setup(){
            const router = useRouter();
            const {proxy} = getCurrentInstance()
            const route = useRoute()

            let page = reactive({
                // 总数
                pageTotalCount:10,
                // 当前页
                currentPage:1,
                // 每一页数量
                pageSize:2
            })
            let dateBlogs = ref([])

            page.currentPage = route.params.currentPage
            page.pageSize = route.params.pageSize

            //当也页码改变时 currentPage页码
            function currentChange(currentPage){
                page.currentPage = currentPage
                queryDateBlogs(page)
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
                queryDateBlogs(page)
                router.push({
                    name:route.name,
                    params:{
                        currentPage:page.currentPage,
                        pageSize:pageSize
                    }
                })
            }

            function queryDateBlogs(page){
                proxy.$axios.post(`${proxy.PATH}/index/blog/queryDateBlogs`,page).then(
                    response=>{
                        page.pageTotalCount = response.data.page.pageTotalCount
                        page.currentPage = response.data.page.currentPage
                        page.pageSize = response.data.page.pageSize
                        dateBlogs.value = response.data.dateBlogs
                    },
                    error=>{
                        console.log('请求失败了',error.message)
                    }
                )
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
                queryDateBlogs(page)
            })

            function time(time){
                return moment(time).format("YYYY-MM-DD HH:mm:ss")
            }

            return {
                page,
                dateBlogs,
                goArticle,
                time,
                currentChange,
                sizeChange
            }
    
        }
    }
</script>

<style scoped>
    .txtarticle{
        position: relative;
        width: 60%;
        display: inline-block;
        vertical-align: top;
        left: 8%;
        margin: 1%;
    }

    .date-title{
        text-align: center;
    }
    .date-detail{
        border: 1px solid #dedede;
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
    .date-ul{
        list-style: none;
    }
    .date-ul li{
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
        left: 20%;
        margin-top: 2px;
    }

    .dayTitle span{
        color: #fff;
    }

    .dayTitle span:hover{
        color: coral;
        cursor: pointer;
    }

    .date-desce{
        border-bottom: #45bcf9 3px solid;
    }

    .page{
        position: relative;
        left: 20%;
    }
</style>>