import client.astralux.Astralux;
import java.awt.Color;
import java.util.Base64;
import net.minecraft.class_239;
import net.minecraft.class_243;
import net.minecraft.class_3532;
import net.minecraft.class_3959;
import net.minecraft.class_3965;
import net.minecraft.class_408;
import net.minecraft.class_4184;
import net.minecraft.class_471;
import net.minecraft.class_498;
import net.minecraft.class_5498;
import net.minecraft.class_7744;
import net.minecraft.class_239.class_240;
import net.minecraft.class_3959.class_242;
import net.minecraft.class_3959.class_3960;
import org.joml.Vector3d;

public final class cj extends bf {
   private final gn speed = new gn(db.of(d856("dVdNTE4=")), 1.0D, 10.0D, 1.0D, 0.1D);
   private final gn acceleration = new gn(db.of(d856("Z0RLTEZOXkxaRl9f")), 0.1D, 1.0D, 0.3D, 0.05D);
   private final gn deceleration = new gn(db.of(d856("YkJLTEZOXkxaRl9f")), 0.1D, 1.0D, 0.5D, 0.05D);
   private final ff allowMining = (new ff(db.of(d856("Z0tERl0LYURARl5W")), false)).setDescription(db.of(d856("Z0tERl0LQURARl5WEkRcXFpSGFBUG1pPW1ojIC8=")));
   private final ff tracers = (new ff(db.of(d856("clVJSk9ZXw==")), false)).setDescription(db.of(d856("YlVJXlkLTQ1CRl5UEkdbFU9YTUsaSVlcUh8iLiY6")));
   public final Vector3d currentPosition = new Vector3d();
   public final Vector3d previousPosition = new Vector3d();
   private final Vector3d velocity = new Vector3d();
   private final Vector3d targetVelocity = new Vector3d();
   private class_5498 currentPerspective;
   private double movementSpeed;
   public float yaw;
   public float pitch;
   public float previousYaw;
   public float previousPitch;
   private boolean isMovingForward;
   private boolean isMovingBackward;
   private boolean isMovingRight;
   private boolean isMovingLeft;
   private boolean isMovingUp;
   private boolean isMovingDown;

   public cj() {
      super(db.of(d856("YFVNTElKQQ==")), db.of(d856("akJcWgpSQ1gOQl9HVxNSR1NSVEAaWk5SS1EkYTYrIWUxKDolLms7JDonPyQmczU2IiI5NTYifDAxKQkPBQ==")), -1, hk.MISC);
      this.addSettings(new ab[]{this.speed, this.acceleration, this.deceleration, this.allowMining, this.tracers});
   }

   public void onEnable() {
      if (mc.field_1724 == null) {
         this.toggle();
      } else {
         mc.field_1690.method_42454().method_41748(0.0D);
         mc.field_1690.method_42448().method_41748(false);
         this.yaw = mc.field_1724.method_36454();
         this.pitch = mc.field_1724.method_36455();
         this.currentPerspective = mc.field_1690.method_31044();
         this.movementSpeed = this.speed.getValue();
         bi.copyVector(this.currentPosition, mc.field_1773.method_19418().method_19326());
         bi.copyVector(this.previousPosition, mc.field_1773.method_19418().method_19326());
         this.velocity.set(0.0D, 0.0D, 0.0D);
         this.targetVelocity.set(0.0D, 0.0D, 0.0D);
         if (mc.field_1690.method_31044() == class_5498.field_26666) {
            this.yaw += 180.0F;
            this.pitch *= -1.0F;
         }

         this.previousYaw = this.yaw;
         this.previousPitch = this.pitch;
         this.isMovingForward = mc.field_1690.field_1894.method_1434();
         this.isMovingBackward = mc.field_1690.field_1881.method_1434();
         this.isMovingRight = mc.field_1690.field_1849.method_1434();
         this.isMovingLeft = mc.field_1690.field_1913.method_1434();
         this.isMovingUp = mc.field_1690.field_1903.method_1434();
         this.isMovingDown = mc.field_1690.field_1832.method_1434();
         this.resetMovementKeys();
         super.onEnable();
      }
   }

