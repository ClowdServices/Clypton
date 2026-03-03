import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.class_1703;
import net.minecraft.class_1713;
import net.minecraft.class_1792;
import net.minecraft.class_1802;
import net.minecraft.class_2960;
import net.minecraft.class_7923;
import net.minecraft.class_8881;
import net.minecraft.class_8898;

public final class ih extends bf {
   private final fp activateKey = (new fp(db.of(d531("89DA3MDWzNya8NnE")), 71, false)).setDescription(db.of(d531("+dbNlcLf2c2a2szN0talsuK3rKDmpLqorL+pv+6/saWmtqa7")));
   private final ep openConfigButton = (new ep(db.of(d531("/cPR25b019fc0ts=")), () -> {
      if (mc != null && mc.field_1755 == null) {
         mc.method_1507(new gy(this));
      }

   })).setDescription(db.of(d531("/cPR25b2zc3V+M7c2Muls+Kgq6ugrq+8uKq4pKGh")));
   private final boolean[] slotPattern = new boolean[9];
   private final class_1792[] recipe = new class_1792[9];
   private boolean hasActivated = false;
   private int clickTimer = 0;
   private int currentSlot = -1;
   private boolean isApplying = false;
   private static final String CONFIG_FILE = "autocrafter_pattern.json";
   private final Gson gson = (new GsonBuilder()).setPrettyPrinting().create();

   public ih() {
      super(db.of(d531("88bA2pb0ytjcz9nP")), db.of(d531("88bA2tvWzNDZ2tDRx5+jrqylraKzta266qi+rKi7taPyoLi6oqQ=")), -1, hk.MISC);

      int i;
      for(i = 0; i < 9; ++i) {
         this.slotPattern[i] = true;
      }

      for(i = 0; i < 9; ++i) {
         this.recipe[i] = class_1802.field_8162;
      }

      this.loadPattern();
      this.addSettings(new ab[]{this.activateKey, this.openConfigButton});
   }

   public void onEnable() {
      super.onEnable();
      this.hasActivated = false;
      this.isApplying = false;
      this.currentSlot = -1;
   }

   public void onDisable() {
      super.onDisable();
      this.hasActivated = false;
      this.isApplying = false;
      this.currentSlot = -1;
   }

   @cp
   public void onTick(hm startTickEvent) {
      try {
         if (mc == null || mc.field_1724 == null) {
            return;
         }

         if (this.isApplying) {
            if (this.clickTimer > 0) {
               --this.clickTimer;
            } else {
               class_1703 var3 = mc.field_1724.field_7512;
               if (!(var3 instanceof class_8881)) {
                  this.isApplying = false;
                  this.currentSlot = -1;
               } else {
                  class_8881 handler = (class_8881)var3;
                  boolean found = false;

                  while(this.currentSlot < 8) {
                     ++this.currentSlot;
                     boolean targetState = this.slotPattern[this.currentSlot];
                     boolean currentState = handler.method_54461(this.currentSlot);
                     if (targetState == currentState) {
                        this.clickCrafterSlot(handler, this.currentSlot);
                        this.clickTimer = 1;
                        found = true;
                        break;
                     }
                  }

                  if (!found) {
                     this.isApplying = false;
                     this.currentSlot = -1;
                  }
               }
            }
         }

         if (dd.isKeyPressed(this.activateKey.getValue()) && mc.field_1755 instanceof class_8898 && !this.hasActivated) {
            this.isApplying = true;
            this.currentSlot = -1;
            this.hasActivated = true;
            return;
         }

         if (!dd.isKeyPressed(this.activateKey.getValue())) {
            this.hasActivated = false;
         }

         if (!(mc.field_1755 instanceof class_8898)) {
            this.hasActivated = false;
            this.isApplying = false;
            this.currentSlot = -1;
         }
      } catch (Exception var6) {
      }

   }

   private void clickCrafterSlot(class_8881 handler, int slot) {
      if (mc.field_1761 != null && mc.field_1724 != null) {
         try {
            mc.field_1761.method_2906(handler.field_7763, slot, 0, class_1713.field_7790, mc.field_1724);
         } catch (Exception var4) {
            System.err.println("Error clicking crafter slot: " + var4.getMessage());
         }

      }
   }

