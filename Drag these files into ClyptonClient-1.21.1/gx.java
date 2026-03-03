import java.util.Base64;
import net.minecraft.class_2338;

public class gx implements bl {
   public final class_2338 pos;

   public gx(class_2338 pos) {
      this.pos = pos;
   }

   // $FF: synthetic method
   private static String d395(String var0) {
      byte[] var1 = Base64.getDecoder().decode(var0);
      byte[] var2 = new byte[var1.length];

      for(int var3 = 0; var3 < var1.length; ++var3) {
         var2[var3] = (byte)(var1[var3] ^ 4508 + var3 & (31 ^ 224));
      }

      return new String(var2);
   }
}
