import com.mojang.blaze3d.platform.GlStateManager;
import java.awt.Font;
import java.io.InputStream;
import java.util.Base64;
import java.util.Objects;
import java.util.Random;
import net.minecraft.class_286;
import net.minecraft.class_287;
import net.minecraft.class_289;
import net.minecraft.class_290;
import net.minecraft.class_4587;
import net.minecraft.class_293.class_5596;

public final class cz {
   public Random random = new Random();
   private float posX;
   private float posY;
   private final int[] colorCode = new int[32];
   private boolean isBold;
   private boolean isItalic;
   private boolean isUnderline;
   private boolean isStrikethrough;
   private final as regular;
   private final as bold;
   private final as italic;
   private final as boldItalic;

   public cz(as regular, as bold, as italic, as boldItalic) {
      this.regular = regular;
      this.bold = bold;
      this.italic = italic;
      this.boldItalic = boldItalic;

      for(int n = 0; n < 32; ++n) {
         int j = (n >> 3 & 1) * 85;
         int k = (n >> 2 & 1) * (246 ^ 92) + j;
         int l = (n >> 1 & 1) * (40 ^ 130) + j;
         int m = (n & 1) * 170 + j;
         if (n == (205 ^ 203)) {
            k += 85;
         }

         if (n >= 16) {
            k /= 4;
            l /= 4;
            m /= 4;
         }

         this.colorCode[n] = (k & 255) << 16 | (l & 255) << 8 | m & (54 ^ 201);
      }

   }

   public static cz a(CharSequence font, int size, boolean bold, boolean italic, boolean boldItalic) {
      char[] chars = new char[395 ^ 139];

      for(int i = 0; i < 256; ++i) {
         chars[i] = (char)i;
      }

      as regularPage = new as(new Font(font.toString(), 0, size), true, true);
      regularPage.generate(chars);
      regularPage.setup();
      as boldPage = regularPage;
      as italicPage = regularPage;
      as boldItalicPage = regularPage;
      if (bold) {
         boldPage = new as(new Font(font.toString(), 1, size), true, true);
         boldPage.generate(chars);
         boldPage.setup();
      }

      if (italic) {
         italicPage = new as(new Font(font.toString(), 2, size), true, true);
         italicPage.generate(chars);
         italicPage.setup();
      }

      if (boldItalic) {
         boldItalicPage = new as(new Font(font.toString(), 3, size), true, true);
         boldItalicPage.generate(chars);
         boldItalicPage.setup();
      }

      return new cz(regularPage, boldPage, italicPage, boldItalicPage);
   }

   public static cz init(CharSequence id, int size, boolean bold, boolean italic, boolean boldItalic) {
      try {
         char[] chars = new char[256];

         for(int i = 0; i < chars.length; ++i) {
            chars[i] = (char)i;
         }

         Font font = Font.createFont(0, (InputStream)Objects.requireNonNull(cz.class.getResourceAsStream(id.toString()))).deriveFont(0, (float)size);
         as regularPage = new as(font, true, true);
         regularPage.generate(chars);
         regularPage.setup();
         as boldPage = regularPage;
         as italicPage = regularPage;
         as boldItalicPage = regularPage;
         if (bold) {
            boldPage = new as(Font.createFont(0, (InputStream)Objects.requireNonNull(cz.class.getResourceAsStream(id.toString()))).deriveFont(1, (float)size), true, true);
            boldPage.generate(chars);
            boldPage.setup();
         }

         if (italic) {
            italicPage = new as(Font.createFont(0, (InputStream)Objects.requireNonNull(cz.class.getResourceAsStream(id.toString()))).deriveFont(2, (float)size), true, true);
            italicPage.generate(chars);
            italicPage.setup();
         }

         if (boldItalic) {
            boldItalicPage = new as(Font.createFont(0, (InputStream)Objects.requireNonNull(cz.class.getResourceAsStream(id.toString()))).deriveFont(3, (float)size), true, true);
            boldItalicPage.generate(chars);
            boldItalicPage.setup();
         }

         return new cz(regularPage, boldPage, italicPage, boldItalicPage);
      } catch (Throwable var11) {
         var11.printStackTrace(System.err);
         return null;
      }
   }

