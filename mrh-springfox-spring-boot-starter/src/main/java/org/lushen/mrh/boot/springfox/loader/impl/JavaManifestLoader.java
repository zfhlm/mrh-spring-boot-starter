package org.lushen.mrh.boot.springfox.loader.impl;

import java.io.File;
import java.io.FileInputStream;
import java.net.URI;
import java.security.CodeSource;
import java.util.jar.JarFile;
import java.util.jar.Manifest;

import org.apache.commons.lang3.StringUtils;
import org.lushen.mrh.boot.springfox.loader.RuntimeLoader;

/**
 * 启动应用 META-INF/MANIFEST.MF 信息加载器
 * 
 * @author hlm
 */
public class JavaManifestLoader implements RuntimeLoader<Manifest> {

	private static final String META_INF = "META-INF";

	private static final String MANIFEST_MF = "MANIFEST.MF";

	private static final String CLASSES = "classes";

	private static final String TEST_CLASSES = "test-classes";

	@Override
	public Manifest load() throws Exception {

		// 获取启动类
		Class<?> commandClass = new JavaCommandLoader().load();

		// 获取项目根目录
		CodeSource codeSource = commandClass.getProtectionDomain().getCodeSource();
		URI location = (codeSource != null) ? codeSource.getLocation().toURI() : null;
		File root = (location != null) ? new File(location.getSchemeSpecificPart()) : null;

		// maven test-classes
		if(StringUtils.equals(TEST_CLASSES, root.getName())) {
			root = new File(root.getParent(), CLASSES);
		}

		// 获取MANIFEST.MF
		if(root.exists()) {
			if(root.isDirectory()) {
				File manifestFile = new File(new File(root, META_INF), MANIFEST_MF);
				if (manifestFile.exists()) {
					try (FileInputStream inputStream = new FileInputStream(manifestFile)) {
						return new Manifest(inputStream);
					}
				}
			} else {
				try (JarFile jarFile = new JarFile(root)) {
					return jarFile.getManifest();
				}
			}
		}

		throw new IllegalStateException("Unable to determine code source archive from " + location);
	}

}
