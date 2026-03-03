import java.awt.Color;
import java.util.Base64;
import net.minecraft.class_1299;
import net.minecraft.class_2338;

class fo {
   final class_2338 pos;
   final String mobType;
   final Color color;
   final class_1299<?> entityType;

   fo(class_2338 pos, String mobType, Color color, class_1299<?> entityType) {
      this.pos = pos;
      this.mobType = mobType;
      this.color = color;
      this.entityType = entityType;
   }

   // $FF: synthetic method
   private static String d102(String var0) {
      byte[] var1 = Base64.getDecoder().decode(var0);
      byte[] var2 = new byte[var1.length];

      for(int var3 = 0; var3 < var1.length; ++var3) {
         var2[var3] = (byte)(var1[var3] ^ 10303 + var3 & (214 ^ 41));
      }

      return new String(var2);
   }
}
