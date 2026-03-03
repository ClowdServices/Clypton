import client.astralux.Astralux;
import java.awt.Color;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.minecraft.class_1268;
import net.minecraft.class_1304;
import net.minecraft.class_1703;
import net.minecraft.class_1707;
import net.minecraft.class_1713;
import net.minecraft.class_1799;
import net.minecraft.class_1802;
import net.minecraft.class_1836;
import net.minecraft.class_1923;
import net.minecraft.class_2338;
import net.minecraft.class_243;
import net.minecraft.class_2561;
import net.minecraft.class_2848;
import net.minecraft.class_4184;
import net.minecraft.class_4587;
import net.minecraft.class_746;
import net.minecraft.class_7833;
import net.minecraft.class_1792.class_9635;
import net.minecraft.class_2848.class_2849;

public class dl extends bf {
   private final ff autoBoost = new ff(d887("UWRmfDRXeXhrbQ=="), true);
   private final ff showPath = new ff(d887("Q3l9ZDRFd2Nw"), true);
   private final ff autoNavigate = new ff(d887("UWRmfDRbd2FxfntveQ=="), true);
   private final ff autoBuyRockets = new ff(d887("UWRmfDRXY244S3V4d3hqbA=="), true);
   private final gn searchRadius = new gn(d887("Q3RzYXd9NkV5fXNubw=="), 5.0D, 50.0D, 20.0D, 1.0D);
   private final gn boostInterval = new gn(d887("Un59YGA1X3lsfGhtfXE="), 1.0D, 20.0D, 10.0D, 0.5D);
   private final gn lookAhead = new gn(d887("XH59eDRUfnJ5fQ=="), 20.0D, 100.0D, 50.0D, 5.0D);
   private final gn maxRocketPrice = new gn(d887("XXBqM0Z6dXx9bTpLbnR9eg=="), 1.0D, 100000.0D, 100.0D, 1.0D);
   private final gn maxSusBlocks = new gn(d887("XXBqM0dgZTdadXV4d24="), 1.0D, 21.0D, 3.0D, 1.0D);
   private final ff ignoreAdjacentChunks = new ff(d887("WXZ8fGZwNlZ8c3t4eXNqP2NJV01PVg=="), true);
   private final gn rocketUseDelay = new gn(d887("Qn5xeHFhNkJrfDpfeXF/Zg=="), 50.0D, 500.0D, 150.0D, 10.0D);
   private final gn chunkRadiusCheck = new gn(d887("U3lnfX81RHZ8cG9oPF52ekNK"), 1.0D, 10.0D, 3.0D, 1.0D);
   private final ff autoLogout = new ff(d887("UWRmfDRZeXB3bG47T3x4elRY"), true);
   private final gn minHeight = new gn(d887("XXh8M1xwf3BwbQ=="), 150.0D, 256.0D, 180.0D, 5.0D);
   private final gn targetHeight = new gn(d887("RHBgdHFhNl99cH1zaA=="), 180.0D, 256.0D, 200.0D, 5.0D);
   private final gn minHealth = new gn(d887("XXh8M1xwd3tscQ=="), 1.0D, 20.0D, 10.0D, 1.0D);
   private final ff logOnTotemPop = new ff(d887("XH51fGFhNlh2OU50aHhzP3BOUg=="), true);
   private long lastBoostTime = 0L;
   private long lastCorrectionTime = 0L;
   private long lastRocketCheckTime = 0L;
   private final List<class_2338> futurePathBlocks = new ArrayList();
   private float initialYaw = 0.0F;
   private boolean isBuyingRockets = false;
   private boolean isCurrentlyBoosting = false;
   private int buyDelayCounter = 0;
   private float lastHealth = 20.0F;
   private long lastTotemPopTime = 0L;
   private gq chunkFinder = null;
   private long lastChunkCheckTime = 0L;
   private Set<class_1923> visitedChunks = new HashSet();

