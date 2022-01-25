package mimetype.getter.getmimes.Controller;
import mimetype.getter.getmimes.Model.DonwloadMimes;
import mimetype.getter.getmimes.Types.MimeType;
import org.apache.tika.mime.MimeTypeException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public boolean writeMimes(@RequestParam String url, @RequestParam MimeType type) throws IOException {
         mimes = new DonwloadMimes(type, url, Path.of(FilePath));
         return mimes.processFiles();
    }
}
