package com.yangbiao.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.fastjson.JSON;
import com.yangbiao.domain.ResponseResult;
import com.yangbiao.domain.entity.Category;
import com.yangbiao.domain.vo.CategoryVo;
import com.yangbiao.domain.vo.ExcelCategoryVo;
import com.yangbiao.enums.AppHttpCodeEnum;
import com.yangbiao.service.CategoryService;
import com.yangbiao.utils.BeanCopyUtils;
import com.yangbiao.utils.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 查询所有分类接口
 */
@RestController
@RequestMapping("/content/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/listAllCategory")
    public ResponseResult listAllCategory(){

        List<CategoryVo> list = categoryService.listAllCategory();

        return ResponseResult.okResult(list);
    }

    /**
     * 文件的下载
     */
    @GetMapping("/export")
    public void export(HttpServletResponse response){

        try{
            //设置下载文件的请求头
            WebUtils.setDownLoadHeader("分类.xlsx",response);

            //获取需要导出的数据
            List<Category> categoryVos = categoryService.list();
            List<ExcelCategoryVo> excelCategoryVos = BeanCopyUtils.copyBeanList(categoryVos, ExcelCategoryVo.class);

            // 这里需要设置不关闭流  把数据写入Excel中
            EasyExcel.write(response.getOutputStream(), ExcelCategoryVo.class).autoCloseStream(Boolean.FALSE).sheet("文章分类")
                    .doWrite(excelCategoryVos);

        } catch(Exception e){
            //如果出现异常也要响应json
            ResponseResult result = ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
            WebUtils.renderString(response, JSON.toJSONString(result));

        }
    }
}
