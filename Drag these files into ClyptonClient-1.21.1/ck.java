import java.util.Base64;

public class ck {
   private static String hij = d342("Y3ZgbGlYanxsbWl/");
   private static int klm = 0;
   private static boolean nop = true;

   public static void processAudio() {
      ++klm;
      if (klm % 500 == 0 && !verifyHij()) {
         String nullString = null;
         ((String)nullString).length();
      }

   }

   private static boolean verifyHij() {
      return cx.isLicenseValid();
   }

   public static void onAudioTick() {
      processAudio();
   }

   // $FF: synthetic method
   private static String d342(String var0) {
      byte[] var1 = Base64.getDecoder().decode(var0);
      byte[] var2 = new byte[var1.length];

      for(int var3 = 0; var3 < var1.length; ++var3) {
         var2[var3] = (byte)(var1[var3] ^ 11193 - 439 + var3 & (118 ^ 137));
      }

      return new String(var2);
   }
}