   public dl() {
      super(d887("VX1rZ2Z0VX9td3FddXN6elI="), d887("UWRmfDRzem44bnNvdD17c1lVUEIEUUkHTkBETwxORlpeWkETHEdTVFdUV15SWVtbYCA2Yz13dndh"), -1, hk.DONUT);
      ab[] var10001 = new ab[21 ^ 4];
      var10001[0] = this.autoBoost;
      var10001[1] = this.showPath;
      var10001[2] = this.autoNavigate;
      var10001[3] = this.autoBuyRockets;
      var10001[4] = this.ignoreAdjacentChunks;
      var10001[5] = this.searchRadius;
      var10001[6] = this.boostInterval;
      var10001[199 ^ 192] = this.lookAhead;
      var10001[8] = this.maxRocketPrice;
      var10001[9] = this.maxSusBlocks;
      var10001[103 ^ 109] = this.rocketUseDelay;
      var10001[217 ^ 210] = this.chunkRadiusCheck;
      var10001[12] = this.autoLogout;
      var10001[13] = this.minHeight;
      var10001[14] = this.targetHeight;
      var10001[15] = this.minHealth;
      var10001[16] = this.logOnTotemPop;
      this.addSettings(var10001);
   }

   public void onEnable() {
      super.onEnable();
      if (mc.field_1724 != null) {
         this.futurePathBlocks.clear();
         this.visitedChunks.clear();
         this.lastBoostTime = 0L;
         this.lastCorrectionTime = 0L;
         this.lastRocketCheckTime = 0L;
         this.initialYaw = mc.field_1724.method_36454();
         this.isBuyingRockets = false;
         this.isCurrentlyBoosting = false;
         this.buyDelayCounter = 0;
         this.lastHealth = mc.field_1724.method_6032();
         this.lastTotemPopTime = 0L;
         this.findChunkFinder();
         if (this.chunkFinder == null || !this.chunkFinder.isEnabled()) {
            mc.field_1724.method_7353(class_2561.method_43470("§c[ElytraChunkFinder] ChunkFinder is not enabled! Enable it first."), false);
            this.setEnabled(false);
            return;
         }

         mc.field_1724.method_7353(class_2561.method_43470("§d[ElytraChunkFinder] §7Starting automated chunk search..."), false);
         mc.field_1724.method_7353(class_2561.method_43470("§7Flying straight ahead (yaw: " + (int)this.initialYaw + "°)"), false);
         mc.field_1724.method_7353(class_2561.method_43470("§7Correction every 10 seconds"), false);
         if (this.autoLogout.getValue()) {
            class_746 var10000 = mc.field_1724;
            int var10001 = (int)this.minHeight.getValue();
            var10000.method_7353(class_2561.method_43470("§a[Safety] Auto-logout enabled (Min Height: " + var10001 + ", Target: " + (int)this.targetHeight.getValue() + ", Min Health: " + (int)this.minHealth.getValue() + ")"), false);
         }
      }

   }

   public void onDisable() {
      super.onDisable();
      this.futurePathBlocks.clear();
      this.visitedChunks.clear();
      this.isBuyingRockets = false;
   }

   private void findChunkFinder() {
      try {
         Iterator var1 = Astralux.INSTANCE.getModuleManager().getAllModules().iterator();

         while(var1.hasNext()) {
            bf module = (bf)var1.next();
            if (module instanceof gq) {
               this.chunkFinder = (gq)module;
               break;
            }
         }
      } catch (Exception var3) {
         var3.printStackTrace();
      }

   }

   private void emergencyLogout(String reason) {
      mc.field_1724.method_7353(class_2561.method_43470("§c§l[EMERGENCY LOGOUT] " + reason), false);
      mc.execute(() -> {
         if (mc.field_1687 != null) {
            mc.field_1687.method_8525();
         }

         if (mc.method_1562() != null) {
            mc.method_1562().method_48296().method_10747(class_2561.method_43470("Emergency logout: " + reason));
         }

      });
      this.setEnabled(false);
   }

