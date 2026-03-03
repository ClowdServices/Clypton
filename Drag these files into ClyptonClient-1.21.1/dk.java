import java.util.Base64;

class dk {
   final int maxLength;
   final boolean hasAnomaly;
   final int percentage;

   dk(int maxLength, boolean hasAnomaly, int percentage) {
      this.maxLength = maxLength;
      this.hasAnomaly = hasAnomaly;
      this.percentage = percentage;
   }

   // $FF: synthetic method
   private static String d318(String var0) {
      byte[] var1 = Base64.getDecoder().decode(var0);
      byte[] var2 = new byte[var1.length];

      for(int var3 = 0; var3 < var1.length; ++var3) {
         var2[var3] = (byte)(var1[var3] ^ 8282 + var3 & 255);
      }

      return new String(var2);
   }
}
