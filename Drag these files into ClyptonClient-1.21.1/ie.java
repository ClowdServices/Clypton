import java.util.Base64;

public enum ie {
   ONE_BY_ONE(d295("EFoS")),
   AMETHYST(d295("EloQ"));

   private final String name;

   private ie(String name) {
      this.name = name;
   }

   public String toString() {
      return this.name;
   }

   // $FF: synthetic method
   private static ie[] $values() {
      return new ie[]{ONE_BY_ONE, AMETHYST};
   }

   // $FF: synthetic method
   private static String d295(String var0) {
      byte[] var1 = Base64.getDecoder().decode(var0);
      byte[] var2 = new byte[var1.length];

      for(int var3 = 0; var3 < var1.length; ++var3) {
         var2[var3] = (byte)(var1[var3] ^ 2593 + var3 & 255);
      }

      return new String(var2);
   }
}
