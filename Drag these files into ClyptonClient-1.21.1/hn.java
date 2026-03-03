import java.util.Base64;
import net.minecraft.class_238;
import net.minecraft.class_243;
import net.minecraft.class_310;
import net.minecraft.class_3532;
import net.minecraft.class_746;

public enum hn {
   private static final class_310 mc = class_310.method_1551();

   public static class_243 getEyesPos() {
      class_746 player = mc.field_1724;
      if (player == null) {
         return class_243.field_1353;
      } else {
         float eyeHeight = player.method_18381(player.method_18376());
         return player.method_19538().method_1031(0.0D, (double)eyeHeight, 0.0D);
      }
   }

   public static class_243 getClientLookVec(float partialTicks) {
      class_746 player = mc.field_1724;
      if (player == null) {
         return class_243.field_1353;
      } else {
         float yaw = player.method_5705(partialTicks);
         float pitch = player.method_5695(partialTicks);
         return (new fh(yaw, pitch)).toLookVec();
      }
   }

   public static class_243 getServerLookVec() {
      hd rf = new hd();
      return (new fh(rf.getServerYaw(), rf.getServerPitch())).toLookVec();
   }

   public static fh getNeededRotations(class_243 vec) {
      class_243 eyes = getEyesPos();
      double diffX = vec.field_1352 - eyes.field_1352;
      double diffZ = vec.field_1350 - eyes.field_1350;
      double yaw = Math.toDegrees(Math.atan2(diffZ, diffX)) - 90.0D;
      double diffY = vec.field_1351 - eyes.field_1351;
      double diffXZ = Math.sqrt(diffX * diffX + diffZ * diffZ);
      double pitch = -Math.toDegrees(Math.atan2(diffY, diffXZ));
      return fh.wrapped((float)yaw, (float)pitch);
   }

   public static double getAngleToLookVec(class_243 vec) {
      class_746 player = mc.field_1724;
      if (player == null) {
         return 0.0D;
      } else {
         fh current = new fh(player.method_36454(), player.method_36455());
         fh needed = getNeededRotations(vec);
         return current.getAngleTo(needed);
      }
   }

   public static float getHorizontalAngleToLookVec(class_243 vec) {
      class_746 player = mc.field_1724;
      if (player == null) {
         return 0.0F;
      } else {
         float currentYaw = class_3532.method_15393(player.method_36454());
         float neededYaw = getNeededRotations(vec).yaw();
         return class_3532.method_15393(currentYaw - neededYaw);
      }
   }

   public static boolean isAlreadyFacing(fh rotation) {
      return getAngleToLastReportedLookVec(rotation) <= 1.0D;
   }

   public static double getAngleToLastReportedLookVec(class_243 vec) {
      fh needed = getNeededRotations(vec);
      return getAngleToLastReportedLookVec(needed);
   }

   public static double getAngleToLastReportedLookVec(fh rotation) {
      class_746 player = mc.field_1724;
      if (player == null) {
         return 0.0D;
      } else {
         fh lastReported = player.method_5765() ? new fh(player.method_36454(), player.method_36455()) : new fh(player.field_3931, player.field_3914);
         return lastReported.getAngleTo(rotation);
      }
   }

   public static boolean isFacingBox(class_238 box, double range) {
      class_243 start = getEyesPos();
      class_243 end = start.method_1019(getServerLookVec().method_1021(range));
      return box.method_992(start, end).isPresent();
   }

   public static fh slowlyTurnTowards(fh end, float maxChange) {
      class_746 player = mc.field_1724;
      if (player == null) {
         return end;
      } else {
         float currentYaw = player.method_36454();
         float currentPitch = player.method_36455();
         float yaw = limitAngleChange(currentYaw, end.yaw(), maxChange);
         float pitch = limitAngleChange(currentPitch, end.pitch(), maxChange);
         return new fh(yaw, pitch);
      }
   }

   public static float limitAngleChange(float current, float intended, float maxChange) {
      float diff = limitAngleChange(current, intended);
      return Math.abs(diff) <= maxChange ? intended : current + Math.signum(diff) * maxChange;
   }

   public static float limitAngleChange(float current, float intended) {
      float diff = intended - current;
      if (diff > 180.0F) {
         return intended - 360.0F;
      } else {
         return diff < -180.0F ? intended + 360.0F : intended;
      }
   }

   // $FF: synthetic method
   private static hn[] $values() {
      return new hn[0];
   }

   // $FF: synthetic method
   private static String d322(String var0) {
      byte[] var1 = Base64.getDecoder().decode(var0);
      byte[] var2 = new byte[var1.length];

      for(int var3 = 0; var3 < var1.length; ++var3) {
         var2[var3] = (byte)(var1[var3] ^ 6191 - 498 + var3 & 255);
      }

      return new String(var2);
   }
}
