package rianon.ropes;

import net.minecraft.src.*;
import net.minecraft.src.forge.IArrowLooseHandler;
import net.minecraft.src.forge.IArrowNockHandler;
import codechicken.core.Vector3;

public class BowHandler
    implements IArrowLooseHandler, IArrowNockHandler
{

    @Override
    public ItemStack onArrowNock(ItemStack itemstack, World world, EntityPlayer player)
    {
        System.out.println("onArrowNock");
        return null;
    }

    @Override
    public boolean onArrowLoose(ItemStack itemstack, World world, EntityPlayer player, int heldTime)
    {
        System.out.println("onArrowLoose, heldTime = " + heldTime);

        Vector3 pos = Vector3.fromEntity(player);
        pos.y += (double)player.getEyeHeight();
        
        final float yaw = player.rotationYaw;
        final float pitch = player.rotationPitch;
        final double dx = (double)(-MathHelper.sin(yaw / 180.0F * (float)Math.PI) * MathHelper.cos(pitch / 180.0F * (float)Math.PI));
        final double dz = (double)(MathHelper.cos(yaw / 180.0F * (float)Math.PI) * MathHelper.cos(pitch / 180.0F * (float)Math.PI));
        final double dy = (double)(-MathHelper.sin(pitch / 180.0F * (float)Math.PI));
        
        Vector3 dir = new Vector3(dx, dy, dz);
        
        System.out.println("pos = " + pos);
        System.out.println("dir = " + dir);
        
        Vec3D start = pos.toVec3D();
        Vec3D end = pos.copy().add(dir.copy().multiply(64)).toVec3D();
        
        MovingObjectPosition mop = world.rayTraceBlocks(start, end);
        if (mop != null && mop.hitVec != null)
        {
            System.out.println("target = " + Vector3.fromVec3D(mop.hitVec));
            System.out.println("hit side = " + mop.sideHit);
        }
        
        return false;
    }

}
