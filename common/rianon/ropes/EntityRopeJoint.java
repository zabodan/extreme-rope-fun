package rianon.ropes;

import net.minecraft.src.*;
import codechicken.core.Vector3;

// EntityRopeJoint entity is used as IFunRopeAttractor when managed by dragMan_
public class EntityRopeJoint extends Entity
    implements IFunRopeAttractor
{
    protected static final double cFirmGripDistSquared = 0.25;
    protected static final double cAttractionFactor = 100.0;
    protected static Vector3 temp_ = new Vector3();

    protected EntityPlayer dragMan_ = null;
    protected FunRopeJoint ropeJoint_;
    protected Vector3 position_;

    protected EntityRopeJoint(World world, Vector3 pos)
    {
        super(world);
        preventEntitySpawning = true;

        ropeJoint_ = new FunRopeJoint(0.01f, pos);
        position_ = pos.copy();
    }

    public boolean tryToDragJoint(EntityPlayer player)
    {
        if (dragMan_ != null)
            return dragMan_ == player;

        dragMan_ = player;
        ropeJoint_.attractTo(this);
        return true;
    }

    public void releaseJoint(EntityPlayer player)
    {
        if (dragMan_ == player)
        {
            dragMan_ = null;
            ropeJoint_.release();
        }
    }

    @Override
    protected void entityInit()
    {
    }

    @Override
    protected void readEntityFromNBT(NBTTagCompound var1)
    {
    }

    @Override
    protected void writeEntityToNBT(NBTTagCompound var1)
    {
    }

    @Override
    public void onUpdate()
    {
        if (dragMan_ != null && dragMan_.isDead)
            releaseJoint(dragMan_);

        if (dragMan_ != null)
            // attractor position
            position_.set(dragMan_.posX, dragMan_.posY + dragMan_.yOffset, dragMan_.posZ);
        else
            // free joint
            position_.set(ropeJoint_.position);

        posX = position_.x;
        posY = position_.y;
        posZ = position_.z;

        super.onUpdate();
    }

    @Override
    public Vector3 getDragForceAt(Vector3 pos)
    {
        return temp_.set(position_).subtract(pos).multiply(cAttractionFactor);
    }

    @Override
    public boolean inFirmGrip(Vector3 pos)
    {
        return temp_.set(pos).subtract(position_).magSquared() < cFirmGripDistSquared;
    }

    @Override
    public Vector3 getPosition()
    {
        return position_;
    }

}
