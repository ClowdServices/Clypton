import java.util.ArrayList;
import java.util.Base64;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.minecraft.class_2561;
import net.minecraft.class_2583;
import net.minecraft.class_266;
import net.minecraft.class_268;
import net.minecraft.class_269;
import net.minecraft.class_274;
import net.minecraft.class_5250;
import net.minecraft.class_8646;
import net.minecraft.class_9011;
import net.minecraft.class_9014;
import net.minecraft.class_9015;
import net.minecraft.class_9020;
import net.minecraft.class_274.class_275;

public final class am extends bf {
   private static final String SCOREBOARD_NAME = "clypton_fake";
   private static final Pattern PATTERN_MONEY = Pattern.compile("(Money)\\s*(.*)");
   private static final Pattern PATTERN_SHARDS = Pattern.compile("(Shards)\\s*(.*)");
   private static final Pattern PATTERN_KILLS = Pattern.compile("(Kills)\\s*(.*)");
   private static final Pattern PATTERN_DEATHS = Pattern.compile("(Deaths)\\s*(.*)");
   private static final Pattern PATTERN_PLAYTIME = Pattern.compile("(Playtime)\\s*(.*)");
   private static final Pattern PATTERN_TEAM = Pattern.compile("(Team)\\s*(.*)");
   private static final Pattern PATTERN_KEYALL = Pattern.compile("(Keyall)\\s*(.*)");
   private static final Pattern PATTERN_PING = Pattern.compile("\\((\\d+)ms\\)");
   private class_266 customObjective;
   private final Map<String, Integer> holderIndexMap = new HashMap();
   private final Map<String, String> staticTextCache = new HashMap();
   private class_266 lastRealObjective;
   private int lastRealEntriesHash;
   private long keyallStartTime;
   private long keyallInitialTime;
   private long lastMsUpdate;
   private int displayMs;
   private int msChangeDirection;
   private final bu moneyValue = new bu(db.of(d253("UHFxRVg=")), d253("KylS"));
   private final bu shardsValue = new bu(db.of(d253("TnZ+UkVR")), d253("KylS"));
   private final bu killsValue = new bu(db.of(d253("VndzTFI=")), d253("KylS"));
   private final bu deathsValue = new bu(db.of(d253("WXt+VElR")), d253("KylS"));
   private final bu playtimeValue = new bu(db.of(d253("TXJ+WVVLTkE=")), d253("Kyl7ABcVTg=="));
   private final bu teamValue = new bu(db.of(d253("SXt+TQ==")), "9/11");
   private final ff hideKeyall = new ff(db.of(d253("VXd7RQFpRl1ESks=")), false);
   private final ff fakeKeyall = new ff(db.of(d253("W390RQFpRl1ESksIfUNGSV8=")), true);
   private final ff fakePing = new ff(db.of(d253("W390RQFySkpC")), true);

   public am() {
      super(db.of(d253("W390RXJWQlBW")), db.of(d253("W390RQFmTEpQUnRleQpYWExaXA==")), -1, hk.DONUT);
      ab[] var10001 = new ab[]{this.moneyValue, this.shardsValue, this.killsValue, this.deathsValue, this.playtimeValue, this.teamValue, null, null, null};
      var10001[122 ^ 124] = this.hideKeyall;
      var10001[108 ^ 107] = this.fakeKeyall;
      var10001[8] = this.fakePing;
      this.addSettings(var10001);
   }

   public void onEnable() {
      super.onEnable();
      cv.getInstance().register(this);
      if (mc.field_1687 != null && mc.field_1724 != null) {
         this.keyallStartTime = System.currentTimeMillis();
         this.keyallInitialTime = 3599L;
         this.lastMsUpdate = System.currentTimeMillis();
         this.displayMs = 50 + (int)(Math.random() * 50.0D);
         this.msChangeDirection = Math.random() < 0.5D ? 1 : -1;
         this.customObjective = null;
         this.lastRealObjective = null;
         this.lastRealEntriesHash = 0;
         this.holderIndexMap.clear();
         this.staticTextCache.clear();
         this.createFakeScoreboard();
      }
   }

   public void onDisable() {
      super.onDisable();
      cv.getInstance().unregister(this);
      if (mc.field_1687 != null) {
         class_269 scoreboard = mc.field_1687.method_8428();
         if (this.lastRealObjective != null && this.objectiveExists(scoreboard, this.lastRealObjective)) {
            scoreboard.method_1158(class_8646.field_45157, this.lastRealObjective);
         } else {
            Iterator var2 = scoreboard.method_1151().iterator();

            while(var2.hasNext()) {
               class_266 obj = (class_266)var2.next();
               if (!obj.method_1113().equals(d253("fG1rUkBOVlx6QEZDTA=="))) {
                  scoreboard.method_1158(class_8646.field_45157, obj);
                  break;
               }
            }
         }

         if (this.customObjective != null && this.objectiveExists(scoreboard, this.customObjective)) {
            scoreboard.method_1194(this.customObjective);
         }

         this.cleanupCustomTeams(scoreboard);
         this.customObjective = null;
         this.lastRealObjective = null;
         this.holderIndexMap.clear();
         this.staticTextCache.clear();
      }
   }

