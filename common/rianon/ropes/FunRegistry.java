package rianon.ropes;

import java.util.ArrayList;

public class FunRegistry
{
    private static FunRegistry instance_;

    private ArrayList<IFunEntity> entities_ = new ArrayList<>();
    private ArrayList<IFunEntity> activeEntities_ = new ArrayList<>();
    private ArrayList<IFunEntity> activeNextTick_ = new ArrayList<>();
    

    private FunRegistry()
    {
        // load
    }

    public static FunRegistry instance()
    {
        if (instance_ == null)
            instance_ = new FunRegistry();
        return instance_;
    }

    public int register(IFunEntity en)
    {
        if (!entities_.add(en))
            throw new RuntimeException("failed to register another entity");
        
        return entities_.size() - 1;
    }
    
    public void deregister(IFunEntity en)
    {
        //if (en.getID() == entities_.size() - 1)
    }

    public void onGameTick()
    {
        for (IFunEntity en : activeEntities_)
            en.solveForces();

        for (IFunEntity en : activeEntities_)
            en.solveMotion();

        for (IFunEntity en : activeEntities_)
            if (en.isActiveEntity())
                activeNextTick_.add(en);

        activeEntities_.clear();
        activeEntities_ = activeNextTick_;
        activeNextTick_ = new ArrayList<>();
    }

    public void activateNextTick(IFunEntity en)
    {
        activeNextTick_.add(en);
    }

}