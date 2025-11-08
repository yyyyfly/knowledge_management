import { createApp } from 'vue'
import { createPinia } from 'pinia'
import App from './App.vue'
import router from './router'
import './assets/main.css'

// 导入Font Awesome
import '@fortawesome/fontawesome-free/css/all.css'

// 导入笔记内容样式
import './styles/note-content.css'

const app = createApp(App)

app.use(createPinia())
app.use(router)

app.mount('#app') 