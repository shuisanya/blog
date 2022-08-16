<template>
    <div class="login">

        <div class="login-from">
            <div class="login-top">
                <h3>
                    Sign In
                </h3>
            </div>

            <div class="login-msg">
                <span v-show="flag">
                    {{msg}}
                </span>
            </div>

             <el-form method="post" class="form" label-width="100px" size="medium">
                
                <el-form-item 
                prop="userCode"
                label="账号">
                    <el-input v-model="user.userCode"></el-input>
                </el-form-item>

                 <el-form-item 
                prop="userPasswrod"
                label="密码">
                    <el-input v-model="user.userPassword" type="password"></el-input>
                </el-form-item>

             </el-form>

             <div class="btn">
                <el-button class="login-btn"  @click="login" @keyup.enter="keyDown(e)" >登入</el-button>
                <el-button class="login-btn"  @click="reset" >重置</el-button>
             </div>

        </div>
    </div>
</template>

<script>
import { reactive ,ref, onMounted, onUnmounted, getCurrentInstance} from 'vue'
import { useRouter } from 'vue-router'
import md5 from "js-md5"

// const PATH = "http://localhost:8888/blog"
// const PATH = "http://192.168.0.11:8888/blog"
// import axios from 'axios'

    export default {
        name:"Login",

        setup(){
            const {proxy} = getCurrentInstance()
            // 错误信息
            let msg = ref("")
            // 开启错误信息
            let flag = ref(false)
            // 用户信息
            let user = reactive({
                userCode:"shuisanya",
                userPassword:"123456",
            })
            // 路由
            let router = useRouter();

            // 登入
            function login(){
                if(user.userCode === "" || user.userPassword === ""){
                    flag.value = true
                    msg.value = "账号或者密码不能为空"
                }else{
                    // md加密
                    user.userPassword = md5(user.userPassword)
                    let userString = JSON.stringify(user)
                    // 登入请求
                    proxy.$axios.post(`${proxy.PATH}/login/${userString}`).then(
                        response=>{
                            const data = response.data;

                            if(data.is_login){
                                localStorage.setItem("token",data.token)
                                localStorage.setItem("userCode",data.userCode)
                                router.push({
                                    name:"manage"
                                })
                            }else{
                               msg.value = data.msg 
                               flag.value = true
                            }
                        },
                        error=>{
                            console.log('请求失败了',error.message)
                        }
                    )
                }
            }

            //回车登入
            function keyDown(e){
                if(e.keyCode === 13){
                    login();
                }
            }
            // 组件挂载之后
            onMounted(() => {
                window.addEventListener('keydown', keyDown)
            })

            // 组件卸载之后
            onUnmounted(() => {
                window.removeEventListener('keydown', keyDown, false)
            })

            // 重置
            function reset(){
                user.userCode = ""
                user.userPassword = ""
                flag.value = false
            }

            return{
                user,
                msg,
                flag,
                login,
                reset,
                keyDown,
            }
        },
        
    }
</script>

<style>
    body{
        background-color:rgba(22, 159, 230, 0.1) 
    }
    .login{
        height: 680px;
    }
    .login-top{
        position: relative;
        color: green;
    }
    .login-top h3:hover{
        color: coral;
        cursor: pointer;
    }
    .login-msg{
        color: red;
    }
    .login-from{
        position: relative;
        width: 25%;
        height: 35%;
        padding: 1%;
        left: 37%;
        top: 25%;
        background-color: rgba(22, 159, 230, 0.1);
        text-align: center;
    }
    .form{
        position: relative;
        margin: 1%;
        text-align: center;
        right: 8%;
        top: 6%;
    }
    .btn{
        position: relative;
        margin: 5%;
        left: 2%;
        top: 10%;
    }
    .login-btn{
        background-color: rgba(22, 159, 230, 0.2);
    }
</style>>