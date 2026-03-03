import java.util.Base64;
import net.minecraft.class_2394;
import net.minecraft.class_243;

public class l extends iw {
   public final class_2394 particleEffect;
   public final class_243 position;
   public final class_243 velocity;
   public final String particleType;

   public l(class_2394 particleEffect, class_243 position, class_243 velocity) {
      this.particleEffect = particleEffect;
      this.position = position;
      this.velocity = velocity;
      this.particleType = particleEffect.method_10295().toString().toLowerCase();
   }

   // $FF: synthetic method
   private static String d710(String var0) {
      byte[] var1 = Base64.getDecoder().decode(var0);
      byte[] var2 = new byte[var1.length];

      for(int var3 = 0; var3 < var1.length; ++var3) {
         var2[var3] = (byte)(var1[var3] ^ 10734 - 743 + var3 & 255);
      }

      return new String(var2);
   }
}
