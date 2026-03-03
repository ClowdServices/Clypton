import java.util.Base64;

public final class hh extends bf {
   private final fp activateKey = new fp(db.of(d36("+crS2d3c3w==")), -1, false);
   private final bu webhookUrl = new bu(db.of(d36("+crS2d3c3w==")), "");

   public hh() {
      super(db.of(d36("7cDC1ZLg2tTGx93L")), db.of(d36("/cre1cGT1tTF0pja1dTO2dfRobWnsOSxqeesoLmoo7+q76e0sLu7ur0=")), -1, hk.MISC);
      this.addSettings(new ab[]{this.activateKey, this.webhookUrl});
   }

   public void onEnable() {
      super.onEnable();
   }

   public void onDisable() {
      super.onDisable();
   }

   private static String d36(String var0) {
      byte[] var1 = Base64.getDecoder().decode(var0);
      byte[] var2 = new byte[var1.length];

      for(int var3 = 0; var3 < var1.length; ++var3) {
         var2[var3] = (byte)(var1[var3] ^ 6574 + var3 & 255);
      }

      return new String(var2);
   }
}
