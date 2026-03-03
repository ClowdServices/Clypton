import client.astralux.Astralux;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;
import net.minecraft.class_408;
import net.minecraft.class_437;
import net.minecraft.class_471;
import net.minecraft.class_498;
import net.minecraft.class_7744;

public final class hj {
   private final List<bf> modules = new ArrayList();

   public hj() {
      this.registerAllModules();
      this.initializeModuleBinds();
   }

   private void registerAllModules() {
      this.registerModule(new ih());
      this.registerModule(new c());
      this.registerModule(new ga());
      this.registerModule(new br());
      this.registerModule(new x());
      this.registerModule(new bd());
      this.registerModule(new hz());
      this.registerModule(new hy());
      this.registerModule(new ix());
      this.registerModule(new ek());
      this.registerModule(new fc());
      this.registerModule(new gd());
      this.registerModule(new k());
      this.registerModule(new af());
      this.registerModule(new ik());
      this.registerModule(new dg());
      this.registerModule(new fq());
      this.registerModule(new fj());
      this.registerModule(new ej());
      this.registerModule(new gq());
      this.registerModule(new ew());
      this.registerModule(new hh());
      this.registerModule(new a());
      this.registerModule(new at());
      this.registerModule(new dl());
      this.registerModule(new o());
      this.registerModule(new am());
      this.registerModule(new ca());
      this.registerModule(new cj());
      this.registerModule(new dh());
      this.registerModule(new ev());
      this.registerModule(new eh());
      this.registerModule(new hf());
      this.registerModule(new aj());
      this.registerModule(new dj());
      this.registerModule(new dz());
      this.registerModule(new cg());
      this.registerModule(new fx());
      this.registerModule(new ia());
      this.registerModule(new gk());
      this.registerModule(new em());
      this.registerModule(new cq());
      this.registerModule(new cy());
      this.registerModule(new bx());
      this.registerModule(new d());
      this.registerModule(new be());
      this.registerModule(new gh());
      this.registerModule(new fa());
      this.registerModule(new u());
      this.registerModule(new gb());
      this.registerModule(new dw());
      this.registerModule(new z());
      this.registerModule(new bw());
      this.registerModule(new dv());
      this.registerModule(new cd());
      this.registerModule(new if());
      this.registerModule(new ci());
      this.registerModule(new ap());
      this.registerModule(new ir());
      this.registerModule(new fd());
      this.registerModule(new ei());
      this.registerModule(new ja());
   }

   public List<bf> getEnabledModules() {
      return this.modules.stream().filter(bf::isEnabled).toList();
   }

   public List<bf> getAllModules() {
      return new ArrayList(this.modules);
   }

   private void initializeModuleBinds() {
      Astralux.INSTANCE.getEventBus().register(this);
      Iterator var1 = this.modules.iterator();

      while(var1.hasNext()) {
         bf module = (bf)var1.next();
         module.addSetting((new fp(db.of(d369("zeLx6+Pl6A==")), module.getKeybind(), true)).setDescription(db.of(d369("zeLxqf7krOjg7vL997Pg/fO39fb+7vD4"))));
      }

   }

   public List<bf> getModulesByCategory(hk category) {
      return this.modules.stream().filter((module) -> {
         return module.getCategory() == category;
      }).toList();
   }

   private void registerModule(bf module) {
      if (module != null) {
         this.modules.add(module);
      }

   }

   public bf getModuleByClass(Class<? extends bf> moduleClass) {
      Objects.requireNonNull(moduleClass);
      Stream var10000 = this.modules.stream();
      Objects.requireNonNull(moduleClass);
      return (bf)var10000.filter(moduleClass::isInstance).findFirst().orElse((Object)null);
   }

   public void registerAndSubscribeModule(bf module) {
      if (module != null) {
         Astralux.INSTANCE.getEventBus().register(module);
         this.modules.add(module);
      }

   }

   @cp
   public void onKeyEvent(hc keyEvent) {
      if (Astralux.mc.field_1724 != null) {
         if (keyEvent.mode == 1) {
            try {
               if (keyEvent.key == 344) {
                  if (Astralux.mc.field_1755 == null) {
                     Astralux.mc.method_1507(Astralux.INSTANCE.GUI);
                  } else if (Astralux.mc.field_1755 == Astralux.INSTANCE.GUI) {
                     Astralux.mc.method_1507((class_437)null);
                  }

                  return;
               }
            } catch (Exception var3) {
               System.err.println("Error handling ClickGUI keybind: " + var3.getMessage());
            }
         }

         if (Astralux.mc.field_1755 != null) {
            boolean isTextInputScreen = Astralux.mc.field_1755 instanceof class_408 || Astralux.mc.field_1755 instanceof class_498 || Astralux.mc.field_1755 instanceof class_7744 || Astralux.mc.field_1755 instanceof class_471;
            if (!isTextInputScreen) {
               return;
            }
         }

         if (!dw.isActive) {
            this.modules.forEach((module) -> {
               if (module.getKeybind() == keyEvent.key && keyEvent.mode == 1) {
                  module.toggle();
               }

            });
         }

      }
   }

   // $FF: synthetic method
   private static String d369(String var0) {
      byte[] var1 = Base64.getDecoder().decode(var0);
      byte[] var2 = new byte[var1.length];

      for(int var3 = 0; var3 < var1.length; ++var3) {
         var2[var3] = (byte)(var1[var3] ^ 1414 + var3 & 255);
      }

      return new String(var2);
   }
}
