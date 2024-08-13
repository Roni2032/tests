package com.enchantment.MiningEnchant.main.Entity.model;// Made with Blockbench 4.10.4
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


import com.enchantment.MiningEnchant.main.Entity.Animation.BossZombieAnimations;
import com.enchantment.MiningEnchant.main.Entity.BossEntity.BossZombieEntity;
import com.enchantment.MiningEnchant.main.MiningEnchant;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;

public class BossZombieModel<T extends Entity> extends HierarchicalModel<T> {
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(MiningEnchant.MOD_ID, "boss_name"), "main");
	private final ModelPart waist;
	private final ModelPart body;
	private final ModelPart head;
	private final ModelPart hat;
	private final ModelPart rightArm;
	private final ModelPart rightItem;
	private final ModelPart leftArm;
	private final ModelPart leftItem;
	private final ModelPart rightLeg;
	private final ModelPart leftLeg;

	public BossZombieModel(ModelPart root) {
		this.waist = root.getChild("waist");
		this.body = this.waist.getChild("body");
		this.head = this.body.getChild("head");
		this.hat = this.head.getChild("hat");
		this.rightArm = this.body.getChild("rightArm");
		this.rightItem =this.rightArm.getChild("rightItem");
		this.leftArm = this.body.getChild("leftArm");
		this.leftItem = this.leftArm.getChild("leftItem");
		this.rightLeg = this.waist.getChild("rightLeg");
		this.leftLeg = this.waist.getChild("leftLeg");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition waist = partdefinition.addOrReplaceChild("waist", CubeListBuilder.create(), PartPose.offset(0.0F, 12.0F, 0.0F));

		PartDefinition body = waist.addOrReplaceChild("body", CubeListBuilder.create().texOffs(16, 16).addBox(-4.0F, -12.0F, -2.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition head = body.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -12.0F, 0.0F));

		PartDefinition hat = head.addOrReplaceChild("hat", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition rightArm = body.addOrReplaceChild("rightArm", CubeListBuilder.create().texOffs(40, 0).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-5.0F, -10.0F, 0.0F));

		PartDefinition rightItem = rightArm.addOrReplaceChild("rightItem", CubeListBuilder.create(), PartPose.offset(-1.0F, 7.0F, 1.0F));

		PartDefinition leftArm = body.addOrReplaceChild("leftArm", CubeListBuilder.create().texOffs(40, 16).mirror().addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(5.0F, -10.0F, 0.0F));

		PartDefinition leftItem = leftArm.addOrReplaceChild("leftItem", CubeListBuilder.create(), PartPose.offset(1.0F, 7.0F, 1.0F));

		PartDefinition rightLeg = waist.addOrReplaceChild("rightLeg", CubeListBuilder.create().texOffs(0, 16).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.9F, 0.0F, 0.0F));

		PartDefinition leftLeg = waist.addOrReplaceChild("leftLeg", CubeListBuilder.create().texOffs(0, 16).mirror().addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(1.9F, 0.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 64, 32);
	}

	@Override
	public void setupAnim(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
		this.applyHeadRotate(netHeadYaw,headPitch,ageInTicks);

		this.animateWalk(BossZombieAnimations.attack_bare_hand,limbSwing,limbSwingAmount,2.0f,2.5f);
		this.animate(((BossZombieEntity)entity).idleAnimation,BossZombieAnimations.attack_bare_hand,1.0f);
		this.animate(((BossZombieEntity)entity).blewAnimation,BossZombieAnimations.blow_player,1.0f);
		this.animate(((BossZombieEntity)entity).useMagicAnimation,BossZombieAnimations.use_masic,1.0f);
	}

	private void applyHeadRotate(float headY,float headPitch,float ageInTicks){
		headY = Mth.clamp(headY,-30.0f,30.0f);
		headPitch = Mth.clamp(headPitch,-25.0f,45.0f);

		this.head.yRot = headY * ((float)Math.PI / 180.0f);
		this.head.xRot = headPitch * ((float)Math.PI / 180.0f);
	}
	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		waist.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}

	@Override
	public ModelPart root() {
		return waist;
	}
}