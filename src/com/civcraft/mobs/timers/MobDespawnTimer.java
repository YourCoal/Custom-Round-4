package com.civcraft.mobs.timers;

import java.util.LinkedList;

import com.civcraft.exception.CivException;
import com.civcraft.main.CivLog;
import com.civcraft.mobs.CommonCustomMob;

public class MobDespawnTimer {
	
	public static void despawn_yobo() throws CivException {
		String name = "yobo";
		
		LinkedList<CommonCustomMob> removeUs = new LinkedList<CommonCustomMob>();
		for (CommonCustomMob mob :CommonCustomMob.customMobs.values()) {
			if (mob.getType().toString().equalsIgnoreCase(name)) {
				removeUs.add(mob);
			}
		}
		
		int count = 0;
		for (CommonCustomMob mob : removeUs) {
			CommonCustomMob.customMobs.remove(mob.entity.getUniqueID());
			mob.entity.getBukkitEntity().remove();
			count++;
		}
		CivLog.warning("Removed "+count+ " "+name+" mobs");
	}
	
	public static void despawn_angryyobo() throws CivException {
		String name = "angryyobo";
		
		LinkedList<CommonCustomMob> removeUs = new LinkedList<CommonCustomMob>();
		for (CommonCustomMob mob :CommonCustomMob.customMobs.values()) {
			if (mob.getType().toString().equalsIgnoreCase(name)) {
				removeUs.add(mob);
			}
		}
		
		int count = 0;
		for (CommonCustomMob mob : removeUs) {
			CommonCustomMob.customMobs.remove(mob.entity.getUniqueID());
			mob.entity.getBukkitEntity().remove();
			count++;
		}
		CivLog.warning("Removed "+count+ " "+name+" mobs");
	}
	
	public static void despawn_behemoth() throws CivException {
		String name = "behemoth";
		
		LinkedList<CommonCustomMob> removeUs = new LinkedList<CommonCustomMob>();
		for (CommonCustomMob mob :CommonCustomMob.customMobs.values()) {
			if (mob.getType().toString().equalsIgnoreCase(name)) {
				removeUs.add(mob);
			}
		}
		
		int count = 0;
		for (CommonCustomMob mob : removeUs) {
			CommonCustomMob.customMobs.remove(mob.entity.getUniqueID());
			mob.entity.getBukkitEntity().remove();
			count++;
		}
		CivLog.warning("Removed "+count+ " "+name+" mobs");
	}
	
	public static void despawn_ruffian() throws CivException {
		String name = "ruffian";
		
		LinkedList<CommonCustomMob> removeUs = new LinkedList<CommonCustomMob>();
		for (CommonCustomMob mob :CommonCustomMob.customMobs.values()) {
			if (mob.getType().toString().equalsIgnoreCase(name)) {
				removeUs.add(mob);
			}
		}
		
		int count = 0;
		for (CommonCustomMob mob : removeUs) {
			CommonCustomMob.customMobs.remove(mob.entity.getUniqueID());
			mob.entity.getBukkitEntity().remove();
			count++;
		}
		CivLog.warning("Removed "+count+ " "+name+" mobs");
	}
	
	public static void despawn_savage() throws CivException {
		String name = "savage";
		
		LinkedList<CommonCustomMob> removeUs = new LinkedList<CommonCustomMob>();
		for (CommonCustomMob mob :CommonCustomMob.customMobs.values()) {
			if (mob.getType().toString().equalsIgnoreCase(name)) {
				removeUs.add(mob);
			}
		}
		
		int count = 0;
		for (CommonCustomMob mob : removeUs) {
			CommonCustomMob.customMobs.remove(mob.entity.getUniqueID());
			mob.entity.getBukkitEntity().remove();
			count++;
		}
		CivLog.warning("Removed "+count+ " "+name+" mobs");
	}
}
