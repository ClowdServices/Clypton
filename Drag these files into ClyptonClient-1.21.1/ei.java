import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.awt.Color;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.class_1268;
import net.minecraft.class_1657;
import net.minecraft.class_1713;
import net.minecraft.class_1799;
import net.minecraft.class_1802;
import net.minecraft.class_2246;
import net.minecraft.class_2248;
import net.minecraft.class_2338;
import net.minecraft.class_2350;
import net.minecraft.class_243;
import net.minecraft.class_2561;
import net.minecraft.class_2818;
import net.minecraft.class_2960;
import net.minecraft.class_4184;
import net.minecraft.class_4587;
import net.minecraft.class_746;
import net.minecraft.class_7833;
import net.minecraft.class_7923;
import net.minecraft.class_9334;
import net.minecraft.class_2183.class_2184;

public final class ei extends bf {
   private final cu mode;
   private final ff playerDetection = new ff(db.of(d277("OAULEgkfTisVBRcQABwZGQ==")), true);
   private final gn playerRange = new gn(db.of(d277("OAULEgkfTj0RHxUW")), 10.0D, 200.0D, 100.0D, 10.0D);
   private final il avoidBlocks = new il(db.of(d277("KR8FAghNLAMfEhkA")));
   private final il detectionBlocks = new il(db.of(d277("LAweDg8ZBwAeUTAfGxYdBA==")));
   private final ff totemCheck = new ff(db.of(d277("PAYeDgFNPgAAUT4cExoDAw==")), true);
   private final ff showPreview = new ff(db.of(d277("OwEFHEw9HAoGGBcE")), true);
   private final ff ignoreCaves = new ff(db.of(d277("IQ4EBB4ITiwRBxcA")), true);
   private final ff autoEat = new ff(db.of(d277("KRweBEwoDxs=")), true);
   private final gn eatAt = new gn(db.of(d277("LQgeSy0Z")), 1.0D, 20.0D, 14.0D, 1.0D);
   private final ff autoMend = new ff(db.of(d277("KRweBEwgCwEU")), true);
   private final gn mendThreshold = new gn(db.of(d277("JQwED0w5Bh0VAhocGBFWUg==")), 1.0D, 100.0D, 30.0D, 1.0D);
   private final ff autoTool = new ff(db.of(d277("KRweBEw5AQAc")), true);
   private final ff refillPick = new ff(db.of(d277("OgwMAgABTj8ZEhkSDBA=")), true);
   private final Map<class_2248, Integer> blockThresholds = new ConcurrentHashMap();
   private int lastTotemCount = 0;
   private ht currentDirection;
   private class_243 lastPosition;
   private List<class_2338> previewBlocks = new ArrayList();
   private float targetYaw = 0.0F;
   private float targetPitch = 2.0F;
   private class_2338 currentTarget = null;
   private int stuckTicks = 0;
   private int turningCooldown = 0;
   private int eatCooldown = 0;
   private int mendCooldown = 0;
   private boolean isMending = false;
   private int previousSlot = -1;
   private static final String CONFIG_FILE = "tunnelbasefinder_thresholds.json";
   private final Gson gson = (new GsonBuilder()).setPrettyPrinting().create();

   public ei() {
      super(db.of(d277("PBwEBQkBTi0RAhdTMhwYEx0L")), db.of(d277("LgAEDx9NDA4DFAFTFgxWExEeHRISGl4L9e/s5uj2")), -1, hk.DONUT);
      this.mode = new cu(db.of(d277("JQYODg==")), ie.ONE_BY_ONE, ie.class);
      this.loadThresholds();
      ab[] var10001 = new ab[]{this.mode, this.playerDetection, this.playerRange, this.avoidBlocks, this.detectionBlocks, this.totemCheck, this.showPreview, this.ignoreCaves, null, null, null, null, null, null};
      var10001[158 ^ 150] = this.autoEat;
      var10001[9] = this.eatAt;
      var10001[10] = this.autoMend;
      var10001[11] = this.mendThreshold;
      var10001[12] = this.autoTool;
      var10001[83 ^ 94] = this.refillPick;
      this.addSettings(var10001);
   }

