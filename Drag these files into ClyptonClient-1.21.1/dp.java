import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

class dp {
   private final List<String> anomalies = new ArrayList();
   private final List<String> details = new ArrayList();
   private final List<Integer> severities = new ArrayList();

   void addAnomaly(String type, String detail, int severity) {
      this.anomalies.add(type);
      this.details.add(detail);
      this.severities.add(severity);
   }

   boolean hasAnomalies() {
      return !this.anomalies.isEmpty();
   }

   String getPrimaryAnomaly() {
      if (this.anomalies.isEmpty()) {
         return d165("BCRsLCAgPTA+OjEm");
      } else {
         int maxIndex = 0;

         for(int i = 1; i < this.severities.size(); ++i) {
            if ((Integer)this.severities.get(i) > (Integer)this.severities.get(maxIndex)) {
               maxIndex = i;
            }
         }

         String var10000 = (String)this.anomalies.get(maxIndex);
         return var10000 + " Detected - " + (String)this.details.get(maxIndex);
      }
   }

   String getShortDescription() {
      if (this.anomalies.isEmpty()) {
         return d165("CScpLCA=");
      } else {
         String var10000 = (String)this.anomalies.get(0);
         return var10000 + " - " + (String)this.details.get(0);
      }
   }

   List<String> getAllAnomalies() {
      List<String> result = new ArrayList();

      for(int i = 0; i < this.anomalies.size(); ++i) {
         String var10001 = (String)this.anomalies.get(i);
         result.add(var10001 + ": " + (String)this.details.get(i));
      }

      return result;
   }

   // $FF: synthetic method
   private static String d165(String var0) {
      byte[] var1 = Base64.getDecoder().decode(var0);
      byte[] var2 = new byte[var1.length];

      for(int var3 = 0; var3 < var1.length; ++var3) {
         var2[var3] = (byte)(var1[var3] ^ 9546 + var3 & 255);
      }

      return new String(var2);
   }
}
