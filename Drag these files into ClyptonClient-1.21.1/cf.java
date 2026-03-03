import java.util.Base64;

public class cf extends iw {
   public double amount;

   public cf(double amount) {
      this.amount = amount;
   }

   // $FF: synthetic method
   private static String d270(String var0) {
      byte[] var1 = Base64.getDecoder().decode(var0);
      byte[] var2 = new byte[var1.length];

      for(int var3 = 0; var3 < var1.length; ++var3) {
         var2[var3] = (byte)(var1[var3] ^ 9573 - 308 + var3 & 255);
      }

      return new String(var2);
   }
}
