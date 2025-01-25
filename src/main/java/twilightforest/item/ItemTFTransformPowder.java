package twilightforest.item;

import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.minecraft.block.Block;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.apache.commons.lang3.tuple.Pair;
import twilightforest.TwilightForestMod;
import twilightforest.util.TFEntityNames;
import twilightforest.util.VanillaEntityNames;

import javax.annotation.Nullable;
import java.util.*;
import java.util.function.Predicate;

public class ItemTFTransformPowder extends ItemTF {

	private static final Predicate<IBlockState> ALL = state -> true;

	private static final Map<ResourceLocation, ResourceLocation> transformMap = new HashMap<>();
	private static final Map<Block, Pair<Predicate<IBlockState>, IBlockState>> transformBlockMap = new Object2ObjectOpenHashMap<>();

	protected ItemTFTransformPowder() {
		MinecraftForge.EVENT_BUS.register(this);
		this.maxStackSize = 64;
		this.setCreativeTab(TFItems.creativeTab);
		addTwoWayTransformation(TFEntityNames.MINOTAUR,       VanillaEntityNames.ZOMBIE_PIGMAN);
		addTwoWayTransformation(TFEntityNames.DEER,           VanillaEntityNames.COW);
		addTwoWayTransformation(TFEntityNames.BIGHORN_SHEEP,  VanillaEntityNames.SHEEP);
		addTwoWayTransformation(TFEntityNames.WILD_BOAR,      VanillaEntityNames.PIG);
		addTwoWayTransformation(TFEntityNames.BUNNY,          VanillaEntityNames.RABBIT);
		addTwoWayTransformation(TFEntityNames.TINY_BIRD,      VanillaEntityNames.PARROT);
		addTwoWayTransformation(TFEntityNames.RAVEN,          VanillaEntityNames.BAT);
		addTwoWayTransformation(TFEntityNames.HOSTILE_WOLF,   VanillaEntityNames.WOLF);
		addTwoWayTransformation(TFEntityNames.PENGUIN,        VanillaEntityNames.CHICKEN);
		addTwoWayTransformation(TFEntityNames.HEDGE_SPIDER,   VanillaEntityNames.SPIDER);
		addTwoWayTransformation(TFEntityNames.SWARM_SPIDER,   VanillaEntityNames.CAVE_SPIDER);
		addTwoWayTransformation(TFEntityNames.WRAITH,         VanillaEntityNames.BLAZE);
		addTwoWayTransformation(TFEntityNames.REDCAP,         VanillaEntityNames.VILLAGER);
		addTwoWayTransformation(TFEntityNames.SKELETON_DRUID, VanillaEntityNames.WITCH);
	}

	public static void addTwoWayTransformation(ResourceLocation from, ResourceLocation to) {
		transformMap.put(from, to);
		transformMap.put(to, from);
	}

	public static void addOneWayTransformation(ResourceLocation from, ResourceLocation to) {
		transformMap.put(from, to);
	}

	public static void addBlockTransformation(Block from, Predicate<IBlockState> predicate, IBlockState to) {
		transformBlockMap.put(from, Pair.of(predicate, to));
	}

	public static boolean removeEntityTransformation(ResourceLocation from) {
		return transformMap.remove(from) != null;
	}

	public static Map<ResourceLocation, ResourceLocation> getTransformMap() {
		return transformMap;
	}

	public static Map<Block, Pair<Predicate<IBlockState>, IBlockState>> getTransformBlockMap() {
		return transformBlockMap;
	}

	public static void removeAll() {
		transformMap.clear();
	}

	public static void addBlockTransformation(Block from, int meta, IBlockState to) {
		addBlockTransformation(from, state -> from.getMetaFromState(state) == meta, to);
	}

	public static void addBlockTransformation(IBlockState from, IBlockState to) {
		addBlockTransformation(from.getBlock(), state -> state == from, to);
	}

