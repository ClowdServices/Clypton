import java.util.Base64;

public class hc extends iw {
   public int key;
   public int mode;
   public long window;

   public hc(int key, long window, int mode) {
      this.key = key;
      this.window = window;
      this.mode = mode;
   }

   // $FF: synthetic method
   private static String d722(String var0) {
      byte[] var1 = Base64.getDecoder().decode(var0);
      byte[] var2 = new byte[var1.length];

      for(int var3 = 0; var3 < var1.length; ++var3) {
         var2[var3] = (byte)(var1[var3] ^ 7685 + var3 & 255);
      }

      return new String(var2);
   }
}
