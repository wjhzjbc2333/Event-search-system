<template>
  <!--
  <header>
    <img alt="Vue logo" class="logo" src="@/assets/logo.svg" width="125" height="125" />

    <div class="wrapper">
      <HelloWorld msg="You did it!" />

      <nav>
        <RouterLink to="/">Home</RouterLink>
        <RouterLink to="/login">About</RouterLink>
      </nav>
    </div>
  </header>
-->
  <div id="app">
        <div v-if="$route.meta.keepAlive">
            <Header></Header>
             <router-view></router-view>
        </div>
        <router-view v-if="!$route.meta.keepAlive"></router-view>
  </div>
  
</template>

<script setup>
import Header from './components/Header.vue';

</script>
