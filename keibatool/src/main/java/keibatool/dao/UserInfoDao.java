package keibatool.dao;

import org.seasar.doma.Dao;
import org.seasar.doma.Insert;
import org.seasar.doma.Select;
import org.seasar.doma.boot.ConfigAutowireable;
import org.springframework.transaction.annotation.Transactional;

import keibatool.entity.UserInfo;


@ConfigAutowireable
@Dao
public interface UserInfoDao {
	@Insert
	@Transactional
	int insert(UserInfo userInfo);

	@Select
	UserInfo selectAll();
}
