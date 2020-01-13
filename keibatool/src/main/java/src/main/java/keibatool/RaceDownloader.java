package src.main.java.keibatool;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class RaceDownloader {

	public static List<Document> getRaceDocs(String filename) throws IOException {
		// TODO 自動生成されたメソッド・スタブ
		File file = new File("./"+filename);
		List<String> urls = new ArrayList<>();
			// Java 6以前
			BufferedReader br = null;
			try {
				br = new BufferedReader(new FileReader(file));
				String text;
				while ((text = br.readLine()) != null) {
					System.out.println(text);
					String[] sts = text.split(",");
					System.out.println(sts.toString());
					urls.addAll(Arrays.asList(sts));
				}
			} catch (FileNotFoundException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			} finally {
				if (br != null) {
					br.close();
				}
			}

		return urls.stream()
				.map(u -> {
					try {
						//https://db.netkeiba.com/race/201906020512/
						Document doc = Jsoup.connect("https://db.netkeiba.com/race/" +u + "/").get();
						System.out.println("Success to download"+u);
						return doc;
					} catch (IOException e) {
						// TODO 自動生成された catch ブロック
						e.printStackTrace();
						System.err.println("Try Again!!");
						try {
							Document doc = Jsoup.connect("https://db.netkeiba.com" +u).get();
							return doc;
						} catch (IOException e1) {
							// TODO 自動生成された catch ブロック
							e1.printStackTrace();
							System.err.println("fail second time!!:"+u);
						}
					}
					return null;
				}).collect(Collectors.toList());
	}

}
