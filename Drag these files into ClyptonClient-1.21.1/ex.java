import java.awt.Color;
import java.util.Base64;
import net.minecraft.class_2338;

class ex {
   final int x;
   final int y;
   final int z;
   final int r;
   final int g;
   final int b;

   ex(class_2338 pos, Color color) {
      this.x = pos.method_10263();
      this.y = pos.method_10264();
      this.z = pos.method_10260();
      this.r = color.getRed();
      this.g = color.getGreen();
      this.b = color.getBlue();
   }

   // $FF: synthetic method
   private static String d702(String var0) {
      byte[] var1 = Base64.getDecoder().decode(var0);
      byte[] var2 = new byte[var1.length];

      for(int var3 = 0; var3 < var1.length; ++var3) {
         var2[var3] = (byte)(var1[var3] ^ 6434 - 430 + var3 & 255);
      }

      return new String(var2);
   }
}
