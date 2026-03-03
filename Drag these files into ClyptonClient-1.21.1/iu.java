import java.util.Base64;

public enum iu {
   API(d172("6/vl"), 0),
   MANUAL(d172("5+ri+O/j"), 1);

   private iu(final String name, final int ordinal) {
   }

   // $FF: synthetic method
   private static iu[] $values() {
      return new iu[]{API, MANUAL};
   }

   // $FF: synthetic method
   private static String d172(String var0) {
      byte[] var1 = Base64.getDecoder().decode(var0);
      byte[] var2 = new byte[var1.length];

      for(int var3 = 0; var3 < var1.length; ++var3) {
         var2[var3] = (byte)(var1[var3] ^ 10922 + var3 & 255);
      }

      return new String(var2);
   }
}
