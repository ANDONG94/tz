package com.tdkj.util;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import org.apache.http.entity.ContentType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;




public class Base64Utils {

	public static void main(String[] args) throws Exception {

		//本地图片地址
		String url = "C:/Users/Administrator/Desktop/628947887489084892.jpg";
		//在线图片地址
		String string = "http://bpic.588ku.com//element_origin_min_pic/17/03/03/7bf4480888f35addcf2ce942701c728a.jpg";

		String str = Base64Utils.ImageToBase64ByLocal(url);

		String ste = Base64Utils.ImageToBase64ByOnline(string);

		System.out.println(str);

	}


	/**
	 *
	 * @Title: ImageToBase64ByLocal
	 * @Description: 本地图片转换成base64字符串
	 * @author and
	 * @date   2019年3月15日
	 *  imgFile 图片本地路径
	 * @return
	 * @return String
	 * @throws
	 */
	public static String ImageToBase64ByLocal(String imgFile) {// 将图片文件转化为字节数组字符串，并对其进行Base64编码处理


		InputStream in = null;
		byte[] data = null;

		// 读取图片字节数组
		try {
			in = new FileInputStream(imgFile);

			data = new byte[in.available()];
			in.read(data);
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 对字节数组Base64编码
		BASE64Encoder encoder = new BASE64Encoder();

		return encoder.encode(data);// 返回Base64编码过的字节数组字符串
	}


	/**
	 *
	 * @Title: ImageToBase64ByLocal
	 * @Description: 在线图片转换成base64字符串
	 * @author and
	 * @date   2019年3月15日
	 *  imgFile 图片本地路径
	 * @return
	 * @return String
	 * @throws
	 */
	public static String ImageToBase64ByOnline(String imgURL) {
		ByteArrayOutputStream data = new ByteArrayOutputStream();
		try {
			// 创建URL
			URL url = new URL(imgURL);
			byte[] by = new byte[1024];
			// 创建链接
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setConnectTimeout(5000);
			InputStream is = conn.getInputStream();
			// 将内容读取内存中
			int len = -1;
			while ((len = is.read(by)) != -1) {
				data.write(by, 0, len);
			}
			// 关闭流
			is.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 对字节数组Base64编码
		BASE64Encoder encoder = new BASE64Encoder();
		return encoder.encode(data.toByteArray());
	}



	/**
	 * @throws IOException
	 *
	 * @Title: Base64ToImage
	 * @Description: base64字符串转换成图片
	 * @author and
	 * @date   2019年3月15日
	 * imgStr base64字符串
	 *  imgFilePath 图片存放路径
	 * @return
	 * @return boolean
	 * @throws
	 */
	public static MultipartFile base64ToFile(String base64) throws IOException {
		if(base64==null||"".equals(base64)) {
			return null;
		}
		byte[] buff=Base64.decode(base64);
		File file=null;
		FileOutputStream fout=null;
		try {
			file = File.createTempFile("tmp", null);
			fout=new FileOutputStream(file);
			fout.write(buff);
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			if(fout!=null) {
				try {
					fout.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		FileInputStream fileInputStream = new FileInputStream(file);
		MultipartFile multipartFile = new MockMultipartFile(file.getName(), file.getName(),
				ContentType.APPLICATION_OCTET_STREAM.toString(), fileInputStream);

		return multipartFile;
	}


	public static MultipartFile base64ToMultipart(String base64) {
		try {
			String[] baseStr = base64.split(",");

			BASE64Decoder decoder = new BASE64Decoder();
			byte[] b = new byte[0];
			b = decoder.decodeBuffer(baseStr[1]);

			for(int i = 0; i < b.length; ++i) {
				if (b[i] < 0) {
					b[i] += 256;
				}
			}

			return  new BASE64DecodedMultipartFile(b, baseStr[0]);
		} catch (IOException e) {
			return null;
		}
	}

}
