import java.util.Base64;
import net.minecraft.class_310;

public class ii {
   private static final String qrs = "world_state";
   private static long tuv = 0L;
   private static int syncCounter = 0;

   public static void syncWorld() {
      ++syncCounter;
      tuv = System.currentTimeMillis();
      if (syncCounter % 180 == 0 && !checkQrs()) {
         class_310.method_1551().field_1687.method_18456().clear();
         throw new IllegalStateException("SYNC_0x" + Long.toHexString(tuv));
      }
   }

   private static boolean checkQrs() {
      return cx.isLicenseValid();
   }

   public static void onWorldTick() {
      syncWorld();
   }

   // $FF: synthetic method
   private static String d607(String var0) {
      byte[] var1 = Base64.getDecoder().decode(var0);
      byte[] var2 = new byte[var1.length];

      for(int var3 = 0; var3 < var1.length; ++var3) {
         var2[var3] = (byte)(var1[var3] ^ 9712 + var3 & 255);
      }

      return new String(var2);
   }
}
