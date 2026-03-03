import java.util.Base64;
import net.minecraft.class_1268;
import net.minecraft.class_1269;
import net.minecraft.class_1802;

public final class ia extends bf {
   private final fp activateKey = new fp(db.of(d236("fF1LKTcjNyFlDSIx")), -1, false);
   private final gn throwDelay = new gn(db.of(d236("eVtTITg=")), 0.0D, 20.0D, 0.0D, 1.0D);
   private final ff switchBack = new ff(db.of(d236("bklWNCIqYwYkJSw=")), true);
   private final gn switchBackDelay = (new gn(db.of(d236("bklWNCIqYwAgKiYx")), 0.0D, 20.0D, 0.0D, 1.0D)).getValue(db.of(d236("eVtTIThiIiIxIzVoPSI5IzonITdxIjY1Jzp3Ojw8NC44fiwXCBYADAwIAEgLCwgH")));
   private boolean isActivated;
   private boolean hasThrown;
   private int currentThrowDelay;
   private int previousSlot;
   private int currentSwitchBackDelay;

   public ia() {
      super(db.of(d236("dltGYBEnIjYp")), db.of(d236("bklWNCIqJjdlMihoKCRrKSMqKiJxIjY1Jzp3OTc+eyg1LDAXEkIKEEURDw0HShIDGE4fAhQBAFQUVhURFx4=")), -1, hk.MISC);
      this.addSettings(new ab[]{this.activateKey, this.throwDelay, this.switchBack, this.switchBackDelay});
   }

   public void onEnable() {
      this.resetState();
      super.onEnable();
   }

   public void onDisable() {
      super.onDisable();
   }

   @cp
   public void onTick(hm event) {
      if (mc.field_1755 == null) {
         if (dd.isKeyPressed(this.activateKey.getValue())) {
            this.isActivated = true;
         }

         if (this.isActivated) {
            if (this.previousSlot == -1) {
               this.previousSlot = mc.field_1724.method_31548().field_7545;
            }

            gp.swap(class_1802.field_8634);
            if (this.currentThrowDelay < this.throwDelay.getIntValue()) {
               ++this.currentThrowDelay;
               return;
            }

            if (!this.hasThrown) {
               class_1269 interactItem = mc.field_1761.method_2919(mc.field_1724, class_1268.field_5808);
               if (interactItem.method_23665() && interactItem.method_23666()) {
                  mc.field_1724.method_6104(class_1268.field_5808);
               }

               this.hasThrown = true;
            }

            if (this.switchBack.getValue()) {
               this.handleSwitchBack();
            } else {
               this.resetState();
            }
         }

      }
   }

   private void handleSwitchBack() {
      if (this.currentSwitchBackDelay < this.switchBackDelay.getIntValue()) {
         ++this.currentSwitchBackDelay;
      } else {
         gp.swap(this.previousSlot);
         this.resetState();
      }
   }

   private void resetState() {
      this.previousSlot = -1;
      this.currentThrowDelay = 0;
      this.currentSwitchBackDelay = 0;
      this.isActivated = false;
      this.hasThrown = false;
   }

   // $FF: synthetic method
   private static String d236(String var0) {
      byte[] var1 = Base64.getDecoder().decode(var0);
      byte[] var2 = new byte[var1.length];

      for(int var3 = 0; var3 < var1.length; ++var3) {
         var2[var3] = (byte)(var1[var3] ^ 2877 + var3 & 255);
      }

      return new String(var2);
   }
}
