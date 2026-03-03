import com.mojang.blaze3d.systems.RenderSystem;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;
import java.util.Base64;
import java.util.HashMap;
import javax.imageio.ImageIO;
import net.minecraft.class_1011;
import net.minecraft.class_1043;
import net.minecraft.class_1044;
import net.minecraft.class_286;
import net.minecraft.class_287;
import net.minecraft.class_289;
import net.minecraft.class_290;
import net.minecraft.class_4587;
import net.minecraft.class_757;
import net.minecraft.class_293.class_5596;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;

public final class as {
   private int imageSize;
   private int maxHeight = -1;
   private final Font font;
   private final boolean antiAlias;
   private final boolean fractionalMetrics;
   private final HashMap<Character, hp> glyphs = new HashMap();
   private BufferedImage img;
   private class_1044 texture;

   public as(Font font, boolean antiAlias, boolean fractionalMetrics) {
      this.font = font;
      this.antiAlias = antiAlias;
      this.fractionalMetrics = fractionalMetrics;
   }

   public void generate(char[] chars) {
      double width = -1.0D;
      double height = -1.0D;
      FontRenderContext frc = new FontRenderContext(new AffineTransform(), this.antiAlias, this.fractionalMetrics);
      char[] var7 = chars;
      int var8 = chars.length;

      for(int var9 = 0; var9 < var8; ++var9) {
         char item = var7[var9];
         Rectangle2D bounds = this.font.getStringBounds(Character.toString(item), frc);
         if (width < bounds.getWidth()) {
            width = bounds.getWidth();
         }

         if (height < bounds.getHeight()) {
            height = bounds.getHeight();
         }
      }

      double maxWidth = width + 2.0D;
      double maxHeight = height + 2.0D;
      this.imageSize = (int)Math.ceil(Math.max(Math.ceil(Math.sqrt(maxWidth * maxWidth * (double)chars.length) / maxWidth), Math.ceil(Math.sqrt(maxHeight * maxHeight * (double)chars.length) / maxHeight)) * Math.max(maxWidth, maxHeight)) + 1;
      this.img = new BufferedImage(this.imageSize, this.imageSize, 2);
      Graphics2D graphics = this.img.createGraphics();
      graphics.setFont(this.font);
      graphics.setColor(ch.safeColor(255, 255, 255, 0));
      graphics.fillRect(0, 0, this.imageSize, this.imageSize);
      graphics.setColor(Color.white);
      graphics.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, this.fractionalMetrics ? RenderingHints.VALUE_FRACTIONALMETRICS_ON : RenderingHints.VALUE_FRACTIONALMETRICS_OFF);
      graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, this.antiAlias ? RenderingHints.VALUE_ANTIALIAS_ON : RenderingHints.VALUE_ANTIALIAS_OFF);
      graphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, this.antiAlias ? RenderingHints.VALUE_TEXT_ANTIALIAS_ON : RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
      FontMetrics metrics = graphics.getFontMetrics();
      int currentHeight = 0;
      int posX = 0;
      int posY = 1;
      char[] var16 = chars;
      int var17 = chars.length;

      for(int var18 = 0; var18 < var17; ++var18) {
         char c = var16[var18];
         hp glyph = new hp();
         Rectangle2D bounds = metrics.getStringBounds(Character.toString(c), graphics);
         glyph.width = bounds.getBounds().width + (248 ^ 240);
         glyph.height = bounds.getBounds().height;
         if (posX + glyph.width >= this.imageSize) {
            posX = 0;
            posY += currentHeight;
            currentHeight = 0;
         }

         glyph.x = posX;
         glyph.y = posY;
         if (glyph.height > this.maxHeight) {
            this.maxHeight = glyph.height;
         }

         if (glyph.height > currentHeight) {
            currentHeight = glyph.height;
         }

         graphics.drawString(Character.toString(c), posX + 2, posY + metrics.getAscent());
         posX += glyph.width;
         this.glyphs.put(c, glyph);
      }

   }

   public void setup() {
      try {
         ByteArrayOutputStream output = new ByteArrayOutputStream();
         ImageIO.write(this.img, d402("U0pC"), output);
         byte[] byteArray = output.toByteArray();
         ByteBuffer data = BufferUtils.createByteBuffer(byteArray.length).put(byteArray);
         data.flip();
         this.texture = new class_1043(class_1011.method_4324(data));
         RenderSystem.setShaderTexture(0, this.texture.method_4624());
         GL11.glTexParameteri(3553, 10241, 9728);
         GL11.glTexParameteri(3553, 10327 ^ 87, 9754 ^ 26);
      } catch (Throwable var4) {
         var4.printStackTrace(System.err);
      }

   }

   public void bind() {
      RenderSystem.setShaderTexture(0, this.texture.method_4624());
      GL11.glTexParameteri(3553, 10311 ^ 70, 9728);
      GL11.glTexParameteri(3553, 10240, 9728);
   }

   public void unbind() {
      RenderSystem.setShaderTexture(0, 0);
   }

   public float drawChar(class_4587 stack, char ch, float x, float y, float r, float b, float g, float alpha) {
      hp glyph = (hp)this.glyphs.get(ch);
      if (glyph == null) {
         return 0.0F;
      } else {
         float pageX = (float)glyph.x / (float)this.imageSize;
         float pageY = (float)glyph.y / (float)this.imageSize;
         float pageWidth = (float)glyph.width / (float)this.imageSize;
         float pageHeight = (float)glyph.height / (float)this.imageSize;
         float width = (float)glyph.width;
         float height = (float)glyph.height;
         RenderSystem.setShader(class_757::method_34543);
         this.bind();
         class_287 builder = class_289.method_1348().method_60827(class_5596.field_27382, class_290.field_1575);
         builder.method_22918(stack.method_23760().method_23761(), x, y + height, 0.0F).method_22915(r, g, b, alpha).method_22913(pageX, pageY + pageHeight);
         builder.method_22918(stack.method_23760().method_23761(), x + width, y + height, 0.0F).method_22915(r, g, b, alpha).method_22913(pageX + pageWidth, pageY + pageHeight);
         builder.method_22918(stack.method_23760().method_23761(), x + width, y, 0.0F).method_22915(r, g, b, alpha).method_22913(pageX + pageWidth, pageY);
         builder.method_22918(stack.method_23760().method_23761(), x, y, 0.0F).method_22915(r, g, b, alpha).method_22913(pageX, pageY);
         class_286.method_43433(builder.method_60800());
         this.unbind();
         return width - 8.0F;
      }
   }

   public float getWidth(char c) {
      return (float)((hp)this.glyphs.get(c)).width;
   }

   public boolean isAntiAlias() {
      return this.antiAlias;
   }

   public boolean isFractionalMetrics() {
      return this.fractionalMetrics;
   }

   public int getMaxHeight() {
      return this.maxHeight;
   }

   // $FF: synthetic method
   private static String d402(String var0) {
      byte[] var1 = Base64.getDecoder().decode(var0);
      byte[] var2 = new byte[var1.length];

      for(int var3 = 0; var3 < var1.length; ++var3) {
         var2[var3] = (byte)(var1[var3] ^ 6435 + var3 & 255);
      }

      return new String(var2);
   }
}
