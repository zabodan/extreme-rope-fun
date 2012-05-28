package rianon.ropes;

import java.util.HashSet;

import codechicken.core.Vector3;

public interface IRopeJoint extends IFunEntity
{
//    private static final double cGravitation = -9.81;
//    private static final double cAirFriction = -0.02;
//    private static final double cMaxVelocity = 8; // m/s
//
//    // temporary Vector3 to help computations without allocating memory
//    private static Vector3 temp_ = new Vector3();
//
//    private HashSet<IRopePiece> edges_;
//    private double totalMass_;
//    private boolean inGround_ = false;
//
//    public Vector3 position = new Vector3();
//    public Vector3 velocity = new Vector3();
//    public Vector3 totalForce = new Vector3();
//
//    public IRopeJoint()
//    {
//        totalMass_ = getRopeJointMass();
//        solveForces();
//    }
//
    public void attachRopePiece(RopePieceBase rp);
    
    public void detachRopePiece(RopePieceBase rp);
    
    public double getRopeJointOwnMass();
    
    public Vector3 position();
    
    public Vector3 velocity();
    
//
//    public void attachRopePiece(IRopePiece rp)
//    {
//        if (edges_.add(rp))
//            totalMass_ += rp.getRopePieceMass() / 2.0;
//    }
//
//    public void detachRopePiece(IRopePiece rp)
//    {
//        if (edges_.remove(rp))
//            totalMass_ -= rp.getRopePieceMass() / 2.0;
//    }
//
//    public void solveForces()
//    {
//        // start with gravitation
//        totalForce.set(0, cGravitation * totalMass_, 0);
//
//        // add air friction
//        totalForce.add(temp_.set(velocity).multiply(cAirFriction));
//
//        // add tensile forces from each attached rope piece
//        for (IRopePiece rp : edges_)
//            rp.applyTensileForceTo(this, totalForce);
//
//        if (inGround_)
//        {
//            // compute ground friction, absorption and repulsion, then add
//            // totalForce_.add(tempGroundFriction_).add(tempGroundAbsorption_).add(tempGroundRepulsion_);
//        }
//    }
//
//    public void solveMotion()
//    {
//        final double timeSpan = 0.05D; // as long as there are 20 ticks per second
//
//        position.add(temp_.set(velocity).multiply(timeSpan));
//        velocity.add(temp_.set(totalForce).multiply(timeSpan / totalMass_));
//
//        // limit maximum velocity
//        final double v = velocity.mag();
//        if (v > cMaxVelocity)
//            velocity.multiply(cMaxVelocity / v);
//    }
//
//    public boolean isActiveEntity()
//    {
//        return !velocity.isZero();
//    }

}