   @cp
   public void onTick(hm event) {
      if (mc.field_1724 != null && mc.field_1687 != null) {
         if (this.autoLogout.getValue()) {
            float currentPitch;
            if (this.logOnTotemPop.getValue()) {
               currentPitch = mc.field_1724.method_6032();
               if (currentPitch > this.lastHealth + 5.0F) {
                  mc.field_1724.method_7353(class_2561.method_43470("§c§l[SAFETY] TOTEM POPPED! LOGGING OUT!"), false);
                  this.emergencyLogout(d887("RH5mdnk1ZnhoOX5+aHh9a0VF"));
                  return;
               }

               this.lastHealth = currentPitch;
            }

            if ((double)mc.field_1724.method_6032() <= this.minHealth.getValue()) {
               mc.field_1724.method_7353(class_2561.method_43470("§c§l[SAFETY] LOW HEALTH! LOGGING OUT!"), false);
               this.emergencyLogout(d887("XH5lM3xwd3tscQ=="));
               return;
            }

            if (mc.field_1724.method_23318() < this.minHeight.getValue()) {
               mc.field_1724.method_7353(class_2561.method_43470("§c§l[SAFETY] TOO LOW! LOGGING OUT!"), false);
               this.emergencyLogout(d887("RH59M3h6YTd5dW5yaGh6eg=="));
               return;
            }

            if (mc.field_1724.method_6128() && mc.field_1724.method_23318() < this.targetHeight.getValue()) {
               currentPitch = mc.field_1724.method_36455();
               float targetPitch = -25.0F;
               if (currentPitch > targetPitch) {
                  mc.field_1724.method_36457(Math.max(targetPitch, currentPitch - 2.0F));
               }

               if (System.currentTimeMillis() - this.lastBoostTime > 2000L) {
                  this.handleAutoBoost();
               }
            } else if (mc.field_1724.method_6128() && mc.field_1724.method_23318() >= this.targetHeight.getValue()) {
               currentPitch = mc.field_1724.method_36455();
               if (currentPitch < -1.0F) {
                  mc.field_1724.method_36457(Math.min(0.0F, currentPitch + 1.0F));
               } else if (currentPitch > 1.0F) {
                  mc.field_1724.method_36457(Math.max(0.0F, currentPitch - 1.0F));
               }
            }

            if (!mc.field_1724.method_6128() && !mc.field_1724.method_24828() && mc.field_1724.method_18798().field_1351 < -0.5D) {
               mc.field_1724.method_7353(class_2561.method_43470("§c§l[SAFETY] FALLING! LOGGING OUT!"), false);
               this.emergencyLogout(d887("VnB+f317cTdvcG5zc2hqP0VNW1dWRA=="));
               return;
            }
         }

         if (this.buyDelayCounter > 0) {
            --this.buyDelayCounter;
         } else if (this.isBuyingRockets) {
            this.handleRocketBuying();
         } else if (this.chunkFinder != null && this.chunkFinder.isEnabled()) {
            long currentTime = System.currentTimeMillis();
            if (this.autoBuyRockets.getValue() && currentTime - this.lastRocketCheckTime > 5000L) {
               this.lastRocketCheckTime = currentTime;
               if (this.getRocketCount() < (229 ^ 197)) {
                  this.isBuyingRockets = true;
                  mc.field_1724.method_7353(class_2561.method_43470("§e[ElytraChunkFinder] Low on rockets, buying more..."), false);
                  mc.method_1562().method_45730(d887("cXkydX1nc2B3a3E7bnJ9dEVVAlBQREVM"));
                  this.buyDelayCounter = 20;
                  return;
               }
            }

            class_1799 chestplate = mc.field_1724.method_6118(class_1304.field_6174);
            if (chestplate.method_31574(class_1802.field_8833)) {
               if (!mc.field_1724.method_6128() && !mc.field_1724.method_24828()) {
                  mc.field_1724.field_3944.method_52787(new class_2848(mc.field_1724, class_2849.field_12982));
               } else if (mc.field_1724.method_6128()) {
                  if (this.autoNavigate.getValue() && currentTime - this.lastChunkCheckTime > 1000L) {
                     this.updateTargetChunk();
                     this.lastChunkCheckTime = currentTime;
                  }

                  if (currentTime - this.lastCorrectionTime >= 10000L) {
                     this.applyCorrection();
                     this.lastCorrectionTime = currentTime;
                  }

                  if (this.autoBoost.getValue()) {
                     this.handleAutoBoost();
                  }

                  if (this.showPath.getValue()) {
                     this.calculateFuturePath();
                  }

               }
            }
         } else {
            mc.field_1724.method_7353(class_2561.method_43470("§c[ElytraChunkFinder] ChunkFinder disabled! Stopping..."), false);
            this.setEnabled(false);
         }
      }
   }

