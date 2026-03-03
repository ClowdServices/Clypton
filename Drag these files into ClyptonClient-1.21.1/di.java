import java.util.Base64;
import net.minecraft.class_332;

public class di extends cn {
   public di(int x, int y) {
      super(d485("Ej08JjE/OTktPyg="), x, y, 220, 202 ^ 216);
   }

   public void render(class_332 context, int mouseX, int mouseY, boolean dragging, boolean selected) {
      this.renderBorder(context, dragging, selected);
      String text = d485("CWhzZWdld3gAYHtqaX5/OltCTlBQUA==");
      i.drawString(text, context, this.x + (200 ^ 206), this.y + 5, v.SUCCESS.getRGB());
   }

   public void savePositionToModule(hf hudModule) {
      hudModule.coordsX = this.x;
      hudModule.coordsY = this.y;
   }

   // $FF: synthetic method
   private static String d485(String var0) {
      byte[] var1 = Base64.getDecoder().decode(var0);
      byte[] var2 = new byte[var1.length];

      for(int var3 = 0; var3 < var1.length; ++var3) {
         var2[var3] = (byte)(var1[var3] ^ 3380 - 227 + var3 & (229 ^ 26));
      }

      return new String(var2);
   }
}
