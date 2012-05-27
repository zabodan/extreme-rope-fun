package rianon.ropes;

public abstract class IFunEntity
{
    protected int entityID_;

    protected IFunEntity()
    {
        entityID_ = FunRegistry.instance().register(this);
    }

    public int getID()
    {
        return entityID_;
    }

    public abstract void solveForces();

    public abstract void solveMotion();

    public abstract boolean isActiveEntity();
}
