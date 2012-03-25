package org.github.bbaron.springcfg.example.batch;

import java.nio.file.*;

import static java.nio.file.Files.*;

import org.springframework.batch.core.launch.support.CommandLineJobRunner;

public class BatchJobRunner {

	public static void main(String[] ignored) throws Exception {
		Path propertiesPath = Paths.get("src", "main", "resources", "batch.properties");
		Path appcfgDir = Paths.get("target", "appcfg");
		if (Files.notExists(appcfgDir)) {
			appcfgDir = createDirectory(appcfgDir);
			Files.copy(propertiesPath, appcfgDir.resolve(propertiesPath.getFileName()));
		}
		System.out.println("cfg dir = " + appcfgDir);
		System.setProperty("spring.profiles.active", "deployed");
		System.setProperty("app.cfg", appcfgDir.toString());
		String[] args = { "classpath:/launch-context.xml", "job1" };
		CommandLineJobRunner.main(args);
	}
}
