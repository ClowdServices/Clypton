import client.astralux.Astralux;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import net.minecraft.class_2248;
import net.minecraft.class_2338;
import net.minecraft.class_243;
import net.minecraft.class_2680;
import net.minecraft.class_2818;
import net.minecraft.class_4184;
import net.minecraft.class_4587;
import net.minecraft.class_7833;
import net.minecraft.class_239.class_240;
import net.minecraft.class_2902.class_2903;

public final class fq extends bf {
   private final gn clusterDistance = new gn(db.of(d168("gq62t7GjteiNo7i4rKCstQ==")), 1.0D, 32.0D, 12.0D, 1.0D);
   private final gn minBlocks = new gn(db.of(d168("jKut5IeqqKuiuQ==")), 3.0D, 50.0D, 5.0D, 1.0D);
   private final gn alpha = new gn(db.of(d168("gK6zrKQ=")), 1.0D, 255.0D, 125.0D, 1.0D);
   private final ff tracers = new ff(db.of(d168("lbCip6C0tA==")), true);
   private final il blocks = new il(db.of(d168("g6OwoeWEq6eqobg=")));
   private final Color BASE_COLOR = new Color(159 ^ 21, 22 ^ 61, 226);
   private final Map<class_2248, Color> blockColors = new ConcurrentHashMap();
   private final AtomicReference<List<eu>> cachedBasesRef = new AtomicReference(Collections.emptyList());
   private final ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor((r) -> {
      Thread t = new Thread(r, d168("g6OwoYCVl+Waqaqio6u9"));
      t.setDaemon(true);
      return t;
   });
   private ScheduledFuture<?> scanTask;

   public fq() {
      super(db.of(d168("g6OwoYCVlw==")), db.of(d168("hae3oaaytOi5pqq1qLzvsrChtqc=")), -1, hk.RENDER);
      this.addSettings(new ab[]{this.clusterDistance, this.minBlocks, this.alpha, this.tracers, this.blocks});
   }

   public Map<class_2248, Color> getBlockColors() {
      return this.blockColors;
   }

   public void onEnable() {
      super.onEnable();
      this.cachedBasesRef.set(Collections.emptyList());
      if (this.scanTask == null || this.scanTask.isCancelled()) {
         this.scanTask = this.executor.scheduleAtFixedRate(this::scanForBases, 0L, 200L, TimeUnit.MILLISECONDS);
      }

   }

   public void onDisable() {
      super.onDisable();
      if (this.scanTask != null) {
         this.scanTask.cancel(false);
         this.scanTask = null;
      }

      this.cachedBasesRef.set(Collections.emptyList());
   }

   private void scanForBases() {
      if (mc != null && mc.field_1724 != null && mc.field_1687 != null) {
         try {
            List<class_2338> foundBlocks = new ArrayList();
            class_243 playerPos = mc.field_1724.method_19538();
            double maxDistSq = 65536.0D;
            Iterator var5 = ft.getLoadedChunks().toList().iterator();

            while(var5.hasNext()) {
               class_2818 chunk = (class_2818)var5.next();

               for(int x = 0; x < 16; ++x) {
                  for(int z = 0; z < 16; ++z) {
                     int highestY = chunk.method_12032(class_2903.field_13202).method_12603(x, z);

                     for(int y = mc.field_1687.method_31607(); y <= highestY + 5; ++y) {
                        class_2338 pos = new class_2338((chunk.method_12004().field_9181 << 4) + x, y, (chunk.method_12004().field_9180 << 4) + z);
                        class_2680 state = chunk.method_8320(pos);
                        if (this.blocks.hasBlock(state.method_26204())) {
                           double dx = playerPos.field_1352 - ((double)pos.method_10263() + 0.5D);
                           double dy = playerPos.field_1351 - ((double)pos.method_10264() + 0.5D);
                           double dz = playerPos.field_1350 - ((double)pos.method_10260() + 0.5D);
                           double distSq = dx * dx + dy * dy + dz * dz;
                           if (distSq <= maxDistSq) {
                              foundBlocks.add(pos);
                           }
                        }
                     }
                  }
               }
            }

            List<eu> clusters = this.clusterBlocks(foundBlocks);
            this.cachedBasesRef.set(Collections.unmodifiableList(clusters));
         } catch (Exception var21) {
         }

      }
   }

   private List<eu> clusterBlocks(List<class_2338> blocks) {
      if (blocks.isEmpty()) {
         return Collections.emptyList();
      } else {
         List<eu> clusters = new ArrayList();
         Set<class_2338> unclustered = new HashSet(blocks);
         int minBlockCount = this.minBlocks.getIntValue();

         while(!unclustered.isEmpty()) {
            class_2338 seed = (class_2338)unclustered.iterator().next();
            List<class_2338> group = this.floodFill(seed, unclustered);
            if (group.size() >= minBlockCount) {
               clusters.add(new eu(group));
            }
         }

         return clusters;
      }
   }

