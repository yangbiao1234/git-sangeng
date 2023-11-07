package com.yangbiao.utils;

import com.yangbiao.domain.entity.Menu;
import com.yangbiao.domain.vo.MenuTreeVo;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class SystemConverter {

    //构造方法 作用为：创建一个新对象时的初始化
    private SystemConverter() {
    }


    public static List<MenuTreeVo> buildMenuSelectTree(List<Menu> menus) {
        List<MenuTreeVo> MenuTreeVos = menus.stream()
                .map(m -> new MenuTreeVo(m.getId(), m.getMenuName(), m.getParentId(), null))
                .collect(Collectors.toList());
        return MenuTreeVos.stream()
                .filter(o -> o.getParentId().equals(0L))
                .peek(o -> o.setChildren(getChildList(MenuTreeVos, o)))
                .collect(Collectors.toList());
    }

    private static List<MenuTreeVo> getChildList(List<MenuTreeVo> list, MenuTreeVo option) {
        return list.stream()
                .filter(o -> Objects.equals(o.getParentId(), option.getId()))
                .peek(o -> o.setChildren(getChildList(list, o)))
                .collect(Collectors.toList());
    }
}