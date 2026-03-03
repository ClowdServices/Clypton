import java.util.Base64;
import net.minecraft.class_332;

public class gc extends cn {
   public gc(int x, int y) {
      super(d766("Q2B0ZH52NFl/ZGw="), x, y, 160, 80);
   }

   public void render(class_332 context, int mouseX, int mouseY, boolean dragging, boolean selected) {
      this.renderBorder(context, dragging, selected);
      String[] modules = new String[]{d766("RWZ8fVNmZnQ="), d766("WGp8fnF6YGw="), d766("T3pkfkZ8YHB7"), d766("XX9ieHxn")};
      int yOff = this.y + (98 ^ 106);
      String[] var8 = modules;
      int var9 = modules.length;

      for(int var10 = 0; var10 < var9; ++var10) {
         String module = var8[var10];
         i.drawString(module, context, this.x + (180 ^ 188), yOff, v.PRIMARY.getRGB());
         yOff += 16;
      }

   }

   public void savePositionToModule(hf hudModule) {
      hudModule.moduleListX = this.x;
      hudModule.moduleListY = this.y;
   }

   // $FF: synthetic method
   private static String d766(String var0) {
      byte[] var1 = Base64.getDecoder().decode(var0);
      byte[] var2 = new byte[var1.length];

      for(int var3 = 0; var3 < var1.length; ++var3) {
         var2[var3] = (byte)(var1[var3] ^ 8148 - 454 + var3 & 255);
      }

      return new String(var2);
   }
}
