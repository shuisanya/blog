<template>
    <div class="all-article">
        <div class="article-list">
            <div class="list">
                <table>
                    <thead>
                        <tr class="list-thead">
                            <td>标题</td>
                            <td>更新时间</td>
                            <td>发布状态</td>
                            <td>推荐数</td>
                            <td>阅读数</td>
                            <td>操作</td>
                            <td>操作</td>
                        </tr>
                    </thead>

                    <tbody class="list-tbody">
                        <tr v-for="blog in blogList" :key="blog.code">
                            <td>
                                <span @click="goArticle(blog.code)">
                                    {{blog.title}}
                                </span> 
                            </td>
                            <td>{{time(blog.updateTime)}}</td>
                            <td>{{blog.release === 1 ? '已发布':'未发布'}}</td>
                            <td>{{blog.recommendNum}}</td>
                            <td>{{blog.readNum}}</td>
                            <td>
                                <el-button type="primary" @click="editorBolg(blog.code)" circle>
                                    <el-icon><Edit/></el-icon>
                                    编辑
                                </el-button>
                            </td>
                            <td>
                                <el-button type="danger" @click="deleteBlog(blog.code,blog.title)" circle>
                                    <el-icon><Close/></el-icon>
                                    删除
                                </el-button> 
                            </td>
                        </tr>
                    </tbody>
                </table>
            </div>

            <div class="none">

            </div>
  
        </div>

        <div class="page">
            <el-pagination
            @current-change="currentChange"
            @size-change="sizeChange"
            background
            :page-size="page.pageSize"
            :page-sizes="[5,10,15]"
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
        name:"All_Article",
        setup(){
            const {proxy} = getCurrentInstance()
            const store = useStore()
            let all = store.state.blog.all
            let page = all.page
            let blogList = computed(() => all.blogList) 

            let route = useRoute()
            let router = useRouter()

            page.currentPage = route.params.currentPage
            page.pageSize = route.params.pageSize

            //当也页码改变时 currentPage页码
            function currentChange(currentPage){
                page.currentPage = currentPage
                page.release = 1
                store.dispatch("blog/queryBlogs",page)
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
                page.release = 1
                store.dispatch("blog/queryBlogs",page)
                router.push({
                    name:route.name,
                    params:{
                        currentPage:page.currentPage,
                        pageSize:pageSize
                    }
                })
            }

            function editorBolg(code){
                router.push({
                    name:"editor",
                    params:{
                        code:code
                    }
                })
            }

            function deleteBlog(code,title){
                proxy.$confirm(`是否需要删除该博客：`+title, '删除操作', {
                    distinguishCancelAndClose: true,
                    confirmButtonText: '删除',
                    cancelButtonText: '放弃删除'
                    })
                    .then(() => {
                        proxy.$axios.get(`${proxy.PATH}/manage/blog/deleteBlog/${code}`).then(
                            response => {
                                proxy.$message({
                                    type: 'success',
                                    message: response.data + "：" + title
                                });
                                store.dispatch("blog/queryBlogs",page)
                            },
                            error => {
                                console.log('请求失败了',error.message)
                            }
                        )
 
                    })
                    .catch(action => {
                        proxy.$message({
                        type: 'info',
                        message: action === 'cancel'
                            ? '放弃删除'
                            : '停留在当前页面'
                    })
                });
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
                page.release = 1
                store.dispatch("blog/queryBlogs",page)
            })

            function time(time){
                return moment(time).format("YYYY-MM-DD HH:mm:ss")
            }

            return{
                page,
                blogList,
                currentChange,
                sizeChange,
                time,
                editorBolg,
                deleteBlog,
                goArticle
            }
        }
    }
</script>

<style scoped>
    .list{
        width: 100%;
        position: relative;
        display: inline-block;
    }
    .none{
        display: inline-block;
    }
    .list table{
        width: 100%;
        border-top: #87CEFF 2px solid;
        border-bottom: #87CEFF 2px solid;
        margin: 2%;
        border-collapse: collapse;
    }
    .list-thead td{
        height: 45px;
        color: blue;
        font-size: 18px;
        font-weight: bolder;
        border-bottom: #87CEFF 1px solid;
    }
    .list-tbody td{
        height: 38px;
        font-size: 13px;
        border-bottom: #57626f solid 1px;
    }
    .list-tbody span{
        cursor: pointer;
    }
    .list-tbody span:hover{
        color: coral;
    }
    tbody tr:nth-of-type(even){
    background-color: #FFFAFA;
    }
    tbody tr:nth-of-type(odd){
        background-color: #E8E8E8;
    }
    .page{
        position: relative;
        left: 28%;
    }
</style>>
