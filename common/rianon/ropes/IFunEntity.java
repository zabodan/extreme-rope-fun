package rianon.ropes;

public abstract class IFunEntity
{
    protected int entityIdx_;

    protected IFunEntity()
    {
        entityIdx_ = FunManager.instance().register(this);
        assert entityIdx_ > 0;
    }

    public abstract void solveForces();

    public abstract void solveMotion();

    public abstract boolean isActiveEntity();
}
