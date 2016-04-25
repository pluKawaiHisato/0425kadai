import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;


public class CalculateSales
{
	public static void main(String[] args)
	{
		//if(args.length != 1);
		HashMap<String, String> branch = new HashMap<String, String>();
		try
		{
			BufferedReader br = new BufferedReader(new FileReader(new File (args[0] , "branch.lst")));
			String str;
			while((str = br.readLine()) != null)
			{
				String[] SalSpl = str.split(",");

				if(SalSpl[0] != "^\\d{3}$")
				{
					System.out.println("支店名義ファイルのフォーマットが不正です");
				}


				branch.put(SalSpl[0], SalSpl[1]);
			}
			//{
				//System.out.println(str);
			//}

			br.close();
		}
		catch(FileNotFoundException e)
		{
			System.out.println("支店定義ファイルが存在しません");
		}
		catch(IOException e)
		{
			System.out.println(e);
		}




//商品定義ファイル
		HashMap<String, String> commodity = new HashMap<String, String>();
		try
		{
			BufferedReader br = new BufferedReader(new FileReader(new File (args[0] , "commodity.lst")));
			String s;
			while((s = br.readLine()) != null)
			{
				String[] ComSpl = s.split(",");

				if(ComSpl[0] !=  "^\\d{8}$")
				{
					System.out.println("商品定義ファイルのフォーマットが不正です");
				}


				commodity.put(ComSpl[0], ComSpl[1]);
				//System.out.println(s);
			}
			br.close();
		}
		catch(FileNotFoundException e)
		{
			System.out.println("商品定義ファイルが存在しません");
		}
		catch(IOException e)
		{
			System.out.println(e);
		}
	}
}