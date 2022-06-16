package com.cleanroommc.command;

import com.cleanroommc.world.NoPortalTeleporter;
import net.minecraft.command.*;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.play.server.SPacketPlayerPosLook;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.DimensionManager;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

public class CommandDimTP extends CommandBase {

    @Nonnull
    @Override
    public String getName() {
        return "tp";
    }

    public int getRequiredPermissionLevel() {
        return 2;
    }

    @Nonnull
    @Override
    public String getUsage(@Nonnull ICommandSender sender) {
        return "galacticrocketry.command.tp.usage";
    }

    @Override
    public void execute(@Nonnull MinecraftServer server, @Nonnull ICommandSender sender, @Nonnull String[] args) throws CommandException {
        if (args.length < 4) throw new WrongUsageException(getUsage(sender));

        if (sender instanceof EntityPlayerMP) {
            EntityPlayerMP player = (EntityPlayerMP) sender;
            if (!(player.world instanceof WorldServer)) return;

            Vec3d vec3d = player.getPositionVector();
            int i = 0;
            CommandBase.CoordinateArg x = parseCoordinate(vec3d.x, args[i++], true);
            CommandBase.CoordinateArg y = parseCoordinate(vec3d.y, args[i++], -4096, 4096, false);
            CommandBase.CoordinateArg z = parseCoordinate(vec3d.z, args[i++], true);

            int dim = parseInt(args[i++], -255, 255);
            if (!DimensionManager.isDimensionRegistered(dim))
                throw new NumberInvalidException("galacticrocketry.command.tp.invalid_dim", dim);

            CommandBase.CoordinateArg yaw = parseCoordinate(args.length > i ? (double) player.rotationYaw : (double) player.rotationYaw, args.length > i ? args[i] : "~", false);
            i += 1;
            CommandBase.CoordinateArg pitch = parseCoordinate(args.length > i ? (double) player.rotationPitch : (double) player.rotationPitch, args.length > i ? args[i] : "~", false);

            doTeleport(player, x, y, z, dim, yaw, pitch);
            notifyCommandListener(sender, this, "galacticrocketry.command.tp.success", x.getResult(), y.getResult(), z.getResult(), dim);
        }
    }

    private static void doTeleport(@Nonnull EntityPlayerMP player, @Nonnull CommandBase.CoordinateArg x, @Nonnull CommandBase.CoordinateArg y, @Nonnull CommandBase.CoordinateArg z, int dimension, @Nonnull CommandBase.CoordinateArg yaw, @Nonnull CommandBase.CoordinateArg pitch) {
        Set<SPacketPlayerPosLook.EnumFlags> set = EnumSet.noneOf(SPacketPlayerPosLook.EnumFlags.class);
        float yawAmount = (float) yaw.getAmount();

        if (yaw.isRelative()) set.add(SPacketPlayerPosLook.EnumFlags.Y_ROT);
        else yawAmount = MathHelper.wrapDegrees(yawAmount);

        float pitchAmount = (float) pitch.getAmount();

        if (pitch.isRelative()) set.add(SPacketPlayerPosLook.EnumFlags.X_ROT);
        else pitchAmount = MathHelper.wrapDegrees(pitchAmount);

        player.dismountRidingEntity();
        player.changeDimension(dimension, new NoPortalTeleporter((WorldServer) player.world));
        player.connection.setPlayerLocation(x.getResult(), y.getResult(), z.getResult(), yawAmount, pitchAmount, set);
        player.setRotationYawHead(yawAmount);

        if (!player.isElytraFlying()) {
            player.motionY = 0.0D;
            player.onGround = true;
        }
    }

    @Nonnull
    @Override
    public List<String> getTabCompletions(@Nonnull MinecraftServer server, @Nonnull ICommandSender sender, @Nonnull String[] args, @Nullable BlockPos targetPos) {
        return args.length < 4 ? getTabCompletionCoordinate(args, 1, targetPos) : Collections.emptyList();
    }
}
