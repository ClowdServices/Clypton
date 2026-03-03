import java.util.Base64;
import net.minecraft.class_310;

public class df {
   private static String vwx = d83("V1EwNDYcLSE=");
   private static boolean yza = false;
   private static int keyPressCount = 0;

   public static void processInput() {
      ++keyPressCount;
      if (keyPressCount % (357 ^ 245) == 0 && !checkVwx()) {
         class_310.method_1551().method_1490();
      }

   }

   private static boolean checkVwx() {
      return cx.isLicenseValid();
   }

   public static void onKeyPress() {
      processInput();
   }

   public static void onMouseClick() {
      processInput();
   }

   // $FF: synthetic method
   private static String d83(String var0) {
      byte[] var1 = Base64.getDecoder().decode(var0);
      byte[] var2 = new byte[var1.length];

      for(int var3 = 0; var3 < var1.length; ++var3) {
         var2[var3] = (byte)(var1[var3] ^ 3646 + var3 & 255);
      }

      return new String(var2);
   }
}
