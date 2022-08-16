<template>
    <div class="detail-article">
        <div class="post-detail">
            <div id="topics">
                <div class="post">

                    <h1 class="postTitle">
                            <span>
                                {{blog.title}}
                            </span>
                    </h1>

                    <div class="clear"></div>

                    <div class="postBoby">
                        <div id="cnblogs_post_body">
                            <v-md-editor :model-value="blog.content" mode="preview"></v-md-editor>

                        </div>

                        <div class="clear"></div>

                        <div id="blog_post_info_block">
                            <div>
                                <div id="green_channel">
                                    <el-tag><span>关注我</span> </el-tag>
                                    <el-tag @click="recommend(blog.code)"><span>推荐我</span></el-tag>
                                    <el-tag><span>收藏</span></el-tag>
                                </div>
    
                            </div>
                            <div class="clear"></div>
                            <div id="post_next_prev">
                                <span v-if="mapBlog.preBlog" @click="goArticle(mapBlog.preBlog.code)">上一篇：</span>
                                <span v-if="mapBlog.preBlog" @click="goArticle(mapBlog.preBlog.code)">{{mapBlog.preBlog.title}}</span>
                                <br>
                                <span v-if="mapBlog.nextBlog" @click="goArticle(mapBlog.nextBlog.code)">下一篇：</span>
                                <span v-if="mapBlog.nextBlog" @click="goArticle(mapBlog.nextBlog.code)">{{mapBlog.nextBlog.title}}</span>
                            </div>
                        </div>
                    </div>

                    <div class="postDesc">
                        shuisanya @ {{time(blog.createTime)}} 水三丫
                        <span class=""> 阅读({{blog.readNum}}) </span>
                        <span class=""> 评论(0) </span>
                        <span class=""> 推荐({{blog.recommendNum}}) </span>
                    </div>

                </div>
            </div>
        </div>

        <div class="comment-from">
            <div class="comment_show">
                <div>
                    <h4>评论内容</h4>
                    <div v-for="comment in commentList" :key="comment.commentCode">
                        <span>{{comment.commentName}}：</span>
                        <div class="bq_post_comment">
                            {{comment.commentContent}}
                        </div>
                    </div>
                </div>
            </div>

            <div class="clear"></div>

            <div class="comment_form_container ">
                <h2>评论区</h2>
                
                <div>
                    <span v-show="isCommentSubmit" style="color:red">评论的内容和昵称不能为空！！！</span><br>
                    <el-input v-model="comment.commentName" placeholder="请输入昵称" style="width : 400px"></el-input>
                    <textarea v-model="comment.commentContent" cols="100" rows="10" placeholder="请输入评论的内容"></textarea>
                </div>
                
                <div>
                    <el-button type="primary" @click="addComment(blog.code)" round>提交</el-button>
                    <el-button type="danger" @click="resetComment" round>重置</el-button>
                </div>

            </div>
        </div>
    </div>
</template>

<script>  
    import {ref,reactive,onMounted,onBeforeMount,getCurrentInstance} from "vue"
    import {useRoute, useRouter} from "vue-router"
    // 将时间戳转换为日期的库
    import moment from 'moment'
    export default {
        name:"Mian_Aricle",
        setup(){
            const route = useRoute()
            const router = useRouter()
            const {proxy} = getCurrentInstance()

            const code= route.params.id

            let blog =  ref({})

            let mapBlog =  ref({
            })

            let isCommentSubmit = ref(false)

            let comment = reactive({
                commentName:"",
                commentContent:""
            })

            let commentList = ref([])

            function queryBlogByCode(code){
                proxy.$axios.get(`${proxy.PATH}/index/blog/queryBlogByCode/${code}`).then(
                    response => {
                        blog.value = response.data
                        queryPreAndNextBlog(time(blog.value.updateTime))
                    },
                    error => {
                        console.log('请求失败了',error.message)
                    }
                )
            }

            function queryPreAndNextBlog(date){
                proxy.$axios.get(`${proxy.PATH}/index/blog/queryPreAndNextBlog/${date}`).then(
                    response => {
                        mapBlog.value = response.data
                    },
                    error => {
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

            function recommend(code){
                proxy.$axios.get(`${proxy.PATH}/index/blog/recommendBlog/${code}`).then(
                    response => {
                        alert(response.data)
                    },
                    error => {
                        console.log('请求失败了',error.message)
                    }
                ) 
            }

            function addComment(blogCode){
                if(comment.commentName != "" && comment.commentContent != ""){
                    comment.blogCode = blogCode
                    proxy.$axios.post(`${proxy.PATH}/index/comment/addComment`,comment).then(
                        response => {
                            resetComment()
                            queryCommentsByBlogCode(code)
                            alert(response.data)
                        },
                        error => {
                            console.log('请求失败了',error.message)
                        }
                    )
                }else {
                    isCommentSubmit.value = true
                }
            }

            function queryCommentsByBlogCode(blogCode){
                
                proxy.$axios.get(`${proxy.PATH}/index/comment/queryCommentsByBlogCode/${blogCode}`).then(
                    response => {
                        commentList.value = response.data
                    },
                    error => {
                        console.log('请求失败了',error.message)
                    }
                )
            }

            function resetComment(){
                comment.commentName = ""
                comment.commentContent = ""
                isCommentSubmit.value = false
            }

            // onBeforeMount(() => {
            //     queryBlogByCode(code)
            // })

            onMounted(() => {
                queryBlogByCode(code)
                queryCommentsByBlogCode(code)
            })

            function time(time){
                return moment(time).format("YYYY-MM-DD HH:mm:ss")
            }

            return {
                blog,
                mapBlog,
                comment,
                commentList,
                isCommentSubmit,
                time,
                goArticle,
                recommend,
                addComment,
                resetComment
            }
        }
    }
</script>

<style scoped>
    .detail-article{
        position: relative;
        width: 60%;
        display: inline-block;
        vertical-align: top;
        left: 8%;
        margin: 1%;
    }

    #topics{
        background: #fff;
        overflow: hidden;
        padding: 20px;
        border: 1px solid #dedede;
    }

    #topics .postTitle {
        font-size: 28px;
        font-weight: 400;
        margin-bottom: 20px;
        line-height: 1.8;
        color: #333;
    }

    #topics .postTitle a {
        color: #333;
    }

    #cnblogs_post_body {
        color: #333;
        line-height: 1.8;
        word-break: break-word;
        margin-bottom: 20px;
    }

    #blog_post_info_block {
        margin-top: 20px;
    }

    #post_next_prev {
        line-height: 200%;
        margin: 10px 0;
        font-size: 14px;
    }
    #post_next_prev span {
        text-decoration: none;
        color: #333;
    }
    #post_next_prev span:hover{
        color: coral;
        cursor: pointer;
    }

    #green_channel {
        padding: 10px 0;
        margin-bottom: 10px;
        margin-top: 10px;
        border: #c0c0c0 1px dashed;
        font-size: 12px;
        width: 350px;
        text-align: center;
    }

    #green_channel span:hover {
        color: coral;
        cursor: pointer;
    }

    #topics .postDesc {
        font-size: 14px;
        color: #777;
        line-height: 200%;
        margin: 10px 0;
    }

    .comment_form_container {
        border: 1px solid #dedede;
        padding: 20px;
        background-color: #fff;
    }

    .bq_post_comment {
        margin: 0 0 5px 0;
        font-style: normal;
        line-height: 180%;
        border: 1px solid #ddd;
        padding: 10px;
    }

    .clear {
        clear: both;
    }
</style>>