   private void loadThresholds() {
      try {
         Path configPath = Paths.get(getConfigDir(), d277("HBwEBQkBDA4DFBQaGhETBScNEgkZDhYQ7OXxre726ek="));
         if (!Files.exists(configPath, new LinkOption[0])) {
            return;
         }

         String json = Files.readString(configPath);
         JsonObject jsonObject = JsonParser.parseString(json).getAsJsonObject();
         Iterator var4 = jsonObject.keySet().iterator();

         while(var4.hasNext()) {
            String blockId = (String)var4.next();

            try {
               class_2960 identifier = class_2960.method_60654(blockId);
               class_2248 block = (class_2248)class_7923.field_41175.method_10223(identifier);
               if (block != null && block != class_2246.field_10124) {
                  this.blockThresholds.put(block, jsonObject.get(blockId).getAsInt());
               }
            } catch (Exception var8) {
            }
         }
      } catch (Exception var9) {
      }

   }

   public void saveThresholds() {
      try {
         Path configDir = Paths.get(getConfigDir());
         if (!Files.exists(configDir, new LinkOption[0])) {
            Files.createDirectories(configDir);
         }

         JsonObject jsonObject = new JsonObject();
         Iterator var3 = this.blockThresholds.entrySet().iterator();

         while(var3.hasNext()) {
            Entry<class_2248, Integer> entry = (Entry)var3.next();
            class_2960 identifier = class_7923.field_41175.method_10221((class_2248)entry.getKey());
            if (identifier != null) {
               jsonObject.addProperty(identifier.toString(), (Number)entry.getValue());
            }
         }

         Files.writeString(Paths.get(getConfigDir(), d277("HBwEBQkBDA4DFBQaGhETBScNEgkZDhYQ7OXxre726ek=")), this.gson.toJson((JsonElement)jsonObject), new OpenOption[0]);
      } catch (Exception var6) {
      }

   }

   private static String getConfigDir() {
      return FabricLoader.getInstance().getGameDir().resolve("config/ClyptonClient").toString();
   }

   public void openDetectionGui() {
      if (mc != null) {
         mc.method_1507(new ef(this));
      }

   }

   public Map<class_2248, Integer> getBlockThresholds() {
      return this.blockThresholds;
   }

   public void onEnable() {
      super.onEnable();
      if (mc.field_1724 != null && mc.field_1687 != null) {
         if (!this.hasPickaxe()) {
            mc.field_1724.method_7353(class_2561.method_43470("§c[TBF] No pickaxe in hotbar!"), false);
            this.setEnabled(false);
         } else {
            this.currentDirection = this.getInitialDirection();
            this.targetYaw = this.calculateYawFromDirection(this.currentDirection);
            this.targetPitch = 2.0F;
            this.lastPosition = mc.field_1724.method_19538();
            this.previewBlocks.clear();
            this.lastTotemCount = this.countTotems();
            this.stuckTicks = 0;
            this.turningCooldown = 0;
            this.eatCooldown = 0;
            this.mendCooldown = 0;
            this.isMending = false;
            this.previousSlot = -1;
            this.currentTarget = null;
            mc.field_1724.method_7353(class_2561.method_43470("§a[TBF] Started! Direction: " + String.valueOf(this.currentDirection)), false);
         }
      } else {
         this.setEnabled(false);
      }
   }

   public void onDisable() {
      super.onDisable();
      if (mc != null) {
         mc.field_1690.field_1913.method_23481(false);
         mc.field_1690.field_1849.method_23481(false);
         mc.field_1690.field_1894.method_23481(false);
         mc.field_1690.field_1886.method_23481(false);
         if (mc.field_1761 != null) {
            mc.field_1761.method_2925();
         }

         if (this.isMending && this.previousSlot != -1) {
            mc.field_1724.method_31548().field_7545 = this.previousSlot;
         }

         this.saveThresholds();
      }
   }

