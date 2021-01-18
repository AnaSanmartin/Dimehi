/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Pojos;

/**
 *
 * @author annys
 */
public class Validator {
    
    public static boolean valString(String s) 
    {
    String str = s.replace(" ", "");
    boolean a = true;
    char comparar;
    boolean validar;

    validar = true;
    for (int i = 0; i < str.length(); i++) 
    {
        comparar = str.charAt(i);
        if (!Character.isAlphabetic(comparar)) {/*returns true if a number is found*/
            validar = false;
            //Meter jPanel
            a = false;
            System.out.println("Introduce only leters, no Numbers\n");

        }
    }
    return a;
    }
    
    // Validate if the age is within a lower and an upper limit
    public static boolean valAgeLimits(int lowerLim, int upperLim, String numberString)
    {
        boolean out = false;
        int num = valAge(numberString);
        
	try
	{
		if((num < upperLim) && (num > lowerLim))
		{	
			System.out.println("In established limits ["+upperLim+","+lowerLim+"]\n");
                        out = true;
		}
                else
                {
                        //Meter jPanel
                        System.out.println("Out of established limits ["+upperLim+","+lowerLim+"]\n");
                }
	}catch(Exception e)
	{
		e.printStackTrace();
		System.out.println("Error Introducing the values");
	}
	return out;
    }
    
    public static int valAge(String stringNumber) 
    {
    //P Methods used to write an integer value without conditions (Just to be an Integer)
        
        boolean out = false;
        int answer = 0;
        
        if (valNumString(stringNumber))
        {
            answer = Integer.parseInt(stringNumber);
            out = true;
        }else
        {
            //Crear aquí un JPanel
            System.out.println(stringNumber);
            System.out.println("outch");
        }
            
        

        return answer;
    }
    
    
    public static boolean valNumString(String val) 
    {
        try 
        {
            Integer.parseInt(val);
            return true;
        } catch (NumberFormatException exception) 
        {
            return false;
        }

    } 
}
