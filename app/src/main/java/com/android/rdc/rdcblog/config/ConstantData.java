package com.android.rdc.rdcblog.config;

/**
 * Time:2016/7/29 14:47
 * Created By:ThatNight
 */
public class ConstantData {

	public static final int PERMISSION_WRITE = 1;

	/**
	 * 登录
	 */
	public static final String urlLogin2="http://10.10.122.3:8080/rdc_blog/login1";

	public static final String urlLogin1="http://10.21.20.114:8080/rdc_blog/login1";

	public static final String urlLogin="http://yashonzhou.cn/rdc_/login";

	public static final int LOGIN_SUCCESS=1;
	public static final int LOGIN_FAILED=-1;
	public static final int FAILED_CONNECT = 2234;

	/**
	 * 用户id
	 */
	public static int userID=31;


	/**
	 * 注册
	 */
	public static final String URL_SIGNUP1 ="http://10.21.20.114:8080/rdc_blog/register1";
	public static final String URL_SIGNUP ="http://yashonzhou.cn/rdc_/register1";


	/**
	 * 连接博客列表URL
	 */
	public static final String HeadUrl1 = "http://10.21.20.114:8080/rdc_blog/blog/allBlog?pagingMethod=byTime&pageNo=";
	public static final String MidUrl1= "&order=DESC&type=";
	public static final String PicHeadUrl1= "http://10.21.20.114:8080/rdc_blog/image/";

	public static final String HeadUrl = "http://yashonzhou.cn/rdc_/blog/allBlog?pagingMethod=byTime&pageNo=";
	public static final String MidUrl = "&order=DESC&type=";
	public static final String PicHeadUrl = "http://yashonzhou.cn/rdc_/image/";

	/**
	 *  照片墙上传
	 */
	public static final String URL_PHOTO_UPLOAD2 ="http://10.10.122.3:8080/rdc_blog/photo/uploadPhoto";

	public static final String URL_PHOTO_UPLOAD1 ="http://10.21.20.114:8080/rdc_blog/photo/uploadPhoto";

	public static final String URL_PHOTO_UPLOAD ="http://yashonzhou.cn/rdc_/photo/uploadPhoto";


	/**
	 * 照片墙下载
	 */
	public static final String URL_PHOTO_DOWNLOAD2 ="http://10.10.122.3:8080/rdc_blog/photo/showAllPhotos";

	public static final String URL_PHOTO_DOWNLOAD1 ="http://10.21.20.114:8080/rdc_blog/photo/showAllPhotos";


	public static final String URL_PHOTO_DOWNLOAD ="http://yashonzhou.cn/rdc_/photo/showAllPhotos";

	public static final String URL_PHOTO_DOWNLOAD_GET2="http://10.10.122.3:8080/rdc_blog/photos/";

	public static final String URL_PHOTO_DOWNLOAD_GET1="http://10.21.20.114:8080/rdc_blog/photos/";

	public static final String URL_PHOTO_DOWNLOAD_GET="http://yashonzhou.cn/rdc_/photos/";


	/**
	 * 私信聊天
	 */
	public static final String URL_CHAT_POST1="http://10.21.20.114:8080/rdc_blog/msg/sendMsg"; //发送

	public static final String URL_CHAT_POST="http://yashonzhou.cn/rdc_/msg/sendMsg"; //发送

	public static final String URL_CHAT_GET1="http://10.21.20.114:8080/rdc_blog/msg/showMsg/";

	public static final String URL_CHAT_GET="http://yashonzhou.cn/rdc_/msg/showMsg/";

	public static final int SEND_SUCCESS=1;
	public static final int SEND_FAILED=0;

	public static final int GET_SUCCESS=3;
	public static final int GET_FAILED=-1;


	public static final String URL_ICON2 ="http://10.10.122.3:8080/rdc_blog/";
	public static final String URL_ICON1 ="http://10.21.20.114:8080/rdc_blog/";

	public static final String URL_ICON ="http://yashonzhou.cn/rdc_/";





	public static final String[] imageUrls = new String[] {
			"http://img.my.csdn.net/uploads/201309/01/1378037235_7476.jpg",
			"http://img.my.csdn.net/uploads/201309/01/1378037235_9280.jpg",
			"http://img.my.csdn.net/uploads/201309/01/1378037234_3539.jpg",
			"http://img.my.csdn.net/uploads/201309/01/1378037234_6318.jpg",
			"http://img.my.csdn.net/uploads/201309/01/1378037194_2965.jpg",
			"http://img.my.csdn.net/uploads/201309/01/1378037193_1687.jpg",
			"http://img.my.csdn.net/uploads/201309/01/1378037193_1286.jpg",
			"http://img.my.csdn.net/uploads/201309/01/1378037192_8379.jpg",
			"http://img.my.csdn.net/uploads/201309/01/1378037178_9374.jpg",
			"http://img.my.csdn.net/uploads/201309/01/1378037177_1254.jpg",
			"http://img.my.csdn.net/uploads/201309/01/1378037177_6203.jpg",
			"http://img.my.csdn.net/uploads/201309/01/1378037152_6352.jpg",
			"http://img.my.csdn.net/uploads/201309/01/1378037151_9565.jpg",
			"http://img.my.csdn.net/uploads/201309/01/1378037151_7904.jpg",
			"http://img.my.csdn.net/uploads/201309/01/1378037148_7104.jpg",
			"http://img.my.csdn.net/uploads/201309/01/1378037129_8825.jpg",
			"http://img.my.csdn.net/uploads/201309/01/1378037128_5291.jpg",
			"http://img.my.csdn.net/uploads/201309/01/1378037128_3531.jpg",
			"http://img.my.csdn.net/uploads/201309/01/1378037127_1085.jpg",
			"http://img.my.csdn.net/uploads/201309/01/1378037095_7515.jpg",
			"http://img.my.csdn.net/uploads/201309/01/1378037094_8001.jpg",
			"http://img.my.csdn.net/uploads/201309/01/1378037093_7168.jpg",
			"http://img.my.csdn.net/uploads/201309/01/1378037091_4950.jpg",
			"http://img.my.csdn.net/uploads/201308/31/1377949643_6410.jpg",
			"http://img.my.csdn.net/uploads/201308/31/1377949642_6939.jpg",
			"http://img.my.csdn.net/uploads/201308/31/1377949630_4505.jpg",
			"http://img.my.csdn.net/uploads/201308/31/1377949630_4593.jpg",
			"http://img.my.csdn.net/uploads/201308/31/1377949629_7309.jpg",
			"http://img.my.csdn.net/uploads/201308/31/1377949629_8247.jpg",
			"http://img.my.csdn.net/uploads/201308/31/1377949615_1986.jpg",
			"http://img.my.csdn.net/uploads/201308/31/1377949614_8482.jpg",
			 };
	public static final int UPLOAD_ERROR = -1;
	public static final int UPLOAD_SUCCESS =1 ;
	public static final int DOWNLOAD_SUCCESS = 30;












}