   private boolean hasPickaxe() {
      for(int i = 0; i < 9; ++i) {
         class_1799 stack = mc.field_1724.method_31548().method_5438(i);
         if (!stack.method_7960() && stack.method_7909().toString().contains(d277("GAAJAA0VCw=="))) {
            return true;
         }
      }

      return false;
   }

   private int countTotems() {
      if (mc.field_1724 == null) {
         return 0;
      } else {
         int count = 0;

         for(int i = 0; i < mc.field_1724.method_31548().method_5439(); ++i) {
            class_1799 stack = mc.field_1724.method_31548().method_5438(i);
            if (stack.method_7909() == class_1802.field_8288) {
               count += stack.method_7947();
            }
         }

         return count;
      }
   }

   @cp
   public void onTick(hm event) {
      if (mc.field_1724 != null && mc.field_1687 != null) {
         if (this.turningCooldown > 0) {
            --this.turningCooldown;
         }

         if (this.eatCooldown > 0) {
            --this.eatCooldown;
         }

         if (this.mendCooldown > 0) {
            --this.mendCooldown;
         }

         if (this.autoEat.getValue() && this.eatCooldown == 0) {
            this.handleAutoEat();
         }

         if (this.autoMend.getValue() && this.mendCooldown == 0) {
            this.handleAutoMend();
         }

         if (this.refillPick.getValue()) {
            this.handleRefillPickaxe();
         }

         if (this.totemCheck.getValue()) {
            int currentTotems = this.countTotems();
            if (currentTotems < this.lastTotemCount) {
               int var9 = this.lastTotemCount - currentTotems;
               this.logout("TOTEM POPPED! (" + var9 + " used)");
               return;
            }

            this.lastTotemCount = currentTotems;
         }

         if (this.playerDetection.getValue()) {
            Iterator var6 = mc.field_1687.method_18456().iterator();

            while(var6.hasNext()) {
               class_1657 player = (class_1657)var6.next();
               if (player != mc.field_1724) {
                  double dist = mc.field_1724.method_5858(player);
                  if (dist <= this.playerRange.getValue() * this.playerRange.getValue()) {
                     String var10001 = player.method_5477().getString();
                     this.logout("PLAYER FOUND: " + var10001 + " at " + (int)Math.sqrt(dist) + " blocks");
                     return;
                  }
               }
            }
         }

         this.checkBlockDetection();
         if (this.turningCooldown <= 0) {
            List<class_2338> targetBlocks = this.getTargetBlocks();
            if (this.showPreview.getValue()) {
               this.previewBlocks = targetBlocks;
            }

            if (this.hasDangerousBlocks(targetBlocks)) {
               this.handleDangerAhead();
            } else if (this.ignoreCaves.getValue() && this.isCaveAhead(targetBlocks)) {
               this.handleCaveAhead();
            } else {
               this.smoothRotate();
               this.centerPlayer();
               mc.field_1690.field_1894.method_23481(true);
               class_2338 targetBlock = this.findBlockToMine(targetBlocks);
               if (targetBlock != null) {
                  this.mineBlock(targetBlock);
               } else {
                  mc.field_1690.field_1886.method_23481(false);
                  this.currentTarget = null;
               }

               this.checkIfStuck();
            }
         }
      } else {
         this.setEnabled(false);
      }
   }

   private List<class_2338> getTargetBlocks() {
      List<class_2338> blocks = new ArrayList();
      class_2338 playerPos = mc.field_1724.method_24515();
      class_2350 facing = this.getMinecraftDirection(this.currentDirection);
      ie mode = (ie)this.mode.getValue();

      for(int dist = 1; dist <= 2; ++dist) {
         class_2338 center = playerPos.method_10079(facing, dist);
         if (mode == ie.ONE_BY_ONE) {
            blocks.add(center);
            if (!mc.field_1724.method_20232()) {
               blocks.add(center.method_10084());
            }
         } else {
            class_2350 right = facing.method_10170();

            for(int y = 0; y <= 2; ++y) {
               for(int x = -1; x <= 1; ++x) {
                  blocks.add(center.method_10079(class_2350.field_11036, y).method_10079(right, x));
               }
            }
         }
      }

      return blocks;
   }

