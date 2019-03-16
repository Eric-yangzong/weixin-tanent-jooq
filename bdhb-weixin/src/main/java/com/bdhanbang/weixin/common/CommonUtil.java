package com.bdhanbang.weixin.common;

import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.bdhanbang.weixin.entity.PayInfo;
import com.bdhanbang.weixin.util.MD5Utils;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.core.util.QuickWriter;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
import com.thoughtworks.xstream.io.xml.XppDriver;

public class CommonUtil {

	public static String getRandomOrderId() {
		// UUID.randomUUID().toString().replace("-","")
		Random random = new Random(System.currentTimeMillis());
		int value = random.nextInt();
		while (value < 0) {
			value = random.nextInt();
		}
		return value + "";
	}

	private static XStream xstream = new XStream(new XppDriver() {
		public HierarchicalStreamWriter createWriter(Writer out) {
			return new PrettyPrintWriter(out) {
				// 增加CDATA标记
				boolean cdata = true;

				@SuppressWarnings("rawtypes")
				public void startNode(String name, Class clazz) {
					super.startNode(name, clazz);
				}

				protected void writeText(QuickWriter writer, String text) {
					if (cdata) {
						writer.write("<![CDATA[");
						writer.write(text);
						writer.write("]]>");
					} else {
						writer.write(text);
					}
				}
			};
		}
	});

	public static String payInfoToXML(PayInfo pi) {
		xstream.alias("xml", pi.getClass());
		return xstream.toXML(pi);
	}

	@SuppressWarnings("unchecked")
	public static Map<String, String> parseXml(String xml) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		Document document = DocumentHelper.parseText(xml);
		Element root = document.getRootElement();
		List<Element> elementList = root.elements();
		for (Element e : elementList)
			map.put(e.getName(), e.getText());
		return map;
	}

	public static String getMD5(String trim) {

		return MD5Utils.getMD5(trim);
	}
}