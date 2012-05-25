package rianon.ropes;

import codechicken.core.Vector3;

// rope piece has fixed length = 1m
public abstract class IRopePiece extends IFunEntity
{
    private static final double cBaseLength = 1D;

    private IRopeJoint begin_;
    private IRopeJoint end_;

    private Vector3 tensileForce_ = new Vector3();
    private double tensileLength_ = cBaseLength;

    public IRopePiece(IRopeJoint begin, IRopeJoint end)
    {
        super(); // start as active entity
        assert begin != null && end != null;

        begin_ = begin;
        end_ = end;

        begin_.attachRopePiece(this);
        end_.attachRopePiece(this);
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

        assert joint == end_;
        return begin_;
    }

    public void replace(IRopeJoint joint, IRopeJoint newJoint)
    {
        assert joint == begin_ || joint == end_;
        assert newJoint != null;

        if (joint == begin_)
            begin_ = newJoint;
        else
            end_ = newJoint;
    }

    public void applyTensileForceTo(IRopeJoint joint, Vector3 totalForce)
    {
        assert joint == begin_ || joint == end_;
        if (joint == begin_)
            totalForce.add(tensileForce_);
        else
            totalForce.subtract(tensileForce_);
    }

    @Override
    public void solveForces()
    {
        tensileForce_.set(end_.position).subtract(begin_.position);
        tensileLength_ = tensileForce_.mag();

        final double delta = tensileLength_ - cBaseLength;
        final double force = delta > 0 ? getContractionConst() * delta : getExpansionConst() * delta;

        tensileForce_.multiply(force / tensileLength_);
    }

    @Override
    public void solveMotion()
    {
    }

    @Override
    public boolean isActiveEntity()
    {
        return begin_.isActiveEntity() || end_.isActiveEntity();
    }

}
