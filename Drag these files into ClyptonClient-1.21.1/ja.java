import java.util.Base64;
import net.minecraft.class_3532;

public final class ja extends bf {
   private final fp activateKey = (new fp(d424("MxAAHAAWDBxaMBkE"), 71, false)).setDescription(d424("ORYNVQIYWBgZDxULHwvlofjs6+g="));
   private final ff holdToRelease = (new ff(d424("OhwYEVYDF1koHhAYHwzl"), true)).setDescription(d424("PR0YDFYNFxYXCFwKFhbs5KLr6+ni7ubuqv/k6K7k9eg="));
   private final ff smoothAnimation = (new ff(d424("IR4bGgIfWDgUEhEcChbv7w=="), true)).setDescription(d424("IR4bGgIfWD81LVwJDB7u8uv37ero"));
   private final gn animationSpeed = (gn)(new gn(d424("Mx0dGBcDERYUWy8NGxrk"), 0.1D, 1.0D, 0.3D, 0.05D)).setDescription(d424("OhwDVRAWCw1aDxQYXgXv7u+j5evv6un94+Tiref8"));
   private final gn zoomLevel = (gn)(new gn(d424("KBwbGFY7HQ8fFw=="), 1.0D, 10.0D, 3.0D, 0.5D)).setDescription(d424("NhYSFAMbDFkAFBMQXhL17fbq9Onv4vo="));
   private final gn scrollSensitivity = (gn)(new gn(d424("IRAGGhobWCofFQ8UChb26Pb6"), 0.1D, 2.0D, 0.5D, 0.1D)).setDescription(d424("OhwDVRsCGxFaCB8PERPsoePl4uDl8/up8OTj4A=="));
   private boolean zoomActive = false;
   private double defaultFOV = 70.0D;
   private double currentFOV = 70.0D;
   private double targetFOV = 70.0D;
   private double currentZoomLevel;

   public ja() {
      super(d424("KBwbGA=="), d424("PQMAHBAeFhxXCAgEEhqg++3s6Q=="), -1, hk.MISC);
      this.addSettings(new ab[]{this.activateKey, this.holdToRelease, this.smoothAnimation, this.animationSpeed, this.zoomLevel, this.scrollSensitivity});
   }

   public void onEnable() {
      super.onEnable();
      if (mc.field_1724 == null) {
         this.toggle();
      } else {
         this.defaultFOV = ((Integer)mc.field_1690.method_41808().method_41753()).doubleValue();
         this.currentFOV = this.defaultFOV;
         this.targetFOV = this.defaultFOV;
         this.currentZoomLevel = this.zoomLevel.getValue();
         this.zoomActive = false;
      }
   }

   public void onDisable() {
      super.onDisable();
      if (mc.field_1690 != null) {
         mc.field_1690.method_41808().method_41748((int)this.defaultFOV);
      }

      this.zoomActive = false;
   }

   @cp
   public void onKey(hc event) {
      if (mc.field_1724 != null && mc.field_1755 == null) {
         if (event.key == this.activateKey.getValue()) {
            if (event.mode == 1) {
               if (this.holdToRelease.getValue()) {
                  this.activateZoom();
               } else if (this.zoomActive) {
                  this.deactivateZoom();
               } else {
                  this.activateZoom();
               }
            } else if (event.mode == 0 && this.holdToRelease.getValue()) {
               this.deactivateZoom();
            }
         }

      }
   }

   @cp
   public void onMouseScroll(cf event) {
      if (mc.field_1724 != null && mc.field_1755 == null) {
         if (this.zoomActive) {
            double scrollAmount = event.amount * this.scrollSensitivity.getValue();
            this.currentZoomLevel = class_3532.method_15350(this.currentZoomLevel + scrollAmount, 1.0D, 10.0D);
            this.targetFOV = this.defaultFOV / this.currentZoomLevel;
            event.cancel();
         }

      }
   }

   @cp
   public void onTick(hm event) {
      if (mc.field_1724 != null) {
         if (this.smoothAnimation.getValue()) {
            double lerpSpeed = this.animationSpeed.getValue();
            this.currentFOV = class_3532.method_16436(lerpSpeed, this.currentFOV, this.targetFOV);
            if (Math.abs(this.currentFOV - this.targetFOV) < 0.1D) {
               this.currentFOV = this.targetFOV;
            }
         } else {
            this.currentFOV = this.targetFOV;
         }

         mc.field_1690.method_41808().method_41748((int)this.currentFOV);
      }
   }

   private void activateZoom() {
      if (!this.zoomActive) {
         this.zoomActive = true;
         this.currentZoomLevel = this.zoomLevel.getValue();
         this.targetFOV = this.defaultFOV / this.currentZoomLevel;
      }

   }

   private void deactivateZoom() {
      if (this.zoomActive) {
         this.zoomActive = false;
         this.targetFOV = this.defaultFOV;
      }

   }

   // $FF: synthetic method
   private static String d424(String var0) {
      byte[] var1 = Base64.getDecoder().decode(var0);
      byte[] var2 = new byte[var1.length];

      for(int var3 = 0; var3 < var1.length; ++var3) {
         var2[var3] = (byte)(var1[var3] ^ 3954 + var3 & 255);
      }

      return new String(var2);
   }
}
