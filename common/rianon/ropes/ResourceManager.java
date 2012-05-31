package rianon.ropes;

import java.io.File;

import net.minecraft.src.Block;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.ModLoader;
import net.minecraft.src.forge.Configuration;
import net.minecraft.src.forge.MinecraftForge;
import net.minecraft.src.forge.NetworkMod;
import codechicken.core.CoreUtils;

public class ResourceManager
{
    private static boolean initialized = false;
    private static final Configuration config = new Configuration(new File(CoreUtils.getMinecraftDir(), "/config/RopeFun.cfg"));
    private static final BowHandler bowHandler = new BowHandler();
    
    public static String modVersion = "pre-alpha-0.01";
    
    public static Block blockPiton;
    public static Item itemRope;
    
    public static void initialize(NetworkMod mod)
    {
        if (initialized)
            return;

        try
        {
            config.load();
        }
        catch (RuntimeException e)
        {
            // ignore errors, regenerate configuration file
        }
        
        
        // blocks
        blockPiton = new BlockPiton(201);
        ModLoader.registerBlock(blockPiton);

        
        // items
        itemRope = new ItemRope(701);
        
        
        // recipes
        ModLoader.addShapelessRecipe(new ItemStack(blockPiton, 1, 0), new Object[] {
            Block.dirt, Block.dirt });
        
        ModLoader.addShapelessRecipe(new ItemStack(itemRope, 1, 0), new Object[] {
            Block.cloth, Block.cloth });
        

        // entities
        int ropeJointEntityID = ModLoader.getUniqueEntityId();
        ModLoader.registerEntityID(EntityRopeJoint.class, "Rope Joint", ropeJointEntityID);
        //MinecraftForge.registerEntity(EntityRopeJoint.class, mod, ropeJointEntityID, 128, 0, initialized)
        
        
        // TODO ...
        
        
        // other hooks
        MinecraftForge.registerArrowNockHandler(bowHandler);
        MinecraftForge.registerArrowLooseHandler(bowHandler);

        config.save();
        initialized = true;
    }

}
