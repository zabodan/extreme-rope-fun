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

    private Vector3 tensileForce_ = new Vector3();
    private double tensileLength_ = cBaseLength;

    public IRopePiece(IRopeJoint begin, IRopeJoint end)
    {
        assert (begin != null && end != null);

        begin_ = begin;
        end_ = end;

        begin_.attachRope(this);
        end_.attachRope(this);
        solveForces();
    }

    public IRopeJoint begin()
    {
        return begin_;
    }

    public IRopeJoint end()
    {
        return end_;
    }

    public abstract double getRopePieceMass();

    public abstract double getContractionConst();

    public abstract double getExpansionConst();

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

    public void solveForces()
    {
        tensileForce_.set(end_.position).subtract(begin_.position);
        tensileLength_ = tensileForce_.mag();

        final double delta = tensileLength_ - cBaseLength;
        final double force = delta > 0 ? getContractionConst() * delta : getExpansionConst() * delta;

        tensileForce_.multiply(force / tensileLength_);
    }

    public void applyTensileForceTo(IRopeJoint joint, Vector3 totalForce)
    {
        assert (joint == begin_ || joint == end_);
        if (joint == begin_)
            totalForce.add(tensileForce_);
        else
            totalForce.subtract(tensileForce_);
    }

}
