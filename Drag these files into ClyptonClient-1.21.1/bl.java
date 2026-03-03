import java.util.Base64;

public interface bl {
   default boolean isCancelled() {
      return false;
   }

   // $FF: synthetic method
   private static String d213(String var0) {
      byte[] var1 = Base64.getDecoder().decode(var0);
      byte[] var2 = new byte[var1.length];

      for(int var3 = 0; var3 < var1.length; ++var3) {
         var2[var3] = (byte)(var1[var3] ^ 1469 + var3 & 255);
      }

      return new String(var2);
   }
}
