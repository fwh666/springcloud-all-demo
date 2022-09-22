package club.fuwenhao.config.role.service;


import club.fuwenhao.config.role.entity.SysPermission;

import java.util.List;

/**
 * Created by smlz on 2019/12/20.
 */
public interface ISysPermissionService {

    List<SysPermission> findByUserId(Integer userId);
}
