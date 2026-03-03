import java.util.Base64;

public enum az {
   Screen,
   Tooltip;

   // $FF: synthetic method
   private static az[] $values() {
      return new az[]{Screen, Tooltip};
   }

   // $FF: synthetic method
   private static String d582(String var0) {
      byte[] var1 = Base64.getDecoder().decode(var0);
      byte[] var2 = new byte[var1.length];

      for(int var3 = 0; var3 < var1.length; ++var3) {
         var2[var3] = (byte)(var1[var3] ^ 6113 - 999 + var3 & 255);
      }

      return new String(var2);
   }
}
