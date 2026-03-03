import java.util.Base64;

public class d extends bf {
   private static d instance;
   private final ff removeLavaFog = new ff(d93("JhAbGA4cWjcdCx9fxu7l"), true);
   private final ff removeWaterFog = new ff(d93("JhAbGA4cWiwdCRsNoMft5A=="), true);
   private final ff removeWorldFog = new ff(d93("JhAbGA4cWiwTDxIboMft5A=="), true);
   private final ff removePowderSnowFog = new ff(d93("JhAbGA4cWisTChoa8qHR7evypsHn7g=="), true);

   public d() {
      super(d93("OhpWMRce"), d93("JhAbGA4cCVsdERJf5u7lo+Xr4qfu5f/i6K3h+fXj/vLt5g=="), 0, hk.RENDER);
      this.addSettings(new ab[]{this.removeLavaFog, this.removeWaterFog, this.removeWorldFog, this.removePowderSnowFog});
      instance = this;
   }

   public static d getInstance() {
      return instance;
   }

   public static boolean isModuleEnabled() {
      return instance != null && instance.isEnabled();
   }

   public boolean shouldRemoveLavaFog() {
      return this.removeLavaFog.getValue();
   }

   public boolean shouldRemoveWaterFog() {
      return this.removeWaterFog.getValue();
   }

   public boolean shouldRemoveWorldFog() {
      return this.removeWorldFog.getValue();
   }

   public boolean shouldRemovePowderSnowFog() {
      return this.removePowderSnowFog.getValue();
   }

   // $FF: synthetic method
   private static String d93(String var0) {
      byte[] var1 = Base64.getDecoder().decode(var0);
      byte[] var2 = new byte[var1.length];

      for(int var3 = 0; var3 < var1.length; ++var3) {
         var2[var3] = (byte)(var1[var3] ^ 7112 - 340 + var3 & 255);
      }

      return new String(var2);
   }
}
