package cn.weixin.service;

import java.io.IOException;
import java.io.InputStream;

import com.google.gson.Gson;

import cn.weixin.pojo.JokeVo;

public class JokeService extends BaseService {


	static JokeVo getContent(InputStream inputStream) {
		byte[] tempbytes = new byte[1000];
		int byteread = 0;
		StringBuffer buffer = new StringBuffer();
		try {
			while ((byteread = inputStream.read(tempbytes)) != -1) {
				buffer.append(new String(tempbytes, 0, byteread));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		//开始解析
		 Gson mgson = new Gson();  
         JokeVo vo = mgson.fromJson(  
        		 buffer.toString(), JokeVo.class);
         return vo;
	}

	public static void main(String[] args) {
		String url = "http://japi.juhe.cn/joke/content/list.from?key=3050135a635f27d901bce7d764c6396c&page=2&pagesize=5&sort=asc&time=1308745232";
		InputStream inputStream = httpRequest(url);
		byte[] tempbytes = new byte[1000];
		int byteread = 0;
		StringBuffer buffer = new StringBuffer();
		try {
			while ((byteread = inputStream.read(tempbytes)) != -1) {
				buffer.append(new String(tempbytes, 0, byteread));
			}
		} catch (IOException e) {

			e.printStackTrace();
		}
		System.out.println(buffer);
		
		//开始解析
		 Gson mgson = new Gson();  
         JokeVo vo = mgson.fromJson(  
        		 buffer.toString(), JokeVo.class); 
         System.out.println(vo);
		
	}

}
