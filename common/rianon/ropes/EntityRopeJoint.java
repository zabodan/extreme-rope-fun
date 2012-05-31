package rianon.ropes;

import java.util.List;

import net.minecraft.src.*;
import codechicken.core.CoreUtils;
import codechicken.core.Vector3;

public class EntityRopeJoint extends Entity
    implements IFunPositionProxy
{
    public static final float cWidth = .25f;
    public static final float cHeight = .25f;
    
    private FunRopeJoint funJoint_;

    public EntityRopeJoint(World world)
    {
        super(world);
        setSize(cWidth, cHeight);

        motionX = motionY = motionZ = 0.0;
        preventEntitySpawning = true;
        renderDistanceWeight = 10;
        funJoint_ = new FunRopeJoint(0.01, this);
        funJoint_.activateNextTick();

        System.out.println(String.format("EntityRopeJoint [%d] born", entityId));
    }
    
    public static EntityRopeJoint create(World world, Vector3 pos)
    {
        EntityRopeJoint entity = new EntityRopeJoint(world);
        entity.setPositionAndRotation(pos.x, pos.y, pos.z, 0, 0);
        return entity;
    }
    
    public FunRopeJoint getJoint()
    {
        return funJoint_;
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
        
        // will run for whole server once per tick from first joint entity
        FunRegistry.instance().onGameTick();
        
        if (!onGround)
            funJoint_.activateNextTick();
        
//        if (motionX * motionX + motionY * motionY + motionZ * motionZ > 0.1)
//            funJoint_.activateNextTick();
        
        
//        if ((entityId + ticksExisted) % 10 == 0)
//            System.out.println(String.format("Entity [%d] exists for %d ticks.", entityId, ticksExisted));
        
        
        //System.out.println(String.format("Entity [%d] moves with %f, %f, %f speed.", entityId, motionX, motionY, motionZ));
        
        if (ticksExisted >= 100 || posY < 60)
            kill();
    }
    
    @Override
    public void setDead()
    {
        if (!funJoint_.getConnectedRopes().isEmpty())
            throw new RuntimeException("Dont you dare kill connected FunRopeJoin!");
            
        System.out.print(CoreUtils.isClient() ? "[client] " : "[server] ");
        System.out.println(String.format("EntityRopeJoint died @ %s after life of %d ticks", Vector3.fromEntity(this), ticksExisted));

        funJoint_.deregister();
        funJoint_ = null;
        
        super.setDead();
        
//        List locals = worldObj.getEntitiesWithinAABBExcludingEntity(this, boundingBox.expand(1, 1, 1));
//        for (Object o : locals)
//        {
//            if (o instanceof EntityRopeJoint)
//                ((EntityRopeJoint)o).getJoint().activateNextTick();
//        }
    }

    @Override
    public void getPositionAndVelocity(Vector3 pos, Vector3 vel)
    {
        pos.set(posX, posY, posZ);
        vel.set(motionX, motionY, motionZ);
    }
    
    @Override
    public void setPositionAndVelocity(Vector3 pos, Vector3 vel)
    {
        System.out.println(String.format("before = %f, %f, %f", motionX, motionY, motionZ));
        
        moveEntity(pos.x - posX, pos.y - posY, pos.z - posZ);
        
        System.out.println(String.format("middle = %f, %f, %f", motionX, motionY, motionZ));
        
        motionX = vel.x;
        motionY = onGround ? -0.0001 : vel.y;
        motionZ = vel.z;

        System.out.println(String.format("returned = %f, %f, %f", vel.x, vel.y, vel.z));
        System.out.println(String.format("after = %f, %f, %f\n", motionX, motionY, motionZ));
    }
    
    @Override
    public boolean canBeCollidedWith()
    {
        return true;
    }
    
    @Override
    public boolean canBePushed()
    {
        return true;
    }
    
    @Override
    public AxisAlignedBB getBoundingBox()
    {
        return boundingBox;
    }
    
    @Override
    public AxisAlignedBB getCollisionBox(Entity en)
    {
        return en.boundingBox; 
    }
    
}
