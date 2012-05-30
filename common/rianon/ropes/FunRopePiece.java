package rianon.ropes;

import codechicken.core.Vector3;

// rope piece has fixed length = 1m
public class FunRopePiece extends IFunEntity
{
    private static final double cBaseLength = 1D;

    private final FunRopeType type_;
    private FunRopeJoint begin_;
    private FunRopeJoint end_;

    private Vector3 tensileForce_ = new Vector3();
    private double tenseLength_ = cBaseLength;
    private double freeLength_ = cBaseLength;

    public FunRopePiece(FunRopeJoint begin, FunRopeJoint end, FunRopeType ropeType)
    {
        super();
        assert begin != null && end != null;

        begin_ = begin;
        end_ = end;
        type_ = ropeType;

        begin_.attachRopePiece(this);
        end_.attachRopePiece(this);

        solveForces();
    }

    public FunRopeJoint begin()
    {
        return begin_;
    }

    public FunRopeJoint end()
    {
        return end_;
    }
    
    public FunRopeType type()
    {
        return type_;
    }

    //    public FunRopeJoint other(FunRopeJoint joint)
    //    {
    //        if (joint == begin_)
    //            return end_;
    //
    //        assert joint == end_;
    //        return begin_;
    //    }
    //
    //    public void replace(FunRopeJoint joint, FunRopeJoint newJoint)
    //    {
    //        assert joint == begin_ || joint == end_;
    //        assert newJoint != null;
    //
    //        if (joint == begin_)
    //            begin_ = newJoint;
    //        else
    //            end_ = newJoint;
    //    }

    public void applyTensileForceTo(FunRopeJoint joint, Vector3 totalForce)
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
        tensileForce_.set(end_.position()).subtract(begin_.position());
        tenseLength_ = tensileForce_.mag();

        final double delta = tenseLength_ - freeLength_;
        final double force = delta > 0 ? type_.cContraction * delta : type_.cExpansion * delta;

        tensileForce_.multiply(force / tenseLength_);
    }

    @Override
    public void solveMotion()
    {
    }

    @Override
    public boolean doesRemainActive()
    {
        return begin_.doesRemainActive() || end_.doesRemainActive();
    }
    
}
