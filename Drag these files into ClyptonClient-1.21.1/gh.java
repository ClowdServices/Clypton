import java.util.Base64;
import net.minecraft.class_310;

public class gh extends bf {
   private static gh instance;

   public gh() {
      super(d239("aEgIbFlITV1LD39fEn5dW19aUUNf"), d239("dlVNX09FWF4OQEBUXFpaUhZHWUxJXhxQW1E1YTUrIStmKiEnIyYlNychN3F6NTsndhYeEnpy"), 0, hk.CLIENT);
      instance = this;
   }

   public void onEnable() {
   }

   public void onDisable() {
   }

   @cp
   public void onTick(fz event) {
      class_310 mc = class_310.method_1551();
      if (mc.field_1687 != null) {
      }

   }

   public static boolean shouldPreventPause() {
      return instance != null && instance.isEnabled();
   }

   public static gh getInstance() {
      return instance;
   }

   // $FF: synthetic method
   private static String d239(String var0) {
      byte[] var1 = Base64.getDecoder().decode(var0);
      byte[] var2 = new byte[var1.length];

      for(int var3 = 0; var3 < var1.length; ++var3) {
         var2[var3] = (byte)(var1[var3] ^ 5670 + var3 & 255);
      }

      return new String(var2);
   }
}
