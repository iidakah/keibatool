package keibatool.entity;

import org.seasar.doma.Entity;
import org.seasar.doma.Id;
import org.seasar.doma.Table;

import lombok.AllArgsConstructor;

@Entity @AllArgsConstructor
@Table(name="UserInfo")
public class UserInfo {

	@Id
	String userId;
	String userName;
}
