package com.electrifiedded.mozombies.client;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.ResourceLocation;

/** biped renderer (armor + held item layers like the 1.7.10 RenderBiped) with a fixed texture */
public class RenderMoZombie<T extends EntityLiving> extends RenderBiped<T> {
    private final ResourceLocation texture;

    public RenderMoZombie(RenderManager m, ModelBiped model, ResourceLocation texture) {
        super(m, model, 0.5F);
        this.texture = texture;
    }

    @Override
    protected ResourceLocation getEntityTexture(T entity) { return texture; }
}
