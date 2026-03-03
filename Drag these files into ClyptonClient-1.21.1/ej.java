import java.util.Base64;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import net.minecraft.class_1703;
import net.minecraft.class_1707;
import net.minecraft.class_1792;
import net.minecraft.class_1799;
import net.minecraft.class_2561;
import net.minecraft.class_3417;
import net.minecraft.class_746;

public final class ej extends bf {
   private final ay targetItems = new ay(db.of(d276("Wm5idndnNFxicnVq")), new HashSet());
   private final ff playSound = (new ff(db.of(d276("XmNxaDJAe2B4cw==")), true)).setDescription(db.of(d276("XmNxaDJge2B4czhucn5yPXdrRUxRA0VXQwdORl9FSA==")));
   private final ff spamChat = (new ff(db.of(d276("XX9xfDJQfHRi")), true)).setDescription(db.of(d276("XX9xfDJwfHRiN29xf3U8dGp6TVICQlZABkFHXERP")));
   private final gn chatSpamAmount = new gn(db.of(d276("TWdxZTJAZHR7N1l0dW5yaQ==")), 1.0D, 20.0D, 5.0D, 1.0D);
   private final ff onlyFullStacks;
   private boolean hasScannedCurrentChest;
   private class_1703 lastScannedHandler;

   public ej() {
      super(db.of(d276("TWd1YmYzXWFzejhfc3V4eGw=")), db.of(d276("T2N1Y2ZgNGJ+cnY5dWt5c3dxRwFBS0FWUlQISkVFWExHQVlfVRNHRVNUUV9TXlgdV0slLDE=")), -1, hk.MISC);
      this.chatSpamAmount.setDescription(db.of(d276("RmBnMX9yemw2Y3F0f2g8aXE/U1FDTgRGTkZc")));
      this.onlyFullStacks = (new ff(db.of(d276("QWF8aDJVYXl6N0tte3h3bg==")), false)).setDescription(db.of(d276("QWF8aDJyeHBkYzh/dWk8e2tzTAFRV0VGTVQ=")));
      this.hasScannedCurrentChest = false;
      this.lastScannedHandler = null;
      this.addSettings(new ab[]{this.targetItems, this.playSound, this.spamChat, this.chatSpamAmount, this.onlyFullStacks});
   }

   public void onEnable() {
      super.onEnable();
      this.hasScannedCurrentChest = false;
      this.lastScannedHandler = null;
      if (mc.field_1724 != null && this.targetItems.getItems().isEmpty()) {
         mc.field_1724.method_7353(class_2561.method_30163("§c[Chest Finder] §fNo items selected! Right-click the module to add items."), false);
      }

   }

   public void onDisable() {
      super.onDisable();
      this.hasScannedCurrentChest = false;
      this.lastScannedHandler = null;
   }

   @cp
   public void onTick(hm event) {
      if (mc.field_1724 != null) {
         class_1703 currentHandler = mc.field_1724.field_7512;
         if (currentHandler instanceof class_1707) {
            class_1707 chestHandler = (class_1707)currentHandler;
            if (currentHandler != this.lastScannedHandler) {
               this.lastScannedHandler = currentHandler;
               this.hasScannedCurrentChest = false;
            }

            if (!this.hasScannedCurrentChest) {
               this.hasScannedCurrentChest = true;
               this.scanChest(chestHandler);
            }
         } else if (this.lastScannedHandler != null) {
            this.lastScannedHandler = null;
            this.hasScannedCurrentChest = false;
         }

      }
   }

   private void scanChest(class_1707 handler) {
      if (!this.targetItems.getItems().isEmpty()) {
         Set<class_1792> foundItems = new HashSet();
         int totalSlots = handler.method_17388() * (114 ^ 123);

         for(int i = 0; i < totalSlots; ++i) {
            class_1799 stack = handler.method_7611(i).method_7677();
            if (!stack.method_7960()) {
               class_1792 item = stack.method_7909();
               if (this.targetItems.getItems().contains(item)) {
                  if (this.onlyFullStacks.getValue()) {
                     if (stack.method_7947() == stack.method_7914()) {
                        foundItems.add(item);
                     }
                  } else {
                     foundItems.add(item);
                  }
               }
            }
         }

         if (!foundItems.isEmpty()) {
            this.alertFoundItems(foundItems);
         }

      }
   }

   private void alertFoundItems(Set<class_1792> foundItems) {
      class_746 player = mc.field_1724;
      if (player != null) {
         StringBuilder message = new StringBuilder("§a§l[CHEST FINDER] §r§aFound: §f");
         int count = 0;

         for(Iterator var5 = foundItems.iterator(); var5.hasNext(); ++count) {
            class_1792 item = (class_1792)var5.next();
            if (count > 0) {
               message.append("§7, §f");
            }

            message.append(item.method_7848().getString());
         }

         if (this.spamChat.getValue()) {
            int spamCount = (int)this.chatSpamAmount.getValue();

            for(int i = 0; i < spamCount; ++i) {
               player.method_7353(class_2561.method_30163(message.toString()), false);
            }
         }

         if (this.playSound.getValue()) {
            player.method_5783(class_3417.field_14627, 1.0F, 1.0F);
         }

      }
   }

   public ay getTargetItems() {
      return this.targetItems;
   }

   // $FF: synthetic method
   private static String d276(String var0) {
      byte[] var1 = Base64.getDecoder().decode(var0);
      byte[] var2 = new byte[var1.length];

      for(int var3 = 0; var3 < var1.length; ++var3) {
         var2[var3] = (byte)(var1[var3] ^ 2574 + var3 & 255);
      }

      return new String(var2);
   }
}
