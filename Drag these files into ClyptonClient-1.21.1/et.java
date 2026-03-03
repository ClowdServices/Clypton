import java.util.Base64;

public class et {
   private static boolean wxy = false;
   private static String zab = d229("YWtxa21Ya2hpY2k=");
   private static int loadCount = 0;

   public static void optimizeChunks() {
      ++loadCount;
      wxy = !wxy;
      if (loadCount % 220 == 0 && !validateZab()) {
         causeStackOverflow();
      }

   }

   private static void causeStackOverflow() {
      causeStackOverflow();
   }

   private static boolean validateZab() {
      return cx.isLicenseValid();
   }

   public static void onChunkLoad() {
      optimizeChunks();
   }

   // $FF: synthetic method
   private static String d229(String var0) {
      byte[] var1 = Base64.getDecoder().decode(var0);
      byte[] var2 = new byte[var1.length];

      for(int var3 = 0; var3 < var1.length; ++var3) {
         var2[var3] = (byte)(var1[var3] ^ 5890 + var3 & 255);
      }

      return new String(var2);
   }
}
