package rianon.ropes;

import java.util.ArrayList;

public class FunRegistry
{
    
    private class FunHolder
    {
        protected IFunEntity entity;
        protected boolean activeNextTick = false;
        protected boolean renderNextFrame = false;
        
        protected FunHolder(IFunEntity en)
        {
            entity = en;
        }
    }
    
    
    private static FunRegistry instance_;

    private ArrayList<FunHolder> entities_ = new ArrayList<>();
    
    private ArrayList<IFunEntity> activeEntities_ = new ArrayList<>();
    private ArrayList<IFunEntity> activeNextTick_ = new ArrayList<>();
    
    private ArrayList<IFunEntity> renderNextFrame_ = new ArrayList<>();
    

    private FunRegistry()
    {
        // load
    }
    
    private FunHolder fun(IFunEntity en)
    {
        return entities_.get(en.getFunEntityID());
    }

    public static FunRegistry instance()
    {
        if (instance_ == null)
            instance_ = new FunRegistry();
        return instance_;
    }

    public void register(IFunEntity en)
    {
        FunHolder f = new FunHolder(en); 
        
        if (!entities_.add(f))
            throw new RuntimeException("failed to register another entity");
        
        en.setFunEntityId(entities_.size() - 1);
        
        if (en.doesRemainActive())
        {
            activeNextTick_.add(en);
            f.activeNextTick = true;
        }
    }
    
    public void deregister(IFunEntity en)
    {
        //if (en.getID() == entities_.size() - 1)
    }

    public void onGameTick()
    {
        for (IFunEntity en : activeEntities_)
            fun(en).activeNextTick = false;
        
        for (IFunEntity en : activeEntities_)
            en.solveForces();

        for (IFunEntity en : activeEntities_)
            en.solveMotion();

        for (IFunEntity en : activeEntities_)
            if (en.doesRemainActive())
                activateNextTick(en);

        activeEntities_.clear();
        activeEntities_ = activeNextTick_;
        activeNextTick_ = new ArrayList<>();
    }
    
    public void onRender()
    {
        ArrayList<IFunEntity> toRender = renderNextFrame_;
        renderNextFrame_ = new ArrayList<>();
        
        for (IFunEntity en : toRender)
            fun(en).renderNextFrame = false;

        for (IFunEntity en : toRender)
            en.onRender();
        
        // EntityRopeJoint is responsible for rendering join and adjacent rope pieces on each subsequent frame
    }
    
    public void activateNextTick(IFunEntity en)
    {
        if (!fun(en).activeNextTick)
        {
            activeNextTick_.add(en);
            fun(en).activeNextTick = true;
        }
    }
    
    public void renderNextFrame(IFunEntity en)
    {
//        renderNextFrame_.add(en);
    }

}
