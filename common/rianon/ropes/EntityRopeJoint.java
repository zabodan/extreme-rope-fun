package rianon.ropes;

import net.minecraft.src.Entity;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.World;
import codechicken.core.CoreUtils;
import codechicken.core.Vector3;

public class EntityRopeJoint extends Entity
    implements IFunPositionProxy
{
    private FunRopeJoint funJoint_;

    public EntityRopeJoint(World world)
    {
        super(world);
        setSize(0.25f, 0.25f);

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
        
//        if (motionX * motionX + motionY * motionY + motionZ * motionZ > 0.1)
//            funJoint_.activateNextTick();
        
        if (ticksExisted % 10 == 0)
            System.out.println(String.format("Entity [%d] exists for %d ticks.", entityId, ticksExisted));
        
        
        if (ticksExisted >= 500 || posY < 60)
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
        // bypass ropeJoint_.updatePositionAndVelocity();
        super.setPosition(pos.x, pos.y, pos.z);
        motionX = vel.x; motionY = vel.y; motionZ = vel.z;
    }
    
}
