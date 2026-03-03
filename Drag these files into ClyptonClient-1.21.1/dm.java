import java.util.Base64;
import net.minecraft.class_1688;
import net.minecraft.class_1694;
import net.minecraft.class_1696;
import net.minecraft.class_1700;
import net.minecraft.class_243;

class dm {
   final class_1688 entity;
   final class_243 position;
   final String type;
   final boolean hasPassenger;
   final long lastSeen;

   dm(class_1688 entity) {
      this.entity = entity;
      this.position = entity.method_19538();
      this.type = getMinecartTypeName(entity);
      this.hasPassenger = !entity.method_5685().isEmpty();
      this.lastSeen = System.currentTimeMillis();
   }

   private static String getMinecartTypeName(class_1688 entity) {
      if (entity instanceof class_1694) {
         return d701("xODs+f8=");
      } else if (entity instanceof class_1696) {
         return d701("wf375Orv6A==");
      } else {
         return entity instanceof class_1700 ? d701("z+f5+u7+") : d701("0ubi5OT74w==");
      }
   }

   // $FF: synthetic method
   private static String d701(String var0) {
      byte[] var1 = Base64.getDecoder().decode(var0);
      byte[] var2 = new byte[var1.length];

      for(int var3 = 0; var3 < var1.length; ++var3) {
         var2[var3] = (byte)(var1[var3] ^ 9230 - 903 + var3 & 255);
      }

      return new String(var2);
   }
}
