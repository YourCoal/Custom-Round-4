//XXX All recoded 12/24/2015 by YourCoal
package com.civcraft.main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.civcraft.camp.Camp;
import com.civcraft.exception.CivException;
import com.civcraft.object.Civilization;
import com.civcraft.object.Resident;
import com.civcraft.object.Town;
import com.civcraft.util.CivColor;

public class BasicMessages {
	
	/* Stores the player name and the hash code of the last message sent to prevent error spamming the player. */
	private static HashMap<String, Integer> lastMessageHashCode = new HashMap<String, Integer>();
	/* Indexed off of town names, contains a list of extra people who listen to town chats.(For listening to towns) */
	private static Map<String, ArrayList<String>> extraTownChatListeners = new ConcurrentHashMap<String, ArrayList<String>>();
	/* Indexed off of civ names, contains a list of extra people who listen to civ chats. (For listening to civs) */
	private static Map<String, ArrayList<String>> extraCivChatListeners = new ConcurrentHashMap<String, ArrayList<String>>();
	
	/* Sends an error to the sender, but only once, until they get a second error, then it can repeat. */
	public static void sendErrorWithNoRepeat(Object sender, String line) {
		if (sender instanceof Player) {
			Player player = (Player)sender;
			Integer hashcode = lastMessageHashCode.get(player.getName());
			if (hashcode != null && hashcode == line.hashCode()) {
				return;
			} lastMessageHashCode.put(player.getName(), line.hashCode());
		} send(sender, CivColor.Red+"[CivCraft Error] "+CivColor.Rose+line);
	}
	
	/* Sends an error to the sender, which can be done infinite times. */
	public static void sendAError(Object sender, String line) {
		send(sender, CivColor.Red+"[CivCraft Error*] "+CivColor.Rose+line);
	}
	
	/* Sends message to playerName(if online) AND console. */
	public static void console(String playerName, String line) {
		try { Player player = CivGlobal.getPlayer(playerName);
			send(player, line);
		} catch (CivException e) {
		} CivLog.info(line);	
	}
	
	public static void send(Object sender, String line) {
		if ((sender instanceof Player)) {
			((Player) sender).sendMessage(line);
		} else if (sender instanceof CommandSender) {
			((CommandSender) sender).sendMessage(line);
		} else if (sender instanceof Resident) {
			try { CivGlobal.getPlayer(((Resident) sender)).sendMessage(line);
			} catch (CivException e) { //Player not online
			}
		}
	}
	
	public static void send(Object sender, String[] lines) {
		boolean isPlayer = false;
		if (sender instanceof Player)
			isPlayer = true;
		
		for (String line : lines) {
			if (isPlayer) { ((Player) sender).sendMessage(line);
			} else { ((CommandSender) sender).sendMessage(line);
			}
		}
	}
	
	public static String build48Title(String title) {
		String line =   "------------------------------------------------";
		String titleBracket = "[ " + CivColor.Yellow + title + CivColor.LightBlue + " ]";
		if (titleBracket.length() > line.length()) {
			return CivColor.LightBlue+"-"+titleBracket+"-";
		}
		
		int min = (line.length() / 2) - titleBracket.length() / 2;
		int max = (line.length() / 2) + titleBracket.length() / 2;
		String out = CivColor.LightBlue + line.substring(0, Math.max(0, min));
		out += titleBracket + line.substring(max);
		return out;
	}
	
	public static String build32Title(String title) {
		String line =   CivColor.LightBlue+"--------------------------------";
		String titleBracket = "[ "+title+" ]";
		int min = (line.length() / 2) - titleBracket.length() / 2;
		int max = (line.length() / 2) + titleBracket.length() / 2;
		String out = CivColor.LightBlue + line.substring(0, Math.max(0, min));
		out += titleBracket + line.substring(max);
		return out;
	}
	
	public static void sendSubHeading(CommandSender sender, String title) {
		send(sender, build32Title(title));
	}
	
	public static void sendHeading(Resident resident, String title) {
		Player player;
		try { player = CivGlobal.getPlayer(resident);
			sendHeading(player, title);
		} catch (CivException e) {
		}
	}
	
	public static void sendHeading(CommandSender sender, String title) {	
		send(sender, build48Title(title));
	}
	
	public static void sendSuccess(CommandSender sender, String message) {
		send(sender, CivColor.Green+"[CivCraft Sucess] "+CivColor.LightGreen+message);
	}
	
	public static void sendControlBlock(CommandSender sender, String message) {
		send(sender, CivColor.Gray+"[Control Block] "+CivColor.LightGray+message);
	}
	
//	public static void sendGlobal(String string) {
//		CivLog.info("[Global] "+string);
//		for (Player player : Bukkit.getOnlinePlayers()) {
//			player.sendMessage(CivColor.LightBlue+"[Global] "+CivColor.White+string);
//		}
//	}
	
