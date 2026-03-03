import java.util.ArrayList;
import java.util.Base64;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import net.minecraft.class_1268;
import net.minecraft.class_1293;
import net.minecraft.class_1713;
import net.minecraft.class_1799;
import net.minecraft.class_1802;
import net.minecraft.class_1844;
import net.minecraft.class_3532;
import net.minecraft.class_6880;
import net.minecraft.class_9334;

public final class fc extends bf {
   private final fp healthKey = (new fp(db.of(d540("LAAHCxwBSiAJFA==")), 72, false)).setDescription(db.of(d540("MA0UCB9JAg4NARoHUAEdBw==")));
   private final fp speedKey = (new fp(db.of(d540("NxUDAgxJIQ4V")), 73, false)).setDescription(db.of(d540("MA0UCB9JGRsJCApPAB4G")));
   private final fp fireResKey = (new fp(db.of(d540("IgwUAkg7DxhMJgsW")), 74, false)).setDescription(db.of(d540("MA0UCB9JDAIeCE4dFQIbAAAUGBQdWQoUCA==")));
   private final fp strengthKey = (new fp(db.of(d540("NxEUAgYOHgNMJgsW")), 211 ^ 152, false)).setDescription(db.of(d540("MA0UCB9JGR8eCAAIBBlSAxsB")));
   private final fp regenKey = (new fp(db.of(d540("NgABAgZJIQ4V")), 76, false)).setDescription(db.of(d540("MA0UCB9JGA4LCABPAB4G")));
   private final fp multiPotKey = (new fp(db.of(d540("KRAKEwFJOgQYTSUKCQ==")), 77, false)).setDescription(db.of(d540("MA0UCB9JBx4AGQcfHBRSAxsBBQ==")));
   private final ff multiPot = new ff(db.of(d540("KRAKEwFJOgQY")), false);
   private final ff throwHealth = new ff(db.of(d540("LQsFCx0ND0skCA8DBBk=")), true);
   private final ff throwSpeed = new ff(db.of(d540("LQsFCx0ND0s/HQsKFA==")), false);
   private final ff throwFireRes = new ff(db.of(d540("LQsFCx0ND0sqBBwKUCMXAA==")), false);
   private final ff throwStrength = new ff(db.of(d540("LQsFCx0ND0s/GRwKHhYGGw==")), false);
   private final ff throwRegen = new ff(db.of(d540("LQsFCx0ND0s+CAkKHg==")), false);
   private final ff smartPot = new ff(db.of(d540("NwgHFRxJOgQY")), true);
   private final gn healthThreshold = new gn(db.of(d540("LAAHCxwBSk4=")), 0.0D, 100.0D, 50.0D, 5.0D);
   private final fr throwDelay = new fr(d540("MA0UCB9JLg4ADBc="), 0.0D, 500.0D, 1.0D, 30.0D, 70.0D);
   private final ff groundThrow = new ff(db.of(d540("IxcJEgYNSj8EHwEY")), true);
   private final gn rotationSpeed = new gn(db.of(d540("NgoSBhwABQVMPh4KFRU=")), 10.0D, 3600.0D, 800.0D, 10.0D);
   private final ff autoRefill = new ff(db.of(d540("JRASCEg7Dw0FAQI=")), true);
   private long lastThrowTime = 0L;
   private int potThrowIndex = 0;
   private boolean isThrowing = false;
   private boolean isRotating = false;
   private float targetPitch = 0.0F;
   private float serverPitch = 0.0F;
   private final Random random = new Random();
   private final List<String> multiPotQueue = new ArrayList();

