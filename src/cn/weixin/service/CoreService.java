package cn.weixin.service;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import cn.weixin.msg.resp.Article;
import cn.weixin.msg.resp.Location;
import cn.weixin.msg.resp.Music;
import cn.weixin.msg.resp.MusicMessage;
import cn.weixin.msg.resp.NewsMessage;
import cn.weixin.msg.resp.TextMessage;
import cn.weixin.pojo.JokeVo;
import cn.weixin.pojo.JokeVo.Joke;
import cn.weixin.pojo.WeatherVo;
import cn.weixin.util.MessageUtil;




/**   
* 
* @Description: 核心服务类
* @author wuy   
* @date 2014-8-20
* @version V1.0   
*/
public class CoreService {
	/**
	 * 处理微信发来的请求
	 * 
	 * @param request
	 * @return
	 */
	public static String processRequest(HttpServletRequest request) {
		String respMessage = null;
		try {
			// 默认返回的文本消息内容
			String respContent = "请求处理异常，请稍候尝试！";

			// xml请求解析
			Map<String, String> requestMap = MessageUtil.parseXml(request);

			// 发送方帐号（open_id）
			String fromUserName = requestMap.get("FromUserName");
			// 公众帐号
			String toUserName = requestMap.get("ToUserName");
			// 消息类型
			String msgType = requestMap.get("MsgType");
			System.out.println("msgType:" + msgType);

			// 回复文本消息
			TextMessage textMessage = new TextMessage();
			textMessage.setToUserName(fromUserName);
			textMessage.setFromUserName(toUserName);
			textMessage.setCreateTime(new Date().getTime());
			textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
			textMessage.setFuncFlag(0);

			// 文本消息
			if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_TEXT)) {
				respContent = requestMap.get("Content");
				
				//测试回复    单条图文消息 或者多条图文消息 测试回复 输入1
				if ("1".equals(respContent)){
					NewsMessage message = new NewsMessage();
					message.setToUserName(fromUserName);
					message.setFromUserName(toUserName);
					message.setCreateTime(new Date().getTime());
					message.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);
					message.setArticleCount(2);
					message.setFuncFlag(0);
					ArrayList<Article> list = new ArrayList<>();
					Article article = new Article();
					article.setUrl("www.baidu.com");
					article.setDescription("你发送的地理位置为：静安区上海信息服务外包产业园龙软万荣基地");
					article.setPicUrl("http://img1.imgtn.bdimg.com/it/u=286277763,507246238&fm=21&gp=0.jpg");
					article.setTitle("测试发送");
					Article article1 = new Article();
					article1.setUrl("www.baidu.com222");
					article1.setDescription("你发送的地理位置为：静安区上海信息服务外包产业园龙软万荣基地22");
					article1.setPicUrl("http://img1.imgtn.bdimg.com/it/u=286277763,507246238&fm=21&gp=0.jpg");
					article1.setTitle("测试发送111");
					list.add(article);
					list.add(article1);
					message.setArticles(list);
					respMessage = MessageUtil.newsMessageToXml(message);
					return respMessage;
				}
				
