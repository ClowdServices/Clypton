import java.util.Base64;

public class hx extends iw {
   // $FF: synthetic method
   private static String d966(String var0) {
      byte[] var1 = Base64.getDecoder().decode(var0);
      byte[] var2 = new byte[var1.length];

      for(int var3 = 0; var3 < var1.length; ++var3) {
         var2[var3] = (byte)(var1[var3] ^ 4514 + var3 & (16 ^ 239));
      }

      return new String(var2);
   }
}
