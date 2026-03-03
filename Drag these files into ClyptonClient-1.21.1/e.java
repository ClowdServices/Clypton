import java.util.Base64;
import net.minecraft.class_332;

public interface e {
   void render(class_332 var1, int var2, int var3, int var4, int var5, int var6);

   void onClick(int var1, int var2);

   void onRelease();

   int getHeight();

   // $FF: synthetic method
   private static String d435(String var0) {
      byte[] var1 = Base64.getDecoder().decode(var0);
      byte[] var2 = new byte[var1.length];

      for(int var3 = 0; var3 < var1.length; ++var3) {
         var2[var3] = (byte)(var1[var3] ^ 4381 + var3 & 255);
      }

      return new String(var2);
   }
}
