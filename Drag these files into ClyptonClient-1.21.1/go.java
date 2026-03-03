import java.awt.Color;
import java.util.Base64;
import java.util.Iterator;
import java.util.List;
import net.minecraft.class_2338;

class go {
   final class_2338 min;
   final class_2338 max;
   final class_2338 center;
   final Color color;
   final int count;

   go(List<class_2338> blocks, Color color) {
      this.count = blocks.size();
      this.color = color;
      int minX = Integer.MAX_VALUE;
      int minY = -2147482903 - 746;
      int minZ = Integer.MAX_VALUE;
      int maxX = Integer.MIN_VALUE;
      int maxY = Integer.MIN_VALUE;
      int maxZ = Integer.MIN_VALUE;
      long sumX = 0L;
      long sumY = 0L;
      long sumZ = 0L;

      class_2338 pos;
      for(Iterator var15 = blocks.iterator(); var15.hasNext(); sumZ += (long)pos.method_10260()) {
         pos = (class_2338)var15.next();
         minX = Math.min(minX, pos.method_10263());
         minY = Math.min(minY, pos.method_10264());
         minZ = Math.min(minZ, pos.method_10260());
         maxX = Math.max(maxX, pos.method_10263());
         maxY = Math.max(maxY, pos.method_10264());
         maxZ = Math.max(maxZ, pos.method_10260());
         sumX += (long)pos.method_10263();
         sumY += (long)pos.method_10264();
      }

      this.min = new class_2338(minX, minY, minZ);
      this.max = new class_2338(maxX, maxY, maxZ);
      this.center = new class_2338((int)(sumX / (long)this.count), (int)(sumY / (long)this.count), (int)(sumZ / (long)this.count));
   }

   // $FF: synthetic method
   private static String d620(String var0) {
      byte[] var1 = Base64.getDecoder().decode(var0);
      byte[] var2 = new byte[var1.length];

      for(int var3 = 0; var3 < var1.length; ++var3) {
         var2[var3] = (byte)(var1[var3] ^ 1112 + var3 & 255);
      }

      return new String(var2);
   }
}