   public int drawStringWithShadow(class_4587 matrices, CharSequence text, float x, float y, int color) {
      return this.drawString(matrices, text, x, y, color, true);
   }

   public int drawStringWithShadow(class_4587 matrices, CharSequence text, double x, double y, int color) {
      return this.drawString(matrices, text, (float)x, (float)y, color, true);
   }

   public int drawString(class_4587 matrices, CharSequence text, float x, float y, int color) {
      return this.drawString(matrices, text, x, y, color, false);
   }

   public int drawString(class_4587 matrices, CharSequence text, double x, double y, int color) {
      return this.drawString(matrices, text, (float)x, (float)y, color, false);
   }

   public int drawCenteredString(class_4587 matrices, CharSequence text, double x, double y, float scale, int color) {
      return this.drawString(matrices, text, (float)x - (float)(this.getStringWidth(text) / 2), (float)y, scale, color, false);
   }

   public int drawCenteredString(class_4587 matrices, CharSequence text, double x, double y, int color) {
      return this.drawString(matrices, text, (float)x - (float)(this.getStringWidth(text) / 2), (float)y, color, false);
   }

   public int drawCenteredStringWithShadow(class_4587 matrices, CharSequence text, double x, double y, int color) {
      return this.drawString(matrices, text, (float)x - (float)(this.getStringWidth(text) / 2), (float)y, color, true);
   }

   public int drawString(class_4587 matrices, CharSequence text, float x, float y, float scale, int color, boolean shadow) {
      this.resetStyles();
      return shadow ? Math.max(this.renderString(matrices, text, x + 1.0F, y + 1.0F, scale, color, true), this.renderString(matrices, text, x, y, scale, color, false)) : this.renderString(matrices, text, x, y, scale, color, false);
   }

   public int drawString(class_4587 matrices, CharSequence text, float x, float y, int color, boolean shadow) {
      this.resetStyles();
      return shadow ? Math.max(this.renderString(matrices, text, x + 1.0F, y + 1.0F, color, true), this.renderString(matrices, text, x, y, color, false)) : this.renderString(matrices, text, x, y, color, false);
   }

   private int renderString(class_4587 matrices, CharSequence text, float x, float y, int color, boolean shadow) {
      if (text == null) {
         return 0;
      } else {
         if ((color & -67108864) == 0) {
            color |= -16777216;
         }

         if (shadow) {
            color = (color & 16580490 - 654) >> 2 | color & -16777082 - 134;
         }

         this.posX = x * 2.0F;
         this.posY = y * 2.0F;
         this.a(matrices, text, shadow, color);
         return (int)(this.posX / 4.0F);
      }
   }

   private int renderString(class_4587 matrices, CharSequence text, float x, float y, float scale, int color, boolean shadow) {
      if (text == null) {
         return 0;
      } else {
         if ((color & -67108864) == 0) {
            color |= -16777216;
         }

         if (shadow) {
            color = (color & 16579836) >> 2 | color & -16777216;
         }

         this.posX = x * 2.0F;
         this.posY = y * 2.0F;
         this.renderStringAtPos(matrices, text, scale, shadow, color);
         return (int)(this.posX / 4.0F);
      }
   }

