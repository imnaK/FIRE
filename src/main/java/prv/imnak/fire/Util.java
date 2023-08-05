package prv.imnak.fire;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class Util {

	public static final	DateFormat	DATE_FORMAT = new SimpleDateFormat("dd.MM.yyyy");
	public static final long ONE_DAY_IN_UNIX = 86400000L;
	public static final int	GAP = Util.rem(.5),
							WIDTH = Util.rem(8), WIDTH_SMALL = Util.rem(5),
							HEIGHT = Util.rem(2);
	
	public static int rem(final double rem) {
		return (int) (rem * 16);
	}
}
