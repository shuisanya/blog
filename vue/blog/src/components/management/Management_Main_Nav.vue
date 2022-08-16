<template>
    <div class="main-nav">
        <div class="nav-title">
            <span>后台管理</span>
        </div>

        <div class="nav-item">
            <div @click="goIndex">
                <h3>首页</h3>
            </div>
        </div>

        <div class="nav-item">
            <div @click="goDetail">
                <h3>管理文章</h3>
            </div>
        </div>

        <div class="nav-item" >
            <div @click="goText">
                <h3>新建文章</h3>
            </div>
        </div>

        <div class="nav-item">
            <div @click="goClassify">
                <h3>新建分类</h3>
            </div>
        </div>

        <div class="nav-item">
            <div @click="goLabel">
                <h3>新建标签</h3>
            </div>
        </div>
    </div>
</template>

<script>
    import { useRouter } from 'vue-router'
    export default {
        nane:"Management_Main_Nav",

        setup(){
            const router = useRouter();

            function goIndex(){
                router.push({
                    name:"index"
                })
            }

            function goDetail(){
                router.push({
                    name:"detail",
                })
            }

            function goText(){
                router.push({
                    name:"text"
                })
            }

            function goClassify(){
                router.push({
                    name:"classify",
                    params:{
                        currentPage:Number(1),
                        pageSize:Number(10)
                    }
                })
            }

            function goLabel(){
                router.push({
                    name:"label",
                    params:{
                        currentPage:Number(1),
                        pageSize:Number(10)
                    }
                })
            }

            return {
                goIndex,
                goDetail,
                goText,
                goClassify,
                goLabel
            }

        }
    }
</script>

<style>
    .main-nav{
        position: relative;
        width: 15%;
        height: 100%;
        display: inline-block;
        background-color: rgb(231, 228, 234);
        vertical-align: top;
        left: 4%;
        border: #87CEFF 1px solid;
    }
    .nav-title{
        height: 8%;
        background-color: #87CEFF;
        text-align: center;
    }
    .nav-title span{
        position: relative;
        top: 20%;
        color: green;
        font-size: 15px;
        font-weight: bolder;
    }
    .nav-item{
        height: 15%;
        margin: 1%;
        position: relative;
    }
    .nav-item div{
        margin: 1%;
        width: 60%;
        height: 50%;
        position: relative;
        background-color: rgba(181, 181, 205, 0.5);
        left: 15%;
    }
    .nav-item div:hover{
        background: coral;
        color:cyan;
        cursor: pointer;
    }
    .nav-item div h3{
        position: relative;
        text-align: center;
        top: 15%;
        color: chocolate;
    }
</style>