   private void a(class_4587 matrices, CharSequence text, boolean shadow, int color) {
      as page = this.getPage();
      float g = (float)(color >> 16 & (47 ^ 208)) / 255.0F;
      float h = (float)(color >> 8 & (152 ^ 103)) / 255.0F;
      float k = (float)(color & 255) / 255.0F;
      matrices.method_22903();
      matrices.method_22905(0.5F, 0.5F, 0.5F);
      GlStateManager._enableBlend();
      GlStateManager._blendFunc(770, 771);
      page.bind();
      GlStateManager._texParameter(3553, 10240, 9892 ^ 164);
      GlStateManager._texParameter(3553, 10241, 9728);

      for(int i = 0; i < text.length(); ++i) {
         char ch = text.charAt(i);
         if (ch == (16 ^ 183) && i + 1 < text.length()) {
            int index = d932("oKCgoKCgoKCgoPv5//n7+cvNz83L1w==").indexOf(Character.toLowerCase(text.charAt(i + 1)));
            if (index < 16) {
               this.isBold = false;
               this.isStrikethrough = false;
               this.isUnderline = false;
               this.isItalic = false;
               if (index < 0) {
                  index = 15;
               }

               if (shadow) {
                  index += 16;
               }

               int j1 = this.colorCode[index];
               g = (float)(j1 >> (217 ^ 201) & 255) / 255.0F;
               h = (float)(j1 >> 8 & 255) / 255.0F;
               k = (float)(j1 & (26 ^ 229)) / 255.0F;
            } else if (index != 16) {
               if (index == (137 ^ 152)) {
                  this.isBold = true;
               } else if (index == 18) {
                  this.isStrikethrough = true;
               } else if (index == 19) {
                  this.isUnderline = true;
               } else if (index == 20) {
                  this.isItalic = true;
               } else {
                  this.isBold = false;
                  this.isStrikethrough = false;
                  this.isUnderline = false;
                  this.isItalic = false;
               }
            }

            ++i;
         } else {
            page = this.getPage();
            page.bind();
            this.doDraw(page.drawChar(matrices, ch, this.posX, this.posY, g, k, h, (float)(color >> (126 ^ 102) & 255) / 255.0F), page);
         }
      }

      page.unbind();
      matrices.method_22909();
   }

   private void renderStringAtPos(class_4587 matrices, CharSequence text, float scale, boolean shadow, int color) {
      as page = this.getPage();
      float g = (float)(color >> (130 ^ 146) & 255) / 255.0F;
      float h = (float)(color >> 8 & (111 ^ 144)) / 255.0F;
      float k = (float)(color & 255) / 255.0F;
      matrices.method_22903();
      matrices.method_22905(scale, scale, scale);
      GlStateManager._enableBlend();
      GlStateManager._blendFunc(871 ^ 101, 987 ^ 216);
      page.bind();
      GlStateManager._texParameter(3463 ^ 102, 10240, 9728);
      GlStateManager._texParameter(3553, 10391 ^ 150, 9797 ^ 69);

      for(int i = 0; i < text.length(); ++i) {
         char ch = text.charAt(i);
         if (ch == 167 && i + 1 < text.length()) {
            int index = d932("oKCgoKCgoKCgoPv5//n7+cvNz83L1w==").indexOf(Character.toLowerCase(text.charAt(i + 1)));
            if (index < 16) {
               this.isBold = false;
               this.isStrikethrough = false;
               this.isUnderline = false;
               this.isItalic = false;
               if (index < 0) {
                  index = 15;
               }

               if (shadow) {
                  index += 16;
               }

               int j1 = this.colorCode[index];
               g = (float)(j1 >> 16 & 255) / 255.0F;
               h = (float)(j1 >> (102 ^ 110) & (63 ^ 192)) / 255.0F;
               k = (float)(j1 & (167 ^ 88)) / 255.0F;
            } else if (index != 16) {
               if (index == 17) {
                  this.isBold = true;
               } else if (index == 18) {
                  this.isStrikethrough = true;
               } else if (index == 19) {
                  this.isUnderline = true;
               } else if (index == (158 ^ 138)) {
                  this.isItalic = true;
               } else {
                  this.isBold = false;
                  this.isStrikethrough = false;
                  this.isUnderline = false;
                  this.isItalic = false;
               }
            }

            ++i;
         } else {
            page = this.getPage();
            page.bind();
            this.doDraw(page.drawChar(matrices, ch, this.posX, this.posY, g, k, h, (float)(color >> (249 ^ 225) & (33 ^ 222)) / 255.0F), page);
         }
      }

      page.unbind();
      matrices.method_22909();
   }

