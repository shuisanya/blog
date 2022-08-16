<template>
    <div class="label">
        <div class="label-title">
            <h3>标签</h3>
            <div>
                <span>共 <h3> {{labels.length}} </h3> 个</span>
            </div>
        </div>

        <div class="label-detail">

            <div class="label-detail-1" v-for="label in labels" :key="label.labelCode" @click="queryBlogsByLabelCode(label.labelCode)">
                <div class="label-detail-1-1">
                    <span><el-tag >{{label.labelName}}</el-tag></span> 
                </div>
                <div class="label-detail-1-2">{{label.blogs.length}}</div>
            </div>

        </div>
    </div>
</template>

<script>
    import {useRoute, useRouter} from 'vue-router'
    import {ref,onMounted,getCurrentInstance} from 'vue'
    import {useStore} from 'vuex'
    export default {
        name:"Main_Home_Label",
        setup(){
            const {proxy} = getCurrentInstance()
            const router = useRouter();
            const route = useRoute();
            const store = useStore()
            const labels = ref([])

            let indexBlog = store.state.indexBlog
            let page = indexBlog.page

            page.currentPage = route.params.currentPage
            page.pageSize = route.params.pageSize
            
            const labelCode = route.params.labelCode

            function queryBlogsByLabelCode(labelCode){
                page.labelCode = labelCode
                page.release = 1
                page.classifyCode = ""
                page.searchInput = ""
                store.dispatch("indexBlog/queryBlogs",page)
                router.push({
                    name:route.name,
                    params:{
                        currentPage:page.currentPage,
                        pageSize:page.pageSize,
                        labelCode:labelCode
                    }
                })
            }


            function queryAllLabels(){
                proxy.$axios.get(`${proxy.PATH}/index/label/queryAllLabels`).then(
                    respones =>{
                        labels.value = respones.data
                    },
                    error => {
                        console.log('请求失败了',error.message)
                    }
                )
            }

            onMounted(() => {
                queryAllLabels()
                page.release = 1
                page.searchInput = ""
                if(labelCode !== "allBlogs"){
                    page.labelCode = labelCode
                }
                store.dispatch("indexBlog/queryBlogs",page)
            })

            return{
                labels,
                queryBlogsByLabelCode
            }
        }
    }
</script>

<style scoped>
    .label{
        background: #fff;
        border: 1px solid #dedede;
        margin-bottom: -1px;
        margin-bottom: 5%;
    }
    .label-title{
        background: #fff;
        border: 1px solid #dedede;
    }
    .label-title div{
        position: relative;
        left: 88%;
        display: inline-block;
    }
    .label-title h3{
        display: inline-block;
        color: green;
    }
    .label-title div h3{
        color: red;
    }
    .label-detail{
        background: #fff;
        border: 1px solid #dedede;
    }
    .label-detail-1{
        margin: 5px 10px 5px 10px;
        display: inline-block;
        vertical-align: top;
    }
    .label-detail-1:hover{
        color: #87CEFF;
        cursor: pointer;
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
</style>>
