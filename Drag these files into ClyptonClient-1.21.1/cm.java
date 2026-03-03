import java.util.Base64;
import net.minecraft.class_332;

public class cm extends cn {
   public cm(int x, int y, int size) {
      super(d686("ZEZNRA=="), x, y, size, size);
   }

   public void render(class_332 context, int mouseX, int mouseY, boolean dragging, boolean selected) {
      this.renderBorder(context, dragging, selected);
      String logo = "A";
      int logoWidth = i.getWidth(logo);
      i.drawString(logo, context, this.x + this.width / 2 - logoWidth / 2, this.y + this.height / 2 - 8, v.PRIMARY.getRGB());
   }

   public void savePositionToModule(hf hudModule) {
      hudModule.logoX = this.x;
      hudModule.logoY = this.y;
   }

   // $FF: synthetic method
   private static String d686(String var0) {
      byte[] var1 = Base64.getDecoder().decode(var0);
      byte[] var2 = new byte[var1.length];

      for(int var3 = 0; var3 < var1.length; ++var3) {
         var2[var3] = (byte)(var1[var3] ^ 1320 + var3 & 255);
      }

      return new String(var2);
   }
}
