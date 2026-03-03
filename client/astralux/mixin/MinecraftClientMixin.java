package client.astralux.mixin;

import client.astralux.Astralux;
import net.minecraft.class_1041;
import net.minecraft.class_310;
import net.minecraft.class_437;
import net.minecraft.class_638;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({class_310.class})
public class MinecraftClientMixin {
   @Shadow
   @Nullable
   public class_638 field_1687;
   @Shadow
   @Final
   private class_1041 field_1704;
   @Shadow
   private int field_1752;
   @Shadow
   @Nullable
   public class_437 field_1755;

   @Inject(
      method = {"tick"},
      at = {@At("HEAD")}
   )
   private void onStartTick(CallbackInfo ci) {
      if (this.field_1687 != null) {
         .ho.b(new .hm());
      }

   }

   @Inject(
      method = {"tick"},
      at = {@At("RETURN")}
   )
   private void onEndTick(CallbackInfo ci) {
      if (this.field_1687 != null) {
         .ho.b(new .fz());
      }

   }

   @Inject(
      method = {"onResolutionChanged"},
      at = {@At("HEAD")}
   )
   private void onResolutionChanged(CallbackInfo ci) {
      .ho.b(new .gw(this.field_1704));
   }

   @Inject(
      method = {"doItemUse"},
      at = {@At("RETURN")},
      cancellable = true
   )
   private void onItemUseReturn(CallbackInfo ci) {
      .cw event = new .cw(this.field_1752);
      .ho.b(event);
      if (event.isCancelled()) {
         ci.cancel();
      }

      this.field_1752 = event.cooldown;
   }

   @Inject(
      method = {"doItemUse"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void onItemUseHead(CallbackInfo ci) {
      .ec event = new .ec(this.field_1752);
      .ho.b(event);
      if (event.isCancelled()) {
         ci.cancel();
      }

      this.field_1752 = event.cooldown;
   }

   @Inject(
      method = {"doAttack"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void onAttack(CallbackInfoReturnable<Boolean> cir) {
      .hx event = new .hx();
      .ho.b(event);
      if (event.isCancelled()) {
         cir.setReturnValue(false);
      }

   }

   @Inject(
      method = {"handleBlockBreaking"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void onBlockBreaking(boolean breaking, CallbackInfo ci) {
      .gv event = new .gv();
      .ho.b(event);
      if (event.isCancelled()) {
         ci.cancel();
      }

   }

   @Inject(
      method = {"setScreen"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void onSetScreen(class_437 screen, CallbackInfo ci) {
      .ao event = new .ao(screen);
      .ho.b(event);
      if (event.isCancelled()) {
         ci.cancel();
      }

      if (screen != null && .gh.shouldPreventPause()) {
         String screenName = screen.getClass().getSimpleName();
         if ((screenName.equals("GameMenuScreen") || screenName.equals("PauseScreen") || screenName.equals("InGameMenuScreen")) && this.field_1755 == null) {
            ci.cancel();
         }
      }

   }

   @Inject(
      method = {"stop"},
      at = {@At("HEAD")}
   )
   private void onClose(CallbackInfo callbackInfo) {
      Astralux.configManager.shutdown();
      Astralux.INSTANCE.getConfigManager().shutdown();
   }
}
