import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.io.PrintStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse.BodyHandlers;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.CompletableFuture;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import net.minecraft.class_1703;
import net.minecraft.class_1707;
import net.minecraft.class_1713;
import net.minecraft.class_1799;
import net.minecraft.class_1802;
import net.minecraft.class_1836;
import net.minecraft.class_2561;
import net.minecraft.class_746;
import net.minecraft.class_1792.class_9635;

public final class br extends bf {
   private final dn snipingItem;
   private final bu price;
   private final cu<iu> mode;
   private final bu apiKey;
   private final gn refreshDelay;
   private final gn buyDelay;
   private final gn apiRefreshRate;
   private final ff showApiNotifications;
   private int delayCounter;
   private boolean isProcessing;
   private final HttpClient httpClient;
   private final Gson gson;
   private long lastApiCallTimestamp;
   private final Map<String, Double> snipingItems;
   private boolean isApiQueryInProgress;
   private boolean isAuctionSniping;
   private int auctionPageCounter;
   private String currentSellerName;

   public br() {
      super(db.of(d924("OQwZDxUSEF/T7+vz4fc=")), db.of(d924("KxcTCxkOXhb05O/wpOrop+n86f/l4uCv+P7n4PG18Pjqufnz+fzu")), -1, hk.DONUT);
      this.snipingItem = new dn(db.of(d924("KxcTCxUTGV/J9efu")), class_1802.field_8162);
      this.price = new bu(db.of(d924("KAsTGBk=")), "1k");
      this.mode = (new cu(db.of(d924("NRYeHg==")), iu.MANUAL, iu.class)).setDescription(db.of(d924("NRgUDh0RXhbzoeTi9/Hj9ajr//+s7P7msPX99uf74rfq/Ovu9e/7v8HUwdfNysiHz9zDi8Pdy8HV1ZLS2NmWw9Dcms/V0Ns=")));
      this.apiKey = (new bu(db.of(d924("OQkTWzcYBw==")), "")).setDescription(db.of("You can get it by typing /api in chat"));
      this.refreshDelay = new gn(db.of(d924("KhwcCRkOFl/E5O7i/Q==")), 0.0D, 100.0D, 2.0D, 1.0D);
      this.buyDelay = new gn(db.of(d924("OgwDWzgYEh75")), 0.0D, 100.0D, 2.0D, 1.0D);
      this.apiRefreshRate = (new gn(db.of(d924("OSkzWy4YGA3l8uqj1uTy4g==")), 10.0D, 5000.0D, 250.0D, 10.0D)).getValue(db.of(d924("MBYNWxMbChruofbspPTz4vrwqv/k6K7OwNiyu/37tvrx9fby7/j98M7F0Yo=")));
      this.showApiNotifications = (new ff(db.of(d924("KxEVDFw8Ljagz+337ePv5On94+Ti/g==")), true)).setDescription(db.of(d924("KxEVDFweFh70oezs8Ozg7uvo/uLj4/2v9v7gs9XF37f5+u7y8/Pt")));
      this.lastApiCallTimestamp = 0L;
      this.snipingItems = new HashMap();
      this.isApiQueryInProgress = false;
      this.isAuctionSniping = false;
      this.auctionPageCounter = -1;
      this.currentSellerName = "";
      this.httpClient = HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(5L)).build();
      this.gson = new Gson();
      ab[] settingArray = new ab[]{this.snipingItem, this.price, this.mode, this.apiKey, this.refreshDelay, this.buyDelay, this.apiRefreshRate, this.showApiNotifications};
      this.addSettings(settingArray);
   }

   public void onEnable() {
      super.onEnable();
      double d = this.parsePrice(this.price.getValue());
      if (d == -1.0D) {
         if (mc.field_1724 != null) {
            class_746 clientPlayerEntity = mc.field_1724;
            clientPlayerEntity.method_7353(class_2561.method_30163(d924("MRcMGhAUGl/Q8+vg4Q==")), true);
         }

         this.toggle();
      } else {
         if (this.snipingItem.getItem() != class_1802.field_8162) {
            Map<String, Double> map = this.snipingItems;
            map.put(this.snipingItem.getItem().toString(), d);
         }

         this.lastApiCallTimestamp = 0L;
         this.isApiQueryInProgress = false;
         this.isAuctionSniping = false;
         this.currentSellerName = "";
      }
   }

   public void onDisable() {
      super.onDisable();
      this.isAuctionSniping = false;
   }

   @cp
   public void onTick(hm startTickEvent) {
      if (mc.field_1724 != null) {
         if (this.delayCounter > 0) {
            --this.delayCounter;
         } else if (this.mode.isMode(iu.API)) {
            this.handleApiMode();
         } else {
            if (this.mode.isMode(iu.MANUAL)) {
               class_1703 screenHandler = mc.field_1724.field_7512;
               if (!(mc.field_1724.field_7512 instanceof class_1707)) {
                  String[] stringArray = this.snipingItem.getItem().method_7876().split("\\.");
                  String string2 = stringArray[stringArray.length - 1];
                  String string3 = (String)Arrays.stream(string2.replace("_", " ").split(" ")).map((string) -> {
                     String var10000 = string.substring(0, 1).toUpperCase();
                     return var10000 + string.substring(1);
                  }).collect(Collectors.joining(" "));
                  mc.method_1562().method_45730("ah " + string3);
                  this.delayCounter = 20;
                  return;
               }

               if (((class_1707)screenHandler).method_17388() == 6) {
                  this.processSixRowAuction((class_1707)screenHandler);
               } else if (((class_1707)screenHandler).method_17388() == 3) {
                  this.processThreeRowAuction((class_1707)screenHandler);
               }
            }

         }
      }
   }

   private void handleApiMode() {
      if (!this.isAuctionSniping) {
         if (mc.field_1724.field_7512 instanceof class_1707 && mc.field_1755.method_25440().getString().contains(d924("KBgdHg=="))) {
            mc.field_1724.method_7346();
            this.delayCounter = 20;
         } else if (!this.isApiQueryInProgress) {
            long l = System.currentTimeMillis();
            long l2 = l - this.lastApiCallTimestamp;
            if (l2 > (long)this.apiRefreshRate.getIntValue()) {
               this.lastApiCallTimestamp = l;
               if (this.apiKey.getValue().isEmpty()) {
                  if (this.showApiNotifications.getValue()) {
                     class_746 clientPlayerEntity = mc.field_1724;
                     clientPlayerEntity.method_7353(class_2561.method_30163("§cAPI key is not set. Set it using /api in-game."), false);
                  }

                  return;
               }

               this.isApiQueryInProgress = true;
               this.queryApi().thenAccept(this::processApiResponse);
            }

         }
      } else {
         class_1703 screenHandler = mc.field_1724.field_7512;
         if (!(mc.field_1724.field_7512 instanceof class_1707)) {
            if (this.auctionPageCounter != -1) {
               if (this.auctionPageCounter <= 40) {
                  ++this.auctionPageCounter;
               } else {
                  this.isAuctionSniping = false;
                  this.currentSellerName = "";
               }
            } else {
               mc.method_1562().method_45730("ah " + this.currentSellerName);
               this.auctionPageCounter = 0;
            }
         } else {
            this.auctionPageCounter = -1;
            if (((class_1707)screenHandler).method_17388() == 6) {
               this.processSixRowAuction((class_1707)screenHandler);
            } else if (((class_1707)screenHandler).method_17388() == 3) {
               this.processThreeRowAuction((class_1707)screenHandler);
            }
         }

      }
   }

   private CompletableFuture<List<?>> queryApi() {
      return CompletableFuture.supplyAsync(() -> {
         try {
            String string = "https://api.donutsmp.net/v1/auction/list/1";
            HttpResponse<String> httpResponse = this.httpClient.send(HttpRequest.newBuilder().uri(URI.create(string)).header(d924("OQwOExMPFwXh9evs6g=="), "Bearer " + this.apiKey.getValue()).header(d924("OxYUDxkTClLU+PLm"), "application/json").POST(BodyPublishers.ofString(d924("A1sJFA4JXEWgo/Dm5+Do8+Tw1efl/vrq9LPv"))).build(), BodyHandlers.ofString());
            if (httpResponse.statusCode() != 200) {
               if (this.showApiNotifications.getValue() && mc.field_1724 != null) {
                  class_746 clientPlayerEntity = mc.field_1724;
                  clientPlayerEntity.method_7353(class_2561.method_30163("§cAPI Error: " + httpResponse.statusCode()), false);
               }

               ArrayList<?> arrayList = new ArrayList();
               this.isApiQueryInProgress = false;
               return arrayList;
            } else {
               Gson gson = this.gson;
               JsonArray jsonArray = ((JsonObject)gson.fromJson((String)httpResponse.body(), JsonObject.class)).getAsJsonArray(d924("ChwJDhAJ"));
               ArrayList<JsonObject> arrayListx = new ArrayList();
               Iterator var6 = jsonArray.iterator();

               while(var6.hasNext()) {
                  JsonElement jsonElement = (JsonElement)var6.next();
                  arrayListx.add(jsonElement.getAsJsonObject());
               }

               this.isApiQueryInProgress = false;
               return arrayListx;
            }
         } catch (Throwable var8) {
            var8.printStackTrace(System.err);
            return List.of();
         }
      });
   }

   private void processApiResponse(List list) {
      Iterator var2 = list.iterator();

      while(var2.hasNext()) {
         Object e = var2.next();

         try {
            String string2 = ((JsonObject)e).getAsJsonObject(d924("EQ0fFg==")).get("id").getAsString();
            long l = ((JsonObject)e).get(d924("CAsTGBk=")).getAsLong();
            String string3 = ((JsonObject)e).getAsJsonObject(d924("CxwWFxkP")).get(d924("FhgXHg==")).getAsString();
            Iterator iterator = this.snipingItems.entrySet().iterator();

            while(iterator.hasNext()) {
               Entry<String, Double> entry = (Entry)iterator.next();
               String string = (String)entry.getKey();
               double d = (Double)entry.getValue();
               if (string2.contains(string) && (double)l <= d) {
                  if (this.showApiNotifications.getValue() && mc.field_1724 != null) {
                     class_746 clientPlayerEntity = mc.field_1724;
                     clientPlayerEntity.method_7353(class_2561.method_30163("§aFound " + string2 + " for " + this.formatPrice((double)l) + " §r(threshold: " + this.formatPrice(d) + ") §afrom seller: " + string3), false);
                  }

                  this.isAuctionSniping = true;
                  this.currentSellerName = string3;
                  return;
               }
            }
         } catch (Exception var13) {
            if (this.showApiNotifications.getValue() && mc.field_1724 != null) {
               class_746 clientPlayerEntity = mc.field_1724;
               clientPlayerEntity.method_7353(class_2561.method_30163("§cError processing auction: " + var13.getMessage()), false);
            }
         }
      }

   }

   private void processSixRowAuction(class_1707 genericContainerScreenHandler) {
      class_1799 itemStack = genericContainerScreenHandler.method_7611(47).method_7677();
      if (itemStack.method_31574(class_1802.field_8162)) {
         this.delayCounter = 2;
      } else {
         Iterator var3 = itemStack.method_7950(class_9635.field_51353, mc.field_1724, class_1836.field_41070).iterator();

         Object e;
         String string;
         do {
            do {
               if (!var3.hasNext()) {
                  for(int i = 0; i < (78 ^ 98); ++i) {
                     class_1799 itemStack2 = genericContainerScreenHandler.method_7611(i).method_7677();
                     if (itemStack2.method_31574(this.snipingItem.getItem()) && this.isValidAuctionItem(itemStack2)) {
                        if (this.isProcessing) {
                           mc.field_1761.method_2906(mc.field_1724.field_7512.field_7763, i, 1, class_1713.field_7794, mc.field_1724);
                           this.isProcessing = false;
                           return;
                        }

                        this.isProcessing = true;
                        this.delayCounter = this.buyDelay.getIntValue();
                        return;
                     }
                  }

                  if (this.isAuctionSniping) {
                     this.isAuctionSniping = false;
                     this.currentSellerName = "";
                     mc.field_1724.method_7346();
                  } else {
                     mc.field_1761.method_2906(mc.field_1724.field_7512.field_7763, 49, 1, class_1713.field_7794, mc.field_1724);
                     this.delayCounter = this.refreshDelay.getIntValue();
                  }

                  return;
               }

               e = var3.next();
               string = e.toString();
            } while(!string.contains(d924("KhwZHhIJEgagzevw8ODi")));
         } while(!((class_2561)e).method_10866().toString().contains(d924("DxETDxk=")) && !string.contains(d924("DxETDxk=")));

         mc.field_1761.method_2906(mc.field_1724.field_7512.field_7763, 47, 1, class_1713.field_7794, mc.field_1724);
         this.delayCounter = 5;
      }
   }

   private void processThreeRowAuction(class_1707 genericContainerScreenHandler) {
      if (this.isValidAuctionItem(genericContainerScreenHandler.method_7611(13).method_7677())) {
         mc.field_1761.method_2906(mc.field_1724.field_7512.field_7763, 245 ^ 250, 1, class_1713.field_7794, mc.field_1724);
         this.delayCounter = 20;
      }

      if (this.isAuctionSniping) {
         this.isAuctionSniping = false;
         this.currentSellerName = "";
      }

   }

   private double parseTooltipPrice(List list) {
      if (list != null && !list.isEmpty()) {
         Iterator iterator = list.iterator();

         while(iterator.hasNext()) {
            String string3 = ((class_2561)iterator.next()).getString();
            if (string3.matches("(?i).*price\\s*:\\s*\\$.*")) {
               String string4 = string3.replaceAll("[,$]", "");
               Matcher matcher = Pattern.compile("([\\d]+(?:\\.[\\d]+)?)\\s*([KMB])?", 2).matcher(string4);
               if (matcher.find()) {
                  String string2 = matcher.group(1);
                  String string = matcher.group(2) != null ? matcher.group(2).toUpperCase() : "";
                  return this.parsePrice(string2 + string);
               }
            }
         }

         return -1.0D;
      } else {
         return -1.0D;
      }
   }

   private boolean isValidAuctionItem(class_1799 itemStack) {
      List list = itemStack.method_7950(class_9635.field_51353, mc.field_1724, class_1836.field_41070);
      double d = this.parseTooltipPrice(list) / (double)itemStack.method_7947();
      double d2 = this.parsePrice(this.price.getValue());
      class_746 clientPlayerEntity;
      if (d2 == -1.0D) {
         if (mc.field_1724 != null) {
            clientPlayerEntity = mc.field_1724;
            clientPlayerEntity.method_7353(class_2561.method_30163(d924("MRcMGhAUGl/Q8+vg4Q==")), true);
         }

         this.toggle();
         return false;
      } else if (d != -1.0D) {
         boolean bl = d <= d2;
         return bl;
      } else {
         if (mc.field_1724 != null) {
            clientPlayerEntity = mc.field_1724;
            clientPlayerEntity.method_7353(class_2561.method_30163(d924("MRcMGhAUGl/B9OH37erop8H97+as3fzm8/Q=")), true);

            for(int i = 0; i < list.size() - 1; ++i) {
               PrintStream printStream = System.out;
               printStream.println(i + ". " + ((class_2561)list.get(i)).getString());
            }
         }

         this.toggle();
         return false;
      }
   }

   private double parsePrice(String string) {
      if (string == null) {
         return -1.0D;
      } else if (string.isEmpty()) {
         return -1.0D;
      } else {
         String string2 = string.trim().toUpperCase();
         double d = 1.0D;
         if (string2.endsWith("B")) {
            d = 1.0E9D;
            string2 = string2.substring(0, string2.length() - 1);
         } else if (string2.endsWith("M")) {
            d = 1000000.0D;
            string2 = string2.substring(0, string2.length() - 1);
         } else if (string2.endsWith("K")) {
            d = 1000.0D;
            string2 = string2.substring(0, string2.length() - 1);
         }

         try {
            return Double.parseDouble(string2) * d;
         } catch (NumberFormatException var6) {
            return -1.0D;
         }
      }
   }

   private String formatPrice(double d) {
      Object[] objectArray;
      if (d >= 1.0E9D) {
         objectArray = new Object[]{d / 1.0E9D};
         return String.format(d924("XVdIHT4="), objectArray);
      } else if (d >= 1000000.0D) {
         objectArray = new Object[]{d / 1000000.0D};
         return String.format(d924("XVdIHTE="), objectArray);
      } else if (d >= 1000.0D) {
         objectArray = new Object[]{d / 1000.0D};
         return String.format(d924("XVdIHTc="), objectArray);
      } else {
         objectArray = new Object[]{d};
         return String.format(d924("XVdIHQ=="), objectArray);
      }
   }

   // $FF: synthetic method
   private static String d924(String var0) {
      byte[] var1 = Base64.getDecoder().decode(var0);
      byte[] var2 = new byte[var1.length];

      for(int var3 = 0; var3 < var1.length; ++var3) {
         var2[var3] = (byte)(var1[var3] ^ 4728 + var3 & 255);
      }

      return new String(var2);
   }
}
