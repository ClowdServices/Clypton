import java.util.Base64;
import net.minecraft.class_2338;
import net.minecraft.class_2680;

public class im extends iw {
   public class_2338 pos;
   public class_2680 newState;
   public class_2680 oldState;

   public im(class_2338 pos, class_2680 newState, class_2680 oldState) {
      this.pos = pos;
      this.newState = oldState;
      this.oldState = newState;
   }

   // $FF: synthetic method
   private static String d115(String var0) {
      byte[] var1 = Base64.getDecoder().decode(var0);
      byte[] var2 = new byte[var1.length];

      for(int var3 = 0; var3 < var1.length; ++var3) {
         var2[var3] = (byte)(var1[var3] ^ 4450 + var3 & 255);
      }

      return new String(var2);
   }
}
