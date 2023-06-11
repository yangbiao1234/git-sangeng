package com.yangbiao.utils;

import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class BeanCopyUtils {

    /**
     * //符合检索条件对象的所有字段
     *         List<Article> records = page.getRecords();
     *         //在次包装
     *         List<HotArticleVo> articleVos = new ArrayList<HotArticleVo>();
     *         //bean拷贝
     *         //records符合查询条件的对象的所有字段过于敏感 用vo在次包装
     *         for (Article record : records) {
     *             HotArticleVo vo = new HotArticleVo();
     *             BeanUtils.copyProperties(record, vo);
     *             articleVos.add(vo);
     *         }
     */

    //只需提供静态方法
    private BeanCopyUtils() {
    }

    //单个实体类的copy
    public static <V> V copyBean(Object source, Class<V> clazz) {
        //创建目标对象
        V result = null;
        try {
            result = clazz.newInstance();

            //实现Bean的copy
            BeanUtils.copyProperties(source, result);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    //集合的copy 使用stream
    public static <V> List<V> copyBeanList(List<Object> list, Class<V> clazz) {
        return list.stream()
                .map(o -> copyBean(o, clazz))
                .collect(Collectors.toList());
    }
}
