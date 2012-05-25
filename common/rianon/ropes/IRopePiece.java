package rianon.ropes;

import net.minecraft.src.Entity;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.World;
import codechicken.core.Vector3;

// rope piece has fixed length = 1m
public abstract class IRopePiece
{
    private static final double cBaseLength = 1D; 
    
    private IRopeJoint begin_;
    private IRopeJoint end_;
    
    private double currentLength_;
    private double currentForce_;

    public IRopePiece(IRopeJoint begin, IRopeJoint end)
    {
        assert (begin != null && end != null);

        begin_ = begin;
        end_ = end;
    }

    public IRopeJoint begin()
    {
        return begin_;
    }

    public IRopeJoint end()
    {
        return end_;
    }

    public abstract double mass();
    
    public abstract double contractionConst();
    
    public abstract double expansionConst();

    public double springLength()
    {
        return currentLength_;
    }
    
    public double springForce()
    {
        return currentForce_;
    }
    
    

    public IRopeJoint other(IRopeJoint joint)
    {
        if (joint == begin_)
            return end_;

        assert (joint == end_);
        return begin_;
    }

    public void replace(IRopeJoint joint, IRopeJoint newJoint)
    {
        assert (joint == begin_ || joint == end_);
        assert (newJoint != null);

        if (joint == begin_)
            begin_ = newJoint;
        else
            end_ = newJoint;
    }
    
    
    public void onGameTick()
    {
        currentLength_ = begin_.position().copy().subtract(end_.position()).mag();

        double delta = currentLength_ - cBaseLength;
        currentForce_ = delta > 0 ? contractionConst() * delta : expansionConst() * delta; 
    }
    

    //    public double calcLength()
    //    {
    //        return begin().position().copy().subtract(end().position()).mag(); 
    //    }
    //
    //
    //    public Vector3 otherJointDirection(IRopeJoint joint)
    //    {
    //        return other(joint).position_.copy().subtract(joint.position_);
    //    }
}
