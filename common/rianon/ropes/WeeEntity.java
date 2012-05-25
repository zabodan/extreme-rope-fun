package rianon.ropes;

public abstract class WeeEntity
{
    protected int entityIdx_;
    protected boolean active_;
    
    protected WeeEntity(boolean active)
    {
        entityIdx_ = WeeManager.instance().register(this);
        if (active)
            WeeManager.instance().activateNextTick(this);
    }
    

    
    
    // called each game tick
    public abstract void solveForces();
    public abstract void solveMotion();
    
    
}
