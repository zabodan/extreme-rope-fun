package rianon.ropes;

import net.minecraft.src.*;
import codechicken.core.CoreUtils;
import codechicken.core.Vector3;

// EntityRopeJoint entity is used as IFunRopeAttractor when managed by attractor_
public class EntityRopeJoint extends Entity
    implements IPositionProxy
{
    protected static final double cFirmGripDistSquared = 0.25;
    protected static final double cAttractionFactor = 100.0;
    //protected static Vector3 temp_ = new Vector3();

    //protected EntityPlayer attractor_ = null;
    protected FunRopeJoint ropeJoint_;

    public EntityRopeJoint(World world)
    {
        super(world);
        setSize(0.25f, 0.25f);

        motionX = motionY = motionZ = 0.0;
        preventEntitySpawning = true;
        renderDistanceWeight = 10;
        ropeJoint_ = new FunRopeJoint(0.01, this);
        ropeJoint_.activateNextTick();

        System.out.println(String.format("EntityRopeJoint [%d] born", entityId));
    }
    
    public static EntityRopeJoint create(World world, Vector3 pos)
    {
        EntityRopeJoint entity = new EntityRopeJoint(world);
        entity.setPositionAndRotation(pos.x, pos.y, pos.z, 0, 0);
        System.out.println(String.format("EntityRopeJoint [%d] born @ %s", entity.entityId, pos));
        return entity;
    }
    
    @Override
    public void setPosition(double par1, double par3, double par5)
    {
        System.out.println(String.format("setPosition @ %s", (new Vector3(par1, par3, par5))));
        super.setPosition(par1, par3, par5);
        
        if (ropeJoint_ != null)
            ropeJoint_.updatePositionAndVelocity();
    }
    
//    @Override
//    public void setPosition(double x, double y, double z)
//    {
//        super.setPosition(x, y, z);
//        
//        if (position_ != null)
//            position_.set(x, y, z);
//        
//        if (ropeJoint_ != null)
//            ropeJoint_.position.set(x, y, z);
//        
//        System.out.println(String.format("setPosition @ %s", position_));
//    }
    
    

//    public boolean tryPullingJoint(EntityPlayer player)
//    {
//        if (attractor_ != null)
//            return attractor_ == player;
//
//        attractor_ = player;
//        ropeJoint_.attractTo(this);
//        return true;
//    }
//
//    public void releaseJoint(EntityPlayer player)
//    {
//        if (attractor_ == player)
//        {
//            attractor_ = null;
//            ropeJoint_.release();
//        }
//    }
    
    public FunRopeJoint getJoint()
    {
        return ropeJoint_;
    }

    @Override
    protected void entityInit()
    {
    }

    @Override
    protected void readEntityFromNBT(NBTTagCompound nbt)
    {
        ticksExisted = nbt.getInteger("ticks");
    }

    @Override
    protected void writeEntityToNBT(NBTTagCompound nbt)
    {
        nbt.setInteger("ticks", ticksExisted); 
    }

    @Override
    public void onUpdate()
    {
        ++ticksExisted;

//        if (attractor_ != null && attractor_.isDead)
//            releaseJoint(attractor_);
//
//        if (attractor_ != null)
//            // attractor position
//            position_.set(attractor_.posX, attractor_.posY + attractor_.yOffset, attractor_.posZ);
//        else
//            // free joint
//            position_.set(ropeJoint_.position);
//
//        posX = position_.x;
//        posY = position_.y;
//        posZ = position_.z;


        if ((entityId + ticksExisted) % 100 == 0)
            System.out.println(String.format("Entity [%d] exists for %d ticks.", entityId, ticksExisted));
        
        if (ticksExisted >= 500)
            kill();
    }
    
    @Override
    public void setDead()
    {
        if (!ropeJoint_.getConnectedRopes().isEmpty())
            throw new RuntimeException("Dont you dare kill connected FunRopeJoin!");
            
        System.out.print(CoreUtils.isClient() ? "[client] " : "[server] ");
        System.out.println(String.format("EntityRopeJoint died @ %s after life of %d ticks", Vector3.fromEntity(this), ticksExisted));

        ropeJoint_.deregister();
        ropeJoint_ = null;
        
        super.setDead();
    }
    
//    @Override
//    public Vector3 getPullingForceAt(Vector3 pos)
//    {
//        if (inFirmGrip(pos)) // temp_ = position_ - pos
//            return temp_.set(0,0,0);
//        
//        return temp_.multiply(cAttractionFactor);
//    }
//
//    @Override
//    public boolean inFirmGrip(Vector3 pos)
//    {
//        return temp_.set(position_).subtract(pos).magSquared() <= cFirmGripDistSquared;
//    }
//
//    @Override
//    public Vector3 getPosition()
//    {
//        return position_;
//    }

    
    // IPositionProxy
    
    @Override
    public Vector3 getPosition(Vector3 rv)
    {
        return rv != null ? rv.set(posX, posY, posZ) : new Vector3(posX, posY, posZ);
    }

    @Override
    public Vector3 getVelocity(Vector3 rv)
    {
        return rv != null ? rv.set(motionX, motionY, motionZ) : new Vector3(motionX, motionY, motionZ);
    }

    @Override
    public void getPositionAndVelocity(Vector3 pos, Vector3 vel)
    {
        assert pos != null && vel != null;
        getPosition(pos);
        getVelocity(vel);
    }
    
    @Override
    public void setPositionAndVelocity(Vector3 pos, Vector3 vel)
    {
        assert pos != null && vel != null;
        
        // bypass ropeJoint_.updatePositionAndVelocity();
        super.setPosition(pos.x, pos.y, pos.z);

        motionX = vel.x;
        motionY = vel.y;
        motionZ = vel.z;
    }
    
}
