package rianon.ropes;

import java.util.ArrayList;
import java.util.HashSet;

public class WeeManager
{
    private static WeeManager instance_;

    private ArrayList<WeeEntity> entities_ = new ArrayList<>();
    private ArrayList<WeeEntity> activeEntities_ = new ArrayList<>();
    private ArrayList<WeeEntity> activeNextTick_ = new ArrayList<>();

    private WeeManager()
    {
        //load
    }

    public static WeeManager instance()
    {
        if (instance_ == null)
            instance_ = new WeeManager();
        return instance_;
    }
    
    public int register(WeeEntity en)
    {
        return entities_.add(en) ? entities_.size() - 1 : -1;
    }

    public void onGameTick()
    {
        for (WeeEntity en : activeEntities_)
            en.solveForces();

        for (WeeEntity en : activeEntities_)
            en.solveMotion();

        for (WeeEntity en : activeEntities_)
            if (en.active_)
                activeNextTick_.add(en);

        activeEntities_.clear();
        activeEntities_ = activeNextTick_;
        activeNextTick_ = new ArrayList<>();
    }

    public void activateNextTick(WeeEntity en)
    {
        if (!en.active_)
        {
            en.active_ = true;
            activeNextTick_.add(en);
        }
    }

}
