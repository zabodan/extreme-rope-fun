package rianon.ropes;

import java.util.ArrayList;

import net.minecraft.src.*;
import codechicken.core.Vector3;

// support, end-point for ropes
// block meta = side where it was placed
public class BlockPiton extends Block
{
    
    private static final AxisAlignedBB bboxes[] = {
        AxisAlignedBB.getBoundingBox(0.38, 0.4, 0.38, 0.62, 1.0, 0.62),
        AxisAlignedBB.getBoundingBox(0.38, 0.0, 0.38, 0.62, 0.6, 0.62),
        AxisAlignedBB.getBoundingBox(0.38, 0.38, 0.4, 0.62, 0.62, 1.0),
        AxisAlignedBB.getBoundingBox(0.38, 0.38, 0.0, 0.62, 0.62, 0.6),
        AxisAlignedBB.getBoundingBox(0.4, 0.38, 0.38, 1.0, 0.62, 0.62),
        AxisAlignedBB.getBoundingBox(0.0, 0.38, 0.38, 0.6, 0.62, 0.62)
    };

    protected BlockPiton(int id)
    {
        super(id, Material.rock);

        setHardness(1F);
        setResistance(5F);
        setBlockName("block.rfpiton");
        blockIndexInTexture = 6; //107;
    }

    @Override
    public void onBlockPlaced(World world, int x, int y, int z, int side)
    {
        super.onBlockPlaced(world, x, y, z, side);

        System.out.println("BlockPiton placed @ " + (new Vector3(x, y, z)) + ", side = " + side);
        world.setBlockMetadata(x, y, z, side);
    }
    
    @Override
    protected int damageDropped(int meta)
    {
        return 0;
    }
    
    @Override
    public ArrayList<ItemStack> getBlockDropped(World world, int x, int y, int z, int metadata, int fortune)
    {
        ArrayList<ItemStack> drop = new ArrayList<>();
        drop.add(new ItemStack(this, 3, 0));
        return drop;
    }
    

    @Override
    public boolean blockActivated(World world, int x, int y, int z, EntityPlayer player)
    {
        if (player.isSneaking())
            return false;
        
        ItemStack stack = player.getCurrentEquippedItem();
        if (stack != null && stack.getItem() instanceof ItemRope)
        {
            Vector3 blockPos = new Vector3(x, y, z);
            System.out.println("BlockPiton activated with rope @ " + blockPos + ", meta = " + world.getBlockMetadata(x, y, z));
            
            //Vector3 jointPos = Vector3.fromEntity(player).add(blockPos).multiply(0.5);
            Entity en = new EntityRopeJoint(world, blockPos.add(0.5, 0.5, 0.5));
            world.spawnEntityInWorld(en);
            
//            Entity en = new EntityArrow(world, x + 0.5, y + 0.5, z + 0.5);
//            en.setVelocity(1, 3, 1);
//            world.spawnEntityInWorld(en);
            
            
            return true;
        }
        
        return false;
    }
    
    @Override
    public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity entity)
    {
        //System.out.println("onEntityCollidedWithBlock @ " + (new Vector3(x, y, z)) + ", meta = " + world.getBlockMetadata(x, y, z));
    }
    
    @Override
    public void onNeighborBlockChange(World world, int x, int y, int z, int neighborID)
    {
        if (world.getBlockId(x, y, z) == blockID)
        {
            int side = world.getBlockMetadata(x, y, z);
            if (!canPlaceBlockOnSide(world, x, y, z, side))
            {
                dropBlockAsItem(world, x, y, z, 0, 0);
                world.setBlockWithNotify(x, y, z, 0);
            }
        }
    }

    @Override
    public boolean isBlockSolidOnSide(World world, int x, int y, int z, int side)
    {
        return false; // do not connect anything to this block
    }

    @Override
    public boolean canPlaceBlockAt(World world, int x, int y, int z)
    {
        return false;
    }

    @Override
    public boolean canPlaceBlockOnSide(World world, int x, int y, int z, int side)
    {
        switch (side)
        {
            case 0: return world.isBlockSolidOnSide(x, y + 1, z, 0);
            case 1: return world.isBlockSolidOnSide(x, y - 1, z, 1);
            case 2: return world.isBlockSolidOnSide(x, y, z + 1, 2);
            case 3: return world.isBlockSolidOnSide(x, y, z - 1, 3);
            case 4: return world.isBlockSolidOnSide(x + 1, y, z, 4);
            case 5: return world.isBlockSolidOnSide(x - 1, y, z, 5);
        }
        return false;
    }
    
    @Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z)
    {
        int side = world.getBlockMetadata(x, y, z);
        return bboxes[side].copy().offset(x, y, z);
    }
    
    @Override
    public void setBlockBoundsBasedOnState(IBlockAccess ba, int x, int y, int z)
    {
        int side = ba.getBlockMetadata(x, y, z);

        assert side >= 0 && side < 6;
        setBlockBounds(bboxes[side]);
    }
    
    @Override
    public void setBlockBoundsForItemRender()
    {
        setBlockBounds(bboxes[1]);
        //setBlockBounds(0,0,0,1,1,1);
    }
    
    private void setBlockBounds(AxisAlignedBB bbox)
    {
        minX = bbox.minX;
        minY = bbox.minY;
        minZ = bbox.minZ;
        maxX = bbox.maxX;
        maxY = bbox.maxY;
        maxZ = bbox.maxZ;
    }
    
    @Override
    public boolean renderAsNormalBlock()
    {
        return false;
    }
    
    @Override
    public boolean isOpaqueCube()
    {
        return false;
    }

//    @Override
//    public int getRenderType()
//    {
//        return mod_RopeFun.ropeRenderID;
//    }

}