   private void doDraw(float f, as page) {
      class_287 buffer;
      if (this.isStrikethrough) {
         buffer = class_289.method_1348().method_60827(class_5596.field_27382, class_290.field_1592);
         buffer.method_22912(this.posX, this.posY + (float)(page.getMaxHeight() / 2), 0.0F);
         buffer.method_22912(this.posX + f, this.posY + (float)(page.getMaxHeight() / 2), 0.0F);
         buffer.method_22912(this.posX + f, this.posY + (float)(page.getMaxHeight() / 2) - 1.0F, 0.0F);
         buffer.method_22912(this.posX, this.posY + (float)(page.getMaxHeight() / 2) - 1.0F, 0.0F);
         class_286.method_43433(buffer.method_60800());
      }

      if (this.isUnderline) {
         buffer = class_289.method_1348().method_60827(class_5596.field_27382, class_290.field_1592);
         int l = this.isUnderline ? -1 : 0;
         buffer.method_22912(this.posX + (float)l, this.posY + (float)page.getMaxHeight(), 0.0F);
         buffer.method_22912(this.posX + f, this.posY + (float)page.getMaxHeight(), 0.0F);
         buffer.method_22912(this.posX + f, this.posY + (float)page.getMaxHeight() - 1.0F, 0.0F);
         buffer.method_22912(this.posX + (float)l, this.posY + (float)page.getMaxHeight() - 1.0F, 0.0F);
         class_286.method_43433(buffer.method_60800());
      }

      this.posX += f;
   }

   private as getPage() {
      if (this.isBold && this.isItalic) {
         return this.boldItalic;
      } else if (this.isBold) {
         return this.bold;
      } else {
         return this.isItalic ? this.italic : this.regular;
      }
   }

   private void resetStyles() {
      this.isBold = false;
      this.isItalic = false;
      this.isUnderline = false;
      this.isStrikethrough = false;
   }

   public int getHeight() {
      return this.regular.getMaxHeight() / 2;
   }

   public int getStringWidth(CharSequence text) {
      if (text == null) {
         return 0;
      } else {
         int width = 0;
         boolean on = false;

         for(int i = 0; i < text.length(); ++i) {
            char ch = text.charAt(i);
            if (ch == '�') {
               on = true;
            } else if (on && ch >= '0' && ch <= 'r') {
               int index = d932("oKCgoKCgoKCgoPv5//n7+cvNz83L1w==").indexOf(ch);
               if (index < 16) {
                  this.isBold = false;
                  this.isItalic = false;
               } else if (index == 17) {
                  this.isBold = true;
               } else if (index == 20) {
                  this.isItalic = true;
               } else if (index == (1 ^ 20)) {
                  this.isBold = false;
                  this.isItalic = false;
               }

               ++i;
               on = false;
            } else {
               if (on) {
                  --i;
               }

               width += (int)(this.getPage().getWidth(text.charAt(i)) - 8.0F);
            }
         }

         return width / 2;
      }
   }

   public CharSequence trimStringToWidth(CharSequence text, int width) {
      return this.trimStringToWidth(text, width, false);
   }

   public CharSequence trimStringToWidth(CharSequence text, int maxWidth, boolean reverse) {
      StringBuilder sb = new StringBuilder();
      boolean on = false;
      int j = reverse ? text.length() - 1 : 0;
      int k = reverse ? -1 : 1;

      for(int width = 0; j >= 0 && j < text.length() && j < maxWidth; j += k) {
         char ch = text.charAt(j);
         if (ch == '�') {
            on = true;
         } else if (on && ch >= '0' && ch <= 'r') {
            int index = d932("oKCgoKCgoKCgoPv5//n7+cvNz83L1w==").indexOf(ch);
            if (index < 16) {
               this.isBold = false;
               this.isItalic = false;
            } else if (index == 17) {
               this.isBold = true;
            } else if (index == 20) {
               this.isItalic = true;
            } else if (index == 21) {
               this.isBold = false;
               this.isItalic = false;
            }

            ++j;
            on = false;
         } else {
            if (on) {
               --j;
            }

            ch = text.charAt(j);
            width += (int)((this.getPage().getWidth(ch) - 8.0F) / 2.0F);
         }

         if (j > width) {
            break;
         }

         if (reverse) {
            sb.insert(0, ch);
         } else {
            sb.append(ch);
         }
      }

      return db.of(sb.toString());
   }

   // $FF: synthetic method
   private static String d932(String var0) {
      byte[] var1 = Base64.getDecoder().decode(var0);
      byte[] var2 = new byte[var1.length];

      for(int var3 = 0; var3 < var1.length; ++var3) {
         var2[var3] = (byte)(var1[var3] ^ 5008 + var3 & (75 ^ 180));
      }

      return new String(var2);
   }
}
