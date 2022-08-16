<template>
    <Management_Head/>
    <Management_Main/>
    <Management_Footer/>
</template>

<script>
    import Management_Head from '@/components/management/Management_Head'
    import Management_Main from '@/components/management/Management_Main'
    import Management_Footer from '@/components/management/Management_Footer'
    export default {
        name:"Management_Home",
        components:{
            Management_Head,Management_Main,Management_Footer
        }
    }
</script>

<style scoped>

</style>>