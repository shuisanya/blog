<template>
    <div class="main-detail">
        <el-tabs v-model="activeName" type="card" @tab-click="handleClick">
            <el-tab-pane label="全部文章" name="all">
            </el-tab-pane>
            <el-tab-pane label="分类文章" name="classify">
            </el-tab-pane>
            <el-tab-pane label="标签文章" name="label">
            </el-tab-pane>
            <el-tab-pane label="归档文章" name="date">
            </el-tab-pane>
            <el-tab-pane label="草稿文章" name="draft">
            </el-tab-pane>

            <keep-alive>
                <router-view></router-view>
            </keep-alive>
        </el-tabs>
    </div>
</template>

<script>
    import All_Article from '@/pages/management/All_Article.vue';
    import Draft_Article from '@/pages/management/Draft_Article.vue';
    import Date_Article from '@/pages/management/Date_Article';
    import Label_Article from '@/pages/management/Label_Article.vue';
    import Classify_Article from '@/pages/management/Classify_Article'

    import { ref } from 'vue';  
import { useRouter } from 'vue-router';
    export default {
        name:"Management_Main_Detail",
        components:{
            All_Article,Draft_Article,Date_Article,Label_Article,Classify_Article
        },

        setup(){
            let activeName = ref('first');

            let router = useRouter()

            function handleClick(tab, event) {
                if(tab.props.name === "all"){
                    router.push({
                        name:"all",
                        params:{
                            currentPage:1,
                            pageSize:10,
                        }
                    })
                }else if(tab.props.name === "classify"){
                    router.push({
                        name:"cls",
                        params:{
                            currentPage:1,
                            pageSize:2,
                        }
                    })
                }else if(tab.props.name === "label"){
                    router.push({
                        name:"lab",
                        params:{
                            currentPage:1,
                            pageSize:2,
                        }
                    })
                }else if(tab.props.name === "date"){
                    router.push({
                        name:"dt",
                        params:{
                            currentPage:1,
                            pageSize:2,
                        }
                    })
                }else if(tab.props.name === "draft"){
                    router.push({
                        name:"draft",
                        params:{
                            currentPage:1,
                            pageSize:10,
                        }
                    }) 
                }
            }

            return{
                activeName,
                handleClick
            }
        }
    }
</script>

<style scoped>
    .main-detail{
        position: relative;
        width: 80%;
        height: 100%;
        display: inline-block;
        vertical-align: top;
        left: 5%;
        border: #87CEFF 1px solid;
    }

    .main-enter-active{
        animation: handoff 0.5s;
    }

    .main-leave-active{
        animation: handoff 0.5s reverse;
     }  

    @keyframes handoff {
        from{
            transform: translateX(100%);
        }
        to{
            transform: translateX(0);
        }
    }
</style>>