   public fc() {
      super(db.of(d540("JRASCEg5BR8=")), db.of(d540("JRASCAUIHgIPDAIDCVEGGwYaAQRYCRUPFRIQDKD26/fspe3i8evj5ej+")), -1, hk.COMBAT);
      ab[] var10001 = new ab[207 ^ 221];
      var10001[0] = this.healthKey;
      var10001[1] = this.speedKey;
      var10001[2] = this.fireResKey;
      var10001[3] = this.strengthKey;
      var10001[4] = this.regenKey;
      var10001[5] = this.multiPotKey;
      var10001[6] = this.multiPot;
      var10001[7] = this.throwHealth;
      var10001[8] = this.throwSpeed;
      var10001[7 ^ 14] = this.throwFireRes;
      var10001[56 ^ 50] = this.throwStrength;
      var10001[11] = this.throwRegen;
      var10001[12] = this.smartPot;
      var10001[13] = this.healthThreshold;
      var10001[14] = this.throwDelay;
      var10001[15] = this.groundThrow;
      var10001[16] = this.rotationSpeed;
      var10001[17] = this.autoRefill;
      this.addSettings(var10001);
   }

   public void onEnable() {
      super.onEnable();
      this.resetState();
   }

   public void onDisable() {
      super.onDisable();
      this.resetState();
   }

   @cp
   public void onTick(hm event) {
      if (mc.field_1755 == null && mc.field_1724 != null) {
         if (System.currentTimeMillis() >= this.lastThrowTime) {
            if (this.smartPot.getValue()) {
               float healthPercent = mc.field_1724.method_6032() / mc.field_1724.method_6063() * 100.0F;
               if ((double)healthPercent <= this.healthThreshold.getValue()) {
                  this.throwPotionByName(d540("DQsVEwkHHjQECA8DBBk="));
                  return;
               }

               if (mc.field_1724.method_5809()) {
                  this.throwPotionByName(d540("AgwUAjcbDxgFHhoOHhIX"));
                  return;
               }
            }

            if (this.multiPot.getValue() && dd.isKeyPressed(this.multiPotKey.getValue())) {
               if (!this.isThrowing) {
                  this.buildMultiPotQueue();
                  this.isThrowing = true;
                  this.potThrowIndex = 0;
               }

               this.handleMultiPot();
            } else {
               this.isThrowing = false;
               if (dd.isKeyPressed(this.healthKey.getValue())) {
                  this.throwPotionByName(d540("DQsVEwkHHjQECA8DBBk="));
               } else if (dd.isKeyPressed(this.speedKey.getValue())) {
                  this.throwPotionByName(d540("FxUDAgw="));
               } else if (dd.isKeyPressed(this.fireResKey.getValue())) {
                  this.throwPotionByName(d540("AgwUAjcbDxgFHhoOHhIX"));
               } else if (dd.isKeyPressed(this.strengthKey.getValue())) {
                  this.throwPotionByName(d540("FxEUAgYOHgM="));
               } else if (dd.isKeyPressed(this.regenKey.getValue())) {
                  this.throwPotionByName(d540("FgABAgYMGAoYBAEB"));
               }

               if (this.autoRefill.getValue()) {
                  this.refillPotions();
               }

            }
         }
      }
   }

   @cp
   public void onMouseUpdate(bk event) {
      if (this.isRotating && mc.field_1724 != null) {
         float pitchDiff = class_3532.method_15393(this.serverPitch - mc.field_1724.method_36455());
         if (Math.abs(pitchDiff) < 1.0F) {
            this.isRotating = false;
         } else {
            event.setDeltaY(event.getDefaultDeltaY() + (double)((int)pitchDiff));
         }
      }
   }

   private void buildMultiPotQueue() {
      this.multiPotQueue.clear();
      if (this.throwHealth.getValue()) {
         this.multiPotQueue.add(d540("DQsVEwkHHjQECA8DBBk="));
      }

      if (this.throwSpeed.getValue()) {
         this.multiPotQueue.add(d540("FxUDAgw="));
      }

      if (this.throwFireRes.getValue()) {
         this.multiPotQueue.add(d540("AgwUAjcbDxgFHhoOHhIX"));
      }

      if (this.throwStrength.getValue()) {
         this.multiPotQueue.add(d540("FxEUAgYOHgM="));
      }

      if (this.throwRegen.getValue()) {
         this.multiPotQueue.add(d540("FgABAgYMGAoYBAEB"));
      }

   }

