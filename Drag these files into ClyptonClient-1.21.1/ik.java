import java.util.Base64;
import net.minecraft.class_634;

public final class ik extends bf {
   private final fr delayRange = new fr(db.of(d649("g62lq7I=")), 1.0D, 80.0D, 1.0D, 10.0D, 30.0D);
   private final cu<ha> mode;
   private final bu playerName;
   private int delayCounter;

   public ik() {
      super(db.of(d649("hr29peuYva8=")), db.of(d649("iqetv6ep7bqnsaXyu7G5pqT4oLWu/Km7s4WRjZGQxZWTmoyLhomfnc+EntKKm4A=")), -1, hk.MISC);
      this.mode = new cu(db.of(d649("iqetrw==")), ha.TPAHERE, ha.class);
      this.playerName = new bu(db.of(d649("l6Sos66+")), d649("g7qNpaW5ubo="));
      this.addSettings(new ab[]{this.mode, this.delayRange, this.playerName});
   }

   public void onEnable() {
      super.onEnable();
   }

   public void onDisable() {
      super.onDisable();
   }

   @cp
   public void onTick(hm event) {
      if (this.delayCounter > 0) {
         --this.delayCounter;
      } else {
         class_634 networkHandler = mc.method_1562();
         String commandPrefix;
         if (((ha)this.mode.getValue()).equals(ha.TPA)) {
            commandPrefix = d649("s7io6g==");
         } else {
            commandPrefix = d649("s7iooq6+qO4=");
         }

         networkHandler.method_45731(commandPrefix + this.playerName.getValue());
         this.delayCounter = this.delayRange.getRandomIntInRange();
      }
   }

   // $FF: synthetic method
   private static String d649(String var0) {
      byte[] var1 = Base64.getDecoder().decode(var0);
      byte[] var2 = new byte[var1.length];

      for(int var3 = 0; var3 < var1.length; ++var3) {
         var2[var3] = (byte)(var1[var3] ^ 6087 + var3 & 255);
      }

      return new String(var2);
   }
}
