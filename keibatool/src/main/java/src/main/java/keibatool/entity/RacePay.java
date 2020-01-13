package src.main.java.keibatool.entity;

import org.seasar.doma.Entity;
import org.seasar.doma.Id;

import lombok.Builder;

/*
 * レースの払い戻し情報
 */
@Entity @Builder
public class RacePay {
	@Id
	String raceId;
	int singleNo;
	int singlePay;
	int fukushoNo1;
	int fukushoPay1;
	int fukushoNo2;
	int fukushoPay2;
	int fukushoNo3;
	int fukushoPay3;
	int fukushoNo4;
	int fukushoPay4;
}
