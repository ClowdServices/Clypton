import java.awt.Color;
import java.util.Base64;
import net.minecraft.class_332;

public class gr extends cn {
   public gr(int x, int y) {
      super(d397("88TSwtrEy9nH"), x, y, 112 ^ 252, 20);
   }

   public void render(class_332 context, int mouseX, int mouseY, boolean dragging, boolean selected) {
      this.renderBorder(context, dragging, selected);
      String text = d397("5dbS1cnF39OM25+BgA==");
      i.drawString(text, context, this.x + 6, this.y + (18 ^ 20), Color.WHITE.getRGB());
   }

   public void savePositionToModule(hf hudModule) {
      hudModule.watermarkX = this.x;
      hudModule.watermarkY = this.y;
   }

   // $FF: synthetic method
   private static String d397(String var0) {
      byte[] var1 = Base64.getDecoder().decode(var0);
      byte[] var2 = new byte[var1.length];

      for(int var3 = 0; var3 < var1.length; ++var3) {
         var2[var3] = (byte)(var1[var3] ^ 1956 + var3 & (250 ^ 5));
      }

      return new String(var2);
   }
}
