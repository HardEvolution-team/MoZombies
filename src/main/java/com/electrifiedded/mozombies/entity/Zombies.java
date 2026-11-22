package com.electrifiedded.mozombies.entity;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;

/** All Mo Zombies mobs. Numbers are those of the 1.7.10 original (Coocoo617). */
public final class Zombies {
    private Zombies() {}

    private static ItemStack[] goldTools() {
        return new ItemStack[]{s(Items.GOLDEN_AXE), s(Items.GOLDEN_PICKAXE), s(Items.GOLDEN_SHOVEL), s(Items.GOLDEN_HOE),
                s(Items.GOLDEN_HELMET), s(Items.GOLDEN_LEGGINGS), s(Items.GOLDEN_BOOTS), s(Items.GOLDEN_CHESTPLATE)};
    }
    private static ItemStack s(Item i) { return new ItemStack(i); }
    private static ItemStack s(Block b) { return new ItemStack(b); }

    public static class King extends EntityMoZombie {
        public King(World w) { super(w, 0.2); }
        protected double maxHealth() { return 25; }
        protected double moveSpeed() { return 1.0; }
        protected double attackDamage() { return 1.0; }
        protected Item commonDrop() { return Items.GOLD_INGOT; }
        protected ItemStack[] rareDrops() { return goldTools(); }
    }

    public static class Chef extends EntityMoZombie {
        private static final Item[] FOOD = {Items.APPLE, Items.BAKED_POTATO, Items.BEEF, Items.BREAD, Items.CAKE, Items.CARROT,
                Items.CHICKEN, Items.COOKED_BEEF, Items.COOKED_CHICKEN, Items.COOKED_FISH, Items.COOKED_PORKCHOP, Items.COOKIE,
                Items.EGG, Items.FISH, Items.MELON, Items.MUSHROOM_STEW, Items.POTATO, Items.PUMPKIN_PIE, Items.SUGAR};
        public Chef(World w) { super(w, 0.23); }
        protected double maxHealth() { return 20; }
        protected double moveSpeed() { return 1.0; }
        protected double attackDamage() { return 4.0; }
        protected boolean burnsInDaylight() { return true; }
        protected ItemStack heldItem() { return s(Items.IRON_AXE); }
        protected ItemStack[] rareDrops() { return goldTools(); }
        @Override
        protected void dropCommon(boolean hit, int looting) {
            int j = rand.nextInt(3) + 1;
            for (int k = 0; k < j; k++) {
                int l = rand.nextInt(3);
                Item item = FOOD[rand.nextInt(FOOD.length)];
                if (looting > 0) l += rand.nextInt(looting + 1);
                for (int i = 0; i < l; i++) dropItem(item, 1);
            }
        }
    }

    public static class Cyborg extends EntityMoZombie {
        public Cyborg(World w) { super(w, 0.23); }
        protected double maxHealth() { return 20; }
        protected double moveSpeed() { return 1.0; }
        protected double attackDamage() { return 6.0; }
        protected SoundEvent getHurtSound(DamageSource d) { return SoundEvents.BLOCK_ANVIL_LAND; }
        protected Item commonDrop() { return Items.REDSTONE; }
        protected ItemStack[] rareDrops() {
            return new ItemStack[]{s(Items.REPEATER), s(Items.COMPARATOR), s(Blocks.REDSTONE_TORCH), s(Blocks.REDSTONE_BLOCK),
                    s(Blocks.DISPENSER), s(Blocks.DROPPER), s(Blocks.LEVER)};
        }
    }

    public static class Dwarf extends EntityMoZombie {
        public Dwarf(World w) { super(w, 0.45); }
        protected double maxHealth() { return 20; }
        protected double moveSpeed() { return 0.75; }
        protected double attackDamage() { return 1.0; }
        protected boolean burnsInDaylight() { return true; }
        protected SoundEvent getHurtSound(DamageSource d) { return SoundEvents.ENTITY_ZOMBIE_PIG_AMBIENT; }
        protected Item commonDrop() { return Items.COAL; }
        protected ItemStack[] rareDrops() { return goldTools(); }
    }

    public static class Herobrine extends EntityMoZombie {
        public Herobrine(World w) { super(w, 1.0); isImmuneToFire = true; }
        protected void addExtraAI() {
            targetTasks.addTask(3, new EntityAINearestAttackableTarget<>(this, Notch.class, false));
        }
        protected double maxHealth() { return 20; }
        protected double moveSpeed() { return 0.75; }
        protected double attackDamage() { return 1.0; }
        @Override
        public boolean isBurning() { return true; }
        protected ItemStack heldItem() { return s(Blocks.FIRE); }
        protected Item commonDrop() { return Item.getItemFromBlock(Blocks.FIRE); }
        protected ItemStack[] rareDrops() { return new ItemStack[]{s(Items.DIAMOND)}; }
    }

