import java.util.Base64;

public final class dw extends bf {
   public static boolean isActive = false;

   public dw() {
      super(db.of(d606("uImBiM+0lIGHhoCVgw==")), db.of(d606("uYmAgZmVgtKHnJDWlJSQn5WI3ZiNb2wiemtwdCdvaGduLHFNfXV1e2dnNWJ4OFhofHNzPnlPUwJHQUlDU0FGRFc=")), -1, hk.CLIENT);
   }

   public void onEnable() {
      super.onEnable();
   }

   public void onDisable() {
      super.onDisable();
   }

   private static String d606(String var0) {
      byte[] var1 = Base64.getDecoder().decode(var0);
      byte[] var2 = new byte[var1.length];

      for(int var3 = 0; var3 < var1.length; ++var3) {
         var2[var3] = (byte)(var1[var3] ^ 6891 + var3 & 255);
      }

      return new String(var2);
   }
}
