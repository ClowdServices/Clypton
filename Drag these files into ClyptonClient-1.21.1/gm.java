import client.astralux.Main;
import java.util.Base64;
import net.minecraft.class_310;
import net.minecraft.class_3944;
import net.minecraft.class_437;
import net.minecraft.class_490;

public final class gm extends bf {
   public static final gn redColor = new gn(db.of(d79("LBrk")), 0.0D, 255.0D, 120.0D, 1.0D);
   public static final gn greenColor = new gn(db.of(d79("OQ3l5Ow=")), 0.0D, 255.0D, 190.0D, 1.0D);
   public static final gn blueColor = new gn(db.of(d79("PBP15A==")), 0.0D, 255.0D, 255.0D, 1.0D);
   public static final gn windowAlpha = new gn(db.of(d79("KRbu5e30pMTq9+Do")), 0.0D, 255.0D, 170.0D, 1.0D);
   public static final ff enableBreathingEffect = (new ff(db.of(d79("PA3l4Pbr7evh")), false)).setDescription(db.of(d79("PRDs7vCj5vfj5vzh4+Xrrevp9vTx57S9+fn04Lrs9en2v9LAy83GytGHx8/Mgg==")));
   public static final ff enableRainbowEffect = (new ff(db.of(d79("LB7p7+Ds8w==")), false)).setDescription(db.of(d79("OxHh4+7m96XKwMrd26vh4urq")));
   public static final ff renderBackground = (new ff(db.of(d79("PB7j6uXx6/Do4w==")), true)).setDescription(db.of(d79("LBru5efx96Xy7+2p6Orv5un9/+T897T68Lfs8f+73/H3/MuB5dbN")));
   public static final ff useCustomFont = new ff(db.of(d79("PQrz9e3upMPp6fw=")), true);
   private final ff preventClose = (new ff(db.of(d79("Lg3l9+ft8KXF6+f67w==")), true)).setDescription(db.of(d79("OBDyofHm9vPj9fup/eL45a7p4vT36fG15vvt/vP1773q98HVgsfLy4HTiMXP34zUwdqQ3sLW2pXC392Z/e71")));
   public static final gn cornerRoundness = new gn(db.of(d79("LBD17+bt4fb1")), 1.0D, 10.0D, 5.0D, 1.0D);
   public static final cu<gu> animationMode;
   public static final ff enableMSAA;
   public boolean shouldPreventClose;

   public gm() {
      super(db.of(d79("Pwz08+Pv8f0=")), db.of(d79("LRr09evt4/am4ef7qv/k6K7s/Pj3/eA=")), 442 ^ 226, hk.CLIENT);
      ab[] var10001 = new ab[]{redColor, greenColor, blueColor, windowAlpha, renderBackground, this.preventClose, null, null, null};
      var10001[150 ^ 144] = cornerRoundness;
      var10001[7] = animationMode;
      var10001[8] = enableMSAA;
      this.addSettings(var10001);
   }

   public void onEnable() {
      class_310 mc = class_310.method_1551();
      if (mc == null) {
         System.err.println(d79("Mxbu5OHx5ePyp+vl4+7i+a7m47H8/OC1//nx7fP68PTk+sSA"));
         this.toggle(false);
      } else {
         try {
            if (Main.getAstralux().GUI == null) {
               Main.getAstralux().GUI = new gf();
            }

            if (!(mc.field_1755 instanceof gf)) {
               Main.getAstralux().screen = mc.field_1755;
            }

            if (Main.getAstralux().GUI != null) {
               mc.method_1507(Main.getAstralux().GUI);
               if (!(mc.field_1755 instanceof gf)) {
                  System.err.println("[Clypton] Failed to open ClickGUI - screen didn't change!");
               }
            } else {
               System.err.println("[Clypton] GUI is null, cannot open!");
            }

            if (mc.field_1755 instanceof class_490) {
               this.shouldPreventClose = true;
            }
         } catch (Exception var3) {
            System.err.println("[Clypton] Error opening ClickGUI:");
            var3.printStackTrace();
            this.toggle(false);
         }

         super.onEnable();
      }
   }

   public void onDisable() {
      class_310 mc = class_310.method_1551();
      if (mc != null) {
         try {
            if (mc.field_1755 instanceof gf) {
               if (Main.getAstralux().GUI != null) {
                  Main.getAstralux().GUI.method_25419();
               }

               if (Main.getAstralux().screen != null) {
                  gm.mc.method_1507(Main.getAstralux().screen);
               } else {
                  gm.mc.method_1507((class_437)null);
               }
            } else if (gm.mc.field_1755 instanceof class_490) {
               this.shouldPreventClose = false;
            }
         } catch (Exception var3) {
            System.err.println("[Clypton] Error closing ClickGUI:");
            var3.printStackTrace();
         }

         super.onDisable();
      }
   }

   @cp
   public void onPacketReceive(ak packetReceiveEvent) {
      if (this.shouldPreventClose && packetReceiveEvent.packet instanceof class_3944 && this.preventClose.getValue()) {
         packetReceiveEvent.cancel();
      }

   }

   static {
      animationMode = new cu(db.of(d79("PxHp7OP37ero9A==")), gu.NORMAL, gu.class);
      enableMSAA = (new ff(db.of(d79("MyzBwA==")), true)).setDescription(db.of(d79("PxH06KLC6Ozn9OHn7avwrdrn+eKy8PX7tv716fv46L3u+tLHzdHJxMjEzYnDzYzUwdqXw9eTwcbf2d+Zzsnd3tvNs+GgtrDloa6+rLnruKWrovCw8qC5urmjsLyo+7CysbTAnQ==")));
   }

   // $FF: synthetic method
   private static String d79(String var0) {
      byte[] var1 = Base64.getDecoder().decode(var0);
      byte[] var2 = new byte[var1.length];

      for(int var3 = 0; var3 < var1.length; ++var3) {
         var2[var3] = (byte)(var1[var3] ^ 1662 + var3 & (175 ^ 80));
      }

      return new String(var2);
   }
}
