import java.util.Base64;
import net.minecraft.class_1268;
import net.minecraft.class_1297;
import net.minecraft.class_1511;
import net.minecraft.class_1621;
import net.minecraft.class_1802;
import net.minecraft.class_2246;
import net.minecraft.class_2338;
import net.minecraft.class_238;
import net.minecraft.class_239;
import net.minecraft.class_3965;
import net.minecraft.class_3966;
import net.minecraft.class_239.class_240;

public final class x extends bf {
   private final fp activateKey = (new fp(db.of(d93("8NHH3cPXw92Z8d7F")), 1, false)).setDescription(db.of(d93("+tfKlMHe1syZ3tTZzp7LqKTioLa8tbOppaaioqo=")));
   private final gn placeDelay = new gn(db.of(d93("4d7S19CW893V28I=")), 0.0D, 20.0D, 0.0D, 1.0D);
   private final gn breakDelay = new gn(db.of(d93("88DW1d6W893V28I=")), 0.0D, 20.0D, 0.0D, 1.0D);
   private final gn crystalSpeed = new gn(db.of(d93("8sDKx8HX25jqyt7Z2Q==")), 1.0D, 5.0D, 1.0D, 0.1D);
   private int placeDelayCounter;
   private int breakDelayCounter;
   public boolean isActive;

   public x() {
      super(db.of(d93("8MfH25X1xcHKztrQ")), db.of(d93("8MfH29jXw9Ha29fQxJ7csrixt6WpteeuqLm/7KuhvfCovaY=")), -1, hk.COMBAT);
      this.addSettings(new ab[]{this.activateKey, this.placeDelay, this.breakDelay, this.crystalSpeed});
   }

   public void onEnable() {
      this.resetCounters();
      this.isActive = false;
      super.onEnable();
   }

   private void resetCounters() {
      this.placeDelayCounter = 0;
      this.breakDelayCounter = 0;
   }

   @cp
   public void onTick(hm startTickEvent) {
      if (mc.field_1755 == null) {
         this.updateCounters();
         if (!mc.field_1724.method_6115()) {
            if (this.isKeyActive()) {
               if (mc.field_1724.method_6047().method_7909() == class_1802.field_8301) {
                  this.handleInteraction();
               }
            }
         }
      }
   }

   private void updateCounters() {
      if (this.placeDelayCounter > 0) {
         --this.placeDelayCounter;
      }

      if (this.breakDelayCounter > 0) {
         --this.breakDelayCounter;
      }

   }

   private boolean isKeyActive() {
      int d = this.activateKey.getValue();
      if (d != -1 && !dd.isKeyPressed(d)) {
         this.resetCounters();
         return this.isActive = false;
      } else {
         return this.isActive = true;
      }
   }

   private void handleInteraction() {
      class_239 crosshairTarget = mc.field_1765;
      if (mc.field_1765 instanceof class_3965) {
         this.handleBlockInteraction((class_3965)crosshairTarget);
      } else {
         class_239 var3 = mc.field_1765;
         if (var3 instanceof class_3966) {
            class_3966 entityHitResult = (class_3966)var3;
            this.handleEntityInteraction(entityHitResult);
         }
      }

   }

   private void handleBlockInteraction(class_3965 blockHitResult) {
      if (blockHitResult.method_17783() == class_240.field_1332) {
         if (this.placeDelayCounter <= 0) {
            class_2338 blockPos = blockHitResult.method_17777();
            if ((ft.isBlockAtPosition(blockPos, class_2246.field_10540) || ft.isBlockAtPosition(blockPos, class_2246.field_9987)) && this.isValidCrystalPlacement(blockPos)) {
               ft.interactWithBlock(blockHitResult, true);
               this.placeDelayCounter = (int)(this.placeDelay.getValue() / this.crystalSpeed.getValue());
            }

         }
      }
   }

   private void handleEntityInteraction(class_3966 entityHitResult) {
      if (this.breakDelayCounter <= 0) {
         class_1297 entity = entityHitResult.method_17782();
         if (entity instanceof class_1511 || entity instanceof class_1621) {
            mc.field_1761.method_2918(mc.field_1724, entity);
            mc.field_1724.method_6104(class_1268.field_5808);
            this.breakDelayCounter = (int)(this.breakDelay.getValue() / this.crystalSpeed.getValue());
         }
      }
   }

   @cp
   public void onPreItemUse(ec preItemUseEvent) {
      if (mc.field_1724.method_6047().method_7909() == class_1802.field_8301) {
         class_239 var3 = mc.field_1765;
         if (var3 instanceof class_3965) {
            class_3965 blockHitResult = (class_3965)var3;
            if (mc.field_1765.method_17783() == class_240.field_1332) {
               class_2338 blockPos = blockHitResult.method_17777();
               if (ft.isBlockAtPosition(blockPos, class_2246.field_10540) || ft.isBlockAtPosition(blockPos, class_2246.field_9987)) {
                  preItemUseEvent.cancel();
               }

            }
         }
      }
   }

   private boolean isValidCrystalPlacement(class_2338 blockPos) {
      class_2338 up = blockPos.method_10084();
      if (!mc.field_1687.method_22347(up)) {
         return false;
      } else {
         int getX = up.method_10263();
         int getY = up.method_10264();
         int compareTo = up.method_10260();
         return mc.field_1687.method_8335((class_1297)null, new class_238((double)getX, (double)getY, (double)compareTo, (double)getX + 1.0D, (double)getY + 2.0D, (double)compareTo + 1.0D)).isEmpty();
      }
   }

   // $FF: synthetic method
   private static String d93(String var0) {
      byte[] var1 = Base64.getDecoder().decode(var0);
      byte[] var2 = new byte[var1.length];

      for(int var3 = 0; var3 < var1.length; ++var3) {
         var2[var3] = (byte)(var1[var3] ^ 7601 + var3 & 255);
      }

      return new String(var2);
   }
}