   @cp
   public void onStartTick(hm event) {
      if (this.isEnabled()) {
         if (mc.field_1687 != null && mc.field_1724 != null) {
            this.checkAndUpdateScoreboard();
         }
      }
   }

   @cp
   public void onEndTick(fz event) {
      if (this.isEnabled()) {
         if (mc.field_1687 != null && mc.field_1724 != null) {
            this.checkAndUpdateScoreboard();
         }
      }
   }

   @cp
   public void onPacketReceive(ak event) {
      if (this.isEnabled()) {
         if (mc.field_1687 != null && mc.field_1724 != null) {
            this.checkAndUpdateScoreboard();
         }
      }
   }

   private void checkAndUpdateScoreboard() {
      class_269 scoreboard = mc.field_1687.method_8428();
      class_266 currentDisplayed = scoreboard.method_1189(class_8646.field_45157);
      if (currentDisplayed != null && currentDisplayed.method_1113().equals(d253("fG1rUkBOVlx6QEZDTA=="))) {
         if (!this.objectiveExists(scoreboard, this.customObjective)) {
            this.createFakeScoreboard();
         } else {
            class_266 realObjective = this.findRealObjective(scoreboard);
            if (realObjective != null) {
               if (this.lastRealObjective != realObjective) {
                  this.createFakeScoreboard();
               } else {
                  int currentHash = this.getEntriesHash(scoreboard, realObjective);
                  if (currentHash != this.lastRealEntriesHash) {
                     this.createFakeScoreboard();
                  } else {
                     this.updateDynamicValues(scoreboard);
                  }
               }
            }
         }
      } else {
         this.createFakeScoreboard();
      }
   }

   private boolean objectiveExists(class_269 scoreboard, class_266 objective) {
      if (objective == null) {
         return false;
      } else {
         Iterator var3 = scoreboard.method_1151().iterator();

         class_266 obj;
         do {
            if (!var3.hasNext()) {
               return false;
            }

            obj = (class_266)var3.next();
         } while(obj != objective);

         return true;
      }
   }

   private class_266 findRealObjective(class_269 scoreboard) {
      Iterator var2 = scoreboard.method_1151().iterator();

      class_266 obj;
      do {
         if (!var2.hasNext()) {
            return null;
         }

         obj = (class_266)var2.next();
      } while(obj.method_1113().equals(d253("fG1rUkBOVlx6QEZDTA==")));

      return obj;
   }

   private int getEntriesHash(class_269 scoreboard, class_266 objective) {
      Collection<class_9011> entries = scoreboard.method_1184(objective);
      return entries.size() * (105 ^ 118) + entries.hashCode();
   }

   private void cleanupCustomTeams(class_269 scoreboard) {
      List<class_268> teamsToRemove = new ArrayList();
      Iterator var3 = scoreboard.method_1159().iterator();

      class_268 team;
      while(var3.hasNext()) {
         team = (class_268)var3.next();
         if (team.method_1197().startsWith(d253("fG1rUkBOVlx6Sk5GTHU="))) {
            teamsToRemove.add(team);
         }
      }

      var3 = teamsToRemove.iterator();

      while(var3.hasNext()) {
         team = (class_268)var3.next();

         try {
            scoreboard.method_1191(team);
         } catch (Exception var6) {
         }
      }

   }

   private void updateDynamicValues(class_269 scoreboard) {
      Iterator var2 = this.holderIndexMap.entrySet().iterator();

      while(true) {
         while(var2.hasNext()) {
            Entry<String, Integer> entry = (Entry)var2.next();
            String holderName = (String)entry.getKey();
            Integer index = (Integer)entry.getValue();
            String customTeamName = "clypton_line_" + index;
            class_268 customTeam = scoreboard.method_1153(customTeamName);
            if (customTeam == null) {
               this.createFakeScoreboard();
               return;
            }

            class_2561 currentPrefix = customTeam.method_1144();
            String textString = currentPrefix.getString();
            if (textString.contains(d253("VntmQU1O")) && this.fakeKeyall.getValue()) {
               String newKeyall = this.getKeyallTimer();
               class_2561 newText = this.replaceValueInText(currentPrefix, PATTERN_KEYALL, newKeyall);
               customTeam.method_1138(newText);
            } else if (textString.contains(d253("cG02")) && this.fakePing.getValue()) {
               Matcher matcher = PATTERN_PING.matcher(textString);
               if (matcher.find()) {
                  String newPing = this.getAnimatedPing();
                  String newTextStr = textString.replace(matcher.group(0), "(" + newPing + ")");
                  class_2561 newText = this.rebuildTextWithColors(currentPrefix, newTextStr);
                  customTeam.method_1138(newText);
               }
            }
         }

         return;
      }
   }

