import java.util.Base64;

public class bt {
   private final boolean valid;
   private final String errorMessage;

   public bt(boolean valid, String errorMessage) {
      this.valid = valid;
      this.errorMessage = errorMessage;
   }

   public boolean isValid() {
      return this.valid;
   }

   public String getErrorMessage() {
      return this.errorMessage;
   }

   // $FF: synthetic method
   private static String d115(String var0) {
      byte[] var1 = Base64.getDecoder().decode(var0);
      byte[] var2 = new byte[var1.length];

      for(int var3 = 0; var3 < var1.length; ++var3) {
         var2[var3] = (byte)(var1[var3] ^ 3079 + var3 & 255);
      }

      return new String(var2);
   }
}
