package net.minecraft.src;

import rianon.ropes.FunRegistry;
import rianon.ropes.ResourceManager;
import net.minecraft.server.MinecraftServer;
import net.minecraft.src.forge.NetworkMod;

public class mod_RopeFun extends NetworkMod
{
    @Override
    public String getVersion()
    {
        return ResourceManager.modVersion;
    }

    @Override
    public void load()
    {
        ResourceManager.initialize();
        ModLoader.setInGameHook(this, true, false);
    }
    
    @Override
    public boolean onTickInGame(MinecraftServer minecraftServer)
    {
        FunRegistry.instance().onGameTick();
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
}