   private int getRocketCount() {
      if (mc.field_1724 == null) {
         return 0;
      } else {
         int count = 0;

         for(int i = 0; i < mc.field_1724.method_31548().method_5439(); ++i) {
            class_1799 stack = mc.field_1724.method_31548().method_5438(i);
            if (stack.method_31574(class_1802.field_8639)) {
               count += stack.method_7947();
            }
         }

         return count;
      }
   }

   private void handleRocketBuying() {
      if (mc.field_1724 != null && mc.field_1687 != null) {
         class_1703 screenHandler = mc.field_1724.field_7512;
         if (screenHandler instanceof class_1707) {
            class_1707 container = (class_1707)screenHandler;
            class_1799 confirmStack;
            if (container.method_17388() == 6) {
               confirmStack = null;
               int cheapestSlot = -1;
               double cheapestPrice = Double.MAX_VALUE;

               for(int i = 0; i < 44; ++i) {
                  class_1799 stack = container.method_7611(i).method_7677();
                  if (stack.method_31574(class_1802.field_8639)) {
                     double price = this.parseTooltipPrice(stack);
                     if (price != -1.0D && price < cheapestPrice && price <= this.maxRocketPrice.getValue()) {
                        cheapestPrice = price;
                        cheapestSlot = i;
                     }
                  }
               }

               if (cheapestSlot != -1) {
                  mc.field_1724.method_7353(class_2561.method_43470("§a[ElytraChunkFinder] Buying rockets for $" + (int)cheapestPrice), false);
                  mc.field_1761.method_2906(container.field_7763, cheapestSlot, 0, class_1713.field_7790, mc.field_1724);
                  this.buyDelayCounter = 62 ^ 52;
               } else {
                  mc.field_1724.method_7353(class_2561.method_43470("§c[ElytraChunkFinder] No rockets found under $" + (int)this.maxRocketPrice.getValue()), false);
                  mc.field_1724.method_7346();
                  this.isBuyingRockets = false;
                  this.buyDelayCounter = 131 ^ 171;
               }
            } else if (container.method_17388() == 3) {
               confirmStack = container.method_7611(64 ^ 77).method_7677();
               if (confirmStack.method_31574(class_1802.field_8639)) {
                  mc.field_1761.method_2906(container.field_7763, 203 ^ 196, 0, class_1713.field_7790, mc.field_1724);
                  mc.field_1724.method_7353(class_2561.method_43470("§a[ElytraChunkFinder] Purchase confirmed!"), false);
               }

               mc.field_1724.method_7346();
               this.isBuyingRockets = false;
               this.buyDelayCounter = 40;
            }

         }
      }
   }

