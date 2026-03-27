package twilightforest;

import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;
import twilightforest.biomes.TFBiomeBase;
import twilightforest.entity.EntityTFPortalSpawnerItem;
import twilightforest.network.PacketStructureProtection;
import twilightforest.network.PacketStructureProtectionClear;
import twilightforest.network.TFPacketHandler;
import twilightforest.util.StructureBoundingBoxUtils;
import twilightforest.world.ChunkGeneratorTFBase;
import twilightforest.world.TFWorld;

@Mod.EventBusSubscriber(modid = TwilightForestMod.ID)
public class TFTickHandler {

	@SubscribeEvent
	public static void portalSpawnerJoin(EntityJoinWorldEvent event) {
		Entity entity = event.getEntity();
		if (entity instanceof EntityItem && !(entity instanceof EntityTFPortalSpawnerItem)) {
			EntityItem item = (EntityItem) entity;
			if (TFConfig.isPortalSpawner(item.getItem())) {
				World world = event.getWorld();
				EntityTFPortalSpawnerItem spawnerItem = new EntityTFPortalSpawnerItem(world, item.posX, item.posY, item.posZ, item.getItem());
				if (TFConfig.adminOnlyPortals) {
					EntityPlayer player = world.getClosestPlayerToEntity(item, Double.MIN_VALUE);
					if (player != null) {
						spawnerItem.setOwner(player.getName());
					}
				}
				spawnerItem.setPickupDelay(40);
				spawnerItem.motionX = entity.motionX;
				spawnerItem.motionY = entity.motionY;
				spawnerItem.motionZ = entity.motionZ;
				entity.setDead();
				event.setCanceled(true);
				world.spawnEntity(spawnerItem);
			}
		}
	}

	@SubscribeEvent
	public static void playerTick(PlayerTickEvent event) {

		EntityPlayer player = event.player;
		World world = player.world;

		// check the player for being in a forbidden progression area, only every 20 ticks
		if (!world.isRemote && event.phase == TickEvent.Phase.END && player.ticksExisted % 20 == 0
				&& TFWorld.isProgressionEnforced(world)
				&& TFWorld.isTwilightForest(world)
				&& !player.isCreative() && !player.isSpectator()) {

			checkBiomeForProgression(player, world);
		}

		// check and send nearby forbidden structures, every 100 ticks or so
		if (!world.isRemote && event.phase == TickEvent.Phase.END && player.ticksExisted % 100 == 0 && TFWorld.isProgressionEnforced(world)) {
			if (TFWorld.isTwilightForest(world)) {
				if (player.isCreative() || player.isSpectator()) {
					sendAllClearPacket(world, player);
				} else {
					checkForLockedStructuresSendPacket(player, world);
				}
			}
		}
	}

	private static void sendStructureProtectionPacket(World world, EntityPlayer player, StructureBoundingBox sbb) {
		if (player instanceof EntityPlayerMP) {
			TFPacketHandler.CHANNEL.sendTo(new PacketStructureProtection(sbb), (EntityPlayerMP) player);
		}
	}

	private static void sendAllClearPacket(World world, EntityPlayer player) {
		if (player instanceof EntityPlayerMP) {
			TFPacketHandler.CHANNEL.sendTo(new PacketStructureProtectionClear(), (EntityPlayerMP) player);
		}
	}

	@SuppressWarnings("UnusedReturnValue")
	private static boolean checkForLockedStructuresSendPacket(EntityPlayer player, World world) {

		ChunkGeneratorTFBase chunkGenerator = TFWorld.getChunkGenerator(world);
		if (chunkGenerator == null) return false;

		int px = MathHelper.floor(player.posX);
		int pz = MathHelper.floor(player.posZ);

		StructureBoundingBox fullSBB = chunkGenerator.getFullSBBNear(px, pz, 100);
		if (fullSBB != null) {

			Vec3i center = StructureBoundingBoxUtils.getCenter(fullSBB);

			TFFeature nearFeature = TFFeature.getFeatureForRegionPos(center.getX(), center.getZ(), world);

			if (!nearFeature.hasProtectionAura || nearFeature.doesPlayerHaveRequiredAdvancements(player)) {
				sendAllClearPacket(world, player);
				return false;
			} else {
				sendStructureProtectionPacket(world, player, fullSBB);
				return true;
			}
		}
		return false;
	}

	/**
	 * Check what biome the player is in, and see if current progression allows that biome.  If not, take appropriate action
	 */
	private static void checkBiomeForProgression(EntityPlayer player, World world) {
		Biome currentBiome = world.getBiome(new BlockPos(player));
		if (currentBiome instanceof TFBiomeBase) {
			TFBiomeBase tfBiome = (TFBiomeBase) currentBiome;
			if (!tfBiome.doesPlayerHaveRequiredAdvancements(player)) {
				tfBiome.enforceProgression(player, world);
			}
		}
	}
}
