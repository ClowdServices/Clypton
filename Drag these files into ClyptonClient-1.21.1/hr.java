import java.util.Base64;
import net.minecraft.class_1799;
import net.minecraft.class_1802;
import net.minecraft.class_2561;
import net.minecraft.class_2960;
import net.minecraft.class_332;
import net.minecraft.class_368;
import net.minecraft.class_374;
import net.minecraft.class_4587;
import net.minecraft.class_368.class_369;

public class hr implements class_368 {
   private static final class_2960 TEXTURE = class_2960.method_60655(d108("0dTQ2qOzo6Ww"), "toast/advancement");
   private final String chunkX;
   private final String chunkZ;
   private long startTime;
   private boolean hasPlayed = false;
   private static final long DISPLAY_TIME = 3000L;

   public hr(String x, String z) {
      this.chunkX = x;
      this.chunkZ = z;
   }

   public class_369 method_1986(class_332 context, class_374 manager, long currentTime) {
      if (this.startTime == 0L) {
         this.startTime = currentTime;
      }

      context.method_52706(TEXTURE, 0, 0, this.method_29049(), this.method_29050());
      class_4587 matrices = context.method_51448();
      matrices.method_22903();
      matrices.method_46416(7.0F, 6.5F, 0.0F);
      matrices.method_22905(1.1F, 1.1F, 1.0F);
      context.method_51427(new class_1799(class_1802.field_8251), 0, 0);
      matrices.method_22909();
      context.method_51439(manager.method_1995().field_1772, class_2561.method_43470(d108("/9XL0avhhKyxq6Lm")), 248 ^ 230, 7, 6963141, false);
      String var10002 = this.chunkX;
      context.method_51439(manager.method_1995().field_1772, class_2561.method_43470("X: " + var10002 + " Z: " + this.chunkZ), 30, 196 ^ 214, 16777215, false);
      return currentTime - this.startTime < 3000L ? class_369.field_2210 : class_369.field_2209;
   }

   public Object method_1987() {
      return gs.INSTANCE;
   }

   public int method_29049() {
      return 160;
   }

   public int method_29050() {
      return 32;
   }

   // $FF: synthetic method
   private static String d108(String var0) {
      byte[] var1 = Base64.getDecoder().decode(var0);
      byte[] var2 = new byte[var1.length];

      for(int var3 = 0; var3 < var1.length; ++var3) {
         var2[var3] = (byte)(var1[var3] ^ 3516 + var3 & (155 ^ 100));
      }

      return new String(var2);
   }
}
