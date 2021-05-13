package org.bookmc.loader;

import net.minecraft.launchwrapper.ITweaker;
import net.minecraft.launchwrapper.LaunchClassLoader;
import org.bookmc.loader.book.DevelopmentModDiscoverer;
import org.bookmc.loader.vessel.ModVessel;
import org.spongepowered.asm.launch.MixinBootstrap;
import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.mixin.Mixins;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class BookMCLoader implements ITweaker {
    private final List<String> args = new ArrayList<>();

    private File gameDir;

    @Override
    public void acceptOptions(List<String> args, File gameDir, File assetsDir, String profile) {
        this.args.addAll(args);

        if (gameDir != null) {
            this.args.add("--gameDir");
            this.args.add(gameDir.getAbsolutePath());

            this.gameDir = gameDir;
        }

        if (assetsDir != null) {
            this.args.add("--assetsDir");
            this.args.add(assetsDir.getAbsolutePath());
        }

        if (profile != null) {
            this.args.add("--version");
            this.args.add(profile);
        }
    }

    @Override
    public void injectIntoClassLoader(LaunchClassLoader classLoader) {
        classLoader.addTransformerExclusion("org.bookmc.loader."); // Disallow transformation of mod loading

        MixinBootstrap.init();

        MixinEnvironment.getDefaultEnvironment().setSide(MixinEnvironment.Side.CLIENT);

        for (MinecraftModDiscoverer discoverer : Loader.getModDiscoverers()) {
            File[] files = gameDir.listFiles();

            if (files != null || discoverer instanceof DevelopmentModDiscoverer) {
                discoverer.discover(files);
            }
        }

        for (ModVessel vessel : Loader.getModVessels()) {
            String mixinEntrypoint = vessel.getMixinEntrypoint();

            if (mixinEntrypoint != null) {
                Mixins.addConfiguration(mixinEntrypoint);
            }
        }

        Mixins.addConfiguration("bookmc-client.mixins.json");

        classLoader.registerTransformer("org.bookmc.services.TransformationService");
    }

    @Override
    public String getLaunchTarget() {
        String version = args.get(args.indexOf("--version") + 1);
        if (!version.equals("1.8.9")) {
            throw new IllegalStateException("Unknown version was launched");
        }

        return "net.minecraft.client.main.Main";
    }

    @Override
    public String[] getLaunchArguments() {
        return args.toArray(new String[0]);
    }
}
