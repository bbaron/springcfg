package cfg;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.Properties;

import org.junit.rules.TemporaryFolder;

public class TemporaryConfigFolder extends TemporaryFolder {

	private final String appName;
	private File appCfg;

	public TemporaryConfigFolder(String appName) {
		this.appName = appName;
	}

	public String getAppName() {
		return appName;
	}

	public String getAppBase() {
		return appCfg.getParentFile().getAbsolutePath();
	}

	public String getAppCfg() {
		return appCfg.getAbsolutePath();
	}

	public void saveProperties(String prop) throws Exception {
		saveProperties(prop, prop);
	}

	public void saveProperty1(String prop1) throws Exception {
		Properties props = new Properties();
		props.setProperty("prop1", prop1);
		saveProperties(props);
	}

	public void saveProperty2(String prop2) throws Exception {
		Properties props = new Properties();
		props.setProperty("prop2", prop2);
		saveProperties(props);
	}

	public void saveProperties(String prop1, String prop2) throws Exception {
		Properties props = new Properties();
		props.setProperty("prop1", prop1);
		props.setProperty("prop2", prop2);
		saveProperties(props);
	}

	public void saveProperties(Properties props) throws Exception {
		File file = File.createTempFile("test", ".properties", appCfg);
		Writer writer = new FileWriter(file);
		props.store(writer, "saving test props");
	}

	@Override
	protected void before() throws Throwable {
		super.before();
		appCfg = newFolder(appName);
		System.out.println("appCfg = " + appCfg.getAbsolutePath());
	}

}
