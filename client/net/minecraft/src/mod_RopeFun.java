package net.minecraft.src;

import java.util.Map;

import codechicken.core.CoreUtils;

import net.minecraft.client.Minecraft;
import rianon.ropes.EntityRopeJoint;
import rianon.ropes.RenderRope;
import rianon.ropes.ResourceManager;

public class mod_RopeFun extends BaseMod
{
    public static int ropeRenderID;
    
    @Override
    public String getVersion()
    {
        return "0.0.1";
    }

    @Override
    public void load()
    {
        ResourceManager.initialize();
        
        
        CoreUtils.addLocalization(ResourceManager.blockPiton.getBlockName() + ".name", "Piton");
        CoreUtils.addLocalization(ResourceManager.itemRope.getItemName() + ".name", "Rope");
        //ModLoader.addName(ResourceManager.blockPiton, "Piton");
        //ModLoader.addName(ResourceManager.itemRope, "Rope");
        
        ropeRenderID = 0; //ModLoader.getUniqueBlockModelID(this, false);
        
        ModLoader.setInGameHook(this, true, false);
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
    
    @Override
    public boolean renderWorldBlock(RenderBlocks rb, IBlockAccess ba, int i, int j, int k, Block block, int model)
    {
        System.out.println("renderWorldBlock");
        if (model == ropeRenderID)
        {
//            if (block instanceof BlockPiton)
//                return RenderPiton.renderWorldBlock(rb, ba, i, j, k, block);
        }
        return false;
    }
    
    @Override
    public void renderInvBlock(RenderBlocks rb, Block block, int meta, int model)
    {
        System.out.println("renderInvBlock");
        if (model == ropeRenderID)
        {
//            if (block instanceof BlockPiton)
//                RenderPiton.renderInvBlock(rb, block);
        }
    }
    
    @Override
    public boolean onTickInGame(float partOfTick, Minecraft mc)
    {
        if (++RenderRope.renderFrame % 50 == 0)
            //System.out.println("onTickInGame, elapsed = " + partOfTick);
            System.out.print("o");
        
        return true;
    }
    
    
}
