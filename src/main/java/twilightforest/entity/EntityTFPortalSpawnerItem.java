package twilightforest.entity;

import com.google.common.base.Strings;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import twilightforest.TFConfig;
import twilightforest.advancements.TFAdvancements;
import twilightforest.block.TFBlocks;

import java.util.Random;

public class EntityTFPortalSpawnerItem extends EntityItem {

    public EntityTFPortalSpawnerItem(World worldIn, double x, double y, double z) {
        super(worldIn, x, y, z);
    }

    public EntityTFPortalSpawnerItem(World worldIn, double x, double y, double z, ItemStack stack) {
        super(worldIn, x, y, z, stack);
    }

    public EntityTFPortalSpawnerItem(World worldIn) {
        super(worldIn);
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        if (this.ticksExisted % (TFConfig.checkPortalDestination ? 100 : 40) == 0) {
            int dimId = world.provider.getDimension();
            if (TFConfig.allowPortalsInOtherDimensions || dimId == TFConfig.originDimension || dimId == TFConfig.dimension.dimensionID) {
                boolean stateCanForm = this.isInWater();
                BlockPos pos = new BlockPos(this);
                if (!stateCanForm) {
                    IBlockState state = world.getBlockState(pos);
                    if (TFBlocks.twilight_portal.canFormPortal(state)) {
                        stateCanForm = true;
                    }
                }
                if (stateCanForm) {
                    if (world.isRemote) {
                        Random rand = new Random();
                        for (int i = 0; i < 2; i++) {
                            double vx = rand.nextGaussian() * 0.02D;
                            double vy = rand.nextGaussian() * 0.02D;
                            double vz = rand.nextGaussian() * 0.02D;
                            world.spawnParticle(EnumParticleTypes.SPELL, posX, posY + 0.2, posZ, vx, vy, vz);
                        }
                    }
                    else {
                        EntityPlayer player;
                        if (Strings.isNullOrEmpty(getOwner())) player = null;
                        else player = world.getPlayerEntityByName(getOwner());
                        if (TFBlocks.twilight_portal.tryToCreatePortal(world, pos, this, player)) {
                            if (player != null) {
                                TFAdvancements.MADE_TF_PORTAL.trigger((EntityPlayerMP) player);
                            }
                        }
                    }
                }
            }
        }
    }
}

/*
private static void checkForPortalCreation(EntityPlayer player, World world, float rangeToCheck) {
		if (world.provider.getDimension() == TFConfig.originDimension
				|| world.provider.getDimension() == TFConfig.dimension.dimensionID
				|| TFConfig.allowPortalsInOtherDimensions) {

			List<EntityItem> itemList = world.getEntitiesWithinAABB(EntityItem.class, player.getEntityBoundingBox().grow(rangeToCheck));

			for (EntityItem entityItem : itemList) {
				if (TFConfig.portalIngredient.apply(entityItem.getItem())) {
					BlockPos pos = entityItem.getPosition();
					IBlockState state = world.getBlockState(pos);
					if (TFBlocks.twilight_portal.canFormPortal(state)) {
						Random rand = new Random();
						for (int i = 0; i < 2; i++) {
							double vx = rand.nextGaussian() * 0.02D;
							double vy = rand.nextGaussian() * 0.02D;
							double vz = rand.nextGaussian() * 0.02D;

							world.spawnParticle(EnumParticleTypes.SPELL, entityItem.posX, entityItem.posY + 0.2, entityItem.posZ, vx, vy, vz);
						}

						if (TFBlocks.twilight_portal.tryToCreatePortal(world, pos, entityItem, player)) {
							TFAdvancements.MADE_TF_PORTAL.trigger((EntityPlayerMP) player);
							return;
						}
					}
				}
			}
		}
	}

 */
