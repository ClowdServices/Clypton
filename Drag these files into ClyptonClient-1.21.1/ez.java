import java.util.Base64;
import net.minecraft.class_332;

public class ez extends iw {
   public class_332 context;
   public float tickDelta;

   public ez(class_332 context, float tickDelta) {
      this.context = context;
      this.tickDelta = tickDelta;
   }

   // $FF: synthetic method
   private static String d725(String var0) {
      byte[] var1 = Base64.getDecoder().decode(var0);
      byte[] var2 = new byte[var1.length];

      for(int var3 = 0; var3 < var1.length; ++var3) {
         var2[var3] = (byte)(var1[var3] ^ 6468 + var3 & 255);
      }

      return new String(var2);
   }
}
