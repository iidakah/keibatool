package keibatool.batch;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Document;

import keibatool.DataShoriUtil;
import keibatool.RaceDownloader;

public class ReceResultMaker {
	/**
	 * レース結果（馬柱）をcsv出力します。
	 * @param filename
	 * @throws IOException
	 */
	public static void main(String[] filename) throws IOException {
		// TODO 自動生成されたメソッド・スタブ
		//ファイル読み込み
		// 出力ファイルの作成
		create(filename[0]);
	}

	private static void create(String filename) throws IOException {
//
//		FileWriter raceInfoCsv = new FileWriter("./raceInfo.csv", false);
//		PrintWriter p4raceInfo= new PrintWriter(new BufferedWriter(raceInfoCsv));
//		DataShoriUtil.createFirstLine(p4raceInfo);
//
//		FileWriter raceDataCsv = new FileWriter("./raceData.csv", false);
//		PrintWriter p4raceData = new PrintWriter(new BufferedWriter(raceDataCsv));
//		p4raceData.print(Columns.RaceData.getValue());
//		p4raceData.println();
//
//		FileWriter racePayCsv = new FileWriter("./racePay.csv", false);
//		PrintWriter p4racePay = new PrintWriter(new BufferedWriter(racePayCsv));
//		p4racePay.print(Columns.RacePayInfo.getValue());
//		p4racePay.println();
//
//		List<Document> docs = RaceDownloader.getRaceDocs(filename);
//		for (Document document : docs) {
//			if (document.select(".raceData").isEmpty()) {
//				continue;
//			}
//			String raceId = DataShoriUtil.findCondition(document.baseUri(), "[0-9]+");
//			DataShoriUtil.createRaceInfo(document, raceId, p4raceInfo);
//			DataShoriUtil.createRacePayInfo(document, raceId, p4racePay);
//			ArrayList<String> horseIdList = DataShoriUtil.updateRaceData(document, raceId, raceDataCsv, p4raceData);
//			DataShoriUtil.updateHorseMstecursively(horseIdList, 2, false);
//		}
//		p4raceInfo.close();
//		p4raceData.close();
//		p4racePay.close();
//		System.out.println("FinishScraping!!!");
	}

}