   private void createFakeScoreboard() {
      if (mc.field_1687 != null) {
         class_269 scoreboard = mc.field_1687.method_8428();
         if (this.customObjective != null && this.objectiveExists(scoreboard, this.customObjective)) {
            try {
               scoreboard.method_1194(this.customObjective);
            } catch (Exception var16) {
            }
         }

         this.cleanupCustomTeams(scoreboard);
         this.holderIndexMap.clear();
         this.staticTextCache.clear();
         class_266 realObjective = this.findRealObjective(scoreboard);
         if (realObjective == null) {
            this.customObjective = null;
            this.lastRealObjective = null;
         } else {
            this.lastRealObjective = realObjective;
            this.lastRealEntriesHash = this.getEntriesHash(scoreboard, realObjective);
            class_2561 originalTitle = realObjective.method_1114();

            try {
               this.customObjective = scoreboard.method_1168(d253("fG1rUkBOVlx6QEZDTA=="), class_274.field_1468, originalTitle, class_275.field_1472, false, class_9020.field_47557);
            } catch (Exception var17) {
               Iterator var5 = scoreboard.method_1151().iterator();

               while(var5.hasNext()) {
                  class_266 obj = (class_266)var5.next();
                  if (obj.method_1113().equals(d253("fG1rUkBOVlx6QEZDTA=="))) {
                     this.customObjective = obj;
                     break;
                  }
               }

               if (this.customObjective == null) {
                  return;
               }
            }

            scoreboard.method_1158(class_8646.field_45157, this.customObjective);
            Collection<class_9011> realEntries = scoreboard.method_1184(realObjective);
            List<gj> entries = new ArrayList();
            Iterator var19 = realEntries.iterator();

            while(true) {
               String holderName;
               class_2561 originalText;
               class_2561 modifiedText;
               do {
                  class_268 realTeam;
                  do {
                     if (!var19.hasNext()) {
                        for(int i = 0; i < entries.size(); ++i) {
                           String teamName = "clypton_line_" + i;
                           class_268 team = scoreboard.method_1171(teamName);
                           team.method_1138(((gj)entries.get(i)).text);
                           gj entryData = (gj)entries.get(i);
                           class_9015 holder = class_9015.method_55422(entryData.holderName);
                           class_9014 score = scoreboard.method_1180(holder, this.customObjective);
                           score.method_55410(entryData.score);
                           scoreboard.method_1172(entryData.holderName, team);
                           this.holderIndexMap.put(entryData.holderName, i);
                        }

                        return;
                     }

                     class_9011 entry = (class_9011)var19.next();
                     holderName = entry.comp_2127();
                     realTeam = null;
                     Iterator var10 = scoreboard.method_1159().iterator();

                     while(var10.hasNext()) {
                        class_268 team = (class_268)var10.next();
                        if (team.method_1204().contains(holderName) && !team.method_1197().startsWith(d253("fG1rUkBOVlx6Sk5GTHU="))) {
                           realTeam = team;
                           break;
                        }
                     }
                  } while(realTeam == null);

                  originalText = realTeam.method_1144();
                  modifiedText = this.replaceValues(originalText);
               } while(this.hideKeyall.getValue() && modifiedText.getString().isEmpty());

               String textString = originalText.getString();
               if (!this.isDynamicLine(textString)) {
                  this.staticTextCache.put(holderName, modifiedText.getString());
               }

               class_9015 holder = class_9015.method_55422(holderName);
               class_9014 scoreAccess = scoreboard.method_1180(holder, realObjective);
               int score = scoreAccess.method_55409();
               entries.add(new gj(holderName, modifiedText, score));
            }
         }
      }
   }

   private String getKeyallTimer() {
      long elapsed = System.currentTimeMillis() - this.keyallStartTime;
      long elapsedSeconds = elapsed / 1000L;
      long remainingSeconds = Math.max(0L, this.keyallInitialTime - elapsedSeconds);
      long minutes = remainingSeconds / 60L;
      long seconds = remainingSeconds % 60L;
      return String.format(d253("OHpyAARGUA=="), minutes, seconds);
   }

