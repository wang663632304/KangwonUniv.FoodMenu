package kr.yudonguk.util;

import java.util.ArrayList;

public class StringUtil
{
	public static String[] split(String input, String regex,
			boolean withoutEmpty)
	{
		if (!withoutEmpty)
		{
			return input.split(regex);
		}
		else
		{
			ArrayList<String> result = new ArrayList<String>();

			for (String splited : input.split(regex))
			{
				if (splited.isEmpty())
					continue;
				result.add(splited);
			}

			return result.toArray(new String[result.size()]);
		}
	}

	public static String removeBracket(String input)
	{
		return removeBracket(input, 1);
	}

	public static String removeBracket(String input, int depth)
	{
		if (depth == 0)
			return input;
		if (depth < 0)
			depth = Integer.MAX_VALUE;

		String bracketRegex = "\\([^\\(]*\\)";

		String result = input.replace(bracketRegex, "");
		for (int i = 0; i < depth && result.matches(bracketRegex); i++)
		{
			result = result.replace(bracketRegex, "");
		}

		return result;
	}
}
