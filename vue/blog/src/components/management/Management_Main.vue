<template>
    <div class="main">
        <Management_Main_Nav/>

        <!-- <Management_Main_Detail/> -->
        <!-- <managment_Main_Text/> -->
        <!-- <Management_Classify_List/> -->
        <!-- <Management_Label_List/> -->
        <!-- <Management_Editor/> -->
        <keep-alive>
            <router-view></router-view>
        </keep-alive>
    </div>
</template>

<script>
    import Management_Main_Detail from '@/pages/management/Management_Main_Detail'
    import Management_Main_Nav from '@/components/management/Management_Main_Nav'
    import Managment_Main_Text from '@/pages/management/Managment_Main_Text'
    import Management_Classify_List from '@/pages/management/Management_Classify_List.vue'
    import Management_Label_List from '@/pages/management/Management_Label_List'
    import Management_Editor from '@/pages/management/Management_Editor'
    export default {
        name:"Management_Mian",
        components:{
            Management_Main_Detail,Management_Main_Nav,Managment_Main_Text,Management_Classify_List,Management_Label_List,Management_Editor
        }
    }
</script>

<style scoped>
    .main{
        position: relative;
        margin-top: 10px;
        margin-bottom: 10px;
        height: 750px;
    }

</style>>