   private String getAnimatedPing() {
      long currentTime = System.currentTimeMillis();
      if (currentTime - this.lastMsUpdate > 2000L + (long)(Math.random() * 2000.0D)) {
         int change = 1 + (int)(Math.random() * 5.0D);
         this.displayMs += this.msChangeDirection * change;
         if (this.displayMs < 20) {
            this.displayMs = 192 ^ 212;
            this.msChangeDirection = 1;
         } else if (this.displayMs > 150) {
            this.displayMs = 150;
            this.msChangeDirection = -1;
         }

         if (Math.random() < 0.1D) {
            this.msChangeDirection *= -1;
         }

         this.lastMsUpdate = currentTime;
      }

      return this.displayMs + "ms";
   }

   private boolean isDynamicLine(String textString) {
      if (textString.contains(d253("VntmQU1O"))) {
         return true;
      } else {
         return textString.contains(d253("cG02"));
      }
   }

   private class_2561 replaceValues(class_2561 originalText) {
      String textString = originalText.getString();
      if (textString.contains(d253("SXt+TQ=="))) {
         return this.replaceValueInText(originalText, PATTERN_TEAM, d253("XG1rUkBOVlwFZUtBTERf"));
      } else if (textString.contains(d253("UHFxRVg="))) {
         return this.replaceValueInText(originalText, PATTERN_MONEY, this.moneyValue.getValue());
      } else if (textString.contains(d253("TnZ+UkVR"))) {
         return this.replaceValueInText(originalText, PATTERN_SHARDS, this.shardsValue.getValue());
      } else if (textString.contains(d253("VndzTFI="))) {
         return this.replaceValueInText(originalText, PATTERN_KILLS, this.killsValue.getValue());
      } else if (textString.contains(d253("WXt+VElR"))) {
         return this.replaceValueInText(originalText, PATTERN_DEATHS, this.deathsValue.getValue());
      } else if (textString.contains(d253("TXJ+WVVLTkE="))) {
         return this.replaceValueInText(originalText, PATTERN_PLAYTIME, this.playtimeValue.getValue());
      } else if (textString.contains(d253("VntmQU1O"))) {
         if (this.hideKeyall.getValue()) {
            return class_2561.method_43470("");
         } else {
            return this.fakeKeyall.getValue() ? this.replaceValueInText(originalText, PATTERN_KEYALL, this.getKeyallTimer()) : originalText;
         }
      } else if (textString.contains(d253("cG02"))) {
         if (this.fakePing.getValue()) {
            Matcher matcher = PATTERN_PING.matcher(textString);
            if (matcher.find()) {
               String newText = textString.replace(matcher.group(0), "(" + this.getAnimatedPing() + ")");
               return this.rebuildTextWithColors(originalText, newText);
            }
         }

         return originalText;
      } else {
         return originalText;
      }
   }

   private class_2561 replaceValueInText(class_2561 originalText, Pattern pattern, String newValue) {
      String textString = originalText.getString();
      Matcher matcher = pattern.matcher(textString);
      String newText;
      if (matcher.find()) {
         String var10000 = textString.substring(0, matcher.start(2));
         newText = var10000 + newValue;
      } else {
         newText = textString;
      }

      return this.rebuildTextWithColors(originalText, newText);
   }

   private class_2561 rebuildTextWithColors(class_2561 original, String newText) {
      List<hg> segments = new ArrayList();
      original.method_27658((style, content) -> {
         segments.add(new hg(content, style));
         return Optional.empty();
      }, class_2583.field_24360);
      if (segments.isEmpty()) {
         return class_2561.method_43470(newText);
      } else {
         class_5250 result = class_2561.method_43473();
         int newTextPos = 0;
         Iterator var6 = segments.iterator();

         while(var6.hasNext()) {
            hg segment = (hg)var6.next();
            int segmentLength = segment.content.length();
            if (newTextPos < newText.length()) {
               int endPos = Math.min(newTextPos + segmentLength, newText.length());
               String part = newText.substring(newTextPos, endPos);
               result.method_10852(class_2561.method_43470(part).method_10862(segment.style));
               newTextPos = endPos;
            }
         }

         if (newTextPos < newText.length() && !segments.isEmpty()) {
            class_2583 lastStyle = ((hg)segments.get(segments.size() - 1)).style;
            result.method_10852(class_2561.method_43470(newText.substring(newTextPos)).method_10862(lastStyle));
         }

         return result;
      }
   }

   // $FF: synthetic method
   private static String d253(String var0) {
      byte[] var1 = Base64.getDecoder().decode(var0);
      byte[] var2 = new byte[var1.length];

      for(int var3 = 0; var3 < var1.length; ++var3) {
         var2[var3] = (byte)(var1[var3] ^ 10013 + var3 & 255);
      }

      return new String(var2);
   }
}