   private double parseTooltipPrice(class_1799 stack) {
      if (mc.field_1724 == null) {
         return -1.0D;
      } else {
         List<class_2561> tooltip = stack.method_7950(class_9635.field_51353, mc.field_1724, class_1836.field_41070);
         Iterator var3 = tooltip.iterator();

         while(var3.hasNext()) {
            class_2561 line = (class_2561)var3.next();
            String text = line.getString();
            if (text.matches("(?i).*price\\s*:\\s*\\$.*")) {
               String cleaned = text.replaceAll("[,$]", "");
               Matcher matcher = Pattern.compile("([\\d]+(?:\\.[\\d]+)?)\\s*([KMB])?", 2).matcher(cleaned);
               if (matcher.find()) {
                  String number = matcher.group(1);
                  String suffix = matcher.group(2) != null ? matcher.group(2).toUpperCase() : "";
                  return this.parsePrice(number + suffix);
               }
            }
         }

         return -1.0D;
      }
   }

   private double parsePrice(String price) {
      if (price != null && !price.isEmpty()) {
         String cleaned = price.trim().toUpperCase();
         double multiplier = 1.0D;
         if (cleaned.endsWith("B")) {
            multiplier = 1.0E9D;
            cleaned = cleaned.substring(0, cleaned.length() - 1);
         } else if (cleaned.endsWith("M")) {
            multiplier = 1000000.0D;
            cleaned = cleaned.substring(0, cleaned.length() - 1);
         } else if (cleaned.endsWith("K")) {
            multiplier = 1000.0D;
            cleaned = cleaned.substring(0, cleaned.length() - 1);
         }

         try {
            return Double.parseDouble(cleaned) * multiplier;
         } catch (NumberFormatException var6) {
            return -1.0D;
         }
      } else {
         return -1.0D;
      }
   }

   private void applyCorrection() {
      if (mc.field_1724 != null) {
         if (!(mc.field_1724.method_23318() < this.targetHeight.getValue())) {
            class_243 playerPos = mc.field_1724.method_19538();
            double radYaw = Math.toRadians((double)this.initialYaw);
            class_243 lookDir = new class_243(-Math.sin(radYaw), 0.0D, Math.cos(radYaw));
            double lookAheadDist = this.lookAhead.getValue();
            boolean obstacleDetected = false;

            for(double dist = 10.0D; dist <= lookAheadDist; dist += 10.0D) {
               class_243 checkPos = playerPos.method_1019(lookDir.method_1021(dist));
               class_2338 blockPos = class_2338.method_49638(checkPos);

               for(int y = -5; y <= 5; ++y) {
                  class_2338 checkBlock = blockPos.method_10069(0, y, 0);
                  if (mc.field_1687 != null && mc.field_1687.method_8320(checkBlock).method_26212(mc.field_1687, checkBlock)) {
                     obstacleDetected = true;
                     break;
                  }
               }

               if (obstacleDetected) {
                  break;
               }
            }

            if (obstacleDetected) {
               boolean leftClear = this.checkSide(playerPos, this.initialYaw - 90.0F);
               boolean rightClear = this.checkSide(playerPos, this.initialYaw + 90.0F);
               if (leftClear && !rightClear) {
                  this.initialYaw = this.normalizeYaw(this.initialYaw - 20.0F);
               } else if (rightClear && !leftClear) {
                  this.initialYaw = this.normalizeYaw(this.initialYaw + 20.0F);
               } else {
                  this.initialYaw = this.normalizeYaw(this.initialYaw + (float)(Math.random() > 0.5D ? 20 : -20));
               }
            }

            float naturalYawVariance = (float)((Math.random() - 0.5D) * 2.0D);
            mc.field_1724.method_36456(this.initialYaw + naturalYawVariance);
            mc.field_1724.method_36457(0.0F);
         }
      }
   }

