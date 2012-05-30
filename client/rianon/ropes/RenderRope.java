package rianon.ropes;

import java.util.HashSet;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import net.minecraft.src.Entity;
import net.minecraft.src.Render;
import net.minecraft.src.Tessellator;
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
            

            GL11.glPushMatrix();
            GL11.glTranslatef((float)p1.x, (float)p1.y, (float)p1.z);
            GL11.glEnable(GL12.GL_RESCALE_NORMAL);
            
            float scale = 1.f;
            GL11.glScalef(scale, scale, scale);
            
            byte textureIndex = 46;
            this.loadTexture("/gui/items.png");
            
            Tessellator tess = Tessellator.instance;
            float var13 = (float)(textureIndex % 16 * 16 + 0) / 256.0F;
            float var14 = (float)(textureIndex % 16 * 16 + 16) / 256.0F;
            float var15 = (float)(textureIndex / 16 * 16 + 0) / 256.0F;
            float var16 = (float)(textureIndex / 16 * 16 + 16) / 256.0F;
            
            float var17 = 1.0F;
            float var18 = 0.5F;
            float var19 = 0.5F;
            
            GL11.glRotatef(180.0F - this.renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
            GL11.glRotatef(-this.renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
            tess.startDrawingQuads();
            tess.setNormal(0.0F, 1.0F, 0.0F);
            tess.addVertexWithUV((double)(0.0F - var18), (double)(0.0F - var19), 0.0D, (double)var13, (double)var16);
            tess.addVertexWithUV((double)(var17 - var18), (double)(0.0F - var19), 0.0D, (double)var14, (double)var16);
            tess.addVertexWithUV((double)(var17 - var18), (double)(1.0F - var19), 0.0D, (double)var14, (double)var15);
            tess.addVertexWithUV((double)(0.0F - var18), (double)(1.0F - var19), 0.0D, (double)var13, (double)var15);
            tess.draw();
            
            GL11.glDisable(GL12.GL_RESCALE_NORMAL);
            GL11.glPopMatrix();
        
        
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
