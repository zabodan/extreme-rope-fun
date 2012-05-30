package rianon.ropes;

import java.util.HashSet;

import net.minecraft.src.Entity;
import net.minecraft.src.Render;
import net.minecraft.src.forge.MinecraftForge;

public class RenderRope extends Render
{
    public static int renderFrame = 0;
    
    @Override
    public void doRender(Entity en, double x, double y, double z, float yaw, float partialTickTime)
    {
        //if (renderFrame % 50 == 0)
            //System.out.print("r");
        
        if (en instanceof EntityRopeJoint)
            renderJoint(((EntityRopeJoint) en).getJoint(), partialTickTime);            
    }
    
    private void renderJoint(FunRopeJoint joint, float partialTickTime)
    {
        if (joint.firstRenderAttempt(renderFrame))
        {
            // TODO render joint
        }
        
        HashSet<FunRopePiece> ropes = joint.getConnectedRopes();
        if (ropes == null)
            return;
        
        for (FunRopePiece rp : ropes)
            renderRope(rp, partialTickTime);
    }

    private void renderRope(FunRopePiece rp, float partialTickTime)
    {
        if (!rp.firstRenderAttempt(renderFrame))
            return;
        
        // TODO render rope piece
    }
}
