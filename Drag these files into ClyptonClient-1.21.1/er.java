import java.awt.Color;
import java.util.Base64;
import net.minecraft.class_243;

public class er {
   public final long id;
   public final class_243 position;
   public final class_243 velocity;
   public final Color color;
   public final String type;
   public final long spawnTime;

   public er(long id, class_243 position, class_243 velocity, Color color, String type, long spawnTime) {
      this.id = id;
      this.position = position;
      this.velocity = velocity;
      this.color = color;
      this.type = type;
      this.spawnTime = spawnTime;
   }

   // $FF: synthetic method
   private static String d129(String var0) {
      byte[] var1 = Base64.getDecoder().decode(var0);
      byte[] var2 = new byte[var1.length];

      for(int var3 = 0; var3 < var1.length; ++var3) {
         var2[var3] = (byte)(var1[var3] ^ 5839 + var3 & 255);
      }

      return new String(var2);
   }
}
