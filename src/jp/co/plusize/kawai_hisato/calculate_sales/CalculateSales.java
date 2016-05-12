package jp.co.plusize.kawai_hisato.calculate_sales;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;


public class CalculateSales
{
	public static void main(String[] args) throws FileNotFoundException
	{
		HashMap<String, Long> branchSumMap = new HashMap<String, Long>();
		HashMap<String, Long> commoditySumMap = new HashMap<String, Long>();
		if(args.length != 1)
		{
			System.out.println("予期せぬエラーが発生しました");
			return;
		}

		//支店定義ファイル
		HashMap<String, String> branchListMap = new HashMap<String, String>();
		
		BufferedReader brBranchList = null;
		try
		{
			brBranchList = new BufferedReader(new FileReader(new File (args[0] + File.separator + "branch.lst")));
			String str;
			while((str = brBranchList.readLine()) != null)
			{
				String[] braSpl = str.split(",", -1);
				
				
				if(braSpl.length != 2)
				{
					System.out.println("支店定義ファイルのフォーマットが不正です");
					return;
				}
				
				if(!braSpl[0].matches("^\\d{3}$"))
				{
					System.out.println("支店定義ファイルのフォーマットが不正です");
					return;
				}
				
				branchListMap.put(braSpl[0], braSpl[1]);
				branchSumMap.put(braSpl[0], 0L);
				
			}
		}
		catch(IOException e)
		{
			System.out.println("支店定義ファイルが存在しません");
			return;
		}
		finally
		{
			try
			{
				brBranchList.close();
			}
			catch(IOException e)
			{
				System.out.println("予期せぬエラーが発生しました");
				return;
			}
		}

		//商品定義ファイル
		HashMap<String, String> commodityListMap = new HashMap<String, String>();
		BufferedReader brCommodityList = null;
		try
		{
			brCommodityList = new BufferedReader(new FileReader(new File (args[0] + File.separator + "commodity.lst")));
			String s;
			while((s = brCommodityList.readLine()) != null)
			{
				String[] comSpl = s.split(",",-1);

				if(comSpl.length != 2)
				{
					System.out.println("商品定義ファイルのフォーマットが不正です");
					return;
				}
				if(!comSpl[0].matches("^\\w{8}$"))
				{
					System.out.println("商品定義ファイルのフォーマットが不正です");
					return;
				}

				commodityListMap.put(comSpl[0], comSpl[1]);
				commoditySumMap.put(comSpl[0], 0L);

			}
		}
		catch(IOException e)
		{
			System.out.println("商品定義ファイルが存在しません");
			return;
		}
		finally
		{
			try
			{
				brCommodityList.close();
			}
			catch(IOException e)
			{
				System.out.println("予期せぬエラーが発生しました");
				return;
			}
		}
		
		//rcdファイルだけ
		File dir = new File(args[0]);
		File files[] = dir.listFiles();
		

		ArrayList<File> rcdList = new ArrayList<File>();

		for(int i = 0; i < files.length; i++)
		{
			
				if(files[i].getName().endsWith(".rcd"))
			{

				//8桁連番のファイルだけArrayListに格納
				String[] fileSpl = files[i].getName().toString().split("\\.");
				int j = 0;
				j = Integer.parseInt(fileSpl[0]);
				if(files[i].isFile())
				{
					if(fileSpl.length == 2)
					{
						if(fileSpl[0].matches("^\\w{8}$"))
						{
							rcdList.add(files[i]);
						}	
					}
				}
				if(j - 1 != i)
				{
					System.out.println("売上ファイル名が連番になっていません");
					return;
				}
				
			}	
		}
		//加算処理
		BufferedReader brRcdFile = null;
		try
		{
			for(int i = 0; i < rcdList.size(); i++)
			{
				String listStr = rcdList.get(i).toString();
				brRcdFile = new BufferedReader(new FileReader(new File(listStr)));
				String sale;
				ArrayList<String> contensOfrcdFile= new ArrayList<String>();
				while((sale = brRcdFile.readLine()) !=null)
				{
					contensOfrcdFile.add(sale);
				}

				if(contensOfrcdFile.size() != 3)
				{
					System.out.println(rcdList.get(i).getName() + "のフォーマットが不正です");
					return;
				}

				long salesSum = Long.parseLong(contensOfrcdFile.get(2));

				//コード不正時のエラー処理
				if(!branchSumMap.containsKey(contensOfrcdFile.get(0)))
				{
					System.out.println(rcdList.get(i).getName() + "の支店コードが不正です");
					return;
				}

				if(!commoditySumMap.containsKey(contensOfrcdFile.get(1)))
				{
					System.out.println(rcdList.get(i).getName() + "の商品コードが不正です");
					return;
				}

				long bratotal = salesSum + branchSumMap.get(contensOfrcdFile.get(0));
				long comtotal = salesSum + commoditySumMap.get(contensOfrcdFile.get(1));

				branchSumMap.put(contensOfrcdFile.get(0), bratotal);
				commoditySumMap.put(contensOfrcdFile.get(1), comtotal);

				//10桁を超えた場合のエラー処理
				String sbt = String.valueOf(bratotal);
				if(sbt.length() > 10)
				{
					System.out.println( "合計金額が10桁を超えました");
					return;
				}

				String sct = String.valueOf(comtotal);
				if(sct.length() > 10)
				{
					System.out.println("合計金額が10桁を超えました");
					return;
				}
			}
		}
		catch(IOException e)
		{
			System.out.println("予期せぬエラーが発生しました");
			return;
		}
		finally
		{
			try
			{
				brRcdFile.close();
			}
			catch(IOException e)
			{
				System.out.println("予期せぬエラーが発生しました");
				return;
			}
		}

		//支店別集計ファイルの出力
		File branchOutFile = new File(args[0] + File.separator + "branch.out");
		BufferedWriter bwBranchOut = null;
 		try
		{
			bwBranchOut = new BufferedWriter(new FileWriter(branchOutFile));

			List<Map.Entry<String,Long>> branchSumEntries =
					new ArrayList<Map.Entry<String,Long>>(branchSumMap.entrySet());
			Collections.sort(branchSumEntries, new Comparator<Map.Entry<String,Long>>()
			{
				@Override
				public int compare(Entry<String,Long> entry1, Entry<String,Long> entry2)
				{
					return ((Long)entry2.getValue()).compareTo((Long)entry1.getValue());
				}
			});

			for(Entry<String, Long> b : branchSumEntries)
			{
				bwBranchOut.write(b.getKey() + "," + branchListMap.get(b.getKey()) + "," + b.getValue());
				bwBranchOut.newLine();
			}
		}
		catch(IOException e)
		{
			System.out.println("予期せぬエラーが発生しました");
			return;
		}
 		finally
 		{
 			try
 			{
 				bwBranchOut.close();
 			}
 			catch(IOException e)
 			{
 				System.out.println("予期せぬエラーが発生しました");
 			}
 					
 		}
		//商品別集計ファイルの出力
		File commodityOutFile = new File(args[0], "commodity.out");
		BufferedWriter bwCommodityOut =null;
		try
		{
			bwCommodityOut = new BufferedWriter(new FileWriter(commodityOutFile));

			List<Map.Entry<String,Long>> commoditySumEntries = new ArrayList<Map.Entry<String,Long>>(commoditySumMap.entrySet());
			Collections.sort(commoditySumEntries, new Comparator<Map.Entry<String,Long>>()
			{
				@Override
				public int compare(Entry<String, Long> entry1, Entry<String, Long> entry2)
				{
					return ((Long)entry2.getValue()).compareTo((Long)entry1.getValue());
				}
			});

			for(Entry<String, Long> c : commoditySumEntries)
			{
				bwCommodityOut.write(c.getKey() + "," + commodityListMap.get(c.getKey()) + "," + c.getValue());
				bwCommodityOut.newLine();
			}
			bwCommodityOut.close();
		}
		catch(IOException e)
		{
			System.out.println("予期せぬエラーが発生しました");
		}
		finally
		{
			try
			{
				bwCommodityOut.close();
			}
			catch(IOException e)
			{
				System.out.println("予期せぬエラーが発生しました");
			}
		}
	}
}