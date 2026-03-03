import java.util.Base64;
import java.util.Iterator;
import net.minecraft.class_266;
import net.minecraft.class_269;
import net.minecraft.class_8646;

public final class dj extends bf {
   private class_266 lastHiddenObjective;

   public dj() {
      super(db.of(d537("1PT6+vPVw9fX")), db.of(d537("1PT6+tOB1svBhdXEx9vPycPM3MuQ0t3exNnTw93Vww==")), -1, hk.DONUT);
   }

   public void onEnable() {
      super.onEnable();
      cv.getInstance().register(this);
      if (mc.field_1687 != null) {
         this.hideScoreboard();
      }
   }

   public void onDisable() {
      super.onDisable();
      cv.getInstance().unregister(this);
      if (mc.field_1687 != null) {
         this.restoreScoreboard();
      }
   }

   @cp
   public void onStartTick(hm event) {
      if (this.isEnabled()) {
         if (mc.field_1687 != null) {
            this.hideScoreboard();
         }
      }
   }

   @cp
   public void onEndTick(fz event) {
      if (this.isEnabled()) {
         if (mc.field_1687 != null) {
            this.hideScoreboard();
         }
      }
   }

   @cp
   public void onPacketReceive(ak event) {
      if (this.isEnabled()) {
         if (mc.field_1687 != null) {
            this.hideScoreboard();
         }
      }
   }

   private void hideScoreboard() {
      if (mc.field_1687 != null) {
         class_269 scoreboard = mc.field_1687.method_8428();
         class_266 currentDisplayed = scoreboard.method_1189(class_8646.field_45157);
         if (currentDisplayed != null) {
            this.lastHiddenObjective = currentDisplayed;
            scoreboard.method_1158(class_8646.field_45157, (class_266)null);
         }

      }
   }

   private void restoreScoreboard() {
      if (mc.field_1687 != null) {
         class_269 scoreboard = mc.field_1687.method_8428();
         if (this.lastHiddenObjective != null && this.objectiveExists(scoreboard, this.lastHiddenObjective)) {
            scoreboard.method_1158(class_8646.field_45157, this.lastHiddenObjective);
         } else {
            Iterator var2 = scoreboard.method_1151().iterator();
            if (var2.hasNext()) {
               class_266 obj = (class_266)var2.next();
               scoreboard.method_1158(class_8646.field_45157, obj);
            }
         }

         this.lastHiddenObjective = null;
      }
   }

   private boolean objectiveExists(class_269 scoreboard, class_266 objective) {
      if (objective == null) {
         return false;
      } else {
         Iterator var3 = scoreboard.method_1151().iterator();

         class_266 obj;
         do {
            if (!var3.hasNext()) {
               return false;
            }

            obj = (class_266)var3.next();
         } while(obj != objective);

         return true;
      }
   }

   // $FF: synthetic method
   private static String d537(String var0) {
      byte[] var1 = Base64.getDecoder().decode(var0);
      byte[] var2 = new byte[var1.length];

      for(int var3 = 0; var3 < var1.length; ++var3) {
         var2[var3] = (byte)(var1[var3] ^ 7068 + var3 & 255);
      }

      return new String(var2);
   }
}
