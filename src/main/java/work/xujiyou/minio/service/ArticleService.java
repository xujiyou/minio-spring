package work.xujiyou.minio.service;

import io.minio.MinioClient;
import io.minio.errors.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.xmlpull.v1.XmlPullParserException;
import work.xujiyou.minio.config.MinioProperties;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;

/**
 * ArticleService class
 *
 * @author jiyouxu
 * @date 2019/11/9
 */
@Service
@Slf4j
public class ArticleService {

    private final MinioProperties minioProperties;

    private final MinioClient minioClient;

    @Autowired
    public ArticleService(MinioProperties minioProperties) throws InvalidPortException, InvalidEndpointException {
        this.minioProperties = minioProperties;
        this.minioClient = new MinioClient(minioProperties.getEndpoint(), minioProperties.getPort(),
                minioProperties.getAccessKey(), minioProperties.getSecretKey());
    }

    public String saveArticle(MultipartFile file, String category, String technology) {
        String filePath = category + "/" + technology + "/" + file.getOriginalFilename();
        try {
            boolean isExist = minioClient.bucketExists(minioProperties.getBucket());
            if (!isExist) {
                minioClient.makeBucket(minioProperties.getBucket());
            }
            InputStream inputStream = new ByteArrayInputStream(file.getBytes());
            minioClient.putObject(minioProperties.getBucket(), filePath, inputStream, file.getSize(), new HashMap<>(), null, file.getContentType());
        } catch (InvalidBucketNameException
                | NoSuchAlgorithmException | InsufficientDataException | IOException
                | InvalidKeyException | NoResponseException | XmlPullParserException
                | ErrorResponseException | InternalException | InvalidResponseException
                | RegionConflictException | InvalidArgumentException e) {
            e.printStackTrace();
        }
        return minioProperties.getBucket();
    }

    public InputStream viewArticle(String category, String technology, String fileName) {
        String filePath = category + "/" + technology + "/" + fileName;
        InputStream inputStream = null;
        try {
            inputStream = this.minioClient.getObject(minioProperties.getBucket(), filePath);
        } catch (InvalidBucketNameException | InvalidResponseException | InvalidArgumentException | NoSuchAlgorithmException | InsufficientDataException | IOException | InvalidKeyException | NoResponseException | XmlPullParserException | ErrorResponseException | InternalException e) {
            e.printStackTrace();
        }
        return inputStream;
    }
}
