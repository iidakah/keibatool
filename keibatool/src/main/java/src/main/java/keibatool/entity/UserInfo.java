package src.main.java.keibatool.entity;

import org.seasar.doma.Entity;
import org.seasar.doma.Id;

import lombok.AllArgsConstructor;

@Entity @AllArgsConstructor
public class UserInfo {

	@Id
	String userId;
	String userName;
}
