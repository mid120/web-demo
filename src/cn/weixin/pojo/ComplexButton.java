package cn.weixin.pojo;


/**   
* 
* @Description: ���Ӱ�ť������ť��
* @author wuy   
* @date 2014-8-20
* @version V1.0   
*/
public class ComplexButton extends Button {
	private Button[] sub_button;

	public Button[] getSub_button() {
		return sub_button;
	}

	public void setSub_button(Button[] sub_button) {
		this.sub_button = sub_button;
	}
}