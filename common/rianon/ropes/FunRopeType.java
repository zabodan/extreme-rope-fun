package rianon.ropes;

public enum FunRopeType
{
    silk("Silk", 0.1, 100.0, 0.001, 20.0, 0),
    steel("Steel", 0.3, 10000.0, 10000.0, 100.0, 1),
    composite("Composite", 0.2, 20000.0, 20000.0, 1000.0, 2);

    public final String namePrefix;
    public final double pieceMass;
    public final double cContraction;
    public final double cExpansion;
    public final double strength;
    private final int metaIndex;

    private FunRopeType(String name, double mass, double cc, double ce, double str, int idx)
    {
        namePrefix = name;
        pieceMass = mass;
        cContraction = cc;
        cExpansion = ce;
        strength = str;
        metaIndex = idx;
    }

    public static FunRopeType fromMeta(int idx)
    {
        switch (idx)
        {
        case 0: return silk;
        case 1: return steel;
        case 2: return composite;
        }
        throw new RuntimeException("Unknown rope metaIndex");
    }
}
