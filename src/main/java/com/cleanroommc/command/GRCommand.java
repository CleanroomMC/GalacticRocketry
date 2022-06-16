package com.cleanroommc.command;

import net.minecraft.command.ICommandSender;
import net.minecraftforge.server.command.CommandTreeBase;

import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.List;

public class GRCommand extends CommandTreeBase {

    public GRCommand() {
        addSubcommand(new CommandDimTP());
    }

    @Nonnull
    @Override
    public String getName() {
        return "galacticrocketry";
    }

    @Nonnull
    @Override
    public List<String> getAliases() {
        return Collections.singletonList("gr");
    }

    @Nonnull
    @Override
    public String getUsage(@Nonnull ICommandSender sender) {
        return "galacticrocketry.command.usage";
    }
}
