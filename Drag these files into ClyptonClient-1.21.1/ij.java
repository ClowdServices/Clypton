import java.util.Base64;
import java.util.function.Consumer;
import java.util.function.Supplier;
import net.minecraft.class_332;

public class ij implements e {
   private final String name;
   private final Supplier<Float> getter;
   private final Consumer<Float> setter;
   private final float min;
   private final float max;
   private boolean dragging;

   public ij(String name, Supplier<Float> getter, Consumer<Float> setter, float min, float max) {
      this.name = name;
      this.getter = getter;
      this.setter = setter;
      this.min = min;
      this.max = max;
   }

   public void render(class_332 ctx, int x, int y, int width, int mouseX, int mouseY) {
      float value = (Float)this.getter.get();
      String valueStr = String.format(d852("tb+j9Q=="), value);
      i.drawString(this.name, ctx, x, y, -2302744);
      int valueWidth = i.getWidth(valueStr);
      i.drawString(valueStr, ctx, x + width - valueWidth, y, -8977478 - 699);
      int trackY = y + 18;
      ctx.method_25294(x, trackY, x + width, trackY + 3, ch.safeColor(30, 30, 45, 187 ^ 68).getRGB());
      float progress = (value - this.min) / (this.max - this.min);
      int fillWidth = (int)((float)width * progress);
      if (fillWidth > 0) {
         ctx.method_25294(x, trackY, x + fillWidth, trackY + 3, ch.safeColor(119, 0, 255, 255).getRGB());
      }

      int handleX = (int)((float)x + (float)width * progress);
      ctx.method_25294(handleX - 3, trackY - 3, handleX + 3, trackY + (130 ^ 132), ch.safeColor(255, 119 ^ 136, 255, 255).getRGB());
      if (this.dragging) {
         float newProgress = Math.max(0.0F, Math.min(1.0F, (float)(mouseX - x) / (float)width));
         this.setter.accept(this.min + newProgress * (this.max - this.min));
      }

   }

   public void onClick(int relX, int relY) {
      this.dragging = true;
   }

   public void onRelease() {
      this.dragging = false;
   }

   public int getHeight() {
      return 224 ^ 250;
   }

   // $FF: synthetic method
   private static String d852(String var0) {
      byte[] var1 = Base64.getDecoder().decode(var0);
      byte[] var2 = new byte[var1.length];

      for(int var3 = 0; var3 < var1.length; ++var3) {
         var2[var3] = (byte)(var1[var3] ^ 5008 + var3 & 255);
      }

      return new String(var2);
   }
}
