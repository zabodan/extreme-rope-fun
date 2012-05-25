package rianon.ropes;

import java.util.HashSet;
import codechicken.core.Vector3;

public abstract class IRopeJoint
{
    private HashSet<IRopePiece> pieces_;
    private double totalMass_;
    
    private Vector3 prevPosition_;
    private Vector3 currPosition_;
    private Vector3 currVelocity_;
    

    public IRopeJoint()
    {
        totalMass_ = mass();
    }

    public abstract Vector3 position();

    public abstract Vector3 velocity();

    public abstract double mass();

    public void attach(IRopePiece rp)
    {
        if (pieces_.add(rp))
        {
            totalMass_ += rp.mass() / 2.0;
        }
    }

    public void detach(IRopePiece rp)
    {
        if (pieces_.remove(rp))
        {
            totalMass_ -= rp.mass() / 2.0;
        }
    }
    
    public void onGameTick()
    {
        prevPosition_ = currPosition_.copy();
        
        Vector3 motion = currVelocity_.copy().multiply(0.05D);
        currPosition_.add(motion);
        
        Vector3 totalForce = new Vector3();
        Vector3 acceleration = totalForce.multiply(1.0 / totalMass_);
    }

}