   private List<class_2338> floodFill(class_2338 start, Set<class_2338> available) {
      List<class_2338> cluster = new ArrayList();
      Queue<class_2338> queue = new LinkedList();
      Set<class_2338> visited = new HashSet();
      double clusterDistSq = this.clusterDistance.getValue() * this.clusterDistance.getValue();
      queue.add(start);
      visited.add(start);
      available.remove(start);

      while(!queue.isEmpty()) {
         class_2338 current = (class_2338)queue.poll();
         cluster.add(current);
         Iterator var9 = (new ArrayList(available)).iterator();

         while(var9.hasNext()) {
            class_2338 other = (class_2338)var9.next();
            if (!visited.contains(other)) {
               double dx = (double)(current.method_10263() - other.method_10263());
               double dy = (double)(current.method_10264() - other.method_10264());
               double dz = (double)(current.method_10260() - other.method_10260());
               double distSq = dx * dx + dy * dy + dz * dz;
               if (distSq <= clusterDistSq) {
                  visited.add(other);
                  available.remove(other);
                  queue.add(other);
               }
            }
         }
      }

      return cluster;
   }

   private class_243 getCrosshairPositionForESP(float tickDelta) {
      bf freecam = mc.field_1724 == null ? null : Astralux.INSTANCE.getModuleManager().getModuleByClass(cj.class);
      if (freecam != null && freecam.isEnabled()) {
         cj fc = (cj)freecam;
         if (mc.field_1765 != null && mc.field_1765.method_17783() != class_240.field_1333) {
            return mc.field_1765.method_17784();
         } else {
            class_243 freecamPos = new class_243(fc.getInterpolatedX(tickDelta), fc.getInterpolatedY(tickDelta), fc.getInterpolatedZ(tickDelta));
            class_243 direction = class_243.method_1030((float)fc.getInterpolatedPitch(tickDelta), (float)fc.getInterpolatedYaw(tickDelta));
            return freecamPos.method_1019(direction.method_1021(4.0D));
         }
      } else {
         return mc.field_1765 != null ? mc.field_1765.method_17784() : mc.field_1724.method_5828(1.0F).method_1021(4.0D).method_1019(mc.field_1724.method_19538());
      }
   }

   @cp
   public void onRender3D(bg event) {
      if (mc != null && mc.field_1724 != null && mc.field_1687 != null) {
         class_4184 cam = iq.getCamera();
         if (cam != null) {
            class_243 camPos = cam.method_19326();
            class_4587 matrices = event.matrixStack;
            matrices.method_22903();
            matrices.method_22907(class_7833.field_40714.rotationDegrees(cam.method_19329()));
            matrices.method_22907(class_7833.field_40716.rotationDegrees(cam.method_19330() + 180.0F));
            matrices.method_22904(-camPos.field_1352, -camPos.field_1351, -camPos.field_1350);
            int alphaValue = this.alpha.getIntValue();
            class_243 crosshairPos = this.getCrosshairPositionForESP(event.tickDelta);
            List<eu> bases = (List)this.cachedBasesRef.get();
            Color baseColor = ch.safeColor(this.BASE_COLOR.getRed(), this.BASE_COLOR.getGreen(), this.BASE_COLOR.getBlue(), alphaValue);
            Iterator var9 = bases.iterator();

            while(var9.hasNext()) {
               eu base = (eu)var9.next();
               iq.renderFilledBox(matrices, (float)base.min.method_10263() - 0.5F, (float)base.min.method_10264() - 0.5F, (float)base.min.method_10260() - 0.5F, (float)base.max.method_10263() + 1.5F, (float)base.max.method_10264() + 1.5F, (float)base.max.method_10260() + 1.5F, baseColor);
               if (this.tracers.getValue()) {
                  class_243 target = new class_243((double)base.center.method_10263() + 0.5D, (double)base.center.method_10264() + 0.5D, (double)base.center.method_10260() + 0.5D);
                  iq.renderLine(matrices, this.BASE_COLOR, crosshairPos, target);
               }
            }

            matrices.method_22909();
         }
      }
   }

   // $FF: synthetic method
   private static String d168(String var0) {
      byte[] var1 = Base64.getDecoder().decode(var0);
      byte[] var2 = new byte[var1.length];

      for(int var3 = 0; var3 < var1.length; ++var3) {
         var2[var3] = (byte)(var1[var3] ^ 7617 + var3 & 255);
      }

      return new String(var2);
   }
}