				//输入2 返回音乐
				if("2".equals(respContent)){
					MusicMessage musicMessage = new MusicMessage();
					musicMessage.setToUserName(fromUserName);
					musicMessage.setFromUserName(toUserName);
					musicMessage.setCreateTime(new Date().getTime());
					musicMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_MUSIC);
					Music music = new Music();
					music.setDescription("最炫名族风");
					music.setTitle("最炫名族风");
					music.setHQMusicUrl("http://i.y.qq.com/v8/playsong.html?hostuin=0&sharefrom=&from_id=0&from_idtype=0&from_name=&songid=5525627&songmid=&type=(9rpl)&platform=(10rpl)&appsongtype=(11rpl)&_wv=1&source=qq&appshare=iphone&media_mid=001UKsxM3IAqcR&disstid=0&from=timeline&isappinstalled=1");
					music.setMusicUrl("http://i.y.qq.com/v8/playsong.html?hostuin=0&sharefrom=&from_id=0&from_idtype=0&from_name=&songid=5525627&songmid=&type=(9rpl)&platform=(10rpl)&appsongtype=(11rpl)&_wv=1&source=qq&appshare=iphone&media_mid=001UKsxM3IAqcR&disstid=0&from=timeline&isappinstalled=1");
					musicMessage.setMusic(music);
					respMessage = MessageUtil.musicMessageToXml(musicMessage);
					return respMessage;
				}
				if(respContent.startsWith("天气")){
					String local = respContent.substring(2).trim();
					String url="http://api.map.baidu.com/telematics/v3/weather?location={LOCATION}&ak=yFobrGyMiW0CBn6Sa4GcN4s4CBlKOucB".replace("{LOCATION}", local);
					InputStream inputStream = WeatherService.httpRequest(url);
					ArrayList<WeatherVo> weatherList = WeatherService.parseWeather(inputStream);
					// 封装基本信息
					NewsMessage message = new NewsMessage();
					message.setToUserName(fromUserName);
					message.setFromUserName(toUserName);
					message.setCreateTime(new Date().getTime());
					message.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);
					message.setArticleCount(4);
					message.setFuncFlag(0);
					ArrayList<Article> list = new ArrayList<>();
					// 封装article 消息

					for(int  i=0;i<4;i++){
						WeatherVo weatherVo = weatherList.get(i);
						Article article = new Article();
						article.setUrl("www.baidu.com");
						article.setDescription("");
						article.setPicUrl(weatherVo.getDayPictureUrl());
						article.setTitle(weatherVo.getDate()+"\r\n"+weatherVo.getWeather()+" "+weatherVo.getWind()+" "+weatherVo.getTemperature()+" ");
						list.add(article);
					}
					
					
					message.setArticles(list);
					respMessage = MessageUtil.newsMessageToXml(message);
					return respMessage;
				}
				if(respContent.startsWith("笑话")){
					String url = "http://japi.juhe.cn/joke/content/list.from?key=3050135a635f27d901bce7d764c6396c&page=1&pagesize=20&sort=asc&time={time}";
					InputStream inputStream =JokeService.httpRequest(url.replace("{time}","1308745232"));
					JokeVo jokeVo = JokeService.getContent(inputStream);
					Random random = new Random();
					int i = random.nextInt(20);
					Joke joke = jokeVo.getResult().getData().get(i);
					String content = joke.getContent();
					textMessage.setContent(content);
				    respMessage = MessageUtil.textMessageToXml(textMessage);
				    return respMessage;
				}
				
			
//				Map infoMap = (Map)request.getSession().getAttribute("bussinessMap");
//				if(infoMap!=null) {
//					String info = infoMap.get(respContent)+"";
//					String[] unit = info.split(" ");//34560 【已读】(填写表单)请假单
//					String returnInfo = BussinessExcute.executeTask(unit[0]);
//					if(respContent.length()>2) {
//						String returnInfo = BussinessExcute.executeTask(respContent.substring(2));
//						System.out.println("respContent.substring(2):" + respContent.substring(2));
//						textMessage.setContent(returnInfo);
				       //如果都没有匹配上，最后的逻辑，输入什么返回什么
						textMessage.setContent(respContent);
						respMessage = MessageUtil.textMessageToXml(textMessage);
