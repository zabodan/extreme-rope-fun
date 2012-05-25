package rianon.ropes;

import java.io.File;

import net.minecraft.src.forge.Configuration;
import net.minecraft.src.forge.MinecraftForge;
import codechicken.core.CoreUtils;

public class RopeFunManager
{
    private static boolean initialized = false;
    private static Configuration config = new Configuration(new File(CoreUtils.getMinecraftDir(), "/config/RopeFun.cfg"));
    private static BowHandler bowHandler = new BowHandler();

    public static void initialize()
    {
        if (initialized)
            return;

        try
        {
            config.load();
        }
        catch (RuntimeException e)
        {
            // ignore errors, regenerate config
        }
        
        
        // init blocks, items, entities, recipies
        
        // ...
        
        // register hooks
        MinecraftForge.registerArrowNockHandler(bowHandler);
        MinecraftForge.registerArrowLooseHandler(bowHandler);

        config.save();
        initialized = true;
    }

}
