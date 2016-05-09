import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;


public class CalculateSales
{
	public static void main(String[] args)
	{
		HashMap<String, Long> braSum = new HashMap<String, Long>();
		HashMap<String, Long> comSum = new HashMap<String, Long>();


		//支店定義ファイル
		HashMap<String, String> branch = new HashMap<String, String>();
		
		try
		{
			BufferedReader br = new BufferedReader(new FileReader(new File (args[0] , "branch.lst")));
			String str;
			while((str = br.readLine()) != null)
			{
				String[] braSpl = str.split(",");

//				Pattern p = Pattern.compile("^\\d{3}$");
//				Matcher m = p.matcher(braSpl[0]);
//				braSpl[0].matches("^\\d{3}$");

				if(!braSpl[0].matches("^\\d{3}$"))

//				if(!m.find())
				{
					System.out.println("支店名義ファイルのフォーマットが不正です");
					br.close();
					return;
				}

				branch.put(braSpl[0], braSpl[1]);
				braSum.put(braSpl[0], 0L);
				{

				}
			}
			//{
				//System.out.println(str);
			//}

			br.close();
		}
		catch(FileNotFoundException e)
		{
			System.out.println("支店定義ファイルが存在しません");
			return;
		}
		catch(IOException e)
		{
			System.out.println(e);
		}
		System.out.println(branch.entrySet());
		System.out.println(braSum.entrySet());




//商品定義ファイル
		HashMap<String, String> commodity = new HashMap<String, String>();
		try
		{
			BufferedReader br = new BufferedReader(new FileReader(new File (args[0] , "commodity.lst")));
			String s;
			while((s = br.readLine()) != null)
			{
				String[] comSpl = s.split(",");

//				Pattern p = Pattern.compile("^\\w{8}$");
//				Matcher m = p.matcher(comSpl[0]);
//				if(!m.find())

				if(!comSpl[0].matches("^\\w{8}$"))
				{
					System.out.println("商品定義ファイルのフォーマットが不正です");
					br.close();
					return;
				}


				commodity.put(comSpl[0], comSpl[1]);
				comSum.put(comSpl[0], 0L);
				//System.out.println(s);
			}
			br.close();
		}
		catch(FileNotFoundException e)
		{
			System.out.println("商品定義ファイルが存在しません");
			return;
		}
		catch(IOException e)
		{
			System.out.println(e);
		}
		System.out.println(commodity.entrySet());
		System.out.println(comSum.entrySet());

//rcdファイルだけ
		File dir = new File(args[0]);
		File files[] = dir.listFiles();


		ArrayList<File> rcdList = new ArrayList<File>();

		for(int i = 0; i < files.length; i++)
		{
			if(files[i].getName().endsWith(".rcd"))
			{

//8桁
				String[] fileSpl = files[i].getName().toString().split("\\.");
				int j = 0;
				if(fileSpl[0].matches("^\\w{8}$"))
				{
					 j = Integer.parseInt(fileSpl[0]);
				}
				else
				{
					System.out.println(files[i] + "は売上ファイル名が8桁ではありません");
					return;
				}
				//rcdNo.add( new Integer(fileSpl[0]).intValue());




//連番処理
				if(j - 1 == i)
				{
					rcdList.add(files[i]);
				}
				else
				{
					System.out.println(files[i] + "は売上ファイル名が連番になっていません");
					return;
				}
			}
		}
		System.out.println(rcdList);

//加算処理
		try
		{
			for(int i = 0; i < rcdList.size(); i++)
			{
				String listStr = rcdList.get(i).toString();
				BufferedReader br = new BufferedReader(new FileReader(new File(listStr)));
				String sale;
				ArrayList<String> contensOfrcdFile= new ArrayList<String>();
				while((sale = br.readLine()) !=null)
				{
					contensOfrcdFile.add(sale);
				}
				
				if(contensOfrcdFile.size() != 3)
				{
					System.out.println(rcdList.get(i).getName() + "のフォーマットが不正です");
					br.close();
					return;
				}

				//System.out.println(listStr);
				//System.out.println(rcdList.get(k));
				//System.out.println(rcdList.get(k).getName());

				System.out.println(contensOfrcdFile.get(0));//=支店コード
				System.out.println(contensOfrcdFile.get(1));//=商品コード
				System.out.println(contensOfrcdFile.get(2));//=売上額

				//if()

				long l = Long.parseLong(contensOfrcdFile.get(2));
				 
				if(!braSum.containsKey(contensOfrcdFile.get(0)))
				{
					System.out.println(rcdList.get(i).getName() + "の支店コードが不正です");
					br.close();
					return;
				}
				
				if(!comSum.containsKey(contensOfrcdFile.get(1)))
				{
					System.out.println(rcdList.get(i).getName() + "商品のコードが不正です");
					br.close();
					return;
				}
				
				long bratotal = l + braSum.get(contensOfrcdFile.get(0));
				long comtotal = l + comSum.get(contensOfrcdFile.get(1));

				braSum.put(contensOfrcdFile.get(0), bratotal);
				comSum.put(contensOfrcdFile.get(1), comtotal);

//10桁を超えた場合のエラー処理
				String sbt = String.valueOf(bratotal);
				if(sbt.length() > 10)
				{
					System.out.println(contensOfrcdFile.get(0) + "は合計金額が10桁を超えました");
					br.close();
					return;
				}

				String sct = String.valueOf(comtotal);
				if(sct.length() > 10)
				{
					System.out.println(contensOfrcdFile.get(1) + "は合計金額が10桁を超えました");
					br.close();
					return;
				}
				
				


				



				br.close();
				System.out.println(braSum.entrySet());
				System.out.println(comSum.entrySet());
				System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
			}

		}
		catch(IOException e)
		{
			System.out.println(e);
		}
	}
}