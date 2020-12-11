package org.lushen.mrh.boot.dfs.support.image;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.Properties;

import javax.imageio.ImageIO;

import org.lushen.mrh.boot.dfs.support.ImageCaptchaProvider;

import com.google.code.kaptcha.GimpyEngine;
import com.google.code.kaptcha.NoiseProducer;
import com.google.code.kaptcha.Producer;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import com.google.code.kaptcha.util.Configurable;
import com.jhlabs.image.RippleFilter;
import com.jhlabs.image.TransformFilter;

/**
 * kaptcha 验证码生成实现
 * 
 * @author hlm
 */
public class IKaptchaProvider extends Configurable implements ImageCaptchaProvider, GimpyEngine {

	private final Producer producer;

	public IKaptchaProvider() {
		super();
		Properties properties = new Properties();
		properties.setProperty("kaptcha.image.width", "100");
		properties.setProperty("kaptcha.image.height", "30");
		properties.setProperty("kaptcha.border", "no");
		properties.setProperty("kaptcha.obscurificator.impl", IKaptchaProvider.class.getName());
		properties.setProperty("kaptcha.textproducer.font.color", "black");
		properties.setProperty("kaptcha.background.clear.from", "white");
		properties.setProperty("kaptcha.background.clear.to", "white");
		properties.setProperty("kaptcha.noise.color", "black");
		properties.setProperty("kaptcha.textproducer.font.size", "25");
		properties.setProperty("kaptcha.textproducer.char.space", "7");
		properties.setProperty("kaptcha.textproducer.char.length", "4");
		DefaultKaptcha kaptcha = new DefaultKaptcha();
		kaptcha.setConfig(new Config(properties));
		this.producer = kaptcha;
	}

	@Override
	public ICaptcha create() {
		try {
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			String capText = producer.createText();
			BufferedImage bufferedImage = producer.createImage(capText);
			ImageIO.write(bufferedImage, "jpg", out);
			return new ICaptcha(out.toByteArray(), capText);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	@Override
	public BufferedImage getDistortedImage(BufferedImage image) {

		NoiseProducer noiseProducer = getConfig().getNoiseImpl();
		BufferedImage distortedImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);

		Graphics2D graph = (Graphics2D) distortedImage.getGraphics();

		RippleFilter rippleFilter = new RippleFilter();
		rippleFilter.setWaveType(RippleFilter.SINE);
		rippleFilter.setEdgeAction(TransformFilter.BILINEAR);

		BufferedImage effectImage = rippleFilter.filter(image, null);

		graph.drawImage(effectImage, 0, 0, null, null);
		graph.dispose();

		noiseProducer.makeNoise(distortedImage, .1f, .1f, .25f, .25f);
		noiseProducer.makeNoise(distortedImage, .1f, .25f, .5f, .9f);

		return distortedImage;
	}

}
