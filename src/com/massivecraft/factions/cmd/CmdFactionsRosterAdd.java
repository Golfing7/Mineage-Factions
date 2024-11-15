package com.massivecraft.factions.cmd;

import com.massivecraft.factions.Rel;
import com.massivecraft.factions.cmd.req.ReqHasFaction;
import com.massivecraft.factions.cmd.type.TypeMPlayer;
import com.massivecraft.factions.cmd.type.TypeRel;
import com.massivecraft.factions.entity.LangConf;
import com.massivecraft.factions.entity.MConf;
import com.massivecraft.factions.entity.MPlayer;
import com.massivecraft.massivecore.MassiveException;
import org.bukkit.ChatColor;

public class CmdFactionsRosterAdd extends FactionsCommand {

    public CmdFactionsRosterAdd() {
        this.addParameter(TypeMPlayer.get(), "player");
        this.addParameter(TypeRel.get(), "rel", "recruit");
        this.addRequirements(ReqHasFaction.get());
    }

    @Override
    public void perform() throws MassiveException {
        if (!MConf.get().rosterEnabled) {
            this.sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cFaction rosters are disabled."));
            return;
        }

        MPlayer mPlayer = this.readArg();
        Rel rel = this.readArg(Rel.RECRUIT);

        if (!MConf.get().isRosterChangeable()) {
            msender.msg(LangConf.get().modificationsToRosterNotEnabledMsg);
            return;
        }

        if (!MConf.get().acceptableDefaultRankFactionRelations.contains(rel)) {
            msender.msg(LangConf.get().notValidDefaultRankMsg);
            return;
        }

        if (msenderFaction.isSystemFaction()) {
            msender.msg(LangConf.get().systemFactionMsg);
            return;
        }

        if (!MConf.get().permittedRanksToModifyRoster.contains(msender.getRelationTo(msenderFaction))) {
            msender.msg(LangConf.get().notPermittedToModifyRosterMsg);
            return;
        }

        if (msenderFaction.getRoster().containsKey(mPlayer.getUuid())) {
            msender.msg(LangConf.get().playerAlreadyInRosterMsg);
            return;
        }

        if (msenderFaction.getRoster().size() >= MConf.get().maxRosterSize) {
            msender.msg(LangConf.get().factionRosterFullMsg);
            return;
        }

        msenderFaction.addToRoster(mPlayer.getUuid(), rel);
        msenderFaction.msg(LangConf.get().playerAddedToRosterMsg.replace("%player%", mPlayer.getName()));
    }

}