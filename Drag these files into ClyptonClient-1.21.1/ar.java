import java.awt.Color;
import java.util.Base64;
import java.util.Collection;
import java.util.Comparator;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import net.minecraft.class_2394;
import net.minecraft.class_243;

public class ar {
   private static final ar INSTANCE = new ar();
   private final Map<Long, er> particles = new ConcurrentHashMap();
   private long particleIdCounter = 0L;
   private boolean enabled = false;

   public static ar getInstance() {
      return INSTANCE;
   }

   public void setEnabled(boolean enabled) {
      this.enabled = enabled;
      if (!enabled) {
         this.particles.clear();
      }

   }

   public boolean isEnabled() {
      return this.enabled;
   }

   public void trackParticle(class_2394 effect, class_243 position, class_243 velocity) {
      if (this.enabled) {
         long id = (long)(this.particleIdCounter++);
         String type = effect.method_10295().toString().toLowerCase();
         Color color = this.getParticleColor(type);
         this.particles.put(id, new er(id, position, velocity, color, type, System.currentTimeMillis()));
      }
   }

   public Collection<er> getParticles() {
      return this.particles.values();
   }

   public void cleanup(long maxAge, int maxParticles) {
      long currentTime = System.currentTimeMillis();
      this.particles.entrySet().removeIf((entry) -> {
         return currentTime - ((er)entry.getValue()).spawnTime > maxAge;
      });

      while(this.particles.size() > maxParticles) {
         Optional var10000 = this.particles.entrySet().stream().min(Comparator.comparingLong((e) -> {
            return ((er)e.getValue()).spawnTime;
         })).map(Entry::getKey);
         Map var10001 = this.particles;
         Objects.requireNonNull(var10001);
         var10000.ifPresent(var10001::remove);
      }

   }

   private Color getParticleColor(String particleType) {
      if (!particleType.contains(d771("GBACAw0=")) && !particleType.contains(d771("BhgeHxUSEwU=")) && !particleType.contains(d771("Ex4fAxsGAhIK"))) {
         if (!particleType.contains(d771("Fh0THhE=")) && !particleType.contains(d771("HBAEEg=="))) {
            if (particleType.contains(d771("Ax4HHw=="))) {
               return new Color(141 ^ 233, 200, 255);
            } else if (!particleType.contains(d771("BxAGFgY=")) && !particleType.contains(d771("EgQQERgQ")) && !particleType.contains(d771("AhAbHQ=="))) {
               if (particleType.contains(d771("AxwdGBE="))) {
                  return new Color(161 ^ 33, 128, 128);
               } else if (!particleType.contains(d771("AhQWAAAaGBI=")) && !particleType.contains(d771("FAQBBw=="))) {
                  if (particleType.contains(d771("AB4ABxUZ"))) {
                     return new Color(128, 0, 83 ^ 172);
                  } else if (particleType.contains(d771("FR8RGxUbAg=="))) {
                     return new Color(100, 255, 185 ^ 113);
                  } else if (particleType.contains(d771("GBQTAQA="))) {
                     return new Color(255, 50, 50);
                  } else if (particleType.contains(d771("Hh4GFg=="))) {
                     return new Color(0, 188 ^ 67, 0);
                  } else if (particleType.contains(d771("EwMbBw=="))) {
                     return new Color(255, 255, 100);
                  } else if (particleType.contains(d771("FAMTFBsb"))) {
                     return new Color(255, 0, 133 ^ 122);
                  } else if (particleType.contains(d771("BxgGEBw="))) {
                     return new Color(150, 0, 150);
                  } else if (particleType.contains(d771("Ax0bHhE="))) {
                     return new Color(100, 255, 100);
                  } else if (particleType.contains(d771("FR8WLAYaEg=="))) {
                     return new Color(255, 255, 255);
                  } else {
                     return particleType.contains(d771("FBAfEhMQ")) ? new Color(200, 50, 50) : new Color(255, 117 ^ 138, 255);
                  }
               } else {
                  return new Color(255, 0, 0);
               }
            } else {
               return new Color(0, 150, 184 ^ 71);
            }
         } else {
            return new Color(255, 23 ^ 115, 0);
         }
      } else {
         return new Color(0, 255, 0);
      }
   }

   // $FF: synthetic method
   private static String d771(String var0) {
      byte[] var1 = Base64.getDecoder().decode(var0);
      byte[] var2 = new byte[var1.length];

      for(int var3 = 0; var3 < var1.length; ++var3) {
         var2[var3] = (byte)(var1[var3] ^ 4720 + var3 & (14 ^ 241));
      }

      return new String(var2);
   }
}
