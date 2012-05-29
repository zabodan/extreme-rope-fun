package rianon.ropes;

public abstract class IFunEntity
{
    private int entityID_;

    protected IFunEntity()
    {
        FunRegistry.instance().register(this);        
    }
    
    public int getFunEntityID()
    {
        return entityID_;
    }
    
    public void setFunEntityId(int id)
    {
        entityID_ = id;
    }
    
    public abstract void solveForces();

    public abstract void solveMotion();

    // tells FunRegistry whether this entity remains active on next turn
    public abstract boolean doesRemainActive();
    
    // please do not call renderNextFrame() from here
    // EntityRopeJoint is responsible for that
    public abstract void onRender();
}
