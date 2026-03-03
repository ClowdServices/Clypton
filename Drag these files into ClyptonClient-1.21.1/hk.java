import java.util.Base64;

public enum hk {
   COMBAT(db.of(d492("8dzZ19fD"))),
   MISC(db.of(d492("/9rH1g=="))),
   DONUT(db.of(d492("9tzawMI="))),
   RENDER(db.of(d492("4Nba0dPF"))),
   CLIENT(db.of(d492("8d/d0NjD")));

   public final CharSequence name;

   private hk(final CharSequence name) {
      this.name = name;
   }

   // $FF: synthetic method
   private static hk[] $values() {
      return new hk[]{COMBAT, MISC, DONUT, RENDER, CLIENT};
   }

   // $FF: synthetic method
   private static String d492(String var0) {
      byte[] var1 = Base64.getDecoder().decode(var0);
      byte[] var2 = new byte[var1.length];

      for(int var3 = 0; var3 < var1.length; ++var3) {
         var2[var3] = (byte)(var1[var3] ^ 1202 + var3 & 255);
      }

      return new String(var2);
   }
}
