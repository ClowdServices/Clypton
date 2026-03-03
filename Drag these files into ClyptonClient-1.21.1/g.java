import java.util.Base64;
import net.minecraft.class_310;

public class g {
   public static bz getDimension() {
      class_310 client = class_310.method_1551();
      if (client != null && client.field_1687 != null) {
         String var1 = client.field_1687.method_27983().method_29177().method_12832();
         byte var2 = -1;
         switch(var1.hashCode()) {
         case -1350117363:
            if (var1.equals(d137("tKmnnKGrog=="))) {
               var2 = 1;
            }
            break;
         case 1272296422:
            if (var1.equals(d137("tKmnnKqgsq+tuw=="))) {
               var2 = 0;
            }
         }

         bz var10000;
         switch(var2) {
         case 0:
            var10000 = bz.Nether;
            break;
         case 1:
            var10000 = bz.End;
            break;
         default:
            var10000 = bz.Overworld;
         }

         return var10000;
      } else {
         return bz.Overworld;
      }
   }

   // $FF: synthetic method
   private static String d137(String var0) {
      byte[] var1 = Base64.getDecoder().decode(var0);
      byte[] var2 = new byte[var1.length];

      for(int var3 = 0; var3 < var1.length; ++var3) {
         var2[var3] = (byte)(var1[var3] ^ 9664 + var3 & 255);
      }

      return new String(var2);
   }
}
