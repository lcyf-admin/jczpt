package cn.lhkj.commons.base;

import java.text.SimpleDateFormat;
import java.util.Date;

import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;

public class JsonProcessor implements JsonValueProcessor{

	private String format = "yyyy-MM-dd";
	public JsonProcessor() {
	}
	public JsonProcessor(String format) {
		this.format = format;
	}
	@Override
	public Object processArrayValue(Object value, JsonConfig arg1) {
		String[] obj = {};
		if(value!=null){
			if (value instanceof Date[]) {
				SimpleDateFormat sf = new SimpleDateFormat(format);
				Date[] dates = (Date[]) value;
				obj = new String[dates.length];
				for (int i = 0; i < dates.length; i++) {
					obj[i] = sf.format(dates[i]);
				}
			}
		}
		return obj;
	}

	@Override
	public Object processObjectValue(String arg0, Object value, JsonConfig arg2) {
			if(value!=null){
				if (value instanceof Date) {
					String str = new SimpleDateFormat(format).format((Date) value);
					return str;
				}
			}
			return value;
	}
	
	public String getFormat() {
		return format;
	}
	
	public void setFormat(String format) {
		this.format = format;
	}

}
