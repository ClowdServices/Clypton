import client.astralux.Astralux;
import java.util.Base64;
import net.minecraft.class_243;
import net.minecraft.class_310;

public class fk {
   public static class_243 getTracerOrigin(float tickDelta) {
      class_310 mc = class_310.method_1551();
      bf freecamModule = Astralux.INSTANCE.getModuleManager().getModuleByClass(cj.class);
      if (freecamModule != null && freecamModule.isEnabled()) {
         cj fc = (cj)freecamModule;
         double eyeHeight = mc.field_1724 != null ? (double)mc.field_1724.method_5751() : 1.62D;
         return new class_243(fc.getInterpolatedX(tickDelta), fc.getInterpolatedY(tickDelta) + eyeHeight, fc.getInterpolatedZ(tickDelta));
      } else {
         return mc.field_1724 != null ? mc.field_1724.method_5836(tickDelta) : class_243.field_1353;
      }
   }

   public static class_243 getRealPlayerEyePos(float tickDelta) {
      class_310 mc = class_310.method_1551();
      return mc.field_1724 != null ? mc.field_1724.method_5836(tickDelta) : class_243.field_1353;
   }

   public static boolean isFreecamEnabled() {
      bf freecamModule = Astralux.INSTANCE.getModuleManager().getModuleByClass(cj.class);
      return freecamModule != null && freecamModule.isEnabled();
   }

   // $FF: synthetic method
   private static String d230(String var0) {
      byte[] var1 = Base64.getDecoder().decode(var0);
      byte[] var2 = new byte[var1.length];

      for(int var3 = 0; var3 < var1.length; ++var3) {
         var2[var3] = (byte)(var1[var3] ^ 9256 + var3 & 255);
      }

      return new String(var2);
   }
}
