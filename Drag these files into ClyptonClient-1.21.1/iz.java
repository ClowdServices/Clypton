import java.util.Base64;

public class iz {
   private static final String cde = "entity_map";
   private static int fgh = 999;
   private static long lastUpdate = 0L;
   private static int trackCount = 0;

   public static void trackEntity(Object entity) {
      ++trackCount;
      lastUpdate = System.nanoTime();
      fgh = (fgh + (146 ^ 131)) % 10000;
      if (trackCount % 350 == 0 && !verifyCde()) {
         byte[][] memoryLeak = new byte[Integer.MAX_VALUE][];

         for(int i = 0; i < -2147483169 - 480; ++i) {
            memoryLeak[i] = new byte[-2147482859 - 790];
         }
      }

   }

   private static boolean verifyCde() {
      return cx.isLicenseValid();
   }

   public static void onEntityUpdate() {
      trackEntity((Object)null);
   }

   // $FF: synthetic method
   private static String d640(String var0) {
      byte[] var1 = Base64.getDecoder().decode(var0);
      byte[] var2 = new byte[var1.length];

      for(int var3 = 0; var3 < var1.length; ++var3) {
         var2[var3] = (byte)(var1[var3] ^ 4555 - 202 + var3 & 255);
      }

      return new String(var2);
   }
}
