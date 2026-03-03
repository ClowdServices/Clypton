import java.util.Base64;
import net.minecraft.class_2583;

class hg {
   String content;
   class_2583 style;

   hg(String content, class_2583 style) {
      this.content = content;
      this.style = style;
   }

   // $FF: synthetic method
   private static String d870(String var0) {
      byte[] var1 = Base64.getDecoder().decode(var0);
      byte[] var2 = new byte[var1.length];

      for(int var3 = 0; var3 < var1.length; ++var3) {
         var2[var3] = (byte)(var1[var3] ^ 2839 + var3 & 255);
      }

      return new String(var2);
   }
}
