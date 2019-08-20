package com.massivecraft.factions.struct;

import org.bukkit.event.block.Action;

public class ChestLogInfo {

   private String user;
   private Action action;
   private String time;
   private int amount;
   private String material;


   public ChestLogInfo(String user, Action action, String time, int amount, String material) {
      this.user = user;
      this.action = action;
      this.time = time;
      this.amount = amount;
      this.material = material;
   }


   public Action getAction() {
      return action;
   }

   public void setAction(Action action) {
      this.action = action;
   }

   public String getUser() {
      return user;
   }

   public void setUser(String user) {
      this.user = user;
   }

   public String getTime() {
      return time;
   }

   public void setTime(String time) {
      this.time = time;
   }

   public int getAmount() {
      return amount;
   }

   public void setAmount(int amount) {
      this.amount = amount;
   }

   public String getMaterial() {
      return material;
   }
}
