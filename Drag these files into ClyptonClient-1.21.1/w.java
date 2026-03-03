import java.util.Base64;
import net.minecraft.class_2672;
import net.minecraft.class_2818;
import net.minecraft.class_310;

public class w extends iw {
   public class_2672 packet;
   public class_2818 chunk;

   public w(class_2672 packet) {
      this.packet = packet;
      class_310 client = class_310.method_1551();
      this.chunk = client != null && client.field_1687 != null ? client.field_1687.method_8497(packet.method_11523(), packet.method_11524()) : null;
   }

   public class_2818 chunk() {
      return this.chunk;
   }

   // $FF: synthetic method
   private static String d706(String var0) {
      byte[] var1 = Base64.getDecoder().decode(var0);
      byte[] var2 = new byte[var1.length];

      for(int var3 = 0; var3 < var1.length; ++var3) {
         var2[var3] = (byte)(var1[var3] ^ 10596 + var3 & (81 ^ 174));
      }

      return new String(var2);
   }
}
