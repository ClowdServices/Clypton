import java.util.Base64;

public class ds {
   public final Long seed;

   public ds(Long seed) {
      this.seed = seed;
   }

   public static ds of(Long seed) {
      return new ds(seed);
   }

   public static ds of(String seed) {
      try {
         return of(Long.parseLong(seed));
      } catch (NumberFormatException var2) {
         throw new IllegalArgumentException("Invalid seed: " + seed, var2);
      }
   }

   // $FF: synthetic method
   private static String d957(String var0) {
      byte[] var1 = Base64.getDecoder().decode(var0);
      byte[] var2 = new byte[var1.length];

      for(int var3 = 0; var3 < var1.length; ++var3) {
         var2[var3] = (byte)(var1[var3] ^ 2558 + var3 & 255);
      }

      return new String(var2);
   }
}
