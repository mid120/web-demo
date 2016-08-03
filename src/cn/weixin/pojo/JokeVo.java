package cn.weixin.pojo;

import java.util.List;

public class JokeVo {
	private String error_code;
	private String reason;
	private Joketemp result;
	public String getError_code() {
		return error_code;
	}
	public void setError_code(String error_code) {
		this.error_code = error_code;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}

	
	public Joketemp getResult() {
		return result;
	}
	public void setResult(Joketemp result) {
		this.result = result;
	}


	public static class Joketemp{
		public List<Joke> data;

		public List<Joke> getData() {
			return data;
		}

		public void setData(List<Joke> data) {
			this.data = data;
		}
	}
	
	public static class Joke{
		private String content; 
		private String hashId; 
		private String unixtime; 
		private String updatetime;
		public String getContent() {
			return content;
		}
		public void setContent(String content) {
			this.content = content;
		}
		public String getHashId() {
			return hashId;
		}
		public void setHashId(String hashId) {
			this.hashId = hashId;
		}
		public String getUnixtime() {
			return unixtime;
		}
		public void setUnixtime(String unixtime) {
			this.unixtime = unixtime;
		}
		public String getUpdatetime() {
			return updatetime;
		}
		public void setUpdatetime(String updatetime) {
			this.updatetime = updatetime;
		}
		
		
	}

}
