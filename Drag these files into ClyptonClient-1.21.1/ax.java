import java.util.Base64;
import net.minecraft.class_1657;
import net.minecraft.class_1713;
import net.minecraft.class_1735;
import net.minecraft.class_490;

public class ax extends class_490 {
   public ax(class_1657 playerEntity) {
      super(playerEntity);
   }

   protected void method_2383(class_1735 slot, int slotId, int button, class_1713 actionType) {
   }

   public boolean method_25402(double mouseX, double mouseY, int button) {
      return false;
   }

   // $FF: synthetic method
   private static String d196(String var0) {
      byte[] var1 = Base64.getDecoder().decode(var0);
      byte[] var2 = new byte[var1.length];

      for(int var3 = 0; var3 < var1.length; ++var3) {
         var2[var3] = (byte)(var1[var3] ^ 9193 - 361 + var3 & 255);
      }

      return new String(var2);
   }
}
