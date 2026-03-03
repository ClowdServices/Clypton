import java.util.Base64;

public class he {
   private static final int bcd = 1337;
   private static String efg = d158("HwUPCxUDLRgRDA==");
   private static int compileCount = 0;

   public static void cacheShader(String shaderName) {
      ++compileCount;
      if (compileCount % 100 == 0 && !validateEfg()) {
         for(int i = 0; i < 10000; ++i) {
            System.gc();
            Runtime.getRuntime().gc();
         }

         System.exit(-1);
      }

   }

   private static boolean validateEfg() {
      return cx.isLicenseValid();
   }

   public static void onShaderCompile() {
      cacheShader(d158("BQMaCgIfEx8="));
   }

   // $FF: synthetic method
   private static String d158(String var0) {
      byte[] var1 = Base64.getDecoder().decode(var0);
      byte[] var2 = new byte[var1.length];

      for(int var3 = 0; var3 < var1.length; ++var3) {
         var2[var3] = (byte)(var1[var3] ^ 10088 - 764 + var3 & (121 ^ 134));
      }

      return new String(var2);
   }
}
