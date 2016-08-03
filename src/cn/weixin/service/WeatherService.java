package cn.weixin.service;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import cn.weixin.pojo.WeatherVo;

public class WeatherService extends BaseService {
	
    /**
     * 解析请求API返回的xml 数据
     * 
     */

    @SuppressWarnings("unchecked")  
     static ArrayList<WeatherVo> parseWeather(InputStream inputStream) {  
        ArrayList<WeatherVo> list = new ArrayList();
        try {  
            // 使用dom4j解析xml字符串  
            SAXReader reader = new SAXReader();  
            Document document = reader.read(inputStream);  
            
     
            // 得到xml根元素  
            Element root = document.getRootElement();  
            // count表示搜索到的歌曲数  
            Element results = root.element("results"); 
            Element weather_data = results.element("weather_data"); 
            
//            for(Iterator it=weather_data.elementIterator();it.hasNext();){       
//                Element element = (Element) it.next(); 
//                System.out.println(element.getText());
//               // do something   
//         }  
          List<Element> nodes1 = weather_data.elements("date"); 
          List<Element>  nodes2 = weather_data.elements("dayPictureUrl"); 
          List<Element>  nodes3 = weather_data.elements("nightPictureUrl"); 
          List<Element>  nodes4 = weather_data.elements("weather"); 
          List<Element>  nodes5 = weather_data.elements("wind"); 
          List<Element>  nodes6 = weather_data.elements("temperature"); 
          int i=0;
          for (Iterator it = nodes1.iterator(); it.hasNext();) {   
            String  date = ((Element) it.next()).getText();
            String  dayPictureUrl = ((Element) nodes2.get(i)).getText();
            String  nightPictureUrl = ((Element) nodes3.get(i)).getText();
            String  weather = ((Element) nodes4.get(i)).getText();
            String  wind = ((Element) nodes5.get(i)).getText();
            String  temperature = ((Element) nodes6.get(i)).getText();
            WeatherVo vo = new WeatherVo();
            vo.setDate(date);
            vo.setDayPictureUrl(dayPictureUrl);
            vo.setNightPictureUrl(nightPictureUrl);
            vo.setTemperature(temperature);
            vo.setWeather(weather);
            vo.setWind(wind);
            list.add(vo);
            i++;
         }
     
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        return list;  
    } 
    
//    public static void main(String[] args) {
//		String url="http://api.map.baidu.com/telematics/v3/weather?location=北京&ak=yFobrGyMiW0CBn6Sa4GcN4s4CBlKOucB";
//		InputStream inputStream = httpRequest(url);
//        byte[] tempbytes = new byte[1000];
//        int byteread = 0;
//        try {
//			while ((byteread = inputStream.read(tempbytes)) != -1) {
//			    System.out.print( new String(tempbytes, 0, byteread));
//			}
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
//             parseWeather(inputStream);
//	}
    
}
