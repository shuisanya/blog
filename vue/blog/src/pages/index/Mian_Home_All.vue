<template>
  
</template>

<script>
    import { onMounted } from 'vue'
    import { useStore } from 'vuex'
    export default {

        name:"Mian_Home_All",
        setup(){
            const store = useStore()
            let indexBlog = store.state.indexBlog
            let page = indexBlog.page
            

            onMounted(() => {
                page.classifyCode = ""
                page.labelCode = ""
                page.searchInput = ""
                page.release = 1
                store.dispatch("indexBlog/queryBlogs",page)
            })
        }
    }
</script>

<style>

</style>