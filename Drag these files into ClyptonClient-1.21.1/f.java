import java.util.Base64;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import net.minecraft.class_1799;
import net.minecraft.class_2561;
import net.minecraft.class_2960;
import net.minecraft.class_332;
import net.minecraft.class_437;
import net.minecraft.class_9288;
import net.minecraft.class_9334;

public class f extends class_437 {
   private static final class_2960 TEXTURE = class_2960.method_60655(d570("ZmVja2xicHRn"), "textures/gui/container/shulker_box.png");
   private final int backgroundWidth = 115 ^ 195;
   private final int backgroundHeight = 58 ^ 156;
   private final class_2561 title;
   private final List<class_1799> inventory;
   private final class_437 parent;
   private int x = 0;
   private int y = 0;

   public f(class_1799 shulker, class_437 parent) {
      super(class_2561.method_43470(d570("WGR4YmR1YzJDZnBgfn1u")));
      this.title = shulker.method_7964();
      class_9288 container = (class_9288)shulker.method_57824(class_9334.field_49622);
      this.inventory = container != null ? container.method_57489().toList() : List.of();
      this.parent = parent;
   }

   protected void method_25426() {
      int var10001 = this.field_22789;
      Objects.requireNonNull(this);
      this.x = (var10001 - (167 ^ 23)) / 2;
      var10001 = this.field_22790;
      Objects.requireNonNull(this);
      this.y = (var10001 - 166) / 2;
   }

   public void method_25394(class_332 context, int mouseX, int mouseY, float delta) {
      super.method_25394(context, mouseX, mouseY, delta);
      this.method_25420(context, mouseX, mouseY, delta);
      this.renderItems(context, this.inventory, this.x + 8, this.y + (73 ^ 91));
      int selectedSlot = this.getSlot(mouseX, mouseY);
      if (selectedSlot >= 0 && selectedSlot < this.inventory.size()) {
         class_1799 stack = (class_1799)this.inventory.get(selectedSlot);
         if (!stack.method_7960()) {
            context.method_51446(this.field_22787.field_1772, stack, mouseX, mouseY);
         }
      }

   }

   public void method_25420(class_332 context, int mouseX, int mouseY, float delta) {
      super.method_25420(context, mouseX, mouseY, delta);
      class_2960 var10001 = TEXTURE;
      int var10002 = this.x;
      int var10003 = this.y;
      Objects.requireNonNull(this);
      Objects.requireNonNull(this);
      context.method_25302(var10001, var10002, var10003, 0, 0, 176, 166);
      context.method_51439(this.field_22787.field_1772, this.title, this.x + 8, this.y + (90 ^ 92), 4211598 - 846, false);
   }

   public void method_25419() {
      if (this.field_22787 != null) {
         this.field_22787.method_1507(this.parent);
      }

   }

   public boolean method_25421() {
      return false;
   }

   private void renderItems(class_332 context, List<class_1799> inventory, int x, int y) {
      int baseX = x;
      int count = 0;
      Iterator var7 = inventory.iterator();

      while(var7.hasNext()) {
         class_1799 item = (class_1799)var7.next();
         context.method_51427(item, x, y);
         context.method_51431(this.field_22787.field_1772, item, x, y);
         x += 18;
         ++count;
         if (count % 9 == 0) {
            x = baseX;
            y += 18;
         }
      }

   }

   private int getSlot(int mouseX, int mouseY) {
      int x = this.x + 8;
      int y = this.y + 18;
      int slotX = (mouseX - x) / 18;
      int slotY = (mouseY - y) / 18;
      return mouseX >= x && mouseY >= y && mouseX <= x + 162 && mouseY <= y + (61 ^ 11) ? slotX + slotY * (229 ^ 236) : -1;
   }

   // $FF: synthetic method
   private static String d570(String var0) {
      byte[] var1 = Base64.getDecoder().decode(var0);
      byte[] var2 = new byte[var1.length];

      for(int var3 = 0; var3 < var1.length; ++var3) {
         var2[var3] = (byte)(var1[var3] ^ 8459 + var3 & 255);
      }

      return new String(var2);
   }
}
