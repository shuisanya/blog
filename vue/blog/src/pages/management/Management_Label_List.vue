<template>
    <div class="all-label">
        <div class="label-top">
            <div class="label-top1">
                <div>
                    <el-button type="primary" @click="addLabel">
                        <el-icon><Plus/></el-icon>增加文章标签
                    </el-button>
                </div>
            </div>
            <div class="label-top1">
                <h2>标签列表</h2>
            </div>
        </div>

        <div class="label-list">
            <div class="list">
                <table>
                    <thead>
                        <tr class="list-thead">
                            <td>标签名</td>
                            <td>文章数</td>
                            <td>更新时间</td>
                            <td>操作</td>
                            <td>操作</td>
                        </tr>
                    </thead>

                    <tbody class="list-tbody">
                        <tr v-for="label in labelList" :key="label.labelCode">
                            <td>
                                <span>
                                    {{label.labelName}}
                                </span> 
                            </td>
                            <td>{{label.blogs.length}}</td>
                            <td>{{time(label.labelUpdateTime)}}</td>
                            <td>
                                <el-button type="primary" @click="updateLabel(label.labelCode,label.labelName)" circle>
                                    <el-icon><Edit/></el-icon>
                                    编辑
                                </el-button>
                            </td>
                            <td>
                                <el-button type="danger" @click="deleteLabel(label.labelCode,label.labelName)" circle>
                                    <el-icon><Close/></el-icon>
                                    删除
                                </el-button> 
                            </td>
                        </tr>
                    </tbody>
                </table>
            </div>

            <div class="none"></div>
        </div>

        <div class="page">
            <el-pagination
            @current-change="currentChange"
            @size-change="sizeChange"
            :page-size="page.pageSize"
            :page-sizes="[5,10,15]"
            :current-page="page.currentPage"
            background
            layout="total, sizes, prev, pager, next, jumper"
            :total="page.pageTotalCount">
            </el-pagination>
        </div>
    </div>
</template>

<script>
    import {ref, getCurrentInstance, reactive, computed, onMounted} from "vue"
    import {useStore} from "vuex"
    import {useRoute,useRouter} from "vue-router"
    // 将时间戳转换为日期的库
    import moment from 'moment'
    export default {
        name:"Management_Label_List",

        setup(){
            const {proxy} = getCurrentInstance()
            const store = useStore()
            let page = store.state.label.page
            let labelList = computed(() => store.state.label.labelList)
            let route = useRoute()
            let router = useRouter()

            page.currentPage = route.params.currentPage
            page.pageSize = route.params.pageSize
            

            //当也页码改变时 currentPage页码
            function currentChange(currentPage){
                page.currentPage = currentPage
                store.dispatch("label/queryLabels",page)
                router.push({
                    name:route.name,
                    params:{
                        currentPage:currentPage,
                        pageSize:page.pageSize
                    }
                })
            }

            // 每页数量改变时
            function sizeChange(pageSize){
                page.pageSize = pageSize
                page.currentPage = 1
                store.dispatch("label/queryLabels",page)
                router.push({
                    name:route.name,
                    params:{
                        currentPage:page.currentPage,
                        pageSize:pageSize
                    }
                })
            }

            
            function addLabel() {
                proxy.$prompt('请输入标签名', '新建标签', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                inputPattern: /^.{1,20}$/,
                inputErrorMessage: '不能为空'
                }).then(({ value }) => {
                    proxy.$axios.get(`${proxy.PATH}/manage/label/addLabel/${value}`).then(
                        response => {
                            proxy.$message({
                                type: 'success',
                                message: response.data + "：" + value
                            });
                            store.dispatch("label/queryLabels",page)
                        },
                        error => {
                            console.log('请求失败了',error.message)
                        }
                    )
                }).catch(() => {
                    proxy.$message({
                        type: 'info',
                        message: '取消输入'
                    });       
                });
            }

            function updateLabel(labelCode,labelName) {
                proxy.$prompt('请输入标签名', '编辑标签', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                inputPattern: /^.{1,20}$/,
                inputErrorMessage: '不能为空或者名称未更改',
                inputValue: labelName
                }).then(({ value }) => {
                    if(labelName !== value){
                        const label = {
                            labelName:value,
                            labelCode:labelCode
                        }
                        let strlabel = JSON.stringify(label)
                        proxy.$axios.get(`${proxy.PATH}/manage/label/updateLabel/${strlabel}`).then(
                            response => {
                                proxy.$message({
                                    type: 'success',
                                    message: response.data + "：" + value
                                });
                                store.dispatch("label/queryLabels",page)
                            },
                            error => {
                                console.log('请求失败了',error.message)
                            }
                        )
                    }
    
                }).catch(() => {
                proxy.$message({
                    type: 'info',
                    message: '取消输入'
                });       
                });
            }

            function deleteLabel(labelCode,labelName){
                proxy.$confirm(`是否需要删除该标签：`+labelName, '删除操作', {
                    distinguishCancelAndClose: true,
                    confirmButtonText: '删除',
                    cancelButtonText: '放弃删除'
                    })
                    .then(() => {
                        proxy.$axios(`${proxy.PATH}/manage/label/deleteLabel/${labelCode}`).then(
                            response => {
                                proxy.$message({
                                    type: 'success',
                                    message: response.data + "：" + labelName
                                });
                                store.dispatch("label/queryLabels",page)
                            },
                            error => {
                                console.log('请求失败了',error.message)
                            }
                        )
 
                    })
                    .catch(action => {
                        proxy.$message({
                        type: 'info',
                        message: action === 'cancel'
                            ? '放弃删除'
                            : '停留在当前页面'
                    })
                });
            }

            // 组件挂载之后
            onMounted(() => {
                store.dispatch("label/queryLabels",page)
            })

            function time(time){
                    return moment(time).format("YYYY-MM-DD HH:mm:ss")
            }

            return{
                page,
                labelList,
                addLabel,
                updateLabel,
                currentChange,
                sizeChange,
                time,
                deleteLabel
            }
        }


    }
</script>

<style scoped>
    .all-label{
        position: relative;
        width: 78%;
        height: 100%;
        display: inline-block;
        vertical-align: top;
        left: 5%;
        border: #87CEFF 1px solid;
        overflow-x: hidden;
    }
    .label-top{
        height: 10%;
    }
    .label-top1{
        position: relative;
        width: 40%;
        height: 80%;
        margin: 1%;
        left: 8%;
        display: inline-block;
        vertical-align: top;
        background-color: #E8E8E8;
        text-align: center;
    }
    .label-top1 div{
        position: relative;
        top: 28%;
    }
    .label-top1 h2{
        color: green;
    }
    .list{
        width: 100%;
        position: relative;
        display: inline-block;
    }
    .none{
        display: inline-block;
    }
    .list table{
        width: 100%;
        border-top: #87CEFF 2px solid;
        border-bottom: #87CEFF 2px solid;
        margin: 2%;
        border-collapse: collapse;
    }
    .list-thead td{
        height: 45px;
        color: blue;
        font-size: 18px;
        font-weight: bolder;
        border-bottom: #87CEFF 1px solid;
    }
    .list-tbody td{
        height: 38px;
        font-size: 13px;
        border-bottom: #57626f solid 1px;
    }
    .list-tbody span{
        cursor: pointer;
    }
    .list-tbody span:hover{
        color: coral;
    }
    tbody tr:nth-of-type(even){
    background-color: #FFFAFA;
    }
    tbody tr:nth-of-type(odd){
        background-color: #E8E8E8;
    }
    .page{
        position: relative;
        left: 28%;
    }
</style>>
