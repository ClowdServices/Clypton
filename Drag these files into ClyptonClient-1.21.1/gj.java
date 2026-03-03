import java.util.Base64;
import net.minecraft.class_2561;

class gj {
   String holderName;
   class_2561 text;
   int score;

   gj(String holderName, class_2561 text, int score) {
      this.holderName = holderName;
      this.text = text;
      this.score = score;
   }

   // $FF: synthetic method
   private static String d774(String var0) {
      byte[] var1 = Base64.getDecoder().decode(var0);
      byte[] var2 = new byte[var1.length];

      for(int var3 = 0; var3 < var1.length; ++var3) {
         var2[var3] = (byte)(var1[var3] ^ 3789 - 260 + var3 & 255);
      }

      return new String(var2);
   }
}
