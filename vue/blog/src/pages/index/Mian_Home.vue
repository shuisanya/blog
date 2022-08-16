<template>
    <div class="mian">
        <!-- <Main_Home_Articles/> -->

        <!-- <Main_Article/> -->

        <!-- <Main_Home_Date/> -->
        <router-view></router-view>
        <Main_nav/>
    </div>
</template>

<script>
import Main_Home_Articles from '@/pages/index/Main_Home_Articles'
import Main_Article from '@/pages/index/Main_Article'
import Main_nav from '@/components/index/Main_nav'
import Main_Home_Date from '@/pages/index/Main_Home_Date'
export default {
    name:"Mian_Home",
    components:{
        Main_Home_Articles,
        Main_nav,
        Main_Article,
        Main_Home_Date
    }
}
</script>

<style scoped>
    .main{
        position: relative;
    }
</style>>