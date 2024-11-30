package twilightforest.events;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.Cancelable;
import net.minecraftforge.fml.common.eventhandler.Event;
import twilightforest.enums.BossVariant;
import twilightforest.tileentity.spawner.TileEntityTFBossSpawner;

public abstract class BossEvent extends Event {

    private final World world;
    private final BlockPos pos;
    private final BossVariant variant;
    private final EntityLivingBase boss;

    public BossEvent(World world, BlockPos pos, BossVariant variant, EntityLivingBase boss) {
        this.world = world;
        this.pos = pos;
        this.variant = variant;
        this.boss = boss;
    }

    public World getWorld() {
        return world;
    }

    public BlockPos getPos() {
        return pos;
    }

    public BossVariant getVariant() {
        return variant;
    }

    public EntityLivingBase getBoss() {
        return boss;
    }

    /**
     * Fired when constructing the boss to spawn it.
     * You can change entity to spawn here.
     * <br>
     * This event is not cancelable. {@link Cancelable}<br>
     * <br>
     * This event does not have a result. {@link HasResult}<br>
     * <br>
     **/
    public static class Construction extends BossEvent {

        private EntityLivingBase boss;
        private final IBlockState state;
        private final TileEntityTFBossSpawner spawner;

        public Construction(World world, BlockPos pos, IBlockState state, TileEntityTFBossSpawner spawner, EntityLivingBase defaultBoss) {
            super(world, pos, spawner.getVariant(), defaultBoss);
            this.state = state;
            this.spawner = spawner;
            this.boss = defaultBoss;
        }

        public IBlockState getState() {
            return state;
        }

        public TileEntityTFBossSpawner getSpawner() {
            return spawner;
        }

        /**
         * Gets the boss. You can use this to add weapons or change attributes of the boss.
         * Changing position or rotation of the entity does nothing.
         **/
        public EntityLivingBase getModifiedBoss() {
            return this.boss;
        }

        /**
         * Use this for entirely changing the boss.
         * Setting the position or rotation is not necessary.
         **/
        public void setBoss(EntityLivingBase boss) {
            this.boss = boss;
        }
    }

    /**
     * Fired before a Twilight Forest boss is being spawned.
     * <br>
     * This event has a {@link HasResult result}:
     * <li>{@link Result#ALLOW} means allow regardless if the default checks return false.</li>
     * <li>{@link Result#DEFAULT} means default checks are used to determine if boss can spawn or not. Like game difficulty check, player range check etc.</li>
     * <li>{@link Result#DENY} means boss spawner will not spawn boss even if default checks return true.</li><br>
     * This event is fired on the {@link MinecraftForge#EVENT_BUS}.<br>
     * Event is not cancellable. {@link Cancelable}
     **/
    @HasResult
    public static class Spawning extends BossEvent {

        private final IBlockState state;
        private final TileEntityTFBossSpawner spawner;

        public Spawning(World world, BlockPos pos, IBlockState state, TileEntityTFBossSpawner spawner, EntityLivingBase boss) {
            super(world, pos, spawner.getVariant(), boss);
            this.state = state;
            this.spawner = spawner;
        }

        public IBlockState getState() {
            return state;
        }

        public TileEntityTFBossSpawner getSpawner() {
            return spawner;
        }
    }

    @Cancelable
    public static class Death extends BossEvent {

        public Death(World world, BlockPos pos, BossVariant variant, EntityLivingBase boss) {
            super(world, pos, variant, boss);
        }
    }
}
