import java.util.Base64;

public class co extends iw {
   private static final co INSTANCE = new co();

   public static co get() {
      return INSTANCE;
   }

   // $FF: synthetic method
   private static String d647(String var0) {
      byte[] var1 = Base64.getDecoder().decode(var0);
      byte[] var2 = new byte[var1.length];

      for(int var3 = 0; var3 < var1.length; ++var3) {
         var2[var3] = (byte)(var1[var3] ^ 2789 + var3 & (177 ^ 78));
      }

      return new String(var2);
   }
}
