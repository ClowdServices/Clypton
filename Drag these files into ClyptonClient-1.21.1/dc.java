import client.astralux.Astralux;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.class_1792;
import net.minecraft.class_2960;
import net.minecraft.class_310;
import net.minecraft.class_7923;
import org.lwjgl.glfw.GLFW;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class dc {
   private static final Logger LOGGER = LoggerFactory.getLogger(dc.class);
   private final Path configDir = FabricLoader.getInstance().getGameDir().resolve(d909("EBsbEB4f"));
   private final Path configFile;
   private final Gson gson = new Gson();
   private JsonObject jsonObject;
   private static final Path CONFIGS_DIR = FabricLoader.getInstance().getGameDir().resolve("config/ClyptonClient");
   private static final Random RANDOM = new Random();
   private final Map<String, Object> runtimeConfig = new ConcurrentHashMap();

   public dc() {
      this.configFile = this.configDir.resolve(d909("EgcBBBYUDAJVFg4REQ=="));

      try {
         if (!Files.exists(CONFIGS_DIR, new LinkOption[0])) {
            Files.createDirectories(CONFIGS_DIR);
         }
      } catch (IOException var2) {
         LOGGER.error(d909("NRUcGhIcWQ4UXB4MGuH156Pn6ujh4e75q+jk/Orz5f3h7Q=="), var2);
      }

   }

   public void set(String key, Object value) {
      if (this.jsonObject == null) {
         this.jsonObject = new JsonObject();
      }

      this.runtimeConfig.put(key, value);
      if (value instanceof Boolean) {
         this.jsonObject.addProperty(key, (Boolean)value);
      } else if (value instanceof Number) {
         this.jsonObject.addProperty(key, (Number)value);
      } else if (value instanceof String) {
         this.jsonObject.addProperty(key, (String)value);
      }

   }

   public int getInt(String key, int defaultValue) {
      try {
         if (this.runtimeConfig.containsKey(key)) {
            return ((Number)this.runtimeConfig.get(key)).intValue();
         }

         if (this.jsonObject != null && this.jsonObject.has(key)) {
            return this.jsonObject.get(key).getAsInt();
         }
      } catch (Exception var4) {
         LOGGER.warn(d909("NgYHGQVYHh8PCBQQGKDo7Pek4+n1qOLv8rat9fI="), key, var4);
      }

      return defaultValue;
   }

   public boolean getBoolean(String key, boolean defaultValue) {
      try {
         if (this.runtimeConfig.containsKey(key)) {
            return (Boolean)this.runtimeConfig.get(key);
         }

         if (this.jsonObject != null && this.jsonObject.has(key)) {
            return this.jsonObject.get(key).getAsBoolean();
         }
      } catch (Exception var4) {
         LOGGER.warn(d909("NgYHGQVYHh8PCBQQGKDj7ezo4OfpqO/l+azm6/aqsenu"), key, var4);
      }

      return defaultValue;
   }

   public double getDouble(String key, double defaultValue) {
      try {
         if (this.runtimeConfig.containsKey(key)) {
            return ((Number)this.runtimeConfig.get(key)).doubleValue();
         }

         if (this.jsonObject != null && this.jsonObject.has(key)) {
            return this.jsonObject.get(key).getAsDouble();
         }
      } catch (Exception var5) {
         LOGGER.warn(d909("NgYHGQVYHh8PCBQQGKDl7fbm6eOn7ub4q+fo97Ww6u8="), key, var5);
      }

      return defaultValue;
   }

   public String getString(String key, String defaultValue) {
      try {
         if (this.runtimeConfig.containsKey(key)) {
            return (String)this.runtimeConfig.get(key);
         }

         if (this.jsonObject != null && this.jsonObject.has(key)) {
            return this.jsonObject.get(key).getAsString();
         }
      } catch (Exception var4) {
         LOGGER.warn(d909("NgYHGQVYHh8PCBQQGKDy9vHt6+Gn7ub4q+fo97Ww6u8="), key, var4);
      }

      return defaultValue;
   }

   public void save() {
      this.saveConfig();
   }

   public void loadProfile() {
      LOGGER.info(d909("IAAUBAMRFx1bCBJeE+/g5qP09+nh4eXvq+r/4eKqsenu"), this.configFile);
      long startTime = System.currentTimeMillis();
      long var3 = 10000L;

      try {
         if (!Files.exists(this.configDir, new LinkOption[0])) {
            LOGGER.info(d909("MAYQFwMRFx1bHxIQGenmouft9+Pk/Ob48rat9fI="), this.configDir);
            Files.createDirectories(this.configDir);
         }

         if (this.jsonObject == null) {
            if (Files.exists(this.configFile, new LinkOption[0])) {
               LOGGER.info(d909("IREUEh4WHloeBBQNC+nv5aPn6ujh4e6q7eXh6w=="));

               try {
                  BufferedReader reader = Files.newBufferedReader(this.configFile, StandardCharsets.UTF_8);

                  try {
                     String content = new String(Files.readAllBytes(this.configFile), StandardCharsets.UTF_8);
                     if (content.trim().isEmpty()) {
                        LOGGER.warn(d909("MBsbEB4fWRwSEBheFvOh5+708f+rqOr47u355+H3sfz247X1+Pb/8/w="));
                        this.jsonObject = new JsonObject();
                     } else {
                        this.jsonObject = JsonParser.parseString(content).getAsJsonObject();
                        LOGGER.info(d909("IAEWFRILChwOEBEHX/Dg8PDh4abk5+fs4uut6Ob89A=="));
                        this.loadRuntimeConfig();
                     }
                  } catch (Throwable var26) {
                     if (reader != null) {
                        try {
                           reader.close();
                        } catch (Throwable var23) {
                           var26.addSuppressed(var23);
                        }
                     }

                     throw var26;
                  }

                  if (reader != null) {
                     reader.close();
                  }
               } catch (Exception var27) {
                  LOGGER.error(d909("NgYHGQVYCx8aGBQQGKDi7e3i7OGn7uDm7qCt7f318Ob6+vK2+f3uuvTy+A=="), var27);
                  this.jsonObject = new JsonObject();
               }
            } else {
               LOGGER.info(d909("PRtVFRgWHxMcXBsXE+Wh5Ozx6+KrqOr47u355+H3sfz247X5+f0="));
               this.jsonObject = new JsonObject();
            }
         }

         ArrayList allModules = new ArrayList();

         try {
            if (Astralux.INSTANCE != null && Astralux.INSTANCE.getModuleManager() != null) {
               List<bf> modules = Astralux.INSTANCE.getModuleManager().getAllModules();
               if (modules != null) {
                  allModules.addAll(modules);
               }
            }

            LOGGER.info(d909("NRsAGBNYAgdbERIaCuzk8aPw6qbr5+juq//o+vv5//XgtPP55Q=="), allModules.size());
         } catch (Exception var22) {
            LOGGER.error(d909("NRUcGhIcWQ4UXBobC6Ds7efx6eOn5OD5/6Ct+eb8/bLn5uy24/e5+fTy6ffx1cSC1M3RzofNxNrf1Y3CxsPF"), var22);
            allModules = new ArrayList();
         }

         int loadedModules = 0;
         int failedModules = 0;
         if (System.currentTimeMillis() - startTime > 10000L) {
            LOGGER.error(d909("MBsbEB4fWRYUHRkXEeeh9urp4OKn5/z+q+3r+urisenutPjl"), 10000L);
            return;
         }

         Iterator var8 = Astralux.INSTANCE.getModuleManager().getAllModules().iterator();

         while(var8.hasNext()) {
            bf module = (bf)var8.next();

            try {
               String moduleName = module.getName().toString();
               JsonElement moduleElem = this.jsonObject.get(moduleName);
               if (moduleElem != null && moduleElem.isJsonObject()) {
                  JsonObject modObj = moduleElem.getAsJsonObject();
                  JsonElement enabledElem = modObj.get(d909("FhoUFBsdHQ=="));
                  if (enabledElem != null && enabledElem.isJsonPrimitive() && enabledElem.getAsBoolean()) {
                     LOGGER.debug(d909("NhoUFBsRFx1bERIaCuzkuKP/+A=="), moduleName);
                     if (!module.isEnabled()) {
                        module.setEnabled(true);
                     }
                  }

                  ArrayList settings = new ArrayList();

                  try {
                     settings.addAll(module.getSettings());
                  } catch (Exception var24) {
                     LOGGER.error(d909("NRUcGhIcWQ4UXBobC6Dy5/fw7Ojg+6ns5P6t4+D05P72rrXt6g=="), moduleName, var24);
                     continue;
                  }

                  Iterator var15 = settings.iterator();

                  while(var15.hasNext()) {
                     ab setting = (ab)var15.next();
                     if (System.currentTimeMillis() - startTime > 10000L) {
                        LOGGER.error(d909("MBsbEB4fWRYUHRkXEeeh9urp4OKn5/z+q+3r+urisenutPjlt+/x8/f5ve7tz8LH0NfMyMCI2s/f2MTAyJDKz53PyA=="), new Object[]{10000L, module.getName(), setting.getName()});
                        break;
                     }

                     try {
                        String settingName = setting.getName().toString();
                        JsonElement settingElem = modObj.get(settingName);
                        if (settingElem != null) {
                           this.setValueFromJson(setting, settingElem, module);
                           LOGGER.trace(d909("PxsUEhIcWQkeCAkXEeeh+f6q/vs="), moduleName, settingName);
                        }
                     } catch (Exception var21) {
                        LOGGER.error(d909("NgYHGQVYFRUaGBQQGKDy5/fw7OjgqPL3q+ri/K/9/vbm+PC27OU="), new Object[]{setting.getName(), moduleName, var21});
                     }
                  }

                  ++loadedModules;
                  LOGGER.debug(d909("IAEWFRILChwOEBEHX+zu4+fh4abq5+3/5+m3rvTtsbro6fjlvg=="), this.getSafeModuleName(module), System.currentTimeMillis() - startTime);
               } else {
                  LOGGER.debug(d909("PRtVFRgWHxMcXBsRCu7louXr96bq5+3/5+m3rvTt"), moduleName);
               }
            } catch (Throwable var25) {
               Throwable t = var25;
               ++failedModules;
               String moduleName = d909("BhoeGBgPFw==");

               try {
                  moduleName = this.getSafeModuleName(module);
                  LOGGER.error(d909("NgYHGQVYFRUaGBQQGKDs7efx6eOn6+bk7eXq+/3x5fv8+q+27OU="), moduleName, t);

                  try {
                     if (module != null && module.isEnabled()) {
                        module.setEnabled(false);
                        LOGGER.info(d909("Nx0GFxUUHB5bERIaCuzkoufx4Kbz56nm5O3p5+H3sffh5vrkrbji5w=="), moduleName);
                     }
                  } catch (Exception var19) {
                     LOGGER.error(d909("NRUcGhIcWQ4UXBkXDOHj7uak6Onj/eXvq+3r+urisffh5vrkrbji5w=="), moduleName, var19);
                  }
               } catch (Exception var20) {
                  LOGGER.error(d909("NgYHGQVYDhISEBheF+Hv5u/t6+Gn5ebu/uDoruP/8Pb6+vK28urr9ek="), var20);
               }
            }
         }

         long totalTime = System.currentTimeMillis() - startTime;
         if (failedModules > 0) {
            LOGGER.warn("Loaded settings for {}/{} modules ({} failed) in {}ms", new Object[]{loadedModules, allModules.size(), failedModules, totalTime});
         } else {
            LOGGER.info("Successfully loaded settings for {}/{} modules in {}ms", new Object[]{loadedModules, allModules.size(), totalTime});
         }
      } catch (Exception var28) {
         LOGGER.error(d909("MAYcAh4bGBZbGQ8MEPKh7uzl4e/p76n6+ePr5+P1sfP14PDkt+Pk9+imveXi"), new Object[]{System.currentTimeMillis() - startTime, var28.getMessage(), var28});
         this.jsonObject = new JsonObject();
      }

   }

   private void loadRuntimeConfig() {
      if (this.jsonObject != null) {
         Iterator var1 = this.jsonObject.keySet().iterator();

         while(true) {
            String key;
            do {
               if (!var1.hasNext()) {
                  LOGGER.info(d909("PxsUEhIcWQEGXA8LEfTo7+ak5unp7uDtq+nj+v359OE="), this.runtimeConfig.size());
                  return;
               }

               key = (String)var1.next();
            } while(!key.startsWith(d909("GwERWA==")) && !key.contains("."));

            try {
               JsonElement element = this.jsonObject.get(key);
               if (element.isJsonPrimitive()) {
                  if (element.getAsJsonPrimitive().isBoolean()) {
                     this.runtimeConfig.put(key, element.getAsBoolean());
                  } else if (element.getAsJsonPrimitive().isNumber()) {
                     this.runtimeConfig.put(key, element.getAsDouble());
                  } else if (element.getAsJsonPrimitive().isString()) {
                     this.runtimeConfig.put(key, element.getAsString());
                  }
               }
            } catch (Exception var4) {
               LOGGER.warn(d909("NgYHGQVYFRUaGBQQGKDz9+3w7OviqOrl5erk6a/2/uCz//Dvrbji5w=="), key, var4);
            }
         }
      }
   }

   public String saveConfigWithId(String name) {
      String configId = this.generateConfigId();
      String var10000 = name.replaceAll("[^a-zA-Z0-9-_]", "");
      String fileName = var10000 + "_" + configId + ".json";
      Path configPath = CONFIGS_DIR.resolve(fileName);

      try {
         JsonObject configData = new JsonObject();
         configData.addProperty(d909("HRUYEw=="), name);
         configData.addProperty("id", configId);
         configData.addProperty(d909("Bx0YEwQMGBcL"), (Number)System.currentTimeMillis());
         JsonObject modules = new JsonObject();
         Iterator var7 = Astralux.INSTANCE.getModuleManager().getAllModules().iterator();

         while(var7.hasNext()) {
            bf module = (bf)var7.next();
            JsonObject modObj = new JsonObject();
            modObj.addProperty(d909("FhoUFBsdHQ=="), module.isEnabled());
            Iterator var10 = module.getSettings().iterator();

            while(var10.hasNext()) {
               ab setting = (ab)var10.next();
               this.save(setting, modObj, module);
            }

            modules.add(module.getName().toString(), modObj);
         }

         configData.add(d909("HhsRAxsdCg=="), modules);
         JsonObject runtime = new JsonObject();
         Iterator var16 = this.runtimeConfig.entrySet().iterator();

         while(var16.hasNext()) {
            Entry<String, Object> entry = (Entry)var16.next();
            Object value = entry.getValue();
            if (value instanceof Boolean) {
               runtime.addProperty((String)entry.getKey(), (Boolean)value);
            } else if (value instanceof Number) {
               runtime.addProperty((String)entry.getKey(), (Number)value);
            } else if (value instanceof String) {
               runtime.addProperty((String)entry.getKey(), (String)value);
            }
         }

         configData.add(d909("AQEbAh4VHA=="), runtime);
         BufferedWriter writer = Files.newBufferedWriter(configPath, StandardCharsets.UTF_8);

         try {
            this.gson.toJson((JsonElement)configData, (Appendable)writer);
         } catch (Throwable var13) {
            if (writer != null) {
               try {
                  writer.close();
               } catch (Throwable var12) {
                  var13.addSuppressed(var12);
               }
            }

            throw var13;
         }

         if (writer != null) {
            writer.close();
         }

         LOGGER.info(d909("IBUDExNYGhUVGhQZX6f6/6Sk8u/z4KnDz7at9fI="), name, configId);
         return configId;
      } catch (Exception var14) {
         LOGGER.error(d909("NRUcGhIcWQ4UXA4fCeWh4ezq4+/gqP7j/+Stx8s="), var14);
         return null;
      }
   }

   public boolean loadConfigById(String configId) {
      try {
         Path configPath = this.findConfigById(configId);
         if (configPath == null) {
            LOGGER.warn(d909("MBsbEB4fWQ0SCBVeNsSh+f6k6+nzqO/l/uLp"), configId);
            return false;
         } else {
            String content = new String(Files.readAllBytes(configPath), StandardCharsets.UTF_8);
            JsonObject configData = JsonParser.parseString(content).getAsJsonObject();
            String name = configData.get(d909("HRUYEw==")).getAsString();
            JsonObject modules = configData.getAsJsonObject(d909("HhsRAxsdCg=="));
            Iterator var7 = Astralux.INSTANCE.getModuleManager().getAllModules().iterator();

            while(true) {
               bf module;
               JsonElement element;
               do {
                  do {
                     String key;
                     if (!var7.hasNext()) {
                        if (configData.has(d909("AQEbAh4VHA=="))) {
                           JsonObject runtime = configData.getAsJsonObject(d909("AQEbAh4VHA=="));
                           Iterator var19 = runtime.keySet().iterator();

                           while(var19.hasNext()) {
                              key = (String)var19.next();
                              element = runtime.get(key);
                              if (element.isJsonPrimitive()) {
                                 if (element.getAsJsonPrimitive().isBoolean()) {
                                    this.runtimeConfig.put(key, element.getAsBoolean());
                                 } else if (element.getAsJsonPrimitive().isNumber()) {
                                    this.runtimeConfig.put(key, element.getAsDouble());
                                 } else if (element.getAsJsonPrimitive().isString()) {
                                    this.runtimeConfig.put(key, element.getAsString());
                                 }
                              }
                           }
                        }

                        LOGGER.info(d909("PxsUEhIcWRkUEhsXGKCm+f6jpa7OzLOq8PGk"), name, configId);
                        return true;
                     }

                     module = (bf)var7.next();
                     key = module.getName().toString();
                     element = modules.get(key);
                  } while(element == null);
               } while(!element.isJsonObject());

               JsonObject modObj = element.getAsJsonObject();
               JsonElement enabledElem = modObj.get(d909("FhoUFBsdHQ=="));
               if (enabledElem != null && enabledElem.isJsonPrimitive()) {
                  boolean shouldBeEnabled = enabledElem.getAsBoolean();
                  if (module.isEnabled() != shouldBeEnabled) {
                     module.setEnabled(shouldBeEnabled);
                  }
               }

               Iterator var20 = module.getSettings().iterator();

               while(var20.hasNext()) {
                  ab setting = (ab)var20.next();
                  String settingName = setting.getName().toString();
                  JsonElement settingElem = modObj.get(settingName);
                  if (settingElem != null) {
                     this.setValueFromJson(setting, settingElem, module);
                  }
               }
            }
         }
      } catch (Exception var17) {
         LOGGER.error(d909("NRUcGhIcWQ4UXBERHuSh4ezq4+/gqP7j/+Stx8uqsenu"), configId, var17);
         return false;
      }
   }

   public boolean deleteConfig(String configId) {
      try {
         Path configPath = this.findConfigById(configId);
         if (configPath == null) {
            LOGGER.warn(d909("MBsbEB4fWQ0SCBVeNsSh+f6k6+nzqO/l/uLp"), configId);
            return false;
         } else {
            Files.delete(configPath);
            LOGGER.info(d909("NxEZEwMdHVoYExMYFueh9erw7abOzLOq8PE="), configId);
            return true;
         }
      } catch (Exception var3) {
         LOGGER.error(d909("NRUcGhIcWQ4UXBkbE+X156Pn6ujh4e4="), var3);
         return false;
      }
   }

   public List<ae> listConfigs() {
      ArrayList configs = new ArrayList();

      try {
         if (!Files.exists(CONFIGS_DIR, new LinkOption[0])) {
            return configs;
         }

         Files.list(CONFIGS_DIR).filter((path) -> {
            return path.toString().endsWith(d909("XR4GGRk="));
         }).forEach((path) -> {
            try {
               String content = new String(Files.readAllBytes(path), StandardCharsets.UTF_8);
               JsonObject data = JsonParser.parseString(content).getAsJsonObject();
               String name = data.get(d909("HRUYEw==")).getAsString();
               String id = data.get("id").getAsString();
               long timestamp = data.get(d909("Bx0YEwQMGBcL")).getAsLong();
               configs.add(new ae(name, id, timestamp));
            } catch (Exception var8) {
               LOGGER.error(d909("NRUcGhIcWQ4UXA8bHuSh4ezq4+/gqO/j5+m3rvTt"), path, var8);
            }

         });
         configs.sort((a, b) -> {
            return Long.compare(b.timestamp, a.timestamp);
         });
      } catch (Exception var3) {
         LOGGER.error(d909("NRUcGhIcWQ4UXBEXDPSh4ezq4+/g+w=="), var3);
      }

      return configs;
   }

   public String exportConfigToClipboard(String configId) {
      try {
         Path configPath = this.findConfigById(configId);
         if (configPath == null) {
            LOGGER.warn(d909("MBsbEB4fWQ0SCBVeNsSh+f6k6+nzqO/l/uLprun/47L27OX55ew="), configId);
            return null;
         } else {
            String jsonContent = new String(Files.readAllBytes(configPath), StandardCharsets.UTF_8);
            LOGGER.info(d909("IREUElcbFhQdFRpeGent56+k6ePp7/3isaz28w=="), jsonContent.length());
            String base64Config = Base64.getEncoder().encodeToString(jsonContent.getBytes(StandardCharsets.UTF_8));
            LOGGER.info(d909("NhoWGRMdHVoPE10cHvPktLeoperi5u7+47at9fI="), base64Config.length());
            String shareableConfig = "CLYPTON_CONFIG:" + base64Config;

            try {
               class_310 mc = class_310.method_1551();
               if (mc != null && mc.method_22683() != null) {
                  GLFW.glfwSetClipboardString(mc.method_22683().method_4490(), shareableConfig);
                  LOGGER.info(d909("NgwFGQUMHB5bHxIQGenmovj5pfLoqOrm4vzv4e7i9bLm5/z48Lje1t3L"), configId);
                  return shareableConfig;
               } else {
                  LOGGER.error(d909("MBUbGBgMWRsYHxgNDKDM6+3h5vTm7v2q/OXj6uDnsfT85rX1+/Hp+PT97/o="));
                  return null;
               }
            } catch (Exception var7) {
               LOGGER.error(d909("NRUcGhIcWQ4UXB4RD/mh9uyk5uru+Ovl6v7prvrj+Pz0tNLa0c8="), var7);
               return shareableConfig;
            }
         }
      } catch (Exception var8) {
         LOGGER.error(d909("NRUcGhIcWQ4UXBgGD+/z9qPn6ujh4e6q/+Ot7eP54fD89efy"), var8);
         return null;
      }
   }

   public boolean importConfigFromClipboard(String base64String) {
      try {
         LOGGER.info(d909("MgABExoIDRMVG10KEKDo7/Pr9/Kn6+bk7eXqruni/v+z9/n/5/r2++n4"));
         if (base64String.startsWith(d909("MichJDY0LCIkPzIwOcnGuA=="))) {
            base64String = base64String.substring(d909("MichJDY0LCIkPzIwOcnGuA==").length());
         }

         byte[] decodedBytes = Base64.getDecoder().decode(base64String);
         String jsonContent = new String(decodedBytes, StandardCharsets.UTF_8);
         LOGGER.info(d909("NxEWGRMdHVoYExMYFuetou/h6+Hz4LOq8PE="), jsonContent.length());
         JsonObject configData = JsonParser.parseString(jsonContent).getAsJsonObject();
         String name = configData.get(d909("HRUYEw==")).getAsString();
         String originalId = configData.get("id").getAsString();
         LOGGER.info(d909("OhkFGQUMEBQcXB4REebo5aOj/vugqP7j/+St4f359vv99fm23ty54eY="), name, originalId);
         String newId = this.generateConfigId();
         configData.addProperty("id", newId);
         configData.addProperty(d909("HRUYEw=="), name + " (Imported)");
         configData.addProperty(d909("Bx0YEwQMGBcL"), (Number)System.currentTimeMillis());
         String var10000 = name.replaceAll("[^a-zA-Z0-9-_]", "");
         String fileName = var10000 + "_imported_" + newId + ".json";
         Path configPath = CONFIGS_DIR.resolve(fileName);
         BufferedWriter writer = Files.newBufferedWriter(configPath, StandardCharsets.UTF_8);

         try {
            this.gson.toJson((JsonElement)configData, (Appendable)writer);
         } catch (Throwable var14) {
            if (writer != null) {
               try {
                  writer.close();
               } catch (Throwable var13) {
                  var14.addSuppressed(var13);
               }
            }

            throw var14;
         }

         if (writer != null) {
            writer.close();
         }

         LOGGER.info(d909("OhkFGQUMHB5bHxIQGenmoqT/+KGn/+D+46zj6/iw2NaptO7r"), name, newId);
         return true;
      } catch (Exception var15) {
         LOGGER.error(d909("NRUcGhIcWQ4UXBQTD+/z9qPn6ujh4e6q7f7i46/z/fvj9vr35fw="), var15);
         return false;
      }
   }

   public String getClipboardContent() {
      try {
         class_310 mc = class_310.method_1551();
         if (mc != null && mc.method_22683() != null) {
            String content = GLFW.glfwGetClipboardString(mc.method_22683().method_4490());
            LOGGER.info(d909("NBsBVhQUEAoZExwMG6Di7e3w4OjzqPz54uLqrsjc18W/tPnz+f/t8qG85uM="), content != null ? content.length() : 0);
            return content;
         } else {
            LOGGER.error(d909("MBUbGBgMWRsYHxgNDKDM6+3h5vTm7v2q/OXj6uDnsfT85rX1+/Hp+PT97/o="));
            return null;
         }
      } catch (Exception var3) {
         LOGGER.error(d909("NRUcGhIcWQ4UXA8bHuSh4e/t9eTo6fvuq/n+5+H3sdXf0sI="), var3);
         return null;
      }
   }

   private String generateConfigId() {
      return String.format(d909("VkRDEg=="), RANDOM.nextInt(1000000));
   }

   private Path findConfigById(String configId) {
      try {
         return !Files.exists(CONFIGS_DIR, new LinkOption[0]) ? null : (Path)Files.list(CONFIGS_DIR).filter((path) -> {
            return path.getFileName().toString().contains("_" + configId + ".json");
         }).findFirst().orElse((Object)null);
      } catch (Exception var3) {
         LOGGER.error(d909("NgYHGQVYHxMVGBQQGKDi7e3i7OGn6vCqwsg="), var3);
         return null;
      }
   }

   public void saveConfig() {
      LOGGER.info(d909("IBUDHxkfWRkUEhsXGPXz4/ft6uin/Oawq/fw"), this.configFile);

      try {
         JsonObject out = new JsonObject();
         int savedModules = 0;
         Iterator var3 = this.runtimeConfig.entrySet().iterator();

         while(var3.hasNext()) {
            Entry entry = (Entry)var3.next();

            try {
               Object value = entry.getValue();
               if (value instanceof Boolean) {
                  out.addProperty((String)entry.getKey(), (Boolean)value);
               } else if (value instanceof Number) {
                  out.addProperty((String)entry.getKey(), (Number)value);
               } else if (value instanceof String) {
                  out.addProperty((String)entry.getKey(), (String)value);
               }
            } catch (Exception var11) {
               LOGGER.error(d909("NgYHGQVYChsNFRMZX/L07Pft6OOn6+bk7eXqrun/47L48eyst+Pk"), entry.getKey(), var11);
            }
         }

         var3 = Astralux.INSTANCE.getModuleManager().getAllModules().iterator();

         while(var3.hasNext()) {
            bf module = (bf)var3.next();

            try {
               JsonObject modObj = new JsonObject();
               modObj.addProperty(d909("FhoUFBsdHQ=="), module.isEnabled());
               Iterator var6 = module.getSettings().iterator();

               while(var6.hasNext()) {
                  ab setting = (ab)var6.next();

                  try {
                     this.save(setting, modObj, module);
                  } catch (Exception var10) {
                     LOGGER.error(d909("NgYHGQVYChsNFRMZX/Pk9vft6+Gn8/Sq7eP/ruL/9ef/8bXt6g=="), new Object[]{setting.getName(), module.getName(), var10});
                  }
               }

               out.add(module.getName().toString(), modObj);
               ++savedModules;
            } catch (Exception var13) {
               LOGGER.error(d909("NgYHGQVYChsNFRMZX+3u5vbo4Kbk5+fs4uv4/O7k+P39rrXt6g=="), module.getName(), var13);
            }
         }

         Path tempFile = Files.createTempFile(this.configDir, d909("EgcBBBYUDAI="), d909("XQAYBg=="));
         BufferedWriter writer = Files.newBufferedWriter(tempFile, StandardCharsets.UTF_8);

         try {
            this.gson.toJson((JsonElement)out, (Appendable)writer);
         } catch (Throwable var12) {
            if (writer != null) {
               try {
                  writer.close();
               } catch (Throwable var9) {
                  var12.addSuppressed(var9);
               }
            }

            throw var12;
         }

         if (writer != null) {
            writer.close();
         }

         Files.move(tempFile, this.configFile, StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.ATOMIC_MOVE);
         LOGGER.info("Successfully saved configuration for {}/{} modules", savedModules, Astralux.INSTANCE.getModuleManager().getAllModules().size());
      } catch (Exception var14) {
         LOGGER.error(d909("NRUcGhIcWQ4UXA4fCeWh4ezq4+/g/fvr/+Xi4A=="), var14);
      }

   }

   public void shutdown() {
      this.saveConfig();
   }

   private String getSafeModuleName(bf module) {
      try {
         return module != null && module.getName() != null ? module.getName().toString() : d909("BhoeGBgPFw==");
      } catch (Exception var3) {
         LOGGER.error(d909("NgYHGQVYHh8PCBQQGKDs7efx6eOn5ujn7g=="), var3);
         return d909("FgYHGQU=");
      }
   }

   private String getSafeSettingName(ab setting) {
      try {
         return setting != null && setting.getName() != null ? setting.getName().toString() : d909("BhoeGBgPFw==");
      } catch (Exception var3) {
         LOGGER.error(d909("NgYHGQVYHh8PCBQQGKDy5/fw7OjgqOfr5uk="), var3);
         return d909("FgYHGQU=");
      }
   }

   private void setValueFromJson(ab setting, JsonElement jsonElement, bf module) {
      if (setting != null && jsonElement != null) {
         try {
            if (setting instanceof ff) {
               ff bs = (ff)setting;
               if (jsonElement.isJsonPrimitive()) {
                  bs.setValue(jsonElement.getAsBoolean());
               }
            } else {
               int key;
               if (setting instanceof cu) {
                  cu<?> ms = (cu)setting;
                  if (jsonElement.isJsonPrimitive()) {
                     key = jsonElement.getAsInt();
                     if (key != -1) {
                        ms.setModeIndex(key);
                     } else {
                        ms.setModeIndex(ms.getOriginalValue());
                     }
                  }
               } else if (setting instanceof gn) {
                  gn ns = (gn)setting;
                  if (jsonElement.isJsonPrimitive()) {
                     ns.setValue(jsonElement.getAsDouble());
                  }
               } else if (setting instanceof fp) {
                  fp bind = (fp)setting;
                  if (jsonElement.isJsonPrimitive()) {
                     key = jsonElement.getAsInt();
                     bind.setValue(key);
                     if (bind.isModuleKey()) {
                        module.setKeybind(key);
                     }
                  }
               } else if (setting instanceof bu) {
                  bu ss = (bu)setting;
                  if (jsonElement.isJsonPrimitive()) {
                     ss.setValue(jsonElement.getAsString());
                  }
               } else if (setting instanceof fr) {
                  fr mm = (fr)setting;
                  if (jsonElement.isJsonObject()) {
                     JsonObject obj = jsonElement.getAsJsonObject();
                     if (obj.has(d909("Hh0b")) && obj.has(d909("HhUN"))) {
                        mm.setCurrentMin(obj.get(d909("Hh0b")).getAsDouble());
                        mm.setCurrentMax(obj.get(d909("HhUN")).getAsDouble());
                     }
                  }
               } else {
                  if (setting instanceof dn) {
                     dn is = (dn)setting;
                     if (jsonElement.isJsonPrimitive()) {
                        is.setItem((class_1792)class_7923.field_41178.method_10223(class_2960.method_60654(jsonElement.getAsString())));
                        return;
                     }
                  }

                  if (setting instanceof il) {
                     il bls = (il)setting;
                     if (jsonElement.isJsonPrimitive()) {
                        bls.setValueFromString(jsonElement.getAsString());
                     }
                  }
               }
            }
         } catch (Exception var13) {
            LOGGER.error(d909("NgYHGQVYCh8PCBQQGKD34+/x4Kbh5/uq8PG3rvTt"), new Object[]{setting != null ? setting.getName() : d909("HQEZGg=="), var13.getMessage(), var13});
         }

      } else {
         LOGGER.warn(d909("MBUbGBgMWQkeCF0IHuz057mk9uPz/ODk7Kzi/K/64v390fnz+v337rv17r7x1c3O"));
      }
   }

   private void save(ab setting, JsonObject jsonObject, bf module) {
      if (setting != null && jsonObject != null && module != null) {
         try {
            String name = setting.getName().toString();
            if (setting instanceof ff) {
               ff bs = (ff)setting;
               jsonObject.addProperty(name, bs.getValue());
            } else if (setting instanceof cu) {
               cu<?> ms = (cu)setting;
               jsonObject.addProperty(name, (Number)ms.getModeIndex());
            } else if (setting instanceof gn) {
               gn ns = (gn)setting;
               jsonObject.addProperty(name, (Number)ns.getValue());
            } else if (setting instanceof fp) {
               fp bind = (fp)setting;
               jsonObject.addProperty(name, (Number)bind.getValue());
            } else if (setting instanceof bu) {
               bu ss = (bu)setting;
               jsonObject.addProperty(name, ss.getValue());
            } else if (setting instanceof fr) {
               fr mm = (fr)setting;
               JsonObject range = new JsonObject();
               range.addProperty(d909("Hh0b"), (Number)mm.getCurrentMin());
               range.addProperty(d909("HhUN"), (Number)mm.getCurrentMax());
               jsonObject.add(name, range);
            } else if (setting instanceof dn) {
               dn is = (dn)setting;
               jsonObject.addProperty(name, class_7923.field_41178.method_10221(is.getItem()).toString());
            } else if (setting instanceof il) {
               il bls = (il)setting;
               jsonObject.addProperty(name, bls.getValueAsString());
            }
         } catch (Exception var14) {
            LOGGER.error(d909("NgYHGQVYChsNFRMZX/Pk9vft6+Gn8/Swq/fw"), new Object[]{setting.getName(), var14.getMessage(), var14});
         }

      } else {
         LOGGER.warn(d909("MBUbGBgMWQkaChheDOX19urq4ryn++z+/+Xj6aOw++H8+tr0/f367re88uy/zc7G1sjAhs7bicTewME="));
      }
   }

   // $FF: synthetic method
   private static String d909(String var0) {
      byte[] var1 = Base64.getDecoder().decode(var0);
      byte[] var2 = new byte[var1.length];

      for(int var3 = 0; var3 < var1.length; ++var3) {
         var2[var3] = (byte)(var1[var3] ^ 2163 + var3 & 255);
      }

      return new String(var2);
   }
}
