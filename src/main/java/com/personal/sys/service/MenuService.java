package com.personal.sys.service;

import com.personal.common.base.CoreService;
import com.personal.common.domain.Tree;
import com.personal.sys.domain.MenuDO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

/**
 * <pre>
 * </pre>
 * <small> 2018年3月23日 | Aron</small>
 */
@Service
public interface MenuService extends CoreService<MenuDO> {
    Tree<MenuDO> getSysMenuTree(Integer id);

    List<Tree<MenuDO>> listMenuTree(Integer id);

    Tree<MenuDO> getTree();

    Tree<MenuDO> getTree(Integer id);

    Set<String> listPerms(Integer userId);
}
