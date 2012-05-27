package rianon.ropes;

public enum BlockSide
{
    px(4),
    nx(5),
    py(0),
    ny(1),
    pz(2),
    nz(3);
    
    public final int side;
    
    private BlockSide(int v)
    {
        side = v;
    }

}