   public boolean[] getSlotPattern() {
      return this.slotPattern;
   }

   public void toggleSlot(int slot) {
      if (slot >= 0 && slot < (56 ^ 49)) {
         this.slotPattern[slot] = !this.slotPattern[slot];
         this.savePattern();
      }

   }

   public boolean isSlotEnabled(int slot) {
      return slot >= 0 && slot < (172 ^ 165) ? this.slotPattern[slot] : true;
   }

   public void setSlotEnabled(int slot, boolean enabled) {
      if (slot >= 0 && slot < 9) {
         this.slotPattern[slot] = enabled;
         this.savePattern();
      }

   }

   public void clearPattern() {
      for(int i = 0; i < 9; ++i) {
         this.slotPattern[i] = true;
      }

      this.savePattern();
   }

   private static String getConfigDir() {
      return FabricLoader.getInstance().getGameDir().resolve("config/ClyptonClient").toString();
   }

   private void loadPattern() {
      try {
         Path configPath = Paths.get(getConfigDir(), d531("08bA2tXF2d/O3s7izt60taexquustKen"));
         if (!Files.exists(configPath, new LinkOption[0])) {
            return;
         }

         String json = Files.readString(configPath);
         JsonObject jsonObject = JsonParser.parseString(json).getAsJsonObject();

         for(int i = 0; i < (250 ^ 243); ++i) {
            String slotKey = "slot_" + i;
            if (jsonObject.has(slotKey)) {
               this.slotPattern[i] = jsonObject.get(slotKey).getAsBoolean();
            }

            String itemKey = "item_" + i;
            if (jsonObject.has(itemKey)) {
               String itemId = jsonObject.get(itemKey).getAsString();
               this.recipe[i] = (class_1792)class_7923.field_41178.method_10223(class_2960.method_60654(itemId));
            }
         }
      } catch (Exception var8) {
         System.err.println("Failed to load AutoCrafter pattern: " + var8.getMessage());
      }

   }

   public void savePattern() {
      try {
         Path configDir = Paths.get(getConfigDir());
         if (!Files.exists(configDir, new LinkOption[0])) {
            Files.createDirectories(configDir);
         }

         JsonObject jsonObject = new JsonObject();

         for(int i = 0; i < 9; ++i) {
            jsonObject.addProperty("slot_" + i, this.slotPattern[i]);
            if (this.recipe[i] != null) {
               jsonObject.addProperty("item_" + i, class_7923.field_41178.method_10221(this.recipe[i]).toString());
            }
         }

         Path configPath = Paths.get(getConfigDir(), d531("08bA2tXF2d/O3s7izt60taexquustKen"));
         Files.writeString(configPath, this.gson.toJson((JsonElement)jsonObject), new OpenOption[0]);
      } catch (Exception var4) {
         System.err.println("Failed to save AutoCrafter pattern: " + var4.getMessage());
      }

   }

   public void setRecipeSlot(int slot, class_1792 item) {
      if (slot >= 0 && slot < (187 ^ 178)) {
         this.recipe[slot] = item;
         this.slotPattern[slot] = item != class_1802.field_8162;
         this.savePattern();
      }
   }

   public void clearRecipe() {
      for(int i = 0; i < 9; ++i) {
         this.recipe[i] = class_1802.field_8162;
         this.slotPattern[i] = false;
      }

      this.savePattern();
   }

   public void saveRecipe() {
      this.savePattern();
   }

   public class_1792 getRecipeSlot(int slot) {
      return slot >= 0 && slot < 9 ? this.recipe[slot] : class_1802.field_8162;
   }

   // $FF: synthetic method
   private static String d531(String var0) {
      byte[] var1 = Base64.getDecoder().decode(var0);
      byte[] var2 = new byte[var1.length];

      for(int var3 = 0; var3 < var1.length; ++var3) {
         var2[var3] = (byte)(var1[var3] ^ 2482 + var3 & 255);
      }

      return new String(var2);
   }
}
