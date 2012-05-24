package rianon.ropes;

import java.util.ArrayList;

import net.minecraft.src.*;


public class EntityRope extends Entity
{
    protected ArrayList<EntityRopePiece> pieces;
    
    public EntityRope(World world, int length)
    {
        super(world);
        pieces = new ArrayList<>(length);
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
