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