   private void handleMultiPot() {
      if (this.potThrowIndex >= this.multiPotQueue.size()) {
         this.isThrowing = false;
         this.potThrowIndex = 0;
      } else {
         String effectName = (String)this.multiPotQueue.get(this.potThrowIndex);
         if (this.throwPotionByName(effectName)) {
            ++this.potThrowIndex;
         }

      }
   }

   private boolean throwPotionByName(String effectName) {
      int potSlot = this.findPotionSlot(effectName);
      if (potSlot == -1) {
         return false;
      } else {
         if (this.groundThrow.getValue()) {
            this.targetPitch = 90.0F;
            this.rotateToPitch();
            if (Math.abs(mc.field_1724.method_36455() - this.targetPitch) > 2.0F) {
               return false;
            }
         }

         int previousSlot = mc.field_1724.method_31548().field_7545;
         gp.swap(potSlot);
         mc.field_1761.method_2919(mc.field_1724, class_1268.field_5808);
         mc.field_1724.method_6104(class_1268.field_5808);
         gp.swap(previousSlot);
         this.lastThrowTime = System.currentTimeMillis() + this.getRandomDelay();
         return true;
      }
   }

   private void rotateToPitch() {
      if (mc.field_1724 != null) {
         this.isRotating = true;
         float currentPitch = mc.field_1724.method_36455();
         float diff = class_3532.method_15393(this.targetPitch - currentPitch);
         float maxChange = this.rotationSpeed.getFloatValue() / 20.0F;
         this.serverPitch = currentPitch + class_3532.method_15363(diff, -maxChange, maxChange);
      }
   }

   private int findPotionSlot(String effectName) {
      for(int i = 0; i < 9; ++i) {
         class_1799 stack = mc.field_1724.method_31548().method_5438(i);
         if (stack.method_31574(class_1802.field_8436)) {
            class_1844 potionContents = (class_1844)stack.method_57824(class_9334.field_49651);
            if (potionContents != null) {
               Iterator var5 = potionContents.method_57397().iterator();

               while(var5.hasNext()) {
                  class_1293 effect = (class_1293)var5.next();
                  class_6880<?> effectEntry = effect.method_5579();
                  if (effectEntry.method_55840().contains(effectName)) {
                     return i;
                  }
               }
            }
         }
      }

      return -1;
   }

   private void refillPotions() {
      for(int i = 234 ^ 227; i < mc.field_1724.method_31548().method_5439(); ++i) {
         class_1799 stack = mc.field_1724.method_31548().method_5438(i);
         if (stack.method_31574(class_1802.field_8436)) {
            for(int j = 0; j < (227 ^ 234); ++j) {
               if (mc.field_1724.method_31548().method_5438(j).method_7960()) {
                  mc.field_1761.method_2906(mc.field_1724.field_7512.field_7763, i, j, class_1713.field_7791, mc.field_1724);
                  return;
               }
            }
         }
      }

   }

   private long getRandomDelay() {
      return (long)(this.throwDelay.getCurrentMin() + this.random.nextDouble() * (this.throwDelay.getCurrentMax() - this.throwDelay.getCurrentMin()));
   }

   private void resetState() {
      this.lastThrowTime = 0L;
      this.potThrowIndex = 0;
      this.isThrowing = false;
      this.isRotating = false;
      this.multiPotQueue.clear();
   }

   // $FF: synthetic method
   private static String d540(String var0) {
      byte[] var1 = Base64.getDecoder().decode(var0);
      byte[] var2 = new byte[var1.length];

      for(int var3 = 0; var3 < var1.length; ++var3) {
         var2[var3] = (byte)(var1[var3] ^ 9828 + var3 & (110 ^ 145));
      }

      return new String(var2);
   }
}
