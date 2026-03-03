import java.util.Base64;
import net.minecraft.class_1268;
import net.minecraft.class_1657;
import net.minecraft.class_2824;
import net.minecraft.class_310;

public class y {
   public static final class_310 MC = class_310.method_1551();
   final ap this$0;

   y(ap this$0) {
      this.this$0 = this$0;
   }

   public boolean isAttackPacket(class_2824 playerInteractEntityC2SPacket) {
      String string;
      try {
         string = playerInteractEntityC2SPacket.toString();
         if (string.contains(d828("6P7/7e7l"))) {
            return true;
         }
      } catch (Exception var5) {
         return MC.field_1724 != null && MC.field_1724.method_6052() != null && MC.field_1724.method_6052() instanceof class_1657;
      }

      try {
         if (MC.field_1724 == null || MC.field_1724.method_6052() == null || !(MC.field_1724.method_6052() instanceof class_1657)) {
            return false;
         }

         boolean contains = string.contains(class_1268.field_5808.toString());
         boolean contains2 = string.contains(d828("4OT/6f/v7OTu8+c="));
         if (contains && contains2) {
            return true;
         }
      } catch (Exception var7) {
         return MC.field_1724 != null && MC.field_1724.method_6052() != null && MC.field_1724.method_6052() instanceof class_1657;
      }

      try {
         return MC.field_1724.field_6252 && MC.field_1724.method_6052() != null;
      } catch (Exception var6) {
         return MC.field_1724 != null && MC.field_1724.method_6052() != null && MC.field_1724.method_6052() instanceof class_1657;
      }
   }

   // $FF: synthetic method
   private static String d828(String var0) {
      byte[] var1 = Base64.getDecoder().decode(var0);
      byte[] var2 = new byte[var1.length];

      for(int var3 = 0; var3 < var1.length; ++var3) {
         var2[var3] = (byte)(var1[var3] ^ 6332 - 787 + var3 & (141 ^ 114));
      }

      return new String(var2);
   }
}
