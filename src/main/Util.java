package main;

public class Util {

	public static final int	GAP = Util.rem(.5),
							WIDTH = Util.rem(8), WIDTH_SMALL = Util.rem(5),
							HEIGHT = Util.rem(2);
	
	public static int rem(final double rem) {
		return (int) (rem * 16);
	}
}
