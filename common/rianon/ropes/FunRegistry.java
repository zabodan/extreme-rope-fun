package rianon.ropes;

import java.util.ArrayList;

import net.minecraft.src.mod_RopeFun;

public class FunRegistry
{
    
    private class FunHolder
    {
        protected IFunEntity entity;
        protected boolean activeNextTick = false;
        
        protected FunHolder(IFunEntity en)
        {
            entity = en;
        }
    }
    
    private static FunRegistry instance_;

    private ArrayList<FunHolder> entityHolders_ = new ArrayList<>();
    private ArrayList<IFunEntity> activeEntities_ = new ArrayList<>();
    private ArrayList<IFunEntity> activeNextTick_ = new ArrayList<>();
    
    
    private int lastSolvedTick_ = 0;

    private FunRegistry()
    {
    }
    
    private FunHolder fun(IFunEntity en)
    {
        return entityHolders_.get(en.getFunEntityID());
    }
    
    

    public static FunRegistry instance()
    {
        if (instance_ == null)
            instance_ = new FunRegistry();
        return instance_;
    }

    public void register(IFunEntity en)
    {
        if (!entityHolders_.add(new FunHolder(en)))
            throw new RuntimeException("failed to register another entity");
        
        en.setFunEntityId(entityHolders_.size() - 1);
    }
    
    public void deregister(IFunEntity en)
    {
        entityHolders_.set(en.getFunEntityID(), null);
        en.setFunEntityId(0);
    }

    public void onGameTick()
    {
        final int currentTick = mod_RopeFun.getCurrentTick(); 
        if (currentTick == lastSolvedTick_)
            return;
        
        for (IFunEntity en : activeEntities_)
        {
            if (en.getFunEntityID() != 0)
                fun(en).activeNextTick = false;
        }
        
        for (IFunEntity en : activeEntities_)
            en.solveForces();

        for (IFunEntity en : activeEntities_)
            en.solveMotion();

        for (IFunEntity en : activeEntities_)
        {
            if (en.getFunEntityID() != 0 && en.doesRemainActive())
                activateNextTick(en);
        }

        activeEntities_.clear();
        activeEntities_ = activeNextTick_;
        activeNextTick_ = new ArrayList<>();
        lastSolvedTick_ = currentTick;
    }
    
    public void activateNextTick(IFunEntity en)
    {
        if (!fun(en).activeNextTick)
        {
            activeNextTick_.add(en);
            fun(en).activeNextTick = true;
        }
    }
    
}
