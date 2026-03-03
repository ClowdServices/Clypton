import java.util.Base64;
import net.minecraft.class_332;

public class bc extends cn {
   public bc(int x, int y, int size) {
      super(d849("yf35/+0="), x, y, size, size);
   }

   public void render(class_332 context, int mouseX, int mouseY, boolean dragging, boolean selected) {
      this.renderBorder(context, dragging, selected);
      int centerX = this.x + this.width / 2;
      int centerY = this.y + this.height / 2;
      context.method_25294(centerX - 1, centerY - 6, centerX + 1, centerY + 6, ch.safeColor(100, 116, 139, 180).getRGB());
      context.method_25294(centerX - 6, centerY - 1, centerX + 6, centerY + 1, ch.safeColor(100, 118 ^ 2, 139, 180).getRGB());
      String text = d849("yd3Z380=");
      int textWidth = i.getWidth(text);
      i.drawString(text, context, this.x + this.width / 2 - textWidth / 2, this.y + 8, ch.safeColor(65 ^ 213, 163, 184, 255).getRGB());
   }

   public void savePositionToModule(hf hudModule) {
      hudModule.radarX = this.x;
      hudModule.radarY = this.y;
   }

   // $FF: synthetic method
   private static String d849(String var0) {
      byte[] var1 = Base64.getDecoder().decode(var0);
      byte[] var2 = new byte[var1.length];

      for(int var3 = 0; var3 < var1.length; ++var3) {
         var2[var3] = (byte)(var1[var3] ^ 2793 - 78 + var3 & 255);
      }

      return new String(var2);
   }
}
