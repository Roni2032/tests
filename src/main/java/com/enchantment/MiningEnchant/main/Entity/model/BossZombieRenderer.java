package com.enchantment.MiningEnchant.main.Entity.model;

import com.enchantment.MiningEnchant.main.Entity.BossEntity.BossZombieEntity;
import com.enchantment.MiningEnchant.main.MiningEnchant;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class BossZombieRenderer extends MobRenderer<BossZombieEntity,BossZombieModel<BossZombieEntity>> {

    public BossZombieRenderer(EntityRendererProvider.Context context) {
        super(context, new BossZombieModel<>(context.bakeLayer(BossZombieModel.LAYER_LOCATION)), 0.5f);
    }

    @Override
    public ResourceLocation getTextureLocation(BossZombieEntity bossZombieEntity) {
        return new ResourceLocation(MiningEnchant.MOD_ID,"textures/entity/boss_zombie.png");
    }

    @Override
    public void render(BossZombieEntity p_115455_, float p_115456_, float p_115457_, PoseStack p_115458_, MultiBufferSource p_115459_, int p_115460_) {
        super.render(p_115455_, p_115456_, p_115457_, p_115458_, p_115459_, p_115460_);
    }
}
