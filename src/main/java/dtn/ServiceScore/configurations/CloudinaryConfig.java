package dtn.ServiceScore.configurations;

import com.cloudinary.Cloudinary;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration

public class CloudinaryConfig {
    @Bean
    public Cloudinary configKey() {
        Map config = new HashMap<>();
        config.put("cloud_name", "dlomvsdxb");
        config.put("api_key", "741976424531679");
        config.put("api_secret", "eLuRLCK7VTayy9YJ1hZCWyMnWhc");
        Cloudinary cloudinary = new Cloudinary(config);
        return new Cloudinary(config);
    }
}
