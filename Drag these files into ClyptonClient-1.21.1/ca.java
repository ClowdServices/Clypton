import java.util.Base64;
import net.minecraft.class_1747;
import net.minecraft.class_1792;
import net.minecraft.class_1799;
import net.minecraft.class_1802;
import net.minecraft.class_1811;
import net.minecraft.class_9334;

public class ca extends bf {
   private final ff onlyXP = new ff(d459("EA4NG0M8NQ=="), false);
   private final ff allowBlocks = new ff(d459("HQwOAQgX"), true);
   private final ff allowItems = new ff(d459("FhQEDxA="), true);
   private final gn useDelay = new gn(d459("GwUNAxo="), 0.0D, 10.0D, 0.0D, 1.0D);

   public ca() {
      super(db.of(d459("GQESFkM0CQcEDQ==")), db.of(d459("DBAADxBEEBUCSAgJHwUCAEE=")), -1, hk.MISC);
      this.addSettings(new ab[]{this.onlyXP, this.allowBlocks, this.allowItems, this.useDelay});
   }

   public void onEnable() {
      super.onEnable();
   }

   public void onDisable() {
      super.onDisable();
   }

   @cp
   public void onPostItemUse(cw postItemUseEvent) {
      class_1799 getMainHandStack = mc.field_1724.method_6047();
      class_1799 getItemUseTime = mc.field_1724.method_6079();
      class_1792 item = getMainHandStack.method_7909();
      class_1792 item2 = mc.field_1724.method_6079().method_7909();
      if (getMainHandStack.method_31574(class_1802.field_8287) || getItemUseTime.method_31574(class_1802.field_8287) || !this.onlyXP.getValue()) {
         if (!this.onlyXP.getValue()) {
            if (!(item instanceof class_1747) && !(item2 instanceof class_1747)) {
               if (!this.allowItems.getValue()) {
                  return;
               }
            } else if (!this.allowBlocks.getValue()) {
               return;
            }
         }

         if (item.method_57347().method_57829(class_9334.field_50075) == null) {
            if (item2.method_57347().method_57829(class_9334.field_50075) == null) {
               if (!getMainHandStack.method_31574(class_1802.field_23141) && !getMainHandStack.method_31574(class_1802.field_8801) && !getItemUseTime.method_31574(class_1802.field_23141) && !getItemUseTime.method_31574(class_1802.field_8801)) {
                  if (!(item instanceof class_1811) && !(item2 instanceof class_1811)) {
                     postItemUseEvent.cooldown = this.useDelay.getIntValue();
                  }
               }
            }
         }
      }
   }

   // $FF: synthetic method
   private static String d459(String var0) {
      byte[] var1 = Base64.getDecoder().decode(var0);
      byte[] var2 = new byte[var1.length];

      for(int var3 = 0; var3 < var1.length; ++var3) {
         var2[var3] = (byte)(var1[var3] ^ 9854 - 543 + var3 & (124 ^ 131));
      }

      return new String(var2);
   }
}
