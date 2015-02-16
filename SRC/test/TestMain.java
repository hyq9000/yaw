import java.io.File;
import java.util.HashMap;
import java.util.Map;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;

public class TestMain {
	public static void main(String[] args) {
		//00001010
		byte a=10,b=1;
		byte c=(byte)(a|b);
		System.out.println(c);
	}
	
	
	public static void testQrCode(){
		 String content = "120605181003;http://www.cnblogs.com/jtmjx";
	     String path = "C:/Users/Administrator/Desktop/testImage";	     
	     MultiFormatWriter multiFormatWriter = new MultiFormatWriter();	     
	     Map hints = new HashMap();
	     hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
	     BitMatrix bitMatrix = multiFormatWriter.encode(content, BarcodeFormat.QR_CODE, 400, 400,hints);
	     File file1 = new File(path,"test.jpg");
	     MatrixToImageWriter (bitMatrix, "jpg", file1);
	}
	///登陆:http://localhost:8088/yaw/action/member!login.action?loginName=hyq9000&password=123456&captcha=f3hg
}
