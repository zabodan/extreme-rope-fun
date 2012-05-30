package rianon.ropes;

import java.util.HashSet;

import net.minecraft.src.Entity;
import net.minecraft.src.Render;
import codechicken.core.Vector3;

public class RenderRope extends Render
{
    public static int renderFrame = 0;
    private Vector3 shift = new Vector3();
    private Vector3 p1 = new Vector3();
    private Vector3 p2 = new Vector3();
    
    @Override
    public void doRender(Entity en, double x, double y, double z, float yaw, float partialTickTime)
    {
        if (en instanceof EntityRopeJoint)
        {
            EntityRopeJoint entity = (EntityRopeJoint)en; 
            shift.set(x, y, z).subtract(entity.getPosition());

            // do render
            renderJoint(entity.getJoint(), partialTickTime);
        }
    }
    
    private void renderJoint(FunRopeJoint joint, float partialTickTime)
    {
        if (joint.firstRenderAttempt(renderFrame))
        {
            // TODO render joint @ joint.pos + shift
            p1.set(joint.position).add(shift);
            
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
        
        // TODO render rope piece @ piece.position + shift
        p1.set(rp.begin().position).add(shift);
        p2.set(rp.end().position).add(shift);
    }
}