    public static class Knight extends EntityMoZombie {
        public Knight(World w) { super(w, 0.3); }
        protected double maxHealth() { return 25; }
        protected double moveSpeed() { return 1.0; }
        protected double attackDamage() { return 3.0; }
        protected ItemStack heldItem() { return s(Items.IRON_SWORD); }
        protected Item commonDrop() { return Items.IRON_INGOT; }
        protected ItemStack[] rareDrops() {
            return new ItemStack[]{s(Items.CHAINMAIL_BOOTS), s(Items.CHAINMAIL_CHESTPLATE), s(Items.CHAINMAIL_LEGGINGS), s(Items.CHAINMAIL_HELMET)};
        }
    }

    public static class Miner extends EntityMoZombie {
        public Miner(World w) { super(w, 0.45); }
        protected double maxHealth() { return 20; }
        protected double moveSpeed() { return 0.75; }
        protected double attackDamage() { return 2.0; }
        protected ItemStack heldItem() { return s(Items.IRON_PICKAXE); }
        protected Item commonDrop() { return Item.getItemFromBlock(Blocks.COBBLESTONE); }
        protected ItemStack[] rareDrops() {
            return new ItemStack[]{s(Items.COAL), s(Items.IRON_INGOT), s(Items.GOLD_INGOT), s(Items.DIAMOND),
                    s(Items.WOODEN_PICKAXE), s(Items.STONE_PICKAXE), s(Items.IRON_PICKAXE), s(Items.GOLDEN_PICKAXE)};
        }
    }

    public static class Notch extends EntityMoZombie {
        public Notch(World w) { super(w, 0.15); }
        protected void addExtraAI() {
            targetTasks.addTask(3, new EntityAINearestAttackableTarget<>(this, Herobrine.class, false));
        }
        protected double maxHealth() { return 20; }
        protected double moveSpeed() { return 1.0; }
        protected double attackDamage() { return 500.0; }
        protected boolean burnsInDaylight() { return true; }
        protected Item commonDrop() { return Items.APPLE; }
        protected ItemStack[] rareDrops() { return new ItemStack[]{new ItemStack(Items.GOLDEN_APPLE)}; }
    }

    /** original attribute code was in a misnamed (never called) method: intended values are used */
    public static class Pa extends EntityMoZombie {
        public Pa(World w) { super(w, 0.5); }
        protected double maxHealth() { return 20; }
        protected double moveSpeed() { return 0.8; }
        protected double attackDamage() { return 9.0; }
        protected Item commonDrop() { return Items.LEATHER; }
        protected ItemStack[] rareDrops() {
            return new ItemStack[]{s(Items.LEATHER_BOOTS), s(Items.LEATHER_CHESTPLATE), s(Items.LEATHER_HELMET), s(Items.LEATHER_LEGGINGS)};
        }
    }

    public static class Pirate extends EntityMoZombie {
        public Pirate(World w) { super(w, 0.45); }
        protected double maxHealth() { return 20; }
        protected double moveSpeed() { return 0.75; }
        protected double attackDamage() { return 6.0; }
        protected boolean burnsInDaylight() { return true; }
        protected ItemStack heldItem() { return s(Items.GOLDEN_SWORD); }
        protected Item commonDrop() { return Items.GOLD_NUGGET; }
        protected ItemStack[] rareDrops() { return new ItemStack[]{s(Items.GOLD_INGOT), s(Items.GOLDEN_SWORD)}; }
    }

    public static class Nether extends EntityMoZombie {
        public Nether(World w) { super(w, 0.23); isImmuneToFire = true; }
        protected double maxHealth() { return 20; }
        protected double moveSpeed() { return 1.0; }
        protected double attackDamage() { return 3.0; }
        protected SoundEvent getHurtSound(DamageSource d) { return SoundEvents.ENTITY_ZOMBIE_PIG_HURT; }
        protected SoundEvent getDeathSound() { return SoundEvents.ENTITY_GHAST_DEATH; }
        protected Item commonDrop() { return Items.ROTTEN_FLESH; }
        protected ItemStack[] rareDrops() {
            return new ItemStack[]{s(Items.NETHER_WART), s(Items.MAGMA_CREAM), s(Items.GHAST_TEAR), s(Items.BLAZE_ROD)};
        }
        @Override
        public boolean getCanSpawnHere() {
            return world.getDifficulty() != EnumDifficulty.PEACEFUL && world.getCollisionBoxes(this, getEntityBoundingBox()).isEmpty()
                    && !world.containsAnyLiquid(getEntityBoundingBox());
        }
    }

