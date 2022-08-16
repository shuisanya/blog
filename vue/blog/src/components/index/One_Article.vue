<template>
    <div class="day" role="article">
        <div class="dayTitle">
            <sapn @click="goArticle(blog.code)">{{time(blog.updateTime)}}</sapn>
        </div>
        <div class="postTitle" role="heading" aria-live="2">
            <span @click="goArticle(blog.code)"> {{blog.title}}</span>
        </div>
        <div class="postCon">
            <div class="c_b_p_desc">
                摘要：{{blog.content}}
                <br/>
                <span @click="goArticle(blog.code)" class="c_b_p_desc_readmor">阅读全文</span>
            </div>
        </div>
        <div class="clear"></div>
        <div class="postDesc">
            shuisanya @ {{time2(blog.updateTime)}} 水三丫
            <span class=""> 阅读({{blog.readNum}}) </span>
            <span class=""> 评论(0) </span>
            <span class=""> 推荐({{blog.recommendNum}}) </span>
        </div>
        <div class="clear"></div>
    </div>
</template>

<script>
    import {useRouter} from "vue-router"
    // 将时间戳转换为日期的库
    import moment from 'moment'
    export default {
        name:"One_Artucle",
        props:['blog'],
        setup(){
            const router = useRouter();

            function goArticle(code){
                const newPage = router.resolve({
                    name:"article",
                    params:{
                        id:code
                    }
                })
                window.open(newPage.href,'_blank')
            }

            function time(time){
                return moment(time).format("YYYY年MM月DD日")
            }
            function time2(time){
                return moment(time).format("YYYY-MM-DD HH:mm:ss")
            }
            return {
                goArticle,
                time,
                time2
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
        background-color:blue;
        width: 60%;
        display: inline-block;
        vertical-align: top;
        left: 8%;
        margin: 1%;
    }
    .day{
        background: #fff;
        padding: 20px;
        border: 1px solid #dedede;
        margin-bottom: -1px;
    }
    .day:hover{
        border: #45bcf9 1px solid;
    }
    .dayTitle{
        color: #fff;
        background-color: #45bcf9;
        padding: 8px 6px;
        font-size: 12px;
        display: block;
        float: left;
        margin-right: 10px;
        z-index: 10;
    }
    .dayTitle span{
        color: #fff;
    }
    .dayTitle:hover{
        background-color: coral;
        cursor: pointer;
    }
    /* a:link{
        text-decoration: none;
    } */
    .day .postTitle{
        font-size: 21px;
        line-height: 1.5em;
        float: left;
        clear: right;
    }
    .postTitle span {
        color: #7e8c8d;
        cursor: pointer;
    }
    .postTitle span:hover{
        color: #45bcf9;
        cursor: pointer;
    }
    .vertical-middle>* {
        vertical-align: middle;
    }
    .postCon{
        padding: 15px 0;
        clear: both;
    }
    .c_b_p_desc {
        font-size: 14px;
        color: #333;
        line-height: 200%;
        word-wrap: break-word;
        word-break: break-all;
        overflow: hidden;
    }
    .postCon span {
        text-decoration: none;
        color: #9ab26b;
        cursor: pointer;
    }
    .c_b_p_desc_readmore {
        color: #9ab26b;
        padding-left: 5px;
    }
    .c_b_p_desc_readmor:hover{
        color:coral;
    }
    .clear{
        clear: both;
    }
    .postDesc{
        clear: both;
        color: #bcbcbc;
        float: none;
        text-align: left;
        line-height: 200%;
        font-size: 12px;
    }
</style>>