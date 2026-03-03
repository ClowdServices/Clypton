import java.util.Base64;

public enum bp {
   HIGHEST(0),
   HIGH(1),
   NORMAL(2),
   LOW(3),
   LOWEST(4);

   private final int value;

   private bp(final int value) {
      this.value = value;
   }

   public int getValue() {
      return this.value;
   }

   // $FF: synthetic method
   private static bp[] $values() {
      return new bp[]{HIGHEST, HIGH, NORMAL, LOW, LOWEST};
   }

   // $FF: synthetic method
   private static String d936(String var0) {
      byte[] var1 = Base64.getDecoder().decode(var0);
      byte[] var2 = new byte[var1.length];

      for(int var3 = 0; var3 < var1.length; ++var3) {
         var2[var3] = (byte)(var1[var3] ^ 3030 + var3 & (204 ^ 51));
      }

      return new String(var2);
   }
}
