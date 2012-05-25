package rianon.ropes;

import java.util.HashSet;

import codechicken.core.Vector3;

public abstract class IRopeJoint extends IFunEntity
{
    private static final double cGravitation = -9.81;
    private static final double cAirFriction = -0.02;
    private static final double cMaxVelocity = 8; // m/s

    // temporary Vector3 to help computations without allocating memory
    private static Vector3 temp_ = new Vector3();

    private HashSet<IRopePiece> pieces_;
    private double totalMass_;
    private boolean inGround_ = false;

    public Vector3 position = new Vector3();
    public Vector3 velocity = new Vector3();
    public Vector3 totalForce = new Vector3();

    public IRopeJoint()
    {
        totalMass_ = getRopeJointMass();
        solveForces();
    }

    public abstract double getRopeJointMass();

    public void attachRopePiece(IRopePiece rp)
    {
        if (pieces_.add(rp))
            totalMass_ += rp.getRopePieceMass() / 2.0;
    }

    public void detachRopePiece(IRopePiece rp)
    {
        if (pieces_.remove(rp))
            totalMass_ -= rp.getRopePieceMass() / 2.0;
    }

    @Override
    public void solveForces()
    {
        // start with gravitation
        totalForce.set(0, cGravitation * totalMass_, 0);

        // add air friction
        totalForce.add(temp_.set(velocity).multiply(cAirFriction));

        // add tensile forces from each attached rope piece
        for (IRopePiece rp : pieces_)
            rp.applyTensileForceTo(this, totalForce);

        if (inGround_)
        {
            // compute ground friction, absorption and repulsion, then add
            // totalForce_.add(tempGroundFriction_).add(tempGroundAbsorption_).add(tempGroundRepulsion_);
        }
    }

    @Override
    public void solveMotion()
    {
        final double timeSpan = 0.05D; // as long as there are 20 ticks per second

        position.add(temp_.set(velocity).multiply(timeSpan));
        velocity.add(temp_.set(totalForce).multiply(timeSpan / totalMass_));

        // limit maximum velocity
        final double v = velocity.mag();
        if (v > cMaxVelocity)
            velocity.multiply(cMaxVelocity / v);
    }

    @Override
    public boolean isActiveEntity()
    {
        return !velocity.isZero();
    }

}
