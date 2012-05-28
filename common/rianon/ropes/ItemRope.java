package rianon.ropes;

import net.minecraft.src.Item;
import net.minecraft.src.forge.ITextureProvider;

// rope piece as seen in inventory or dropped on the ground 
public class ItemRope extends Item
    implements ITextureProvider
{

    protected ItemRope(int id)
    {
        super(id);
        setIconIndex(0);
        setItemName("rf.irope");
    }

    @Override
    public String getTextureFile()
    {
        return "/rianon/ropes/items.png";
    }

}