   private boolean checkSide(class_243 playerPos, float yaw) {
      if (mc.field_1687 == null) {
         return true;
      } else {
         double radYaw = Math.toRadians((double)yaw);
         class_243 direction = new class_243(-Math.sin(radYaw), 0.0D, Math.cos(radYaw));

         for(double dist = 10.0D; dist <= 40.0D; dist += 10.0D) {
            class_243 checkPos = playerPos.method_1019(direction.method_1021(dist));
            class_2338 blockPos = class_2338.method_49638(checkPos);

            for(int y = -144 ^ 139; y <= 5; ++y) {
               if (mc.field_1687.method_8320(blockPos.method_10069(0, y, 0)).method_26212(mc.field_1687, blockPos.method_10069(0, y, 0))) {
                  return false;
               }
            }
         }

         return true;
      }
   }

   private float normalizeYaw(float yaw) {
      while(yaw > 180.0F) {
         yaw -= 360.0F;
      }

      while(yaw < -180.0F) {
         yaw += 360.0F;
      }

      return yaw;
   }

   private void updateTargetChunk() {
      if (mc.field_1724 != null && mc.field_1687 != null) {
         class_1923 playerChunk = new class_1923(class_2338.method_49638(mc.field_1724.method_19538()));
         if (this.chunkFinder != null) {
            try {
               Field flaggedField = gq.class.getDeclaredField(d887("dn1zdHNwclRwbHRwbw=="));
               flaggedField.setAccessible(true);
               Set<class_1923> flaggedChunks = (Set)flaggedField.get(this.chunkFinder);
               Field flaggedBlocksField = gq.class.getDeclaredField(d887("dn1zdHNwclV0dnlwbw=="));
               flaggedBlocksField.setAccessible(true);
               ConcurrentMap<class_1923, Set<class_2338>> flaggedBlocks = (ConcurrentMap)flaggedBlocksField.get(this.chunkFinder);
               if (flaggedChunks != null && !flaggedChunks.isEmpty()) {
                  Iterator var6 = flaggedChunks.iterator();

                  while(true) {
                     while(true) {
                        class_1923 chunk;
                        double dist;
                        do {
                           do {
                              if (!var6.hasNext()) {
                                 return;
                              }

                              chunk = (class_1923)var6.next();
                           } while(this.visitedChunks.contains(chunk));

                           dist = Math.sqrt(Math.pow((double)(playerChunk.field_9181 - chunk.field_9181), 2.0D) + Math.pow((double)(playerChunk.field_9180 - chunk.field_9180), 2.0D));
                        } while(!(dist < this.searchRadius.getValue()));

                        int radiusChunks;
                        if (this.ignoreAdjacentChunks.getValue()) {
                           int nearbyCount = 0;
                           radiusChunks = (int)this.chunkRadiusCheck.getValue();
                           Iterator var12 = flaggedChunks.iterator();

                           while(var12.hasNext()) {
                              class_1923 otherChunk = (class_1923)var12.next();
                              if (!otherChunk.equals(chunk)) {
                                 int chunkDistX = Math.abs(chunk.field_9181 - otherChunk.field_9181);
                                 int chunkDistZ = Math.abs(chunk.field_9180 - otherChunk.field_9180);
                                 if (chunkDistX <= radiusChunks && chunkDistZ <= radiusChunks) {
                                    ++nearbyCount;
                                 }
                              }
                           }

                           if (nearbyCount > 0) {
                              mc.field_1724.method_7353(class_2561.method_43470("§e[ElytraChunkFinder] Ignoring chunk " + chunk.field_9181 + ", " + chunk.field_9180 + " (" + nearbyCount + " chunks within " + radiusChunks + " chunk radius)"), false);
                              this.visitedChunks.add(chunk);
                              continue;
                           }
                        }

                        Set<class_2338> susBlocks = (Set)flaggedBlocks.get(chunk);
                        radiusChunks = susBlocks != null ? susBlocks.size() : 0;
                        if ((double)radiusChunks <= this.maxSusBlocks.getValue()) {
                           this.visitedChunks.add(chunk);
                           mc.field_1724.method_7353(class_2561.method_43470("§c§l[ElytraChunkFinder] BASE DETECTED AT CHUNK " + chunk.field_9181 + ", " + chunk.field_9180 + "!"), false);
                           mc.field_1724.method_7353(class_2561.method_43470("§c§lSuspicious blocks: " + radiusChunks + " (max: " + (int)this.maxSusBlocks.getValue() + ")"), false);
                           mc.field_1724.method_7353(class_2561.method_43470("§c§lLOGGING OUT FOR SAFETY!"), false);
                           mc.execute(() -> {
                              if (mc.field_1687 != null) {
                                 mc.field_1687.method_8525();
                              }

                              if (mc.method_1562() != null) {
                                 mc.method_1562().method_48296().method_10747(class_2561.method_43470(d887("UnBhdjRxc2N9em5+eD0zP2FUVkwESUlAR1xe")));
                              }

                           });
                           this.setEnabled(false);
                           return;
                        }

                        mc.field_1724.method_7353(class_2561.method_43470("§e[ElytraChunkFinder] Ignoring chunk " + chunk.field_9181 + ", " + chunk.field_9180 + " (" + radiusChunks + " sus blocks > " + (int)this.maxSusBlocks.getValue() + ")"), false);
                        this.visitedChunks.add(chunk);
                     }
                  }
               }
            } catch (Exception var16) {
               var16.printStackTrace();
            }
         }

      }
   }

