import java.util.Base64;

enum du {
   PRIMARY,
   BACKGROUND,
   SECONDARY,
   TEXT,
   HIGHLIGHT;

   // $FF: synthetic method
   private static du[] $values() {
      return new du[]{PRIMARY, BACKGROUND, SECONDARY, TEXT, HIGHLIGHT};
   }

   // $FF: synthetic method
   private static String d526(String var0) {
      byte[] var1 = Base64.getDecoder().decode(var0);
      byte[] var2 = new byte[var1.length];

      for(int var3 = 0; var3 < var1.length; ++var3) {
         var2[var3] = (byte)(var1[var3] ^ 7526 + var3 & (105 ^ 150));
      }

      return new String(var2);
   }
}
