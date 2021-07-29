package org.bookmc.loader.impl.discoverer;

import net.minecraft.launchwrapper.hacks.LaunchWrapperHacks;
import org.bookmc.loader.api.MinecraftModDiscoverer;
import org.bookmc.loader.impl.Loader;
import org.bookmc.loader.impl.candidate.DirectoryModCandidate;
import org.bookmc.loader.impl.candidate.ZipModCandidate;
import org.bookmc.loader.shared.Constants;
import org.bookmc.loader.shared.utils.ZipUtils;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;

public class ClasspathModDiscoverer implements MinecraftModDiscoverer {
    @Override
    public void discover(File[] files) {
        URL[] classpath = LaunchWrapperHacks.getClasspathURLs();

        for (URL url : classpath) {
            try {
                File file = new File(url.toURI());
                String name = file.getName();

                if (!name.endsWith(Constants.DISABLED_SUFFIX)) {
                    if (ZipUtils.isZipFile(file)) {
                        Loader.registerCandidate(new ZipModCandidate(new File(url.toURI())));
                    } else if (file.isDirectory()) {
                        Loader.registerCandidate(new DirectoryModCandidate(file));
                    }
                }
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean isFilesRequired() {
        return false;
    }
}
