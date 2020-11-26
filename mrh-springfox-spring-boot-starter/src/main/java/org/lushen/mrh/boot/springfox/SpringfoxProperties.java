package org.lushen.mrh.boot.springfox;

/**
 * swagger 文档描述对象
 * 
 * @author helm
 */
public class SpringfoxProperties {

	// 版本号
	private String version;

	// 标题
	private String title;

	// 描述
	private String description;

	// 服务条款地址
	private String termsOfServiceUrl;

	// 许可证
	private String license;

	// 许可证地址
	private String licenseUrl;

	// 联系方式
	private Contant contact;

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getTermsOfServiceUrl() {
		return termsOfServiceUrl;
	}

	public void setTermsOfServiceUrl(String termsOfServiceUrl) {
		this.termsOfServiceUrl = termsOfServiceUrl;
	}

	public String getLicense() {
		return license;
	}

	public void setLicense(String license) {
		this.license = license;
	}

	public String getLicenseUrl() {
		return licenseUrl;
	}

	public void setLicenseUrl(String licenseUrl) {
		this.licenseUrl = licenseUrl;
	}

	public Contant getContact() {
		return contact;
	}

	public void setContact(Contant contact) {
		this.contact = contact;
	}

	/**
	 * swagger 联系信息配置对象
	 * 
	 * @author helm
	 */
	public static class Contant {

		// 联系人
		private String name;

		// 地址
		private String url;

		// 邮箱
		private String email;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getUrl() {
			return url;
		}

		public void setUrl(String url) {
			this.url = url;
		}

		public String getEmail() {
			return email;
		}

		public void setEmail(String email) {
			this.email = email;
		}

	}

}
