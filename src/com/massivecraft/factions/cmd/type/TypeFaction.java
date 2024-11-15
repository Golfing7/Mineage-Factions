package com.massivecraft.factions.cmd.type;

import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.entity.FactionColl;
import com.massivecraft.factions.entity.MPlayer;
import com.massivecraft.factions.entity.MPlayerColl;
import com.massivecraft.massivecore.MassiveCore;
import com.massivecraft.massivecore.MassiveException;
import com.massivecraft.massivecore.command.type.TypeAbstract;
import com.massivecraft.massivecore.comparator.ComparatorCaseInsensitive;
import com.massivecraft.massivecore.util.IdUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.Set;
import java.util.TreeSet;

public class TypeFaction extends TypeAbstract<Faction>
{
	// -------------------------------------------- //
	// INSTANCE & CONSTRUCT
	// -------------------------------------------- //
	
	private static TypeFaction i = new TypeFaction();
	public static TypeFaction get() { return i; }
	public TypeFaction() { super(Faction.class); }
	
	// -------------------------------------------- //
	// OVERRIDE
	// -------------------------------------------- //
	
	@Override
	public String getVisualInner(Faction value, CommandSender sender)
	{
		return value.describeTo(MPlayer.get(sender));
	}

	@Override
	public String getNameInner(Faction value)
	{
		return ChatColor.stripColor(value.getName());
	}
	
	@Override
	public Faction read(String str, CommandSender sender) throws MassiveException
	{
		Faction ret;
		
		// Nothing/Remove targets Wilderness
		if (MassiveCore.NOTHING_REMOVE.contains(str))
		{
			return FactionColl.get().getNone();
		}
		
		// Faction Id Exact
		if (FactionColl.get().containsId(str))
		{
			ret = FactionColl.get().get(str);
			if (ret != null) return ret;
		}
		
		// Faction Name Exact
		ret = FactionColl.get().getByName(str);
		if (ret != null) return ret;
		
		// MPlayer Name Exact
		String id = IdUtil.getId(str);
		MPlayer mplayer = MPlayerColl.get().get(id, false);
		if (mplayer != null)
		{
			return mplayer.getFaction();
		}
		
		throw new MassiveException().addMsg("<b>No faction or player matching \"<p>%s<b>\".", str);
	}

	@Override
	public Collection<String> getTabList(CommandSender sender, String arg)
	{
		// Create
		Set<String> ret = new TreeSet<>(ComparatorCaseInsensitive.get());
		
		// Fill
		for (Faction faction : FactionColl.get().getAll())
		{
			ret.add(ChatColor.stripColor(faction.getName()));
		}
		
		for (Player p : Bukkit.getOnlinePlayers())
		{
			ret.add(p.getName());
		}
		
		// Return
		return ret;
	}
	
}
