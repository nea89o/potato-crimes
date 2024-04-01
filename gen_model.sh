#!/usr/bin/env bash
awk '/		PartDefinition partdefinition = meshdefinition.getRoot\(\);/{flag=1;next}/	\}/{flag=0;next} flag' PotatoGuard.java \
| sed 's/PartDefinition/ModelPartData/;s/partdefinition/modelPartData/;s/addOrReplaceChild/addChild/;s/CubeListBuilder/ModelPartBuilder/;s/texOffs/uv/g;s/addBox/cuboid/g;s/CubeDeformation/Dilation/g;s/LayerDefinition.create/TexturedModelData.of/;s/meshdefinition/modelData/g;s/PartPose.offset/ModelTransform.pivot/'