   private boolean hasDangerousBlocks(List<class_2338> blocks) {
      Iterator var2 = blocks.iterator();

      class_2248 block;
      do {
         if (!var2.hasNext()) {
            return false;
         }

         class_2338 pos = (class_2338)var2.next();
         block = mc.field_1687.method_8320(pos).method_26204();
      } while(!this.avoidBlocks.hasBlock(block));

      return true;
   }

   private boolean isCaveAhead(List<class_2338> blocks) {
      class_2338 playerPos = mc.field_1724.method_24515();
      class_2350 facing = this.getMinecraftDirection(this.currentDirection);

      for(int dist = 2; dist <= 4; ++dist) {
         class_2338 checkPos = playerPos.method_10079(facing, dist);
         int airCount = 0;

         for(int x = -1; x <= 1; ++x) {
            for(int y = -1; y <= 2; ++y) {
               for(int z = -1; z <= 1; ++z) {
                  class_2338 pos = checkPos.method_10069(x, y, z);
                  if (mc.field_1687.method_8320(pos).method_26215()) {
                     ++airCount;
                  }
               }
            }
         }

         if (airCount > (216 ^ 204)) {
            return true;
         }
      }

      return false;
   }

   private void handleDangerAhead() {
      mc.field_1690.field_1894.method_23481(false);
      mc.field_1690.field_1886.method_23481(false);
      ht safeDir = this.findSafeDirection();
      if (safeDir != null) {
         mc.field_1724.method_7353(class_2561.method_43470("§e[TBF] Avoiding danger, turning " + safeDir.name().toLowerCase()), false);
         this.turnToDirection(safeDir);
      } else {
         ht diagonal = this.tryDiagonal();
         if (diagonal != null) {
            mc.field_1724.method_7353(class_2561.method_43470("§e[TBF] Going diagonal to avoid danger"), false);
            this.turnToDirection(diagonal);
         } else {
            this.logout(d277("OxwYGQMYAAsVFVIRDVUSFhYeHwldXTAQoPLj5eGl9ub84arq+uzn4/Hz/va6"));
         }
      }

   }

   private void handleCaveAhead() {
      mc.field_1690.field_1894.method_23481(false);
      mc.field_1690.field_1886.method_23481(false);
      ht safeDir = this.findSafeDirection();
      if (safeDir != null) {
         mc.field_1724.method_7353(class_2561.method_43470("§e[TBF] Cave detected, turning " + safeDir.name().toLowerCase()), false);
         this.turnToDirection(safeDir);
      } else {
         this.logout(d277("KwgcDkweFxwEFB9TEBACEhsNHx9dXTAQoPLj5eGl8vLm5+/nrP3v+/ix8+X1/Pr2+vX/tQ=="));
      }

   }

   private ht findSafeDirection() {
      ht right = this.getRightDirection(this.currentDirection);
      ht left = this.getLeftDirection(this.currentDirection);
      if (this.isSafePath(right)) {
         return right;
      } else {
         return this.isSafePath(left) ? left : null;
      }
   }

   private ht tryDiagonal() {
      ht right = this.getRightDirection(this.currentDirection);
      ht left = this.getLeftDirection(this.currentDirection);
      if (this.isSafePath(right)) {
         return right;
      } else {
         return this.isSafePath(left) ? left : null;
      }
   }

