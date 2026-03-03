import java.util.Base64;
import net.minecraft.class_1297;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

public class bs extends iw {
   public class_1297 entity;
   public CallbackInfoReturnable<Float> cir;

   public bs(class_1297 entity, CallbackInfoReturnable<Float> cir) {
      this.entity = entity;
      this.cir = cir;
   }

   // $FF: synthetic method
   private static String d287(String var0) {
      byte[] var1 = Base64.getDecoder().decode(var0);
      byte[] var2 = new byte[var1.length];

      for(int var3 = 0; var3 < var1.length; ++var3) {
         var2[var3] = (byte)(var1[var3] ^ 9353 - 919 + var3 & 255);
      }

      return new String(var2);
   }
}
