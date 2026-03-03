import java.util.Base64;

public class ce extends iw {
   private static final ce INSTANCE = new ce();

   public static ce get() {
      return INSTANCE;
   }

   // $FF: synthetic method
   private static String d476(String var0) {
      byte[] var1 = Base64.getDecoder().decode(var0);
      byte[] var2 = new byte[var1.length];

      for(int var3 = 0; var3 < var1.length; ++var3) {
         var2[var3] = (byte)(var1[var3] ^ 7731 + var3 & (93 ^ 162));
      }

      return new String(var2);
   }
}
