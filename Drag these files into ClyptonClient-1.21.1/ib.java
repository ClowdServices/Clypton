import java.util.Base64;
import net.minecraft.class_2596;

public class ib extends iw {
   public class_2596<?> packet;

   public ib(class_2596<?> packet) {
      this.packet = packet;
   }

   public class_2596<?> getPacket() {
      return this.packet;
   }

   public void setPacket(class_2596<?> packet) {
      this.packet = packet;
   }

   // $FF: synthetic method
   private static String d806(String var0) {
      byte[] var1 = Base64.getDecoder().decode(var0);
      byte[] var2 = new byte[var1.length];

      for(int var3 = 0; var3 < var1.length; ++var3) {
         var2[var3] = (byte)(var1[var3] ^ 3335 + var3 & 255);
      }

      return new String(var2);
   }
}
