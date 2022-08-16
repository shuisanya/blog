import {createStore} from "vuex"
import {ref} from "vue"

import axios from 'axios'

const PATH = "http://localhost:8888/blog"

const classify = {
    //命名空间
    namespaced:true,

    actions:{
        // 按需求查询类别Classfiy
        queryClassifies(store,data){
            let strdata = JSON.stringify(data)
            axios.get(`${PATH}/manage/classify/queryClassifies/${strdata}`).then(
                response=>{
                    store.commit('CHANGE_CLASSIFY_LIST',response.data)
                },
                error=>{
                    console.log('请求失败了',error.message)
                }
            )
        },
        // 查询全部类别的名称和编号
        queryAllClassifies(store){
            axios.get(`${PATH}/manage/classify/queryAllClassifies`).then(
                response => {
                    store.commit('CHANGE_ALL_CLASSIFIES',response.data)
                },
                error => {
                    console.log('请求失败了',error.message)
                }
            )
        }
    },

    mutations:{
        // 查询回来的分类数据进行更改
        CHANGE_CLASSIFY_LIST(state,data){
            state.page.pageTotalCount = data.pageTotalCount
            state.page.currentPage = data.currentPage
            state.page.pageSize = data.pageSize

            state.classifyList = data.items
        },
        // 更改全部类别信息
        CHANGE_ALL_CLASSIFIES(state,data){
            state.allClassifies = data
        }
    },

    state(){
        return{
            page:{
                // 总数
                pageTotalCount:20,
                // 当前页
                currentPage:1,
                // 每一页数量
                pageSize:10
            },
            // classify数据
            classifyList:[{
                // 类别code
                classifyCode:"001",
                blogs:[],
                //    类别名称
                classifyName:"Java",
                //  最近更新时间
                classifyUpdateTime:"2022-7-30"
            },],
            // 全部类别的名称和编号
            allClassifies:[]
        } 
    }
    
}

const label = {
    //命名空间
    namespaced:true,

    actions:{
        // 按需求查询标签label
        queryLabels(store,data){
            let strdata = JSON.stringify(data)
            axios.get(`${PATH}/manage/label/queryLabels/${strdata}`).then(
                response=>{
                    store.commit('CHANGE_LABEL_LIST',response.data)
                },
                error=>{
                    console.log('请求失败了',error.message)
                }
            )
        },
        // 查询全部标签
        queryAllLabels(store){
            axios.get(`${PATH}/manage/label/queryAllLabels`).then(
                response => {
                    store.commit('CHANGE_ALL_LABELS',response.data)
                },
                error => {
                    console.log('请求失败了',error.message)
                }
            )
        }
    },

    mutations:{
        // 查询回来的分类数据进行更改
        CHANGE_LABEL_LIST(state,data){
            state.page.pageTotalCount = data.pageTotalCount
            state.page.currentPage = data.currentPage
            state.page.pageSize = data.pageSize

            state.labelList = data.items
        },
        // 查询全部标签进行更改
        CHANGE_ALL_LABELS(state,data){
            state.allLabels = data
        }
    },

    state(){
        return{
            page:{
                // 总数
                pageTotalCount:20,
                // 当前页
                currentPage:1,
                // 每一页数量
                pageSize:10
            },
            // classify数据
            labelList:[{
                // 类别code
                labelCode:"001",
                blogs:[],
                //    类别名称
                labelName:"Java springboot",
                //  最近更新时间
                labelUpdateTime:"2022-7-30"
            },],
            // 全部标签的名称和编号
            allLabels:[]
        } 
    }
}


