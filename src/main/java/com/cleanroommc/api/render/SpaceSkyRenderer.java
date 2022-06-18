package com.cleanroommc.api.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.vertex.VertexBuffer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.client.IRenderHandler;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import java.util.Random;

/**
 * the methods in this class are modified from {@link RenderGlobal}'s `renderSky()`, `generateStars()`, and `renderStars()`
 */
@SideOnly(Side.CLIENT)
public class SpaceSkyRenderer extends IRenderHandler {

    private static final int STARS_AMOUNT = 3000;

    @Override
    public void render(float partialTicks, @Nonnull WorldClient world, @Nonnull Minecraft mc) {
        RenderGlobal renderGlobal = mc.renderGlobal;
        generateStars(renderGlobal);

        Entity entity = mc.getRenderViewEntity();
        if (entity == null) return;

        GlStateManager.disableTexture2D();
        Vec3d vec3d = world.getSkyColor(entity, partialTicks);
        float x = (float) vec3d.x;
        float y = (float) vec3d.y;
        float z = (float) vec3d.z;

        int pass = MinecraftForgeClient.getRenderPass();
        if (pass != 2) {
            float xNew = (x * 30.0F + y * 59.0F + z * 11.0F) / 100.0F;
            float yNew = (x * 30.0F + y * 70.0F) / 100.0F;
            float zNew = (x * 30.0F + z * 70.0F) / 100.0F;
            x = xNew;
            y = yNew;
            z = zNew;
        }

        GlStateManager.color(x, y, z);
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        GlStateManager.depthMask(false);
        GlStateManager.enableFog();
        GlStateManager.color(x, y, z);

        if (OpenGlHelper.useVbo()) {
            renderGlobal.skyVBO.bindBuffer();
            GlStateManager.glEnableClientState(32884);
            GlStateManager.glVertexPointer(3, 5126, 12, 0);
            renderGlobal.skyVBO.drawArrays(7);
            renderGlobal.skyVBO.unbindBuffer();
            GlStateManager.glDisableClientState(32884);
        } else {
            GlStateManager.callList(renderGlobal.glSkyList);
        }

        GlStateManager.disableFog();
        GlStateManager.disableAlpha();
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        RenderHelper.disableStandardItemLighting();

        GlStateManager.enableTexture2D();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        GlStateManager.pushMatrix();
        GlStateManager.rotate(world.getCelestialAngle(partialTicks) * 360.0F, 1.0F, 1.0F, 1.0F); // rotating stars in the x, y, and z instead of x only because it's cool
        GlStateManager.disableTexture2D();
        float starBrightness = world.getStarBrightness(partialTicks);

        if (starBrightness > 0.0F) {
            GlStateManager.color(starBrightness, starBrightness, starBrightness, starBrightness);

            if (OpenGlHelper.useVbo()) {
                renderGlobal.starVBO.bindBuffer();
                GlStateManager.glEnableClientState(32884);
                GlStateManager.glVertexPointer(3, 5126, 12, 0);
                renderGlobal.starVBO.drawArrays(7);
                renderGlobal.starVBO.unbindBuffer();
                GlStateManager.glDisableClientState(32884);
            } else {
                GlStateManager.callList(renderGlobal.starGLCallList);
            }
        }

        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.disableBlend();
        GlStateManager.enableAlpha();
        GlStateManager.enableFog();
        GlStateManager.popMatrix();
        GlStateManager.disableTexture2D();
        GlStateManager.color(0.0F, 0.0F, 0.0F);
        double eyeLevel = mc.player.getPositionEyes(partialTicks).y - world.getHorizon();

        if (eyeLevel < 0.0D) {
            GlStateManager.pushMatrix();
            GlStateManager.translate(0.0F, 12.0F, 0.0F);

            if (OpenGlHelper.useVbo()) {
                renderGlobal.sky2VBO.bindBuffer();
                GlStateManager.glEnableClientState(32884);
                GlStateManager.glVertexPointer(3, 5126, 12, 0);
                renderGlobal.sky2VBO.drawArrays(7);
                renderGlobal.sky2VBO.unbindBuffer();
                GlStateManager.glDisableClientState(32884);
            } else {
                GlStateManager.callList(renderGlobal.glSkyList2);
            }

            GlStateManager.popMatrix();
            float starLevel = -((float) (eyeLevel + 65.0D));
            bufferbuilder.begin(7, DefaultVertexFormats.POSITION_COLOR);
            bufferbuilder.pos(-1.0D, starLevel, 1.0D).color(0, 0, 0, 255).endVertex();
            bufferbuilder.pos(1.0D, starLevel, 1.0D).color(0, 0, 0, 255).endVertex();
            bufferbuilder.pos(1.0D, -1.0D, 1.0D).color(0, 0, 0, 255).endVertex();
            bufferbuilder.pos(-1.0D, -1.0D, 1.0D).color(0, 0, 0, 255).endVertex();
            bufferbuilder.pos(-1.0D, -1.0D, -1.0D).color(0, 0, 0, 255).endVertex();
            bufferbuilder.pos(1.0D, -1.0D, -1.0D).color(0, 0, 0, 255).endVertex();
            bufferbuilder.pos(1.0D, starLevel, -1.0D).color(0, 0, 0, 255).endVertex();
            bufferbuilder.pos(-1.0D, starLevel, -1.0D).color(0, 0, 0, 255).endVertex();
            bufferbuilder.pos(1.0D, -1.0D, -1.0D).color(0, 0, 0, 255).endVertex();
            bufferbuilder.pos(1.0D, -1.0D, 1.0D).color(0, 0, 0, 255).endVertex();
            bufferbuilder.pos(1.0D, starLevel, 1.0D).color(0, 0, 0, 255).endVertex();
            bufferbuilder.pos(1.0D, starLevel, -1.0D).color(0, 0, 0, 255).endVertex();
            bufferbuilder.pos(-1.0D, starLevel, -1.0D).color(0, 0, 0, 255).endVertex();
            bufferbuilder.pos(-1.0D, starLevel, 1.0D).color(0, 0, 0, 255).endVertex();
            bufferbuilder.pos(-1.0D, -1.0D, 1.0D).color(0, 0, 0, 255).endVertex();
            bufferbuilder.pos(-1.0D, -1.0D, -1.0D).color(0, 0, 0, 255).endVertex();
            bufferbuilder.pos(-1.0D, -1.0D, -1.0D).color(0, 0, 0, 255).endVertex();
            bufferbuilder.pos(-1.0D, -1.0D, 1.0D).color(0, 0, 0, 255).endVertex();
            bufferbuilder.pos(1.0D, -1.0D, 1.0D).color(0, 0, 0, 255).endVertex();
            bufferbuilder.pos(1.0D, -1.0D, -1.0D).color(0, 0, 0, 255).endVertex();
            tessellator.draw();
        }

        if (world.provider.isSkyColored()) GlStateManager.color(x * 0.2F + 0.04F, y * 0.2F + 0.04F, z * 0.6F + 0.1F);
        else GlStateManager.color(x, y, z);

        GlStateManager.pushMatrix();
        GlStateManager.translate(0.0F, -((float) (eyeLevel - 16.0D)), 0.0F);
        GlStateManager.callList(renderGlobal.glSkyList2);
        GlStateManager.popMatrix();
        GlStateManager.enableTexture2D();
        GlStateManager.depthMask(true);
    }

