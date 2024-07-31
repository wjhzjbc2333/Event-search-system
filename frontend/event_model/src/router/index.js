import { createRouter, createWebHistory } from 'vue-router'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      component: () => import('../views/HomePage1.vue'),
      meta: {
        keepAlive: true
      },
    },
    {
      path: '/mapOfCHN',
      name: 'mapOfCHN',
      component: () => import('../views/mapOfCHN.vue'),
      meta: {
        keepAlive: true
      },
    },
    {
      path: '/mapOfUSA',
      name: 'mapOfUSA',
      component: () => import('../views/mapOfUSA.vue'),
      meta: {
        keepAlive: true
      },
    },
    
    {
      path: '/crisis',
      name: 'crisis',
      component: () => import('../views/CrisisEvents.vue'),
      meta: {
        keepAlive: true
      },
    },
    {
      path: '/crisis/KoreaWar',
      name: 'KoreaWar',
      component: () => import('../components/CrisisEvents/KoreaWar.vue'),
      meta: {
        keepAlive: true
      },
    },
    {
      path: '/crisis/VietnamWar',
      name: 'VietnamWar',
      component: () => import('../components/CrisisEvents/VietnamWar.vue'),
      meta: {
        keepAlive: true
      },
    },
    {
      path: '/crisis/BosnianWar',
      name: 'BosnianWar',
      component: () => import('../components/CrisisEvents/BosnianWar.vue'),
      meta: {
        keepAlive: true
      },
    },
    {
      path: '/crisis/AfghanistanWar',
      name: 'AfghanistanWar',
      component: () => import('../components/CrisisEvents/AfghanistanWar.vue'),
      meta: {
        keepAlive: true
      },
    },
    {
      path: '/crisis/IraqWar',
      name: 'IraqWar',
      component: () => import('../components/CrisisEvents/IraqWar.vue'),
      meta: {
        keepAlive: true
      },
    },
    {
      path: '/crisis/LibyaWar',
      name: 'LibyaWar',
      component: () => import('../components/CrisisEvents/LibyaWar.vue'),
      meta: {
        keepAlive: true
      },
    },
    {
      path: '/addEvent',
      name: 'addEvent',
      component: () => import('../views/addEvent.vue'),
      meta: {
        keepAlive: true
      },
    },
    {
      path: '/addEventSchema',
      name: 'addEventSchema',
      component: () => import('../components/AddEventSchema.vue'),
      meta: {
        keepAlive: true
      },
    },
    {
      path: '/addEventTheme',
      name: 'addEventTheme',
      component: () => import('../components/AddEventTheme.vue'),
      meta: {
        keepAlive: true
      },
    },
    {
      path: '/searchResults',
      name: 'searchResults',
      component: () => import('../views/SearchResults.vue'),
      meta: {
        keepAlive: true
      },
    },
    {
      path: '/eventDetail',
      name: 'eventDetail',
      component: () => import('../views/EventDetail.vue'),
      meta: {
        keepAlive: true
      },
    },
  ]
})

export default router
