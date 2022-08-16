<template>
    <div class="footer">
        <div class="box">
            <div>
                <img src="../../img/001.png" alt="联系方式">
            </div>
        </div>
        <div class="box">
            <h3>联系我</h3>
            
            <p>1264125219@qq.com</p> 
            <p><a href="https://www.cnblogs.com/shuisanya/" target="_blank">www.cnblogs.com/shuisanya</a> </p>
        </div>
        <div class="box">
            <div>
                <img src="../../img/002.png" alt="联系方式">
            </div>
        </div>
        <div class="box">
            <h3>BLOG</h3>
            <p>不登上悬崖，怎么能领略一览众山小的绝顶风光；不行万里路，怎么能见到天地之广阔。</p>
        </div>
    </div>
</template>

<script>
export default {
    name:'Footer'
}
</script>

<style scoped>
    .footer{
        height: 200px;
        background-color: #87CEFF;
        position: relative;
    }
    .box{
        position: relative;
        height: 180px;
        width: 20%;
        margin: 10px;
        display: inline-block;
        left: 7%;
        vertical-align: top;
        color: #ffff;
    }
    .box div{
        position: relative;
        left: 20%;
        top: 10%;
    }
    .box h3{
        text-align: center;
    }
    .box p{
        text-align: center;
    }
    .box a{
        text-decoration: none;
        color: #ffff;
    }
    .box a:hover{
        color: coral;
    }
</style>>
