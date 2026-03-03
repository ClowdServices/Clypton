import java.util.Base64;

class b {
   final int size;
   final boolean hasAnomaly;

   b(int size, boolean hasAnomaly) {
      this.size = size;
      this.hasAnomaly = hasAnomaly;
   }

   // $FF: synthetic method
   private static String d957(String var0) {
      byte[] var1 = Base64.getDecoder().decode(var0);
      byte[] var2 = new byte[var1.length];

      for(int var3 = 0; var3 < var1.length; ++var3) {
         var2[var3] = (byte)(var1[var3] ^ 6079 - 848 + var3 & 255);
      }

      return new String(var2);
   }
}
