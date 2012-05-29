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

    private HashSet<FunRopePiece> ropes_;
    private IFunRopeAttractor attractor_ = null;
    private double totalMass_;

    public Vector3 position;
    public Vector3 velocity = new Vector3();
    public Vector3 totalForce = new Vector3();

    public FunRopeJoint(double jointMass, Vector3 pos)
    {
        totalMass_ = jointMass;
        position = pos.copy();
        
        solveForces();
    }
    
    public void push(Vector3 delta)
    {
        position.add(delta);
        activateNextTick();
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

    public void activateNextTick()
    {
        FunRegistry.instance().activateNextTick(this);
        for (FunRopePiece rp : ropes_)
            FunRegistry.instance().activateNextTick(rp);
    }
    
    public void renderNextFrame()
    {
        FunRegistry.instance().renderNextFrame(this);
        for (FunRopePiece rp : ropes_)
            FunRegistry.instance().renderNextFrame(rp);
    }
    
    
    // should be used only by FunRopePiece
    protected void attachRopePiece(FunRopePiece rp)
    {
        if (ropes_.add(rp))
            totalMass_ += rp.type().pieceMass / 2.0;
    }

    // should be used only by FunRopePiece
    protected void detachRopePiece(FunRopePiece rp)
    {
        if (ropes_.remove(rp))
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
        for (FunRopePiece rp : ropes_)
            rp.applyTensileForceTo(this, totalForce);

        // TODO compute ground friction, absorption and repulsion, then add
        
        if (attractor_ != null)
            totalForce.add(attractor_.getPullingForceAt(position));
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
    public boolean doesRemainActive()
    {
        return !velocity.isZero();
    }
    
    @Override
    public void onRender()
    {
        // TODO Auto-generated method stub
        
    }
    
}