    public static class Disco extends EntityMoZombie {
        public Disco(World w) { super(w, 0.3); }
        protected double maxHealth() { return 20; }
        protected double moveSpeed() { return 1.0; }
        protected double attackDamage() { return 2.0; }
        protected boolean burnsInDaylight() { return true; }
        protected SoundEvent getAmbientSound() { return SoundEvents.BLOCK_NOTE_BASS; }
        protected ItemStack heldItem() { return s(Blocks.NOTEBLOCK); }
        protected Item commonDrop() { return Item.getItemFromBlock(Blocks.NOTEBLOCK); }
        protected ItemStack[] rareDrops() {
            return new ItemStack[]{s(Blocks.JUKEBOX), s(Items.RECORD_11), s(Items.RECORD_13), s(Items.RECORD_BLOCKS), s(Items.RECORD_CAT),
                    s(Items.RECORD_CHIRP), s(Items.RECORD_FAR), s(Items.RECORD_MALL), s(Items.RECORD_MELLOHI), s(Items.RECORD_STAL),
                    s(Items.RECORD_STRAD), s(Items.RECORD_WAIT), s(Items.RECORD_WARD)};
        }
        @Override
        public void onLivingUpdate() {
            world.spawnParticle(EnumParticleTypes.NOTE,
                    posX + rand.nextFloat() * width * 2.0F - width, posY + 0.5 + rand.nextFloat() * height,
                    posZ + rand.nextFloat() * width * 2.0F - width,
                    rand.nextGaussian() * 0.02, rand.nextGaussian() * 0.02, rand.nextGaussian() * 0.02);
            super.onLivingUpdate();
        }
    }

    /** Zombie Creeper: a creeper with the original stats, daylight burning and TNT drops. */
    public static class Creeper extends EntityCreeper {
        public Creeper(World w) { super(w); }
        @Override
        protected void initEntityAI() {
            tasks.addTask(1, new EntityAISwimming(this));
            tasks.addTask(2, new EntityAICreeperSwell(this));
            tasks.addTask(3, new EntityAIAttackMelee(this, 1.0, false));
            tasks.addTask(4, new EntityAIWander(this, 0.8));
            tasks.addTask(5, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
            tasks.addTask(6, new EntityAILookIdle(this));
            targetTasks.addTask(1, new EntityAINearestAttackableTarget<>(this, EntityPlayer.class, true));
            targetTasks.addTask(2, new EntityAINearestAttackableTarget<>(this, EntityVillager.class, true));
            targetTasks.addTask(3, new EntityAIHurtByTarget(this, false));
        }
        @Override
        protected void applyEntityAttributes() {
            super.applyEntityAttributes();
            getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(20.0);
            getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(40.0);
            getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.23);
        }
        @Override
        public void onLivingUpdate() { EntityMoZombie.sunBurn(this); super.onLivingUpdate(); }
        protected SoundEvent getAmbientSound() { return SoundEvents.ENTITY_ZOMBIE_AMBIENT; }
        protected SoundEvent getHurtSound(DamageSource d) { return SoundEvents.ENTITY_ZOMBIE_HURT; }
        protected SoundEvent getDeathSound() { return SoundEvents.ENTITY_ZOMBIE_DEATH; }
        @Override
        protected void dropLoot(boolean hit, int looting, DamageSource src) {
            int n = rand.nextInt(3);
            if (looting > 0) n += rand.nextInt(looting + 1);
            for (int i = 0; i < n; i++) dropItem(Item.getItemFromBlock(Blocks.TNT), 1);
        }
        @Override
        public net.minecraft.entity.EnumCreatureAttribute getCreatureAttribute() { return net.minecraft.entity.EnumCreatureAttribute.UNDEAD; }
    }

    /** passive wanderer lured by iron ingots */
    public static class Survivor extends EntityAnimal {
        public Survivor(World w) { super(w); setSize(0.6F, 2.0F); }
        @Override
        protected void initEntityAI() {
            tasks.addTask(0, new EntityAISwimming(this));
            tasks.addTask(1, new EntityAITempt(this, 0.45, Items.IRON_INGOT, false));
            tasks.addTask(2, new EntityAIWander(this, 0.45));
            tasks.addTask(3, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
            tasks.addTask(4, new EntityAILookIdle(this));
        }
        @Override
        protected void applyEntityAttributes() {
            super.applyEntityAttributes();
            getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(20.0);
            getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.75);
        }
        @Override
        public EntityAgeable createChild(EntityAgeable other) { return null; }
        protected SoundEvent getHurtSound(DamageSource d) { return SoundEvents.ENTITY_GENERIC_HURT; }
        protected SoundEvent getDeathSound() { return SoundEvents.ENTITY_GENERIC_HURT; }
        @Override
        protected void dropLoot(boolean hit, int looting, DamageSource src) {
            int n = rand.nextInt(3);
            if (looting > 0) n += rand.nextInt(looting + 1);
            for (int i = 0; i < n; i++) dropItem(Items.BREAD, 1);
        }
    }
}
