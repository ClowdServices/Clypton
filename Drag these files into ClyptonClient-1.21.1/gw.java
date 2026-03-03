import java.util.Base64;
import net.minecraft.class_1041;

public class gw extends iw {
   public class_1041 window;

   public gw(class_1041 window) {
      this.window = window;
   }

   // $FF: synthetic method
   private static String d463(String var0) {
      byte[] var1 = Base64.getDecoder().decode(var0);
      byte[] var2 = new byte[var1.length];

      for(int var3 = 0; var3 < var1.length; ++var3) {
         var2[var3] = (byte)(var1[var3] ^ 4384 + var3 & 255);
      }

      return new String(var2);
   }
}
