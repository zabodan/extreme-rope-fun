package net.minecraft.src;

import java.util.Map;

import net.minecraft.client.Minecraft;
import net.minecraft.src.forge.NetworkMod;
import rianon.ropes.EntityRopeJoint;
import rianon.ropes.FunRegistry;
import rianon.ropes.RenderRope;
import rianon.ropes.ResourceManager;
import codechicken.core.CoreUtils;

public class mod_RopeFun extends NetworkMod
{
    public static int sampleRenderID;
    
    @Override
    public String getVersion()
    {
        return ResourceManager.modVersion;
    }

    @Override
    public void load()
    {
        ResourceManager.initialize();
        
        CoreUtils.addLocalization(ResourceManager.blockPiton.getBlockName() + ".name", "Piton");
        CoreUtils.addLocalization(ResourceManager.itemRope.getItemName() + ".name", "Rope");
        
        sampleRenderID = 0; //ModLoader.getUniqueBlockModelID(this, false);
        
        ModLoader.setInGameHook(this, true, false); // false == each frame, true == timed (slower)
    }

    
    @Override
    public String getPriorities()
    {
        return "required-after:mod_CodeChickenCore";
    }
    
    @Override
    public void addRenderer(Map renderMap)
    {
        renderMap.put(EntityRopeJoint.class, new RenderRope()); 
    }
    
//    @Override
//    public boolean renderWorldBlock(RenderBlocks rb, IBlockAccess ba, int i, int j, int k, Block block, int model)
//    {
//        System.out.println("renderWorldBlock");
//        if (model == sampleRenderID)
//        {
//        }
//        return false;
//    }
//    
//    @Override
//    public void renderInvBlock(RenderBlocks rb, Block block, int meta, int model)
//    {
//        System.out.println("renderInvBlock");
//        if (model == sampleRenderID)
//        {
//        }
//    }
    
    @Override
    public boolean onTickInGame(float partOfTick, Minecraft mc)
    {
        if (!CoreUtils.isClient())
            // update physics only in SSP mode
            FunRegistry.instance().onGameTick();

        ++RenderRope.renderFrame;
        
        return true;
    }
    
    @Override
    public boolean serverSideRequired()
    {
        return false;
    }
}
