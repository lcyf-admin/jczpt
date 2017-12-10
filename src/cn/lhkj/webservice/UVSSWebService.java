package cn.lhkj.webservice;

public interface UVSSWebService {
	
	/**
	 * 车底扫描接口
	 * @param UVSSImage	Base64编码的车底图像文件
	 * @param PlateImage Base64编码的车牌图像文件
	 * @param PlateNumber 车牌号码
	 * @param CheckDateTime 检查日期时间的格式为yyyy/m/d h:mm:ss，如：2014/1/2 3:04:05、2014/10/20 13:14:15。
	 */
	public void SendCheckInfo(String UVSSImage, String PlateImage, String PlateNumber, String CheckDateTime);
	
	/**
	 * 车底扫描接口
	 * @param UVSSImage	车底图像文件路径
	 * @param PlateImage 车牌图像文件路径
	 * @param PlateNumber 车牌号码
	 * @param CheckDateTime 检查日期时间的格式为yyyy/m/d h:mm:ss，如：2014/1/2 3:04:05、2014/10/20 13:14:15。
	 */
	public void SendCheckPathInfo(String UVSSImagePath, String PlateImagePath, String PlateNumber, String CheckDateTime);
}
