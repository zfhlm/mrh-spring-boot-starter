package org.lushen.mrh.boot.springfox.loader.impl;

import java.util.jar.Attributes;

public class BootManifest {

	private static final String MANIFEST_VERSION = "Manifest-Version";

	private static final String IMPLEMENTATION_TITLE = "Implementation-Title";

	private static final String IMPLEMENTATION_VERSION = "Implementation-Version";

	private static final String ARCHIVER_VERSION = "Archiver-Version";

	private static final String BUILT_BY = "Built-By";

	private static final String IMPLEMENTATION_VENDOR_ID = "Implementation-Vendor-Id";

	private static final String SPRING_BOOT_VERSION = "Spring-Boot-Version";

	private static final String MAIN_CLASS = "Main-Class";

	private static final String START_CLASS = "Start-Class";

	private static final String SPRING_BOOT_CLASSES = "Spring-Boot-Classes";

	private static final String SPRING_BOOT_LIB = "Spring-Boot-Lib";

	private static final String CREATED_BY = "Created-By";

	private static final String BUILD_JDK = "Build-Jdk";

	private static final String IMPLEMENTATION_URL = "Implementation-URL";

	private Attributes attributes;

	BootManifest(Attributes attributes) {
		super();
		this.attributes = attributes;
	}

	public String getManifestVersion() {
		return this.attributes.getValue(MANIFEST_VERSION);
	}

	public String getImplementationTitle() {
		return this.attributes.getValue(IMPLEMENTATION_TITLE);
	}

	public String getImplementationVersion() {
		return this.attributes.getValue(IMPLEMENTATION_VERSION);
	}

	public String getArchiverVersion() {
		return this.attributes.getValue(ARCHIVER_VERSION);
	}

	public String getBuiltBy() {
		return this.attributes.getValue(BUILT_BY);
	}

	public String getImplementationVendorId() {
		return this.attributes.getValue(IMPLEMENTATION_VENDOR_ID);
	}

	public String getSpringBootVersion() {
		return this.attributes.getValue(SPRING_BOOT_VERSION);
	}

	public String getMainClass() {
		return this.attributes.getValue(MAIN_CLASS);
	}

	public String getStartClass() {
		return this.attributes.getValue(START_CLASS);
	}

	public String getSpringBootClasses() {
		return this.attributes.getValue(SPRING_BOOT_CLASSES);
	}

	public String getSpringBootLib() {
		return this.attributes.getValue(SPRING_BOOT_LIB);
	}

	public String getCreatedBy() {
		return this.attributes.getValue(CREATED_BY);
	}

	public String getBuildJdk() {
		return this.attributes.getValue(BUILD_JDK);
	}

	public String getImplementationURL() {
		return this.attributes.getValue(IMPLEMENTATION_URL);
	}

	public String getAttribute(String name) {
		return this.attributes.getValue(name);
	}

}
