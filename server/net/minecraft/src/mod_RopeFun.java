package net.minecraft.src;

import net.minecraft.server.MinecraftServer;
import net.minecraft.src.forge.NetworkMod;
import rianon.ropes.FunRegistry;
import rianon.ropes.ResourceManager;

public class mod_RopeFun extends NetworkMod
{
    private static int ticksRan = 0;
    
    @Override
    public String getVersion()
    {
        return ResourceManager.modVersion;
    }

    @Override
    public void load()
    {
        ResourceManager.initialize(this);
        ModLoader.setInGameHook(this, true, false); // on server third parameter does not matter
    }
    
    @Override
    public boolean onTickInGame(MinecraftServer minecraftServer)
    {
        ++ticksRan;
        return true;
    }

    @Override
    public String getPriorities()
    {
        return "required-after:mod_CodeChickenCore";
    }
    
    @Override
    public boolean clientSideRequired()
    {
        return true;
    }
    
    public static int getCurrentTick()
    {
        return ticksRan;
    }
}
