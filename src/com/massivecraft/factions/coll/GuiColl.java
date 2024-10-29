package com.massivecraft.factions.coll;

import com.massivecraft.factions.entity.GuiConf;
import com.massivecraft.factions.entity.LangConf;
import com.massivecraft.massivecore.MassiveCore;
import com.massivecraft.massivecore.store.Coll;

public class GuiColl extends Coll<GuiConf>
{
    // -------------------------------------------- //
    // INSTANCE & CONSTRUCT
    // -------------------------------------------- //

    private static GuiColl i = new GuiColl();
    public static GuiColl get() { return i; }

    // -------------------------------------------- //
    // STACK TRACEABILITY
    // -------------------------------------------- //

    @Override
    public void onTick()
    {
        super.onTick();
    }

    // -------------------------------------------- //
    // OVERRIDE
    // -------------------------------------------- //

    @Override
    public void setActive(boolean active)
    {
        super.setActive(active);
        if (!active) return;
        GuiConf.set(this.get(MassiveCore.INSTANCE, true));
    }

}
