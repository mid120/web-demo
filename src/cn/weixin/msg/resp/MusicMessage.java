package cn.weixin.msg.resp;



/**   
* 
* @Description: 音乐消息
* @author wuy   
* @date 2014-8-20
* @version V1.0   
*/
public class MusicMessage extends BaseMessage {
	// 音乐
	private Music Music;

	public Music getMusic() {
		return Music;
	}

	public void setMusic(Music music) {
		Music = music;
	}
}