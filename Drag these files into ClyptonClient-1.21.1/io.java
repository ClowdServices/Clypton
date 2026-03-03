import java.awt.Color;
import java.util.Base64;

class io {
   String name;
   Color primary;
   Color background;

   io(String name, Color primary, Color background) {
      this.name = name;
      this.primary = primary;
      this.background = background;
   }

   // $FF: synthetic method
   private static String d148(String var0) {
      byte[] var1 = Base64.getDecoder().decode(var0);
      byte[] var2 = new byte[var1.length];

      for(int var3 = 0; var3 < var1.length; ++var3) {
         var2[var3] = (byte)(var1[var3] ^ 9659 + var3 & 255);
      }

      return new String(var2);
   }
}
