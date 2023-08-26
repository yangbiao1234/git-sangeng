package com.yangbiao.service.impl;

import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import com.yangbiao.constants.SystemConstants;
import com.yangbiao.domain.ResponseResult;
import com.yangbiao.enums.AppHttpCodeEnum;
import com.yangbiao.exception.SystemException;
import com.yangbiao.service.UploadService;
import com.yangbiao.utils.PathUtils;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

@Service
@Data
@ConfigurationProperties(prefix = "oss")
public class OssUploadService implements UploadService {

    private String accessKey;
    private String secretKey;
    private String bucket;

    @Override
    public ResponseResult uploadImg(MultipartFile img) {
        //获取原始文件名
        String originalFilename = img.getOriginalFilename();
//        String originalFilename = null;
//        try{
//             originalFilename = img.getOriginalFilename();
//        } catch (NullPointerException e) {
//            throw new SystemException(AppHttpCodeEnum.FILE_TYPE_NULL);
//        }

        //对原始文件后缀进行判断
        if(!originalFilename.endsWith(".jpg") && !originalFilename.endsWith(".png")){
            throw new SystemException(AppHttpCodeEnum.FILE_TYPE_ERROR);
        }

        //如果判断通过上传文件到OSS
        String filePath = PathUtils.generateFilePath(originalFilename);

        String url = uploadOss(img, filePath);

        //如果判断上传到oss服务器
        return ResponseResult.okResult(url);

    }


    private String uploadOss(MultipartFile imgFile,String filePath) {
        //构造一个带指定 Region 对象的配置类
        Configuration cfg = new Configuration(Region.autoRegion());
//...其他参数参考类注释
        UploadManager uploadManager = new UploadManager(cfg);
//...生成上传凭证，然后准备上传
//            String accessKey = "your access key";
//            String secretKey = "your secret key";
//            String bucket = "your bucket name";
//默认不指定key的情况下，以文件内容的hash值作为文件名
        String key = filePath;
        try {
            //文件的上传
            InputStream inputStream = imgFile.getInputStream();
            Auth auth = Auth.create(accessKey, secretKey);
            String upToken = auth.uploadToken(bucket);
            try {
                Response response = uploadManager.put(inputStream, key, upToken, null, null);
                //解析上传成功的结果
                DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
                System.out.println(putRet.key);
                System.out.println(putRet.hash);
                return SystemConstants.httpUrl + "/" + key;
            } catch (QiniuException ex) {
                Response r = ex.response;
                System.err.println(r.toString());
                try {
                    System.err.println(r.bodyString());
                } catch (QiniuException ex2) {
                    //ignore
                }
            }
        } catch (Exception ex) {
            //ignore
        }
        return "SystemConstants.httpUrl + / + key";
    }
}


