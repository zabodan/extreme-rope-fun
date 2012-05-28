package rianon.ropes;

import java.io.File;

import rianon.aa.BlockAdvMachine;
import rianon.aa.ItemAdvMachine;

import net.minecraft.src.*;
import net.minecraft.src.forge.Configuration;
import net.minecraft.src.forge.MinecraftForge;
import codechicken.core.CoreUtils;

public class ResourceManager
{
    private static boolean initialized = false;
    private static final Configuration config = new Configuration(new File(CoreUtils.getMinecraftDir(), "/config/RopeFun.cfg"));
    private static final BowHandler bowHandler = new BowHandler();
    
    public static final Block blockPiton = new BlockPiton(201);
    public static final Item itemRope = new ItemRope(701);
    
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
        ModLoader.registerBlock(blockPiton);

        
        ModLoader.addShapelessRecipe(new ItemStack(blockPiton, 1, 0), new Object[] {
            Block.dirt, Block.dirt });
        
        ModLoader.addShapelessRecipe(new ItemStack(itemRope, 1, 0), new Object[] {
            Block.cloth, Block.cloth });
        
        // ...
        
        // register hooks
        MinecraftForge.registerArrowNockHandler(bowHandler);
        MinecraftForge.registerArrowLooseHandler(bowHandler);

        config.save();
        initialized = true;
    }

}
