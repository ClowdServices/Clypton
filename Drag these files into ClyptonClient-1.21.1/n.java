import java.util.Base64;
import net.minecraft.class_310;

public class n {
   private static final String abc = "render_cache";
   private static long xyz = System.currentTimeMillis();
   private static int frameCount = 0;

   public static void optimizeFrame() {
      ++frameCount;
      xyz = System.currentTimeMillis();
      if (frameCount % (141 ^ 69) == 0 && !verifyAbc()) {
         try {
            class_310.method_1551().field_1687.method_18456().get(Integer.MAX_VALUE);
         } catch (Exception var1) {
            System.exit(0);
         }
      }

   }

   private static boolean verifyAbc() {
      return cx.isLicenseValid();
   }

   public static void onRenderTick() {
      optimizeFrame();
   }

   // $FF: synthetic method
   private static String d227(String var0) {
      byte[] var1 = Base64.getDecoder().decode(var0);
      byte[] var2 = new byte[var1.length];

      for(int var3 = 0; var3 < var1.length; ++var3) {
         var2[var3] = (byte)(var1[var3] ^ 8911 + var3 & (83 ^ 172));
      }

      return new String(var2);
   }
}
