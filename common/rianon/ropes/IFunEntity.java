package rianon.ropes;

public interface IFunEntity
{
    public int getFunEntityID();
    
    public void setFunEntityID(int id);

    public void solveForces();

    public void solveMotion();

    public boolean isActiveEntity();
}
