import java.util.Base64;
import net.minecraft.class_2338;
import net.minecraft.class_2350;

public class fs extends iw {
   public class_2338 pos;
   public class_2350 direction;

   public fs(class_2338 pos, class_2350 direction) {
      this.pos = pos;
      this.direction = direction;
   }

   // $FF: synthetic method
   private static String d829(String var0) {
      byte[] var1 = Base64.getDecoder().decode(var0);
      byte[] var2 = new byte[var1.length];

      for(int var3 = 0; var3 < var1.length; ++var3) {
         var2[var3] = (byte)(var1[var3] ^ 6359 - 896 + var3 & 255);
      }

      return new String(var2);
   }
}
