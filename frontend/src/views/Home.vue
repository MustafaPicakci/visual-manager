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
          <div class="blog-masonry masonry-true">
            <div class="post-masonry col-md-4 col-sm-6">
              <div class="post-thumb">
                <img src="../assets/images/1.jpg" alt="" />
                <div class="title-over">
                  <h4><a href="#">Title one goes here</a></h4>
                </div>
                <div class="post-hover text-center">
                  <div class="inside">
                    <i class="fa fa-plus"></i>
                    <span class="date">25 Jan 2084</span>
                    <h4><a href="#">Title one goes here</a></h4>
                    <p>Cum sociis natoque penatibus et magnis dis parturient</p>
                  </div>
                </div>
              </div>
            </div>
            <!-- /.post-masonry -->
            <div class="post-masonry col-md-4 col-sm-6">
              <div class="post-thumb">
                <img src="../assets/images/2.jpg" alt="" />
                <div class="title-over">
                  <h4><a href="#">Second Image Title</a></h4>
                </div>
                <div class="post-hover text-center">
                  <div class="inside">
                    <i class="fa fa-plus"></i>
                    <span class="date">24 Jan 2084</span>
                    <h4><a href="#">Second Image Title</a></h4>
                    <p>Donec venenatis libero sapien, eu condimentum lacus</p>
                  </div>
                </div>
              </div>
            </div>
            <!-- /.post-masonry -->
            <div class="post-masonry col-md-4 col-sm-6">
              <div class="post-thumb">
                <img src="../assets/images/3.jpg" alt="" />
                <div class="title-over">
                  <h4><a href="#">Image Number Three</a></h4>
                </div>
                <div class="post-hover text-center">
                  <div class="inside">
                    <i class="fa fa-plus"></i>
                    <span class="date">23 Jan 2084</span>
                    <h4><a href="#">Image Number Three</a></h4>
                    <p>Sed vitae lacus eget ipsum scelerisque condimentum</p>
                  </div>
                </div>
              </div>
            </div>
            <!-- /.post-masonry -->
            <div class="post-masonry col-md-4 col-sm-6">
              <div class="post-thumb">
                <img src="../assets/images/4.jpg" alt="" />
                <div class="title-over">
                  <h4><a href="#">Aliquam sed magna risus</a></h4>
                </div>
                <div class="post-hover text-center">
                  <div class="inside">
                    <i class="fa fa-plus"></i>
                    <span class="date">22 Jan 2084</span>
                    <h4><a href="#">Aliquam sed magna risus</a></h4>
                    <p>Praesent pharetra blandit ex sed imperdiet</p>
                  </div>
                </div>
              </div>
            </div>
            <!-- /.post-masonry -->
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "Home",
  data() {
    return {
   
    };
  },
  created(){
       this.$store.dispatch("getUserImages");
  }
};
</script>
