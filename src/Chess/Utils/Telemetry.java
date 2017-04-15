package Chess.Utils;

/**
 * @author Thomas
 * @date 13/04/2017
 * <p>
 * Project: Chezz
 * Package: Chess.Utils
 */
public class Telemetry
{
	private static Telemetry instance;

	private static long nodesSearched;
	private static long start;
	private static long end;
	private static long maxSearchDepth;

	private Telemetry ()
	{
		Telemetry.nodesSearched = 0;
		Telemetry.maxSearchDepth = 0;
		Telemetry.start = 0;
		Telemetry.end = 0;
	}

	/*
	public static Telemetry getInstance ()
	{
		if (instance == null)
		{
			instance = new Telemetry();
			return instance;
		}
		else
		{
			return instance;
		}
	}
	*/

	public static void start ()
	{
		Telemetry.start = System.nanoTime();
	}

	public static void stop ()
	{
		Telemetry.end = System.nanoTime();
	}

	public static void searchedNode (long depth)
	{
		Telemetry.nodesSearched++;

		if (depth > Telemetry.maxSearchDepth)
		{
			Telemetry.maxSearchDepth = depth;
		}
	}

	public static void printResults ()
	{
		System.out.println("RESULTS");
		long time_ns = Telemetry.end - Telemetry.start;
		double time_us = time_ns / 1000;
		double time_ms = time_ns / 1000000;
		double time_s = time_ns / 1000000000;

		long secs_rem = (long) time_s % 60;
		long mins = (long) (time_s - secs_rem) / 60;

		System.out.println("Searching took " + time_ns + "ns = " + time_us + "µs = " + time_ms + "ms = " + time_s + "s = " + mins + ":" + secs_rem);
		System.out.println("A total of " + nodesSearched + " nodes were searched");
		System.out.println("A depth of " + maxSearchDepth + " plies was reached");
	}
}