   private boolean isSafePath(ht dir) {
      class_2338 playerPos = mc.field_1724.method_24515();
      class_2350 mcDir = this.getMinecraftDirection(dir);
      ie mode = (ie)this.mode.getValue();
      int scanDistance = mode == ie.ONE_BY_ONE ? 19 ^ 25 : 12;
      int width = mode == ie.ONE_BY_ONE ? 1 : 2;

      int dist;
      class_2338 checkPos;
      int x;
      int z;
      for(dist = 1; dist <= scanDistance; ++dist) {
         checkPos = playerPos.method_10079(mcDir, dist);
         class_2350 perp = mcDir.method_10170();

         for(x = -width; x <= width; ++x) {
            class_2338 sidePos = checkPos.method_10079(perp, x);
            z = mode == ie.ONE_BY_ONE ? 1 : 2;

            for(int y = -1; y <= z; ++y) {
               class_2248 block = mc.field_1687.method_8320(sidePos.method_10086(y)).method_26204();
               if (this.avoidBlocks.hasBlock(block)) {
                  return false;
               }
            }

            class_2248 floor = mc.field_1687.method_8320(sidePos.method_10074()).method_26204();
            if (floor == class_2246.field_10124 || this.avoidBlocks.hasBlock(floor)) {
               return false;
            }
         }
      }

      if (this.ignoreCaves.getValue()) {
         for(dist = 2; dist <= 6; ++dist) {
            checkPos = playerPos.method_10079(mcDir, dist);
            int airCount = 0;

            for(x = -2; x <= 2; ++x) {
               for(int y = -1; y <= 3; ++y) {
                  for(z = -2; z <= 2; ++z) {
                     if (mc.field_1687.method_8320(checkPos.method_10069(x, y, z)).method_26215()) {
                        ++airCount;
                     }
                  }
               }
            }

            if (airCount > 40) {
               return false;
            }
         }
      }

      return true;
   }

   private void turnToDirection(ht newDir) {
      this.currentDirection = newDir;
      this.targetYaw = this.calculateYawFromDirection(newDir);
      this.turningCooldown = 49 ^ 47;
      this.stuckTicks = 0;
   }

   private void smoothRotate() {
      float currentYaw = mc.field_1724.method_36454();

      float yawDiff;
      for(yawDiff = this.targetYaw - currentYaw; yawDiff > 180.0F; yawDiff -= 360.0F) {
      }

      while(yawDiff < -180.0F) {
         yawDiff += 360.0F;
      }

      float rotationSpeed = 15.0F;
      if (Math.abs(yawDiff) > rotationSpeed) {
         mc.field_1724.method_36456(currentYaw + Math.signum(yawDiff) * rotationSpeed);
      } else {
         mc.field_1724.method_36456(this.targetYaw);
      }

      mc.field_1724.method_36457(this.targetPitch);
   }

   private void centerPlayer() {
      if (this.turningCooldown <= 0 && this.currentTarget == null) {
         double x = mc.field_1724.method_23317();
         double z = mc.field_1724.method_23321();
         double centerX = Math.floor(x) + 0.5D;
         double centerZ = Math.floor(z) + 0.5D;
         double threshold = 0.25D;
         boolean moveLeft = false;
         boolean moveRight = false;
         switch(this.currentDirection.ordinal()) {
         case 0:
            if (x - centerX > threshold) {
               moveLeft = true;
            } else if (x - centerX < -threshold) {
               moveRight = true;
            }
            break;
         case 1:
            if (z - centerZ > threshold) {
               moveLeft = true;
            } else if (z - centerZ < -threshold) {
               moveRight = true;
            }
            break;
         case 2:
            if (x - centerX > threshold) {
               moveRight = true;
            } else if (x - centerX < -threshold) {
               moveLeft = true;
            }
            break;
         case 3:
            if (z - centerZ > threshold) {
               moveRight = true;
            } else if (z - centerZ < -threshold) {
               moveLeft = true;
            }
         }

         mc.field_1690.field_1913.method_23481(moveLeft);
         mc.field_1690.field_1849.method_23481(moveRight);
      }
   }

