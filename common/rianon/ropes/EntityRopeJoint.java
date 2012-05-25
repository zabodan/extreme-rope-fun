package rianon.ropes;

import java.util.ArrayList;

import net.minecraft.src.Entity;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.Vec3D;
import net.minecraft.src.World;

public class EntityRopeJoint extends Entity
{
    protected ArrayList<EntityRopePiece> pieces_;
    protected Vec3D position_;
    

    public EntityRopeJoint(World world)
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
