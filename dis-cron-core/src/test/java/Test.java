import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * 
 */

/**
 * @author fulianqiu
 *
 */
public class Test {

	/**
	 * @param args
	 * @throws UnsupportedEncodingException 
	 */
	public static void main(String[] args) {
		String param="test=1&test=2&test=3";
		try {
			param=URLEncoder.encode(param, "UTF-8");
			System.out.println(param);
			param=URLDecoder.decode(param, "UTF-8");
			param=URLEncoder.encode(param, "UTF-8");
			System.out.println(param);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