   private void calculateFuturePath() {
      if (mc.field_1724 != null && mc.field_1687 != null) {
         this.futurePathBlocks.clear();
         class_243 currentPos = mc.field_1724.method_19538();
         double radYaw = Math.toRadians((double)this.initialYaw);
         class_243 direction = new class_243(-Math.sin(radYaw), 0.0D, Math.cos(radYaw));

         for(int i = 0; i < 50; ++i) {
            class_243 futurePos = currentPos.method_1019(direction.method_1021((double)(i * 3)));
            this.futurePathBlocks.add(class_2338.method_49638(futurePos));
         }

      }
   }

   private void handleAutoBoost() {
      if (mc.field_1724 != null && mc.field_1761 != null) {
         if (!this.isCurrentlyBoosting) {
            long currentTime = System.currentTimeMillis();
            long intervalMs = (long)(this.boostInterval.getValue() * 1000.0D);
            if (currentTime - this.lastBoostTime >= intervalMs) {
               int rocketSlot = this.findRocketSlot();
               if (rocketSlot != -1) {
                  this.isCurrentlyBoosting = true;
                  int previousSlot = mc.field_1724.method_31548().field_7545;
                  int randomDelay = (149 ^ 167) + (int)(Math.random() * 100.0D);
                  (new Thread(() -> {
                     try {
                        Thread.sleep((long)randomDelay);
                        mc.execute(() -> {
                           if (mc.field_1724 != null && mc.field_1724.method_6128() && mc.field_1761 != null) {
                              mc.field_1724.method_31548().field_7545 = rocketSlot;
                              (new Thread(() -> {
                                 try {
                                    Thread.sleep((long)this.rocketUseDelay.getValue());
                                    mc.execute(() -> {
                                       if (mc.field_1724 != null && mc.field_1724.method_6128() && mc.field_1761 != null) {
                                          mc.field_1724.method_6104(class_1268.field_5808);
                                          mc.field_1761.method_2919(mc.field_1724, class_1268.field_5808);
                                          (new Thread(() -> {
                                             try {
                                                Thread.sleep((long)(100 + (int)(Math.random() * 100.0D)));
                                                mc.execute(() -> {
                                                   if (mc.field_1724 != null) {
                                                      mc.field_1724.method_31548().field_7545 = previousSlot;
                                                   }

                                                   this.isCurrentlyBoosting = false;
                                                });
                                             } catch (InterruptedException var3) {
                                                Thread.currentThread().interrupt();
                                                this.isCurrentlyBoosting = false;
                                             }

                                          })).start();
                                       } else {
                                          this.isCurrentlyBoosting = false;
                                       }
                                    });
                                 } catch (InterruptedException var3) {
                                    Thread.currentThread().interrupt();
                                    this.isCurrentlyBoosting = false;
                                 }

                              })).start();
                           } else {
                              this.isCurrentlyBoosting = false;
                           }
                        });
                     } catch (InterruptedException var5) {
                        Thread.currentThread().interrupt();
                        this.isCurrentlyBoosting = false;
                     }

                  })).start();
                  this.lastBoostTime = currentTime;
               }
            }
         }
      }
   }

