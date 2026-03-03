import client.astralux.Astralux;
import java.util.Base64;
import net.minecraft.class_2561;
import net.minecraft.class_310;
import net.minecraft.class_332;
import net.minecraft.class_437;
import org.lwjgl.glfw.GLFW;

class ad extends class_437 {
   private final bu setting;
   private String content;
   private int cursorPosition;
   private int selectionStart;
   private final boolean selecting;
   private long lastCursorBlink;
   private boolean cursorVisible;
   private final int CURSOR_BLINK_SPEED = 536 ^ 10;
   final el this$0;

   public ad(el this$0, bu setting) {
      super(class_2561.method_43473());
      this.this$0 = this$0;
      this.selectionStart = -1;
      this.selecting = false;
      this.lastCursorBlink = 0L;
      this.cursorVisible = true;
      this.setting = setting;
      this.content = setting.getValue();
      this.cursorPosition = this.content.length();
   }

   public void method_25394(class_332 drawContext, int n, int n2, float n3) {
      iq.unscaledProjection();
      super.method_25394(drawContext, n * (int)class_310.method_1551().method_22683().method_4495(), n2 * (int)class_310.method_1551().method_22683().method_4495(), n3);
      long currentTimeMillis = System.currentTimeMillis();
      if (currentTimeMillis - this.lastCursorBlink > 530L) {
         this.cursorVisible = !this.cursorVisible;
         this.lastCursorBlink = currentTimeMillis;
      }

      int width = this.this$0.mc.method_22683().method_4480();
      int height = this.this$0.mc.method_22683().method_4507();
      int a;
      if (gm.renderBackground.getValue()) {
         a = 7 ^ 179;
      } else {
         a = 0;
      }

      drawContext.method_25294(0, 0, width, height, ch.safeColor(0, 0, 0, a).getRGB());
      int width2 = this.this$0.mc.method_22683().method_4480();
      int height2 = this.this$0.mc.method_22683().method_4507();
      int a2 = ac.clampInt(i.getWidth(this.content) + 80, 535 ^ 79, this.this$0.mc.method_22683().method_4480() - 100);
      int n4 = (width2 - a2) / 2;
      int n5 = (height2 - 120) / 2;
      iq.renderRoundedQuad(drawContext.method_51448(), aq.getPanelBackgroundWithAlpha(76 ^ 188), (double)n4, (double)n5, (double)(n4 + a2), (double)(n5 + 120), 8.0D, 8.0D, 8.0D, 8.0D, 20.0D);
      iq.renderRoundedQuad(drawContext.method_51448(), aq.getHeaderBackground(), (double)n4, (double)n5, (double)(n4 + a2), (double)(n5 + (207 ^ 209)), 8.0D, 8.0D, 0.0D, 0.0D, 20.0D);
      drawContext.method_25294(n4, n5 + 30, n4 + a2, n5 + (228 ^ 251), bi.getMainColor(1 ^ 254, 1).getRGB());
      i.drawCenteredString(this.setting.getName(), drawContext, n4 + a2 / 2, n5 + 8, aq.getBrightTextColor().getRGB());
      int n6 = n4 + 20;
      int n7 = n5 + (134 ^ 180);
      int n8 = a2 - 40;
      iq.renderRoundedQuad(drawContext.method_51448(), aq.getGridBackground(), (double)n6, (double)n7, (double)(n6 + n8), (double)(n7 + 30), 5.0D, 5.0D, 5.0D, 5.0D, 20.0D);
      iq.renderRoundedOutline(drawContext, aq.getButtonBorder(), (double)n6, (double)n7, (double)(n6 + n8), (double)(n7 + (102 ^ 120)), 5.0D, 5.0D, 5.0D, 5.0D, 1.0D, 20.0D);
      String content = this.content;
      int n9 = n6 + (206 ^ 196);
      int n10 = n7 + 10;
      String substring = this.content.substring(0, this.cursorPosition);
      content.substring(this.cursorPosition);
      int n11 = n9 + i.getWidth(substring);
      int n12;
      int n13;
      if (this.selectionStart != -1 && this.selectionStart != this.cursorPosition) {
         n12 = Math.min(this.selectionStart, this.cursorPosition);
         n13 = Math.max(this.selectionStart, this.cursorPosition);
         String substring2 = content.substring(0, n12);
         String substring3 = content.substring(n12, n13);
         String substring4 = content.substring(n13);
         int a3 = i.getWidth(substring2);
         int a4 = i.getWidth(substring3);
         i.drawString(substring2, drawContext, n9, n10, aq.getTextColor().getRGB());
         drawContext.method_25294(n9 + a3, n10 - 2, n9 + a3 + a4, n10 + 14, bi.getMainColor(100, 1).getRGB());
         i.drawString(substring3, drawContext, n9 + a3, n10, aq.getBrightTextColor().getRGB());
         i.drawString(substring4, drawContext, n9 + a3 + a4, n10, aq.getTextColor().getRGB());
      } else {
         i.drawString(content, drawContext, n9, n10, aq.getTextColor().getRGB());
         if (this.cursorVisible) {
            drawContext.method_25294(n11, n10 - 2, n11 + 1, n10 + (83 ^ 93), aq.getTextColor().getRGB());
         }
      }

      n12 = n5 + (93 ^ 37) - 30;
      n13 = n4 + a2 - (97 ^ 49) - (103 ^ 115);
      int n14 = n13 - 80 - 10;
      iq.renderRoundedQuad(drawContext.method_51448(), bi.getMainColor(255, 1), (double)n13, (double)n12, (double)(n13 + 80), (double)(n12 + 25), 5.0D, 5.0D, 5.0D, 5.0D, 20.0D);
      i.drawCenteredString(d175("jb6WhA=="), drawContext, n13 + (92 ^ 116), n12 + 6, aq.getBrightTextColor().getRGB());
      iq.renderRoundedQuad(drawContext.method_51448(), aq.getButtonBorder(), (double)n14, (double)n12, (double)(n14 + 80), (double)(n12 + 25), 5.0D, 5.0D, 5.0D, 5.0D, 20.0D);
      i.drawCenteredString(d175("nb6OgoeP"), drawContext, n14 + (178 ^ 154), n12 + 6, aq.getBrightTextColor().getRGB());
      i.drawString(d175("jq2FkpHDoZaFhpiMyp+DzZ2OhpTSkpqR1pSUlome"), drawContext, n4 + 20, n5 + (4 ^ 124) - 20, ch.safeColor(18 ^ 132, 150, 63 ^ 169, 115 ^ 187).getRGB());
      iq.scaledProjection();
   }

