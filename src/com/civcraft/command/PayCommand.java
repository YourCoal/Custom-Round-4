package com.civcraft.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.civcraft.exception.CivException;
import com.civcraft.main.CivGlobal;
import com.civcraft.main.BasicMessages;
import com.civcraft.object.Resident;

public class PayCommand implements CommandExecutor {
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		try { Player player = CivGlobal.getPlayer(sender.getName());
			Resident resident = CivGlobal.getResident(player);
			if (resident == null) {
				BasicMessages.sendAError(sender, "command.PayCommand-20: You are not a registered user??? Relog or contact an admin!");
				return false;
			} if (args.length < 2) {
				throw new CivException("Enter a player and an amount to pay /pay [player] [amount]");
			} Resident payTo = CivGlobal.getResident(args[0]);
			if (payTo == null) {
				throw new CivException("Couldn't find the player "+args[0]+".");
			} if (resident == payTo) {
				player.damage(4);
				throw new CivException("Don't pay yourself!");
			} Double amount;
			try { amount = Double.valueOf(args[1]);
				if (!resident.getTreasury().hasEnough(amount)) {
					throw new CivException("You do not have enough coins.");
				}
			} catch (NumberFormatException e) {
				throw new CivException("Please enter a number.");
			} if (amount < 1) {
				player.damage(4);
				throw new CivException("Cannot pay someone less than one coin.");
			} amount = Math.floor(amount);
			resident.getTreasury().withdraw(amount);
			payTo.getTreasury().deposit(amount);
			BasicMessages.sendSuccess(player, "Paid "+payTo.getName()+" "+amount+" coins");
			try { Player payToPlayer = CivGlobal.getPlayer(payTo);
				BasicMessages.sendSuccess(payToPlayer, "Given "+amount+" coins from "+resident.getName());
			} catch (CivException e) {
			}
		} catch (CivException e) {
			BasicMessages.sendAError(sender, "command.PayCommand-48: "+e.getMessage());
			return false;
		} return true;
	}
}
