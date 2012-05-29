package rianon.ropes;

public abstract class IFunEntity
{
    private int entityID_;
    private int lastRenderFrame_;

    protected IFunEntity()
    {
        FunRegistry.instance().register(this);
        lastRenderFrame_ = 0;
    }
    
    public void deregister()
    {
        FunRegistry.instance().deregister(this);
    }
    
    public int getFunEntityID()
    {
        return entityID_;
    }
    
    public void setFunEntityId(int id)
    {
        entityID_ = id;
    }
    
    public boolean firstRenderAttempt(int frame)
    {
        if (lastRenderFrame_ != frame)
        {
            lastRenderFrame_ = frame;
            return true;
        }
        return false;
    }
    
    public abstract void solveForces();

    public abstract void solveMotion();

    // tells FunRegistry whether this entity remains active on next turn
    public abstract boolean doesRemainActive();
}
