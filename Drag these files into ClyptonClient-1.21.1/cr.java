import java.awt.Color;
import java.util.Base64;
import java.util.function.Consumer;
import java.util.function.Supplier;
import net.minecraft.class_332;

public class cr implements e {
   private final String name;
   private final Supplier<Boolean> getter;
   private final Consumer<Boolean> setter;

   public cr(String name, Supplier<Boolean> getter, Consumer<Boolean> setter) {
      this.name = name;
      this.getter = getter;
      this.setter = setter;
   }

   public void render(class_332 ctx, int x, int y, int width, int mouseX, int mouseY) {
      boolean value = (Boolean)this.getter.get();
      boolean hovered = mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + 20;
      i.drawString(this.name, ctx, x, y + 1, hovered ? -1 : -2302744);
      int toggleX = x + width - 36;
      int toggleY = y + 1;
      Color toggleBg = value ? ch.safeColor(19 ^ 100, 0, 50 ^ 205, 255) : ch.safeColor(40, 40, 55, 255);
      ctx.method_25294(toggleX, toggleY, toggleX + 32, toggleY + 16, toggleBg.getRGB());
      int knobX = value ? toggleX + 18 : toggleX + 2;
      ctx.method_25294(knobX, toggleY + 2, knobX + 12, toggleY + 14, ch.safeColor(255, 232 ^ 23, 211 ^ 44, 255).getRGB());
   }

   public void onClick(int relX, int relY) {
      this.setter.accept(!(Boolean)this.getter.get());
   }

   public void onRelease() {
   }

   public int getHeight() {
      return 22;
   }

   // $FF: synthetic method
   private static String d291(String var0) {
      byte[] var1 = Base64.getDecoder().decode(var0);
      byte[] var2 = new byte[var1.length];

      for(int var3 = 0; var3 < var1.length; ++var3) {
         var2[var3] = (byte)(var1[var3] ^ 7400 + var3 & 255);
      }

      return new String(var2);
   }
}
