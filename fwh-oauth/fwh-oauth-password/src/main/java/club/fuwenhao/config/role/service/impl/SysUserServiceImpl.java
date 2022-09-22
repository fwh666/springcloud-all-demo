package club.fuwenhao.config.role.service.impl;


import club.fuwenhao.config.role.entity.SysUser;
import club.fuwenhao.config.role.mapper.SysUserMapper;
import club.fuwenhao.config.role.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by smlz on 2019/12/20.
 */
@Component
public class SysUserServiceImpl implements ISysUserService {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Override
    public SysUser getByUsername(String username) {
        return sysUserMapper.findByUserName(username);
    }
}
