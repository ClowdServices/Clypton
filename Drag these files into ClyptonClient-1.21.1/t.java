import java.util.ArrayList;
import java.util.Base64;
import java.util.Iterator;
import java.util.List;
import net.minecraft.class_332;

public class t {
   private String name;
   private int x;
   private int y;
   private int width;
   private boolean open;
   private final List<e> buttons;
   private int dragX;
   private int dragY;
   private int scrollOffset;
   private final int panelIndex;
   // $FF: synthetic field
   final fe this$0;

   public t(final fe this$0, String name, int x, int y, int width, boolean open) {
      this.this$0 = this$0;
      this.buttons = new ArrayList();
      this.name = name;
      this.x = x;
      this.y = y;
      this.width = width;
      this.open = open;
      this.panelIndex = this$0.widgets.size();
   }

   public void addButton(e button) {
      this.buttons.add(button);
   }

   public void render(class_332 ctx, int mouseX, int mouseY, float delta) {
      int contentHeight = this.open ? this.buttons.stream().mapToInt(e::getHeight).sum() : 0;
      int headerHeight = 24;
      int totalHeight = headerHeight + (this.open ? contentHeight + (125 ^ 123) : 0);
      ctx.method_25294(this.x, this.y, this.x + this.width, this.y + totalHeight, ch.safeColor(253 ^ 233, 20, 28, 88 ^ 190).getRGB());
      boolean headerHovered = this.isHeaderHovered(mouseX, mouseY);
      ctx.method_25294(this.x, this.y, this.x + this.width, this.y + headerHeight, headerHovered ? ch.safeColor(42 ^ 2, 40, 55, 130 ^ 125).getRGB() : ch.safeColor(28, 28, 40, 255).getRGB());
      ctx.method_25294(this.x, this.y + headerHeight - 2, this.x + this.width, this.y + headerHeight, ch.safeColor(119, 0, 244 ^ 11, 255).getRGB());
      i.drawString(this.name, ctx, this.x + 6, this.y + 7, -1);
      String arrow = this.open ? "v" : ">";
      int arrowWidth = i.getWidth(arrow);
      i.drawString(arrow, ctx, this.x + this.width - arrowWidth - 6, this.y + 7, -4934456);
      if (this.open) {
         int yOff = this.y + headerHeight + 3 - this.scrollOffset;

         int btnHeight;
         for(Iterator var12 = this.buttons.iterator(); var12.hasNext(); yOff += btnHeight) {
            e button = (e)var12.next();
            btnHeight = button.getHeight();
            if (yOff >= this.y + headerHeight && yOff < this.y + totalHeight) {
               button.render(ctx, this.x + 6, yOff, this.width - (205 ^ 193), mouseX, mouseY);
            }
         }
      }

   }

   public boolean mouseClicked(int mouseX, int mouseY, int button) {
      if (this.isHeaderHovered(mouseX, mouseY)) {
         if (button == 0) {
            this.this$0.currentDragging = this;
            this.dragX = mouseX - this.x;
            this.dragY = mouseY - this.y;
         } else if (button == 1) {
            this.open = !this.open;
         }

         return true;
      } else {
         if (this.open && button == 0) {
            int headerHeight = 63 ^ 39;
            int yOff = this.y + headerHeight + 3 - this.scrollOffset;

            int btnHeight;
            for(Iterator var6 = this.buttons.iterator(); var6.hasNext(); yOff += btnHeight) {
               e btn = (e)var6.next();
               btnHeight = btn.getHeight();
               if (mouseX >= this.x + 6 && mouseX <= this.x + this.width - 6 && mouseY >= yOff && mouseY <= yOff + btnHeight) {
                  btn.onClick(mouseX - this.x - (231 ^ 225), mouseY - yOff);
                  return true;
               }
            }
         }

         return false;
      }
   }

   public void mouseReleased(int mouseX, int mouseY, int button) {
      this.buttons.forEach(e::onRelease);
   }

   public void onDrag(int mouseX, int mouseY) {
      this.x = mouseX - this.dragX;
      this.y = mouseY - this.dragY;
      this.x = Math.max(0, Math.min(this.this$0.field_22789 - this.width, this.x));
      this.y = Math.max(0, Math.min(this.this$0.field_22790 - 24, this.y));
      if (this.panelIndex == 0) {
         fe.controlPanelX = this.x;
         fe.controlPanelY = this.y;
      } else if (this.panelIndex == 1) {
         fe.stylePanelX = this.x;
         fe.stylePanelY = this.y;
      }

   }

   public void onScroll(double amount) {
      if (this.open) {
         this.scrollOffset = (int)Math.max(0.0D, (double)this.scrollOffset - amount * 20.0D);
      }

   }

   private boolean isHeaderHovered(int mouseX, int mouseY) {
      return mouseX >= this.x && mouseX <= this.x + this.width && mouseY >= this.y && mouseY <= this.y + 24;
   }

   public boolean isHovered(int mouseX, int mouseY) {
      int contentHeight = this.open ? this.buttons.stream().mapToInt(e::getHeight).sum() : 0;
      int totalHeight = (60 ^ 36) + (this.open ? contentHeight + (173 ^ 171) : 0);
      return mouseX >= this.x && mouseX <= this.x + this.width && mouseY >= this.y && mouseY <= this.y + totalHeight;
   }

   // $FF: synthetic method
   private static String d139(String var0) {
      byte[] var1 = Base64.getDecoder().decode(var0);
      byte[] var2 = new byte[var1.length];

      for(int var3 = 0; var3 < var1.length; ++var3) {
         var2[var3] = (byte)(var1[var3] ^ 6037 - 232 + var3 & (64 ^ 191));
      }

      return new String(var2);
   }
}
