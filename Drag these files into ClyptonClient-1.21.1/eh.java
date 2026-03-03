import client.astralux.mixin.HandledScreenMixin;
import java.util.Base64;
import net.minecraft.class_1713;
import net.minecraft.class_1723;
import net.minecraft.class_1735;
import net.minecraft.class_1802;
import net.minecraft.class_437;
import net.minecraft.class_490;

public final class eh extends bf {
   private final gn tickDelay = (new gn(db.of(d970("e1lSWRNwUFpWQQ==")), 0.0D, 20.0D, 0.0D, 1.0D)).getValue(db.of(d970("e1lSWUAUQVkXT1hTTxxfW0s3JCctZCo2IjooPiIjIz0=")));
   private final ff hotbarTotem = (new ff(db.of(d970("Z19FUFJGFWJYTFxX")), true)).setDescription(db.of(d970("blxCXRNEWVdUXUoaWhxJUUslLGIqKmU/KD07ajs+KCgqIiM3N3Q9OSM6OCh7LzExKw==")));
   private final gn hotbarSlot = (new gn(db.of(d970("Z19FUFJGFWVbV00=")), 1.0D, 9.0D, 1.0D, 1.0D)).getValue(db.of(d970("dl9EQBNER1NRXUtIXlgdVlA0IyMxZDYqKDxpLCQ+bTogJDQ/c3xke25x")));
   private final ff autoSwitchToTotem = (new ff(db.of(d970("bkVFXRNnQl9DW1Eab1MdalA0JC8=")), false)).setDescription(db.of(d970("bkVFXV5VQV9UWVVWQhxOSVY0IiomN2UyKGg9JT8pIG48PD4mcyM9Mzl4MDQtOTMqMBIYQgoXRQkXDQcPDw==")));
   private int remainingDelay;

   public eh() {
      super(db.of(d970("Z19HV0EUYVlDXVQ=")), db.of(d970("akFEW0NHFVcXTFZOXlEdV1FgLiQlLCQoI2goJC9sIj47OT48Mjg5L3cwNi45PS9+KAgEDEMMChACGgAEDEwCGAoCUR0dEVUfGVgQFA0ZEwoQ8vg=")), -1, hk.COMBAT);
      this.addSettings(new ab[]{this.tickDelay, this.hotbarTotem, this.hotbarSlot, this.autoSwitchToTotem});
   }

   public void onEnable() {
      this.resetDelay();
      super.onEnable();
   }

   @cp
   public void onTick(hm event) {
      if (mc.field_1724 != null) {
         class_437 currentScreen = mc.field_1755;
         if (!(mc.field_1755 instanceof class_490)) {
            this.resetDelay();
         } else {
            class_1735 focusedSlot = ((HandledScreenMixin)currentScreen).getFocusedSlot();
            if (focusedSlot != null && focusedSlot.method_34266() <= (113 ^ 82)) {
               if (this.autoSwitchToTotem.getValue()) {
                  mc.field_1724.method_31548().field_7545 = this.hotbarSlot.getIntValue() - 1;
               }

               if (focusedSlot.method_7677().method_7909() == class_1802.field_8288) {
                  if (this.remainingDelay > 0) {
                     --this.remainingDelay;
                  } else {
                     int index = focusedSlot.method_34266();
                     int syncId = ((class_1723)((class_490)currentScreen).method_17577()).field_7763;
                     if (!mc.field_1724.method_6079().method_31574(class_1802.field_8288)) {
                        this.equipOffhandTotem(syncId, index);
                     } else {
                        if (this.hotbarTotem.getValue()) {
                           int n = this.hotbarSlot.getIntValue() - 1;
                           if (!mc.field_1724.method_31548().method_5438(n).method_31574(class_1802.field_8288)) {
                              this.equipHotbarTotem(syncId, index, n);
                           }
                        }

                     }
                  }
               }
            }
         }
      }
   }

   private void equipOffhandTotem(int n, int n2) {
      mc.field_1761.method_2906(n, n2, 40, class_1713.field_7791, mc.field_1724);
      this.resetDelay();
   }

   private void equipHotbarTotem(int n, int n2, int n3) {
      mc.field_1761.method_2906(n, n2, n3, class_1713.field_7791, mc.field_1724);
      this.resetDelay();
   }

   private void resetDelay() {
      this.remainingDelay = this.tickDelay.getIntValue();
   }

   // $FF: synthetic method
   private static String d970(String var0) {
      byte[] var1 = Base64.getDecoder().decode(var0);
      byte[] var2 = new byte[var1.length];

      for(int var3 = 0; var3 < var1.length; ++var3) {
         var2[var3] = (byte)(var1[var3] ^ 4655 + var3 & 255);
      }

      return new String(var2);
   }
}
