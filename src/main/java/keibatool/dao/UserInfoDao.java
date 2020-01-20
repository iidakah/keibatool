package keibatool.dao;

import org.seasar.doma.Dao;
import org.seasar.doma.Select;
import org.seasar.doma.boot.ConfigAutowireable;

import keibatool.AppConfig;
import keibatool.entity.UserInfo;


@ConfigAutowireable
@Dao(config = AppConfig.class)
public interface UserInfoDao {
	@Select
	UserInfo selectAll();
}
