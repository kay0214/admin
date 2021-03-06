package com.personal.sys.service.impl;

import com.personal.common.base.CoreServiceImpl;
import com.personal.common.domain.Tree;
import com.personal.common.exception.IFastException;
import com.personal.common.enums.EnumErrorCode;
import com.personal.common.utils.*;
import com.personal.oss.domain.FileDO;
import com.personal.oss.service.FileService;
import com.personal.sys.dao.DeptDao;
import com.personal.sys.dao.UserDao;
import com.personal.sys.dao.UserRoleDao;
import com.personal.sys.domain.DeptDO;
import com.personal.sys.domain.UserDO;
import com.personal.sys.domain.UserRoleDO;
import com.personal.sys.service.UserService;
import com.personal.sys.vo.UserVO;
import lombok.AllArgsConstructor;
import org.apache.commons.lang.ArrayUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.*;

/**
 * <pre>
 * </pre>
 * 
 * <small> 2018年3月23日 | Aron</small>
 */
@Transactional
@Service("sysUserServiceImpl")
@AllArgsConstructor
public class UserServiceImpl extends CoreServiceImpl<UserDao, UserDO> implements UserService {

    private final UserRoleDao userRoleMapper;
    private final DeptDao deptMapper;
    private final FileService sysFileService;

    @Override
    public UserDO getById(Serializable id) {
        List<Integer> roleIds = userRoleMapper.listRoleId(id);
        UserDO user = baseMapper.selectById(id);
        DeptDO deptDO = deptMapper.selectById(user.getDeptId());
        if(Objects.nonNull(deptDO)){
            user.setDeptName(deptDO.getName());
        }
        user.setRoleIds(roleIds);
        return user;
    }

    @Transactional
    @Override
    public boolean save(UserDO user) {

        String salt = UUIDUtils.get();
        String encodePasswd = PasswdUtils.get(user.getPassword(), salt);
        user.setSalt(salt);
        user.setPassword(encodePasswd);

        int count = baseMapper.insert(user);
        Integer userId = user.getId();
        List<Integer> roles = user.getRoleIds();
        userRoleMapper.removeByUserId(userId);
        List<UserRoleDO> list = new ArrayList<>();
        if(Objects.nonNull(roles)){
            for (Integer roleId : roles) {
                UserRoleDO ur = new UserRoleDO();
                ur.setUserId(userId);
                ur.setRoleId(roleId);
                list.add(ur);
            }
        }
        if (list.size() > 0) {
            userRoleMapper.batchSave(list);
        }
        return retBool(count);
    }

    @Override
    public boolean updateById(UserDO user) {
        int r = baseMapper.updateById(user);
        Integer userId = user.getId();
        List<Integer> roles = user.getRoleIds();
        userRoleMapper.removeByUserId(userId);
        List<UserRoleDO> list = new ArrayList<>();
        for (Integer roleId : roles) {
            UserRoleDO ur = new UserRoleDO();
            ur.setUserId(userId);
            ur.setRoleId(roleId);
            list.add(ur);
        }
        if (list.size() > 0) {
            userRoleMapper.batchSave(list);
        }
        return retBool(r);
    }

    @Override
    public boolean removeById(Serializable userId) {
        userRoleMapper.removeByUserId(userId);
        return retBool(baseMapper.deleteById(userId));
    }

    @Override
    public boolean exist(Map<String, Object> params) {
        return retBool(baseMapper.selectByMap(params).size());
    }

    @Override
    public Set<String> listRoles(Long userId) {
        return null;
    }

    @Override
    public int resetPwd(UserVO userVO, UserDO userDO) {
        if (Objects.equals(userVO.getUserDO().getId(), userDO.getId())) {
            if (Objects.equals(PasswdUtils.get(userVO.getPwdOld(), userDO.getSalt()), userDO.getPassword())) {
                String newSalt = UUIDUtils.get();
                String newPasswd = PasswdUtils.get(userVO.getPwdNew(), newSalt);
                userDO.setPassword(newPasswd);
                userDO.setSalt(newSalt);
                return baseMapper.updateById(userDO);
            } else {
                throw new IFastException("输入的旧密码有误！");
            }
        } else {
            throw new IFastException("你修改的不是你登录的账号！");
        }
    }