const blog = {
    //命名空间
    namespaced:true,

    actions:{
        //按需求查询博客Blogs
        queryBlogs(store,data){
            axios.post(`${PATH}/manage/blog/queryBlogs`,data).then(
                response=>{
                    if(data.release === 1){
                        store.commit('CHANGE_ALL_BLOG_LIST',response.data)
                    }else if(data.release === 0){
                        store.commit('CHANGE_DRAFT_BLOG_LIST',response.data)
                    } 
                },
                error=>{
                    console.log('请求失败了',error.message)
                }
            )
        },
        // 根据类别查询Blogs
        queryClassifyBlogs(store,data){
            axios.post(`${PATH}/manage/blog/queryClassifyBlogs`,data).then(
                response=>{
                    store.commit('CHANGE_CLASSIFY_LIST',response.data)
                },
                error=>{
                    console.log('请求失败了',error.message)
                }
            )
        },
        // 根据标签查询Blogs
        queryLabelBlogs(store,data){
            axios.post(`${PATH}/manage/blog/queryLabelBlogs`,data).then(
                response=>{
                    store.commit('CHANGE_LABEL_LIST',response.data)
                },
                error=>{
                    console.log('请求失败了',error.message)
                }
            )
        },
        // 根据时间查询Blogs
        queryDateBlogs(store,data){
            axios.post(`${PATH}/manage/blog/queryDateBlogs`,data).then(
                response=>{
                    store.commit('CHANGE_DATE_LIST',response.data)
                },
                error=>{
                    console.log('请求失败了',error.message)
                }
            )
        },
      
    },

    mutations:{
        // 发布下 查询回来的分类数据进行更改
        CHANGE_ALL_BLOG_LIST(state,data){
            state.all.page.pageTotalCount = data.pageTotalCount
            state.all.page.currentPage = data.currentPage
            state.all.page.pageSize = data.pageSize

            state.all.blogList = data.items
        },
        // 未发布下 查询回来的分类数据进行更改    
        CHANGE_DRAFT_BLOG_LIST(state,data){
            state.draft.page.pageTotalCount = data.pageTotalCount
            state.draft.page.currentPage = data.currentPage
            state.draft.page.pageSize = data.pageSize

            state.draft.blogList = data.items
        },
        // 根据类别查询Blogs
        CHANGE_CLASSIFY_LIST(state,data){
            state.classify.page.pageTotalCount = data.pageTotalCount
            state.classify.page.currentPage = data.currentPage
            state.classify.page.pageSize = data.pageSize
            state.classify.classifyList = data.items
        },
        // 根据标签查询Blogs
        CHANGE_LABEL_LIST(state,data){
            state.label.page.pageTotalCount = data.pageTotalCount
            state.label.page.currentPage = data.currentPage
            state.label.page.pageSize = data.pageSize
            state.label.labelList = data.items
        },
        // 根据时间查询Blogs
        CHANGE_DATE_LIST(state,data){
            state.date.page.pageTotalCount = data.page.pageTotalCount
            state.date.page.currentPage = data.page.currentPage
            state.date.page.pageSize = data.page.pageSize
            state.date.dateBlogs = data.dateBlogs
        },


    },

    state(){
        return{
            all:{
                page:{
                    // 总数
                    pageTotalCount:20,
                    // 当前页
                    currentPage:1,
                    // 每一页数量
                    pageSize:10
                },
                blogList:ref([])
            },
            
            draft:{
                page:{
                    // 总数
                    pageTotalCount:20,
                    // 当前页
                    currentPage:1,
                    // 每一页数量
                    pageSize:10
                },
                blogList:ref([])
            },

            // 文章分类
            classify:{
                page:{
                    // 总数
                    pageTotalCount:10,
                    // 当前页
                    currentPage:1,
                    // 每一页数量
                    pageSize:2
                },
                classifyList:[]
            },
            // 文章标签
            label:{
                page:{
                    // 总数
                    pageTotalCount:10,
                    // 当前页
                    currentPage:1,
                    // 每一页数量
                    pageSize:2
                },
                labelList:[]
            },
            // 文章发布时间分类
            date:{
                page:{
                    // 总数
                    pageTotalCount:10,
                    // 当前页
                    currentPage:1,
                    // 每一页数量
                    pageSize:2
                },
                dateBlogs:[]
            },

            // 编辑的文章
            editor:{
                blog:{}
            }
        } 
    }
       
}

const indexBlog = {
   //命名空间
   namespaced:true,

   actions:{
    // 首页查询blogs
      queryBlogs(store,data){
            axios.post(`${PATH}/index/blog/queryBlogs`,data).then(
                response => {
                    store.commit('CHANGE_BLOG_LIST',response.data)
                },
                error => {
                    console.log('请求失败了',error.message)
                }
            )
      }
      
   },

   mutations:{
        CHANGE_BLOG_LIST(state,data){
            state.page.pageTotalCount = data.pageTotalCount
            state.page.currentPage = data.currentPage
            state.page.pageSize = data.pageSize
            state.blogList = data.items
        }
   },

   state(){
       return{
           page:{
               // 总数
               pageTotalCount:20,
               // 当前页
               currentPage:1,
               // 每一页数量
               pageSize:10,

               classifyCode:"",

               labelCode:"",
           },
           // blog数据
           blogList:[],
       } 
   }
}


export default createStore({
    modules:{
        classify,
        label,
        blog,
        indexBlog
    }
})
