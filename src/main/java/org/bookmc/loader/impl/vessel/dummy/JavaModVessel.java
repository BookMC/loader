package org.bookmc.loader.impl.vessel.dummy;

import org.bookmc.loader.api.vessel.ModVessel;
import org.bookmc.loader.api.vessel.dependency.ModDependency;

import java.io.File;
import java.net.URL;

public class JavaModVessel implements ModVessel {
    @Override
    public String getName() {
        return "Java";
    }

    @Override
    public String getId() {
        return "java";
    }

    @Override
    public String getDescription() {
        return "Java is a set of computer software " +
            "and specifications developed by James Gosling at Sun Microsystems, " +
            "which was later acquired by the Oracle Corporation, " +
            "that provides a system for developing application software " +
            "and deploying it in a cross-platform computing environment.";
    }

    @Override
    public String getConfig() {
        return null;
    }

    @Override
    public String[] getAuthors() {
        return new String[]{"Oracle Corporation", "Sun Microsystems", "James Gosling"};
    }

    @Override
    public String getVersion() {
        return System.getProperty("java.version");
    }

    @Override
    public String getIcon() {
        return null;
    }

    @Override
    public String getEntrypoint() {
        return null;
    }

    @Override
    public File getFile() {
        return null;
    }

    @Override
    public String getMixinEntrypoint() {
        return null;
    }

    @Override
    public ModDependency[] getDependsOn() {
        return new ModDependency[0];
    }

    @Override
    public ModDependency[] getSuggestions() {
        return new ModDependency[0];
    }

    @Override
    public URL[] getExternalDependencies() {
        return new URL[0];
    }

    @Override
    public boolean isCompatibilityLayer() {
        return false;
    }
}
