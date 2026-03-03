package client.astralux;

import java.io.File;
import net.minecraft.class_310;
import net.minecraft.class_437;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class Astralux {
   public static final Logger LOG = LoggerFactory.getLogger(Astralux.class);
   public static final class_310 mc = class_310.method_1551();
   public static Astralux INSTANCE;
   public static .dc configManager;
   public static .hd rotationFaker;
   public static .ff useCustomFont = new .ff("Use Custom Font", true);
   public static .ff renderBackground = new .ff("Render Background", true);
   public .hj MODULE_MANAGER;
   public .ho EVENT_BUS;
   public String version;
   public boolean shouldPreventClose;
   public .gf GUI;
   public class_437 screen;
   public long modified;
   public File jar;

   public Astralux() {
      INSTANCE = this;
      this.version = " b1.3";
      this.screen = null;
      LOG.info("Initializing Clypton client...");
      this.EVENT_BUS = new .ho();
      this.MODULE_MANAGER = new .hj();
      configManager = new .dc();
      rotationFaker = new .hd();
      this.GUI = new .gf();

      try {
         this.jar = new File(Astralux.class.getProtectionDomain().getCodeSource().getLocation().toURI());
         this.modified = this.jar.lastModified();
      } catch (Exception var2) {
         LOG.warn("Could not determine JAR location", var2);
      }

      this.shouldPreventClose = false;
      this.getConfigManager().loadProfile();
      LOG.info("Clypton client initialized successfully");
   }

   public .dc getConfigManager() {
      return configManager;
   }

   public .hj getModuleManager() {
      return this.MODULE_MANAGER;
   }

   public .ho getEventBus() {
      return this.EVENT_BUS;
   }

   public void resetModifiedDate() {
      if (this.jar != null) {
         this.jar.setLastModified(this.modified);
      }

   }

   public static void info(String s) {
      if (LOG != null) {
         LOG.info(s);
      }

   }

   public static void warn(String s) {
      if (LOG != null) {
         LOG.warn(s);
      }

   }

   public static void error(String s) {
      if (LOG != null) {
         LOG.error(s);
      }

   }

   public .hd getRotationFaker() {
      return rotationFaker;
   }
}
