package rianon.ropes;

import java.util.HashSet;

import codechicken.core.Vector3;

public class FunRopeJoint extends IFunEntity
{
    private static final double cGravitation = -9.81;
    private static final double cAirFriction = -0.02;
    private static final double cMaxVelocity = 8; // m/s

    // temporary Vector3 to help computations without allocating memory
    private static Vector3 temp_ = new Vector3();

    private HashSet<FunRopePiece> edges_;
    private double totalMass_;
    //private boolean inGround_ = false;
    private IFunRopeAttractor attractor_ = null;

    public Vector3 position;
    public Vector3 velocity = new Vector3();
    public Vector3 totalForce = new Vector3();

    public FunRopeJoint(double jointMass, Vector3 pos)
    {
        totalMass_ = jointMass;
        position = pos.copy();
        
        solveForces();
    }
    
    
    public void attractTo(IFunRopeAttractor attr)
    {
        attractor_ = attr;
        activateNextTick();
    }
    
    public void release()
    {
        attractor_ = null;
        activateNextTick();
    }

    public void attachRopePiece(FunRopePiece rp)
    {
        if (edges_.add(rp))
            totalMass_ += rp.type().pieceMass / 2.0;
    }

    public void detachRopePiece(FunRopePiece rp)
    {
        if (edges_.remove(rp))
            totalMass_ -= rp.type().pieceMass / 2.0;
    }

    @Override
    public void solveForces()
    {
        // start with gravitation
        totalForce.set(0, cGravitation * totalMass_, 0);

        // add air friction
        totalForce.add(temp_.set(velocity).multiply(cAirFriction));

        // add tensile forces from each attached rope piece
        for (FunRopePiece rp : edges_)
            rp.applyTensileForceTo(this, totalForce);

//        if (inGround_)
//        {
//            // compute ground friction, absorption and repulsion, then add
//            // totalForce_.add(tempGroundFriction_).add(tempGroundAbsorption_).add(tempGroundRepulsion_);
//        }
        
        if (attractor_ != null)
            totalForce.add(attractor_.getDragForceAt(position));
    }

    @Override
    public void solveMotion()
    {
        if (attractor_ != null && attractor_.inFirmGrip(position))
        {
            position.set(attractor_.getPosition());
            velocity.set(0, 0, 0);
            return;
        }
        
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
