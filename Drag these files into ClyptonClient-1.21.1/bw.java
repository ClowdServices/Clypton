import java.awt.Color;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.class_1792;
import net.minecraft.class_1799;
import net.minecraft.class_1802;
import net.minecraft.class_3675;
import net.minecraft.class_437;

public final class bw extends bf {
   public static bw INSTANCE;
   private final cu<az> mode;
   private final ff requireKey;
   private final fp previewKey;
   public static final Map<class_1792, Color> SHULKER_COLORS = new HashMap();
   private boolean wasKeyPressed = false;

   public bw() {
      super(d268("hLCstrC5r4qwj42WipQ="), d268("h6q8rLK5qv6siJSOiIGXxoWHkcqIg4Oaip6FgQ=="), -1, hk.MISC);
      INSTANCE = this;
      this.mode = new cu(d268("mre9vw=="), az.Screen, az.class);
      this.requireKey = new ff(d268("hb2or7KuuP6UhZg="), false);
      this.previewKey = (new fp(db.of(d268("h6q8rLK5qv6UhZg=")), 86, false)).setDescription(db.of(d268("nL2g+q+z/a6thZeLhpPFlY+dhYGOns2NgJ6Fl52Ahg==")));
      this.addSettings(new ab[]{this.mode, this.requireKey, this.previewKey});
   }

   @cp
   public void onTick(hm event) {
      if (this.isEnabled()) {
         if (this.isScreenMode()) {
            if (mc.field_1724 != null) {
               if (mc.field_1755 == null) {
                  int key = this.previewKey.getValue();
                  if (key != -1) {
                     boolean isKeyPressed = dd.isKeyPressed(key);
                     if (isKeyPressed && !this.wasKeyPressed) {
                        class_1799 heldItem = mc.field_1724.method_6047();
                        if (SHULKER_COLORS.containsKey(heldItem.method_7909())) {
                           mc.method_1507(new f(heldItem, (class_437)null));
                        }
                     }

                     this.wasKeyPressed = isKeyPressed;
                  }
               }
            }
         }
      }
   }

   public boolean isKeyPressed() {
      if (!this.requireKey.getValue()) {
         return true;
      } else {
         long handle = bf.mc.method_22683().method_4490();
         int key = this.previewKey.getValue();
         return key == -1 ? false : class_3675.method_15987(handle, key);
      }
   }

   public boolean isPreviewKeyPressed(int keyCode) {
      int previewKeyValue = this.previewKey.getValue();
      return previewKeyValue != -1 && keyCode == previewKeyValue;
   }

   public boolean isScreenMode() {
      return this.mode.getValue() == az.Screen;
   }

   public boolean isTooltipMode() {
      return this.mode.getValue() == az.Tooltip;
   }

   static {
      SHULKER_COLORS.put(class_1802.field_8545, new Color(142, 108, 142));
      SHULKER_COLORS.put(class_1802.field_8722, new Color(225, 230, 230));
      SHULKER_COLORS.put(class_1802.field_8451, new Color(175 ^ 38, 125 ^ 244, 128));
      SHULKER_COLORS.put(class_1802.field_8627, new Color(60, 65, 68));
      SHULKER_COLORS.put(class_1802.field_8268, new Color(63 ^ 32, 31, 21 ^ 54));
      SHULKER_COLORS.put(class_1802.field_8584, new Color(189 ^ 204, 70, 39));
      SHULKER_COLORS.put(class_1802.field_8676, new Color(152, 127 ^ 91, 34));
      SHULKER_COLORS.put(class_1802.field_8380, new Color(241, 76 ^ 62, 194 ^ 205));
      SHULKER_COLORS.put(class_1802.field_8271, new Color(249, 196, 35));
      SHULKER_COLORS.put(class_1802.field_8548, new Color(110, 30 ^ 167, 24));
      SHULKER_COLORS.put(class_1802.field_8461, new Color(13 ^ 94, 107, 29));
      SHULKER_COLORS.put(class_1802.field_8213, new Color(22, 133, 213 ^ 69));
      SHULKER_COLORS.put(class_1802.field_8829, new Color(175 ^ 150, 16 ^ 161, 142 ^ 89));
      SHULKER_COLORS.put(class_1802.field_8350, new Color(49, 99 ^ 87, 152));
      SHULKER_COLORS.put(class_1802.field_8816, new Color(113, 37, 48 ^ 150));
      SHULKER_COLORS.put(class_1802.field_8050, new Color(152 ^ 47, 61, 172));
      SHULKER_COLORS.put(class_1802.field_8520, new Color(237 ^ 2, 135, 55 ^ 145));
   }

   // $FF: synthetic method
   private static String d268(String var0) {
      byte[] var1 = Base64.getDecoder().decode(var0);
      byte[] var2 = new byte[var1.length];

      for(int var3 = 0; var3 < var1.length; ++var3) {
         var2[var3] = (byte)(var1[var3] ^ 7127 + var3 & 255);
      }

      return new String(var2);
   }
}
