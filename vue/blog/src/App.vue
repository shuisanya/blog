<template>
  <!-- <Index/> -->
  <!-- <Management/> -->
  <!-- <Login/> -->
  <!-- <HelloWorld/> -->
  <router-view></router-view>
</template>

<script>
import Index from './pages/Index.vue'
import Management from '@/pages/Management'
import Login from '@/pages/Login'
export default {
  name: 'App',
  components: {
    Index,Management,Login
  }
}
</script>
