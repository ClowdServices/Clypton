import java.util.Base64;
import net.minecraft.class_243;
import net.minecraft.class_310;
import net.minecraft.class_746;

public final class hd {
   private boolean fakeRotation;
   private float serverYaw;
   private float serverPitch;
   private float realYaw;
   private float realPitch;

   @cp
   public void onPreMotion(ge event) {
      if (this.fakeRotation) {
         class_746 player = class_310.method_1551().field_1724;
         if (player != null) {
            this.realYaw = player.method_36454();
            this.realPitch = player.method_36455();
            player.method_36456(this.serverYaw);
            player.method_36457(this.serverPitch);
         }

      }
   }

   @cp
   public void onPostMotion(fg event) {
      if (this.fakeRotation) {
         class_746 player = class_310.method_1551().field_1724;
         if (player != null) {
            player.method_36456(this.realYaw);
            player.method_36457(this.realPitch);
         }

         this.fakeRotation = false;
      }
   }

   public void faceVectorPacket(class_243 vec) {
      fh needed = hn.getNeededRotations(vec);
      class_746 player = class_310.method_1551().field_1724;
      if (player != null) {
         this.fakeRotation = true;
         this.serverYaw = hn.limitAngleChange(player.method_36454(), needed.yaw());
         this.serverPitch = needed.pitch();
      }

   }

   public void faceVectorClient(class_243 vec) {
      fh needed = hn.getNeededRotations(vec);
      class_746 player = class_310.method_1551().field_1724;
      if (player != null) {
         player.method_36456(hn.limitAngleChange(player.method_36454(), needed.yaw()));
         player.method_36457(needed.pitch());
      }

   }

   public void faceVectorClientIgnorePitch(class_243 vec) {
      fh needed = hn.getNeededRotations(vec);
      class_746 player = class_310.method_1551().field_1724;
      if (player != null) {
         player.method_36456(hn.limitAngleChange(player.method_36454(), needed.yaw()));
         player.method_36457(0.0F);
      }

   }

   public float getServerYaw() {
      class_746 player = class_310.method_1551().field_1724;
      return this.fakeRotation ? this.serverYaw : (player != null ? player.method_36454() : 0.0F);
   }

   public float getServerPitch() {
      class_746 player = class_310.method_1551().field_1724;
      return this.fakeRotation ? this.serverPitch : (player != null ? player.method_36455() : 0.0F);
   }

   // $FF: synthetic method
   private static String d511(String var0) {
      byte[] var1 = Base64.getDecoder().decode(var0);
      byte[] var2 = new byte[var1.length];

      for(int var3 = 0; var3 < var1.length; ++var3) {
         var2[var3] = (byte)(var1[var3] ^ 11451 - 493 + var3 & 255);
      }

      return new String(var2);
   }
}
