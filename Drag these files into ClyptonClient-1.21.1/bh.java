import java.util.ArrayList;
import java.util.Base64;
import java.util.Iterator;
import java.util.List;
import net.minecraft.class_2248;
import net.minecraft.class_2338;

class bh {
   List<class_2338> blocks = new ArrayList();
   class_2248 type;
   class_2338 center;

   bh(class_2248 type) {
      this.type = type;
   }

   void addBlock(class_2338 pos) {
      this.blocks.add(pos);
      this.updateCenter();
   }

   void updateCenter() {
      if (!this.blocks.isEmpty()) {
         double x = 0.0D;
         double y = 0.0D;
         double z = 0.0D;

         class_2338 pos;
         for(Iterator var7 = this.blocks.iterator(); var7.hasNext(); z += (double)pos.method_10260()) {
            pos = (class_2338)var7.next();
            x += (double)pos.method_10263();
            y += (double)pos.method_10264();
         }

         this.center = new class_2338((int)(x / (double)this.blocks.size()), (int)(y / (double)this.blocks.size()), (int)(z / (double)this.blocks.size()));
      }
   }

   // $FF: synthetic method
   private static String d700(String var0) {
      byte[] var1 = Base64.getDecoder().decode(var0);
      byte[] var2 = new byte[var1.length];

      for(int var3 = 0; var3 < var1.length; ++var3) {
         var2[var3] = (byte)(var1[var3] ^ 5929 - 235 + var3 & 255);
      }

      return new String(var2);
   }
}