   public void onDisable() {
      this.resetMovementKeys();
      this.previousPosition.set(this.currentPosition);
      this.previousYaw = this.yaw;
      this.previousPitch = this.pitch;
      this.velocity.set(0.0D, 0.0D, 0.0D);
      this.targetVelocity.set(0.0D, 0.0D, 0.0D);
      super.onDisable();
   }

   private void resetMovementKeys() {
      mc.field_1690.field_1894.method_23481(false);
      mc.field_1690.field_1881.method_23481(false);
      mc.field_1690.field_1849.method_23481(false);
      mc.field_1690.field_1913.method_23481(false);
      mc.field_1690.field_1903.method_23481(false);
      mc.field_1690.field_1832.method_23481(false);
   }

   @cp
   private void handleSetScreenEvent(ao setScreenEvent) {
      this.resetMovementKeys();
      this.previousPosition.set(this.currentPosition);
      this.previousYaw = this.yaw;
      this.previousPitch = this.pitch;
   }

   @cp
   private void handleTickEvent(hm startTickEvent) {
      if (Astralux.mc.field_1724 != null && !(Astralux.mc.field_1755 instanceof class_408) && !(Astralux.mc.field_1755 instanceof class_498) && !(Astralux.mc.field_1755 instanceof class_7744) && !(Astralux.mc.field_1755 instanceof class_471)) {
         if (mc.field_1719.method_5757()) {
            mc.method_1560().field_5960 = true;
         }

         if (!this.currentPerspective.method_31034()) {
            mc.field_1690.method_31043(class_5498.field_26664);
         }

         this.previousPosition.set(this.currentPosition);
         this.previousYaw = this.yaw;
         this.previousPitch = this.pitch;
         class_243 forwardVector = class_243.method_1030(0.0F, this.yaw);
         class_243 rightVector = class_243.method_1030(0.0F, this.yaw + 90.0F);
         double targetX = 0.0D;
         double targetY = 0.0D;
         double targetZ = 0.0D;
         double speedMultiplier = 0.5D;
         if (mc.field_1690.field_1867.method_1434()) {
            speedMultiplier = 1.0D;
         }

         boolean isMovingHorizontally = false;
         if (this.isMovingForward) {
            targetX += forwardVector.field_1352 * speedMultiplier * this.movementSpeed;
            targetZ += forwardVector.field_1350 * speedMultiplier * this.movementSpeed;
            isMovingHorizontally = true;
         }

         if (this.isMovingBackward) {
            targetX -= forwardVector.field_1352 * speedMultiplier * this.movementSpeed;
            targetZ -= forwardVector.field_1350 * speedMultiplier * this.movementSpeed;
            isMovingHorizontally = true;
         }

         boolean isMovingLaterally = false;
         if (this.isMovingRight) {
            targetX += rightVector.field_1352 * speedMultiplier * this.movementSpeed;
            targetZ += rightVector.field_1350 * speedMultiplier * this.movementSpeed;
            isMovingLaterally = true;
         }

         if (this.isMovingLeft) {
            targetX -= rightVector.field_1352 * speedMultiplier * this.movementSpeed;
            targetZ -= rightVector.field_1350 * speedMultiplier * this.movementSpeed;
            isMovingLaterally = true;
         }

         double accel;
         if (isMovingHorizontally && isMovingLaterally) {
            accel = 1.0D / Math.sqrt(2.0D);
            targetX *= accel;
            targetZ *= accel;
         }

         if (this.isMovingUp) {
            targetY += speedMultiplier * this.movementSpeed;
         }

         if (this.isMovingDown) {
            targetY -= speedMultiplier * this.movementSpeed;
         }

         this.targetVelocity.set(targetX, targetY, targetZ);
         accel = this.acceleration.getValue();
         double decel = this.deceleration.getValue();
         boolean stopping = targetX == 0.0D && targetY == 0.0D && targetZ == 0.0D;
         double factor = stopping ? decel : accel;
         this.velocity.x = class_3532.method_16436(factor, this.velocity.x, this.targetVelocity.x);
         this.velocity.y = class_3532.method_16436(factor, this.velocity.y, this.targetVelocity.y);
         this.velocity.z = class_3532.method_16436(factor, this.velocity.z, this.targetVelocity.z);
         if (Math.abs(this.velocity.x) < 0.001D) {
            this.velocity.x = 0.0D;
         }

         if (Math.abs(this.velocity.y) < 0.001D) {
            this.velocity.y = 0.0D;
         }

         if (Math.abs(this.velocity.z) < 0.001D) {
            this.velocity.z = 0.0D;
         }

         this.currentPosition.set(this.currentPosition.x + this.velocity.x, this.currentPosition.y + this.velocity.y, this.currentPosition.z + this.velocity.z);
         this.updateFreecamCrosshair();
      }
   }

