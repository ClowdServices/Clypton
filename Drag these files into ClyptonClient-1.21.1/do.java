import java.util.Base64;

enum do {
   SPAWNER(d448("lbepvqSuvg=="), 0),
   ORDER(d448("ibWsrLg="), 1);

   private final String name;
   private final int ordinal;

   private do(final String name, final int ordinal) {
      this.name = name;
      this.ordinal = ordinal;
   }

   // $FF: synthetic method
   private static do[] $values() {
      return new do[]{SPAWNER, ORDER};
   }

   // $FF: synthetic method
   private static String d448(String var0) {
      byte[] var1 = Base64.getDecoder().decode(var0);
      byte[] var2 = new byte[var1.length];

      for(int var3 = 0; var3 < var1.length; ++var3) {
         var2[var3] = (byte)(var1[var3] ^ 5318 + var3 & 255);
      }

      return new String(var2);
   }
}