    public static void generateStars(@Nonnull RenderGlobal renderGlobal) {
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();

        if (renderGlobal.starVBO != null) {
            renderGlobal.starVBO.deleteGlBuffers();
        }

        if (renderGlobal.starGLCallList >= 0) {
            GLAllocation.deleteDisplayLists(renderGlobal.starGLCallList);
            renderGlobal.starGLCallList = -1;
        }

        if (OpenGlHelper.useVbo()) {
            renderGlobal.starVBO = new VertexBuffer(renderGlobal.vertexBufferFormat);
            renderStars(bufferbuilder);
            bufferbuilder.finishDrawing();
            bufferbuilder.reset();
            renderGlobal.starVBO.bufferData(bufferbuilder.getByteBuffer());
        } else {
            renderGlobal.starGLCallList = GLAllocation.generateDisplayLists(1);
            GlStateManager.pushMatrix();
            GlStateManager.glNewList(renderGlobal.starGLCallList, 4864);
            renderStars(bufferbuilder);
            tessellator.draw();
            GlStateManager.glEndList();
            GlStateManager.popMatrix();
        }
    }

    public static void renderStars(@Nonnull BufferBuilder bufferBuilderIn) {
        Random random = new Random(42069L); //yes
        bufferBuilderIn.begin(7, DefaultVertexFormats.POSITION);

        for (int i = 0; i < STARS_AMOUNT; ++i) {
            double x = random.nextFloat() * 2.0F - 1.0F;
            double y = random.nextFloat() * 2.0F - 1.0F;
            double z = random.nextFloat() * 2.0F - 1.0F;
            double d3 = 0.15F + random.nextFloat() * 0.1F;
            double d4 = x * x + y * y + z * z;

            if (d4 < 1.0D && d4 > 0.01D) {
                d4 = 1.0D / Math.sqrt(d4);
                x = x * d4;
                y = y * d4;
                z = z * d4;
                double d8 = Math.atan2(x, z);
                double d9 = Math.sin(d8);
                double d10 = Math.cos(d8);
                double d11 = Math.atan2(Math.sqrt(x * x + z * z), y);
                double d12 = Math.sin(d11);
                double d13 = Math.cos(d11);
                double d14 = random.nextDouble() * Math.PI * 2.0D;
                double d15 = Math.sin(d14);
                double d16 = Math.cos(d14);

                for (int j = 0; j < 4; ++j) {
                    double d18 = (double) ((j & 2) - 1) * d3;
                    double d19 = (double) ((j + 1 & 2) - 1) * d3;
                    double d21 = d18 * d16 - d19 * d15;
                    double d22 = d19 * d16 + d18 * d15;
                    double d23 = d21 * d12;
                    double d24 = -d21 * d13;
                    double d25 = d24 * d9 - d22 * d10;
                    double d26 = d22 * d9 + d24 * d10;
                    bufferBuilderIn.pos(x * 100.0D + d25, y * 100.0D + d23, z * 100.0D + d26).endVertex();
                }
            }
        }
    }
}
