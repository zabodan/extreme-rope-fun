package rianon.ropes;

import net.minecraft.src.*;

public class EntityGrapple extends EntityThrowable
{

    public EntityGrapple(World world, EntityLiving owner)
    {
        super(world, owner);
    }

    @Override
    protected void onImpact(MovingObjectPosition target)
    {
    }

}