	public static void globalCamp(String string) {
		CivLog.info("[Global Camp] "+string);
		for (Player player : Bukkit.getOnlinePlayers()) {
			player.sendMessage(CivColor.LightBlue+"[Global "+CivColor.DarkPurple+"Camp"+CivColor.LightBlue+"] "+CivColor.White+string);
		}
	}
	
	public static void globalTown(String string) {
		CivLog.info("[Global Town] "+string);
		for (Player player : Bukkit.getOnlinePlayers()) {
			player.sendMessage(CivColor.LightBlue+"[Global "+CivColor.Gold+"Town"+CivColor.LightBlue+"] "+CivColor.White+string);
		}
	}
	
	public static void globalCiv(String string) {
		CivLog.info("[Global Civ] "+string);
		for (Player player : Bukkit.getOnlinePlayers()) {
			player.sendMessage(CivColor.LightBlue+"[Global "+CivColor.LightPurple+"Civ"+CivColor.LightBlue+"] "+CivColor.White+string);
		}
	}
	
	public static void sendMob(String string) {
		CivLog.info("[Mob] "+string);
		for (Player player : Bukkit.getOnlinePlayers()) {
			player.sendMessage(CivColor.Yellow+"[Mob] "+CivColor.White+string);
		}
	}
	
	public static void sendGlobalHeading(String string) {
		CivLog.info("[GlobalHeading] "+string);
		for (Player player : Bukkit.getOnlinePlayers()) { send(player, build48Title(string));
		}
	}
	
	public static void sendScoutTower(Civilization civ, String string) {
		CivLog.info("[Scout:"+civ.getName()+"] "+string);
		for (Town t : civ.getTowns()) {
			for (Resident resident : t.getResidents()) {
				if (!resident.isShowScout()) { continue;
				} Player player;
				try { player = CivGlobal.getPlayer(resident);
					if (player != null) {
						BasicMessages.send(player, CivColor.Purple+"[Scout] "+CivColor.White+string);
					}
				} catch (CivException e) {
				}
			}
		}
	}
	
	public static void sendCamp(Camp camp, String message) {
		for (Resident resident : camp.getMembers()) {
			try { Player player = CivGlobal.getPlayer(resident);
				player.sendMessage(CivColor.DarkPurple+"[Camp] "+CivColor.DarkPurple+message);		
				CivLog.info("[Camp:"+camp.getName()+"] "+message);
			} catch (CivException e) {
			}
		}
	}
	
	public static void sendToTown(Town town, String string) {
		CivLog.info("[Town:"+town.getName()+"] "+string);
		for (Resident resident : town.getResidents()) {
			if (!resident.isShowTown()) { continue;
			} Player player;
			try { player = CivGlobal.getPlayer(resident);
				if (player != null) {
					BasicMessages.send(player, CivColor.Gold+"[Town] "+CivColor.White+string);
				}
			} catch (CivException e) {
			}
		}
	}
	
	public static void sendToCiv(Civilization civ, String string) {
		CivLog.info("[Civ:"+civ.getName()+"] "+string);
		for (Town t : civ.getTowns()) {
			for (Resident resident : t.getResidents()) {
				if (!resident.isShowCiv()) { continue;
				} Player player;
				try { player = CivGlobal.getPlayer(resident);
					if (player != null) {
						BasicMessages.send(player, CivColor.LightPurple+"[Civ] "+CivColor.White+string);
					}
				} catch (CivException e) {
				}
			}
		}
	}
	
	public static void send(CommandSender sender, List<String> outs) {
		for (String str : outs) { send(sender, str);
		}
	}
	
	public static void sendToTownChat(Town town, Resident resident, String format, String message) {
		if (town == null) {
			try { Player player = CivGlobal.getPlayer(resident);
				player.sendMessage(CivColor.Rose+"You are not part of a town, nobody hears you. Type /tc to chat normally.");
			} catch (CivException e) {
			} return;
		} CivLog.info("[TC:"+town.getName()+"] "+resident.getName()+": "+message);
		
		for (Resident r : town.getResidents()) {
			try { Player player = CivGlobal.getPlayer(r);
				String msg = CivColor.LightBlue+"[TC]"+CivColor.White+String.format(format, resident.getName(), message);
				player.sendMessage(msg);
			} catch (CivException e) {
				continue; /* player not online. */
			}
		} for (String name : getExtraTownChatListeners(town)) {
			try { Player player = CivGlobal.getPlayer(name);
				String msg = CivColor.LightBlue+"[TC:"+town.getName()+"]"+CivColor.White+String.format(format, resident.getName(), message);
				player.sendMessage(msg);
			} catch (CivException e) { /* player not online. */
			}
		}
	}
	
