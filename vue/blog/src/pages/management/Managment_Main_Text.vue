<template>
    <div class="main-text">
        <div class="text-top">
            <div class="text-top1">
                <span>新添文章</span>
            </div>
            <div class="text-title">
                <span>标题</span>
                <el-input
                placeholder="请输入标题"
                v-model="blog.title"
                clearable>
                </el-input>
            </div>
        </div>

        <div class="markdown">
            <v-md-editor 
            :autofocus="true"
            v-model="blog.content" 
            height="510px"
            placeholder="请输入内容"
            left-toolbar="undo redo clear | h bold italic strikethrough quote | ul ol table hr | image | save "
            :disabled-menus="[]"
            @upload-image="handleUploadImage"
            >
            </v-md-editor>
        </div>

        <div class="text-footer">
            <div class="footer1">
                <div class="footer1-1">
                    <span>类别：</span>
                </div>   
                <div class="footer1-2">
                    <el-select v-model="blog.classify" clearable placeholder="请选择类别">
                        <el-option
                        v-for="item in allClassifies"
                        :key="item.classifyCode"
                        :label="item.classifyName"
                        :value="item.classifyCode">
                        </el-option>
                    </el-select>
                </div>
            </div>
            <div class="footer1">
               <div class="footer1-1">
                    <span>标签：</span>
                </div>   
                <div class="footer1-2">
                    <el-select v-model="blog.labels" multiple placeholder="请选择标签">
                        <el-option
                        v-for="item in allLabels"
                        :key="item.labelCode"
                        :label="item.labelName"
                        :value="item.labelCode">
                        </el-option>
                    </el-select>
                </div>
            </div>
            <div class="footer2">
                <el-button type="primary" @click="release(1)" class="footer-button"><el-icon><Position/></el-icon>发布</el-button>
                <el-button type="success" @click="release(0)" class="footer-button"><el-icon><Edit/></el-icon>存为草稿</el-button>
                <el-button type="warning" @click="callDetail" class="footer-button"><el-icon><Close/></el-icon>取消</el-button>
            </div>
        </div>
    </div>
</template>

<script>
    import {reactive, ref, onMounted, computed, getCurrentInstance} from 'vue'
    import {useStore} from "vuex"
import { useRouter } from 'vue-router'
    export default {
        name:"Managment_Main_Text",
        components:{
        },
        setup(){
            const {proxy} = getCurrentInstance()

            let blog = reactive({
                title:"",
                content:"",
                classify:"",
                labels:ref([]),
                img:"",
                commentIs:0,
                release:0
            })

            const store = useStore()

            const router = useRouter()

            const allClassifies = computed(() => store.state.classify.allClassifies) 

            const allLabels = computed(() => store.state.label.allLabels) 


            function release(release){
                if(blog.title !== "" && blog.title !== null && blog.content !== '' && blog.content !== null){
                    blog.release = release
                    blog.commentIs = 1
                    blog.userCode = localStorage.getItem("userCode")
                    proxy.$axios.post(`${proxy.PATH}/manage/blog/addBlog`,blog).then(
                        response => {
                            alert(response.data + "：" + blog.title)
                            if(release === 1){
                                router.push({
                                    name:"detail"
                                })
                            }
                        },
                        error => {
                            console.log('请求失败了',error.message)
                        }
                    )
                }else{
                    alert("请输入标题或者内容!!!")
                }
            }

            function handleUploadImage(event,insertImage,files){
                for(let i in files){
                    const formData = new FormData();
	                formData.append('file', files[i]);
                    console.log(files[0])
                    proxy.$axios.post(`${proxy.PATH}/uploadImg`,formData).then(
                        response => {
                            insertImage({
                                url:response.data,
                                desc: 'DESC',
                            })
                        },
                        error => {
                            console.log('请求失败了',error.message)
                        }
                    )
                }
            }

            function callDetail(){
                router.push({
                    name:"detail"
                })
            }

            onMounted(() => {
                store.dispatch("classify/queryAllClassifies")
                store.dispatch("label/queryAllLabels")
            })

            return {
                allClassifies,
                allLabels,
                blog,
                handleUploadImage,
                release,
                callDetail
            }
        }

    }
</script>

<style scoped>
    .main-text{
        position: relative;
        width: 80%;
        height: 100%;
        display: inline-block;
        vertical-align: top;
        left: 5%;
        border: #87CEFF 1px solid;
    }
    .text-top{
        position: relative;
        height: 15%;
    }
    .text-top1{
        background: #87CEFF;
        height: 30%;
        margin-bottom: 1%;
        text-align: center;
        color: coral;
        font-size: 18px;
    }
    .text-title{
        text-align: center;
        color: blue;
        font-size: 20px;
        font-weight: bolder;
    }
    .markdown{
        position: relative;
        height: 70%;
        overflow-y: auto;
        overflow-x: hidden;
    }
    .text-footer{
        position: relative;
        height: 15%;
    }

    .footer1{
        position: relative;
        display: inline-block;
        vertical-align: top;
        width: 40%;
        height: 37%;
        margin: 1%;
        left: 8%;
        background-color: #D1EEEE;
        color: #000;
        font-weight: bolder;
    }
    .footer1 div{
        position: relative;
        display: inline-block;
        vertical-align: top;
    }
    .footer1-1{
        top: 18%;
        left: 28%;
    }
    .footer1-2{
        top: 10%;
        left: 30%;
    }
    .footer2{
        position: relative;
        height: 37%;
    }
    .footer-button{
        position: relative;
        margin-top: 5px;
        margin-left: 15%;
        left: 8%;
    }

</style>>