package information.service;

import information.util.HttpUtil;
import information.util.YunSu;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import javax.imageio.ImageIO;

import org.apache.http.HttpEntity;

import com.alibaba.fastjson.JSONObject;

public class Information {

	/**
	 * 识别验证码
	 * @throws Exception 
	 */
	public static String recognitionImg() throws Exception{
		HttpUtil httpUtil = new HttpUtil();
		HttpEntity httpEntityRand = httpUtil.sendGet("http://zxx.haedu.cn/jsp/public/imagegen.jsp?time="+System.currentTimeMillis(), null);
		InputStream inputStream = httpEntityRand.getContent();
		BufferedImage bufferedImage = ImageIO.read(inputStream);
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		//保存验证码到本地
//		ImageIO.write(bufferedImage, "JPG", new File("D://test//pic//"+System.currentTimeMillis()+".jpg"));
		ImageIO.write(bufferedImage, "JPG", byteArrayOutputStream);
		byte[] bytes = byteArrayOutputStream.toByteArray();
		String res =  YunSu.createByPost("xxxx", "xxxx", "3040", "60", "30804", "89a9752c89964b26b51177f6bb025ac0", bytes);
		System.out.println(res);
		JSONObject jsonObject =  JSONObject.parseObject(res);
		res = jsonObject.get("Result").toString();
		if (res.length()!=4) {
			throw new Exception("验证码识别失败");
		}
		return res;
	}
	
	public void doLogin(){
		
	}
	
	
	
	public static void main(String[] args) throws Exception {
		Information.recognitionImg();
	}

}
