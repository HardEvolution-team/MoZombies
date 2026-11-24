package com.electrifiedded.mozombies.entity;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * Shared behaviour of all Mo' Zombies hostile mobs (the 1.7.10 original copy-pasted this into every class).
 * Subclasses only supply the numbers: speed, attributes, sounds, drops.
 */
public abstract class EntityMoZombie extends EntityMob {
    /** AI speed multiplier (original: 0.15 .. 1.0). */
    private final double aiSpeed;

    protected EntityMoZombie(World world, double aiSpeed) {
        super(world);
        this.aiSpeed = aiSpeed;
        setSize(0.6F, 1.8F);
        ((net.minecraft.pathfinding.PathNavigateGround) getNavigator()).setBreakDoors(true);
        setupAI();
    }

    /** vanilla calls this from the EntityLiving constructor, before aiSpeed is assigned (was 0 => mobs froze) */
    @Override
    protected void initEntityAI() {}

    private void setupAI() {
        double s = aiSpeed;
        tasks.addTask(0, new EntityAISwimming(this));
        tasks.addTask(2, new EntityAIAttackMelee(this, s, false));
        tasks.addTask(5, new EntityAIMoveTowardsRestriction(this, s));
        tasks.addTask(6, new EntityAIMoveThroughVillage(this, s, false));
        tasks.addTask(7, new EntityAIWander(this, s));
        tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        tasks.addTask(8, new EntityAILookIdle(this));
        targetTasks.addTask(1, new EntityAIHurtByTarget(this, true));
        targetTasks.addTask(2, new EntityAINearestAttackableTarget<>(this, EntityPlayer.class, true));
        targetTasks.addTask(2, new EntityAINearestAttackableTarget<>(this, EntityVillager.class, false));
        addExtraAI();
    }

    protected void addExtraAI() {}

    protected abstract double maxHealth();
    protected double followRange() { return 40.0; }
    protected abstract double moveSpeed();
    protected abstract double attackDamage();

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(maxHealth());
        getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(followRange());
        getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(moveSpeed());
        getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(attackDamage());
    }

    /** the original did not set health above 20 on spawn; we keep max health as designed and fill it */
    @Override
    public void onLivingUpdate() {
        if (burnsInDaylight()) burnInSun();
        super.onLivingUpdate();
    }

    protected boolean burnsInDaylight() { return false; }

    /** 1.7.10 zombie sun-burn, helmet takes damage instead of burning. */
    protected void burnInSun() { sunBurn(this); }

    public static void sunBurn(net.minecraft.entity.EntityLiving e) {
        World world = e.world;
        java.util.Random rand = e.getRNG();
        if (world.isDaytime() && !world.isRemote && !e.isChild()) {
            float f = e.getBrightness();
            BlockPos pos = new BlockPos(e.posX, e.posY, e.posZ);
            if (f > 0.5F && rand.nextFloat() * 30.0F < (f - 0.4F) * 2.0F && world.canSeeSky(pos)) {
                boolean burn = true;
                ItemStack helm = e.getItemStackFromSlot(EntityEquipmentSlot.HEAD);
                if (!helm.isEmpty()) {
                    if (helm.isItemStackDamageable()) {
                        helm.setItemDamage(helm.getItemDamage() + rand.nextInt(2));
                        if (helm.getItemDamage() >= helm.getMaxDamage()) {
                            e.setItemStackToSlot(EntityEquipmentSlot.HEAD, ItemStack.EMPTY);
                        }
                    }
                    burn = false;
                }
                if (burn) e.setFire(8);
            }
        }
    }

    @Override
    protected SoundEvent getAmbientSound() { return SoundEvents.ENTITY_ZOMBIE_AMBIENT; }
    @Override
    protected SoundEvent getHurtSound(DamageSource src) { return SoundEvents.ENTITY_ZOMBIE_HURT; }
    @Override
    protected SoundEvent getDeathSound() { return SoundEvents.ENTITY_ZOMBIE_DEATH; }
    @Override
    protected void playStepSound(BlockPos pos, net.minecraft.block.Block block) {
        playSound(SoundEvents.ENTITY_ZOMBIE_STEP, 0.15F, 1.0F);
    }

    @Override
    public EnumCreatureAttribute getCreatureAttribute() { return EnumCreatureAttribute.UNDEAD; }

    /** held item the original returned from getHeldItem(), shown in the main hand */
    protected ItemStack heldItem() { return ItemStack.EMPTY; }

    @Override
    public ItemStack getHeldItemMainhand() {
        ItemStack h = heldItem();
        return h.isEmpty() ? super.getHeldItemMainhand() : h;
    }

    // ---- drops (1.7.10 getDropItem / dropRareDrop / dropFewItems) ----
    protected Item commonDrop() { return null; }
    /** one of these is dropped (rare drop); empty = none */
    protected ItemStack[] rareDrops() { return new ItemStack[0]; }

    @Override
    protected void dropLoot(boolean recentlyHit, int looting, DamageSource src) {
        dropCommon(recentlyHit, looting);
        if (recentlyHit) {
            ItemStack[] rare = rareDrops();
            if (rare.length > 0 && rand.nextInt(200) - looting < 5) {
                entityDropItem(rare[rand.nextInt(rare.length)].copy(), 0.0F);
            }
        }
    }

    protected void dropCommon(boolean recentlyHit, int looting) {
        Item item = commonDrop();
        if (item == null) return;
        int n = rand.nextInt(3);
        if (looting > 0) n += rand.nextInt(looting + 1);
        for (int i = 0; i < n; i++) dropItem(item, 1);
    }

}
