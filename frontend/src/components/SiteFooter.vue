<template>
  <!-- Footer -->
  <div class="site-footer ">
    <footer>
      <div class="footer-top">
        <div class="container">
          <div v-if="currentUser" class="row">
            <div class="col-md-3 footer-about wow fadeInUp text-center">
              <h3>Visual <span class="green">Manager</span></h3>
              <hr />
              <div class="text-left">
                <p>
                  Visual Manager ekibi olarak görsellerinizi online olarak
                  organize edebileceğiniz platformumuzu sizlere sunuyoruz.
                </p>
              </div>
            </div>
            <div
              class="col-md-4 offset-md-1 footer-contact wow fadeInDown text-center"
            >
              <h3>İletişim</h3>
              <hr />
              <div class="text-left">
                <p>
                  <i class="fas fa-map-marker-alt"></i
                  ><font-awesome-icon icon="map-marker-alt" /> Düzce / Turkey
                </p>
                <p>
                  <i class="fas fa-phone"></i
                  ><font-awesome-icon icon="phone" /> (000) 123 45 67 890
                </p>
                <p>
                  <i class="fas fa-envelope"></i>
                  <font-awesome-icon icon="envelope" />
                  <a
                    href="mailto:info.VisualManager2@gmail.com"
                    style="color:gray;"
                    >info.VisualManager2@gmail.com</a
                  >
                </p>
              </div>
            </div>
            <div class="col-md-4 footer-links wow fadeInUp text-center">
              <div class="row">
                <div class="col">
                  <h3>Bağlantılar</h3>
                  <hr />
                </div>
              </div>
              <div class="row">
                <div class="col-md-12 ">
                  <div class="text-left">
                    <p>
                      <router-link
                        active-class="active-class"
                        exact
                        to="/home"
                        tag="a"
                        class="scroll-link"
                        ><font-awesome-icon icon="home" /> Anasayfa</router-link
                      >
                    </p>
                    <p>
                      <router-link
                        active-class="active-class "
                        exact
                        to="/myImages"
                        tag="a"
                        class="scroll-link"
                        ><font-awesome-icon icon="images" /> Galeri</router-link
                      >
                    </p>
                    <p>
                      <router-link
                        active-class="active-class "
                        exact
                        to="/profile"
                        tag="a"
                        class="scroll-link"
                        ><font-awesome-icon icon="user" /> Profil</router-link
                      >
                    </p>
                  </div>
                </div>
              </div>
            </div>
          </div>


        <div v-else class="row">
            <div class="col-md-6 footer-about wow fadeInUp text-center">
              <h3>Visual <span class="green">Manager</span></h3>
              <hr />
              <div class="text-left">
                <p>
                  Visual Manager ekibi olarak görsellerinizi online olarak
                  organize edebileceğiniz platformumuzu sizlere sunuyoruz.
                </p>
              </div>
            </div>
            <div
              class="col-md-6   footer-contact wow fadeInDown text-center"
            >
              <h3>İletişim</h3>
              <hr />
              <div class="text-left">
                <p>
                  <i class="fas fa-map-marker-alt"></i
                  ><font-awesome-icon icon="map-marker-alt" /> Düzce / Turkey
                </p>
                <p>
                  <i class="fas fa-phone"></i
                  ><font-awesome-icon icon="phone" /> (000) 123 45 67 890
                </p>
                <p>
                  <i class="fas fa-envelope"></i>
                  <font-awesome-icon icon="envelope" />
                  <a
                    href="mailto:info.VisualManager2@gmail.com"
                    style="color:gray;"
                    >info.VisualManager2@gmail.com</a
                  >
                </p>
              </div>
            </div>
            
          </div>



        </div>
      </div>
      <div class="footer-bottom">
        <div class="container">
          <div class="row">
            <div class="col-md-12 footer-copyright text-center">
              &copy; Visual Manager 2021
            </div>
          </div>
        </div>
      </div>
    </footer>
  </div>
</template>
<script>
export default {
  props: ["currentUser"]
};
</script>
<style scoped>
hr {
  border: 1px solid white;
  background-color: white;
}
.site-footer {
  position: relative;
  z-index: 4;

  background: rgba(0, 0, 0, 0.6);

  margin-top: 250px;
}
.footer-top {
  padding: 60px 0;
  /* background: #333;*/
  text-align: left;
  color: #aaa;
}
.footer-top h3 {
  padding-bottom: 10px;
  color: #fff;
}

.footer-about img.logo-footer {
  max-width: 74px;
  margin-top: 0;
  margin-bottom: 18px;
}
.footer-about p a {
  border: 0;
}
.footer-about p a:hover,
.footer-about p a:focus {
  border: 0;
}

.footer-contact p {
  word-wrap: break-word;
}
.footer-contact i {
  padding-right: 10px;
  font-size: 18px;
  color: #666;
}
.footer-contact p a {
  border: 0;
}
.footer-contact p a:hover,
.footer-contact p a:focus {
  border: 0;
}

.footer-links a {
  color: #aaa;
  border: 0;
}
.footer-links a:hover,
.footer-links a:focus {
  color: #fff;
}

.footer-bottom {
  padding: 15px 0 17px 0;
  /*background: #444;*/
  text-align: left;
  color: #aaa;
}

.footer-social {
  padding-top: 3px;
  text-align: right;
}
.footer-social a {
  margin-left: 20px;
  color: #777;
  border: 0;
}
.footer-social a:hover,
.footer-social a:focus {
  color: #79a05f;
  border: 0;
}
.footer-social i {
  font-size: 24px;
  vertical-align: middle;
}

.footer-copyright {
  padding-top: 5px;
}
.footer-copyright a {
  color: #fff;
  border: 0;
}
.footer-copyright a:hover,
.footer-copyright a:focus {
  color: #aaa;
  border: 0;
}
.green {
  color: #6e6;
}
</style>