   private class_2338 findBlockToMine(List<class_2338> blocks) {
      Iterator var2 = blocks.iterator();

      class_2338 pos;
      do {
         if (!var2.hasNext()) {
            return null;
         }

         pos = (class_2338)var2.next();
      } while(mc.field_1687.method_8320(pos).method_26215());

      return pos;
   }

   private void mineBlock(class_2338 pos) {
      if (!this.isMending) {
         if (this.autoTool.getValue()) {
            class_2248 block = mc.field_1687.method_8320(pos).method_26204();
            int bestSlot = this.findBestTool(block);
            if (bestSlot != -1) {
               mc.field_1724.method_31548().field_7545 = bestSlot;
            }
         }

         class_243 center = new class_243((double)pos.method_10263() + 0.5D, (double)pos.method_10264() + 0.5D, (double)pos.method_10260() + 0.5D);
         mc.field_1724.method_5702(class_2184.field_9851, center);
         class_2350 side = this.getMinecraftDirection(this.currentDirection).method_10153();
         if (mc.field_1761.method_2902(pos, side)) {
            mc.field_1713.method_3054(pos, side);
            mc.field_1724.method_6104(class_1268.field_5808);
         }

         this.currentTarget = pos;
         mc.field_1690.field_1886.method_23481(true);
      }
   }

   private int findBestTool(class_2248 block) {
      int bestSlot = 0;
      float bestSpeed = 0.0F;

      for(int i = 0; i < 9; ++i) {
         class_1799 stack = mc.field_1724.method_31548().method_5438(i);
         if (!stack.method_7960()) {
            float speed = stack.method_7924(block.method_9564());
            if (speed > bestSpeed) {
               bestSpeed = speed;
               bestSlot = i;
            }
         }
      }

      return bestSlot;
   }

   private void checkIfStuck() {
      class_243 currentPos = mc.field_1724.method_19538();
      double distMoved = currentPos.method_1022(this.lastPosition);
      if (distMoved < 0.1D) {
         ++this.stuckTicks;
      } else {
         this.stuckTicks = 0;
      }

      this.lastPosition = currentPos;
      if (this.stuckTicks > 100) {
         mc.field_1724.method_7353(class_2561.method_43470("§c[TBF] Stuck! Trying to recover..."), false);
         ht newDir = this.findSafeDirection();
         if (newDir != null) {
            this.turnToDirection(newDir);
         } else {
            this.logout(d277("Ox0fCAdNGQYEGVIdG1UFFh4cWgsdCRZe"));
         }

         this.stuckTicks = 0;
      }

   }

   private void handleAutoEat() {
      if ((double)mc.field_1724.method_7344().method_7586() <= this.eatAt.getValue()) {
         int foodSlot = this.findFood();
         if (foodSlot != -1) {
            int prev = mc.field_1724.method_31548().field_7545;
            mc.field_1724.method_31548().field_7545 = foodSlot;
            mc.field_1690.field_1904.method_23481(true);
            (new Thread(() -> {
               try {
                  Thread.sleep(1500L);
                  mc.execute(() -> {
                     mc.field_1690.field_1904.method_23481(false);
                     mc.field_1724.method_31548().field_7545 = prev;
                  });
               } catch (InterruptedException var2) {
               }

            })).start();
            this.eatCooldown = 60;
         }
      }

   }

   private int findFood() {
      for(int i = 0; i < (11 ^ 2); ++i) {
         class_1799 stack = mc.field_1724.method_31548().method_5438(i);
         if (!stack.method_7960() && stack.method_57353().method_57832(class_9334.field_50075)) {
            return i;
         }
      }

      return -1;
   }

