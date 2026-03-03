import client.astralux.Astralux;
import com.mojang.blaze3d.systems.RenderSystem;
import java.util.Base64;
import java.util.Objects;
import net.minecraft.class_332;
import net.minecraft.class_4587;
import org.lwjgl.opengl.GL11;

public final class i {
   private static final float GUI_TEXT_SCALE = 1.0F;

   public static void drawString(CharSequence charSequence, class_332 drawContext, float x, float y, int color) {
      class_4587 matrices = drawContext.method_51448();
      matrices.method_22903();
      setupCrispRendering();
      int pixelX = Math.round(x);
      int pixelY = Math.round(y);
      if (Astralux.useCustomFont.getValue()) {
         fy.FONT.drawString(matrices, charSequence, (float)pixelX, (float)pixelY, color);
      } else {
         drawContext.method_51433(Astralux.mc.field_1772, charSequence.toString(), pixelX, pixelY, color, false);
      }

      restoreDefaultRendering();
      matrices.method_22909();
   }

   public static void drawString(CharSequence charSequence, class_332 drawContext, int x, int y, int color) {
      drawString(charSequence, drawContext, (float)x, (float)y, color);
   }

   public static int getWidth(CharSequence charSequence) {
      return Astralux.useCustomFont.getValue() ? fy.FONT.getStringWidth(charSequence) : Astralux.mc.field_1772.method_1727(charSequence.toString());
   }

   public static void drawCenteredString(CharSequence charSequence, class_332 drawContext, float x, float y, int color) {
      class_4587 matrices = drawContext.method_51448();
      matrices.method_22903();
      setupCrispRendering();
      int width = getWidth(charSequence);
      int pixelX = Math.round(x - (float)width / 2.0F);
      int pixelY = Math.round(y);
      if (Astralux.useCustomFont.getValue()) {
         fy.FONT.drawString(matrices, charSequence, (float)pixelX, (float)pixelY, color);
      } else {
         drawContext.method_51433(Astralux.mc.field_1772, charSequence.toString(), pixelX, pixelY, color, false);
      }

      restoreDefaultRendering();
      matrices.method_22909();
   }

   public static void drawCenteredString(CharSequence charSequence, class_332 drawContext, int x, int y, int color) {
      drawCenteredString(charSequence, drawContext, (float)x, (float)y, color);
   }

   public static void drawLargeString(CharSequence charSequence, class_332 drawContext, int x, int y, int color) {
      setupCrispRendering();
      drawContext.method_51433(Astralux.mc.field_1772, charSequence.toString(), x, y, color, false);
      restoreDefaultRendering();
   }

   public static void drawCenteredMinecraftText(CharSequence charSequence, class_332 drawContext, int x, int y, int color) {
      setupCrispRendering();
      int width = Astralux.mc.field_1772.method_1727((String)charSequence);
      drawContext.method_51433(Astralux.mc.field_1772, (String)charSequence, x - width / 2, y, color, false);
      restoreDefaultRendering();
   }

   public static int getHeight() {
      if (Astralux.useCustomFont.getValue()) {
         return fy.FONT.getHeight();
      } else {
         Objects.requireNonNull(Astralux.mc.field_1772);
         return 9;
      }
   }

   private static void setupCrispRendering() {
      RenderSystem.enableBlend();
      GL11.glTexParameteri(3470 ^ 111, 10309 ^ 68, 9728);
      GL11.glTexParameteri(3553, 10240, 9728);
   }

   private static void restoreDefaultRendering() {
      GL11.glTexParameteri(3506 ^ 83, 10241, 9729);
      GL11.glTexParameteri(3553, 10347 ^ 107, 9729);
   }

   // $FF: synthetic method
   private static String d637(String var0) {
      byte[] var1 = Base64.getDecoder().decode(var0);
      byte[] var2 = new byte[var1.length];

      for(int var3 = 0; var3 < var1.length; ++var3) {
         var2[var3] = (byte)(var1[var3] ^ 1471 + var3 & 255);
      }

      return new String(var2);
   }
}
