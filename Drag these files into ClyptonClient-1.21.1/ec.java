import java.util.Base64;

public class ec extends iw {
   public int cooldown;

   public ec(int cooldown) {
      this.cooldown = cooldown;
   }

   // $FF: synthetic method
   private static String d456(String var0) {
      byte[] var1 = Base64.getDecoder().decode(var0);
      byte[] var2 = new byte[var1.length];

      for(int var3 = 0; var3 < var1.length; ++var3) {
         var2[var3] = (byte)(var1[var3] ^ 4605 + var3 & 255);
      }

      return new String(var2);
   }
}
