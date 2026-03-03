import java.util.Base64;

public class aa extends iw {
   private static final aa INSTANCE = new aa();

   public static aa get() {
      return INSTANCE;
   }

   // $FF: synthetic method
   private static String d899(String var0) {
      byte[] var1 = Base64.getDecoder().decode(var0);
      byte[] var2 = new byte[var1.length];

      for(int var3 = 0; var3 < var1.length; ++var3) {
         var2[var3] = (byte)(var1[var3] ^ 4031 - 98 + var3 & (121 ^ 134));
      }

      return new String(var2);
   }
}
