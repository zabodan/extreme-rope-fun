package rianon.ropes;

import net.minecraft.src.*;


//abstract interface IConnection
//{
//    public abstract Vec3D position();
//}
//
//abstract interface IEndPoint extends IConnection
//{
//    public abstract EntityRopePiece ropeOne();
//}
//
//abstract interface IRopeJoint extends IConnection
//{
//    public abstract EntityRopePiece ropeOne();
//    public abstract EntityRopePiece ropeTwo();
//}



// # = Fixed End (Grapple or Piton)
// o = Elastic Joint
// -- = 1m Rope Piece
//
// #--o--o--o--o--o


// rope piece has fixed length = 1m
public class EntityRopePiece extends Entity
{
    public static final double balancedLength = 1D;
    protected EntityRopeJoint begin_;
    protected EntityRopeJoint end_;
    protected double mass_;
    
    public double tempCurrentLength_;
    public double tempCurrentForce_;
    
    public double calcLength()
    {
        return begin_.position_.distanceTo(end_.position_); 
    }
    
    public EntityRopeJoint other(EntityRopeJoint joint)
    {
        return joint == begin_ ? end_ : joint == end_ ? begin_ : null;
    }
    
    public Vec3D direction(EntityRopeJoint joint)
    {
        return other(joint).position_.subtract(joint.position_);
    }
    
    public EntityRopePiece(World world)
    {
        super(world);
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

}