   private void updateFreecamCrosshair() {
      if (mc.field_1687 != null && mc.field_1724 != null) {
         class_243 freecamPos = new class_243(this.currentPosition.x, this.currentPosition.y + (double)mc.field_1724.method_5751(), this.currentPosition.z);
         class_243 direction = class_243.method_1030(this.pitch, this.yaw);
         class_243 endPos = freecamPos.method_1019(direction.method_1021(128.0D));
         class_3959 context = new class_3959(freecamPos, endPos, class_3960.field_17559, class_242.field_1348, mc.field_1724);
         mc.field_1765 = mc.field_1687.method_17742(context);
      }
   }

   @cp
   public void onKey(hc keyEvent) {
      if (Astralux.mc.field_1724 != null && !(Astralux.mc.field_1755 instanceof class_408) && !(Astralux.mc.field_1755 instanceof class_498) && !(Astralux.mc.field_1755 instanceof class_7744) && !(Astralux.mc.field_1755 instanceof class_471)) {
         if (!dd.isKeyPressed(292)) {
            boolean keyHandled = true;
            if (mc.field_1690.field_1894.method_1417(keyEvent.key, 0)) {
               this.isMovingForward = keyEvent.mode != 0;
               mc.field_1690.field_1894.method_23481(false);
            } else if (mc.field_1690.field_1881.method_1417(keyEvent.key, 0)) {
               this.isMovingBackward = keyEvent.mode != 0;
               mc.field_1690.field_1881.method_23481(false);
            } else if (mc.field_1690.field_1849.method_1417(keyEvent.key, 0)) {
               this.isMovingRight = keyEvent.mode != 0;
               mc.field_1690.field_1849.method_23481(false);
            } else if (mc.field_1690.field_1913.method_1417(keyEvent.key, 0)) {
               this.isMovingLeft = keyEvent.mode != 0;
               mc.field_1690.field_1913.method_23481(false);
            } else if (mc.field_1690.field_1903.method_1417(keyEvent.key, 0)) {
               this.isMovingUp = keyEvent.mode != 0;
               mc.field_1690.field_1903.method_23481(false);
            } else if (mc.field_1690.field_1832.method_1417(keyEvent.key, 0)) {
               this.isMovingDown = keyEvent.mode != 0;
               mc.field_1690.field_1832.method_23481(false);
            } else {
               keyHandled = false;
            }

            if (keyHandled) {
               keyEvent.cancel();
            }

         }
      }
   }

   @cp
   private void handleMouseButtonEvent(de event) {
      if (this.allowMining.getValue() && (event.button == 0 || event.button == 1) && event.actions == 1 && mc.field_1724 != null && mc.field_1687 != null) {
         class_243 realPlayerEyePos = mc.field_1724.method_33571();
         class_243 realPlayerLookVec = mc.field_1724.method_5828(1.0F);
         class_243 realPlayerLookEnd = realPlayerEyePos.method_1019(realPlayerLookVec.method_1021(4.5D));
         class_3959 context = new class_3959(realPlayerEyePos, realPlayerLookEnd, class_3960.field_17559, class_242.field_1348, mc.field_1724);
         class_239 realPlayerTarget = mc.field_1687.method_17742(context);
         if (realPlayerTarget != null && realPlayerTarget.method_17783() == class_240.field_1332) {
            class_3965 blockHit = (class_3965)realPlayerTarget;
            double distance = realPlayerEyePos.method_1022(blockHit.method_17784());
            if (distance <= 4.5D) {
               return;
            }
         }
      }

      boolean buttonHandled = false;
      if (mc.field_1690.field_1894.method_1433(event.button)) {
         this.isMovingForward = event.actions != 0;
         mc.field_1690.field_1894.method_23481(false);
         buttonHandled = true;
      } else if (mc.field_1690.field_1881.method_1433(event.button)) {
         this.isMovingBackward = event.actions != 0;
         mc.field_1690.field_1881.method_23481(false);
         buttonHandled = true;
      } else if (mc.field_1690.field_1849.method_1433(event.button)) {
         this.isMovingRight = event.actions != 0;
         mc.field_1690.field_1849.method_23481(false);
         buttonHandled = true;
      } else if (mc.field_1690.field_1913.method_1433(event.button)) {
         this.isMovingLeft = event.actions != 0;
         mc.field_1690.field_1913.method_23481(false);
         buttonHandled = true;
      } else if (mc.field_1690.field_1903.method_1433(event.button)) {
         this.isMovingUp = event.actions != 0;
         mc.field_1690.field_1903.method_23481(false);
         buttonHandled = true;
      } else if (mc.field_1690.field_1832.method_1433(event.button)) {
         this.isMovingDown = event.actions != 0;
         mc.field_1690.field_1832.method_23481(false);
         buttonHandled = true;
      }

      if (buttonHandled) {
         event.cancel();
      }

   }

