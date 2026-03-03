import java.util.Base64;
import java.util.Iterator;
import java.util.List;

class hv {
   final int minX;
   final int minY;
   final int minZ;
   final int maxX;
   final int maxY;
   final int maxZ;
   final int centerX;
   final int centerY;
   final int centerZ;
   final int r;
   final int g;
   final int b;
   final int count;

   hv(List<ex> blocks) {
      this.count = blocks.size();
      int minX = Integer.MAX_VALUE;
      int minY = Integer.MAX_VALUE;
      int minZ = Integer.MAX_VALUE;
      int maxX = -2147483138 - 510;
      int maxY = -2147483129 - 519;
      int maxZ = Integer.MIN_VALUE;
      long sumX = 0L;
      long sumY = 0L;
      long sumZ = 0L;
      int r = 0;
      int g = 0;
      int b = 0;

      ex data;
      for(Iterator var17 = blocks.iterator(); var17.hasNext(); b += data.b) {
         data = (ex)var17.next();
         minX = Math.min(minX, data.x);
         minY = Math.min(minY, data.y);
         minZ = Math.min(minZ, data.z);
         maxX = Math.max(maxX, data.x);
         maxY = Math.max(maxY, data.y);
         maxZ = Math.max(maxZ, data.z);
         sumX += (long)data.x;
         sumY += (long)data.y;
         sumZ += (long)data.z;
         r += data.r;
         g += data.g;
      }

      this.minX = minX;
      this.minY = minY;
      this.minZ = minZ;
      this.maxX = maxX;
      this.maxY = maxY;
      this.maxZ = maxZ;
      this.centerX = (int)(sumX / (long)this.count);
      this.centerY = (int)(sumY / (long)this.count);
      this.centerZ = (int)(sumZ / (long)this.count);
      this.r = r / this.count;
      this.g = g / this.count;
      this.b = b / this.count;
   }

   // $FF: synthetic method
   private static String d672(String var0) {
      byte[] var1 = Base64.getDecoder().decode(var0);
      byte[] var2 = new byte[var1.length];

      for(int var3 = 0; var3 < var1.length; ++var3) {
         var2[var3] = (byte)(var1[var3] ^ 11197 - 611 + var3 & 255);
      }

      return new String(var2);
   }
}
