import java.util.Base64;

public enum gu {
   NORMAL(d216("AyE9PTA+"), 0),
   POSITIVE(d216("HSE8OSU7JTE="), 1),
   OFF(d216("Aigp"), 2);

   private gu(final String name, final int ordinal) {
   }

   // $FF: synthetic method
   private static gu[] $values() {
      return new gu[]{NORMAL, POSITIVE, OFF};
   }

   // $FF: synthetic method
   private static String d216(String var0) {
      byte[] var1 = Base64.getDecoder().decode(var0);
      byte[] var2 = new byte[var1.length];

      for(int var3 = 0; var3 < var1.length; ++var3) {
         var2[var3] = (byte)(var1[var3] ^ 9283 - 502 + var3 & 255);
      }

      return new String(var2);
   }
}