   @cp
   private void handleMouseScrolledEvent(cf mouseScrolledEvent) {
      if (mc.field_1755 == null) {
         this.movementSpeed += mouseScrolledEvent.amount * 0.25D * this.movementSpeed;
         if (this.movementSpeed < 0.1D) {
            this.movementSpeed = 0.1D;
         }

         mouseScrolledEvent.cancel();
      }

   }

   @cp
   private void handleChunkMarkClosedEvent(hq chunkMarkClosedEvent) {
      chunkMarkClosedEvent.cancel();
   }

   public void updateRotation(double deltaYaw, double deltaPitch) {
      this.previousYaw = this.yaw;
      this.previousPitch = this.pitch;
      this.yaw += (float)deltaYaw;
      this.pitch += (float)deltaPitch;
      this.pitch = class_3532.method_15363(this.pitch, -90.0F, 90.0F);
   }

   public double getInterpolatedX(float partialTicks) {
      return class_3532.method_16436((double)partialTicks, this.previousPosition.x, this.currentPosition.x);
   }

   public double getInterpolatedY(float partialTicks) {
      return class_3532.method_16436((double)partialTicks, this.previousPosition.y, this.currentPosition.y);
   }

   public double getInterpolatedZ(float partialTicks) {
      return class_3532.method_16436((double)partialTicks, this.previousPosition.z, this.currentPosition.z);
   }

   public double getInterpolatedYaw(float partialTicks) {
      return (double)class_3532.method_16439(partialTicks, this.previousYaw, this.yaw);
   }

   public double getInterpolatedPitch(float partialTicks) {
      return (double)class_3532.method_16439(partialTicks, this.previousPitch, this.pitch);
   }

   @cp
   private void onRender3D(bg event) {
      if (this.tracers.getValue() && mc.field_1724 != null) {
         class_4184 camera = mc.field_1773.method_19418();
         class_243 freecamPos = new class_243(this.getInterpolatedX(event.tickDelta), this.getInterpolatedY(event.tickDelta) + (double)mc.field_1724.method_5751(), this.getInterpolatedZ(event.tickDelta));
         class_243 playerPos = mc.field_1724.method_30950(event.tickDelta).method_1031(0.0D, (double)mc.field_1724.method_5751(), 0.0D);
         class_243 cameraPos = camera.method_19326();
         event.matrixStack.method_22903();
         event.matrixStack.method_22904(-cameraPos.field_1352, -cameraPos.field_1351, -cameraPos.field_1350);
         iq.renderLine(event.matrixStack, new Color(255, 255, 255, 219 ^ 36), freecamPos, playerPos);
         event.matrixStack.method_22909();
      }
   }

   // $FF: synthetic method
   private static String d856(String var0) {
      byte[] var1 = Base64.getDecoder().decode(var0);
      byte[] var2 = new byte[var1.length];

      for(int var3 = 0; var3 < var1.length; ++var3) {
         var2[var3] = (byte)(var1[var3] ^ 4390 + var3 & 255);
      }

      return new String(var2);
   }
}
