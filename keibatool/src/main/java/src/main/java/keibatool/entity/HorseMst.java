package src.main.java.keibatool.entity;

import java.time.LocalDate;

import org.seasar.doma.Entity;
import org.seasar.doma.Id;

import lombok.Builder;

/*
 * 競走馬情報を管理します。なお履歴管理はしません。めんどくさいので
 * 2003102501,○地エールスタンス,牝,,1995108742,1991190001,000a001c11,吉田照哉,武市康男 (美浦),社台ファーム,26戦3勝 [3-3-1-19]
 */
@Entity
@Builder
public class HorseMst {
 @Id
 String horseId;

 /*
  * 馬の名前
  */
 String horseName;

 /*
  * 性別
  */
 String sex;

 /*
  * 年齢
  */
 int age;

 /*
  * 父
  */
 String sire;

 /*
  * 母
  */
 String broodMare;

 /*
  * 母父
  */
 String bms;

 /*
  * 馬主
  */
  String owner;

  /*
   * 調教師
   */
  String trainer;

  /*
   * 生産者
   */
   String breeder;

   /*
    * 戦績
    */
   String career;

   /*
    * 情報を取得した日
    */
   LocalDate updateDate;
}
