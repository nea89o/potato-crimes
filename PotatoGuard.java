// Made with Blockbench 4.9.4
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


public class PotatoGuard<T extends Entity> extends EntityModel<T> {
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation("modid", "potatoguard"), "main");
	private final ModelPart PotatoGuard;
	private final ModelPart PotatoHead;

	public PotatoGuard(ModelPart root) {
		this.PotatoGuard = root.getChild("PotatoGuard");
		this.PotatoHead = root.getChild("PotatoHead");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition PotatoGuard = partdefinition.addOrReplaceChild("PotatoGuard", CubeListBuilder.create().texOffs(0, 37).addBox(-7.0F, -21.5F, -7.0F, 14.0F, 21.0F, 14.0F, new CubeDeformation(0.0F))
		.texOffs(0, 0).addBox(-8.0F, 0.5F, -8.0F, 16.0F, 21.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.5F, 0.0F));

		PartDefinition PotatoHead = partdefinition.addOrReplaceChild("PotatoHead", CubeListBuilder.create().texOffs(56, 29).addBox(-4.0F, -6.5F, -4.0F, 8.0F, 13.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -28.5F, 0.0F));

		return LayerDefinition.create(meshdefinition, 128, 128);
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		PotatoGuard.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		PotatoHead.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}
}