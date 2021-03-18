package com.skyternity.bottomless.objloader;

import com.skyternity.bottomless.BottomlessMain;
import com.skyternity.bottomless.objloader.baked.OBJUnbakedModel;
import de.javagl.obj.Obj;
import de.javagl.obj.ObjReader;
import de.javagl.obj.ObjUtils;
import net.fabricmc.fabric.api.client.model.ModelProviderContext;
import net.fabricmc.fabric.api.client.model.ModelResourceProvider;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.model.UnbakedModel;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.resource.Resource;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class OBJLoader implements ModelResourceProvider, Function<ResourceManager, ModelResourceProvider> {
    public static final OBJLoader INSTANCE = new OBJLoader();


    private OBJLoader() {
    }

    public OBJUnbakedModel loadModel(Reader reader, String modid, ResourceManager manager, ModelTransformation transform) {
        OBJUnbakedModel model;

        try {
            Obj obj = ObjUtils.convertToRenderable(ObjReader.read(reader));
            model = new OBJUnbakedModel(ObjUtils.triangulate(obj), loadMTL(manager, obj.getMtlFileNames()), transform);
        } catch (IOException e) {
            BottomlessMain.LOGGER.error("Could not read obj model!", e);
            return null;
        }

        return model;
    }

    public Map<String, mtlloader> loadMTL(ResourceManager manager, List<String> mtlNames) throws IOException {
        Map<String, mtlloader> mtls = new LinkedHashMap<>();
        for (String name : mtlNames) {
            Identifier resourceId = new Identifier("bottomless", "models/block/obj/" + name);
            // Use 1.0.0 MTL path as a fallback

            // Continue with normal resource loading code
            if(manager.containsResource(resourceId)) {
                Resource resource = manager.getResource(resourceId);

                MtlReader.read(resource.getInputStream()).forEach(mtl -> {
                    mtls.put(mtl.getName(), mtl);
                });
            } else {
                BottomlessMain.LOGGER.warn("Warning, a model specifies an MTL File but it could not be found! Source: " + "the_beginning_remaster" + ":" + name);
            }
        }

        return mtls;
    }

    @Override
    public UnbakedModel loadModelResource(Identifier identifier, ModelProviderContext modelProviderContext) {
        return loadModelResource (identifier, modelProviderContext, ModelTransformation.NONE);
    }

    protected UnbakedModel loadModelResource(Identifier identifier, ModelProviderContext modelProviderContext,
                                             ModelTransformation transform) {
        if(identifier.getPath().endsWith(".obj")) {
            ResourceManager resourceManager = MinecraftClient.getInstance().getResourceManager();

            try (Reader reader = new InputStreamReader(resourceManager.getResource(new Identifier(identifier.getNamespace(), "models/" + identifier.getPath())).getInputStream())) {
                return loadModel(reader, identifier.getNamespace(), resourceManager, transform);
            } catch (IOException e) {
                BottomlessMain.LOGGER.error("Unable to load OBJ Model, Source: " + identifier.toString(), e);
            }
        }

        return null;
    }

    @Override
    public ModelResourceProvider apply(ResourceManager manager) {
        return this;
    }
}