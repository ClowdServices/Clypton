import java.util.Base64;
import net.minecraft.class_243;
import net.minecraft.class_310;
import net.minecraft.class_3532;
import net.minecraft.class_2828.class_2831;
import org.joml.Quaternionf;

public record fh(float yaw, float pitch) {
   private static final class_310 MC = class_310.method_1551();

   public fh(float yaw, float pitch) {
      this.yaw = yaw;
      this.pitch = pitch;
   }

   public void applyToClientPlayer() {
      float adjustedYaw = hn.limitAngleChange(MC.field_1724.method_36454(), this.yaw);
      MC.field_1724.method_36456(adjustedYaw);
      MC.field_1724.method_36457(this.pitch);
   }

   public void sendPlayerLookPacket() {
      this.sendPlayerLookPacket(MC.field_1724.method_24828());
   }

   public void sendPlayerLookPacket(boolean onGround) {
      MC.field_1724.field_3944.method_52787(new class_2831(this.yaw, this.pitch, onGround));
   }

   public double getAngleTo(fh other) {
      float yaw1 = class_3532.method_15393(this.yaw);
      float yaw2 = class_3532.method_15393(other.yaw);
      float diffYaw = class_3532.method_15393(yaw1 - yaw2);
      float pitch1 = class_3532.method_15393(this.pitch);
      float pitch2 = class_3532.method_15393(other.pitch);
      float diffPitch = class_3532.method_15393(pitch1 - pitch2);
      return Math.sqrt((double)(diffYaw * diffYaw + diffPitch * diffPitch));
   }

   public fh withYaw(float yaw) {
      return new fh(yaw, this.pitch);
   }

   public fh withPitch(float pitch) {
      return new fh(this.yaw, pitch);
   }

   public class_243 toLookVec() {
      float radPerDeg = 0.017453292F;
      float pi = 3.1415927F;
      float adjustedYaw = -class_3532.method_15393(this.yaw) * radPerDeg - pi;
      float cosYaw = class_3532.method_15362(adjustedYaw);
      float sinYaw = class_3532.method_15374(adjustedYaw);
      float adjustedPitch = -class_3532.method_15393(this.pitch) * radPerDeg;
      float nCosPitch = -class_3532.method_15362(adjustedPitch);
      float sinPitch = class_3532.method_15374(adjustedPitch);
      return new class_243((double)(sinYaw * nCosPitch), (double)sinPitch, (double)(cosYaw * nCosPitch));
   }

   public Quaternionf toQuaternion() {
      float radPerDeg = 0.017453292F;
      float yawRad = -class_3532.method_15393(this.yaw) * radPerDeg;
      float pitchRad = class_3532.method_15393(this.pitch) * radPerDeg;
      float sinYaw = class_3532.method_15374(yawRad / 2.0F);
      float cosYaw = class_3532.method_15362(yawRad / 2.0F);
      float sinPitch = class_3532.method_15374(pitchRad / 2.0F);
      float cosPitch = class_3532.method_15362(pitchRad / 2.0F);
      float x = sinPitch * cosYaw;
      float y = cosPitch * sinYaw;
      float z = -sinPitch * sinYaw;
      float w = cosPitch * cosYaw;
      return new Quaternionf(x, y, z, w);
   }

   public static fh wrapped(float yaw, float pitch) {
      return new fh(class_3532.method_15393(yaw), class_3532.method_15393(pitch));
   }

   public float yaw() {
      return this.yaw;
   }

   public float pitch() {
      return this.pitch;
   }

   // $FF: synthetic method
   private static String d120(String var0) {
      byte[] var1 = Base64.getDecoder().decode(var0);
      byte[] var2 = new byte[var1.length];

      for(int var3 = 0; var3 < var1.length; ++var3) {
         var2[var3] = (byte)(var1[var3] ^ 6700 + var3 & 255);
      }

      return new String(var2);
   }
}
