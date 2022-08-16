<template>
    <div class="txtarticle">

        <router-view></router-view>
        <div v-for="blog in blogList" :key="blog.code">
            <One_Article :blog="blog" />
        </div>
        


        <div class="page">
            <el-pagination
            background
            @current-change="currentChange"
            :page-size="page.pageSize"
            :current-page="page.currentPage"
            layout="total, prev, pager, next, jumper"
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

    import One_Article from '../../components/index/One_Article.vue'
    import Main_Home_Classify from '@/pages/index/Main_Home_Classify'
    import Mian_Home_Label from '@/pages/index/Mian_Home_Label'
    export default {
        name:"Mian_Home_Artucles",
        components: { 
            One_Article,Main_Home_Classify,Mian_Home_Label
        },

        setup(){
            const {proxy} = getCurrentInstance()
            const store = useStore()
            let indexBlog = store.state.indexBlog
            let page = indexBlog.page
            let blogList = computed(() => indexBlog.blogList) 

            let route = useRoute()
            let router = useRouter()

            page.currentPage = route.params.currentPage
            page.pageSize = route.params.pageSize

            //当也页码改变时 currentPage页码
            function currentChange(currentPage){
                page.currentPage = currentPage
                page.release = 1
                store.dispatch("indexBlog/queryBlogs",page)
                router.push({
                    name:route.name,
                    params:{
                        currentPage:currentPage,
                        pageSize:page.pageSize
                    }
                })
            }

            // onMounted(() => {
            //     page.release = 1
            //     store.dispatch("indexBlog/queryBlogs",page)
            // })

            function time(time){
                return moment(time).format("YYYY-MM-DD HH:mm:ss")
            }

            return{
                page,
                blogList,
                currentChange,
                time,
            }
        }
    }
</script>

<style scoped>
    * {
        margin: 0;
        padding: 0;
    }
    .txtarticle{
        position: relative;
        width: 60%;
        display: inline-block;
        vertical-align: top;
        left: 8%;
        margin: 1%;
    }

    .page{
        position: relative;
        margin: 10px;
        left: 50%;
    }
</style>>