package mimetype.getter.getmimes.Controller;
import mimetype.getter.getmimes.Model.DonwloadMimes;
import mimetype.getter.getmimes.Types.MimeType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Path;

@RestController
@RequestMapping("/mimes")
public class FilesController {

    @Value("${download.path}")
    String FilePath;

    DonwloadMimes mimes;

    @GetMapping
    public void writeMimes(@RequestParam String url, @RequestParam MimeType type) throws IOException {
         new DonwloadMimes(type, url, Path.of(FilePath)).processFiles();
    }
}
