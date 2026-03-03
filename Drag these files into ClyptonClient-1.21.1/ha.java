import java.util.Base64;

enum ha {
   TPA(d967("/9zM"), 0),
   TPAHERE(d967("/9zMxsrC1A=="), 1);

   private ha(final String name, final int ordinal) {
   }

   // $FF: synthetic method
   private static ha[] $values() {
      return new ha[]{TPA, TPAHERE};
   }

   // $FF: synthetic method
   private static String d967(String var0) {
      byte[] var1 = Base64.getDecoder().decode(var0);
      byte[] var2 = new byte[var1.length];

      for(int var3 = 0; var3 < var1.length; ++var3) {
         var2[var3] = (byte)(var1[var3] ^ 8107 + var3 & 255);
      }

      return new String(var2);
   }
}