   private void handleAutoMend() {
      if (this.isMending) {
         if (this.getLowestArmorDurability() > this.mendThreshold.getValue()) {
            this.isMending = false;
            if (this.previousSlot != -1) {
               mc.field_1724.method_31548().field_7545 = this.previousSlot;
               this.previousSlot = -1;
            }

            mc.field_1690.field_1886.method_23481(true);
            mc.field_1724.method_7353(class_2561.method_43470("§a[TBF] Mending complete!"), false);
            this.mendCooldown = 100;
         }

      } else {
         double lowestDur = this.getLowestArmorDurability();
         if (lowestDur < this.mendThreshold.getValue() && lowestDur > 0.0D) {
            int mendSlot = this.findMendingTool();
            if (mendSlot != -1 && this.hasXP()) {
               this.previousSlot = mc.field_1724.method_31548().field_7545;
               mc.field_1724.method_31548().field_7545 = mendSlot;
               this.isMending = true;
               mc.field_1690.field_1886.method_23481(false);
               class_746 var10000 = mc.field_1724;
               String var10001 = d277("TUdbDQ==");
               Object[] var10002 = new Object[]{lowestDur};
               var10000.method_7353(class_2561.method_43470("§e[TBF] Mending (armor at " + String.format(var10001, var10002) + "%)"), false);
            }
         }

      }
   }

   private double getLowestArmorDurability() {
      double lowest = 100.0D;
      Iterator var3 = mc.field_1724.method_5661().iterator();

      while(var3.hasNext()) {
         class_1799 armor = (class_1799)var3.next();
         if (!armor.method_7960() && armor.method_7963()) {
            double dur = (double)(armor.method_7936() - armor.method_7919()) / (double)armor.method_7936() * 100.0D;
            if (dur < lowest) {
               lowest = dur;
            }
         }
      }

      return lowest;
   }

   private int findMendingTool() {
      for(int i = 0; i < 9; ++i) {
         class_1799 stack = mc.field_1724.method_31548().method_5438(i);
         if (!stack.method_7960() && stack.method_7942() && stack.method_58657().toString().contains(d277("BQwEDwUDCQ=="))) {
            return i;
         }
      }

      return -1;
   }

   private boolean hasXP() {
      return mc.field_1724.field_7520 > 0 || mc.field_1724.field_7510 > 0.0F;
   }

   private void handleRefillPickaxe() {
      class_1799 current = mc.field_1724.method_6047();
      if (current.method_7909().toString().contains(d277("GAAJAA0VCw==")) && current.method_7963() && current.method_7919() >= current.method_7936() - (29 ^ 23)) {
         for(int i = 9; i < (137 ^ 173); ++i) {
            class_1799 stack = mc.field_1724.method_31548().method_5438(i);
            if (stack.method_7909().toString().contains(d277("GAAJAA0VCw==")) && stack.method_7919() < stack.method_7936() / 2) {
               mc.field_1761.method_2906(mc.field_1724.field_7512.field_7763, i, mc.field_1724.method_31548().field_7545, class_1713.field_7791, mc.field_1724);
               mc.field_1724.method_7353(class_2561.method_43470("§a[TBF] Refilled pickaxe!"), false);
               break;
            }
         }
      }

   }

   private void checkBlockDetection() {
      Map<class_2248, Integer> found = new HashMap();
      class_2338 foundPos = null;
      Iterator var3 = ft.getLoadedChunks().toList().iterator();

      while(var3.hasNext()) {
         class_2818 chunk = (class_2818)var3.next();
         Iterator var5 = chunk.method_12021().iterator();

         while(var5.hasNext()) {
            class_2338 pos = (class_2338)var5.next();
            class_2248 block = mc.field_1687.method_8320(pos).method_26204();
            if (this.detectionBlocks.hasBlock(block) && this.blockThresholds.containsKey(block)) {
               found.put(block, (Integer)found.getOrDefault(block, 0) + 1);
               foundPos = pos;
            }
         }
      }

      var3 = found.entrySet().iterator();

      Entry entry;
      do {
         if (!var3.hasNext()) {
            return;
         }

         entry = (Entry)var3.next();
      } while((Integer)entry.getValue() < (Integer)this.blockThresholds.getOrDefault(entry.getKey(), Integer.MAX_VALUE));

      String name = class_7923.field_41175.method_10221((class_2248)entry.getKey()).method_12832().replace("_", " ").toUpperCase();
      this.logout("BASE FOUND: " + String.valueOf(entry.getValue()) + " " + name + " at " + String.valueOf(foundPos));
   }

