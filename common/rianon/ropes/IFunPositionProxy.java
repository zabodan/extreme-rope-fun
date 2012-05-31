package rianon.ropes;

import codechicken.core.Vector3;

public interface IFunPositionProxy
{

    /**
     * Read object position and velocity into provided vectors
     * @param pos - storage for position, not NULL
     * @param vel - storage for velocity, not NULL
     */
    public void getPositionAndVelocity(Vector3 pos, Vector3 vel);

    /**
     * Set object position and velocity from provided values
     * @param pos - position to set, not NULL
     * @param vel - velocity to set, not NULL
     */
    public void setPositionAndVelocity(Vector3 pos, Vector3 vel);
    
//    
//    public Vector3 getVelocity(Vector3 rv);
//    public Vector3 getPosition(Vector3 rv);
//    public void addVelocity(Vector3 rv);
    
}