    @Override
    public int adminResetPwd(UserVO userVO) {
        UserDO userDO = getById(userVO.getUserDO().getId());
        if ("admin".equals(userDO.getUsername())) {
            throw new IFastException(EnumErrorCode.userUpdatePwd4adminNotAllowed.getCodeStr());
        }

        String salt = UUIDUtils.get();
        String passwd = PasswdUtils.get(userVO.getPwdNew(), salt);
        userDO.setSalt(salt);
        userDO.setPassword(passwd);

        return baseMapper.updateById(userDO);

    }

    @Transactional
    public boolean deleteBatchIds(List<? extends Serializable> idList) {
        int count = baseMapper.deleteBatchIds(idList);
        userRoleMapper.deleteBatchIds(idList);
        return retBool(count);
    }

    @Override
    public Tree<DeptDO> getTree() {
        List<Tree<DeptDO>> trees = new ArrayList<Tree<DeptDO>>();
        List<DeptDO> depts = deptMapper.selectList(null);
        Integer[] pDepts = deptMapper.listParentDept();
        Integer[] uDepts = baseMapper.listAllDept();
        Integer[] allDepts = (Integer[]) ArrayUtils.addAll(pDepts, uDepts);
        for (DeptDO dept : depts) {
            if (!ArrayUtils.contains(allDepts, dept.getId())) {
                continue;
            }
            Tree<DeptDO> tree = new Tree<DeptDO>();
            tree.setId(dept.getId().toString());
            tree.setParentId(dept.getParentId().toString());
            tree.setText(dept.getName());
            Map<String, Object> state = new HashMap<>(16);
            state.put("opened", true);
            state.put("mType", "dept");
            tree.setState(state);
            trees.add(tree);
        }
        List<UserDO> users = baseMapper.selectList(null);
        for (UserDO user : users) {
            Tree<DeptDO> tree = new Tree<DeptDO>();
            tree.setId(user.getId().toString());
            tree.setParentId(user.getDeptId().toString());
            tree.setText(user.getName());
            Map<String, Object> state = new HashMap<>(16);
            state.put("opened", true);
            state.put("mType", "user");
            tree.setState(state);
            trees.add(tree);
        }
        // 默认顶级菜单为０，根据数据库实际情况调整
        Tree<DeptDO> t = BuildTree.build(trees);
        return t;
    }

    @Override
    public int updatePersonal(UserDO userDO) {
        return baseMapper.updateById(userDO);
    }

    @Override
    public Map<String, Object> updatePersonalImg(MultipartFile file, String avatar_data, Integer userId) throws Exception {
        String fileName = file.getOriginalFilename();
        fileName = UUIDUtils.get() + "." + fileName.substring(fileName.lastIndexOf(".") + 1);
        String url = "";

        // 获取图片后缀
        String prefix = fileName.substring((fileName.lastIndexOf(".") + 1));
        String[] str = avatar_data.split(",");
        // 获取截取的x坐标
        int x = (int) Math.floor(Double.parseDouble(str[0].split(":")[1]));
        // 获取截取的y坐标
        int y = (int) Math.floor(Double.parseDouble(str[1].split(":")[1]));
        // 获取截取的高度
        int h = (int) Math.floor(Double.parseDouble(str[2].split(":")[1]));
        // 获取截取的宽度
        int w = (int) Math.floor(Double.parseDouble(str[3].split(":")[1]));
        // 获取旋转的角度
        int r = Integer.parseInt(str[4].split(":")[1].replaceAll("}", ""));
        try {
            BufferedImage cutImage = ImageUtils.cutImage(file, x, y, w, h, prefix);
            BufferedImage rotateImage = ImageUtils.rotateImage(cutImage, r);
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            ImageIO.write(rotateImage, prefix, out);
            // 转换后存入数据库
            byte[] b = out.toByteArray();
            url = sysFileService.upload(b, fileName);
        } catch (Exception e) {
            log.warn(e.getMessage());
            throw new IFastException("图片裁剪错误！！");
        }
        Map<String, Object> result = new HashMap<>();
        FileDO sysFile = new FileDO(FileType.fileType(fileName), url, LocalDateTime.now());
        if (sysFileService.save(sysFile)) {
            UserDO userDO = new UserDO();
            userDO.setId(userId);
            userDO.setIcon(url);
            if (retBool(baseMapper.updateById(userDO))) {
                result.put("url", sysFile.getUrl());
            }
        }
        return result;
    }

}