   private ht getInitialDirection() {
      float yaw = mc.field_1724.method_36454() % 360.0F;
      if (yaw < 0.0F) {
         yaw += 360.0F;
      }

      if (yaw >= 45.0F && yaw < 135.0F) {
         return ht.WEST;
      } else if (yaw >= 135.0F && yaw < 225.0F) {
         return ht.NORTH;
      } else {
         return yaw >= 225.0F && yaw < 315.0F ? ht.EAST : ht.SOUTH;
      }
   }

   private ht getRightDirection(ht dir) {
      return ht.values()[(dir.ordinal() + 1) % 4];
   }

   private ht getLeftDirection(ht dir) {
      return ht.values()[(dir.ordinal() + 3) % 4];
   }

   private class_2350 getMinecraftDirection(ht dir) {
      switch(dir.ordinal()) {
      case 0:
         return class_2350.field_11043;
      case 1:
         return class_2350.field_11034;
      case 2:
         return class_2350.field_11035;
      case 3:
         return class_2350.field_11039;
      default:
         return class_2350.field_11043;
      }
   }

   private float calculateYawFromDirection(ht dir) {
      switch(dir.ordinal()) {
      case 0:
         return 180.0F;
      case 1:
         return 270.0F;
      case 2:
         return 0.0F;
      case 3:
         return 90.0F;
      default:
         return 0.0F;
      }
   }

   private void logout(String reason) {
      if (mc.field_1724 != null) {
         mc.field_1724.method_7353(class_2561.method_43470("§c§l[TBF] " + reason), false);
         mc.field_1724.method_7353(class_2561.method_43470("§c§lLOGGING OUT FOR SAFETY!"), false);
      }

      (new Thread(() -> {
         try {
            Thread.sleep(2000L);
            mc.execute(() -> {
               if (mc.field_1687 != null) {
                  mc.field_1687.method_8525();
               }

               if (mc.method_1562() != null) {
                  mc.method_1562().method_48296().method_10747(class_2561.method_43470("Auto logout: " + reason));
               }

            });
         } catch (InterruptedException var2) {
         }

      })).start();
      this.setEnabled(false);
   }

   @cp
   public void onRender3D(bg event) {
      if (this.showPreview.getValue() && !this.previewBlocks.isEmpty() && mc.field_1724 != null) {
         class_4184 cam = iq.getCamera();
         if (cam != null) {
            class_243 camPos = iq.getCameraPos();
            class_4587 matrices = event.matrixStack;
            matrices.method_22903();
            matrices.method_22907(class_7833.field_40714.rotationDegrees(cam.method_19329()));
            matrices.method_22907(class_7833.field_40716.rotationDegrees(cam.method_19330() + 180.0F));
            matrices.method_22904(-camPos.field_1352, -camPos.field_1351, -camPos.field_1350);
            Color color = new Color(0, 255, 0, 71 ^ 35);
            Iterator var6 = this.previewBlocks.iterator();

            while(var6.hasNext()) {
               class_2338 pos = (class_2338)var6.next();
               iq.renderFilledBox(matrices, (float)pos.method_10263() + 0.1F, (float)pos.method_10264() + 0.1F, (float)pos.method_10260() + 0.1F, (float)pos.method_10263() + 0.9F, (float)pos.method_10264() + 0.9F, (float)pos.method_10260() + 0.9F, color);
            }

            matrices.method_22909();
         }
      }
   }

   // $FF: synthetic method
   private static String d277(String var0) {
      byte[] var1 = Base64.getDecoder().decode(var0);
      byte[] var2 = new byte[var1.length];

      for(int var3 = 0; var3 < var1.length; ++var3) {
         var2[var3] = (byte)(var1[var3] ^ 2408 + var3 & 255);
      }

      return new String(var2);
   }
}
