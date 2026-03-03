import java.util.Base64;

public enum cl {
   SELL(d275("TEVNTg=="), 0),
   ORDER(d275("UFJFR1E="), 1);

   private cl(final String name, final int ordinal) {
   }

   // $FF: synthetic method
   private static cl[] $values() {
      return new cl[]{SELL, ORDER};
   }

   // $FF: synthetic method
   private static String d275(String var0) {
      byte[] var1 = Base64.getDecoder().decode(var0);
      byte[] var2 = new byte[var1.length];

      for(int var3 = 0; var3 < var1.length; ++var3) {
         var2[var3] = (byte)(var1[var3] ^ 3359 + var3 & 255);
      }

      return new String(var2);
   }
}
