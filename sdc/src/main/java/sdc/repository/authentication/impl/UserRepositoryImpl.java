package sdc.repository.authentication.impl;

import com.leadingsoft.bizfuse.common.jpa.repository.AbstractRepository;
import com.leadingsoft.bizfuse.common.web.support.Searchable;
import com.querydsl.core.BooleanBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import sdc.model.authentication.QUser;
import sdc.model.authentication.User;
import sdc.repository.authentication.UserRepositoryCustom;

import java.util.List;

public class UserRepositoryImpl extends AbstractRepository implements UserRepositoryCustom {

    @Override
    public Page<User> searchAll(final Searchable searchable, final Pageable pageable) {
        return this.search(this.searchCondition(searchable), pageable, QUser.user);
    }

    @Override
    public List<User> searchAll(final Searchable searchable) {
        return this.query().selectFrom(QUser.user).where(this.searchCondition(searchable)).fetch();
    }

    private BooleanBuilder searchCondition(final Searchable searchable) {
        final QUser qUser = QUser.user;
        final BooleanBuilder where = new BooleanBuilder();
        // 手机号过滤
        where.and(this.equalsStr(qUser.mobile, searchable, "mobile"));
        // 登录名过滤
        where.and(this.equalsStr(qUser.loginId, searchable, "loginId"));
        // 邮箱过滤
        where.and(this.equalsStr(qUser.email, searchable, "email"));
        // 姓名模糊查询
        where.and(this.containsStr(qUser.details.name, searchable, "name"));
        return where;
    }

    @Override
    protected Class<?> getModelClass() {
        return User.class;
    }

}
