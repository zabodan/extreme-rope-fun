package rianon.ropes;

import java.util.ArrayList;

public class FunManager
{
    private static FunManager instance_;

    private ArrayList<IFunEntity> entities_ = new ArrayList<>();
    private ArrayList<IFunEntity> activeEntities_ = new ArrayList<>();
    private ArrayList<IFunEntity> activeNextTick_ = new ArrayList<>();

    private FunManager()
    {
        // load
    }

    public static FunManager instance()
    {
        if (instance_ == null)
            instance_ = new FunManager();
        return instance_;
    }

    public int register(IFunEntity en)
    {
        return entities_.add(en) ? entities_.size() - 1 : -1;
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
