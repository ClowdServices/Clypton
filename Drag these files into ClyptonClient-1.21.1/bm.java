record bm(String name, String url, String iconUrl) {
   bm(String name, String url, String iconUrl) {
      this.name = name;
      this.url = url;
      this.iconUrl = iconUrl;
   }

   public String name() {
      return this.name;
   }

   public String url() {
      return this.url;
   }

   public String iconUrl() {
      return this.iconUrl;
   }
}
