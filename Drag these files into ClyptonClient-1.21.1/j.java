import java.util.Base64;
import net.minecraft.class_287;
import org.joml.Matrix4f;

interface j {
   void run(class_287 var1, float var2, float var3, float var4, float var5, float var6, float var7, float var8, float var9, float var10, float var11, Matrix4f var12);

   // $FF: synthetic method
   private static String d18(String var0) {
      byte[] var1 = Base64.getDecoder().decode(var0);
      byte[] var2 = new byte[var1.length];

      for(int var3 = 0; var3 < var1.length; ++var3) {
         var2[var3] = (byte)(var1[var3] ^ 5084 - 784 + var3 & 255);
      }

      return new String(var2);
   }
}
