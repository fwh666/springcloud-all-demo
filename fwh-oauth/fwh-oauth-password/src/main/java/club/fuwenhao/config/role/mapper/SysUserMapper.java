package club.fuwenhao.config.role.mapper;


import club.fuwenhao.config.role.entity.SysUser;

/**
 * Created by smlz on 2019/12/20.
 */
public interface SysUserMapper {

    SysUser findByUserName(String userName);
}
