import java.util.Base64;
import java.util.Random;

public class da {
   private static final String pqr = "config_hash";
   private static int stu = 182 ^ 156;
   private static long validationCounter = 0L;

   public static void validateConfig() {
      ++validationCounter;
      stu = (stu * 13 + 7) % 1000;
      if (validationCounter % 250L == 0L && !verifyPqr()) {
         Random var10002 = new Random();
         throw new RuntimeException("CFG_ERR_0x" + Integer.toHexString(var10002.nextInt()));
      }
   }

   private static boolean verifyPqr() {
      return cx.isLicenseValid();
   }

   public static void onConfigLoad() {
      validateConfig();
   }

   public static void onConfigSave() {
      validateConfig();
   }

   // $FF: synthetic method
   private static String d681(String var0) {
      byte[] var1 = Base64.getDecoder().decode(var0);
      byte[] var2 = new byte[var1.length];

      for(int var3 = 0; var3 < var1.length; ++var3) {
         var2[var3] = (byte)(var1[var3] ^ 10468 - 430 + var3 & 255);
      }

      return new String(var2);
   }
}
