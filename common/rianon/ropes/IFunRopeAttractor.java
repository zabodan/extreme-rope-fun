package rianon.ropes;

import codechicken.core.Vector3;

public interface IFunRopeAttractor
{
    public Vector3 getPullingForceAt(Vector3 pos);
    
    public boolean inFirmGrip(Vector3 pos);
    
    public Vector3 getPosition();
}