package client.astralux.mixin;

import client.astralux.Astralux;
import net.minecraft.class_5223;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin({class_5223.class})
public class TextVisitFactoryMixin {
   @ModifyArg(
      at = @At(
   value = "INVOKE",
   target = "Lnet/minecraft/text/TextVisitFactory;visitFormatted(Ljava/lang/String;ILnet/minecraft/text/Style;Lnet/minecraft/text/Style;Lnet/minecraft/text/CharacterVisitor;)Z",
   ordinal = 0
),
      method = {"visitFormatted(Ljava/lang/String;ILnet/minecraft/text/Style;Lnet/minecraft/text/CharacterVisitor;)Z"},
      index = 0
   )
   private static String adjustText(String s) {
      if (s == null) {
         return null;
      } else if (Astralux.INSTANCE != null && Astralux.INSTANCE.MODULE_MANAGER != null) {
         if (Astralux.mc != null && Astralux.mc.method_1548() != null) {
            .cy nameprotect = (.cy)Astralux.INSTANCE.MODULE_MANAGER.getModuleByClass(.cy.class);
            if (nameprotect != null && nameprotect.isEnabled()) {
               String realName = Astralux.mc.method_1548().method_1676();
               if (realName != null && !realName.isEmpty()) {
                  if (!s.contains(realName)) {
                     return s;
                  } else {
                     String fakeName = nameprotect.getFakeName();
                     return fakeName == null ? s : s.replace(realName, fakeName);
                  }
               } else {
                  return s;
               }
            } else {
               return s;
            }
         } else {
            return s;
         }
      } else {
         return s;
      }
   }
}
