import java.util.Base64;
import net.minecraft.class_332;

public class fv extends cn {
   public fv(int x, int y) {
      super(d606("1/HGzg=="), x, y, 180, 18);
   }

   public void render(class_332 context, int mouseX, int mouseY, boolean dragging, boolean selected) {
      this.renderBorder(context, dragging, selected);
      String text = d606("2M/zm4KSkJGG24j5w8XLl46dhdzB");
      i.drawString(text, context, this.x + 6, this.y + 5, ch.safeColor(200, 200, 220, 255).getRGB());
   }

   public void savePositionToModule(hf hudModule) {
      hudModule.infoX = this.x;
      hudModule.infoY = this.y;
   }

   // $FF: synthetic method
   private static String d606(String var0) {
      byte[] var1 = Base64.getDecoder().decode(var0);
      byte[] var2 = new byte[var1.length];

      for(int var3 = 0; var3 < var1.length; ++var3) {
         var2[var3] = (byte)(var1[var3] ^ 7070 + var3 & 255);
      }

      return new String(var2);
   }
}
