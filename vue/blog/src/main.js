import { createApp } from 'vue'

// el ui界面的引入
import ElementPlus from 'element-plus'
import * as ElementPlusIconsVue from '@element-plus/icons-vue'
import 'element-plus/dist/index.css'

// markdown 的引入
import VueMarkdownEditor from '@kangc/v-md-editor'
import '@kangc/v-md-editor/lib/style/base-editor.css'
// import vuepressTheme from '@kangc/v-md-editor/lib/theme/vuepress.js'
// import '@kangc/v-md-editor/lib/theme/style/vuepress.css'
// import Prism from 'prismjs'
import VMdpreview from '@kangc/v-md-editor/lib/preview'
import vuepressTheme from '@kangc/v-md-editor/lib/theme/vuepress.js'
import '@kangc/v-md-editor/lib/theme/style/vuepress.css'


// github主题
import githubTheme from '@kangc/v-md-editor/lib/theme/github.js'
import '@kangc/v-md-editor/lib/theme/style/github.css'
// 引入 highlight核心代码
import hljs from 'highlight.js/lib/core'
// 引入代码高亮
import json from 'highlight.js/lib/languages/json'
import java from 'highlight.js/lib/languages/java'
import javascript from 'highlight.js/lib/languages/javascript'
import c from 'highlight.js/lib/languages/c'
import cpp from 'highlight.js/lib/languages/cpp'
import armasm from 'highlight.js/lib/languages/armasm'
// 按需引入
hljs.registerLanguage('json',json)
hljs.registerLanguage('java',java)
hljs.registerLanguage('javascript',javascript)
hljs.registerLanguage('c',c)
hljs.registerLanguage('cpp',cpp)
hljs.registerLanguage('armasm',armasm)

import App from './App.vue'

import store from './store'
import router from './router'
import axios from 'axios'


// VueMarkdownEditor.use(vuepressTheme,{
//     Prism
// })
VMdpreview.use(vuepressTheme)

VueMarkdownEditor.use(githubTheme,{
    Hljs: hljs,
    extend(md){
        // 扩展插件
    }
})

const app = createApp(App)

app.use(store)
app.use(router)
app.use(ElementPlus)
for(const [key,component] of Object.entries(ElementPlusIconsVue)){
    app.component(key,component)
}
app.use(VueMarkdownEditor)
app.use(VMdpreview)
app.config.globalProperties.PATH = "http://localhost:8888/blog"
app.config.globalProperties.$axios = axios

app.mount('#app')


// 每次请求前 有token就带上
axios.interceptors.request.use( config => {
    const token = localStorage.getItem("token");
    if(token !== null && token !== ""){
        config.headers.token = token;
    }
    return config
})
