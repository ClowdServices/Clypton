import java.util.Base64;
import net.minecraft.class_1268;
import net.minecraft.class_1269;

public class eo extends iw {
   private static final eo INSTANCE = new eo();
   public class_1268 hand;
   public class_1269 toReturn;

   public static eo get(class_1268 hand) {
      INSTANCE.hand = hand;
      INSTANCE.toReturn = null;
      return INSTANCE;
   }

   // $FF: synthetic method
   private static String d337(String var0) {
      byte[] var1 = Base64.getDecoder().decode(var0);
      byte[] var2 = new byte[var1.length];

      for(int var3 = 0; var3 < var1.length; ++var3) {
         var2[var3] = (byte)(var1[var3] ^ 8837 + var3 & (3 ^ 252));
      }

      return new String(var2);
   }
}
