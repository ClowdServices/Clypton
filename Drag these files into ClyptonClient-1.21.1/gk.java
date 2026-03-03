import java.util.Base64;
import java.util.Iterator;
import java.util.Random;
import net.minecraft.class_1268;
import net.minecraft.class_1297;
import net.minecraft.class_1657;
import net.minecraft.class_238;
import net.minecraft.class_243;
import net.minecraft.class_310;
import net.minecraft.class_3532;
import net.minecraft.class_3959;
import net.minecraft.class_3965;
import net.minecraft.class_465;
import net.minecraft.class_746;
import net.minecraft.class_239.class_240;
import net.minecraft.class_3959.class_242;
import net.minecraft.class_3959.class_3960;

public final class gk extends bf {
   private final gn range = new gn(db.of(d453("2Ori6us=")), 1.0D, 6.0D, 4.25D, 0.05D);
   private final gn rotationSpeed = new gn(db.of(d453("2OT47Prm//+ywOTw8/M=")), 10.0D, 3600.0D, 600.0D, 10.0D);
   private final gn fov = new gn(db.of(d453("zMTa")), 30.0D, 360.0D, 360.0D, 10.0D);
   private final fr delayRange = new fr(d453("zu7g7Pc="), 1.0D, 100.0D, 1.0D, 20.0D, 70.0D);
   private final ff targetInvis = new ff(d453("3ur+6uv7sNj85f3m"), false);
   private class_1297 target;
   private float serverYaw;
   private float serverPitch;
   private long lastAttackTime;
   private final Random random = new Random();

   public gk() {
      super(db.of(d453("weLg4e/64vDe9vP84g==")), db.of(d453("y//47O3k47Hi//Xs8+XrufT+/e++5s/U")), -1, hk.COMBAT);
      this.addSettings(new ab[]{this.range, this.rotationSpeed, this.fov, this.delayRange, this.targetInvis});
   }

   public void onEnable() {
      this.target = null;
      this.lastAttackTime = 0L;
      super.onEnable();
   }

   @cp
   public void onTick(hm event) {
      if (mc.field_1755 instanceof class_465) {
         this.target = null;
      } else {
         this.target = null;
         double closestDistance = Double.MAX_VALUE;
         double rangeSq = this.range.getValue() * this.range.getValue();
         Iterator var6 = mc.field_1687.method_18112().iterator();

         while(true) {
            class_1297 entity;
            double distance;
            do {
               do {
                  do {
                     do {
                        do {
                           if (!var6.hasNext()) {
                              if (this.target != null) {
                                 class_238 box = this.target.method_5829();
                                 fh needed = hn.getNeededRotations(box.method_1005());
                                 fh next = hn.slowlyTurnTowards(needed, this.rotationSpeed.getFloatValue() / 20.0F);
                                 this.serverYaw = next.yaw();
                                 this.serverPitch = next.pitch();
                                 if (this.canAttack() && (hn.isAlreadyFacing(needed) || hn.isFacingBox(box, this.range.getValue()))) {
                                    mc.field_1761.method_2918(mc.field_1724, this.target);
                                    mc.field_1724.method_6104(class_1268.field_5808);
                                    this.lastAttackTime = System.currentTimeMillis() + this.getRandomDelay();
                                 }
                              }

                              return;
                           }

                           entity = (class_1297)var6.next();
                        } while(!(entity instanceof class_1657));
                     } while(entity == mc.field_1724);
                  } while(!this.targetInvis.getValue() && ((class_1657)entity).method_5767());

                  distance = mc.field_1724.method_5858(entity);
               } while(distance > rangeSq);
            } while(this.fov.getValue() < 360.0D && (double)this.getAngleToEntity(entity) > this.fov.getValue() / 2.0D);

            if (hasLineOfSight(entity.method_5829().method_1005()) && distance < closestDistance) {
               closestDistance = distance;
               this.target = entity;
            }
         }
      }
   }

   private boolean canAttack() {
      if (this.target == null) {
         return false;
      } else if (mc.field_1724.method_7261(0.5F) < 0.9F) {
         return false;
      } else {
         return System.currentTimeMillis() >= this.lastAttackTime;
      }
   }

   @cp
   public void onMouseUpdate(bk event) {
      if (this.target != null && mc.field_1724 != null) {
         float yawDiff = class_3532.method_15393(this.serverYaw - mc.field_1724.method_36454());
         float pitchDiff = class_3532.method_15393(this.serverPitch - mc.field_1724.method_36455());
         if (!(Math.abs(yawDiff) < 1.0F) || !(Math.abs(pitchDiff) < 1.0F)) {
            event.setDeltaX(event.getDefaultDeltaX() + (double)((int)yawDiff));
            event.setDeltaY(event.getDefaultDeltaY() + (double)((int)pitchDiff));
         }
      }
   }

   long getRandomDelay() {
      return (long)(this.delayRange.getCurrentMin() + this.random.nextDouble() * (this.delayRange.getCurrentMax() - this.delayRange.getCurrentMin()));
   }

   private float getAngleToEntity(class_1297 entity) {
      class_238 box = entity.method_5829();
      double dx = box.method_1005().field_1352 - mc.field_1724.method_23317();
      double dz = box.method_1005().field_1350 - mc.field_1724.method_23321();
      float yaw = (float)(class_3532.method_15349(dz, dx) * 180.0D / 3.141592653589793D - 90.0D);
      return Math.abs(class_3532.method_15393(yaw - mc.field_1724.method_36454()));
   }

   public static boolean hasLineOfSight(class_243 to) {
      return raycast(getEyesPos(), to).method_17783() == class_240.field_1333;
   }

   public static class_243 getEyesPos() {
      class_746 player = class_310.method_1551().field_1724;
      float eyeHeight = player.method_18381(player.method_18376());
      return player.method_19538().method_1031(0.0D, (double)eyeHeight, 0.0D);
   }

   public static class_3965 raycast(class_243 from, class_243 to) {
      return raycast(from, to, class_242.field_1348);
   }

   public static class_3965 raycast(class_243 from, class_243 to, class_242 fluidHandling) {
      class_3959 context = new class_3959(from, to, class_3960.field_17558, fluidHandling, class_310.method_1551().field_1724);
      return class_310.method_1551().field_1687.method_17742(context);
   }

   // $FF: synthetic method
   private static String d453(String var0) {
      byte[] var1 = Base64.getDecoder().decode(var0);
      byte[] var2 = new byte[var1.length];

      for(int var3 = 0; var3 < var1.length; ++var3) {
         var2[var3] = (byte)(var1[var3] ^ 4494 - 772 + var3 & 255);
      }

      return new String(var2);
   }
}