	public static void sendToCivChat(Civilization civ, Resident resident, String format, String message) {
		if (civ == null) {
			try { Player player = CivGlobal.getPlayer(resident);
				player.sendMessage(CivColor.Rose+"You are not part of a civ, nobody hears you. Type /cc to chat normally.");
			} catch (CivException e) {
			} return;
		} String townName = "";
		if (resident.getTown() != null) { townName = resident.getTown().getName();
		} for (Town t : civ.getTowns()) {
			for (Resident r : t.getResidents()) {
				try { Player player = CivGlobal.getPlayer(r);
				String msg = CivColor.Gold+"[CC "+townName+"]"+CivColor.White+String.format(format, resident.getName(), message);
					player.sendMessage(msg);
				} catch (CivException e) {
					continue; /* player not online. */
				}
			}
		} for (String name : getExtraCivChatListeners(civ)) {
			try { Player player = CivGlobal.getPlayer(name);
				String msg = CivColor.Gold+"[CC:"+civ.getName()+" "+townName+"]"+CivColor.White+String.format(format, resident.getName(), message);
				player.sendMessage(msg);
			} catch (CivException e) { /* player not online. */
			}
		} return;
	}
	
	public static void sendChat(Resident resident, String format, String message) {
		for (Player player : Bukkit.getOnlinePlayers()) {
			String msg = String.format(format, resident.getName(), message);
			player.sendMessage(msg);
		}
	}
	
	public static void addExtraTownChatListener(Town town, String name) {
		ArrayList<String> names = extraTownChatListeners.get(town.getName().toLowerCase());
		if (names == null) {
			names = new ArrayList<String>();
		} for (String str : names) {
			if (str.equals(name)) {
				return;
			}
		} names.add(name);		
		extraTownChatListeners.put(town.getName().toLowerCase(), names);
	}
	
	public static void removeExtraTownChatListener(Town town, String name) {
		ArrayList<String> names = extraTownChatListeners.get(town.getName().toLowerCase());
		if (names == null) {
			return;
		} for (String str : names) {
			if (str.equals(name)) {
				names.remove(str);
				break;
			}
		} extraTownChatListeners.put(town.getName().toLowerCase(), names);
	}
	
	public static ArrayList<String> getExtraTownChatListeners(Town town) {
		ArrayList<String> names = extraTownChatListeners.get(town.getName().toLowerCase());
		if (names == null) {
			return new ArrayList<String>();
		} return names;
	}
	
	public static void addExtraCivChatListener(Civilization civ, String name) {
		ArrayList<String> names = extraCivChatListeners.get(civ.getName().toLowerCase());
		if (names == null) {
			names = new ArrayList<String>();
		} for (String str : names) {
			if (str.equals(name)) {
				return;
			}
		} names.add(name);
		extraCivChatListeners.put(civ.getName().toLowerCase(), names);
	}
	
	public static void removeExtraCivChatListener(Civilization civ, String name) {
		ArrayList<String> names = extraCivChatListeners.get(civ.getName().toLowerCase());
		if (names == null) {
			return;
		} for (String str : names) {
			if (str.equals(name)) {
				names.remove(str);
				break;
			}
		} extraCivChatListeners.put(civ.getName().toLowerCase(), names);
	}
	
	public static ArrayList<String> getExtraCivChatListeners(Civilization civ) {
		ArrayList<String> names = extraCivChatListeners.get(civ.getName().toLowerCase());
		if (names == null) { return new ArrayList<String>();
		} return names;
	}
	
	public static void sendTownSound(Town town, Sound sound, float f, float g) {
		for (Resident resident : town.getResidents()) { Player player;
			try { player = CivGlobal.getPlayer(resident);
			player.playSound(player.getLocation(), sound, f, g);
			} catch (CivException e) { //player not online
			}
		}
	}
	
	public static void sendAll(String str) {
		for (Player player : Bukkit.getOnlinePlayers()) {
			player.sendMessage(str);
		}
	}
	
	public static void sendTownHeading(Town town, String string) {
		CivLog.info("[Town:"+town.getName()+"] "+string);
		for (Resident resident : town.getResidents()) {
			if (!resident.isShowTown()) {
				continue;
			} Player player;
			try { player = CivGlobal.getPlayer(resident);
				if (player != null) { BasicMessages.sendHeading(player, string);
				}
			} catch (CivException e) {
			}
		}
	}
	
	public static void sendSuccess(Resident resident, String message) {
		try { Player player = CivGlobal.getPlayer(resident);
			sendSuccess(player, message);
		} catch (CivException e) {
			return;
		}
	}
}
