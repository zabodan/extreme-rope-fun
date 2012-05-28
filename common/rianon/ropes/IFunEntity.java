package rianon.ropes;

public abstract class IFunEntity
{
    private int entityID_;

    protected IFunEntity()
    {
        entityID_ = FunRegistry.instance().register(this);        
    }
    
    public int getFunEntityID()
    {
        return entityID_;
    }
    
    public abstract void solveForces();

    public abstract void solveMotion();

    public abstract boolean isActiveEntity();
    
    public void activateNextTick()
    {
        FunRegistry.instance().activateNextTick(this);
    }
}
