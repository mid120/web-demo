package cn.weixin.msg.resp;


/**   
* 
* @Description: 文本消息
* @author wuy   
* @date 2014-8-20
* @version V1.0   
*/
public class TextMessage extends BaseMessage {
	// 回复的消息内容
	private String Content;

	public String getContent() {
		return Content;
	}

	public void setContent(String content) {
		Content = content;
	}
}