package com.massivecraft.factions.cmd.money;

import com.massivecraft.factions.Factions;
import com.massivecraft.factions.Perm;
import com.massivecraft.factions.cmd.FactionsCommand;
import com.massivecraft.factions.cmd.req.ReqBankCommandsEnabled;
import com.massivecraft.factions.cmd.type.TypeFaction;
import com.massivecraft.factions.cmd.type.TypeMPlayer;
import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.entity.MConf;
import com.massivecraft.factions.entity.MPlayer;
import com.massivecraft.factions.integration.Econ;
import com.massivecraft.massivecore.MassiveException;
import com.massivecraft.massivecore.command.requirement.RequirementHasPerm;
import com.massivecraft.massivecore.command.type.primitive.TypeDouble;
import com.massivecraft.massivecore.money.Money;
import com.massivecraft.massivecore.util.Txt;
import org.bukkit.ChatColor;

public class CmdFactionsMoneyTransferFp extends FactionsCommand
{
	// -------------------------------------------- //
	// CONSTRUCT
	// -------------------------------------------- //
	
	public CmdFactionsMoneyTransferFp()
	{
		// Fields
		this.setSetupEnabled(false);

		// Aliases
		this.addAliases("fp");

		// Parameters
		this.addParameter(TypeDouble.get(), "amount");
		this.addParameter(TypeFaction.get(), "faction");
		this.addParameter(TypeMPlayer.get(), "player");

		// Requirements
		this.addRequirements(RequirementHasPerm.get(Perm.MONEY_F2P));
		this.addRequirements(ReqBankCommandsEnabled.get());
	}
	
	// -------------------------------------------- //
	// OVERRIDE
	// -------------------------------------------- //
	
	@Override
	public void perform() throws MassiveException
	{
		double amount = this.readArg();
		Faction from = this.readArg();
		MPlayer to = this.readArg();
		
		boolean success = Econ.transferMoney(msender, from, to, amount);

		if (success && MConf.get().logMoneyTransactions)
		{
			Factions.get().log(ChatColor.stripColor(Txt.parse("%s transferred %s from the faction \"%s\" to the player \"%s\"", msender.getName(), Money.format(amount), from.describeTo(null), to.describeTo(null))));
		}
	}
	
}
