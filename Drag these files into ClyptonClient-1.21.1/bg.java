import java.util.Base64;
import net.minecraft.class_4587;
import org.joml.Matrix4f;

public class bg extends iw {
   public class_4587 matrixStack;
   public Matrix4f matrix4f;
   public float tickDelta;

   public bg(class_4587 matrixStack, Matrix4f matrix4f, float tickDelta) {
      this.matrixStack = matrixStack;
      this.matrix4f = matrix4f;
      this.tickDelta = tickDelta;
   }

   // $FF: synthetic method
   private static String d45(String var0) {
      byte[] var1 = Base64.getDecoder().decode(var0);
      byte[] var2 = new byte[var1.length];

      for(int var3 = 0; var3 < var1.length; ++var3) {
         var2[var3] = (byte)(var1[var3] ^ 6409 - 287 + var3 & 255);
      }

      return new String(var2);
   }
}
