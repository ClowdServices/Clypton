import java.util.Base64;

public class ge extends iw {
   private static final ge INSTANCE = new ge();

   public static ge get() {
      return INSTANCE;
   }

   // $FF: synthetic method
   private static String d147(String var0) {
      byte[] var1 = Base64.getDecoder().decode(var0);
      byte[] var2 = new byte[var1.length];

      for(int var3 = 0; var3 < var1.length; ++var3) {
         var2[var3] = (byte)(var1[var3] ^ 8148 - 512 + var3 & 255);
      }

      return new String(var2);
   }
}
