import {createRouter,createWebHistory} from "vue-router"

import Index from "@/pages/Index"

import Main_Home_Articles from '@/pages/index/Main_Home_Articles'
import Main_Home_Date from '@/pages/index/Main_Home_Date'
import Main_Article from '@/pages/index/Main_Article'

import Mian_Home_All from '@/pages/index/Mian_Home_All'
import Main_Home_Classify from '@/pages/index/Main_Home_Classify'
import Mian_Home_Label from '@/pages/index/Mian_Home_Label'

import Management from '@/pages/Management'

import Management_Main_Detail from '@/pages/management/Management_Main_Detail'
import All_Article from '@/pages/management/All_Article'
import Classify_Article from '@/pages/management/Classify_Article'
import Label_Article from '@/pages/management/Label_Article'
import Date_Article from '@/pages/management/Date_Article'
import Draft_Article from '@/pages/management/Draft_Article'

import Managment_Main_Text from '@/pages/management/Managment_Main_Text'
import Management_Editor from '@/pages/management/Management_Editor'
import Management_Classify_List from '@/pages/management/Management_Classify_List'
import Management_Label_List from '@/pages/management/Management_Label_List'

import Login from "@/pages/Login"
import axios from "axios"

const router = createRouter({
    history:createWebHistory(),
    routes:[
        // 访问 / 默认跳到首页 也就是所有人可以见的页面
        // 登入页
        {
            name:"login",
            path:"/blog/login",
            component:Login,
            meta:{title:"Blog后台登录"},
        },

        {
            path:'/',
            redirect:{
                name:'index'
            }
        },
        // 首页
        {
            name:"index",
            path:"/blog/index",
            component:Index,
            redirect:{
                name:'home'
            },
            meta:{},
            children:[
                {
                    name:"home",
                    path:"home",
                    component:Main_Home_Articles,
                    redirect:{
                        name:'allBlog',
                        params:{
                            currentPage:1,
                            pageSize:10,
                        }
                    },
                    meta:{},
                    children:[
                        {
                            name:"allBlog",
                            path:"allBlog/:pageSize/:currentPage",
                            component:Mian_Home_All,
                            meta:{title:"Blog首页",
                                props($route){
                                    return{
                                        currentPage:$route.params.currentPage,
                                        pageSize:$route.params.pageSize,
                                    }
                                },
                            },
                        },
                        {
                            name:"index_classify",
                            path:"classify/:classifyCode/:pageSize/:currentPage",
                            component:Main_Home_Classify,
                            meta:{title:"Blog首页分类",
                                props($route){
                                    return{
                                        currentPage:$route.params.currentPage,
                                        pageSize:$route.params.pageSize,
                                        classifyCode:$route.params.classifyCode,
                                    }
                                },  
                            }, 
                        },
                        {
                            name:"index_label",
                            path:"label/:labelCode/:pageSize/:currentPage",
                            component:Mian_Home_Label,
                            meta:{title:"Blog首页标签",
                                props($route){
                                    return{
                                        currentPage:$route.params.currentPage,
                                        pageSize:$route.params.pageSize,
                                        labelCode:$route.params.labelCode,
                                    }
                                }, 
                            }, 
                        }
                    ]
                },
                {
                    name:"date",
                    path:"date/:pageSize/:currentPage",
                    component:Main_Home_Date,
                    meta:{
                        title:"Blog首页归档",
                        props($route){
                            return{
                                currentPage:$route.params.currentPage,
                                pageSize:$route.params.pageSize,
                            }
                        }, 
                        },
                },
                {
                    name:"article",
                    path:"article/:id",
                    component:Main_Article,
                    meta:{
                        props($route){
                            return{
                                id:$route.params.id,
                            }
                        },
                        title:"Blog文章"
                    },
                }
            ]
        },

        // 后台管理页
        {
            name:"manage",
            path:"/blog/manage",
            component:Management,
            // 刚进入跳入的位置
            redirect:{
                name:'detail'
            },
            meta:{},
            children:[
                // 管理页
                {
                    name:"detail",
                    path:"detail",
                    component:Management_Main_Detail,
                    redirect:{
                        name:'all',
                        params:{
                            currentPage:1,
                            pageSize:10,
                        }
                    },
                    meta:{title:"Blog后台管理页",
                    },
                    children:[
                        {
                            name:"all",
                            path:"all/:pageSize/:currentPage",
                            component:All_Article,
                            meta:{title:"Blog后台管理页的所有博客",
                                props($route){
                                    return{
                                        currentPage:$route.params.currentPage,
                                        pageSize:$route.params.pageSize,
                                    }
                                },
                            },
                        },
                        {
                            name:"cls",
                            path:"cls/:pageSize/:currentPage",
                            component:Classify_Article,
                            meta:{title:"Blog后台管理页的所有博客",
                                props($route){
                                    return{
                                        currentPage:$route.params.currentPage,
                                        pageSize:$route.params.pageSize,
                                    }
                                },
                            },
                        },
                        {
                            name:"lab",
                            path:"lab/:pageSize/:currentPage",
                            component:Label_Article,
                            meta:{title:"Blog后台管理页的所有博客",
                                props($route){
                                    return{
                                        currentPage:$route.params.currentPage,
                                        pageSize:$route.params.pageSize,
                                    }
                                },
                            },
                        },
                        {
                            name:"dt",
                            path:"dt/:pageSize/:currentPage",
                            component:Date_Article,
                            meta:{title:"Blog后台管理页的所有博客",
                                props($route){
                                    return{
                                        currentPage:$route.params.currentPage,
                                        pageSize:$route.params.pageSize,
                                    }
                                },
                            },
                        },
                        {
                            name:"draft",
                            path:"draft/:pageSize/:currentPage",
                            component:Draft_Article,
                            meta:{title:"Blog后台管理页的所有博客",
                                props($route){
                                    return{
                                        currentPage:$route.params.currentPage,
                                        pageSize:$route.params.pageSize,
                                    }
                                },
                            },
                        },
                    ]
                    
                    
                },
                // 新建文章页
                {
                    name:"text",
                    path:"text",
                    component:Managment_Main_Text,
                    meta:{title:"Blog后台新建文章页"},
                },
                // 编辑文章页
                {
                    name:"editor",
                    path:"editor/:code",
                    component:Management_Editor,
                    meta:{title:"Blog后台编辑文章页",
                        props($route){
                            return{
                                code:$route.params.code,
                            }
                        },
                    },
                },
                // 新建分类页
                {
                    name:"classify",
                    path:"classify/:pageSize/:currentPage",
                    component:Management_Classify_List,
                    meta:{
                        title:"Blog后台新建分类页",
                        props($route){
                            return{
                                currentPage:$route.params.currentPage,
                                pageSize:$route.params.pageSize,
                            }
                        },
                    },
                },
                // 新建标签页
                {
                    name:"label",
                    path:"label/:pageSize/:currentPage",
                    component:Management_Label_List,
                    meta:{
                        title:"Blog后台新建标签页",
                        props($route){
                            return{
                                currentPage:$route.params.currentPage,
                                pageSize:$route.params.pageSize,
                            }
                        },
                    },
                },
            ]

        },
    ]
});

const PATH = "http://localhost:8888/blog"
//全局前置路由守卫，权限检测，初始化时也会被调用
router.beforeEach((to,from,next)=>{
    const path = to.path
    
    if(path.indexOf("/blog/manage/") != -1 ){
        const token = localStorage.getItem("token");
        if(token !== null && token !== ""){
            axios.get(`${PATH}/token/${token}`).then(
                response=>{
                    if(response.data){
                        // console.log("有 token  true")
                        next();
                    }else{
                        // console.log("有 token  false")
                        next("/blog/login")
                    }
                    
                },
                error=>{
                    console.log('请求失败了',error.message)
                })
        }else{
            // console.log("无 token")
            next("/blog/login")
        }
    }else{
        // console.log("next 不是请求的 management")
        next()
    }


})

//后置路由守卫，没有next属性,
router.afterEach((to,from)=>{
    //换title的作用
    document.title = to.meta.title || 'Blog'
})

export default router;