package org.lushen.mrh.boot.autoconfigure.support.runtime;

import java.util.jar.Attributes;

/**
 * SpringBoot META-INF/MANIFEST.MF 信息加载器
 * 
 * @author hlm
 */
public class BootManifestLoader implements RuntimeLoader<BootManifest> {

	@Override
	public BootManifest load() throws Exception {
		Attributes attributes = new JavaManifestLoader().load().getMainAttributes();
		return new BootManifest(attributes);
	}

}
