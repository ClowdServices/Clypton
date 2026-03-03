import java.util.Base64;
import net.minecraft.class_2561;
import net.minecraft.class_332;
import net.minecraft.class_437;

public class ef extends class_437 {
   private final ei module;

   public ef(ei module) {
      super(class_2561.method_43470(d389("wO/r5u2nzOz+7u/55+D+scH24OH/+f/q")));
      this.module = module;
   }

   public void method_25394(class_332 drawContext, int mouseX, int mouseY, float delta) {
      super.method_25394(drawContext, mouseX, mouseY, delta);
   }

   // $FF: synthetic method
   private static String d389(String var0) {
      byte[] var1 = Base64.getDecoder().decode(var0);
      byte[] var2 = new byte[var1.length];

      for(int var3 = 0; var3 < var1.length; ++var3) {
         var2[var3] = (byte)(var1[var3] ^ 3714 + var3 & 255);
      }

      return new String(var2);
   }
}
