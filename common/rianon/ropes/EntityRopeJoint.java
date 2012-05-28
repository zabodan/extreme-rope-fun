package rianon.ropes;

import java.util.HashSet;

import net.minecraft.src.Entity;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.World;
import codechicken.core.Vector3;

public class EntityRopeJoint extends Entity
    implements IRopeJoint
{
    private static final double cGravitation = -9.81;
    private static final double cAirFriction = -0.02;
    private static final double cManagerTension = 10;
    private static final double cMaxVelocity = 8; // m/s

    // temporary Vector3 to help computations without allocating memory
    private static Vector3 temp_ = new Vector3();

    
    protected int funEntityID_;
    protected Vector3 position_;
    protected Vector3 velocity_;
    protected Vector3 totalForce_;
    protected double totalMass_;
    
    protected HashSet<RopePieceBase> ropes_ = new HashSet<>();
    

    protected EntityPlayer manager_ = null;
     
    
    public EntityRopeJoint(World world)
    {
        super(world);
        super.preventEntitySpawning = true;
        
        position_ = Vector3.fromEntity(this);
        velocity_ = new Vector3(motionX, motionY, motionZ);
        totalForce_ = new Vector3(0, 0, 0);
        totalMass_ = getRopeJointOwnMass();
        FunRegistry.instance().register(this);
    }

    @Override
    protected void entityInit() {}

    @Override
    protected void readEntityFromNBT(NBTTagCompound var1) {}

    @Override
    protected void writeEntityToNBT(NBTTagCompound var1) {}
    
    @Override
    public void onUpdate() {}
    


    
    @Override
    public int getFunEntityID()
    {
        return funEntityID_;
    }

    @Override
    public void setFunEntityID(int id)
    {
        funEntityID_ = id;
    }

    @Override
    public void solveForces()
    {
        // start with gravitation
        totalForce_.set(0, cGravitation * totalMass_, 0);

        // add air drag
        totalForce_.add(temp_.set(velocity_).multiply(cAirFriction));

        // add tensile forces from each attached rope piece
        for (RopePieceBase rp : ropes_)
            rp.applyTensileForceTo(this, totalForce_);
        
        if (manager_ != null)
        {
            if (manager_.isDead)
            {
                manager_ = null;
            }
            else
            {
                // as temporary solution, add big tensile force towards player
                totalForce_.add(temp_.set(manager_.posX, manager_.posY + manager_.yOffset, manager_.posZ).subtract(position_).multiply(cManagerTension));
            }
        }
    }

    @Override
    public void solveMotion()
    {
        final double timeSpan = 0.05D; // as long as there are 20 ticks per second
        assert totalMass_ > 0;

        position_.add(temp_.set(velocity_).multiply(timeSpan));
        velocity_.add(temp_.set(totalForce_).multiply(timeSpan / totalMass_));

        // limit maximum velocity
        final double v = velocity_.mag();
        if (v > cMaxVelocity)
            velocity_.multiply(cMaxVelocity / v);
        
        lastTickPosX = prevPosX = posX;
        lastTickPosY = prevPosY = posY;
        lastTickPosZ = prevPosZ = posZ;
        
        posX = position_.x;
        posY = position_.y;
        posZ = position_.z;
        
        motionX = velocity_.x;
        motionY = velocity_.y;
        motionZ = velocity_.z;
    }

    @Override
    public boolean isActiveEntity()
    {
        return !velocity_.isZero();
    }

    @Override
    public double getRopeJointOwnMass()
    {
        return 1;
    }

    @Override
    public void attachRopePiece(RopePieceBase rp)
    {
        if (ropes_.add(rp))
            totalMass_ += rp.getRopePieceMass();
    }

    @Override
    public void detachRopePiece(RopePieceBase rp)
    {
        if (ropes_.remove(rp))
            totalMass_ -= rp.getRopePieceMass();
    }

    @Override
    public Vector3 position()
    {
        return position_;
    }

    @Override
    public Vector3 velocity()
    {
        return velocity_;
    }

}
