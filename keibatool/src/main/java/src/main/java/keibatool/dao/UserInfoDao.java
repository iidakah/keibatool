package src.main.java.keibatool.dao;

import org.seasar.doma.Dao;
import org.seasar.doma.Insert;
import org.seasar.doma.boot.ConfigAutowireable;

import src.main.java.keibatool.entity.UserInfo;

@ConfigAutowireable
@Dao
public interface UserInfoDao {
	@Insert
	int insert(UserInfo userInfo);
}
