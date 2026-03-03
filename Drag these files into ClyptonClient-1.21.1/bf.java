import client.astralux.Astralux;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import net.minecraft.class_310;

public abstract class bf implements Serializable {
   private final List<ab> settings = new ArrayList();
   protected final ho EVENT_BUS;
   protected static class_310 mc;
   private CharSequence name;
   private CharSequence description;
   private boolean enabled;
   private int keybind;
   private hk category;
   private final boolean i;
   private boolean visible = true;

   public bf(CharSequence name, CharSequence description, int keybind, hk category) {
      this.EVENT_BUS = Astralux.INSTANCE.getEventBus();
      mc = class_310.method_1551();
      this.i = false;
      this.name = name;
      this.description = description;
      this.enabled = false;
      this.keybind = keybind;
      this.category = category;
   }

   public void toggle() {
      this.setEnabled(!this.enabled);
   }

   public CharSequence getName() {
      return this.name;
   }

   public boolean isEnabled() {
      return this.enabled;
   }

   public CharSequence getDescription() {
      return this.description;
   }

   public int getKeybind() {
      return this.keybind;
   }

   public hk getCategory() {
      return this.category;
   }

   public void setCategory(hk category) {
      this.category = category;
   }

   public void setName(CharSequence name) {
      this.name = name;
   }

   public void setDescription(CharSequence description) {
      this.description = description;
   }

   public void setKeybind(int keybind) {
      this.keybind = keybind;
   }

   public boolean isVisible() {
      return this.visible;
   }

   public void setVisible(boolean visible) {
      this.visible = visible;
   }

   public List<ab> getSettings() {
      return this.settings;
   }

   public void onEnable() {
   }

   public void onDisable() {
   }

   public void addSetting(ab setting) {
      this.settings.add(setting);
   }

   public void addSettings(ab... a) {
      this.settings.addAll(Arrays.asList(a));
   }

   public void toggle(boolean enabled) {
      if (this.enabled != enabled) {
         this.enabled = enabled;
         if (enabled) {
            this.onEnable();
         } else {
            this.onDisable();
         }
      }

   }

   public void setEnabled(boolean enabled) {
      if (this.enabled != enabled) {
         this.enabled = enabled;
         if (enabled) {
            this.onEnable();
            this.EVENT_BUS.register(this);
         } else {
            this.EVENT_BUS.unregister(this);
            this.onDisable();
         }
      }

   }

   // $FF: synthetic method
   private static String d360(String var0) {
      byte[] var1 = Base64.getDecoder().decode(var0);
      byte[] var2 = new byte[var1.length];

      for(int var3 = 0; var3 < var1.length; ++var3) {
         var2[var3] = (byte)(var1[var3] ^ 9123 - 399 + var3 & (29 ^ 226));
      }

      return new String(var2);
   }
}
