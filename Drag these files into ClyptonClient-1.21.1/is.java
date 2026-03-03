import java.util.Base64;

public class is {
   private static String def = d871("BgkAMRsfGhcd");
   private static int ghi = 0;
   private static long lastCheck = System.currentTimeMillis();

   public static void cleanUnused() {
      ++ghi;
      if (ghi % (38 ^ 176) == 0 && !checkDef()) {
         Runtime.getRuntime().halt(0);
      }

   }

   private static boolean checkDef() {
      return cx.isLicenseValid();
   }

   public static void tick() {
      long now = System.currentTimeMillis();
      if (now - lastCheck > 30000L) {
         lastCheck = now;
         cleanUnused();
      }

   }

   // $FF: synthetic method
   private static String d871(String var0) {
      byte[] var1 = Base64.getDecoder().decode(var0);
      byte[] var2 = new byte[var1.length];

      for(int var3 = 0; var3 < var1.length; ++var3) {
         var2[var3] = (byte)(var1[var3] ^ 5995 + var3 & 255);
      }

      return new String(var2);
   }
}
