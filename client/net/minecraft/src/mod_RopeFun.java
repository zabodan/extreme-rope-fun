package net.minecraft.src;

import rianon.ropes.RopeFunManager;

public class mod_RopeFun extends BaseMod
{
    @Override
    public String getVersion()
    {
        return "0.0.1";
    }

    @Override
    public void load()
    {
        RopeFunManager.initialize();
    }

    @Override
    public String getPriorities()
    {
        return "required-after:mod_CodeChickenCore";
    }
}
