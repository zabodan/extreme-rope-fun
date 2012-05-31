package rianon.ropes;

import java.util.HashSet;

import net.minecraft.src.*;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

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
            EntityRopeJoint entity = (EntityRopeJoint) en;
            shift.set(x, y, z).subtract(entity.getJoint().position());

            renderOffsetAABB(en.boundingBox, shift.x, shift.y, shift.z);
            
            renderJoint(entity.getJoint(), partialTickTime);
        }
    }

    private void renderJoint(FunRopeJoint joint, float partialTickTime)
    {
        if (joint.firstRenderAttempt(renderFrame))
        {
            // TODO render joint @ joint.pos + shift
            p1.set(joint.position()).add(shift);

//            GL11.glPushMatrix();
//            GL11.glTranslatef((float) p1.x, (float) p1.y, (float) p1.z);
//            GL11.glEnable(GL12.GL_RESCALE_NORMAL);
//
//            byte textureIndex = 46;
//            loadTexture("/gui/items.png");
//
//            Tessellator tess = Tessellator.instance;
//            double u1 = (textureIndex % 16 * 16 + 0) / 256.0;
//            double u2 = (textureIndex % 16 * 16 + 16) / 256.0;
//            double v1 = (textureIndex / 16 * 16 + 0) / 256.0;
//            double v2 = (textureIndex / 16 * 16 + 16) / 256.0;
//
//            GL11.glRotatef(180.0F - renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
//            GL11.glRotatef(-renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
//            tess.startDrawingQuads();
//            tess.setNormal(0.0F, 1.0F, 0.0F);
//            tess.addVertexWithUV(-0.5, -0.5, 0, u1, v2);
//            tess.addVertexWithUV( 0.5, -0.5, 0, u2, v2);
//            tess.addVertexWithUV( 0.5,  0.5, 0, u2, v1);
//            tess.addVertexWithUV(-0.5,  0.5, 0, u1, v1);
//            tess.draw();
//
//            GL11.glDisable(GL12.GL_RESCALE_NORMAL);
//            GL11.glPopMatrix();
            
            //final double w = EntityRopeJoint.cWidth, h = EntityRopeJoint.cHeight;
            //AxisAlignedBB bbox = AxisAlignedBB.getBoundingBoxFromPool(-w/2, 0, -w/2, w/2, h, w/2); 
            //renderOffsetAABB(bbox, p1.x, p1.y, p1.z);
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
        //p1.set(rp.begin().position()).add(shift);
        //p2.set(rp.end().position()).add(shift);
    }
}
