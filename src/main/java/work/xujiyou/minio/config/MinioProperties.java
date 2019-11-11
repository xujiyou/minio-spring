package work.xujiyou.minio.config;

import lombok.Data;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * MinioProperties class
 *
 * @author jiyouxu
 * @date 2019/11/10
 */
@Configuration
@ConfigurationProperties(prefix = "minio")
@Data
@ToString
public class MinioProperties {

    private String endpoint;

    private Integer port;

    private String accessKey;

    private String secretKey;

    private String bucket;
}
