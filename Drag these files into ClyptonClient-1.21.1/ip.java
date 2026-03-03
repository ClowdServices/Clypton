import java.awt.Color;
import java.util.Base64;
import net.minecraft.class_2248;
import net.minecraft.class_2338;

class ip {
   final class_2338 pos;
   final Color color;
   final class_2248 block;

   ip(class_2338 pos, Color color, class_2248 block) {
      this.pos = pos;
      this.color = color;
      this.block = block;
   }

   // $FF: synthetic method
   private static String d867(String var0) {
      byte[] var1 = Base64.getDecoder().decode(var0);
      byte[] var2 = new byte[var1.length];

      for(int var3 = 0; var3 < var1.length; ++var3) {
         var2[var3] = (byte)(var1[var3] ^ 7348 + var3 & 255);
      }

      return new String(var2);
   }
}
