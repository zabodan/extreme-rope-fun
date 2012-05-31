package rianon.ropes;

import codechicken.core.Vector3;

public interface IFunRopeAttractor
{
    public Vector3 getPullingForceAt(Vector3 pos);
    
    public boolean inFirmGrip(Vector3 pos);
    
    public Vector3 getPosition();
}


//protected static final double cFirmGripDistSquared = 0.25;
//protected static final double cAttractionFactor = 100.0;


//@Override
//public Vector3 getPullingForceAt(Vector3 pos)
//{
//  if (inFirmGrip(pos)) // temp_ = position_ - pos
//      return temp_.set(0,0,0);
//  
//  return temp_.multiply(cAttractionFactor);
//}
//
//@Override
//public boolean inFirmGrip(Vector3 pos)
//{
//  return temp_.set(position_).subtract(pos).magSquared() <= cFirmGripDistSquared;
//}
//
//@Override
//public Vector3 getPosition()
//{
//  return position_;
//}