   public void method_16014(double n, double n2) {
      super.method_16014(n, n2);
      this.cursorVisible = true;
      this.lastCursorBlink = System.currentTimeMillis();
   }

   public boolean method_25402(double n, double n2, int n3) {
      double n4 = n * class_310.method_1551().method_22683().method_4495();
      double n5 = n2 * class_310.method_1551().method_22683().method_4495();
      int width = this.this$0.mc.method_22683().method_4480();
      int height = this.this$0.mc.method_22683().method_4507();
      int max = Math.max(600, ac.clampInt(i.getWidth(this.content) + 80, 400, this.this$0.mc.method_22683().method_4480() - (104 ^ 12)));
      int n6 = (height - 120) / 2 + (243 ^ 139) - (138 ^ 148);
      int n7 = (width - max) / 2 + max - (222 ^ 142) - 20;
      if (this.isHovered(n4, n5, n7, n6, 50 ^ 98, 25)) {
         this.setting.setValue(this.content.trim());
         this.this$0.mc.method_1507(Astralux.INSTANCE.GUI);
         return true;
      } else if (this.isHovered(n4, n5, n7 - 80 - 10, n6, 80, 127 ^ 102)) {
         this.this$0.mc.method_1507(Astralux.INSTANCE.GUI);
         return true;
      } else {
         return super.method_25402(n4, n5, n3);
      }
   }

   private boolean isHovered(double n, double n2, int n3, int n4, int n5, int n6) {
      return n >= (double)n3 && n <= (double)(n3 + n5) && n2 >= (double)n4 && n2 <= (double)(n4 + n6);
   }

