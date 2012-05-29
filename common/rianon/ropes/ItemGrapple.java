package rianon.ropes;

import net.minecraft.src.Item;

// grappling hook on the end of a rope
public class ItemGrapple extends Item
{
    protected ItemGrapple(int id)
    {
        super(id);
        setItemName("item.rfgrapple");
    }
}
