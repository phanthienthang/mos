package h2.ui.se.mo.util;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class MOUtil 
{
	public static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ12345674890";
	public static final Random mRandom = new Random();
	public static final Set<String> mIdentifier = new HashSet<String>();
	
	public String getRandomName()
	{
		StringBuilder builder = new StringBuilder();
		 while(builder.toString().length() == 0) {
		        int length = mRandom.nextInt(5)+5;
		        for(int i = 0; i < length; i++)
		            builder.append(CHARACTERS.charAt(mRandom.nextInt(CHARACTERS.length())));
		        if(mIdentifier.contains(builder.toString())) 
		            builder = new StringBuilder();
		    }
		    return builder.toString();
	}
	
}
