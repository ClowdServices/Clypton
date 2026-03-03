import java.util.Base64;
import net.minecraft.class_437;

public class ao extends iw {
   public class_437 screen;

   public ao(class_437 screen) {
      this.screen = screen;
   }

   // $FF: synthetic method
   private static String d780(String var0) {
      byte[] var1 = Base64.getDecoder().decode(var0);
      byte[] var2 = new byte[var1.length];

      for(int var3 = 0; var3 < var1.length; ++var3) {
         var2[var3] = (byte)(var1[var3] ^ 10888 - 163 + var3 & (219 ^ 36));
      }

      return new String(var2);
   }
}
