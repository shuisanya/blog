<template>
    <div class="head">
        <div class="head-img">
            <div class="head-item1">
                <h1>BLOG</h1>
            </div>
            <div class="head-item1-1">
                <img src="../../assets/001.gif" alt="好看的图片">
            </div>
        </div>

        <div class="head-log">
            <div class="head-item1">
                <h1>水三丫</h1>
            </div>

            <div class="head-item2">
                <img src="../../assets/004.jpg" alt="好看的图片">
            </div>
        </div>

        <div class="head-login">
            <div class="login" v-on:mouseover="showOutLogin" v-on:mouseout="closeOutLogin">
                <img src="../../assets/003.jpg" alt="头像" >
                <div class="out-login" v-show="showFlag">
                    <br>
                    <span @click="changeLogin">切换</span>
                    <br>
                    <br>
                    <span @click="outLogin">注销</span>
                </div>
            </div>
            <div class="name">
                <h3>水三丫</h3>
            </div>
        </div>
    </div>
</template>

<script>
    import {ref} from "vue"
import { useRouter } from 'vue-router'
    export default {
        name:"Management_Head",
        setup(){
            let showFlag = ref(false)
            const router = useRouter()

            function showOutLogin(){
                showFlag.value = true
            }

            function closeOutLogin(){
                showFlag.value = false
            }

            function changeLogin(){
                
                localStorage.removeItem("token")
                localStorage.removeItem("userCode")
                router.push({
                    name:"login"
                })
                    
            }
            function outLogin(){
                
                localStorage.removeItem("token")
                localStorage.removeItem("userCode")
                router.push({
                    name:"index"
                })
                    
            }

            return {
                showOutLogin,
                closeOutLogin,
                changeLogin,
                outLogin,
                showFlag
            }
        }
    }
</script>

<style scoped>
    .head{
        margin-bottom: 10px;
        height: 150px;
        border: 1px solid #87CEFF;
        background-color: #87CEFF;
    }
    .head-img,.head-log,.head-login{
        position: relative;
        display: inline-block;
        vertical-align: top;
        height: 80%;
        width: 30%;
        margin: 1%;
    }
    .head-item1,.head-item2,.head-item1-1{
        position: relative;
        display: inline-block;
        vertical-align: top;
        height: 90%;
        width: 47%;
        margin: 5px;
        text-align: center;
        border: #87CEFF 1px solid;
    }
    .head-item1 h1{
        color: green;
    }
    .head-item1-1 img{
        width: 120px;
        height: 120px;
        position: relative;
        left: 2%;
    }
    .head-item2 img{
        width: 250px;
        height: 125px;
        position: relative;
        left: 2%;
    }
    .head-log .head-item1 h1{
        color: coral;
    }

    .login{
        position: relative;
        display: inline-block;
        vertical-align: top;
        width: 47%;
        left: 33%;
        top: 16%;
    }
    .out-login{
        position: absolute;
        width: 100px;
        height: 110px;
        background: rgba(232, 229, 229, 0.5);
        top: 6%;
        left: -3%;
        text-align: center;
        cursor: pointer;
    }
    .out-login span{
        color: blue;
    }
    .out-login span:hover{
        color: coral;
    }
    .login img{
        width: 80px;
        height: 80px;
        border-radius: 50%;
        border: #000 solid 2px;
    }
    .name{
        position: relative;
        display: inline-block;
        vertical-align: top;
        width: 47%;
        left:9%;
        top: 25%;
    }
    .name h3{
        color:navy;
    }

</style>>