	public static void addBlockTransformation(Block from, IBlockState to) {
		addBlockTransformation(from, ALL, to);
	}

	public static ResourceLocation getTransformationEntity(ResourceLocation from) {
		return transformMap.get(from);
	}

	@Nullable
	public static IBlockState getTransformationBlock(IBlockState from) {
		Pair<Predicate<IBlockState>, IBlockState> pair = transformBlockMap.get(from.getBlock());
		if (pair == null) return null;
		return pair.getKey().test(from) ? pair.getValue() : null;
	}

	@SuppressWarnings("unused")
	@SubscribeEvent
	public void onEntityRightClick(PlayerInteractEvent.EntityInteract event) {
		World world = event.getWorld();
		Entity entity = event.getTarget();
		ItemStack heldItem = event.getItemStack();
		if (entity.isDead || heldItem.getItem() != this) return;
		ResourceLocation location = transformMap.get(EntityList.getKey(entity));
		if (location == null) return;
		Entity newEntity = EntityList.createEntityByIDFromName(location, world);
		if (newEntity == null) return;
		event.getEntityPlayer().swingArm(event.getHand());
		if (world.isRemote) {
			AxisAlignedBB fanBox = getEffectAABB(event.getEntityPlayer());
			// particle effect
			for (int i = 0; i < 30; i++) {
				world.spawnParticle(EnumParticleTypes.CRIT_MAGIC, fanBox.minX + world.rand.nextFloat() * (fanBox.maxX - fanBox.minX),
						fanBox.minY + world.rand.nextFloat() * (fanBox.maxY - fanBox.minY),
						fanBox.minZ + world.rand.nextFloat() * (fanBox.maxZ - fanBox.minZ),
						0, 0, 0);
			}
			event.setResult(Event.Result.ALLOW);
			return;
		}
		newEntity.setLocationAndAngles(entity.posX, entity.posY, entity.posZ, entity.rotationYaw, entity.rotationPitch);
		if (newEntity instanceof EntityLiving) {
			((EntityLiving) newEntity).onInitialSpawn(world.getDifficultyForLocation(new BlockPos(entity)), null);
		}
		try { // try copying what can be copied
			UUID uuid = newEntity.getUniqueID();
			newEntity.readFromNBT(entity.writeToNBT(newEntity.writeToNBT(new NBTTagCompound())));
			newEntity.setUniqueId(uuid);
		} catch (Exception e) {
			TwilightForestMod.LOGGER.warn("Couldn't transform entity NBT data: {}", e);
		}
		world.spawnEntity(newEntity);
		entity.setDead();
        if (!event.getEntityPlayer().isCreative()) heldItem.shrink(1);
        if (entity instanceof EntityLiving) {
            ((EntityLiving) entity).spawnExplosionParticle();
            ((EntityLiving) entity).spawnExplosionParticle();
        }
        else {
            this.spawnExplosionParticles(world, entity);
            this.spawnExplosionParticles(world, entity);
        }
        entity.playSound(SoundEvents.ENTITY_ZOMBIE_VILLAGER_CURE, 1.0F + itemRand.nextFloat(), itemRand.nextFloat() * 0.7F + 0.3F);
        event.setResult(Event.Result.ALLOW);
    }

	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		IBlockState state = world.getBlockState(pos);
		ItemStack heldItem = player.getHeldItem(hand);
		IBlockState stateOut = getTransformationBlock(state);
		if (stateOut == null) return super.onItemUse(player, world, pos, hand, facing, hitX, hitY, hitZ);
		if (world.isRemote) {
			AxisAlignedBB fanBox = getEffectAABB(player);
			// particle effect
			for (int i = 0; i < 30; i++) {
				world.spawnParticle(EnumParticleTypes.CRIT_MAGIC, fanBox.minX + world.rand.nextFloat() * (fanBox.maxX - fanBox.minX),
						fanBox.minY + world.rand.nextFloat() * (fanBox.maxY - fanBox.minY),
						fanBox.minZ + world.rand.nextFloat() * (fanBox.maxZ - fanBox.minZ),
						0, 0, 0);
			}
			return EnumActionResult.SUCCESS;
		}
		world.setBlockState(pos, handleStateProperties(state, stateOut));
		if (!player.isCreative()) heldItem.shrink(1);
		this.spawnExplosionParticles(world, pos);
		this.spawnExplosionParticles(world, pos);
		world.playSound(null, pos, SoundEvents.ENTITY_ZOMBIE_VILLAGER_CURE, SoundCategory.BLOCKS, 1.0F + itemRand.nextFloat(), itemRand.nextFloat() * 0.7F + 0.3F);
		return EnumActionResult.SUCCESS;
	}

	private static IBlockState handleStateProperties(IBlockState from, IBlockState to) {
		BlockStateContainer containerFrom = from.getBlock().getBlockState();
		BlockStateContainer containerTo = to.getBlock().getBlockState();
		IProperty<?> axisFrom = containerFrom.getProperty("axis");
		IProperty<?> axisTo = containerTo.getProperty("axis");
		IProperty<?> facingFrom = containerFrom.getProperty("facing");
		IProperty<?> facingTo = containerTo.getProperty("facing");
		if (axisFrom != null && axisTo != null) {
			to = to.withProperty((IProperty) axisTo, (Comparable) axisTo.parseValue(((IStringSerializable) from.getValue(axisFrom)).getName()));
		}
		if (facingFrom != null && facingTo != null) {
			to = to.withProperty((IProperty) facingTo, (Comparable) facingTo.parseValue(((IStringSerializable) from.getValue(facingFrom)).getName()));
		}
		return to;
	}

	private AxisAlignedBB getEffectAABB(EntityPlayer player) {
		double range = 2.0D;
		double radius = 1.0D;
		Vec3d srcVec = new Vec3d(player.posX, player.posY + player.getEyeHeight(), player.posZ);
		Vec3d lookVec = player.getLookVec();
		Vec3d destVec = srcVec.add(lookVec.x * range, lookVec.y * range, lookVec.z * range);

		return new AxisAlignedBB(destVec.x - radius, destVec.y - radius, destVec.z - radius, destVec.x + radius, destVec.y + radius, destVec.z + radius);
	}

	private void spawnExplosionParticles(World world, Entity entity) {
		for (int i = 0; i < 20; ++i) {
			double d0 = world.rand.nextGaussian() * 0.02D;
			double d1 = world.rand.nextGaussian() * 0.02D;
			double d2 = world.rand.nextGaussian() * 0.02D;
			world.spawnParticle(EnumParticleTypes.EXPLOSION_NORMAL, entity.posX + (double)(world.rand.nextFloat() * entity.width * 2.0F) - (double)entity.width - d0 * 10.0D, entity.posY + (double)(world.rand.nextFloat() * entity.height) - d1 * 10.0D, entity.posZ + (double)(world.rand.nextFloat() * entity.width * 2.0F) - (double)entity.width - d2 * 10.0D, d0, d1, d2);
		}
	}

	private void spawnExplosionParticles(World world, BlockPos pos) {
		for (int i = 0; i < 20; ++i) {
			double d0 = world.rand.nextGaussian() * 0.02D;
			double d1 = world.rand.nextGaussian() * 0.02D;
			double d2 = world.rand.nextGaussian() * 0.02D;
			world.spawnParticle(EnumParticleTypes.EXPLOSION_NORMAL, pos.getX() + 0.5D + (double)(world.rand.nextFloat() * 2.0F) - 1.0D - d0 * 10.0D, pos.getY() + 0.5D + (double)(world.rand.nextFloat()) - d1 * 10.0D, pos.getZ() + 0.5D + (double)(world.rand.nextFloat() * 2.0F) - 1.0D - d2 * 10.0D, d0, d1, d2);
		}
	}
}
