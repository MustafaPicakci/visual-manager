<template>
  <div>
    <div class="content-bg"></div>
    <div class="bg-overlay"></div>
    <!-- SITE TOP -->
    <div class="site-top">
      <div class="site-header clearfix">
        <div class="container">
          <a href="#" class="site-brand pull-left"
            ><strong>Masonry</strong> Free Template</a
          >
          <div class="social-icons pull-right">
            <ul>
              <li><a href="#" class="fa fa-facebook"></a></li>
              <li><a href="#" class="fa fa-twitter"></a></li>
              <li><a href="#" class="fa fa-behance"></a></li>
              <li><a href="#" class="fa fa-dribbble"></a></li>
              <li><a href="#" class="fa fa-google-plus"></a></li>
            </ul>
          </div>
        </div>
      </div>
      <!-- .site-header -->
      <div class="site-banner">
        <div class="container">
          <div class="row">
            <div class="col-md-offset-2 col-md-8 text-center">
              <h2>
                Get free templates from <span class="blue">template</span
                ><span class="green">mo</span>
              </h2>
              <p>
                Masonry is free responsive template that can be used for any
                website. You may download, modify and use this layout for your
                personal or commercial websites. Please tell your friends about
                <span class="blue">template</span
                ><span class="green">mo</span>.com website. Thank you.
              </p>
            </div>
          </div>
          <div class="row">
            <form action="#" method="post" class="subscribe-form">
              <fieldset class="col-md-offset-4 col-md-3 col-sm-8">
                <input
                  type="email"
                  id="subscribe-email"
                  placeholder="Enter your email..."
                />
              </fieldset>
              <fieldset class="col-md-5 col-sm-4">
                <input
                  type="submit"
                  id="subscribe-submit"
                  class="button white"
                  value="Subscribe!"
                />
              </fieldset>
            </form>
          </div>
        </div>
      </div>
      <!-- .site-banner -->
    </div>
    <!-- .site-top -->

    <!----------------------------------------------------------------------------------->

    <!-- MAIN POSTS -->
    <div class="main-posts">
      <div class="container">
        <div class="row">
         
            <app-thumbnails :images="imageList"></app-thumbnails>
            
            <!-- /.post-masonry -->
          
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import Thumbnails from "../components/Thumbnails";
import {mapGetters} from "vuex"
export default {
  name: "Home",
  components: {
    "app-thumbnails": Thumbnails
  },
  data() {
    return {};
  },
  created() {
    this.$store.dispatch("getUserImages");
  },
  computed: {
   ...mapGetters({
     imageList:'getImages'
   })
  }
};
</script>