//					}
//				}
			}
			// 图片消息
			else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_IMAGE)) {
				respContent = "您发送的是图片消息！";
			}
			// 地理位置消息
			else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LOCATION)) {
				respContent="你发送的地理位置为："+requestMap.get("Label");
			}
			// 链接消息
			else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LINK)) {
				respContent = "您发送的是链接消息！";
			}
			// 音频消息
			else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_VOICE)) {
				respContent = "您发送的是音频消息！";
			}
			// 事件推送
			else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_EVENT)) {
				// 事件类型
				String eventType = requestMap.get("Event");
				// 订阅
				if (eventType.equals(MessageUtil.EVENT_TYPE_SUBSCRIBE)) {
					respContent = "谢谢您关注申哥个人公众号！";
				}
				// 取消订阅
				else if (eventType.equals(MessageUtil.EVENT_TYPE_UNSUBSCRIBE)) {
					// TODO 取消订阅后用户再收不到公众号发送的消息，因此不需要回复消息
				}
				// 自定义菜单点击事件
				else if (eventType.equals(MessageUtil.EVENT_TYPE_CLICK)) {
					// 事件KEY值，与创建自定义菜单时指定的KEY值对应
					String eventKey = requestMap.get("EventKey");
					
					// 创建图文消息
					NewsMessage newsMessage = new NewsMessage();
					newsMessage.setToUserName(fromUserName);
					newsMessage.setFromUserName(toUserName);
					newsMessage.setCreateTime(new Date().getTime());
					newsMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);
					newsMessage.setFuncFlag(0);
					List<Article> articleList = new ArrayList<Article>();
					
					if (eventKey.equals("11")) {//公司介绍
						respContent = "公司介绍菜单项被点击！";
						textMessage.setContent(respContent);
						respMessage = MessageUtil.textMessageToXml(textMessage);
					} else if (eventKey.equals("12")) {//新闻速递						
						Article article = new Article();
						article.setTitle("第一届中国业务流程管理软件大会完美落幕");
						article.setDescription("由中国软件行业协会企业管理软件分会指导，中国首家BPM软件和服务提供商炎黄盈动主办，AMT(上海企源科技股份有限公司)协办的第一届中国业务流程管理软件大会于5月23日在北京唯实国际会议中心隆重召开。");
						article.setPicUrl(request.getRequestURL().substring(0, request.getRequestURL().length()-11) + "images/xwsd.jpg");
						System.out.println("--------图片路径：" + request.getRequestURL().substring(0, request.getRequestURL().length()-11) + "images/xwsd.jpg");
						article.setUrl("http://www.actionsoft.com.cn/news/news.jsp?id=7c796e0d3f96dbd66ee5da35bcf2149a&flag=gsxw");
						articleList.add(article);
						// 设置图文消息个数
						newsMessage.setArticleCount(articleList.size());
						// 设置图文消息包含的图文集合
						newsMessage.setArticles(articleList);
						// 将图文消息对象转换成xml字符串
						respMessage = MessageUtil.newsMessageToXml(newsMessage);
					} else if (eventKey.equals("13")) {//产品推荐
						respContent = "产品推荐菜单项被点击！";
						textMessage.setContent(respContent);
						respMessage = MessageUtil.textMessageToXml(textMessage);
					} else if (eventKey.equals("21")) {//产品试用
						respContent = "产品试用菜单项被点击！";
						textMessage.setContent(respContent);
						respMessage = MessageUtil.textMessageToXml(textMessage);
					} else if (eventKey.equals("22")) {//近期活动
						respContent = "近期活动菜单项被点击！";
						textMessage.setContent(respContent);
						respMessage = MessageUtil.textMessageToXml(textMessage);
					}  else if (eventKey.equals("23")) {//联系我们
						respContent = "联系我们菜单项被点击！";
						textMessage.setContent(respContent);
						respMessage = MessageUtil.textMessageToXml(textMessage);
					} else if (eventKey.equals("31")) {//我的代办
						Article article = new Article();
						article.setTitle("我的代办事件列表");
						article.setDescription("用户可以通过我的代办事件列表可以查看当前的代办事件！");
						article.setPicUrl(request.getRequestURL().substring(0, request.getRequestURL().length()-11) + "images/test.jpg");
						System.out.println("--------图片路径：" + request.getRequestURL().substring(0, request.getRequestURL().length()-11) + "images/test.jpg");
						request.setAttribute("test", "1234567890");
						article.setUrl("http://27.115.18.254/weixin/bussinessServlet");
						articleList.add(article);
						// 设置图文消息个数
						newsMessage.setArticleCount(articleList.size());
						// 设置图文消息包含的图文集合
						newsMessage.setArticles(articleList);
						// 将图文消息对象转换成xml字符串
						respMessage = MessageUtil.newsMessageToXml(newsMessage);
					} else if (eventKey.equals("32")) {//帐号绑定
						respContent = "帐号绑定菜单项被点击！";
						textMessage.setContent(respContent);
						respMessage = MessageUtil.textMessageToXml(textMessage);
					}
				}
			}
			textMessage.setContent(respContent);
			respMessage = MessageUtil.textMessageToXml(textMessage);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return respMessage;
	}
}
