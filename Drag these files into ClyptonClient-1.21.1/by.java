import java.awt.Color;
import java.util.Base64;
import net.minecraft.class_332;

public class by extends cn {
   public by(int x, int y) {
      super(d832("8s7FzA=="), x, y, 100, 18);
   }

   public void render(class_332 context, int mouseX, int mouseY, boolean dragging, boolean selected) {
      this.renderBorder(context, dragging, selected);
      String text = d832("l5WSmp6RmZuO//0=");
      int textWidth = i.getWidth(text);
      i.drawString(text, context, this.x + this.width / 2 - textWidth / 2, this.y + 5, Color.WHITE.getRGB());
   }

   public void savePositionToModule(hf hudModule) {
      hudModule.timeX = this.x;
      hudModule.timeY = this.y;
   }

   // $FF: synthetic method
   private static String d832(String var0) {
      byte[] var1 = Base64.getDecoder().decode(var0);
      byte[] var2 = new byte[var1.length];

      for(int var3 = 0; var3 < var1.length; ++var3) {
         var2[var3] = (byte)(var1[var3] ^ 10921 - 515 + var3 & 255);
      }

      return new String(var2);
   }
}
