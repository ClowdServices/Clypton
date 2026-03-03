import java.util.Base64;
import net.minecraft.class_1802;
import net.minecraft.class_1819;
import net.minecraft.class_2246;
import net.minecraft.class_239;
import net.minecraft.class_3965;
import net.minecraft.class_9334;
import org.lwjgl.glfw.GLFW;

public final class c extends bf {
   private final gn switchDelay = new gn(db.of(d968("Cy4zLz81fhsFDQMa")), 0.0D, 20.0D, 0.0D, 1.0D);
   private final gn glowstoneDelay = new gn(db.of(d968("HzU1LC8pMTEFQSYGCAQf")), 0.0D, 20.0D, 0.0D, 1.0D);
   private final gn explodeDelay = new gn(db.of(d968("HSEqNzM5O38kBA4CHQ==")), 0.0D, 20.0D, 0.0D, 1.0D);
   private final gn totemSlot = new gn(db.of(d968("DDYuPjF9DTMPFQ==")), 1.0D, 9.0D, 1.0D, 1.0D);
   private int keybind = 0;
   private int glowstoneDelayCounter = 0;
   private int explodeDelayCounter = 0;

   public c() {
      super(db.of(d968("GTc5MzMvfhIBAhAM")), db.of(d968("GSwuNDE8KjYDAA4PHUUECwceGUsZHU4dFQICEgMbVhYWGhIUDg5eGe/zovrr8A==")), -1, hk.COMBAT);
      this.addSettings(new ab[]{this.switchDelay, this.glowstoneDelay, this.explodeDelay, this.totemSlot});
   }

   public void onEnable() {
      this.resetCounters();
      super.onEnable();
   }

   public void onDisable() {
      super.onDisable();
   }

   @cp
   public void onTick(hm startTickEvent) {
      if (mc.field_1755 == null) {
         if (!this.isShieldOrFoodActive()) {
            if (dd.isKeyPressed(1)) {
               this.handleAnchorInteraction();
            }

         }
      }
   }

   private boolean isShieldOrFoodActive() {
      boolean isFood = mc.field_1724.method_6047().method_7909().method_57347().method_57832(class_9334.field_50075) || mc.field_1724.method_6079().method_7909().method_57347().method_57832(class_9334.field_50075);
      boolean isShield = mc.field_1724.method_6047().method_7909() instanceof class_1819 || mc.field_1724.method_6079().method_7909() instanceof class_1819;
      boolean isRightClickPressed = GLFW.glfwGetMouseButton(mc.method_22683().method_4490(), 1) == 1;
      return (isFood || isShield) && isRightClickPressed;
   }

   private void handleAnchorInteraction() {
      class_239 var2 = mc.field_1765;
      if (var2 instanceof class_3965) {
         class_3965 blockHitResult = (class_3965)var2;
         if (ft.isBlockAtPosition(blockHitResult.method_17777(), class_2246.field_23152)) {
            mc.field_1690.field_1904.method_23481(false);
            if (ft.isRespawnAnchorUncharged(blockHitResult.method_17777())) {
               this.placeGlowstone(blockHitResult);
            } else if (ft.isRespawnAnchorCharged(blockHitResult.method_17777())) {
               this.explodeAnchor(blockHitResult);
            }

         }
      }
   }

   private void placeGlowstone(class_3965 blockHitResult) {
      if (!mc.field_1724.method_6047().method_31574(class_1802.field_8801)) {
         if (this.keybind < this.switchDelay.getIntValue()) {
            ++this.keybind;
            return;
         }

         this.keybind = 0;
         gp.swap(class_1802.field_8801);
      }

      if (mc.field_1724.method_6047().method_31574(class_1802.field_8801)) {
         if (this.glowstoneDelayCounter < this.glowstoneDelay.getIntValue()) {
            ++this.glowstoneDelayCounter;
            return;
         }

         this.glowstoneDelayCounter = 0;
         ft.interactWithBlock(blockHitResult, true);
      }

   }

   private void explodeAnchor(class_3965 blockHitResult) {
      int selectedSlot = this.totemSlot.getIntValue() - 1;
      if (mc.field_1724.method_31548().field_7545 != selectedSlot) {
         if (this.keybind < this.switchDelay.getIntValue()) {
            ++this.keybind;
            return;
         }

         this.keybind = 0;
         mc.field_1724.method_31548().field_7545 = selectedSlot;
      }

      if (mc.field_1724.method_31548().field_7545 == selectedSlot) {
         if (this.explodeDelayCounter < this.explodeDelay.getIntValue()) {
            ++this.explodeDelayCounter;
            return;
         }

         this.explodeDelayCounter = 0;
         ft.interactWithBlock(blockHitResult, true);
      }

   }

   private void resetCounters() {
      this.keybind = 0;
      this.glowstoneDelayCounter = 0;
      this.explodeDelayCounter = 0;
   }

   // $FF: synthetic method
   private static String d968(String var0) {
      byte[] var1 = Base64.getDecoder().decode(var0);
      byte[] var2 = new byte[var1.length];

      for(int var3 = 0; var3 < var1.length; ++var3) {
         var2[var3] = (byte)(var1[var3] ^ 6488 + var3 & 255);
      }

      return new String(var2);
   }
}
