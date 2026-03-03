import client.astralux.mixin.MinecraftClientAccessor;
import java.util.Base64;
import net.minecraft.class_4174;
import net.minecraft.class_9334;

public final class hz extends bf {
   private final gn healthThreshold = new gn(db.of(d33("RGhvY2R5Mkd8Z3NkcHZ2fw==")), 0.0D, 19.0D, 17.0D, 1.0D);
   private final gn hungerThreshold = new gn(db.of(d33("RHhgaHVjMkd8Z3NkcHZ2fw==")), 0.0D, 19.0D, 19.0D, 1.0D);
   public boolean isEa;
   private int selectedFoodSlot;
   private int previousSelectedSlot;

   public hz() {
      super(db.of(d33("TXh6YDBUc2c=")), db.of(" It detects whenever the hungerbar/health falls a certain threshold, selects food in your hotbar, and starts eating."), -1, hk.MISC);
      this.addSettings(new ab[]{this.healthThreshold, this.hungerThreshold});
   }

   public void onEnable() {
      super.onEnable();
   }

   public void onDisable() {
      super.onDisable();
   }

   @cp
   public void onTick(hm startTickEvent) {
      if (this.isEa) {
         if (this.shouldEat()) {
            if (mc.field_1724.method_31548().method_5438(this.selectedFoodSlot).method_57824(class_9334.field_50075) != null) {
               int k = this.findBestFoodSlot();
               if (k == -1) {
                  this.stopEating();
                  return;
               }

               this.selectSlot(k);
            }

            this.startEating();
         } else {
            this.stopEating();
         }
      } else if (this.shouldEat()) {
         this.selectedFoodSlot = this.findBestFoodSlot();
         if (this.selectedFoodSlot != -1) {
            this.saveCurrentSlot();
         }
      }

   }

   public boolean shouldEat() {
      boolean b = mc.field_1724.method_6032() <= (float)this.healthThreshold.getIntValue();
      boolean b2 = mc.field_1724.method_7344().method_7586() <= this.hungerThreshold.getIntValue();
      return this.findBestFoodSlot() != -1 && (b || b2);
   }

   private int findBestFoodSlot() {
      int n = -1;
      int n2 = -1;

      for(int i = 0; i < 9; ++i) {
         Object value = mc.field_1724.method_31548().method_5438(i).method_7909().method_57347().method_57829(class_9334.field_50075);
         if (value != null) {
            int nutrition = ((class_4174)value).comp_2491();
            if (nutrition > n2) {
               n = i;
               n2 = nutrition;
            }
         }
      }

      return n;
   }

   private void saveCurrentSlot() {
      this.previousSelectedSlot = mc.field_1724.method_31548().field_7545;
      this.startEating();
   }

   private void startEating() {
      this.selectSlot(this.selectedFoodSlot);
      this.setUseKeyPressed(true);
      if (!mc.field_1724.method_6115()) {
         ((MinecraftClientAccessor)mc).invokeDoItemUse();
      }

      this.isEa = true;
   }

   private void stopEating() {
      this.selectSlot(this.previousSelectedSlot);
      this.setUseKeyPressed(false);
      this.isEa = false;
   }

   private void setUseKeyPressed(boolean pressed) {
      mc.field_1690.field_1904.method_23481(pressed);
   }

   private void selectSlot(int f) {
      gp.swap(f);
      this.selectedFoodSlot = f;
   }

   // $FF: synthetic method
   private static String d33(String var0) {
      byte[] var1 = Base64.getDecoder().decode(var0);
      byte[] var2 = new byte[var1.length];

      for(int var3 = 0; var3 < var1.length; ++var3) {
         var2[var3] = (byte)(var1[var3] ^ 1036 + var3 & (105 ^ 150));
      }

      return new String(var2);
   }
}
