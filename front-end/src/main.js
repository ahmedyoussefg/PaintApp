import { createApp } from 'vue'
import App from './App.vue'
import PrimeVue from 'primevue/config';
import 'primevue/resources/themes/tailwind-light/theme.css';
import 'primevue/resources/primevue.min.css';
import 'primeicons/primeicons.css';
import Menu from 'primevue/menu';
import Tooltip from 'primevue/tooltip';

import { library } from '@fortawesome/fontawesome-svg-core'
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome'
import { fas } from '@fortawesome/free-solid-svg-icons'
library.add(fas)

const app = createApp(App);
app.component('Menu',Menu);
app.component('font-awesome-icon', FontAwesomeIcon);
app.directive('tooltip', Tooltip);
app.use(PrimeVue);
app.mount('#app')