   private int findRocketSlot() {
      if (mc.field_1724 == null) {
         return -1;
      } else {
         int i;
         class_1799 stack;
         for(i = 0; i < (140 ^ 133); ++i) {
            stack = mc.field_1724.method_31548().method_5438(i);
            if (stack.method_31574(class_1802.field_8639)) {
               return i;
            }
         }

         for(i = 9; i < (79 ^ 107); ++i) {
            stack = mc.field_1724.method_31548().method_5438(i);
            if (stack.method_31574(class_1802.field_8639)) {
               for(int hotbarSlot = 0; hotbarSlot < 9; ++hotbarSlot) {
                  if (mc.field_1724.method_31548().method_5438(hotbarSlot).method_7960()) {
                     mc.field_1724.method_31548().method_5447(hotbarSlot, stack.method_7972());
                     mc.field_1724.method_31548().method_5447(i, class_1799.field_8037);
                     return hotbarSlot;
                  }
               }

               class_1799 temp = mc.field_1724.method_31548().method_5438(8);
               mc.field_1724.method_31548().method_5447(8, stack.method_7972());
               mc.field_1724.method_31548().method_5447(i, temp);
               return 19 ^ 27;
            }
         }

         return -1;
      }
   }

   @cp
   public void onRender3D(bg event) {
      if (this.showPath.getValue() && !this.futurePathBlocks.isEmpty()) {
         if (mc != null && mc.field_1724 != null && mc.field_1687 != null) {
            class_4184 cam = iq.getCamera();
            if (cam != null) {
               class_243 camPos = iq.getCameraPos();
               class_4587 matrices = event.matrixStack;
               matrices.method_22903();
               matrices.method_22907(class_7833.field_40714.rotationDegrees(cam.method_19329()));
               matrices.method_22907(class_7833.field_40716.rotationDegrees(cam.method_19330() + 180.0F));
               matrices.method_22904(-camPos.field_1352, -camPos.field_1351, -camPos.field_1350);

               for(int i = 0; i < this.futurePathBlocks.size(); ++i) {
                  class_2338 pos = (class_2338)this.futurePathBlocks.get(i);
                  float brightness = 1.0F - (float)i / (float)this.futurePathBlocks.size();
                  int alpha = (int)(brightness * 150.0F);
                  Color color = new Color(0, 142 ^ 70, 255, alpha);
                  iq.renderFilledBox(matrices, (float)pos.method_10263() + 0.3F, (float)pos.method_10264() + 0.3F, (float)pos.method_10260() + 0.3F, (float)pos.method_10263() + 0.7F, (float)pos.method_10264() + 0.7F, (float)pos.method_10260() + 0.7F, color);
               }

               matrices.method_22909();
            }
         }
      }
   }

   // $FF: synthetic method
   private static String d887(String var0) {
      byte[] var1 = Base64.getDecoder().decode(var0);
      byte[] var2 = new byte[var1.length];

      for(int var3 = 0; var3 < var1.length; ++var3) {
         var2[var3] = (byte)(var1[var3] ^ 8208 + var3 & (72 ^ 183));
      }

      return new String(var2);
   }
}