   public boolean method_25404(int n, int n2, int n3) {
      this.cursorVisible = true;
      this.lastCursorBlink = System.currentTimeMillis();
      if (n == 256) {
         this.setting.setValue(this.content.trim());
         this.this$0.mc.method_1507(Astralux.INSTANCE.GUI);
         return true;
      } else if (n == (285 ^ 28)) {
         this.setting.setValue(this.content.trim());
         this.this$0.mc.method_1507(Astralux.INSTANCE.GUI);
         return true;
      } else {
         int max;
         if (isPaste(n)) {
            String clipboard = this.this$0.mc.field_1774.method_1460();
            if (this.selectionStart != -1 && this.selectionStart != this.cursorPosition) {
               max = Math.min(this.selectionStart, this.cursorPosition);
               this.content = this.content.substring(0, max) + clipboard + this.content.substring(Math.max(this.selectionStart, this.cursorPosition));
               this.cursorPosition = max + clipboard.length();
            } else {
               this.content = this.content.substring(0, this.cursorPosition) + clipboard + this.content.substring(this.cursorPosition);
               this.cursorPosition += clipboard.length();
            }

            this.selectionStart = -1;
            return true;
         } else if (isCopy(n)) {
            if (this.selectionStart != -1 && this.selectionStart != this.cursorPosition) {
               GLFW.glfwSetClipboardString(this.this$0.mc.method_22683().method_4490(), this.content.substring(Math.min(this.selectionStart, this.cursorPosition), Math.max(this.selectionStart, this.cursorPosition)));
            }

            return true;
         } else {
            String var10001;
            int min4;
            if (isCut(n)) {
               if (this.selectionStart != -1 && this.selectionStart != this.cursorPosition) {
                  min4 = Math.min(this.selectionStart, this.cursorPosition);
                  max = Math.max(this.selectionStart, this.cursorPosition);
                  GLFW.glfwSetClipboardString(this.this$0.mc.method_22683().method_4490(), this.content.substring(min4, max));
                  var10001 = this.content.substring(0, min4);
                  this.content = var10001 + this.content.substring(max);
                  this.cursorPosition = min4;
                  this.selectionStart = -1;
               }

               return true;
            } else if (n == 259) {
               if (this.selectionStart != -1 && this.selectionStart != this.cursorPosition) {
                  min4 = Math.min(this.selectionStart, this.cursorPosition);
                  this.content = this.content.substring(0, min4) + this.content.substring(Math.max(this.selectionStart, this.cursorPosition));
                  this.cursorPosition = min4;
                  this.selectionStart = -1;
               } else if (this.cursorPosition > 0) {
                  var10001 = this.content.substring(0, this.cursorPosition - 1);
                  this.content = var10001 + this.content.substring(this.cursorPosition);
                  --this.cursorPosition;
               }

               return true;
            } else if (n != 261) {
               if (n == (385 ^ 134)) {
                  if ((n3 & 1) != 0) {
                     if (this.selectionStart == -1) {
                        this.selectionStart = this.cursorPosition;
                     }
                  } else {
                     this.selectionStart = -1;
                  }

                  if (this.cursorPosition > 0) {
                     --this.cursorPosition;
                  }

                  return true;
               } else if (n == 262) {
                  if ((n3 & 1) != 0) {
                     if (this.selectionStart == -1) {
                        this.selectionStart = this.cursorPosition;
                     }
                  } else {
                     this.selectionStart = -1;
                  }

                  if (this.cursorPosition < this.content.length()) {
                     ++this.cursorPosition;
                  }

                  return true;
               } else if (n == 268) {
                  if ((n3 & 1) != 0) {
                     if (this.selectionStart == -1) {
                        this.selectionStart = this.cursorPosition;
                     }
                  } else {
                     this.selectionStart = -1;
                  }

                  this.cursorPosition = 0;
                  return true;
               } else if (n == (394 ^ 135)) {
                  if ((n3 & 1) != 0) {
                     if (this.selectionStart == -1) {
                        this.selectionStart = this.cursorPosition;
                     }
                  } else {
                     this.selectionStart = -1;
                  }

                  this.cursorPosition = this.content.length();
                  return true;
               } else {
                  return super.method_25404(n, n2, n3);
               }
            } else {
               if (this.selectionStart != -1 && this.selectionStart != this.cursorPosition) {
                  min4 = Math.min(this.selectionStart, this.cursorPosition);
                  this.content = this.content.substring(0, min4) + this.content.substring(Math.max(this.selectionStart, this.cursorPosition));
                  this.cursorPosition = min4;
                  this.selectionStart = -1;
               } else if (this.cursorPosition < this.content.length()) {
                  var10001 = this.content.substring(0, this.cursorPosition);
                  this.content = var10001 + this.content.substring(this.cursorPosition + 1);
               }

               return true;
            }
         }
      }
   }

   public boolean method_25400(char c, int n) {
      if (this.selectionStart != -1 && this.selectionStart != this.cursorPosition) {
         int min = Math.min(this.selectionStart, this.cursorPosition);
         this.content = this.content.substring(0, min) + c + this.content.substring(Math.max(this.selectionStart, this.cursorPosition));
         this.cursorPosition = min + 1;
         this.selectionStart = -1;
      } else {
         this.content = this.content.substring(0, this.cursorPosition) + c + this.content.substring(this.cursorPosition);
         ++this.cursorPosition;
      }

      this.cursorVisible = true;
      this.lastCursorBlink = System.currentTimeMillis();
      return true;
   }

   public static boolean isPaste(int n) {
      return n == 86 && hasControlDown();
   }

   public static boolean isCopy(int n) {
      return n == (177 ^ 242) && hasControlDown();
   }

   public static boolean isCut(int n) {
      return n == (75 ^ 19) && hasControlDown();
   }

   public static boolean hasControlDown() {
      int n;
      if (class_310.field_1703) {
         n = GLFW.glfwGetKey(class_310.method_1551().method_22683().method_4490(), 343);
      } else {
         n = GLFW.glfwGetKey(class_310.method_1551().method_22683().method_4490(), 341);
      }

      return n == 1;
   }

   public void method_25420(class_332 drawContext, int n, int n2, float n3) {
   }

   public boolean method_25422() {
      return false;
   }

   // $FF: synthetic method
   private static String d175(String var0) {
      byte[] var1 = Base64.getDecoder().decode(var0);
      byte[] var2 = new byte[var1.length];

      for(int var3 = 0; var3 < var1.length; ++var3) {
         var2[var3] = (byte)(var1[var3] ^ 9182 + var3 & 255);
      }

      return new String(var2);
   }
}
